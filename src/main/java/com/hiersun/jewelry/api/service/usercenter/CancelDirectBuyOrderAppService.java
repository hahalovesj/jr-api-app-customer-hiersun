package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.OrderCategory;
import com.hiersun.jewelry.api.direct.pojo.JrdsOrder;
import com.hiersun.jewelry.api.direct.service.DirectOrderService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4020;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("cancelDirectBuyOrderAppService")
public class CancelDirectBuyOrderAppService implements BaseService {

	private static Logger log = Logger.getLogger(CancelDirectBuyOrderAppService.class);

	@Resource
	DirectOrderService directOrderService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4020 body = JSON.parseObject(bodyStr, Request4020.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("cancelDirectBuyOrder	4020	请求信息：" + reqHead.toString());
		log.info("cancelDirectBuyOrder	4020 	请求信息：" + bodyStr);

		try {
			Request4020 body = JSON.parseObject(bodyStr, Request4020.class);

			String orderNo = body.getOrderNo();
			String actionType = body.getActionType();
			JrdsOrder jrdsOrder = directOrderService.selectOrderByOrderNo(orderNo);

			if (actionType.equals(OrderCategory.ORDER_CATEGORY_FQGM)) {
				directOrderService.updateGiveUpOrder(jrdsOrder);
			} else if (actionType.equals(OrderCategory.ORDER_CATEGORY_QRGM)) {
				directOrderService.updateConfirmBuyOrder(jrdsOrder);
			} else if (actionType.equals(OrderCategory.ORDER_CATEGORY_SCDD)) {
				directOrderService.updateDeleteOrder(jrdsOrder);
			} else if (actionType.equals(OrderCategory.ORDER_CATEGORY_QRSH)) {
				directOrderService.updateConfirmReceivingOrder(jrdsOrder);
			}

			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			ResponseBody resp = new ResponseBody();
			return this.packageMsgMap(resp, respHead);
		} catch (Exception e) {
			log.error("cancelDirectBuyOrder	4020	接口发生异常，异常信息" + e.getMessage());
			e.printStackTrace();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody resp = new ResponseBody();
			return this.packageMsgMap(resp, respHead);
		}
	}

	private Map<String, Object> packageMsgMap(ResponseBody res, ResponseHeader respHead) {
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("body", res);
		responseMsg.put("head", respHead);
		return responseMsg;
	}
}
