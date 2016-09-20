package com.yc.newssys.utils;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;

/**
 * 上传
 * @author Administrator
 *
 */
public class UploadUtil {
	public static  String PATH="upload"; //默认的图片保存路径
	private static final String ALLOWED="gif,png,jpg,jpeg"; //允许上传的格式
	private static final String DENIED="exe,bat,jsp,com"; //不可以上传的格式
	private static final int SINGLEFILESIZE=1024*1024; //单个文件最大值
	private static final int TOTALSIZE=1024*1024*20; //每次允许上传的最大大小
	private static final String CHARSETNAME="UTF-8"; //编码集
	
	public Map<String, String> upload(PageContext pageContext) throws Exception{
		Map<String, String> map=new HashMap<String, String>();
		SmartUpload su=new SmartUpload();
		
		//初始化
		su.initialize(pageContext);
		su.setAllowedFilesList(ALLOWED);
		su.setDeniedFilesList(DENIED);
		su.setMaxFileSize(SINGLEFILESIZE);
		su.setTotalMaxFileSize(TOTALSIZE);
		su.setCharset(CHARSETNAME);
		
		su.upload(); //开始上传
		
		Request request=su.getRequest();
		
		//获取所有的表单元素的name属性的值
		@SuppressWarnings("unchecked")
		Enumeration<String> enums=request.getParameterNames();
		String filedNmae;
		while(enums.hasMoreElements()){
			filedNmae=enums.nextElement();
			map.put(filedNmae, request.getParameter(filedNmae));
		}
		
		Files files=su.getFiles(); //获取要上传的文件
		String photo="";
		if(files!=null && files.getCount()>0){
			String fileName;
			@SuppressWarnings("unchecked")
			Collection<File> list=files.getCollection();
			for(File fl:list){ //循环要上传的文件
				if(!fl.isMissing()){ //如果丢失文件
					fileName=PATH+"/"+System.currentTimeMillis()+new Random().nextInt(10000)+"."+fl.getFileExt();
					
					//保存到服务器
					fl.saveAs(fileName, SmartUpload.SAVE_VIRTUAL);
					photo+=fileName+","; //存入数据库的图片路径
				}
			}
			
			if(photo.indexOf(",")>0){
				photo=photo.substring(0,photo.lastIndexOf(",")); //除掉最后一个,号
			}
		}
		map.put("photo", photo);
		return map;
	}
}
