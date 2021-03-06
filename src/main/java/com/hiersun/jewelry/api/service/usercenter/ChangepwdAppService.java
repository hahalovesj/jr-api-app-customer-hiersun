package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.CatchKey;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.dictionary.QualificationType;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4009;
import com.hiersun.jewelry.api.entity.response.RespUser;
import com.hiersun.jewelry.api.entity.response.ResponseLogin;
import com.hiersun.jewelry.api.entity.vo.BankCardNum;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.user.domain.User;
import com.hiersun.jewelry.api.user.domain.UserInfo;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.CommonUtils;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.RandomStringUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("changepwdAppService")
public class ChangepwdAppService implements BaseService {

	private static Logger log = Logger.getLogger(ChangepwdAppService.class);

	@Resource
	private UserService userService;

	@Resource
	private RedisBaseService redisBaseServiceImpl;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4009 body = JSON.parseObject(bodyStr, Request4009.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("changepwd	4009	请求信息：" + reqHead.toString());
		log.info("changepwd	4009 	请求信息：" + bodyStr);

		try {
			Request4009 body = JSON.parseObject(bodyStr, Request4009.class);

			String acctionType = body.getsMsgAcctionType();
			String veriCode = body.getVeriCode();

			User user = new User();
			user.setId(userId);
			// 根据userid 查询用户
			User restUser = userService.getUserByMobile(user);
			String mobile = restUser.getUserMobile();

			String cacheveriCode = redisBaseServiceImpl.get(CatchKey.APP_MSG_KEY + acctionType + mobile);
			if (StringUtils.isEmpty(cacheveriCode) || !cacheveriCode.equals(veriCode)) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100201);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}
			restUser.setUserPassword(body.getPassword());
			userService.modifyPassword(restUser);
			// 删除旧的token
			String token = reqHead.getToken();
			redisBaseServiceImpl.del(CatchKey.APP_USERID_CACH_KEY_START + token);
			UserInfo info = new UserInfo();
			info.setUserMobile(restUser.getUserMobile());
			UserInfo resultUserInfo = userService.getUserInfoByMobile(info);
			// 登陆成功 存token
			String newToken = RandomStringUtil.randomString(16);
			redisBaseServiceImpl.set(CatchKey.APP_USERID_CACH_KEY_START + newToken, resultUserInfo.getUserId()
					.toString());
			// 删除缓存的验证码
			redisBaseServiceImpl.del(CatchKey.APP_MSG_KEY + acctionType + mobile);
			// 配置返回信息
			ResponseLogin responseBody = new ResponseLogin();

			// responseBody.setMobile(resultUserInfo.getUserMobile());
			// responseBody.setToken(newToken);
			RespUser resuUser = new RespUser();
			resuUser.setToken(newToken);
			resuUser.setMobile(resultUserInfo.getUserMobile());
			resuUser.setBigIcon(Commons.PIC_DOMAIN + resultUserInfo.getBigIcon());
			resuUser.setSmallIcon(Commons.PIC_DOMAIN + resultUserInfo.getSmallIcon());

			if (resultUserInfo.getSex() == null) {
				resuUser.setSex(QualificationType.SEX_MAP.get("0"));
			} else {
				resuUser.setSex(QualificationType.SEX_MAP.get(resultUserInfo.getSex()));
			}
			if (resultUserInfo.getNickName() != null) {
				resuUser.setNickName(resultUserInfo.getNickName());
			} else {
				resuUser.setNickName(CommonUtils.mobileForNickName(resultUserInfo.getUserMobile()));
			}
			if (resultUserInfo.getBirthday() != null) {
				resuUser.setBirthday(DateUtil.dateTypeToString(resultUserInfo.getBirthday(), "yyyy-MM-dd HH:mm:ss"));
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
			resuUser.setBankCardNum(bankCarNum);
			responseBody.setUser(resuUser);

			// 配置返回信息
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			log.error("changepwd	4009	接口发生异常，异常信息：" + e.getMessage());
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			e.printStackTrace();
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
