package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.constant.StatusMap;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.dictionary.TradeLogDesc;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4011;
import com.hiersun.jewelry.api.entity.response.Response4011;
import com.hiersun.jewelry.api.entity.vo.ResponseAddress;
import com.hiersun.jewelry.api.entity.vo.ResponseServiceOrder;
import com.hiersun.jewelry.api.orderservice.domain.OrderVo;
import com.hiersun.jewelry.api.orderservice.pojo.JrasOrderLog;
import com.hiersun.jewelry.api.orderservice.service.OrderService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.user.pojo.JrMemberAddress;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("serviceOrderInfoAppService")
public class ServiceOrderInfoAppService implements BaseService {

	private static Logger log = Logger.getLogger(ServiceOrderInfoAppService.class);

	@Resource
	private OrderService orderService;

	@Resource
	private UserService userService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4011 body = JSON.parseObject(bodyStr, Request4011.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {

		log.info("serviceOrderInfo 	4011	接口请求消息体：" + reqHead.toString());
		log.info("serviceOrderInfo 	4011	接口请求消息体：" + bodyStr);

		try {
			Request4011 body = JSON.parseObject(bodyStr, Request4011.class);
			OrderVo orderVo = orderService.selectOrderDetailByNO(body.getOrderNO());
			if (orderVo == null) {
				ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 401101);
				ResponseBody responseBody = new ResponseBody();
				return this.packageMsgMap(responseBody, respHeader);
			}

			ResponseServiceOrder responseServiceOrder = new ResponseServiceOrder();
			responseServiceOrder.setCreateTime(DateUtil.dateToStr(orderVo.getPayTime(), "yyyy-MM-dd HH:mm:ss"));
			responseServiceOrder.setGoodsName(orderVo.getName());
			responseServiceOrder.setGoodsPicUrl(Commons.PIC_DOMAIN + orderVo.getFullName());
			responseServiceOrder.setOrderID(orderVo.getId());
			responseServiceOrder.setOrderNO(orderVo.getOrderNo());
			responseServiceOrder.setOrderMsg(orderVo.getOrderMsg());
			
			// 如果订单状态为待配送和待发货 需要用鉴定状态去判断显示什么内容
			if (orderVo.getStatus().intValue() == StatusMap.SERVICE_ORDER_DB_STAUE_DPS
					|| orderVo.getStatus().intValue() == StatusMap.SERVICE_ORDER_DB_STAUE_DSH) {
				
				responseServiceOrder.setOrderStatusCode(StatusMap.SERVICE_ORDER_STAUTECODE_APP_MAP.get(orderVo.getStype()
						+ "_" + orderVo.getStatus() + "_" + orderVo.getJdResult()));
				
				responseServiceOrder.setOrderStatusDes(StatusMap.SERVICE_ORDER_STAUTEDES_APP_MAP.get(orderVo.getStype()
						+ "_" + orderVo.getStatus() + "_" + orderVo.getJdResult()));
			} else {
				
				responseServiceOrder.setOrderStatusCode(StatusMap.SERVICE_ORDER_STAUTECODE_APP_MAP.get(orderVo.getStype()
						+ "_" + orderVo.getStatus()));
				
				responseServiceOrder.setOrderStatusDes(StatusMap.SERVICE_ORDER_STAUTEDES_APP_MAP.get(orderVo.getStype()
						+ "_" + orderVo.getStatus()));
			}
			responseServiceOrder.setOrderPrice(orderVo.getAmount().doubleValue());

			// 收货地址
			Long addressID = orderVo.getAddressId();
			JrMemberAddress address = userService.getAddressById(addressID);
			ResponseAddress responseAddress = new ResponseAddress();
			responseAddress.setArea(address.getArea());
			responseAddress.setDetailedAddress(address.getDetailedAddress());
			responseAddress.setReceiver(address.getReceiver());
			responseAddress.setReceiverMobile(address.getReceiverMobile());
			// 获取收货地址列表 并组合成日志描述字符串
			String orderLog = "";
			List<JrasOrderLog> jrasOrderLogs = orderService.getLogsByOrderId(orderVo.getId());
			for (JrasOrderLog jrasOrderLog : jrasOrderLogs) {
				// 验证时间和完成时间对用户不做呈现
				if (jrasOrderLog.getLogStatus().intValue() != 4 && jrasOrderLog.getLogStatus().intValue() != 9) {

					orderLog = orderLog
							+ TradeLogDesc.SERVICEORDER_TRADELOG_MAP.get(orderVo.getStype() + "_"
									+ jrasOrderLog.getLogStatus().intValue()) + ":"
							+ DateUtil.dateToStr(jrasOrderLog.getCreated(), "yyyy-MM-dd HH:mm:ss") + "\r\n\r\n";
				}
			}
			orderLog = orderLog.length() == 0 ? "" : orderLog.substring(0, orderLog.length() - 1);
			Response4011 res = new Response4011();
			res.setOrder(responseServiceOrder);
			res.setAddress(responseAddress);
			res.setOrderLog(orderLog);
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(res, respHead);
		} catch (Exception e) {
			log.error("serviceOrderInfo 	4011	接口发生异常，异常信息：" + e.getMessage());
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			e.printStackTrace();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}

	private Map<String, Object> packageMsgMap(Response4011 res, ResponseHeader respHead) {
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
