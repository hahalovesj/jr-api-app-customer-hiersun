package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;

/**
 * date 2016/1/13 19:50
 *
 * @author Leon yang_xu@hiersun.com
 * @version V1.0
 */
public class RequestWeixinPay extends Body {

    private String orderNo;

    private String goodInfo;

    private String totalFee;

    @Override
    public int volidateValue() {
        return 0;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGoodInfo() {
        return goodInfo;
    }

    public void setGoodInfo(String goodInfo) {
        this.goodInfo = goodInfo;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }
}
