package com.feihuwang.constants;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	private final static String PRODUCT_HOST = "182.92.160.36";
	private final static String DEVELOP_HOST = "127.0.0.1";
	private final static String PRODUCT_PORT = "8080";
	private final static String DEVELOP_PORT = "8080";
	private final static String PRODUCT_CONTEXT = "/"; // 应用上下文
	private final static String DEVELOP_CONTEXT = "/ab_mobile/"; // 应用上下文
	public final static String LOCAL_DEBUG = "127"; // 本地调试标记
	public final static String VIRTUAL_DEBUG = ""; // 虚拟调试标记
	
	public final static String AUTH_KEY = ""; // 忽略，不需要传递
	public final static String DEBUG = "debug"; // 调试参数，调试时候可以传递，用户获取假报文
	public final static String PLAT = "plat"; // 平台标记参数，需要传递
	public final static String KEY_URL = "key_url"; // 接口标记参数，需要传递
	public final static String PRODUCT_PATH = "http://" + PRODUCT_HOST + ":" + PRODUCT_PORT + PRODUCT_CONTEXT;
	public final static String DEVELOP_PATH = "http://" + DEVELOP_HOST + ":" + DEVELOP_PORT + DEVELOP_CONTEXT;
	
	/**
	 * 平台参数标记，必填参数
	 */
	public static Map<String, String> PLAT_MAP = new HashMap<String, String>();
	
	/**
	 * 栏目接口标记，必填参数，比如 key_url = "KEY_CHANNEL_LIST";
	 */
	public static Map<String, String> KEY_MAP = new HashMap<String, String>(); 
	
	static {
		PLAT_MAP.put("0", "0"); // pc端
		PLAT_MAP.put("1", "1"); // html5端
		PLAT_MAP.put("6", "6"); // android
		
		KEY_MAP.put("KEY_CHANNEL_ALL", "channel/all.json"); // 栏目列表接口
		KEY_MAP.put("KEY_CHANNEL_CONTENT", "channel/content.json"); // 栏目内容列表接口
	}
}
