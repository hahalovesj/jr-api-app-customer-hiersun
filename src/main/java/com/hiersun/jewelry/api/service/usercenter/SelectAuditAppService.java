package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.direct.domain.JrdsGoodsPutawayVo;
import com.hiersun.jewelry.api.direct.pojo.JrdsGood;
import com.hiersun.jewelry.api.direct.pojo.JrdsGoodAudit;
import com.hiersun.jewelry.api.direct.service.DirectGoodsService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4017;
import com.hiersun.jewelry.api.entity.request.Request4026;
import com.hiersun.jewelry.api.entity.response.Response4026;
import com.hiersun.jewelry.api.orderservice.service.OrderService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("selectAuditAppService")
public class SelectAuditAppService implements BaseService {

	private static Logger log = Logger.getLogger(ReviewBuyOrderInfoAppService.class);
	
	@Resource
	OrderService orderService;
	@Resource
	DirectGoodsService directGoodsService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4026 body = JSON.parseObject(bodyStr, Request4026.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		
		log.info("selectAudit 	4026	接口请求消息体：" + reqHead.toString());
		log.info("selectAudit 	4026	接口请求消息体：" + bodyStr);
		
		try {
			Request4026 body = JSON.parseObject(bodyStr, Request4026.class);
			String goodsNo = body.getGoodsNO();
			// 根据订单查询goodsId
			
			JrdsGoodsPutawayVo jgpvo = new JrdsGoodsPutawayVo();
			jgpvo.setGoodNo(goodsNo);
			// 根据商品编号查询商品详情
			JrdsGood jg = directGoodsService.getJrdsGoodId(jgpvo);
			if(jg == null ){
				ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
				ResponseBody resp = new ResponseBody();
				return this.packageMsgMap(resp, respHead);
			}
//			Long goodsId = orderService.selectGoodIdByOrderNO(orderNo);
			// 鉴定信息
			JrdsGoodAudit jrdsGoodAudit = directGoodsService.getGoodAudit(jg.getId());
			Response4026 resp = new Response4026();
			resp.setAuditExplain(jrdsGoodAudit.getCommentInfo());
			resp.setAuditResult(jrdsGoodAudit.getResult() == true ? "同意直售" : "不予直售");

			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(resp, respHead);
		} catch (Exception e) {
			log.error("selectAudit 	4026	接口发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody resp = new ResponseBody();
			return this.packageMsgMap(resp, respHead);
		}
	}

	private Map<String, Object> packageMsgMap(Response4026 res, ResponseHeader respHead) {
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
