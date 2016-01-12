package com.hiersun.jewelry.api.entity;

public class RequestHeader extends Header {

	/** 签名 */
	private String sign;

	/** 来源 */
	private String terminal;

	/** 终端版本(1.0.1) */
	private String version;

	/** 手机唯一识别号 */
	private String imei;

	/** 手机型号 */
	private String ua;

	/** 登陆后的令牌 */
	private String token;

	/** 网络运营商 */
	private String networkOper;

	/** 网络状态（WIFI,移动网络） */
	private String networkStatus;

	
	@Override
	public int volidateValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNetworkOper() {
		return networkOper;
	}

	public void setNetworkOper(String networkOper) {
		this.networkOper = networkOper;
	}

	public String getNetworkStatus() {
		return networkStatus;
	}

	public void setNetworkStatus(String networkStatus) {
		this.networkStatus = networkStatus;
	}

	@Override
	public String toString() {
		return "RequestHeader{" +
				"sign='" + sign + '\'' +
				", terminal='" + terminal + '\'' +
				", version='" + version + '\'' +
				", imei='" + imei + '\'' +
				", ua='" + ua + '\'' +
				", token='" + token + '\'' +
				", networkStatus='" + networkStatus + '\'' +
				'}';
	}
}
