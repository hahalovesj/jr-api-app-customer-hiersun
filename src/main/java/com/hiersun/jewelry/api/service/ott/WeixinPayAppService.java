package com.hiersun.jewelry.api.service.ott;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.request.RequestWeixinPay;
import com.hiersun.jewelry.api.entity.response.ResponseWeixinPay;
import com.hiersun.jewelry.api.ott.domain.WeixinPayInfoVo;
import com.hiersun.jewelry.api.ott.service.WeixinPayService;
import com.hiersun.jewelry.api.service.BaseService;
import com.hiersun.jewelry.api.util.ResponseUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * date 2016/1/13 19:50
 *
 * @author Leon yang_xu@hiersun.com
 * @version V1.0
 */
@Service("weixinPayAppService")
public class WeixinPayAppService implements BaseService {
    private static final Logger log = Logger.getLogger(WeixinPayAppService.class);

    private static final String APP_ID = "wx54fdac58be580e83";
    @Resource
    private WeixinPayService weixinPayService;

    @Override
    public boolean ifValidateLogin() {
        return true;
    }


    @Override
    public Integer baseValidateMsgBody(String bodyStr, Long userId) {
        RequestWeixinPay body = JSON.parseObject(bodyStr, RequestWeixinPay.class);
        return body.volidateValue();
    }

    //String goodInfo, String orderNo, Integer totalFee, String IPAddress, String tradeType
    @Override
    public Map<String, Object> doController(RequestHeader reqHead, String bodyStr, Long userId) throws Exception {
        log.info("weixinpay 接口请求消息体：" + reqHead.toString());
        log.info("weixinpa 接口请求消息体：" + bodyStr);
        try {
            RequestWeixinPay body = JSON.parseObject(bodyStr, RequestWeixinPay.class);
            String goodDesc = body.getOrderDes();
            String orderNo = body.getOrderNO();
            String totalFee = String.valueOf(body.getOrderPrice());
            String goodInfo = body.getOrderName();
            BigDecimal total = new BigDecimal(totalFee);
            WeixinPayInfoVo weixinPayInfoVo = weixinPayService.getWeixinParams(goodInfo, goodDesc, orderNo, total.multiply(new BigDecimal("100")).intValue(), reqHead.getIPAddress(), "APP");
            ResponseWeixinPay responseWeixinPay = new ResponseWeixinPay();
            responseWeixinPay.setPartnerId(weixinPayInfoVo.getPartnerId());
            responseWeixinPay.setSign(weixinPayInfoVo.getSign());
            responseWeixinPay.setTimeStamp(weixinPayInfoVo.getTimeStamp());
            responseWeixinPay.setNonceStr(weixinPayInfoVo.getNonceStr());
            responseWeixinPay.setPrepayId(weixinPayInfoVo.getPrepayId());
            responseWeixinPay.setAppId(APP_ID);
            ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 0);
            return this.packageMsgMap(responseWeixinPay, respHeader);
        } catch (Exception e) {
            log.error("weixinpay 接口发生异常，异常信息：" + e.getMessage());
            e.printStackTrace();
            ResponseHeader respHeader = ResponseUtil.getRespHead(reqHead, 99999);
            ResponseBody responseBody = new ResponseBody();
            return this.packageMsgMap(responseBody, respHeader);
        }

    }

    private Map<String, Object> packageMsgMap(ResponseBody res, ResponseHeader respHead) {
        Map<String, Object> responseMsg = new HashMap<String, Object>();
        responseMsg.put("body", res);
        responseMsg.put("head", respHead);
        return responseMsg;
    }
}
