package com.yc.newssys.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yc.newssys.dao.INewsDao;
import com.yc.newssys.dao.INewsTypeDao;
import com.yc.newssys.entity.News;
import com.yc.newssys.entity.NewsType;
import com.yc.newssys.impl.NewsDaoImpl;
import com.yc.newssys.impl.NewsTypeDaoImpl;
import com.yc.newssys.utils.UploadUtil;

@SuppressWarnings("serial")
public class NewsServlet extends CommonServlet {
	private INewsDao newsDao=new NewsDaoImpl();
	private INewsTypeDao newsTypeDao=new NewsTypeDaoImpl();
	private JSONArray json;
	private JSONObject jb;
	private PrintWriter out;
	private HttpSession session;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String op=request.getParameter("op");
		
		out=response.getWriter();
		
		session=request.getSession();
		if("getPageNewsInfo".equals(op)){//分页查询新闻信息
			getPageNewsInfo(request,response);
		}else if("addNewsInfo".equals(op)){ //添加新闻信息
			addNewsInfo(request,response);
		}else if("findNewsByNid".equals(op)){
			findNewsByNid(request,response);
		}else if("delNewsInfo".equals(op)){ //删除新闻
			delNewsInfo(request,response);
		}else if("indexDataInfo".equals(op)){  //首页新闻数据
			indexDataInfo(request,response);
		}else if("showNewsByNid".equals(op)){ 
			showNewsByNid(request,response);
		}else if("findNewsByInfo".equals(op)){  //多条件复合查询
			findNewsByInfo(request,response);
		}else if("updateNewsInfo".equals(op)){ //修改新闻数据
			updateNewsInfo(request,response);
		}
	}

	
	//修改新闻
	private void updateNewsInfo(HttpServletRequest request,HttpServletResponse response) {
		UploadUtil uploadUtil=new UploadUtil();
		PageContext pageContext=JspFactory.getDefaultFactory().getPageContext(this,request,response,null,true,1024, true);
		
		try {
			Map<String,String> map =uploadUtil.upload(pageContext);
			int result=newsDao.update(new News(Integer.parseInt(map.get("nid")),map.get("title"),map.get("ndate"),map.get("content"),map.get("auth"),map.get("photo"),Integer.parseInt(map.get("tid")),0,Integer.parseInt(map.get("weight")),map.get("tname")));
			out.print(result);
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//多条件组合查询
	private void findNewsByInfo(HttpServletRequest request,HttpServletResponse response) {
		String title=request.getParameter("title");
		String ndate=request.getParameter("ndate");
		String tid=request.getParameter("tid");
		
		int page=Integer.parseInt(request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		
		Map <String,String> map=new HashMap<String,String>();
		if(tid!=null && !"".equals(tid) && !"0".equals(tid)){
			map.put("n.tid", tid);
		}
		
		List<News> list =newsDao.find(title,ndate,map,page,rows);
		
		json=JSONArray.fromObject(list);
		jb=new JSONObject();
		jb.put("total", newsDao.total());
		jb.put("rows", json);
		
		out.print(jb.toString());
		out.flush();
		out.close();
	}

	private void showNewsByNid(HttpServletRequest request,HttpServletResponse response) {
		String nid=request.getParameter("nid");
		News news=newsDao.find(Integer.parseInt(nid));
		session.setAttribute("currentNews", news);
		try {
			response.sendRedirect("front/shownew.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 首页数据
	 * @param request
	 * @param response
	 */
	private void indexDataInfo(HttpServletRequest request,HttpServletResponse response) {
		//查询所有的新闻类型
		List<NewsType> newsTypes =newsTypeDao.finds();
		session.setAttribute("types", newsTypes);
		
		//所有新闻
		List<News> news=newsDao.findIndex(1, 25);
		session.setAttribute("allNews", news);
		
//		List<News> guoNeiNews=newsDao.find("国内",1,11);
//		session.setAttribute("guoNeiNews", guoNeiNews);
//		
//		List<News> guoJiNews=newsDao.find("国际",1,11);
//		session.setAttribute("guoJiNews", guoJiNews);
//		
//		List<News> yuLeNews=newsDao.find("娱乐",1,11);
//		session.setAttribute("yuLeNews", yuLeNews);
		
		List<News> typeNews=newsDao.find("国内,国际,娱乐",1,11);
    	session.setAttribute("typeNews", typeNews);

		out.print("1");
		out.flush();
		out.close();
		
//		try {
//			response.sendRedirect("front/index.jsp");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		json=JSONArray.fromObject(newsTypes);
//		jb=new JSONObject();
//		jb.put("types", json);
//		
//		out.print(jb.toString());
//		out.flush();
//		out.close();
	}

	/**
	 * 删除新闻
	 * @param request
	 * @param response
	 */
	private void delNewsInfo(HttpServletRequest request,HttpServletResponse response) {
		String nid=request.getParameter("nids");
		if(newsDao.del(nid)>0){  //删除成功
			out.print(1);
		}else{//删除失败
			out.print(0);
		}
	}

	private void findNewsByNid(HttpServletRequest request,HttpServletResponse response) {
		int nid=Integer.parseInt(request.getParameter("nid"));
		
		News news=newsDao.find(nid);
		jb=new JSONObject();
		jb.put("rows", news);
		
		out.print(jb.toString());
		out.flush();
		out.close();
	}

	/**
	 * 添加新闻信息
	 * @param request
	 * @param response
	 */
	private void addNewsInfo(HttpServletRequest request,HttpServletResponse response) {
		UploadUtil uploadUtil=new UploadUtil();
		PageContext pageContext=JspFactory.getDefaultFactory().getPageContext(this,request,response,null,true,1024, true);
		
		try {
			Map<String,String> map =uploadUtil.upload(pageContext);
			int result=newsDao.addNews(new News(1,map.get("title"),map.get("ndate"),map.get("content"),map.get("auth"),map.get("photo"),Integer.parseInt(map.get("tid")),0,Integer.parseInt(map.get("weight")),map.get("tname")));
			out.print(result);
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
		/*String tid=request.getParameter("tid");
		String tname=request.getParameter("tname");//新闻类型名
		String title=request.getParameter("title"); //标题
		String auth=request.getParameter("auth");  //作者
		String ndate=request.getParameter("ndate"); //日期
		String content=request.getParameter("content"); //内容
		String weight=request.getParameter("weight"); //权重
		
		
		
		News news=new News(1,title,ndate,content,auth,null,Integer.parseInt(tid),0,Integer.parseInt(weight),tname);
		if(newsDao.addNews(news)>0){ //添加成功
			out.print(1);
		}else{ //添加失败
			out.print(0);
		}
		out.flush();
		out.close();*/


	/**
	 * 分页查询新闻信息
	 * @param request
	 * @param response
	 */
	private void getPageNewsInfo(HttpServletRequest request,HttpServletResponse response) {
		int page=Integer.parseInt(request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		
		List<News> list =newsDao.find(page,rows);
		json=JSONArray.fromObject(list);
		jb=new JSONObject();
		jb.put("total", newsDao.total());
		jb.put("rows", json);
		
		out.print(jb.toString());
		out.flush();
		out.close();
	}

}
