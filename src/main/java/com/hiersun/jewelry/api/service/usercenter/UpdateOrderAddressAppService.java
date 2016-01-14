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
import com.hiersun.jewelry.api.entity.request.Request4028;
import com.hiersun.jewelry.api.expressinfo.service.ExpressInfoService;
import com.hiersun.jewelry.api.orderservice.pojo.JrasOrder;
import com.hiersun.jewelry.api.orderservice.service.OrderService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;


@Service("updateOrderAddressAppService")
public class UpdateOrderAddressAppService implements BaseService {
	
	private static final Logger log = Logger.getLogger(StationMessageAppService.class);
	@Resource
	private ExpressInfoService expressInfoService;
	@Resource
	private DirectOrderService directOrderService;
	@Resource
	private OrderService orderService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4028 body = JSON.parseObject(bodyStr, Request4028.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		log.info("updateOrderAddress  	4028	接口请求消息体：" + reqHead.toString());
		log.info("updateOrderAddress  	4028	接口请求消息体：" + bodyStr);
		try {
			Request4028 body = JSON.parseObject(bodyStr, Request4028.class);
			// 地址ID是否为空 为空直接返回true 不为空 需要更新订单的addressID
			JrdsOrder jrdsOrder = directOrderService.selectOrderByOrderNo(body.getOrderNo());

			if (body.getType() == 1 && body.getAddressId() != null) {

				jrdsOrder.setAddressId(body.getAddressId());
				expressInfoService.updateJrdsOrderAddress(jrdsOrder);
			} else if(body.getType() == 1 && body.getAddressId() == null){
				expressInfoService.updateJrdsOrderAddress(jrdsOrder);
			}

			JrasOrder jrasOrder = orderService.selectOrderByOrderNo(body.getOrderNo());

			if (body.getType() == 0 && body.getAddressId() != null) {
				jrasOrder.setAddressId(body.getAddressId());
				expressInfoService.updateJrasOrderAddress(jrasOrder);
			} else if(body.getType() == 0 && body.getAddressId() == null){
				expressInfoService.updateJrasOrderAddress(jrasOrder);
			}
			
			ResponseBody res = new ResponseBody();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(res, respHead);
			
		} catch (Exception e) {
			log.error("updateOrderAddress  	4028	接口发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseBody res = new ResponseBody();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 999999);
			return this.packageMsgMap(res, respHead);
		}
	}

	private Map<String, Object> packageMsgMap(ResponseBody res, ResponseHeader respHead) {
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("body", res);
		responseMsg.put("head", respHead);
		return responseMsg;
	}

}
