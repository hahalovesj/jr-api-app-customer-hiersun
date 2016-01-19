package com.hiersun.jewelry.api.util;

import java.util.HashMap;
import java.util.Map;

import com.hiersun.jewelry.api.dictionary.IcoDictionary;

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

	/**
	 * 获取男女头像 0是女 1是男
	 */
	public static Map<String, String> getIco(int i) {
		if (i > 1) {
			return null;
		}
		int randomKey = (int) com.hiersun.jewelry.api.util.Trunc.getRandomForRange(1, 10);
		Map<String, String> map = new HashMap<String, String>();
		String big, small;
		// 0 是女
		if (i == 0) {
			big = IcoDictionary.BIG_WOMAN_PHOTO.get(randomKey);
			small = IcoDictionary.SMALL_WOMAN_PHOTO.get(randomKey);
		} else {
			big = IcoDictionary.BIG_MAN_PHOTO.get(randomKey);
			small = IcoDictionary.SMALL_MAN_PHOTO.get(randomKey);
		}
		map.put("big", big);
		map.put("small", small);
		return map;
	}
}
