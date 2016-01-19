package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.direct.domain.JrdsGoodVo;
import com.hiersun.jewelry.api.direct.domain.JrdsGoodsPutawayVo;
import com.hiersun.jewelry.api.direct.pojo.JrdsGood;
import com.hiersun.jewelry.api.direct.pojo.JrdsOrder;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.direct.service.DirectOrderService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4025;
import com.hiersun.jewelry.api.entity.response.Response4025;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("cancelOrderAppService")
public class CancelOrderAppService implements BaseService {

	private static Logger log = Logger.getLogger(CancelOrderAppService.class);

	@Resource
	DirectGoodsService directGoodsService;
	@Resource
	DirectOrderService directOrderService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4025 body = JSON.parseObject(bodyStr, Request4025.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("cancelOrder	4025	请求信息：" + reqHead.toString());
		log.info("cancelOrder	4025 	请求信息：" + bodyStr);

		try {
			Request4025 body = JSON.parseObject(bodyStr, Request4025.class);
			// 派分请求
			switch (Integer.parseInt(body.getActionType())) {
			// 取消订单
			case 1: {
				// 查询goodID
				JrdsGoodsPutawayVo jgpvo = new JrdsGoodsPutawayVo();
				jgpvo.setGoodNo(body.getGoodsNO());
				// 根据商品编号查询商品详情
				JrdsGood jg = directGoodsService.getJrdsGoodId(jgpvo);
				if (jg != null) {
					JrdsOrder jo = new JrdsOrder();
					jo.setSellerMemberId(userId);
					jo.setGoodId(jg.getId());
					// 取消订单
					directOrderService.cancelOrder(jo);
					Response4025 resp = new Response4025();
					ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
					return this.packageMsgMap(resp, respHead);
				}
			}
			// 删除订单
			case 2: {
				JrdsGoodVo jgvo = new JrdsGoodVo();
				jgvo.setSellerMemberId(userId);
				jgvo.setGoodNo(body.getGoodsNO());
				directGoodsService.delJrdsGoods(jgvo);
				Response4025 resp = new Response4025();
				ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
				return this.packageMsgMap(resp, respHead);
			}
			// 商品重新上架
			case 3: {
				JrdsGoodsPutawayVo jgpvo = new JrdsGoodsPutawayVo();
				jgpvo.setGoodNo(body.getGoodsNO());
				// 根据商品编号查询商品详情
				JrdsGood jg = directGoodsService.getJrdsGoodId(jgpvo);
				if (jg != null) {
					long between = System.currentTimeMillis() - jg.getPublishTime().getTime();// 得到两者的毫秒数
					long day = between / (24 * 60 * 60 * 1000);
					if (day < 1) {
						// 重新上架需要24小时
						ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 40250301);
						ResponseBody responseBody = new ResponseBody();
						return this.packageMsgMap(responseBody, respHeader);
					} else {
						// 状态修改为发布
						directGoodsService.orderPutaway(jg.getId());
						Response4025 resp = new Response4025();
						ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
						return this.packageMsgMap(resp, respHead);
					}
				}
			}
			}
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody resp = new ResponseBody();
			return this.packageMsgMap(resp, respHead);
		} catch (Exception e) {
			log.error("cancelOrder	4025	接口发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody resp = new ResponseBody();
			return this.packageMsgMap(resp, respHead);
		}
	}

	private Map<String, Object> packageMsgMap(Response4025 res, ResponseHeader respHead) {
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
