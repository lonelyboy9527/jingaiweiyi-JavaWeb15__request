package com.itheima.login;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.register.User;
import com.itheima.utils.DataSourceUtils;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		//1.获取用户名字和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//2.调用一个业务方法进行该用户查询
		User login = null;
		try {
			login = login(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//3.通过user是否为空，判断是否正确
		if (login != null) {
			//跳转网站首页
			response.sendRedirect(request.getContextPath() ); // 可以不写+ "/index.jsp"，默认跳转
		} else {
			//跳回当前页login.jsp
			//使用转发，可以带一些错误信息过来login.jsp
			request.setAttribute("loginInfo", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
	
	public User login(String username,String password) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM user WHERE username=? AND password=?";
		User user = runner.query(sql, new BeanHandler<User>(User.class), username, password);
		return user;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}
}
