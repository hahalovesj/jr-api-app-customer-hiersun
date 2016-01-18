package com.hiersun.jewelry.api.service.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.direct.pojo.JrdsOrder;
import com.hiersun.jewelry.api.direct.service.DirectOrderService;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.Request4014;
import com.hiersun.jewelry.api.entity.response.Response4014;
import com.hiersun.jewelry.api.entity.vo.LogisticsInfo;
import com.hiersun.jewelry.api.expressinfo.pojo.ExpressInfo;
import com.hiersun.jewelry.api.expressinfo.pojo.LogisticsTrackingInfo;
import com.hiersun.jewelry.api.expressinfo.service.ExpressInfoService;
import com.hiersun.jewelry.api.expressinfo.service.LogisticsTrackingInfoService;
import com.hiersun.jewelry.api.orderservice.pojo.JrasOrder;
import com.hiersun.jewelry.api.orderservice.service.OrderService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;

@Service("logisticsInfosAppService")
public class LogisticsInfosAppService implements BaseService {

	private static Logger log = Logger.getLogger(LogisticsInfosAppService.class);
	
	@Resource
	DirectOrderService directOrderService;
	@Resource
	ExpressInfoService expressInfoService;
	@Resource
	OrderService orderService;
	@Resource
	LogisticsTrackingInfoService logisticsTrackingInfoService;

	@Override
	public boolean ifValidateLogin() {
		return true;
	}

	@Override
	public Integer baseValidateMsgBody(String bodyStr, Long userId) {
		Request4014 body = JSON.parseObject(bodyStr, Request4014.class);
		return body.volidateValue();
	}

	@Override
	public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
	
		log.info("logisticsInfos 	4014	接口请求消息体：" + reqHead.toString());
		log.info("logisticsInfos 	4014	接口请求消息体：" + bodyStr);
		
		try {

			Request4014 body = JSON.parseObject(bodyStr, Request4014.class);

			ExpressInfo exInfo = null;
			// 判断订单是直售30/服务31
			if (body.getOrderNO().startsWith("30")) {
				JrdsOrder dsOrder = directOrderService.selectOrderByOrderNo(body.getOrderNO());
				// 获取ex表对象
				ExpressInfo ex = new ExpressInfo();
				ex.setBusinessType(Byte.valueOf("2"));
				ex.setExpressMark(true);
				ex.setOrderId(dsOrder.getId());
				exInfo = expressInfoService.getExpressInfo(ex);
			} else if (body.getOrderNO().startsWith("31")) {
				JrasOrder asOrder = orderService.selectOrderByOrderNo(body.getOrderNO());

				ExpressInfo ex = new ExpressInfo();
				ex.setBusinessType(Byte.valueOf("1"));
				//2,4,6,7 邮入 其他 邮出
				if(asOrder.getStatus().intValue() == 2 || asOrder.getStatus().intValue() == 4|| 
						asOrder.getStatus().intValue() == 6 || asOrder.getStatus().intValue() == 7 ){
					ex.setExpressMark(false);//1是邮出，0是邮入
				}else{
					ex.setExpressMark(true);//1是邮出，0是邮入
				}
				if(asOrder.getIsDelivery()==null || asOrder.getIsDelivery().intValue()==0){
					exInfo = null;
				}else{
					ex.setOrderId(asOrder.getId());
					exInfo = expressInfoService.getExpressInfo(ex);
				}
				//ex.setExpressMark(asOrder.getStatus().intValue() > 7 ? true : false);//1是邮出，0是邮入
				
			}

			// 返回的body
			Response4014 responseBody = new Response4014();
			List<LogisticsInfo> infoList = new ArrayList<LogisticsInfo>();
			if(exInfo!=null){
				// 根据物流信息对象查询该物流的跟踪信息
				List<LogisticsTrackingInfo> list = logisticsTrackingInfoService.queryTrackingInfoList(exInfo);
				if(list!=null){
					// 拼装app数据
					for (int i = 0; i < list.size(); i++) {
						LogisticsInfo li = new LogisticsInfo();
						li.setDes(list.get(i).getContent());
						li.setTime(list.get(i).getFormatTime());
						li.setIsEnd(list.get(i).getState().intValue() == 3 ? true : false);
						infoList.add(li);
					}
				}
			}
		
			responseBody.setInfoList(infoList);
			// 配置返回信息
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 0);
			return this.packageMsgMap(responseBody, respHead);
		} catch (Exception e) {
			log.error("logisticsInfos 	4014	接口发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			ResponseBody responseBody = new ResponseBody();
			ResponseHeader respHead = ResponseUtil.getRespHead(reqHead, 99999);
			return this.packageMsgMap(responseBody, respHead);
		}
	}

	private Map<String, Object> packageMsgMap(Response4014 res, ResponseHeader respHead) {
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
