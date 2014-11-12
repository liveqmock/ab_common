package com.feihuwang.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class HttpClientUtil {

	private static final Log log = LogFactory.getLog(HttpClientUtil.class);
	
	private MultiThreadedHttpConnectionManager httpClientManager;

	private HttpClient httpClient;

	public static HttpClientUtil getInstance(){
		return InstanceHolder._instance;
	}
	
	private static class InstanceHolder{
		public static HttpClientUtil _instance = new HttpClientUtil();
	}

	private HttpClientUtil() {
		init();
	}

	private void init() {

		httpClientManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = httpClientManager.getParams();
		params.setStaleCheckingEnabled(true);
		params.setMaxTotalConnections(800);
		params.setDefaultMaxConnectionsPerHost(500);

		httpClient = new HttpClient(httpClientManager);
		httpClient.getParams().setParameter("http.socket.timeout", 1500);
		httpClient.getParams().setParameter("http.connection.timeout", 1500);
		httpClient.getParams().setParameter("http.connection-manager.timeout", 20000L);
	}
	
	public String getResponseAsString(String url) throws IOException {
		if (StringUtils.isBlank(url)) {
			log.error("args error when call http requst;url=" + url);
			return null;
		}
		BufferedReader in = null;
		InputStream is = null;
		GetMethod getMethod = null;
		int statusCode = 0;
		try {
			getMethod = new GetMethod(url);
			getMethod.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.executeMethod(getMethod);
			statusCode = getMethod.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new IOException("HTTP request error status: " + getMethod.getStatusLine() + " " + url);
			}
			is = getMethod.getResponseBodyAsStream();
			StringBuilder buffer = new StringBuilder();
			in = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				buffer.append(inputLine);
			}
			
			return buffer.toString();
		} catch (IOException e) {
			log.error("Net work exception caused by " + e.getMessage() + "; url: " + url, e);
			throw e;
		} finally {
			log.info("HttpClient get " + url + " [" + statusCode + "] ");
			if (null != getMethod) {
				getMethod.releaseConnection();
			}
			try {
				if (null != is) {
					is.close();
				}
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}

	}
	

	public static void main(String[] args) throws Exception{
		try {
			System.out.println(HttpClientUtil.getInstance().getResponseAsString("http://wwww.baidu.com?&id=21&p=1&s=20"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(HttpClientUtil.getInstance().getResponseAsString("http://www.baidu.com"));
	}
}
