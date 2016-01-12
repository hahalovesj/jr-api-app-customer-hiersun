package com.hiersun.jewelry.api.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("getHotWordAppService")
public class GetHotWordAppService implements BaseService {
	
	private static Logger log = Logger.getLogger(GetHotWordAppService.class);

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
		log.info("正在处理搜索热词接口getHotWord(2010),Header=" + reqHead.toString());
		log.info("正在处理搜索热词接口getHotWord(2010),Body=" + bodyStr);
		try {
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
		} catch (Exception e) {
			for (int i = 0; i < e.getStackTrace().length; i++) {
				log.error("搜索热词失败，异常类：\"" + e.getStackTrace()[i].getClassName() + "\"; 函数方法：\""
						+ e.getStackTrace()[i].getMethodName() + "()\"; Error：" + e.getMessage() + "; 错误行："
						+ e.getStackTrace()[i].getLineNumber());
			}
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
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
