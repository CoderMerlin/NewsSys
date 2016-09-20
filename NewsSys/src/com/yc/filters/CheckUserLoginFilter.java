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

public class CheckUserLoginFilter implements Filter {
	private String errorPage="index.html"; //错误页面，即如果没有校验成功，则跳到该页面
	/**
	 * 销毁的方法
	 */
	public void destroy() {
		
	}

	/**
	 * 过滤的方法
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		//检查该用户是否已经登录
		HttpServletRequest request= (HttpServletRequest) arg0;
		HttpServletResponse response= (HttpServletResponse) arg1;
		
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){//说明没有登录
			PrintWriter out=response.getWriter();
			//获取基地址路径，即到WebRoot 下
			String basePath =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
			out.print("<script>alert('请先登录！！！'); location.href='"+basePath+errorPage+"';</script>");
		}else{//说明已经登录成功，则调用下一个过滤器
			arg2.doFilter(arg0, arg1);
		}
	}

	/**
	 * 初始化方法
	 */
	public void init(FilterConfig arg0) throws ServletException {
		String temp=arg0.getInitParameter("errorPage");
		if(temp!=null){ //说明配置了初始化页面信息
			errorPage=temp;
		}
	}

}
