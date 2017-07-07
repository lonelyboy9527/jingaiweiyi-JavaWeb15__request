package com.itheima.register;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;

import com.itheima.utils.DataSourceUtils;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 *  客户端 王五(UTF-8)------>传送给服务端
		 *  服务端 ISO8859-1解码---->乱码
		 *  解决方法:
		 *  所以我们在服务端逆向来， 将获得先进行 ISO8859-1编码------>再使用UTF-8解码----->正常
		 * */
		
		
		//设置request的编码---只适合post方式
		request.setCharacterEncoding("UTF-8");
		
		//get方式的乱码解决(也适用post)
		//get方式采用这种方式需要对每一个字段进行遍历设置
		//post可以按照上面直接设置setCharacterEncoding解决
		String username = request.getParameter("username");//乱码
		//先用ISO8859编码，再使用UTF-8解码，得到的username就是正常的
		username = new String(username.getBytes("ISO8859-1"), "UTF-8");
		
		//1.获取数据
		//
		//request.getParameter("password");
		//...
		
		//2.将散装数据封装到javaBean中
		
		//使用BeanUtils进行自动映射封装
		//BeanUtils工作原理，将map中的数据根据key与实体的属性的对应关系封装
		//只要key名字与属性名字一样，自动封装到实体中 
		User user = new User();
		Map<String, String[]> properties = request.getParameterMap();
		try {
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		//现在这个位置，user对象已经封装好了
		//手动封装uid---uuid---随机不重复的字符串32位---java代码生成后是36位的
		user.setUid(UUID.randomUUID().toString());
		
		//3.将参数传递给一个业务操作方法
		try {
			register(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//4.认为注册成功，跳转到登录页面
		//request.getRequestDispatcher("/login.jsp").forward(request, response);//地址栏不会变
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}
	
	//注册的方法
	public void register(User user) throws SQLException {
		//操作数据库
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO user values(?,?,?,?,?,?,?,?,?,?)";
		runner.update(sql, user.getUid(),user.getUsername(),user.getPassword(), user.getName(), user.getEmail(), user.getTelephone(), user.getBirthday(), user.getSex(),user.getState(), user.getCode() );
	}
}
