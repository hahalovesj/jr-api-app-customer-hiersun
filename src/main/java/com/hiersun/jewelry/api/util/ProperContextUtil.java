package com.hiersun.jewelry.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.ws.rs.client.Client;

public class ProperContextUtil {

	public static Properties getProper(String porperName) {
		Properties properties = new Properties();

		return null;
	}

	public String getValue(String key) {

		Properties properties = new Properties();
		InputStream inputStream = getClass().getResourceAsStream(("/menu.properties"));
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
		InputStreamReader ipIn = null;
		String value = "";
		Properties ip;
		try {
			ip = new Properties();
			ipIn = new InputStreamReader(Client.class.getClassLoader().getResourceAsStream(url), "UTF-8");

			if (ipIn != null) {
				ip.load(ipIn);
				value = ip.getProperty(key);
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
