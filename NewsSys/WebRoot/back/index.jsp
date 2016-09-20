<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>新闻管理系统</title>
	<link rel="stylesheet" type="text/css" href="../easyui/css/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/css/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/css/demo.css">
	<link rel="stylesheet" type="text/css" href="../css/index.css">
	<link rel="shortcut icon" type="image/x-icon" href="../images/yc2.png">
	
	<script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="../easyui/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="../easyui/js/index.js"></script>
	
	<script type="text/javascript" src="../js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="../js/showpic.js"></script>
	
	<!--编译器 -->
	<script type="text/javascript" src="../ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="../ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" src="../ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"  id="newslogo">
		<div id="top_adminlogin_right">
			<c:if test="${not empty loginAdmin }">
    				<a href="#"><font color="#FF0000" size="2">当前登录用户：${loginAdmin.aname }</font></a>&nbsp;&nbsp;
					<a href="javascript:loginOut()"><font color="#FF0000" size="2">[注销]</font></a>
    			</c:if>
    			<c:if test="${empty loginAdmin }">
		    		<a href="javascript:showlogin()"><font color="#FF0000" size="2">请登录</font></a>
					&nbsp;&nbsp;
					<a href="javascript:showzc()"><font color="#FF0000" size="2">免费注册</font></a>
    			</c:if>
		</div>
	</div>
	<div data-options="region:'west',split:true,title:'导航'" style="width:200px;padding:10px;">
		<ul class="easyui-tree" id="menu-tree">
		<li>
			<span>菜单</span>
			<ul>
				<li id="admin_manager">管理员信息管理</li>
				<li id="type_manager">新闻类型管理</li>
				<li id="users_manager">会员信息管理</li>
				<li data-options="state:'open'">
					<span>新闻管理</span>
					<ul>
						<li id="add_newsInfo">
							<span>添加新闻</span>
						</li>
						<li id="show_news_info">
							<span>浏览新闻</span>
						</li>
						<li>
							<span>修改新闻</span>
						</li>
					</ul>
				</li>
			
				<li id="change_pwd">修改密码</li>
			</ul>
		</li>
	</ul>
	</div>
	<div data-options="region:'east',split:true,collapsed:true,title:'帮助'" style="width:100px;padding:0px;margin:0px;">
		<img alt="图片加载失败" src="../images/weixin.jpg" width="90px" height="90px">
	</div>
	<div data-options="region:'south',border:false" style="background:#A9FACD; margin:0px;padding:10px; " id="copyright">
		<p> <a href="http://www.maitenginfo.com">迈腾科技股份有限公司</a> &copy; 版权所有 15886480862</p>
	</div>
	<div data-options="region:'center',title:'内容'">
		<div id="index_content" class="easyui-tabs" data-options="fit:true">
		
		</div>
	</div>
	
	<!-- 详细 -->
<div id="news_show_newsInfo" class="easyui-dialog" title="新闻详细" data-options="fit:true,iconCls:'icon-search1',resizable:true,modal:true,closed:true">
	<form action="" style="padding:20px;float:lect;display:inline-block;">
		<label>新闻类型:</label><input type="text" id="news_tname_show" class="myinput" readonly="readonly"/><br /><br />
		<label>新闻标题:</label><input type="text" name="title" id="news_title_show" class="myinput" readonly="readonly"/><br /><br />
		<label>发布日期:</label><input type="ndate" name="ndate" id="news_ndate_show" class="myinput" readonly="readonly"/><br /><br />
		<label>新闻作者:</label><input type="text" name="auth" id="news_auth_show" class="myinput" readonly="readonly"/><br /><br />
		<label>新闻权重:</label><input class="myinput" name="weight" id="news_weight_show" readonly="readonly"/><br /><br />
		<label>浏览次数:</label><input class="myinput" name="view" id="news_view_show" readonly="readonly"/><br /><br />
		<label>新闻内容:</label>
		<div id="news_content_show">
		</div><br /><br />
	</form>
	<div style="float:right;width:380px;margin-right:20px; margin-top:20px;" id="news_pic_show_info">
	
	</div>
</div>
<script>
	//新闻详细
	function showNewsDetail(nid){
		$("#news_show_newsInfo").dialog("open");
		$.post("../newsServlet",{op:"findNewsByNid",nid:nid},function(data){
			var news=data.rows;
			$("#news_tname_show").val(news.tname);
			$("#news_title_show").val(news.title);
			$("#news_ndate_show").val(news.ndate);
			$("#news_auth_show").val(news.auth);
			$("#news_weight_show").val(news.weight);
			$("#news_view_show").val(news.views);
			$("#news_content_show").html(news.content);
			
			var str="";
			
			var pics=news.pic.split(",");
			for(var i=0; i<pics.length;i++){
				str+="<img src='../"+pics[i]+"' width='100px' height='100px' />&nbsp;";
			}
			$("#news_pic_show_info").html($(str));
		},"json");
	}
</script>

 <div id="changepwd" class="easyui-dialog" title="修改密码" data-options="iconCls:'icon-search1',resizable:true,modal:true,closed:true" style="width:250px; height:150px;">
 	<label>修改密码:</label><input type="password" name="changepwda" id="changepwda" class="myinput"/><br /><br />
	<label>密码确认:</label><input type="password" name="rchangepwd" id="rchangepwd" class="myinput"/><br /><br />
	<button onclick="changeadminpwd()">确认</button>
 </div>
</body>
</html>