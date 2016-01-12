package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.Map;

import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.response.Response4012;
import com.hiersun.jewelry.api.entity.vo.Company;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;
import com.hiersun.jewelry.api.util.SubsystemIp;

public class ReceivInfoAppService implements BaseService {

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
		try {
			Map<String, Object> responseMsg = new HashMap<String, Object>();
			// 返回的body
			Response4012 responseBody = new Response4012();

			responseBody.setPerson(SubsystemIp.getInstance().getValue("recipient"));
			responseBody.setAddress(SubsystemIp.getInstance().getValue("ECaddress"));
			responseBody.setTel(SubsystemIp.getInstance().getValue("tel"));

			Company company = new Company();
			company.setCompanyName("顺丰快递公司");
			company.setCompanyCode("1");

			responseBody.setCompany(company);

			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			responseMsg.put("body", responseBody);
			responseMsg.put("head", respHead);
			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseBody respBody = new ResponseBody();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
			return this.packageMsgMap(respBody, respHead);

		}
	}

	private Map<String, Object> packageMsgMap(Response4012 res, ResponseHeader respHead) {
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
