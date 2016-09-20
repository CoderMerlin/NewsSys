package com.yc.newssys.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yc.newssys.dao.IAdminDao;
import com.yc.newssys.entity.Admin;
import com.yc.newssys.impl.AdminDaoImpl;

@SuppressWarnings("serial")
public class AdminServlet extends CommonServlet {
	private IAdminDao adminDao=new AdminDaoImpl();
	private JSONArray json;
	private JSONObject jb;
	private PrintWriter out;
	private HttpSession session;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		session=request.getSession();
		String op=request.getParameter("op");  //操作
		
		out=response.getWriter();
		
		if("getPageAdminInfo".equals(op)){//分页查询管理员信息
			getPageAdminInfo(request,response);
		}else if("addAdminInfo".equals(op)){ //添加管理员信息
			addAdminInfo(request,response);
		}else if("delAdminInfo".equals(op)){  //删除管理员信息
			delAdminInfo(request,response);
		}else if("updateAdminInfo".equals(op)){ //修改管理员信息
			updateAdminInfo(request,response);
		}
	}

	
	

	


	//修改管理员
	private void updateAdminInfo(HttpServletRequest request,HttpServletResponse response) {
		String aname=request.getParameter("aname");
		String pwd=request.getParameter("pwd");
		String aid=request.getParameter("aid");
		if(adminDao.updateAdmin(new Admin(Integer.parseInt(aid),aname,pwd))>0){ //修改成功
			out.print(1);
		}else{//修改失败
			out.print(0);
		}
		out.flush();
		out.close();
	}


	private void delAdminInfo(HttpServletRequest request,HttpServletResponse response) {
		String aid=request.getParameter("aids");
		if(adminDao.del(aid)>0){ //删除成功
			out.print(1);
		}else{//删除失败
			out.print(0);
		}
		out.flush();
		out.close();
	}


	private void addAdminInfo(HttpServletRequest request,HttpServletResponse response) {
		String aname=request.getParameter("aname");
		String pwd=request.getParameter("pwd");
		if(adminDao.addAdmin(new Admin(1,aname,pwd))>0){ //添加成功
			out.print(1);
		}else{//添加失败
			out.print(0);
		}
		out.flush();
		out.close();
	}


	/**
	 * 分页查询管理员信息
	 * @param request
	 * @param response
	 */
	private void getPageAdminInfo(HttpServletRequest request,HttpServletResponse response) {
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		List<Admin> admin=adminDao.find(Integer.parseInt(page), Integer.parseInt(rows));
		
		json=JSONArray.fromObject(admin);
		jb=new JSONObject();
		jb.put("total", adminDao.total());
		jb.put("rows", json);
		
		out.print(jb.toString());
		out.flush();
		out.close();
	}

}
