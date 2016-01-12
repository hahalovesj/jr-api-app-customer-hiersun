package com.hiersun.jewelry.api.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request2010;
import com.hiersun.jewelry.api.entity.response.Response2010;
import com.hiersun.jewelry.api.homescreen.pojo.JrdsSearchKeyword;
import com.hiersun.jewelry.api.homescreen.service.KeyWordService;
import com.hiersun.jewelry.api.service.BaseService;

@Service("getHotWordAppService")
public class GetHotWordAppService implements BaseService {

	@Resource
	KeyWordService keyWordService;

	@Override
	public boolean ifValidateLogin() {
		return false;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request2010 body = JSON.parseObject(bodyStr, Request2010.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		// 返回的header
		ResponseHeader respHead = new ResponseHeader(0);
		respHead.setMessageID(reqHead.getMessageID());
		respHead.setTimeStamp(new Date().getTime());
		respHead.setTransactionType(reqHead.getTransactionType());
		// 返回的body
		Response2010 responseBody = new Response2010();
		List<JrdsSearchKeyword> list = keyWordService.getListKeyWord();

		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			arrayList.add(list.get(i).getKeyWord());
		}
		responseBody.setHotWords(arrayList);

		responseMsg.put("body", responseBody);
		responseMsg.put("head", respHead);

		return this.packageMsgMap(responseBody, respHead);

	}

	private Map<String, Object> packageMsgMap(Response2010 res, ResponseHeader respHead) {
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
