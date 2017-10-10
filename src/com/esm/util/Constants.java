package com.esm.util;

import java.io.InputStream;
import java.util.Properties;

public class Constants {
	static {
		InputStream in = null;
		try {
			Properties pro = new Properties();
			in = Constants.class.getResourceAsStream("/config.properties");
			pro.load(in);
			setAppKey((String) pro.get("appKey"));
			setAppSecret((String) pro.get("appSecret"));
			setTbUrl((String) pro.get("tbUrl"));
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(in != null) {
					in.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * appKey.
	 */
	private static String appKey;
	
	/**
	 * appSecret.
	 */
	private static String appSecret;
	
	private static String tbUrl;

	public static String getAppKey() {
		return appKey;
	}

	private static void setAppKey(String appKey) {
		Constants.appKey = appKey;
	}

	public static String getAppSecret() {
		return appSecret;
	}

	private static void setAppSecret(String appSecret) {
		Constants.appSecret = appSecret;
	}

	public static String getTbUrl() {
		return tbUrl;
	}

	private static void setTbUrl(String tbUrl) {
		Constants.tbUrl = tbUrl;
	}
}
