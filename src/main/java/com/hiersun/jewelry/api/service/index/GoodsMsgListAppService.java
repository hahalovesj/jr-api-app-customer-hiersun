package com.hiersun.jewelry.api.service.index;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.direct.domain.DirectGoodMessageVo;
import com.hiersun.jewelry.api.direct.service.DirectGoodMessageService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request2005;
import com.hiersun.jewelry.api.entity.response.Response2005;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;
@Service("goodsMsgListAppService")
public class GoodsMsgListAppService implements BaseService{
	
	@Resource
	DirectGoodMessageService directGoodMessageService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request2005 body = JSON.parseObject(bodyStr, Request2005.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		try {
			Request2005 body = JSON.parseObject(bodyStr, Request2005.class);
			// 请求的信息
			// body.getGoodsID();
			// 。。。(根据传来的值做数据查询)
			long goodsId = body.getGoodsID();
			List<DirectGoodMessageVo> msgList = directGoodMessageService.getDirectGoodMessageList(goodsId);
			// 返回的header
			ResponseHeader respHead = new ResponseHeader(0);
			respHead.setMessageID(reqHead.getMessageID());
			respHead.setTimeStamp(new Date().getTime());
			respHead.setTransactionType(reqHead.getTransactionType());
			// 返回的body
			Response2005 responseBody = new Response2005();
			responseBody.setGoodsID(goodsId);
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

			for (DirectGoodMessageVo dvo : msgList) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (dvo.getInitiator() != null) {
					map.put("msgFromUserName", dvo.getInitiator());
				}
				if (dvo.getReplyor() != null) {
					map.put("msgToUserName", dvo.getReplyor());
				}
				if (dvo.getInitiatorId() != null) {
					map.put("msgFromUserID", dvo.getInitiatorId());
				}
				if (dvo.getReplyorId() != null) {
					map.put("msgToUserID", dvo.getReplyorId());
				}
				if (dvo.getMessage() != null) {
					map.put("msgContent", dvo.getMessage());
				}
				if (dvo.getCreated() != null) {
					map.put("msgTime", DateUtil.dateToStr(dvo.getCreated(), "yyyy-MM-dd HH:mm:ss"));
				}
				map.put("icon", Commons.HEAD_IOC);
				resultList.add(map);
			}

			responseBody.setMsgList(resultList);
			return this.packageMsgMap(responseBody, respHead);
			
		} catch (Exception e) {
			e.printStackTrace();
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}
	
	private Map<String,Object> packageMsgMap(Response2005 res,ResponseHeader respHead){
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("body", res);
		responseMsg.put("head", respHead);
		return responseMsg;
	}
	
	private Map<String,Object> packageMsgMap(ResponseBody res,ResponseHeader respHead){
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("body", res);
		responseMsg.put("head", respHead);
		return responseMsg;
	}

}
