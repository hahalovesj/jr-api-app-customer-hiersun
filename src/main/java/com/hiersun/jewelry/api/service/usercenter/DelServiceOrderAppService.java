package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4017;
import com.hiersun.jewelry.api.orderservice.domain.OrderVo;
import com.hiersun.jewelry.api.orderservice.service.OrderService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("delServiceOrderAppService")
public class DelServiceOrderAppService implements BaseService {

	private static Logger log = Logger.getLogger(DelServiceOrderAppService.class);

	@Resource
	OrderService orderService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4017 body = JSON.parseObject(bodyStr, Request4017.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("delServiceOrder 	4015	接口请求消息体：" + reqHead.toString());
		log.info("delServiceOrder 	4015	接口请求消息体：" + bodyStr);

		try {
			Request4017 body = JSON.parseObject(bodyStr, Request4017.class);
			String orderNo = body.getOrderNO();

			orderService.delOrderByOrderNo(orderNo);
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);

			ResponseBody res = new ResponseBody();

			return this.packageMsgMap(res, respHead);
		} catch (Exception e) {
			log.error("delServiceOrder 	4015	接口发生异常，异常信息：" + e.getMessage());
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
