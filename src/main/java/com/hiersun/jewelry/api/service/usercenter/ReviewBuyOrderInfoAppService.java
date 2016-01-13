package com.hiersun.jewelry.api.service.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.direct.pojo.JrdsGood;
import com.hiersun.jewelry.api.direct.pojo.JrdsOrder;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.direct.service.DirectOrderService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4017;
import com.hiersun.jewelry.api.entity.response.Response4022;
import com.hiersun.jewelry.api.entity.vo.Qualification;
import com.hiersun.jewelry.api.entity.vo.ResponseOrder;
import com.hiersun.jewelry.api.orderservice.domain.OrderQualificationPicVo;
import com.hiersun.jewelry.api.orderservice.pojo.JrasGoodQualification;
import com.hiersun.jewelry.api.orderservice.service.OrderService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("reviewBuyOrderInfoAppService")
public class ReviewBuyOrderInfoAppService implements BaseService {

	@Resource
	OrderService orderService;
	@Resource
	DirectOrderService directOrderService;
	@Resource
	DirectGoodsService directGoodsService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4017 body = JSON.parseObject(bodyStr, Request4017.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		try {
			Request4017 body = JSON.parseObject(bodyStr, Request4017.class);
			String orderNo = body.getOrderNO();
			// 根据orderNo查询订单信息（goodsid 价格等）
			JrdsOrder jrdsOrder = directOrderService.selectOrderByOrderNo(orderNo);
			// 根据goodsId查询商品信息（图片等）
			JrdsGood jrdsGood = directGoodsService.getOneDirectGoods(jrdsOrder.getGoodId(),false);
			// 鉴定信息的商品原图片
			List<OrderQualificationPicVo> OPiclist = orderService.selectOrderPic(jrdsOrder.getGoodId());

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("goodsId", jrdsOrder.getGoodId());
			// 直售业务的鉴定信息
			paramMap.put("businessType", 2);
			JrasGoodQualification qual = orderService.selectQualification(paramMap);
			// 返回信息
			Response4022 resp = new Response4022();
			ResponseOrder order = new ResponseOrder();
			order.setFreight("￥0.00元运费（平台包邮");
			order.setGoodsBuyPrice(jrdsGood.getBuyingPrice().doubleValue());
			order.setGoodsName(jrdsGood.getGoodName());
			order.setGoodsPrice(jrdsGood.getDirectPrice().doubleValue());
			order.setOrderNO(jrdsOrder.getOrderNo());
			Map<String, Object> goodsPicList = new HashMap<String, Object>();
			goodsPicList.put("picUrl", jrdsGood.getHostGragp());
			order.setGoodsPicList(goodsPicList);
			Qualification qualification = new Qualification();

			qualification.setBeanInfo(qual.getRemark());
			qualification.setIdentifyResult(qual.getRemark());

			List<Map<String, String>> qualiPicList = new ArrayList<Map<String, String>>();
			Map<String, String> resultOM = new HashMap<String, String>();
			for (int i = 0; i < OPiclist.size(); i++) {
				resultOM.put("picUrl", OPiclist.get(i).getPicUrl());
				resultOM.put("picDesc", OPiclist.get(i).getPicDesc());
				qualiPicList.add(resultOM);
			}
			qualification.setQualiPicList(qualiPicList);

			resp.setQualification(qualification);
			resp.setOrder(order);

			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(resp, respHead);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody resp = new ResponseBody();
			return this.packageMsgMap(resp, respHead);
		}

	}

	private Map<String, Object> packageMsgMap(Response4022 res, ResponseHeader respHead) {
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
