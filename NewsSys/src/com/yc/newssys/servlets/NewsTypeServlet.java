package com.yc.newssys.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yc.newssys.dao.INewsTypeDao;
import com.yc.newssys.entity.NewsType;
import com.yc.newssys.impl.NewsTypeDaoImpl;

@SuppressWarnings("serial")
public class NewsTypeServlet extends CommonServlet {
	private INewsTypeDao newsTypeDao=new NewsTypeDaoImpl();
	private JSONArray json;
	private JSONObject jb;
	private PrintWriter out;
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String op=request.getParameter("op");
		
		out=response.getWriter();
		
		if("getPageNewsTypeInfo".equals(op)){//分页查询新闻类型信息
			getPageNewsTypeInfo(request,response);
		}else if("addNewsTypeInfo".equals(op)){ //添加新闻类型
			addNewsTypeInfo(request,response);
		}else if("delNewsTypeInfo".equals(op)){  //删除新闻类型
			delNewsTypeInfo(request,response);
		}else if("updateNewsTypeInfo".equals(op)){ //修改新闻类型
			updateNewsTypeInfo(request,response);
		}else if("getAllNewsType".equals(op)){  //所有的新闻类型
			getAllNewsType(request,response);
		}
	}
	
	
	private void getAllNewsType(HttpServletRequest request,HttpServletResponse response) {
		
		List<NewsType> list =newsTypeDao.finds();
		json=JSONArray.fromObject(list);
		jb=new JSONObject();
		jb.put("rows", json);
		
		out.print(jb.toString());
		out.flush();
		out.close();
	}


	private void updateNewsTypeInfo(HttpServletRequest request,HttpServletResponse response) {
		String tname=request.getParameter("tname");
		String status=request.getParameter("status");
		String tid=request.getParameter("tid");
		if(newsTypeDao.update(new NewsType(Integer.parseInt(tid),tname,Integer.parseInt(status)))>0){ //修改成功
			out.print(1);
		}else{//修改失败
			out.print(0);
		}
		out.flush();
		out.close();
	}


	/**
	 * 删除新闻类型
	 * @param request
	 * @param response
	 */
	private void delNewsTypeInfo(HttpServletRequest request,HttpServletResponse response) {
		String tid=request.getParameter("tids");
		if(newsTypeDao.del(tid)>0){ //删除成功
			out.print(1);
		}else{//删除失败
			out.print(0);
		}
		out.flush();
		out.close();
	}
	
	
	/**
	 * 添加新闻类型
	 * @param request
	 * @param response
	 */
	private void addNewsTypeInfo(HttpServletRequest request,HttpServletResponse response) {
		String tname=request.getParameter("tname");
		if(newsTypeDao.addNewsType(new NewsType(1,tname,1))>0){ //添加成功
			out.print(1);
		}else{//添加失败
			out.print(0);
		}
		out.flush();
		out.close();
	}
	
	/**
	 * 分页查询新闻类型信息
	 * @param request
	 * @param response
	 */
	private void getPageNewsTypeInfo(HttpServletRequest request,HttpServletResponse response) {
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		List<NewsType> Newstype=newsTypeDao.find(Integer.parseInt(page), Integer.parseInt(rows));
		
		json=JSONArray.fromObject(Newstype);
		jb=new JSONObject();
		jb.put("total", newsTypeDao.total());
		jb.put("rows", json);
		
		out.print(jb.toString());
		out.flush();
		out.close();
	}

}
