package com.hiersun.jewelry.api.service.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.CatchKey;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.RequestSmsg;
import com.hiersun.jewelry.api.entity.response.ResponseSmsg;
import com.hiersun.jewelry.api.ott.service.SMSMessageService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.DoSmsUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;
import com.hiersun.jewelry.api.util.Trunc;

@Service("smsgAppService")
public class SmsgAppService implements BaseService{
	@Resource
	private SMSMessageService sMSMessageService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private RedisBaseService redisBaseServiceImpl;
	
	@Override
	public boolean ifValidateLogin() {
		return false;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr,Long userId) {
		RequestSmsg body = JSON.parseObject(bodyStr, RequestSmsg.class);
		return body.volidateValue();
	}

	
	@Override
	public Map<String,Object> doController(RequestHeader reqHead,String bodyStr,Long userId)  throws Exception{
		try {
			RequestSmsg body = JSON.parseObject(bodyStr, RequestSmsg.class);
			String mobile = body.getMobile();
			String acctionType = body.getAcctionType();
			// 生成验证码
			Long veriNumber = Trunc.getRandomForRange(100000, 999999);
			// ... 操作数据库
			boolean isExis = userService.isExistMobile(mobile);

			if (acctionType.equals("reg")) {
				// 手机号存在返回已存在
				if (isExis) {
					ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100101);
					ResponseBody responseBody = new ResponseBody();
					return this.packageMsgMap(responseBody, respHeader);
				}
			}
			if (acctionType.equals("pwd")) {
				if (!isExis) {
					ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 100102);
					ResponseBody responseBody = new ResponseBody();
					return this.packageMsgMap(responseBody, respHeader);
				}
			}

			// 放入缓存中
			redisBaseServiceImpl.set(CatchKey.APP_MSG_KEY + acctionType + mobile, 1 * 60 * 60 * 24 * 7, veriNumber.toString());
			// 发送短信
			//DoSmsUtil.doSms(veriNumber, mobile, acctionType);
			String content = "您好，您的6位验证码为：" + veriNumber + "【二手珠宝】";
			String appName = "二手珠宝";
//			String content = "内容";
			String scenario = acctionType;
			sMSMessageService.sendMessage(content, appName, mobile, scenario);
			
			// 配置返回信息
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);

			ResponseSmsg responseBody = new ResponseSmsg();
			responseBody.setMobile(body.getMobile());

			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			e.printStackTrace();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}
	
	private Map<String,Object> packageMsgMap(ResponseSmsg res,ResponseHeader respHead){
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
