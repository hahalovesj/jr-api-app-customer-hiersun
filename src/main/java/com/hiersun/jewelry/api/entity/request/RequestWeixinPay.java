package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;

/**
 * date 2016/1/13 19:50
 *
 * @author Leon yang_xu@hiersun.com
 * @version V1.0
 */
public class RequestWeixinPay extends Body {

    private String orderNO;
    private String orderName;
    private String orderDes;
    private double orderPrice;

    /**
     * 验证参数
     * @return
     */
    @Override
    public int volidateValue() {
        return 0;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderDes() {
        return orderDes;
    }

    public void setOrderDes(String orderDes) {
        this.orderDes = orderDes;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }
}
