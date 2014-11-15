package com.feihuwang.constants;

public class Constants {
	private final static String HOST = "182.92.160.36";
	private final static String PORT = "8080";
	public final static String PATH = "http://" + HOST + PORT + "/ab_mobile";
	/**
	 * 平台参数标记，必填参数
	 */
	public final static int PLAT = 6; // android：6，html5:1，pc：0
	
	/**
	 * 栏目列表接口
	 */
	public final static String KEY_CHANNEL_LIST = "/channel/all/{0}.json";
}
