package com.hiersun.jewelry.api.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class ValidateUtil {

	public static boolean objsIsEmpty(List<Object> obgList) {
		for (Object obj : obgList) {
			if (StringUtils.isEmpty(obj)) {
				return false;
			}
		}
		return true;

	}
	/**
	 * 验证6位数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[0-9]{6}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

}
