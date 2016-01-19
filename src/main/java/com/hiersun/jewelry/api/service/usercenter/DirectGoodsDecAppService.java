package com.hiersun.jewelry.api.service.usercenter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.constant.StatusMap;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.dictionary.QualificationType;
import com.hiersun.jewelry.api.direct.domain.QueryGoodsByParamVo;
import com.hiersun.jewelry.api.direct.pojo.JrdsOrderLog;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.direct.service.DirectOrderService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4024;
import com.hiersun.jewelry.api.entity.response.Response4024;
import com.hiersun.jewelry.api.entity.vo.ResponseJrdsGood;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("directGoodsDecAppService")
public class DirectGoodsDecAppService implements BaseService {

	private static Logger log = Logger.getLogger(DirectGoodsDecAppService.class);

	@Resource
	private DirectGoodsService directGoodsService;
	@Resource
	private DirectOrderService directOrderService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4024 body = JSON.parseObject(bodyStr, Request4024.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("directGoodsDec 	4024	接口请求消息体：" + reqHead.toString());
		log.info("directGoodsDec 	4024	接口请求消息体：" + bodyStr);

		try {
			Request4024 body = JSON.parseObject(bodyStr, Request4024.class);
			Response4024 res = new Response4024();
			// 查询good对象 辅助以订单和审核相关信息
			QueryGoodsByParamVo queryGoodsByParamVo = directGoodsService
					.getGoodsInfoByGoodNo(body.getGoodsNO(), userId);
			if(queryGoodsByParamVo == null){
				ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 200102);
				ResponseBody resbody = new ResponseBody();
				return this.packageMsgMap(resbody, respHead);
			}
			// 打包返回信息
			ResponseJrdsGood responseJrdsGood = this.packageResponseJrdsGood(queryGoodsByParamVo);
			// 商品交易日志信息
			List<JrdsOrderLog> jrdsOrderLogs = directOrderService.getJrdsOrderLogByOrderId(queryGoodsByParamVo
					.getOrderId());

			Map<Integer, String> map = new HashMap<Integer, String>();
			for (JrdsOrderLog jrdsOrderLog : jrdsOrderLogs) {
				map.put(jrdsOrderLog.getLogStatus().intValue(),
						DateUtil.dateToStr(jrdsOrderLog.getCreated(), "yyyy-MM-dd HH:mm:ss"));
			}
			String orderLog = this.packageOrderLog(map, queryGoodsByParamVo);
			responseJrdsGood.setOrderLogs(orderLog);

			// 消息头
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			// 消息体
			res.setGoods(responseJrdsGood);
			// 消息
			return this.packageMsgMap(res, respHead);
		} catch (Exception e) {
			log.error("directGoodsDec 	4024	接口发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody res = new ResponseBody();
			return this.packageMsgMap(res, respHead);
		}

	}

	// 打包
	private ResponseJrdsGood packageResponseJrdsGood(QueryGoodsByParamVo vo) throws Exception {

		ResponseJrdsGood jesponseJrdsGood = new ResponseJrdsGood();
		// 封装订单列表集合
		jesponseJrdsGood = new ResponseJrdsGood();
		//jesponseJrdsGood.setCreateTime(DateUtil.dateToStr(vo.getCreated(), "yyyy-MM-dd HH:mm:ss"));
		jesponseJrdsGood.setGoodsName(vo.getGoodName());
		jesponseJrdsGood.setGoodsPicUrl(Commons.PIC_DOMAIN + vo.getHostGragp());
		jesponseJrdsGood.setGoodsID(vo.getId());
		jesponseJrdsGood.setGoodsNO(vo.getGoodNo());
		jesponseJrdsGood.setGoodsPrice(vo.getDirectPrice().doubleValue());
		jesponseJrdsGood.setGoodsBuyPrice(vo.getBuyingPrice().doubleValue());
		jesponseJrdsGood.setOrderMsg(vo.getOrderMsg());
		jesponseJrdsGood.setOrderNO(vo.getOrderNo());
		// 成交价
		BigDecimal commission = vo.getCommission()==null ? BigDecimal.ZERO :vo.getCommission();
		BigDecimal commissionPrice = commission.multiply(vo.getDirectPrice()).setScale(2,RoundingMode.HALF_UP);
		Double settlement = vo.getDirectPrice().subtract(
				commissionPrice
						).doubleValue();
		jesponseJrdsGood.setSettlementPrice(settlement);
		jesponseJrdsGood.setCommissionPrice(commissionPrice.doubleValue());
		
		// orderStatus 为空时 证明没有产生订单，状态需要看goodStatus goodStatus为空说明还没有审核
		Integer orderStatusCode = null;
		if(vo.getApplicationStatus().intValue()==1){
			orderStatusCode = -3;//待审核
		}else if(vo.getApplicationStatus().intValue()==2){
			orderStatusCode = -2;//审核未通过
		}else{
			//有订单号的话	就用订单的状态，没有订单的话为-1 售卖中
			orderStatusCode = vo.getOrderStatus() != null ? vo.getOrderStatus().intValue() : -1;
		}
		
		jesponseJrdsGood.setOrderStatusCode(StatusMap.DIRECT_GOODS_STAUTECODE_APP_MAP.get(orderStatusCode));
		jesponseJrdsGood.setOrderStatusDes(StatusMap.DIRECT_GOODS_STAUTEDES_APP_MAP.get(orderStatusCode));


		return jesponseJrdsGood;
	}

