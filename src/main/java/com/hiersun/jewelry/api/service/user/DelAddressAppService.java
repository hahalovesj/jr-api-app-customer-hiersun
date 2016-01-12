package com.hiersun.jewelry.api.service.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request2012;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.user.domain.AddressVo;
import com.hiersun.jewelry.api.user.service.UserService;

@Service("delAddressAppService")
public class DelAddressAppService implements BaseService {

	@Resource
	UserService userService;
	
	@Override
	public boolean ifValidateLogin() {
		return false;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request2012 body = JSON.parseObject(bodyStr, Request2012.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		// 删除收货地址（库操作）
		Request2012 body = JSON.parseObject(bodyStr, Request2012.class);
		long addrId = body.getAddressID();
		AddressVo vo = new AddressVo();
		vo.setId(addrId);

		boolean isDele = userService.deletedAddress(vo);
		if (!isDele) {
			ResponseHeader respHead = new ResponseHeader(9999);
			ResponseBody responseBody = new ResponseBody();
			this.packageMsgMap(responseBody, respHead);
		}
		// 返回的header
		ResponseHeader respHead = new ResponseHeader(0);
		ResponseBody responseBody = new ResponseBody();

		return this.packageMsgMap(responseBody, respHead);

	}

	private Map<String, Object> packageMsgMap(ResponseBody res, ResponseHeader respHead) {
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("body", res);
		responseMsg.put("head", respHead);
		return responseMsg;
	}
}
