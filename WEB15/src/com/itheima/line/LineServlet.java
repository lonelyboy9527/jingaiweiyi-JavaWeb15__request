package com.itheima.line;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LineServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取请求方式
		String method = request.getMethod();
		System.out.println("method:" + method);
		//2.获得请求的资源相关的内容
		String requestURI = request.getRequestURI();
		StringBuffer requestURL = request.getRequestURL();
		System.out.println("requestURI:" + requestURI);
		System.out.println("requestURL:" + requestURL);
		//获得web应用名称
		String contextPath = request.getContextPath();
		System.out.println("contextPath:" + contextPath);
		//地址后的参数的字符串
		String queryString = request.getQueryString();
		System.out.println("queryString:" + queryString);
		//3.获得客户机的信息---获得ip地址
		String remoteAddr = request.getRemoteAddr();
		System.out.println("ip:" + remoteAddr);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}
}
