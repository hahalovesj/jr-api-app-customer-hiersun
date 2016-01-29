package com.hiersun.jewelry.api;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.junit.Before;

import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.utiltest.Application;


public class MyContextUnitilsJUnit extends MyUnitilsJUnit {

	static {
		Application.init();
	}
	
	@Before
	public void onMyContextUnitilsJUnitSetUp() {
		applicationContext = Application.applicationContext;
	}
	

	/**
	 * 读取文件内容
	 * 
	 * @param clz			测试类
	 * @param fileName		要读取的文件名
	 * @return
	 * @throws Exception	异常信息
	 */
	protected String readFile(Class<?> clz, String fileName) throws Exception {
		return FileUtils.readFileToString(new File(
				clz.getResource("").getPath().substring(1)  + fileName), "UTF-8");
	}
	
	/**
	 * 删除特殊字符(\t \r \n)
	 * 
	 * @param str	字符串
	 */
	protected String deleteSpecialChar(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	
}
