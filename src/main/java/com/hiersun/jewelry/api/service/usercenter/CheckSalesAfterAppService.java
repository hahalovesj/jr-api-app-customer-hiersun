package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.direct.domain.JrAfterSalesAuditVo;
import com.hiersun.jewelry.api.direct.domain.JrdsOrderVo;
import com.hiersun.jewelry.api.direct.service.DirectOrderService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4027;
import com.hiersun.jewelry.api.entity.response.Response4027;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("checkSalesAfterAppService")
public class CheckSalesAfterAppService implements BaseService {

	private static Logger log = Logger.getLogger(CheckSalesAfterAppService.class);
	
	@Resource
	DirectOrderService directOrderService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4027 body = JSON.parseObject(bodyStr, Request4027.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		
		log.info("checkSalesAfter	4027	接口请求消息体：" + reqHead.toString());
		log.info("checkSalesAfter	4027	接口请求消息体：" + bodyStr);
		
		try {
			Request4027 body = JSON.parseObject(bodyStr, Request4027.class);
			String orderNo = body.getOrderNo();
			JrAfterSalesAuditVo jrSalesVo = directOrderService.selectAfterByOrderId(orderNo);
			JrdsOrderVo jrdsOrderVo = directOrderService.selectDirectOrder(orderNo);

			Response4027 res = new Response4027();
			res.setJrAfterSalesAuditVo(jrSalesVo);
			res.setJrdsOrderVo(jrdsOrderVo);

			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(res, respHead);
		} catch (Exception e) {
			log.error("checkSalesAfter	4027	接口发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			ResponseBody res = new ResponseBody();
			return this.packageMsgMap(res, respHead);
		}
	}

	private Map<String, Object> packageMsgMap(Response4027 res, ResponseHeader respHead) {
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
