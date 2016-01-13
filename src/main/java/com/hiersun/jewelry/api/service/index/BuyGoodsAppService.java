package com.hiersun.jewelry.api.service.index;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.direct.pojo.JrdsGood;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request2007;
import com.hiersun.jewelry.api.entity.response.Response2007;
import com.hiersun.jewelry.api.entity.vo.Goods;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.service.RedisBaseService;
import com.hiersun.jewelry.api.user.domain.AddressVo;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.ResponseUtil;


@Service("buyGoodsAppService")
public class BuyGoodsAppService implements BaseService{
	
	 private static final Logger log = Logger.getLogger(BuyGoodsAppService.class);
	
	@Resource
	RedisBaseService redisBaseServiceImpl;
	
	@Resource	
	DirectGoodsService directGoodsService;
	
	@Resource
	UserService userService;
	
	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request2007 body = JSON.parseObject(bodyStr, Request2007.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		log.info("buyGoods	2007	接口 请求消息体："+reqHead.toString());
		log.info("buyGoods	2007	接口 请求消息体："+bodyStr);
		try {
			String reqToken = reqHead.getToken();
			String userid = redisBaseServiceImpl.get("api.token." + reqToken);
			if (userid == null || userid.trim().length() < 1) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 900010);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}

			Request2007 body = JSON.parseObject(bodyStr, Request2007.class);
			long goodsId = body.getGoodsID();
			// 查询商品
			JrdsGood goods = directGoodsService.getOneDirectGoods(goodsId);
			if (goods == null) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 200102);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}

			AddressVo vo = new AddressVo();
			vo.setUserId(Long.parseLong(userid));
			// 查询地址
			List<AddressVo> addrList = userService.getListAddressVo(vo);

			// 返回的header
			ResponseHeader respHead = new ResponseHeader(0);
			respHead.setMessageID(reqHead.getMessageID());
			respHead.setTimeStamp(new Date().getTime());
			respHead.setTransactionType(reqHead.getTransactionType());
			// 返回的body
			Response2007 responseBody = new Response2007();
			responseBody.setFreight(0);
			responseBody.setSendBy("顺丰快递");
			long token = System.currentTimeMillis();
			responseBody.setGoodsToken(token + "");
			redisBaseServiceImpl.set("goodstoken" + token, 1800, token + "");
			Goods resultGoods = new Goods();
			resultGoods.setGoodsDesc(goods.getGoodDesc());
			resultGoods.setGoodsID(goods.getId());
			resultGoods.setGoodsName(goods.getGoodName());
			resultGoods.setGoodsPrice(goods.getDirectPrice().doubleValue());
			resultGoods.setGoodsBuyPrice(goods.getBuyingPrice().doubleValue());
			resultGoods.setGoodsPicUrl(Commons.PIC_DOMAIN + goods.getHostGragp());

			responseBody.setGoods(resultGoods);

			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < addrList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (addrList.get(i).getIsDefault().equals("1")) {
					if (addrList.get(i).getId() != null) {
						map.put("addressID", addrList.get(i).getId());
					}
					if (addrList.get(i).getReceiver() != null) {
						map.put("receiver", addrList.get(i).getReceiver());
					}
					if (addrList.get(i).getReceiverMobile() != null) {
						map.put("receiverMobile", addrList.get(i).getReceiverMobile());
					}
					if (addrList.get(i).getArea() != null) {
						map.put("area", addrList.get(i).getArea());
					}
					if (addrList.get(i).getDetailedAddress() != null) {
						map.put("detailedAddress", addrList.get(i).getDetailedAddress());
					}
					if (addrList.get(i).getIsDefault() != null) {
						boolean isDef = addrList.get(i).getIsDefault().equals("1") ? true : false;
						map.put("Boolean", isDef);
					}
					resultList.add(map);
					break;
				}
			}
			if (resultList.size() == 0 && addrList != null && addrList.size() > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (addrList.get(0).getId() != null) {
					map.put("addressID", addrList.get(0).getId());
				}
				if (addrList.get(0).getReceiver() != null) {
					map.put("receiver", addrList.get(0).getReceiver());
				}
				if (addrList.get(0).getReceiverMobile() != null) {
					map.put("receiverMobile", addrList.get(0).getReceiverMobile());
				}
				if (addrList.get(0).getArea() != null) {
					map.put("area", addrList.get(0).getArea());
				}
				if (addrList.get(0).getDetailedAddress() != null) {
					map.put("detailedAddress", addrList.get(0).getDetailedAddress());
				}
				if (addrList.get(0).getIsDefault() != null) {
					boolean isDef = addrList.get(0).getIsDefault().equals("1") ? true : false;
					map.put("Boolean", isDef);
				}
				resultList.add(map);
			}
			responseBody.setAddressList(resultList);

			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			log.error("buyGoods 2007接口，发生异常，异常信息："+e.getMessage());
			e.printStackTrace();
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}
	
	private Map<String,Object> packageMsgMap(Response2007 res,ResponseHeader respHead){
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
