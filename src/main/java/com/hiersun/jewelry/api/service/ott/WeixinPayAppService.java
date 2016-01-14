package com.hiersun.jewelry.api.service.ott;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.request.RequestWeixinPay;
import com.hiersun.jewelry.api.ott.domain.WeixinPayInfoVo;
import com.hiersun.jewelry.api.ott.service.WeixinPayService;
import com.hiersun.jewelry.api.service.BaseService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * date 2016/1/13 19:50
 *
 * @author Leon yang_xu@hiersun.com
 * @version V1.0
 */
public class WeixinPayAppService implements BaseService {

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
        RequestWeixinPay body = JSON.parseObject(bodyStr, RequestWeixinPay.class);
        String goodInfo = body.getGoodInfo();
        String orderNo = body.getOrderNo();
        String totalFee = body.getTotalFee();
        BigDecimal total = new BigDecimal(totalFee);
        //TODO 获取IP
        WeixinPayInfoVo weixinPayInfoVo = weixinPayService.getWeixinParams(goodInfo, orderNo, total.multiply(new BigDecimal("100")).intValue(), "", "APP");
        return null;
    }
}
