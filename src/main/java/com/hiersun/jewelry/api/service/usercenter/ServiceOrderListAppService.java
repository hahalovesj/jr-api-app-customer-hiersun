package com.hiersun.jewelry.api.service.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.constant.StatusMap;
import com.hiersun.jewelry.api.dictionary.Commons;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4010;
import com.hiersun.jewelry.api.entity.response.Response4010;
import com.hiersun.jewelry.api.entity.vo.ResponseServiceOrder;
import com.hiersun.jewelry.api.orderservice.domain.OrderVo;
import com.hiersun.jewelry.api.orderservice.service.OrderService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("serviceOrderListAppService")
public class ServiceOrderListAppService implements BaseService{

	@Resource
	private OrderService orderService;
	
	
	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr,Long userId) {
		Request4010 body = JSON.parseObject(bodyStr, Request4010.class);
		return body.volidateValue();
	}

	
	@Override
	public Map<String,Object> doController(RequestHeader reqHead,String bodyStr,Long userId)  throws Exception{
		try {
			// 解析验证消息体
			Request4010 body = JSON.parseObject(bodyStr, Request4010.class);
			// 将客户端传入的状态翻译成数据库状态
			Integer[] statusArray = StatusMap.SERVICE_ORDER_STAUTECODE_MAP.get(body.getOrderTypeCode());
			// 查询总数
			OrderVo queryOrderVo = new OrderVo();
			queryOrderVo.setMemberId(userId);
			queryOrderVo.setStatusArray(statusArray);
			// 查询总数
			Integer conunt = orderService.getServiceOrderCountByUserAndStatus(queryOrderVo);
			// 记录为0 不做进一步查询，列表为空
			if (conunt == null || conunt == 0) {
				Response4010 res = new Response4010();
				res.setIsEnd(true);
				res.setPageNO(body.getPageNO());
				ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
				return this.packageMsgMap(res, respHead);
			}
			int pageNO = body.getPageNO();
			queryOrderVo.setPageSize(20);
			queryOrderVo.setPageIndex(pageNO * 20 + 1);
			List<OrderVo> orderVoList = orderService.getServiceOrderByUserAndStatus(queryOrderVo);
			List<ResponseServiceOrder> orderList = new ArrayList<ResponseServiceOrder>();
			ResponseServiceOrder responseServiceOrder = null;
			// 封装订单列表集合
			for (OrderVo orderVo : orderVoList) {
				responseServiceOrder = new ResponseServiceOrder();
				responseServiceOrder.setCreateTime(DateUtil.dateToStr(orderVo.getCreated(), "yyyy-MM-dd HH:mm:ss"));
				responseServiceOrder.setGoodsName(orderVo.getName());
				responseServiceOrder.setGoodsPic(Commons.PIC_DOMAIN + orderVo.getFullName());
				responseServiceOrder.setOrderID(orderVo.getId());
				responseServiceOrder.setOrderNO(orderVo.getOrderNo());
				responseServiceOrder.setOrderMsg(orderVo.getOrderMsg());
				responseServiceOrder.setOrderStatusCode(orderVo.getStatus().intValue());
				// 如果订单状态为待配送和待发货 需要用鉴定状态去判断显示什么内容
				if (orderVo.getStatus().intValue() == StatusMap.SERVICE_ORDER_DB_STAUE_DPS
						|| orderVo.getStatus().intValue() == StatusMap.SERVICE_ORDER_DB_STAUE_DSH) {
					responseServiceOrder.setOrderStatusDes(StatusMap.SERVICE_ORDER_STAUTEDES_APP_MAP.get(orderVo
							.getStype() + "_" + orderVo.getStatus() + "_" + orderVo.getJdResult()));
				} else {
					responseServiceOrder.setOrderStatusDes(StatusMap.SERVICE_ORDER_STAUTEDES_APP_MAP.get(orderVo
							.getStype() + "_" + orderVo.getStatus()));
				}

				responseServiceOrder.setOrderPrice(orderVo.getAmount().doubleValue());
				orderList.add(responseServiceOrder);
			}

			int nowNumber = (pageNO + 1) * 20;
			Response4010 res = new Response4010();
			res.setIsEnd(nowNumber >= conunt ? true : false);
			res.setOrderList(orderList);
			res.setPageNO(pageNO);
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			
			return this.packageMsgMap(res, respHead);
		} catch (Exception e) {
			ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
			ResponseBody responseBody = new ResponseBody();
			e.printStackTrace();
			return this.packageMsgMap(responseBody, respHeader);
		}
	}
	
	private Map<String,Object> packageMsgMap(Response4010 res,ResponseHeader respHead){
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
