package com.feihuwang.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

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

	@SuppressWarnings("rawtypes")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String result = "";
		String rKeyUrl = request.getParameter(Constants.KEY_URL); //接口标记
		String rPlat = request.getParameter(Constants.PLAT); //平台标记
		String keyUrl = Constants.KEY_MAP.get(rKeyUrl);
		String plat = Constants.PLAT_MAP.get(rPlat);
		
		if (rKeyUrl != null && rPlat != null && keyUrl != null && plat != null) {
			Enumeration enu = request.getParameterNames();  
			StringBuffer paramsSb = new StringBuffer();
			while(enu.hasMoreElements()){  
				String paraName = (String)enu.nextElement();  
				if (!Constants.KEY_URL.equals(paraName) && !Constants.PLAT.equals(paraName)) {
					paramsSb.append("&" + paraName + "=" + request.getParameter(paraName));
				}
			} 
			String url = Constants.PATH + keyUrl + "?auth_key=" + Constants.AUTH_KEY + "&plat=" + plat + paramsSb.toString();
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