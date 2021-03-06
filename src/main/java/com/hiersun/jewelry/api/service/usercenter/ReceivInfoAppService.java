package com.hiersun.jewelry.api.service.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.response.Response4012;
import com.hiersun.jewelry.api.entity.vo.Company;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;
import com.hiersun.jewelry.api.util.SubsystemIp;

@Service("receivInfoAppService")
public class ReceivInfoAppService implements BaseService {

	private static Logger log = Logger.getLogger(ReceivInfoAppService.class);

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

		log.info("receivInfo 	4012	接口请求消息体：" + reqHead.toString());
		log.info("receivInfo 	4012	接口请求消息体：" + bodyStr);

		try {
			Map<String, Object> responseMsg = new HashMap<String, Object>();
			// 返回的body
			Response4012 responseBody = new Response4012();

			responseBody.setPerson(SubsystemIp.getInstance().getValue("recipient"));
			responseBody.setAddress(SubsystemIp.getInstance().getValue("ECaddress"));
			responseBody.setTel(SubsystemIp.getInstance().getValue("tel"));
			List<Company> companyList = new ArrayList<Company>();
			Company company = new Company();
			company.setCompanyName("顺丰快递公司");
			company.setCompanyCode("1");
			//companyList.add(company);/
			responseBody.setCompany(company);
			//responseBody.setCompanyList(companyList);
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			responseMsg.put("body", responseBody);
			responseMsg.put("head", respHead);
			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			log.error("receivInfo 	4012	接口发生异常，异常信息：" + e.getMessage());
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
