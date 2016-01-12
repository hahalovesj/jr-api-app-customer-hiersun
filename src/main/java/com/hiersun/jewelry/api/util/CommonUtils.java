package com.hiersun.jewelry.api.util;

public class CommonUtils {
	/**
	 * 把手机号变成 138****1111
	 * 
	 * @param mobile
	 * @return
	 */
	public static String mobileForNickName(String mobile) {
		char[] mobiles = mobile.toCharArray();
		String newMobile = "";
		for (int i = 0; i < mobiles.length; i++) {
			if (i > 2 && i < 7) {
				newMobile += "*";
			} else {
				newMobile += mobiles[i];
			}
		}
		return newMobile;
	}

	/**
	 * 去掉html标签
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static String stripHtml(String htmlStr) {
		String str = htmlStr.replaceAll("<[.[^<]]*>", "");
		return str;
	}
}
