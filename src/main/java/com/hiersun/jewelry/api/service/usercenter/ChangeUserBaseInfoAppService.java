package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4008;
import com.hiersun.jewelry.api.entity.response.RespUser;
import com.hiersun.jewelry.api.entity.response.Response4008;
import com.hiersun.jewelry.api.entity.vo.BankCardNum;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.user.domain.JrMemberInfoVo;
import com.hiersun.jewelry.api.user.domain.UserInfo;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("changeUserBaseInfoAppService")
public class ChangeUserBaseInfoAppService implements BaseService {

	private static Logger log = Logger.getLogger(ChangeUserBaseInfoAppService.class);

	@Resource
	private UserService userService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4008 body = JSON.parseObject(bodyStr, Request4008.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("changeUserBaseInfo	3007	请求信息：" + reqHead.toString());
		log.info("changeUserBaseInfo	3007 	请求信息：" + bodyStr);

		try {
			Request4008 body = JSON.parseObject(bodyStr, Request4008.class);

			JrMemberInfoVo jrMemberInfoVo = new JrMemberInfoVo();
			jrMemberInfoVo.setUserId(userId);
			
			if (!StringUtils.isEmpty(body.getNickName())) {
				jrMemberInfoVo.setNickName(body.getNickName());
			}
			if (!StringUtils.isEmpty(body.getSex())) {
				jrMemberInfoVo.setSex(body.getSex());
			}

			userService.updateUserInfo(jrMemberInfoVo);

			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(userId);

			UserInfo user = userService.getUserInfoByMobile(userInfo);
			Response4008 res = new Response4008();
			if (user == null) {
				ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 200102);
				return this.packageMsgMap(res, respHead);
			}

			RespUser result = new RespUser();
			result.setNickName(user.getNickName());
			result.setMobile(user.getUserMobile());
			if (StringUtils.isEmpty(user.getSex())) {
				result.setSex("男");
			} else {
				result.setSex(user.getSex());
			}

			BankCardNum bank = new BankCardNum();
			bank.setBankCardNum(user.getCardNo());
			bank.setBankName(user.getBankName());
			bank.setUserRealName(user.getRealName());
			result.setBankCardNum(bank);
			res.setUser(result);

			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(res, respHead);
		} catch (Exception e) {
			log.error("changeUserBaseInfo	3007	接口发生异常，异常信息：" + e.getMessage());
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			e.printStackTrace();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(Response4008 res, ResponseHeader respHead) {
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
