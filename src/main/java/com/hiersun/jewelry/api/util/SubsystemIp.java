package com.hiersun.jewelry.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SubsystemIp {

	public static String subsystem_ip = "context/sms.properties";

	private Properties ip;

	private static class SingletonHolder {
		private static SubsystemIp instance = new SubsystemIp();
	}

	private SubsystemIp() {
		InputStream ipIn = null;
		try {
			ip = new Properties();
			ipIn = getClass().getClassLoader().getResourceAsStream(subsystem_ip);
			if (ipIn != null) {
				ip.load(ipIn);
			} else {
				System.out.println("没有sms.properties");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (ipIn != null)
					ipIn.close();
			} catch (IOException e) {
			}
		}
	}

	public static SubsystemIp getInstance() {
		return SingletonHolder.instance;
	}

	public String getValue(String key) {
		String value = ip.getProperty(key);
		return treatVar(value);
	}

	private String treatVar(String value) {
		int start = -1, end = -1;
		if (!isEmpty(value) && (start = value.indexOf("${")) != -1 && (end = value.substring(start, value.length()).indexOf("}")) != -1) {
			String rkey = value.substring(start + 2, end + start);
			String rvalue = ip.getProperty(rkey);
			if (!isEmpty(rvalue)) {
				value = value.replaceAll("\\$" + "\\{" + rkey + "\\}", rvalue);
				value = treatVar(value);
			}
		}
		return value;
	}
	
	private boolean isEmpty(String str){
		return str == null || str.trim().length() == 0;
	}

	public static void main(String[] args) throws IOException {

		System.out.println(SubsystemIp.getInstance().getValue("tel"));
	}
}
