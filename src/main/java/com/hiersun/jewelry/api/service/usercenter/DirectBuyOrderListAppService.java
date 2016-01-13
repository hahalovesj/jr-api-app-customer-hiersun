package com.hiersun.jewelry.api.service.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.constant.StatusMap;
import com.hiersun.jewelry.api.direct.domain.JrdsOrderVo;
import com.hiersun.jewelry.api.direct.service.DirectOrderService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4018;
import com.hiersun.jewelry.api.entity.response.Response4010;
import com.hiersun.jewelry.api.entity.vo.ResponseServiceOrder;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("directBuyOrderListAppService")
public class DirectBuyOrderListAppService implements BaseService {

	private static Logger log = Logger.getLogger(DirectBuyOrderListAppService.class);
	
	@Resource
	DirectOrderService directOrderService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4018 body = JSON.parseObject(bodyStr, Request4018.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("directBuyOrderList 	4018	接口请求消息体：" + reqHead.toString());
		log.info("directBuyOrderList 	4018	接口请求消息体：" + bodyStr);
		
		try {
			Request4018 body = JSON.parseObject(bodyStr, Request4018.class);
			int orderTypeCode = body.getOrderTypeCode();
			Integer[] status = StatusMap.SERVICE_ORDER_STAUTECODE_MAP.get(orderTypeCode);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("status", status);
			paramMap.put("buyerMemeberId", userId);
			paramMap.put("start", body.getPageNO() * 20);
			paramMap.put("end", 20);

			// 总条数
			int countNumber = directOrderService.countOrder(paramMap);
			// 记录为0 不做进一步查询，列表为空
			if (countNumber == 0) {
				Response4010 res = new Response4010();
				res.setIsEnd(true);
				res.setPageNO(body.getPageNO());
				ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
				return this.packageMsgMap(res, respHead);
			}
			// 根据买家id和状态查询订单列表
			List<JrdsOrderVo> orderList = directOrderService.getOrderList(paramMap);

			int nowNumber = (body.getPageNO() + 1) * 20;
			// 返回信息
			Response4010 resp = new Response4010();
			resp.setPageNO(body.getPageNO());
			// 是否是最后一页
			if (countNumber > nowNumber) {
				resp.setIsEnd(false);
			} else {
				resp.setIsEnd(true);
			}
			List<ResponseServiceOrder> resultList = new ArrayList<ResponseServiceOrder>();
			ResponseServiceOrder responseServiceOrder = null;
			// 封装订单列表集合
			for (int i = 0; i < orderList.size(); i++) {
				responseServiceOrder = new ResponseServiceOrder();
				responseServiceOrder.setCreateTime(DateUtil.dateToStr(orderList.get(i).getCreated(),
						"yyyy-MM-dd HH:mm:ss"));
				responseServiceOrder.setGoodsName(orderList.get(i).getGoodsName());
				responseServiceOrder.setGoodsPicUrl(orderList.get(i).getGoodsPic());
				responseServiceOrder.setOrderID(orderList.get(i).getId());
				responseServiceOrder.setOrderNO(orderList.get(i).getOrderNo());
				responseServiceOrder.setOrderPrice(orderList.get(i).getOrderAmount().doubleValue());
//				responseServiceOrder.setOrderPrice(orderList.get(i).getPayAmount().doubleValue());
				responseServiceOrder.setGoodsBuyPrice(orderList.get(i).getGoodsPrice().doubleValue());
				
				responseServiceOrder.setOrderStatusCode(StatusMap.DIRECT_ORDER_STAUTECODE_APP_MAP.get((int) orderList.get(i).getOrderStatus()));
				responseServiceOrder.setOrderStatusDes(StatusMap.DIRECT_ORDER_STAUTEDES_APP_MAP.get((int) orderList.get(i).getOrderStatus()));
				resultList.add(responseServiceOrder);
			}
			resp.setOrderList(resultList);
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(resp, respHead);
		} catch (Exception e) {
			log.error("directBuyOrderList 	4018	接口发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseBody resp = new ResponseBody();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
			return this.packageMsgMap(resp, respHead);
		}

	}

	private Map<String, Object> packageMsgMap(Response4010 res, ResponseHeader respHead) {
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
