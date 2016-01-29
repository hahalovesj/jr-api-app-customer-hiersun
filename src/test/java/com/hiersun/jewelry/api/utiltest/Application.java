package com.hiersun.jewelry.api.utiltest;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Application {

    private static String APP_PATH ="";
    private static String CONFIG_PATH;
    public static ApplicationContext applicationContext;
    
    public static boolean isOpeningTime = false;

	public static void init() {

		init(true);
	}

	public static void init(Boolean initLotoConfig) {

		initPathAndProperties(null);
		applicationContext = createApplicationContext();

	}
	
	public static void init(ApplicationContext appContext, String log4jProperties, Boolean initLotoConfig) {

		initPathAndProperties(log4jProperties);
		applicationContext = appContext;

	}
	
    private static ApplicationContext createApplicationContext() {    	
        try {
    		ApplicationContext appContext = new ClassPathXmlApplicationContext(
    				new String[] {"classpath:config/spring_mvc.xml"
    				});
        	return appContext;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
	private static void initPathAndProperties(String log4jProperties) {
		try {
			initPath();
			PropertyConfigurator.configure(null == log4jProperties ? (getCONFIG_PATH() + "test-log4j.properties") : log4jProperties);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static String getAPP_PATH() {
   		initPath();
        return APP_PATH;
    }

    public static String getCONFIG_PATH() {
   		initPath();
        return CONFIG_PATH;
    }
    
    private static void initPath() {
    	synchronized (APP_PATH) {
			if ("".equals(APP_PATH)) {
		    	APP_PATH = System.getProperty("user.dir") + "/";
		    	CONFIG_PATH = APP_PATH + "/target/classes/";
			}
		}
    }    
    
}
