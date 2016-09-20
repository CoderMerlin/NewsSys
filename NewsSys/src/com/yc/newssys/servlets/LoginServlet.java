package com.yc.newssys.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.newssys.dao.IAdminDao;
import com.yc.newssys.dao.IUsersDao;
import com.yc.newssys.entity.Admin;
import com.yc.newssys.entity.Users;
import com.yc.newssys.impl.AdminDaoImpl;
import com.yc.newssys.impl.UsersDaoImpl;
import com.yc.newssys.utils.SessionAttributeKey;

@SuppressWarnings("serial")
public class LoginServlet extends CommonServlet {

	private IUsersDao usersDao=new UsersDaoImpl();
	private IAdminDao adminDao=new AdminDaoImpl();
	private HttpSession session;
	private PrintWriter out;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");
		out=response.getWriter();
		session=request.getSession();
		
		if("adminLogin".equals(op)){ //管理员登录操作
			adminLogin(request,response);
		}else if("adminloginout".equals(op)){ //注销管理员
			adminloginout(request,response);
		}else if("userloginout".equals(op)){ //会员注销
			userloginout(request,response);
		}else if("checkuseruname".equals(op)){ //检查会员注册的名称
			checkuseruname(request,response);
		}else if("userzc".equals(op)){//会员注册
			userzc(request,response);
		}else if("checkuseremail".equals(op)){  //邮箱验证
			checkuseremail(request,response);
		}else if("adminzc".equals(op)){//管理员注册
			adminzc(request,response);
		}else if("checkcodeimg".equals(op)){ //会员注册时的验证码验证
			checkcodeimg(request,response);
		}else if("checkadminpwd".equals(op)){ //检查管理员密码是否真确
			checkadminpwd(request,response);
		}else if("changeadminpwd".equals(op)){ //修改管理员密码
			changeadminpwd(request,response);
		}

	}
	
	
	
	//改变管理员密码
	private void changeadminpwd(HttpServletRequest request,HttpServletResponse response) {
		String pwd=request.getParameter("pwd");
		Admin loginadmin= (Admin) session.getAttribute("loginAdmin");
		if(adminDao.updateAdmin(new Admin(loginadmin.getAid(),loginadmin.getAname(),pwd))>0){ //修改成功
			out.print(1);
		}else{//修改失败
			out.print(0);
		}
		out.flush();
		out.close();
	}




	//检查管理员密码是否真确
	private void checkadminpwd(HttpServletRequest request,HttpServletResponse response) {
		String pwd=request.getParameter("pwd");
		Admin loginadmin= (Admin) session.getAttribute("loginAdmin");
		
		Admin admin= adminDao.find(loginadmin.getAid(),pwd);
		
		if(admin!=null){
			out.print(1);
		}else{
			out.print(0);
		}
		out.flush();
		out.close();
		
	}




	//会员注册时的验证码验证
	private void checkcodeimg(HttpServletRequest request,HttpServletResponse response) {
		String zccode=request.getParameter("zccode");  //获取输出的验证码
		String sessioncode=(String) session.getAttribute("rand");  //获取随机生成验证码  由点击图片产生的
		//判断是否相等
		if(zccode.equals(sessioncode)){
			out.print(1);
		}else{
			out.print(0);
		}
		out.flush();
		out.close();
	}



	//管理员注册
	private void adminzc(HttpServletRequest request, HttpServletResponse response) {
		String aname=request.getParameter("uname");
		String pwd=request.getParameter("rpwd");
		int admin=adminDao.addAdmin(new Admin(1,aname,pwd));
		if(admin>0){ //添加成功
			out.print("<script>alert('注册成功！请重新登录！');location.href='login.jsp'</script>");
		}else{//添加失败
			out.print("<script>alert('注册失败！请重新注册！');location.href='login.jsp'</script>");
		}
		out.flush();
		out.close();
	}
	

	//会员注册
	private void userzc(HttpServletRequest request, HttpServletResponse response) {
		String uname=request.getParameter("zcuname");
		String pwd=request.getParameter("zcpwd");
		String email=request.getParameter("zcemail");
		
		if(usersDao.addUsers(new Users(1,uname,pwd,email,1))>0){ //添加成功
			out.print(1);
		}else{//添加失败
			out.print(0);
		}
		out.flush();
		out.close();
	}

	//邮箱验证
	private void checkuseremail(HttpServletRequest request,HttpServletResponse response) {
		String email=request.getParameter("zcemail");
		
		Users users=usersDao.findZC("email",email);
		if(users==null){
			out.print(1);
		}else{
			out.print(0);
		}
		out.flush();
		out.close();
	}


	//检查会员注册的名称
	private void checkuseruname(HttpServletRequest request,HttpServletResponse response) {
		String uname=request.getParameter("uname");
		
		
		Users users=usersDao.findZC("uname",uname);
		
		if(users==null){
			out.print(1);
		}else{
			out.print(0);
		}
		out.flush();
		out.close();
	}



	//会员注销
	private void userloginout(HttpServletRequest request,HttpServletResponse response) {
		session.removeAttribute("loginUser");
		
		out.print(1);
		out.flush();
		out.close();
	}


	//注销管理员
	private void adminloginout(HttpServletRequest request,HttpServletResponse response) {
		session.removeAttribute("loginAdmin");
		
		out.print(1);
		out.flush();
		out.close();
	}
	//管理员登录
	private void adminLogin(HttpServletRequest request,HttpServletResponse response) {
		String aname=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		String acode=request.getParameter("vcode");  //获取输入的验证码
		String sessioncode=(String) session.getAttribute("rand");  //获取随机生成验证码  由点击图片产生的
		
		Admin admin=adminDao.login(new Admin(1,aname,pwd));
		
		if(acode.equals(sessioncode)){
			if(admin !=null){
				//先存入session
				session.setAttribute(SessionAttributeKey.FRONTLOGINADMIN, admin);
				try {
					response.sendRedirect("back/index.jsp");
				} catch (IOException e) {
					e.printStackTrace();
				}
				out.print(admin.getAname());
			}else{
				out.print("<script>alert('您输入的用户名或密码错误，请重新输入...');location.href='login.jsp'</script>");
			}
		}else{
			out.print("<script>alert('请确认您的验证码正确后再点击登录....');location.href='login.jsp'</script>");
		}
		
		out.flush();
		out.close();
		
	}

}