	private String packageOrderLog(Map<Integer, String> logMap, QueryGoodsByParamVo queryGoodsByParamVo)
			throws Exception {

		StringBuffer returnStrBuffer = new StringBuffer();

		returnStrBuffer.append("申请编号:").append(queryGoodsByParamVo.getApplicationNo()).append("\r\n\r\n");
		if (queryGoodsByParamVo.getApplicationStatus().intValue() == 1) {
			return returnStrBuffer.toString();
		} else {
			returnStrBuffer.append("审核时间:")
					.append(DateUtil.dateToStr(queryGoodsByParamVo.getAuditTime(), "yyyy-MM-dd HH:mm:ss"))
					.append("\r\n\r\n");
		}

		if (queryGoodsByParamVo.getOrderNo() == null) {
			return returnStrBuffer.toString();
		} else {
			returnStrBuffer.append("订单编号:")
					.append(queryGoodsByParamVo.getOrderNo()).append("\r\n\r\n");

			returnStrBuffer.append("下单时间:").append(logMap.get(0)).append("\r\n\r\n");
		}

		if (queryGoodsByParamVo.getPayType() == null) {
			return returnStrBuffer.toString();
		} else {
			returnStrBuffer.append("支付方式:").append(QualificationType.PAY_TYPE_MAP.get(queryGoodsByParamVo.getPayType()))
					.append("\r\n\r\n");

			returnStrBuffer.append("支付时间：").append(logMap.get(1)).append("\r\n\r\n");
		}

		if (queryGoodsByParamVo.getOrderStatus().intValue() < 2) {
			return returnStrBuffer.toString();
		} else {
			returnStrBuffer.append("发货到平台:").append(logMap.get(2)).append("\r\n\r\n");
		}

		if (queryGoodsByParamVo.getOrderStatus().intValue() < 3) {
			return returnStrBuffer.toString();
		} else {
			returnStrBuffer.append("鉴定时间:").append(logMap.get(3) != null ? logMap.get(3) : logMap.get(4))
					.append("\r\n\r\n");
		}

		if (queryGoodsByParamVo.getOrderStatus().intValue() < 5) {
			return returnStrBuffer.toString();
		} else {
			// 需要确认的才做确认记录
			if (logMap.get(5) != null) {
				returnStrBuffer.append("再次确认时间").append(logMap.get(5)).append("\r\n\r\n");
			}
		}

		if (queryGoodsByParamVo.getOrderStatus().intValue() < 9) {
			return returnStrBuffer.toString();
		} else {
			// 需要确认的才做确认记录
			if (logMap.get(9) != null) {
				returnStrBuffer.append("发货到买家时间").append(logMap.get(9)).append("\r\n\r\n");
			}
		}

		if (queryGoodsByParamVo.getOrderStatus().intValue() != 10) {
			return returnStrBuffer.toString();
		} else {
			// 需要确认的才做确认记录
			if (logMap.get(10) != null) {
				returnStrBuffer.append("发货到买家时间").append(logMap.get(10)).append("\r\n\r\n");
			}
		}

		return returnStrBuffer.toString();
	}

	private Map<String, Object> packageMsgMap(Response4024 res, ResponseHeader respHead) {
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("body", res);
		responseMsg.put("head", respHead);
		return responseMsg;
	}

	private Map<String, Object> packageMsgMap(ResponseBody res, ResponseHeader respHead) {
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("body", res);
		responseMsg.put("head", respHead);
		return responseMsg;
	}
}
