package com.hiersun.jewelry.api.service.sad;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request3002;
import com.hiersun.jewelry.api.entity.response.Response3001;
import com.hiersun.jewelry.api.entity.response.Response3002;
import com.hiersun.jewelry.api.orderservice.domain.ServiceOrderVO;
import com.hiersun.jewelry.api.orderservice.service.OrderService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("subServiceOrderAppService")
public class SubServiceOrderAppService implements BaseService{

	@Resource
	private UserService userService;
	
	@Resource
	private RedisBaseService redisBaseServiceImpl;
	
	@Resource
	private DirectGoodsService directGoodsService;
	
	@Resource
	private OrderService orderService;
	
	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr,Long userId) {
		Request3002 body = JSON.parseObject(bodyStr, Request3002.class);
		//TODO 实现此方法
		return body.volidateValue();
	}

	
	@Override
	public Map<String,Object> doController(RequestHeader reqHead,String bodyStr,Long userId)  throws Exception{
		try{	
			Request3002 body = JSON.parseObject(bodyStr, Request3002.class);
			ServiceOrderVO vo = new ServiceOrderVO();
			vo.setInputServiceType(body.getServiceType());
			vo.setUserId(userId);
			List<Map> picList = body.getGoodsPicList();
			List<Long> picId = new ArrayList<Long>();
			for (int i = 0; i < picList.size(); i++) {
				picId.add(Long.parseLong(picList.get(i).get("picID").toString()));
				boolean isMain = (Boolean) picList.get(i).get("isMain");
				if (isMain) {
					vo.setInputMainPicID(Long.parseLong(picList.get(i).get("picID").toString()));
				}
			}
			vo.setInputPicID(picId);
			vo.setInputAddressID(body.getAddressID());
			vo.setInputMaterial(body.getMaterial());
			vo.setInputWeight(body.getWeight());
			vo.setInputGoodsName(body.getGoodsName());
			vo.setInputGoodsDec(body.getGoodsDec());
			vo.setInputPayFor(body.getPayFor());
			// 计算价格
	
			// 逻辑处理
			ServiceOrderVO result = orderService.createServiceOrder(vo);
			// ServiceOrderVO result = new ServiceOrderVO();
	
			ResponseHeader respHead = null;
			if (result != null) {
				// 返回的header
				respHead = new ResponseHeader(0);
				respHead.setMessageID(reqHead.getMessageID());
				respHead.setTimeStamp(new Date().getTime());
				respHead.setTransactionType(reqHead.getTransactionType());
			}
	
			// 返回的body
			Response3002 responseBody = new Response3002();
			responseBody.setGoodsID(result.getOutputGoodsID());
			responseBody.setGoodsName(body.getGoodsName());
			responseBody.setOrderID(result.getOutputOrderID());
			responseBody.setOrderNO(result.getOutputPrderNO());
			responseBody.setGoodsPrice(body.getPayFor());
			responseBody.setGoodsDesc(body.getGoodsDec());
	
			return this.packageMsgMap(responseBody, respHead);

		} catch (Exception e) {
			e.printStackTrace();
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}
	
	private Map<String,Object> packageMsgMap(Response3002 res,ResponseHeader respHead){
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
