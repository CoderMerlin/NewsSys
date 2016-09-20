package com.yc.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.newssys.utils.SessionAttributeKey;

public class CheckAdminLoginFilter implements Filter {
	private String errorPage="login.jsp"; //错误页面，即如果没有校验成功，则跳到该页面
	private HttpSession session;
	private PrintWriter out;
	//销毁的方法
	public void destroy() {
		System.out.println("检查管理员登录的过滤器已被销毁");
	}

	//过滤的方法
	public void doFilter(ServletRequest arg0, ServletResponse arg1,FilterChain arg2) throws IOException, ServletException {
		System.out.println("检查管理员的方法正在过滤");
		//检查管理员是否登录
		HttpServletRequest request=(HttpServletRequest) arg0;  
		HttpServletResponse response=(HttpServletResponse) arg1;  
		
		session=request.getSession();
		if(session.getAttribute(SessionAttributeKey.FRONTLOGINADMIN)==null){  //说明没有登录
			out=response.getWriter();
			//获取基地址路径，即到WebRoot 下
			String basePath =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
			out.print("<script>alert('请先登录！！！'); location.href='"+basePath+errorPage+"';</script>");
			out.flush();
		}else{//说明已经登录成功，则调用下一个过滤器
			arg2.doFilter(arg0, arg1);
		}
	}

	//初始化的方法
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("过滤管理员的方法正在初始化....");
		String temp=arg0.getInitParameter("errorPage");
		if(temp!=null){ //说明配置了初始化页面信息
			errorPage=temp;
		}
	}

}
