package com.feihuwang.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.feihuwang.constants.Constants;
import com.feihuwang.http.HttpClientUtil;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = -2388152467479022418L;
	private static Log log = LogFactory.getLog(DispatcherServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		String result = "";
		String rKeyUrl = request.getParameter("key_url"); //接口标记
		String rPlat = request.getParameter("plat"); //平台标记
		String keyUrl = Constants.KEY_MAP.get(rKeyUrl);
		String plat = Constants.PLAT_MAP.get(rPlat);
		
		if (rKeyUrl != null && rPlat != null && keyUrl != null && plat != null) {
			String params = request.getParameter("params");//参数标记标记
			String url = Constants.PATH + keyUrl + "?auth_key=" + Constants.AUTH_KEY + "&plat=" + plat + "&" + params;
			result = HttpClientUtil.getInstance().getResponseAsString(url);
		}
		out.write(result);
		out.flush();
		//request.getRequestDispatcher("/WxSubclaus?id=" + subClauseId).forward(request, response); //请求转发
	}
	
	//获取客户端请求真实IP
    public String getIpAddr(HttpServletRequest request) {      
    	String ip = request.getHeader("x-forwarded-for");      
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
    		ip = request.getHeader("Proxy-Client-IP");      
    	}      
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
    		ip = request.getHeader("WL-Proxy-Client-IP");      
    	}      
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
    		ip = request.getRemoteAddr();      
    	}      
    	return ip;      
    } 
}