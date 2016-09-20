package com.yc.newssys.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.newssys.dao.IUsersDao;
import com.yc.newssys.entity.Users;
import com.yc.newssys.impl.UsersDaoImpl;
import com.yc.newssys.utils.SessionAttributeKey;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class UsersServlet extends CommonServlet {
	private HttpSession session;
	private JSONArray json;
	private JSONObject jb;
	private PrintWriter out;
	private IUsersDao usersDao=new UsersDaoImpl(); //会员
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session=request.getSession();
		out=response.getWriter();
		
		String op=request.getParameter("op");
		
		if("userLogin".equals(op)){  //会员登录
			userLogin(request,response);
		}else if("getPageUsersInfo".equals(op)){ //会员信息
			getPageUsersInfo(request,response);
		}else if("updateUsersInfo".equals(op)){ //修改会员信息
			updateUsersInfo(request,response);
		}else if("delUsersInfo".equals(op)){ //删除会员信息
			delUsersInfo(request,response);
		}else if("checkUsers".equals(op)){  //验证会员登录
			checkUsers(request,response);
		}else if("addUsersInfo".equals(op)){ //添加会员信息
			addUsersInfo(request,response);
		}else if("userLogin".equals(op)){
			userLogin(request,response);  //会员登录
		}
		
		
	}


	/**
	 * 添加会员
	 * @param request
	 * @param response
	 */
	private void addUsersInfo(HttpServletRequest request,HttpServletResponse response) {
		String uname=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		String email=request.getParameter("email");
		String status=request.getParameter("status");
		if(usersDao.addUsers(new Users(1,uname,pwd,email,Integer.parseInt(status)))>0){ //添加成功
			out.print(1);
		}else{//添加失败
			out.print(0);
		}
		out.flush();
		out.close();
	}


	/**
	 * 验证会员登录
	 * @param request
	 * @param response
	 */
	private void checkUsers(HttpServletRequest request,HttpServletResponse response) {
		String uname=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		if(usersDao.login(new Users(0,uname,pwd,"",0))==null){ //说明失败
			out.print(0);
		}else{//说明成功
			out.print(1);
		}
		out.flush();
		out.close();
	}


	/**
	 * 删除会员信息
	 * @param request
	 * @param response
	 */
	private void delUsersInfo(HttpServletRequest request,HttpServletResponse response) {
		String usid=request.getParameter("usids");
		if(usersDao.del(usid)>0){ //删除成功
			out.print(1);
		}else{//删除失败
			out.print(0);
		}
		out.flush();
		out.close();
	}


	/**
	 * 修改会员信息
	 * @param request
	 * @param response
	 */
	private void updateUsersInfo(HttpServletRequest request,HttpServletResponse response) {
		String uname=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		String usid=request.getParameter("usid");
		String email=request.getParameter("email");
		String status=request.getParameter("status");
		if(usersDao.updateUsers(new Users(Integer.parseInt(usid),uname,pwd,email,Integer.parseInt(status)))>0){ //修改成功
			out.print(1);
		}else{//修改失败
			out.print(0);
		}
		out.flush();
		out.close();
	}


	/**
	 * 会员信息
	 * @param request
	 * @param response
	 */
	private void getPageUsersInfo(HttpServletRequest request,HttpServletResponse response) {
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		List<Users> users=usersDao.find(Integer.parseInt(page), Integer.parseInt(rows));
		json=JSONArray.fromObject(users);
		jb=new JSONObject();
		jb.put("total", usersDao.total());
		jb.put("rows", json);
		
		out.print(jb.toString());
		out.flush();
		out.close();
	}


	/**
	 * 会员登录
	 * @param request
	 * @param response
	 */
	private void userLogin(HttpServletRequest request,HttpServletResponse response) {
		String uname=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		
		Users users=usersDao.login(new Users(1,uname,pwd,"",0));
		if(users !=null){
			//先存入session
			session.setAttribute(SessionAttributeKey.FRONTLOGINUSER, users);
			out.print(users.getUname());
		}else{
			out.print(1);
		}
		out.flush();
		out.close();
	}

}
