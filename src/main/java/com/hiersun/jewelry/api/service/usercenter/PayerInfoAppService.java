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
import com.hiersun.jewelry.api.entity.request.Request4006;
import com.hiersun.jewelry.api.entity.response.Response4006;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.ResponseUtil;
import com.hiersun.jewelry.api.util.bank.GetBankNameUtil;

@Service("payerInfoAppService")
public class PayerInfoAppService implements BaseService {

	private static Logger log = Logger.getLogger(PayerInfoAppService.class);

	@Resource
	private UserService userService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4006 body = JSON.parseObject(bodyStr, Request4006.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("payerInfo 	4006	接口请求消息体：" + reqHead.toString());
		log.info("payerInfo 	4006	接口请求消息体：" + bodyStr);

		try {

			Request4006 body = JSON.parseObject(bodyStr, Request4006.class);
			String cardNum = body.getBankCardNum();
			String bankName = GetBankNameUtil.getBankName(cardNum);

			// 配置返回信息
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);

			Response4006 responseBody = new Response4006();
			responseBody.setBankName(bankName);
			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			log.error("payerInfo 	4006	接口发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(Response4006 res, ResponseHeader respHead) {
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
