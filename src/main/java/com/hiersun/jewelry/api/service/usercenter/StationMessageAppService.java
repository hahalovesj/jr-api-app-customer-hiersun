package com.hiersun.jewelry.api.service.usercenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4001;
import com.hiersun.jewelry.api.entity.response.Response4001;
import com.hiersun.jewelry.api.entity.vo.Cmd;
import com.hiersun.jewelry.api.entity.vo.StationMessage;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.user.domain.JrMemberCentreMessageVo;
import com.hiersun.jewelry.api.user.pojo.JrMemberCentreMessage;
import com.hiersun.jewelry.api.user.service.MemberCentreMessageService;
import com.hiersun.jewelry.api.util.DateUtil;

@Service("stationMessageAppService")
public class StationMessageAppService implements BaseService{

	@Resource
	private MemberCentreMessageService memberCentreMessageService;
	
	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr,Long userId) {
		Request4001 body = JSON.parseObject(bodyStr, Request4001.class);
		return body.volidateValue();
	}

	
	@Override
	public Map<String,Object> doController(RequestHeader reqHead,String bodyStr,Long userId)  throws Exception{
		Request4001 body = JSON.parseObject(bodyStr, Request4001.class);
		// 设置每页数量
		int PageSize = 20;
		Integer pageNo = body.getPageNo();
		// 逻辑处理
		JrMemberCentreMessageVo jmcmvo = new JrMemberCentreMessageVo();
		jmcmvo.setStart(pageNo * PageSize);
		jmcmvo.setEnd(PageSize);
		jmcmvo.setMemberId(body.getMemberId());
		List<JrMemberCentreMessage> messageList = memberCentreMessageService.getMessageListByMember(jmcmvo);

		// 总数
		int countNumber = memberCentreMessageService.getCountMessageCount(jmcmvo);
		int nowNumber = (pageNo + 1) * PageSize;

		// 返回的header
		ResponseHeader respHead = new ResponseHeader(0);
		respHead.setMessageID(reqHead.getMessageID());
		respHead.setTimeStamp(new Date().getTime());
		respHead.setTransactionType(reqHead.getTransactionType());
		// 返回的body
		Response4001 responseBody = new Response4001();
		List<StationMessage> stationMessageList = new ArrayList<StationMessage>();

		for (int i = 0; i < messageList.size(); i++) {
			System.out.println("对象：" + messageList.get(i));
			StationMessage sm = new StationMessage();
			sm.setTitle(messageList.get(i).getTitile());
			sm.setTime(DateUtil.dateToStr(messageList.get(i).getCreated(), "yyyy-MM-dd HH:mm:ss"));
			sm.setIsRead(messageList.get(i).getIsRead());
			sm.setContent(messageList.get(i).getContent());
			Cmd cmd = new Cmd();
			cmd.setTransactionType(messageList.get(i).getTransactionType());
			cmd.setTransactionID("" + messageList.get(i).getOrderId());
			sm.setCmd(cmd);

			stationMessageList.add(sm);
		}

		responseBody.setStationMessageList(stationMessageList);
		responseBody.setPageNO(pageNo + 1);
		// 是否有下一页
		if (countNumber > nowNumber) {
			responseBody.setIsEnd(false);
		} else {
			responseBody.setIsEnd(true);
		}

		return this.packageMsgMap(responseBody, respHead);
	}
	
	private Map<String,Object> packageMsgMap(Response4001 res,ResponseHeader respHead){
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("body", res);
		responseMsg.put("head", respHead);
		return responseMsg;
	}
}
