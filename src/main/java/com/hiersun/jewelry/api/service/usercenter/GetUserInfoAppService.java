package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.dictionary.QualificationType;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.response.RespUser;
import com.hiersun.jewelry.api.entity.response.Response4003;
import com.hiersun.jewelry.api.entity.vo.BankCardNum;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.user.domain.UserInfo;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("getUserInfoAppService")
public class GetUserInfoAppService implements BaseService{
	
	private static final Logger log = Logger.getLogger(GetUserInfoAppService.class);
	
	@Resource
	private UserService userService;
	
	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		return 0;
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		
		log.info("getUserInfoAppService	4003	请求信息：" + reqHead.toString());
		log.info("getUserInfoAppService	4003 	请求信息：" + bodyStr);
		
		try{
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(userId);

			UserInfo user = userService.getUserInfoByMobile(userInfo);
			Response4003 res = new Response4003();
			if (user == null) {
				ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 200102);
				return this.packageMsgMap(res, respHead);
			}

			RespUser result = new RespUser();
			result.setNickName(user.getNickName());
			result.setMobile(user.getUserMobile());
			if (StringUtils.isEmpty(user.getSex())) {
				result.setSex(QualificationType.SEX_MAP.get("0"));
			} else {
				result.setSex(QualificationType.SEX_MAP.get(user.getSex()));
			}
			result.setToken(reqHead.getToken());

			BankCardNum bank = new BankCardNum();
			bank.setBankCardNum(user.getCardNo());
			bank.setBankName(user.getBankName());
			bank.setUserRealName(user.getRealName());
			result.setBankCardNum(bank);
			res.setUser(result);

			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(res, respHead);
			
		} catch (Exception e) {
			log.error("getUserInfoAppService	4003	接口发生异常，异常信息：" + e.getMessage());
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			e.printStackTrace();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}
	private Map<String, Object> packageMsgMap(Response4003 res, ResponseHeader respHead) {
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
