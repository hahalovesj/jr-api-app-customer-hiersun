package com.hiersun.jewelry.api.util;

/**
 * 生成验证码
 * @author lilong
 *
 */
public class Trunc {
	public static long getRandomForRange(long start, long end) {
		return (long) (Math.random() * (end - start) + start + 1);
	}

	public static void main(String[] args) {
		System.out.println(getRandomForRange(100000l, 999999));
	}
}
