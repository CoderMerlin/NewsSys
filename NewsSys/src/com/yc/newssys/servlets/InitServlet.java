package com.yc.newssys.servlets;


import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.yc.newssys.utils.UploadUtil;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public void destroy(){
		super.destroy();
	}
	
	
	public void init() throws ServletException {  //初始化方法
		String uploadPath="../uploadPic";
		
		//说明web.xml中配置初始化参数名为uploadPath 的参数
		if(this.getInitParameter("uploadPath")!=null){
			uploadPath=this.getInitParameter("uploadPath");
		}
		
		//应该判断该路径是否存在，如果不存在则应该创建一下
		File file =new File(this.getServletContext().getRealPath(uploadPath));
		
		//如果目录不存在，则创建
		if(!file.exists()){
			file.mkdirs();  //创建多级目录
		}
		
		//修改UploadUtil中 文件保存路径
		UploadUtil.PATH=uploadPath;
	}

}
