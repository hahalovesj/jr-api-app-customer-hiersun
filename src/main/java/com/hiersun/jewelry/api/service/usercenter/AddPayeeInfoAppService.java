package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.QualificationType;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4007;
import com.hiersun.jewelry.api.entity.response.RespUser;
import com.hiersun.jewelry.api.entity.response.Response4007;
import com.hiersun.jewelry.api.entity.vo.BankCardNum;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.user.domain.MemberBankVo;
import com.hiersun.jewelry.api.user.domain.UserInfo;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("addPayeeInfoAppService")
public class AddPayeeInfoAppService implements BaseService {

	private static Logger log = Logger.getLogger(AddPayeeInfoAppService.class);

	@Resource
	private UserService userService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4007 body = JSON.parseObject(bodyStr, Request4007.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("addPayeeInfo	3006	接口请求消息体：" + reqHead.toString());
		log.info("addPayeeInfo	3006	接口请求消息体：" + bodyStr);

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
			// user.setRealName(body.getUserRealName());
			// user.setCardNo(body.getBankCardNum());
			// user.setBankName(body.getBankName());

			RespUser respUser = new RespUser();
			BankCardNum bank = new BankCardNum();
			bank.setBankCardNum(user.getCardNo());
			bank.setBankName(user.getBankName());
			bank.setUserRealName(user.getRealName());
			respUser.setBankCardNum(bank);
			respUser.setNickName(user.getNickName());

			if (StringUtils.isEmpty(user.getSex())) {
				respUser.setSex(QualificationType.SEX_MAP.get("0"));
			} else {
				respUser.setSex(QualificationType.SEX_MAP.get(user.getSex()));
			}
			//respUser.setBirthday(DateUtil.dateTypeToString(user.getBirthday(), "yyyy-MM-dd"));

			respUser.setMobile(user.getUserMobile());

			// respUser.setBankCardNum(bankCardNum);

			Response4007 res = new Response4007();

			res.setUser(respUser);

			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);

			return this.packageMsgMap(respUser, respHead);
		} catch (Exception e) {
			log.error("addPayeeInfo	3006	 接口发生异常，异常信息：" + e.getMessage());
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			e.printStackTrace();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(RespUser res, ResponseHeader respHead) {
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
