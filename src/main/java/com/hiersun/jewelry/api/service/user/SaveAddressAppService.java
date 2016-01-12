package com.hiersun.jewelry.api.service.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.direct.service.DirectOrderService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request2009;
import com.hiersun.jewelry.api.entity.response.Response2009;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.user.domain.AddressVo;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("saveAddressAppService")
public class SaveAddressAppService implements BaseService {
	
	private static Logger log = Logger.getLogger(SaveAddressAppService.class);

	@Resource
	RedisBaseService redisBaseServiceImpl;

	@Resource
	DirectOrderService directOrderService;
	
	@Resource
	UserService userService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request2009 body = JSON.parseObject(bodyStr, Request2009.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		log.info("正在处理添加/修改收货地址接口saveAddress(2009),Header=" + reqHead.toString());
		log.info("正在处理添加/修改收货地址接口saveAddress(2009),Body=" + bodyStr);
		try {
			Request2009 body = JSON.parseObject(bodyStr, Request2009.class);
			String type = body.getType();

			AddressVo addrVo = new AddressVo();
			addrVo.setArea(body.getArea());
			addrVo.setDetailedAddress(body.getDetailedAddress());

			addrVo.setIsDefault(body.getIsDefault() ? "1" : "0");
			addrVo.setReceiver(body.getReceiver());
			addrVo.setReceiverMobile(body.getReceiverMobile());
			addrVo.setUserId(userId);
			long id = 0;
			boolean isUp = false;
			if (type.equals("add")) {
				id = userService.saveAddressVo(addrVo);
			}
			if (type.equals("update")) {
				addrVo.setId(body.getAddressID());
				isUp = userService.updateAddressVo(addrVo);
			}
			// 返回的body
			Response2009 responseBody = new Response2009();
			ResponseHeader respHead = new ResponseHeader(0);
			if (id > 0 || isUp == true) {
				// 返回的header
				responseBody.setIsDefault(body.getIsDefault());
				responseBody.setArea(body.getArea());
				responseBody.setDetailedAddress(body.getDetailedAddress());
				responseBody.setReceiver(body.getReceiver());
				responseBody.setReceiverMobile(body.getReceiverMobile());
			} else {
				responseBody = null;
				respHead = new ResponseHeader(9999);
				log.error("添加/修改收货地址失败，新增地址id=" + id + ",更新地址状态isUp=" + isUp);
			}
			respHead.setMessageID(reqHead.getMessageID());
			respHead.setTimeStamp(new Date().getTime());
			respHead.setTransactionType(reqHead.getTransactionType());
			
			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			for (int i = 0; i < e.getStackTrace().length; i++) {
				log.error("添加/修改收货地址失败，异常类：\"" + e.getStackTrace()[i].getClassName() + "\"; 函数方法：\""
						+ e.getStackTrace()[i].getMethodName() + "()\"; Error：" + e.getMessage() + "; 错误行："
						+ e.getStackTrace()[i].getLineNumber());
			}
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(Response2009 res, ResponseHeader respHead) {
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
