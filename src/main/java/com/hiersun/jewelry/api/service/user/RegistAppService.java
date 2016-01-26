package com.hiersun.jewelry.api.service.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.CatchKey;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.dictionary.QualificationType;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.RequestRegist;
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

/**
 * Created by lijunteng on 16/1/11.
 */
@Service("registAppService")
public class RegistAppService implements BaseService {

	private static final Logger log = Logger.getLogger(RegistAppService.class);

	@Resource
	private RedisBaseService redisBaseServiceImpl;
	@Resource
	private UserService userService;

	@Override
	public boolean ifValidateLogin() {
		return false;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {

		RequestRegist body = JSON.parseObject(bodyStr, RequestRegist.class);
		return body.volidateValue();
		// return 0;
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("regist 1002 用户注册请求头信息: " + reqHead.toString());
		log.info("regist 1002 用户注册请求消息体: " + bodyStr);

		try {
			RequestRegist body = JSON.parseObject(bodyStr, RequestRegist.class);

			String veriCode = body.getVeriCode();
			String mobile = body.getMobile();
			String acctionType = body.getsMsgAcctionType();
			// 对比验证码
			String cacheNumber = redisBaseServiceImpl.get(CatchKey.APP_MSG_KEY + acctionType + mobile);
			if (cacheNumber == null || cacheNumber.trim().length() < 1) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100201);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}
			if (!veriCode.equals(cacheNumber)) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100201);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}
			// ... 操作数据库
			boolean isExis = userService.isExistMobile(mobile);
			// 手机号存在返回已存在
			if (isExis) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100101);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}

			User user = new User();
			user.setUserMobile(mobile);
			user.setUserPassword(body.getPassword());
			user.setImie(reqHead.getImei());
			user.setPushId(body.getPushMsgID());

			Map<String, String> icoMap = CommonUtils.getIco(0);

			// 默认女头像和女性别
			user.setBigIcon(icoMap.get("big"));
			user.setSmallIcon(icoMap.get("small"));
			user.setSex(0);

			user.setNickName(CommonUtils.mobileForNickName(mobile));
			userId = userService.regist(user);
			String token = RandomStringUtil.randomString(16);

			// 注册成功，删除缓存的验证码
			redisBaseServiceImpl.del(CatchKey.APP_MSG_KEY + acctionType + mobile);
			// 存token
			redisBaseServiceImpl.set(CatchKey.APP_USERID_CACH_KEY_START + token, userId.toString());

			ResponseLogin responseBody = new ResponseLogin();

			UserInfo info = new UserInfo();
			info.setUserMobile(body.getMobile());
			UserInfo resultUserInfo = userService.getUserInfoByMobile(info);

			responseBody.setJumpTransaction(body.getJumpTransaction());
			RespUser respUser = new RespUser();
			respUser.setMobile(body.getMobile());
			respUser.setToken(token);
			respUser.setBigIcon(Commons.PIC_DOMAIN + resultUserInfo.getBigIcon());
			respUser.setSmallIcon(Commons.PIC_DOMAIN + resultUserInfo.getSmallIcon());
			if (resultUserInfo.getSex() == null) {
				respUser.setSex(QualificationType.SEX_MAP.get("0"));
			} else {
				respUser.setSex(resultUserInfo.getSex().equals("0") ? "女" : "男");
			}
			if (resultUserInfo.getNickName() != null) {
				respUser.setNickName(resultUserInfo.getNickName());
			} else {
				respUser.setNickName(CommonUtils.mobileForNickName(body.getMobile()));
			}
			if (resultUserInfo.getBirthday() != null) {
				respUser.setBirthday(DateUtil.dateTypeToString(resultUserInfo.getBirthday(), "yyyy-MM-dd HH:mm:ss"));
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
			respUser.setBankCardNum(bankCarNum);
			responseBody.setUser(respUser);

			// // 配置返回信息
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			log.error("regist 1002 用户注册接口，发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();

			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(RequestRegist res, ResponseHeader respHead) {
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
