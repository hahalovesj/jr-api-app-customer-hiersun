package com.hiersun.jewelry.api.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	// protected final static Log _log = LogFactory.getLog(DateUtil.class);
	private static SimpleDateFormat dateformater;

	static java.text.SimpleDateFormat sdfShort = new java.text.SimpleDateFormat("yyyyMMdd");
	static java.text.SimpleDateFormat sdfLong = new java.text.SimpleDateFormat("yyyy-MM-dd");
	static java.text.SimpleDateFormat sdfLongTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
	static java.text.SimpleDateFormat sdfLongTimePlus = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static java.text.SimpleDateFormat sdfLongTimePlusMill = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSSS");
	private static long DAY_IN_MILLISECOND = 0x5265c00L;

	/**
	 * 获取时间戳
	 * 
	 * @return
	 */
	public static long getTimeStamp() {
		return System.currentTimeMillis();
	}

	/**
	 * @param date
	 * @param format
	 *            日期格式
	 * @return String
	 * @return String
	 */
	public static String dateToStr(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			System.out.println("Date �?String 类型失败: " + e);
			return null;
		}
	}

	/**
	 * 把字符型日期表达式转换成java.util.Date
	 * 
	 * @param strDate
	 *            型如 "yyyy-M-dd" 的日期字符串
	 * @return 转换后的java.util.Date对象；转换失败返回null
	 */
	public Date parseDate(String strDate) {
		Date date = null;
		try {
			date = getDateFormater().parse(strDate);
		} catch (Exception ex) {
			// System.err.println(ex.getMessage());
		}
		return date;
	}

	public Date parseDate(String strDate, String formate) {
		Date date = null;
		try {
			date = new SimpleDateFormat(formate).parse(strDate);
		} catch (Exception ex) {
			// System.err.println(ex.getMessage());
		}
		return date;
	}

	private DateFormat getDateFormater() {
		if (dateformater == null)
			dateformater = new SimpleDateFormat("yyyy-M-dd");
		return dateformater;
	}

	/**
	 * @author zhangyong
	 * @return DATE 型加具体的天�?
	 * 
	 * @param Date
	 *            date, int days
	 */
	public static Date dateAddDays(Date date, int days) {
		long now = date.getTime() + (long) days * DAY_IN_MILLISECOND;
		return new Date(now);
	}

	/**
	 * @return 将DATE 转换成字符�?日期格式
	 * @param Date
	 *            date,String fFormatStr eg:yyyy-MM-dd HH:mm:ss
	 */
	public static String dateTypeToString(Date date, String fFormatStr) {
		// yyyy-MM-dd HH:mm:ss
		SimpleDateFormat dateformat = new SimpleDateFormat(fFormatStr);
		String strDate = dateformat.format(date);
		return strDate;
	}

	/**
	 * @param yyyy
	 *            -MM-dd
	 * @获取当前的系统时间，并按照固定的格式初始�?/ public static String getStringOfNowDate(String
	 *                          fFormatStr){ String
	 *                          nowDateString=dateTypeToString(new
	 *                          Date(),fFormatStr); return nowDateString; }
	 * 
	 *                          /**@param yyyy-MM-dd
	 * @获取当前的系统时间，并按照固定的格式初始�?/ public static Date getDateOfNowDate(String
	 *                          fFormatStr){ String
	 *                          nowDateString=dateTypeToString(new
	 *                          Date(),fFormatStr); return new
	 *                          DateUtil().parseDate(nowDateString); }
	 * 
	 *                          /** @ author zhangyong @ 获取当月的第�?���?009-05-01
	 */
	public static String getStringOfFirstDayInMonth() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String temp = sdf.format(date);
		String firstDayInMoth = "";
		firstDayInMoth = temp + "-01";

		return firstDayInMoth;

	}

	/**
	 * @ 获取当月的第�?���?009-05-01
	 */
	public static Date getDateOfFirstDayInMonth() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String temp = sdf.format(date);
		String firstDayInMoth = "";
		firstDayInMoth = temp + "-01";

		return new DateUtil().parseDate(firstDayInMoth);

	}

	/**
	 * Descrption:取得当前日期,格式�?yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 * @throws java.lang.Exception
	 */
	public static String getPlusTime2(Date date) {

		if (date == null)
			return null;
		try {
			String nowDate = sdfLongTimePlus.format(date);
			return nowDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获得指定日期是星期几
	 * 
	 * 2015-3-11
	 * 
	 * @author Li.Long modifier:
	 * @param dt
	 * @return
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 指定时间前的hours小时mm分钟
	 * 
	 * 2015-3-27
	 * 
	 * @author Li.Long modifier:
	 * @param date
	 * @param hours
	 * @param mm
	 * @return
	 */
	public static Date beforeSomeHour(Date date, int hours, int mm) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.HOUR, -hours);
			c.add(Calendar.MINUTE, -mm);
			date = c.getTime();
			return date;
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
	}

}
