<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
<title>新闻中国</title>
<base href="<%=basePath%>">
<%@include file="header.jsp" %>

<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/login.css" />

<style>
	.youtu{
		display:inline-block;
		background:url("images/tu.png") no-repeat right center;
		padding-right:18px;
	}
</style>
<script>
	/*$.post("newsServlet?d="+new Date(),{op:"indexDataInfo"},function(data){
		var str="<a href=\"javascript:showNewsByTid(-1)\">全部</a>";
		$.each(data.types,function(index,item){
			str+="<a href=\"javascript:showNewsByTid('"+item.tid+"')\">"+item.tname+"</a>";
		});
		$("#class_month").append( $(str) );
	},"json");*/
</script>
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
    	<div class="sidebar">
        	<div class="side_list">
          		<img src="images/title_1.gif" />
                <ul class="left_new">
                	<c:forEach items="${typeNews}" var="item"> 
                		<c:if test="${item.tname eq '国内' }">
                			<%-- ><li><a href="newsServlet?op=showNewsByNid&nid=${item.nid }" title="${item.title }">${item.title10 }</a></li>--%>
                			<li><a href="newsServlet?op=showNewsByNid&nid=${item.nid }" target="_blank" title="${item.title }">${item.title10 }</a></li>
                		</c:if>
                	</c:forEach>
                </ul>
            </div>
            <div class="side_list">
          		<img src="images/title_2.gif" />
                 <ul class="left_new">
                	<c:forEach items="${typeNews}" var="item"> 
                		<c:if test="${item.tname eq '国际' }">
                			<li><a href="newsServlet?op=showNewsByNid&nid=${item.nid }" target="_blank" title="${item.title }">${item.title10 }</a></li>
                		</c:if>
                	</c:forEach>
                </ul>
            </div>
            <div class="side_list">
          		<img src="images/title_3.gif" />
                 <ul class="left_new">
                	<c:forEach items="${typeNews}" var="item"> 
                		<c:if test="${item.tname eq '娱乐' }">
                			<li><a href="newsServlet?op=showNewsByNid&nid=${item.nid }" target="_blank" title="${item.title }">${item.title10 }</a></li>
                		</c:if>
                	</c:forEach>
                </ul>
            </div>
        </div>
        <div class="main">
        	<div class="class_type">
            	<img src="images/class_type.gif"/>
           	</div>
            <div class="content">
            	<div class="class_date" id="class_month">
            		<a href="javascript:showNewsByTid('-1')">全部</a>
            		<c:forEach items="${types }" var="item">
            			<a href="javascript:showNewsByTid('${item.tid }')">${item.tname }</a>
            		</c:forEach>
               </div>
               <div class="classlist">
               		<ul>
               			<c:forEach items="${allNews }" var="item" varStatus="i">
               				<li>
               					<c:choose>
               						<c:when test="${not empty item.pic }">
               							<a href="newsServlet?op=showNewsByNid&nid=${item.nid }" target="_blank" title="${item.title }" class="youtu">${item.title20 }</a>
               						</c:when>
               						<c:otherwise>
               							<a href="newsServlet?op=showNewsByNid&nid=${item.nid }" target="_blank" title="${item.title }">${item.title20 }</a>
               						</c:otherwise>
               					</c:choose>
               				<span>${item.ndates }</span></li>
               				<c:if test="${i.count!=0 and i.count%5==0 }">
               					<li class="space"></li>
               				</c:if>
               			</c:forEach>
                        <li class="space"><span>当前页数：[1/2] <a href="#">下一页</a> <a href="#">末页</a></span></li>
                    </ul>
               </div>
            </div>
            <div class="picnews">
            	<c:set value="0" var="count"></c:set>
            	<c:forEach items="${allNews }" var="item" varStatus="i">
            		<c:if test="${not empty item.pic and count<4}">
            			<c:set value="${count+1 }" var="count"></c:set>
            			<li><img src="${item.firstPic }" width="250px" height="160px"/><a href="newsServlet?op=showNewsByNid&nid=${item.nid }" target="_blank" title="item.title">${item.title10 }</a></li>
            		</c:if>
            	</c:forEach>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>
