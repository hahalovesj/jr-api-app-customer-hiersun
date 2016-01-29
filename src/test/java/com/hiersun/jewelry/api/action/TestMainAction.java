package com.hiersun.jewelry.api.action;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.MyContextUnitilsJUnit;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.service.utils.UserUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;
import com.hiersun.jewelry.api.util.SpringContextUtil;
import com.hiersun.jewelry.api.util.ValiHeadUtil;
import com.hiersun.jewelry.api.utiltest.ValiHeadUtilTest;

public class TestMainAction extends MyContextUnitilsJUnit{
	
	RedisBaseService redisBaseServiceImpl;
 
	
	
	
	@Test
	public void testMain() throws Exception{
		Map<String,Object> pubMsg = testMain("smsg.txt");
		pubMsg.remove("timeStamp");
		String expecteddata = this.readFile(getClass(), "expecteddata/smsg.txt");
		
		String str = JSON.toJSONString(pubMsg);
		
		Assert.assertEquals(expecteddata, str);
	}
	
	
	private Map<String,Object> testMain(String fileName) throws Exception{
	
		redisBaseServiceImpl = applicationContext.getBean("redisBaseServiceImpl",RedisBaseService.class);
		
		String msg = this.readFile(getClass(), "data/"+fileName);
		// 验证url参数
		boolean isRight = ValiHeadUtilTest.VeriParams(msg);
		if (!isRight) {
			return ValiHeadUtilTest.map;
		}
		String newMsg = msg.replace(" ", "");
		// 获取接口编码
		String tranType = ValiHeadUtil.getTranTypeStr(newMsg);
		// 获取请求header的实体
		RequestHeader reqHead = ValiHeadUtil.getRequestHeader(newMsg);
		// 请求body
		String requestBodyStr = ValiHeadUtil.getBodyStr(newMsg);
		BaseService baseService = (BaseService)SpringContextUtil.getBean(tranType+"AppService",BaseService.class);
		//看是否需要登陆
		Long userId = null;
		userId = reqHead.getToken() == null?null : UserUtil.getUsetId(reqHead.getToken(),redisBaseServiceImpl);
		if(baseService.ifValidateLogin()){
			if (userId == null) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 900010);
				ResponseBody responseBody = new ResponseBody();
				Map<String, Object> responseMsg = new HashMap<String, Object>();
				responseMsg.put("body", responseBody);
				responseMsg.put("head", respHeader);
				return responseMsg;
			}
		}
		//基础校验
		int resCode = baseService.baseValidateMsgBody(requestBodyStr,userId);
		if(resCode==0){
			Map<String,Object> map = baseService.doController(reqHead,requestBodyStr,userId);
			return map;
		}else{
			ResponseHeader respHeader = new ResponseHeader(resCode);
			ResponseBody responseBody = new ResponseBody();
			Map<String, Object> responseMsg = new HashMap<String, Object>();
			responseMsg.put("body", responseBody);
			responseMsg.put("head", respHeader);
			return responseMsg;
		}
	
	}
	
	
	
}
