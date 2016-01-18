package com.hiersun.jewelry.api.service.index;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.direct.domain.DirectGoodMessageVo;
import com.hiersun.jewelry.api.direct.service.DirectGoodMessageService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request2006;
import com.hiersun.jewelry.api.entity.response.Response2006;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("setGoodsMsgAppService")
public class SetGoodsMsgAppService implements BaseService {

	private static final Logger log = Logger.getLogger(SetGoodsMsgAppService.class);

	@Resource
	DirectGoodMessageService directGoodMessageService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request2006 body = JSON.parseObject(bodyStr, Request2006.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		log.info("setGoodsMsg	2006	接口 请求消息体:	" + reqHead.toString());
		log.info("setGoodsMsg	2006	接口 请求消息体:	" + bodyStr);
		try {
			Request2006 body = JSON.parseObject(bodyStr, Request2006.class);
			long goodsId = body.getGoodsID();
			long uId = body.getGoodsUserID();

			DirectGoodMessageVo vo = new DirectGoodMessageVo();
			vo.setGoodId(body.getGoodsID());
			vo.setMessage(body.getMsgContent());
			vo.setInitiator(body.getMsgFromUserName());
			vo.setInitiatorId(userId);
			vo.setReplyor(body.getMsgToUserName());
			vo.setReplyorId(body.getMsgToUserID());
			vo.setSellerMemberId(body.getGoodsUserID());
			vo.setCreated(new Date());
			directGoodMessageService.putDirectGoodMessage(vo);

			List<DirectGoodMessageVo> msgList = directGoodMessageService.getDirectGoodMessageList(goodsId);
			// 返回的header
			ResponseHeader respHead = new ResponseHeader(0);
			respHead.setMessageID(reqHead.getMessageID());
			respHead.setTimeStamp(new Date().getTime());
			respHead.setTransactionType(reqHead.getTransactionType());
			// 返回的body
			Response2006 responseBody = new Response2006();
			// 返回的body
			responseBody.setGoodsID(goodsId);
			responseBody.setGoodsUserID(uId);
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
				map.put("bigIcon",Commons.PIC_DOMAIN +  dvo.getBigIcon());
				map.put("smallIcon", Commons.PIC_DOMAIN + dvo.getSmallIcon());

				resultList.add(map);
			}
			responseBody.setMsgList(resultList);

			return this.packageMsgMap(responseBody, respHead);

		} catch (Exception e) {
			log.error("setGoodsMsg 2006接口，发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(Response2006 res, ResponseHeader respHead) {
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
