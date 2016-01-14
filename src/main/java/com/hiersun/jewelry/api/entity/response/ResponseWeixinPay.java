package com.hiersun.jewelry.api.entity.response;

import com.hiersun.jewelry.api.entity.ResponseBody;

/**
 * date 2016/1/14 10:33
 *
 * @author Leon yang_xu@hiersun.com
 * @version V1.0
 */
public class ResponseWeixinPay extends ResponseBody {

    private String partnerId;
    private String prepayId;
    private String nonceStr;
    private String timeStamp;
    private String sign;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
