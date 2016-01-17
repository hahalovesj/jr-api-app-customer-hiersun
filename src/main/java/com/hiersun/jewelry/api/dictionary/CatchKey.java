package com.hiersun.jewelry.api.dictionary;

/**
 * 定义redis缓存的KEY
 * @author lilong
 *
 */
public class CatchKey {
	
	/**
	 * token 的key
	 */
	public static String APP_USERID_CACH_KEY_START = "api.app.token." ;
	
	/**
	 * 购买商品时的表单token
	 */
	public static String APP_GOOD_TOKEN = "api.app.goods.token.";
	
	/**
	 * 短信的key
	 */
	public static String APP_MSG_KEY = "api.app.msg." ;
	
	/**
	 * 头像的key
	 */
	public static String USER_HEAD_ICO_KEY = "api.app.head.icos" ;

}
