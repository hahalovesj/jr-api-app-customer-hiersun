package com.hiersun.jewelry.api.service.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.direct.domain.JrdsOrderInfoByCreatVo;
import com.hiersun.jewelry.api.direct.service.DirectOrderService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request2008;
import com.hiersun.jewelry.api.entity.response.Response2008;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("subGoodsAppService")
public class SubGoodsAppService implements BaseService {
	
	private static Logger log = Logger.getLogger(SubGoodsAppService.class);
	
	@Resource
	RedisBaseService redisBaseServiceImpl;
	
	@Resource
	DirectOrderService directOrderService;
	
	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr,Long userId) {
		Request2008 body = JSON.parseObject(bodyStr, Request2008.class);
		return body.volidateValue();
	}

	
	@Override
	public Map<String,Object> doController(RequestHeader reqHead, String bodyStr,Long userId)  throws Exception{
		log.info("正在处理提交订单接口subGoods(2008),Header=" + reqHead.toString());
		log.info("正在处理提交订单接口subGoods(2008),Body=" + bodyStr);
		try {
			Request2008 body = JSON.parseObject(bodyStr, Request2008.class);
			
			String goodsToken = body.getGoodsToken();
			String cacheToken = redisBaseServiceImpl.get("goodstoken" + goodsToken);
			if (!cacheToken.equals(goodsToken.trim())) {
				log.error("提交订单接口subGoods(2008),goodsToken不正确,goodsToken=" + goodsToken + ",cacheToken=" + cacheToken);
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 200801);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}
			JrdsOrderInfoByCreatVo vo = new JrdsOrderInfoByCreatVo();
			vo.setGoodsID(body.getGoodsID());
			vo.setInputFreight(body.getFreight());
			vo.setInputAddressID(body.getAddressID());
			vo.setInputSendBy(body.getSendBy());
			vo.setInputConsumerMsg(body.getConsumerMsg());
			vo.setBuyerID(userId);

			JrdsOrderInfoByCreatVo resultVo = directOrderService.createDirectOrder(vo);
			redisBaseServiceImpl.decr("goodstoken" + goodsToken);
			
			// 返回的header
			ResponseHeader respHead = new ResponseHeader(0);
			respHead.setMessageID(reqHead.getMessageID());
			respHead.setTimeStamp(new Date().getTime());
			respHead.setTransactionType(reqHead.getTransactionType());
			
			// 返回的body
			Response2008 responseBody = new Response2008();

			responseBody.setGoodsID(resultVo.getGoodsID());
			responseBody.setGoodsName(resultVo.getOutputGoodsName());
			responseBody.setGoodsPrice(resultVo.getOutputGoodsPrice().doubleValue());
			responseBody.setOrderNO(resultVo.getOutputOrderNO());
			responseBody.setGoodsDesc(resultVo.getOutputGoodsDesc());
			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			for (int i = 0; i < e.getStackTrace().length; i++) {
				log.error("创建直售订单失败，异常类：\"" + e.getStackTrace()[i].getClassName() + "\"; 函数方法：\""
						+ e.getStackTrace()[i].getMethodName() + "()\"; Error：" + e.getMessage() + "; 错误行："
						+ e.getStackTrace()[i].getLineNumber());
			}
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}
	
	private Map<String,Object> packageMsgMap(Response2008 res,ResponseHeader respHead){
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
