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
import com.hiersun.jewelry.api.entity.request.Request4009;
import com.hiersun.jewelry.api.entity.response.ResponseResetpwd;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.service.utils.UserUtil;
import com.hiersun.jewelry.api.user.domain.User;
import com.hiersun.jewelry.api.user.service.UserService;
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
			// TODO 缓存KEY统一管理 需要抽离
			String cacheveriCode = redisBaseServiceImpl.get("api" + acctionType + mobile);

			// 如果为空，或者和缓存中的不等
			if (StringUtils.isEmpty(cacheveriCode) || !cacheveriCode.equals(veriCode)) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100201);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}

			restUser.setUserPassword(body.getPassword());
			userService.modifyPassword(restUser);
			redisBaseServiceImpl.del(UserUtil.APP_USERID_CACH_KEY_START + reqHead.getToken());
			// 配置返回信息
			ResponseResetpwd responseBody = new ResponseResetpwd();
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

	private Map<String, Object> packageMsgMap(ResponseResetpwd res, ResponseHeader respHead) {
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
