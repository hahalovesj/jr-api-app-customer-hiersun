package com.hiersun.jewelry.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProperContextUtil {

	public static Properties getProper(String porperName) {
		Properties properties = new Properties();
		
		return null;
	}

	public String getValue(String key) {

		Properties properties = new Properties();
		InputStream inputStream = getClass().getResourceAsStream("/menu.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(properties.getProperty("a"));
		return key;
	}

	public String getValue(String properName, String key) {
		String url = "context/" + properName;
		InputStream ipIn = null;
		String value = "";
		Properties ip;
		try {
			ip = new Properties();
			ipIn = getClass().getClassLoader().getResourceAsStream(url);
			if (ipIn != null) {
				ip.load(ipIn);
				value = ip.getProperty(key);
			} else {
				System.out.println("没有" + properName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ipIn != null)
					ipIn.close();
			} catch (IOException e) {
			}
		}
		return value;
	}

}
