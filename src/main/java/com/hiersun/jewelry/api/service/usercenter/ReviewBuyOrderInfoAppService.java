package com.hiersun.jewelry.api.service.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.dictionary.QualificationType;
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
import com.hiersun.jewelry.api.orderservice.pojo.JrasGoodInfoConfirm;
import com.hiersun.jewelry.api.orderservice.service.OrderService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.uploadresource.domain.AttachmentVo;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("reviewBuyOrderInfoAppService")
public class ReviewBuyOrderInfoAppService implements BaseService {

	private static Logger log = Logger.getLogger(ReviewBuyOrderInfoAppService.class);

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
		log.info("reviewBuyOrderInfo 	4022	接口请求消息体：" + reqHead.toString());
		log.info("reviewBuyOrderInfo 	4022	接口请求消息体：" + bodyStr);

		try {
			Request4017 body = JSON.parseObject(bodyStr, Request4017.class);
			String orderNo = body.getOrderNO();
			// 根据orderNo查询订单信息（goodsid 价格等）

			JrdsOrder jrdsOrder = directOrderService.selectOrderByOrderNo(orderNo);
			// 确认信息
			JrasGoodInfoConfirm jrasGoodInfoConfirm = orderService.selectConfirm(jrdsOrder.getGoodId(),
					Byte.parseByte("2"));
			// 根据goodsId查询商品信息（图片等）
			JrdsGood jrdsGood = directGoodsService.getOneDirectGoods(jrasGoodInfoConfirm.getGoodId(), false);
			// 直售业务的鉴定信息
			// List<OrderQualificationPicVo> OPiclist =
			// orderService.selectOrderPic(jrasGoodInfoConfirm.getId(),
			// "jrds_good_info_confirm");

			// 商品实物信息
			List<AttachmentVo> pciList = directGoodsService.getJrdsGoodPic(jrasGoodInfoConfirm.getId(),
					"jras_good_info_confirm", "jrdsconfirm");

			// 返回信息
			Response4022 resp = new Response4022();
			ResponseOrder order = new ResponseOrder();
			order.setFreight("￥0.00元运费（平台包邮");
			order.setGoodsBuyPrice(jrdsGood.getBuyingPrice().doubleValue());
			order.setGoodsName(jrdsGood.getGoodName());
			order.setGoodsPrice(jrdsGood.getDirectPrice().doubleValue());
			order.setOrderNO(jrdsOrder.getOrderNo());
			Map<String, Object> goodsPicList = new HashMap<String, Object>();
			goodsPicList.put("picUrl", Commons.PIC_DOMAIN + jrdsGood.getHostGragp());

			List<Map<String, Object>> picList = new ArrayList<Map<String, Object>>();
			picList.add(goodsPicList);

			order.setGoodsPicList(picList);
			Qualification qualification = new Qualification();

			Integer mNumber = jrasGoodInfoConfirm.getMatchedDegree().intValue();
			qualification.setBeanInfo(QualificationType.MATCHED_DEGREE.get(mNumber));
			qualification.setIsIdentifyResult(jrasGoodInfoConfirm.getSpecify());

			List<Map<String, String>> resultO = new ArrayList<Map<String, String>>();
			Map<String, String> resultOM = new HashMap<String, String>();
			for (int i = 0; i < pciList.size(); i++) {
				resultOM.put("picUrl", Commons.PIC_DOMAIN + pciList.get(i).getFullName());
				resultOM.put("picDesc", pciList.get(i).getAttrDesc());
				resultO.add(resultOM);
			}

			qualification.setQualiPicList(resultO);

			resp.setQualification(qualification);
			resp.setOrder(order);

			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(resp, respHead);
		} catch (Exception e) {
			log.error("reviewBuyOrderInfo 	4022	接口发生异常，异常信息：" + e.getMessage());
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
