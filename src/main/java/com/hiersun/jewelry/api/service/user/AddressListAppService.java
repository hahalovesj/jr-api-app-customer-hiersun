package com.hiersun.jewelry.api.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.response.Response2011;
import com.hiersun.jewelry.api.homescreen.service.KeyWordService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.user.domain.AddressVo;
import com.hiersun.jewelry.api.user.service.UserService;

@Service("addressListAppService")
public class AddressListAppService implements BaseService {

	@Resource
	KeyWordService keyWordService;
	
	@Resource
	UserService userService;
	
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
		// 根据userid查询收货地址列表
		AddressVo addrVo = new AddressVo();
		addrVo.setUserId(userId);

		List<AddressVo> addrList = userService.getListAddressVo(addrVo);
		List<Object> resultList = new ArrayList<Object>();
		for (int i = 0; i < addrList.size(); i++) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			if (addrList.get(i).getIsDefault() != null) {
				boolean isDef = addrList.get(i).getIsDefault().equals("1") ? true : false;
				map.put("isDefault", isDef);
			}
			if (addrList.get(i).getId() != null) {
				map.put("addressID", addrList.get(i).getId());
			}
			if (addrList.get(i).getReceiver() != null) {
				map.put("receiver", addrList.get(i).getReceiver());
			}
			if (addrList.get(i).getReceiverMobile() != null) {
				map.put("receiverMobile", addrList.get(i).getReceiverMobile());
			}
			if (addrList.get(i).getDetailedAddress() != null) {
				map.put("detailedAddress", addrList.get(i).getDetailedAddress());
			}
			if (addrList.get(i).getArea() != null) {
				map.put("area", addrList.get(i).getArea());
			}
			resultList.add(map);
		}

		// 返回的header
		ResponseHeader respHead = new ResponseHeader(0);
		respHead.setMessageID(reqHead.getMessageID());
		respHead.setTimeStamp(new Date().getTime());
		respHead.setTransactionType(reqHead.getTransactionType());
		// 返回的body
		Response2011 responseBody = new Response2011();
		responseBody.setAddressList(resultList);

		return this.packageMsgMap(responseBody, respHead);

	}

	private Map<String, Object> packageMsgMap(Response2011 res, ResponseHeader respHead) {
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
