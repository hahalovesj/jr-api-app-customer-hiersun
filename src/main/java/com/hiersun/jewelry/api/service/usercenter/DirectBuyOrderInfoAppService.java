package com.hiersun.jewelry.api.service.usercenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.constant.StatusMap;
import com.hiersun.jewelry.api.dictionary.QualificationType;
import com.hiersun.jewelry.api.direct.domain.JrdsOrderVo;
import com.hiersun.jewelry.api.direct.pojo.JrdsOrderLog;
import com.hiersun.jewelry.api.direct.service.DirectOrderService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4017;
import com.hiersun.jewelry.api.entity.response.Response4019;
import com.hiersun.jewelry.api.entity.vo.ResponseAddress;
import com.hiersun.jewelry.api.entity.vo.ResponseJrdsOrder;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.user.pojo.JrMemberAddress;
import com.hiersun.jewelry.api.user.service.UserService;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

/***
 * 3.1.19 我购买的---订单详情 （directBuyOrderInfo  4019）
 * @author lilong
 *
 */
public class DirectBuyOrderInfoAppService  implements BaseService{

	@Resource
	private DirectOrderService directOrderService;
	@Resource
	private UserService userService;
	
	@Override
	public boolean ifValidateLogin() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4017 body = JSON.parseObject(bodyStr, Request4017.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
		try{
			Request4017 body = JSON.parseObject(bodyStr, Request4017.class);
			String orderNo = body.getOrderNO();
			// 基本信息
			JrdsOrderVo jrdsOrderVo = directOrderService.selectDirectOrder(orderNo);
	
			List<JrdsOrderLog> jrdsOrderLogs = directOrderService.getJrdsOrderLogByOrderId(jrdsOrderVo.getId());
			Response4019 response4019 = new Response4019();
			
			ResponseJrdsOrder order = new ResponseJrdsOrder();
			order.setGoodsName(jrdsOrderVo.getGoodsName());
			order.setGoodsPic(jrdsOrderVo.getGoodsPic());
			order.setOrderID(jrdsOrderVo.getId());
			order.setOrderNO(jrdsOrderVo.getOrderNo());
			order.setOrderPrice(jrdsOrderVo.getOrderAmount().doubleValue());
			order.setGoodsBuyPrice(jrdsOrderVo.getPayAmount().doubleValue());
			order.setFreightDesc("￥0.00元运费（平台包邮）");// 暂时写死
			order.setOrderMsg(jrdsOrderVo.getOrderMessage());
			order.setOrderStatusCode(jrdsOrderVo.getOrderStatus().toString());
			order.setOrderStatusDes(StatusMap.SERVICE_ORDER_STAUTEDES_DB_MAP.get(jrdsOrderVo.getOrderStatus().intValue()));
	
			Map<Integer, String> map = new HashMap<Integer, String>();
			for (JrdsOrderLog jrdsOrderLog : jrdsOrderLogs) {
				map.put(jrdsOrderLog.getLogStatus().intValue(),
						DateUtil.dateToStr(jrdsOrderLog.getCreated(), "yyyy-MM-dd HH:mm:ss"));
			}
			// 0 下单时间 1 支付时间 7 鉴定时间 9 配送时间 10 确认收货时间
			order.setCreateTime(map.get(0));
			order.setPayType(QualificationType.PAY_TYPE_MAP.get(jrdsOrderVo.getPayType()));
			order.setDeliveryedTime(map.get(9));
			order.setConfirmedTime(map.get(10));
			order.setAppraisaledTime(map.get(7) == null ? map.get(5) : null);
	
			// 收货地址
			Long addressID = jrdsOrderVo.getAddressId();
			JrMemberAddress address = userService.getAddressById(addressID);
	
			ResponseAddress addressResult = new ResponseAddress();
			addressResult.setArea(address.getArea());
			addressResult.setDetailedAddress(address.getDetailedAddress());
			addressResult.setReceiver(address.getReceiver());
			addressResult.setReceiverMobile(address.getReceiverMobile());
			order.setAddress(addressResult);
			
			response4019.setOrder(order);
			
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return packageMsgMap(response4019,respHead);
		}catch (Exception e) {
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			e.printStackTrace();
			return this.packageMsgMap(responseBody, respHeader);
		}
		
	


	}
	
	
	private Map<String,Object> packageMsgMap(Response4019 res,ResponseHeader respHead){
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
