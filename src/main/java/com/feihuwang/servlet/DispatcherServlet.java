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
		response.setContentType("text/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String keyUrl = request.getParameter("key_url");//key_url标记
		String api_key = "";
		String url = Constants.PATH + keyUrl + "&" + api_key;
		String result = HttpClientUtil.getInstance().getResponseAsString(url);
		out.write(result);
		out.flush();
		//请求转发
		//request.getRequestDispatcher("/WxSubclaus?id=" + subClauseId).forward(request, response);
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