package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.direct.pojo.JrdsOrder;
import com.hiersun.jewelry.api.direct.service.DirectOrderService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4013;
import com.hiersun.jewelry.api.entity.response.Response4013;
import com.hiersun.jewelry.api.expressinfo.domain.ExpressVo;
import com.hiersun.jewelry.api.expressinfo.pojo.ExpressInfo;
import com.hiersun.jewelry.api.expressinfo.service.ExpressInfoService;
import com.hiersun.jewelry.api.expressinfo.service.LogisticsTrackingInfoService;
import com.hiersun.jewelry.api.orderservice.pojo.JrasOrder;
import com.hiersun.jewelry.api.orderservice.service.OrderService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("putLogisticsAppService")
public class PutLogisticsAppService implements BaseService {

	private static Logger log = Logger.getLogger(PutLogisticsAppService.class);

	@Resource
	DirectOrderService directOrderService;

	@Resource
	ExpressInfoService expressInfoService;

	@Resource
	LogisticsTrackingInfoService logisticsTrackingInfoService;

	@Resource
	OrderService orderService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4013 body = JSON.parseObject(bodyStr, Request4013.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("putLogistics 	4013	接口请求消息体：" + reqHead.toString());
		log.info("putLogistics 	4013	接口请求消息体：" + bodyStr);
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", "顺丰速运");
		try {
			Request4013 body = JSON.parseObject(bodyStr, Request4013.class);

			ExpressVo evo = new ExpressVo();
			evo.setEcName(map.get(body.getCompanyCode()));
			evo.setEcCode(Byte.valueOf(body.getCompanyCode()));
			evo.setExpressNo(body.getNumbers());
			if(body.getReturnAddressId()!=null){
				evo.setRerturnAddressId(body.getReturnAddressId());
			}
			if (body.getOrderNO().startsWith("30")) {
				JrdsOrder dsOrder = directOrderService.selectOrderByOrderNo(body.getOrderNO());
				evo.setOrderId(dsOrder.getId());
				evo.setBusinessType(Byte.valueOf("2"));//直售的业务类型为2
				evo.setJrdsOrder(dsOrder);
				
				ExpressInfo expressInfo = new ExpressInfo();
				expressInfo.setOrderId(dsOrder.getId());
				// 查询快递单号是否存在
				ExpressInfo ei = expressInfoService.getExpressInfo(expressInfo);
				if (ei != null) {
					log.error("订单信息已经存在，无须重新提交订单！订单号：" + body.getOrderNO());
					ResponseBody res = new ResponseBody();
					ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
					return this.packageMsgMap(res, respHead);
				}
				// 保存物流信息
				expressInfoService.saveExpressJrds(evo);
			} else if (body.getOrderNO().startsWith("31")) {
				JrasOrder asOrder = orderService.selectOrderByOrderNo(body.getOrderNO());
				evo.setOrderId(asOrder.getId());
				evo.setJrasOrder(asOrder);
				evo.setBusinessType(Byte.valueOf("1"));//服务的业务类型为1
				
				ExpressInfo expressInfo = new ExpressInfo();
				expressInfo.setOrderId(asOrder.getId());
				// 查询快递单号是否存在
				ExpressInfo ei = expressInfoService.getExpressInfo(expressInfo);
				if (ei != null) {
					log.error("订单信息已经存在，无须重新提交订单！订单号：" + body.getOrderNO());
					ResponseBody res = new ResponseBody();
					ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
					return this.packageMsgMap(res, respHead);
				}
				// 保存服务物流信息
				expressInfoService.saveExpress(evo);
			}
			// 订阅物流信息
			logisticsTrackingInfoService.regTracking(body.getCompanyCode(), body.getNumbers());

			Response4013 responseBody = new Response4013();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			log.error("putLogistics 	4013	接口发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseBody res = new ResponseBody();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
			return this.packageMsgMap(res, respHead);
		}
	}
	
	private Map<String, Object> packageMsgMap(Response4013 res, ResponseHeader respHead) {
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
