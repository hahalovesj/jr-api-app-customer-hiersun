package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4007;
import com.hiersun.jewelry.api.entity.response.Response4007;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.user.domain.MemberBankVo;
import com.hiersun.jewelry.api.user.domain.UserInfo;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("addPayeeInfoAppService")
public class AddPayeeInfoAppService implements BaseService{

	@Resource
	private UserService userService;
	
	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr,Long userId) {
		Request4007 body = JSON.parseObject(bodyStr, Request4007.class);
		return body.volidateValue();
	}

	
	@Override
	public Map<String,Object> doController(RequestHeader reqHead,String bodyStr,Long userId)  throws Exception{
		try {
			Request4007 body = JSON.parseObject(bodyStr, Request4007.class);
			MemberBankVo memberBankVo = new MemberBankVo();
			memberBankVo.setBankName(body.getBankName());
			memberBankVo.setCardNo(body.getBankCardNum());
			memberBankVo.setRealName(body.getUserRealName());
			memberBankVo.setUserId(userId);

			userService.addMemberBank(memberBankVo);
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(userId);

			UserInfo user = userService.getUserInfoByMobile(userInfo);
			user.setRealName(body.getUserRealName());
			user.setCardNo(body.getBankCardNum());
			user.setBankName(body.getBankName());

			Response4007 res = new Response4007();
			res.setUserInfo(user);

			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			
			return this.packageMsgMap(res, respHead);
		} catch (Exception e) {
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			e.printStackTrace();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}
	
	private Map<String,Object> packageMsgMap(Response4007 res,ResponseHeader respHead){
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
