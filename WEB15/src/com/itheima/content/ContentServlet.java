package com.itheima.content;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ContentServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//**********下面的方法，不管是get还是post请求，都可以用**********
		
		//1.获取单个表单值
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("username:" + username);
		System.out.println("password:" + password);
		
		//2.获得多个表单的值
		String[] hobbys = request.getParameterValues("hobby");
		for (String hobby : hobbys) {
			System.out.println("hobby:" + hobby);
		}
		System.out.println("-------------------");
		//3.获得请求参数的所有名称
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			System.out.println(parameterNames.nextElement());
		}
		System.out.println("-------------------");
		//4.获得所有的参数，参数封装到map中<string, string[]>
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry:parameterMap.entrySet()) {
			System.out.println(entry.getKey());
			for (String value : entry.getValue()) {
				System.out.println(value);
			}
			System.out.println("-------------------");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}
}
