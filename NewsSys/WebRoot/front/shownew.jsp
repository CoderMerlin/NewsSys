<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
	<base href="<%=basePath%>">
	<link rel="stylesheet" type="text/css" href="css/shownew.css" />
	<link rel="stylesheet" type="text/css" href="css/login.css" />
	<%@include file="header.jsp" %>
</head>

<body>
	<div id="bg" class="bg" style="display:none;"></div>
	<%@include file="login.jsp" %>
	<div id="header">
    	<div id="top_login">
    		<div class="top_login_left">
					<ul>
						<li><a href="javascript:void(0);"
							onclick="SetHome(this,'http://www.hao23.com');">设为首页</a>
						</li>
						<li><a href="javascript:void(0);"
							onclick="AddFavorite('Navy新闻网',location.href)">点击收藏</a>
						</li>
						<li><a href="javascript:writerToLetter()" title="">联系我们</a>
						</li>
					</ul>
			</div>
    		<div id="top_login_right">
	    		<c:if test="${not empty loginUser }">
    				<a href="#"><font color="#FF0000" size="2">当前登录用户：${loginUser.uname }</font></a>&nbsp;&nbsp;
					<a href="javascript:loginOut()"><font color="#FF0000" size="2">[注销]</font></a>
    			</c:if>
    			<c:if test="${empty loginUser }">
		    		<a href="javascript:showlogin()"><font color="#FF0000" size="2">请登录</font></a>
					&nbsp;&nbsp;
					<a href="javascript:showzc()"><font color="#FF0000" size="2">免费注册</font></a>
    			</c:if>
			</div>
        	
        <div id="nav">
        	<div id="logo"><img src="images/logo.jpg" /></div>
            <div><img src="images/a_b01.gif" style="margin:10px 0px 0px 2px;"/></div>
        </div>
     </div>
    </div>
    <div id="container">
        <div class="content">
            <div class="new_header">
                <h1>${currentNews.title}</h1>
                <span class="new_header_left">作者：${currentNews.auth }</span>
                <span class="new_header_right">发布日期：${currentNews.ndate }</span>
            </div>
            <div>
            	<c:if test="${not empty currentNews.pic  }">
            		${currentNews.picStr }
            	</c:if>
            	${currentNews.content }
            </div>
        </div>
        <div class="picnews">
            <li><img src="images/Picture1.jpg"/><a href="#">幻想中穿却时空</a></li>
            <li><img src="images/Picture2.jpg"/><a href="#">庆多亮的发型</a></li>
            <li><img src="images/Picture3.jpg"/><a href="#">新技术照亮都市</a></li>
            <li><img src="images/Picture4.jpg"/><a href="#">群星闪耀红地毯</a></li>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>
