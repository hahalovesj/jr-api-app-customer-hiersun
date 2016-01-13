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
import com.hiersun.jewelry.api.entity.request.Request4002;
import com.hiersun.jewelry.api.entity.response.Response4002;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.user.domain.JrMemberFeedbackVo;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("feedbackAppService")
public class FeedbackAppService implements BaseService {

	private static Logger log = Logger.getLogger(FeedbackAppService.class);

	@Resource
	private UserService userService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4002 body = JSON.parseObject(bodyStr, Request4002.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("feedback 	3002	接口请求消息体：" + reqHead.toString());
		log.info("feedback 	3002	接口请求消息体：" + bodyStr);

		try {
			Request4002 body = JSON.parseObject(bodyStr, Request4002.class);

			JrMemberFeedbackVo jmfv = new JrMemberFeedbackVo();
			jmfv.setContent(body.getFeedbackContent());
			jmfv.setMemberId(userId);
			String terminal = reqHead.getTerminal().toUpperCase().equals("IOS") ? "1" : "2";
			jmfv.setSource(Byte.parseByte(terminal));
			userService.addFeedBack(jmfv);

			Response4002 res = new Response4002();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(res, respHead);
		} catch (Exception e) {
			log.error("feedback 	3002	接口发生异常，异常信息：" + e.getMessage());
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);

		}
	}

	private Map<String, Object> packageMsgMap(Response4002 res, ResponseHeader respHead) {
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
