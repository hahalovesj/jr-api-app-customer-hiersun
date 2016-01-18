package com.hiersun.jewelry.api.service.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.CatchKey;
import com.hiersun.jewelry.api.dictionary.QualificationType;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.RequestLogin;
import com.hiersun.jewelry.api.entity.response.RespUser;
import com.hiersun.jewelry.api.entity.response.ResponseLogin;
import com.hiersun.jewelry.api.entity.vo.BankCardNum;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.user.domain.UserInfo;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.CommonUtils;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.RandomStringUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

/**
 * Created by lijunteng on 16/1/11.
 */
@Service("loginAppService")
public class LoginAppService implements BaseService {

	private static final Logger log = Logger.getLogger(LoginAppService.class);

	@Resource
	private UserService userService;
	@Resource
	private RedisBaseService redisBaseServiceImpl;

	@Override
	public boolean ifValidateLogin() {
		return false;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		RequestLogin body = JSON.parseObject(bodyStr, RequestLogin.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("login 1002 用户登录请求头信息: " + reqHead.toString());
		log.info("login 1002 用户登录请求消息体: " + bodyStr);
		try {
			RequestLogin body = JSON.parseObject(bodyStr, RequestLogin.class);

			UserInfo users = new UserInfo();
			users.setUserMobile(body.getMobile());
			// 数据库操作...
			UserInfo userInfo = userService.getUserInfoByMobile(users);

			if (userInfo == null) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100102);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}
			if (!userInfo.getPassword().toUpperCase().equals(body.getPassword().toUpperCase())) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100304);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}
			// 用户状态 1可用 0 不可用
			if (userInfo.getUserStatus() == 0) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100305);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}
			UserInfo info = new UserInfo();
			info.setUserMobile(body.getMobile());
			UserInfo resultUserInfo = userService.getUserInfoByMobile(info);

			// 登陆成功 存token
			String token = RandomStringUtil.randomString(16);
			redisBaseServiceImpl.set(CatchKey.APP_USERID_CACH_KEY_START + token, userInfo.getUserId().toString());
			// 配置返回信息
			ResponseLogin responseBody = new ResponseLogin();

			responseBody.setJumpTransaction(body.getJumpTransaction());
			responseBody.setMobile(userInfo.getUserMobile());
			responseBody.setToken(token);
			RespUser user = new RespUser();
//			 user.setMobile(userInfo.getUserMobile());
//			 user.setToken(token);
			if (resultUserInfo.getSex() == null) {
				user.setSex(QualificationType.SEX_MAP.get("0"));
			} else {
				user.setSex(resultUserInfo.getSex().equals("0") ? "女" : "男");
			}
			if (resultUserInfo.getNickName() != null) {
				user.setNickName(resultUserInfo.getNickName());
			} else {
				user.setNickName(CommonUtils.mobileForNickName(body.getMobile()));
			}
			if (resultUserInfo.getBirthday() != null) {
				user.setBirthday(DateUtil.dateTypeToString(resultUserInfo.getBirthday(), "yyyy-MM-dd HH:mm:ss"));
			}
			BankCardNum bankCarNum = new BankCardNum();
			if (resultUserInfo.getCardNo() != null) {
				bankCarNum.setBankCardNum(resultUserInfo.getCardNo());
			}
			if (resultUserInfo.getBankName() != null) {
				bankCarNum.setBankName(resultUserInfo.getBankName());
			}
			if (resultUserInfo.getRealName() != null) {
				bankCarNum.setUserRealName(resultUserInfo.getRealName());
			}
			user.setBankCardNum(bankCarNum);
			responseBody.setUser(user);

			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			log.error("login 1002 用户登录接口，发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();

			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(ResponseLogin res, ResponseHeader respHead) {
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
