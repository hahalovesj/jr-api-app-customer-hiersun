package com.hiersun.jewelry.api.service.user;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.MyContextUnitilsJUnit;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.service.utils.UserUtil;
import com.hiersun.jewelry.api.util.ValiHeadUtil;

public class TestSmsAppService extends MyContextUnitilsJUnit{

	SmsgAppService smsgAppService;
	
	RedisBaseService redisBaseServiceImpl;
	
	protected RequestHeader reqHead;
	
	protected String bodyStr;
	
	protected Long userId;
	
	/**
	 * 初始化
	 * @throws Exception
	 */
	@Before
	public void init() throws Exception{
		//读取接口请求数据
		String msg = this.readFile(getClass(), "data/smsg.txt");
		// 获取请求header的实体
		reqHead = ValiHeadUtil.getRequestHeader(msg);
		//获取消息体
		bodyStr = ValiHeadUtil.getBodyStr(msg);
		//注入service
		smsgAppService = applicationContext.getBean("smsgAppService",SmsgAppService.class);
		redisBaseServiceImpl = applicationContext.getBean("redisBaseServiceImpl",RedisBaseService.class);
		//userID也可以指定 不受是否登陆的影响
		userId = reqHead.getToken() == null?null : UserUtil.getUsetId(reqHead.getToken(),redisBaseServiceImpl);
	}
	
	/**
	 * 执行测试
	 * @throws Exception
	 */
	@Test
	public void testDoController() throws Exception{
		String expecteddata = this.readFile(getClass(), "expecteddata/smsg.txt");
		//执行和被测试的方法
		Map<String, Object> map = smsgAppService.doController(reqHead, bodyStr, userId);
		//期望数据转换为map
		Map<String,Object> expectMap = JSON.parseObject(expecteddata);
	 
		//断言
		Assert.assertEquals(expectMap.get("resCode"), map.get("resCode"));
	}
	
}
