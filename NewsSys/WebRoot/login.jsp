<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>用户登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width,initial-scale=1"/>
<link rel="stylesheet" href="easyui/css/bootstrap.min.css">
<link rel="stylesheet" href="easyui/css/login.css">
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script src="easyui/js/bootstrap.min.js"></script>
<script src="js/login.js" charset="utf-8"></script>


<script>
	$(function(){ 
		$(".container").css("position","fixed").css("top",($(window).height()-$(".container").height())/2)
		.css("left",($(window).width()-$(".container").width())/2);
		
		$('.close-button').click(function(){
			$(this).parent().removeClass("slidePageInFromLeft").addClass("slidePageBackLeft");
		});
		
		$(window).resize(function(){
			$(".container").css("position","fixed").css("top",($(window).height()-$(".container").height())/2)
			.css("left",($(window).width()-$(".container").width())/2);
		});
			
	});
	
	
	//登录
	function login(id,role) {
    	var flag = $("#"+id).text();
    	$("#"+role).val(flag);
	}
	
	function showRegisterPage(){
		$(".register-page").addClass("slidePageInFromLeft").removeClass("slidePageBackLeft");
	}
	
	function backlogin(){
		$(".register-page").removeClass("slidePageInFromLeft").addClass("slidePageBackLeft");
	}
		
    //产生随机验证码
    function showMyCodeImg(obj){
    	obj.src="valiCodeImg.jsp?d="+new Date();
   	}
			

</script>
</head>

<body>
	<div class="container">
		<div class="row">
            <div class="col-md-5 col-md-offset-3">
            	<div class="panel">
                	<div class="panel-heading login-top">用户登录</div>
                    <div class="panel-body">
                    	<form class="form-group col-lg-10 col-md-offset-1" action="loginServlet?op=adminLogin" method="post" role="form"  >
                			<div class="input-group">
								<div class="input-group-btn">
									<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">角&nbsp;&nbsp;色<span class="caret"></span></button>
									<ul class="dropdown-menu">
										<li><a id="role1" href="javascript:login('role1','loginrole')">学工处</a></li>
										<li><a id="role2" href="javascript:login('role2','loginrole')">保卫处</a></li>
										<li><a id="role3" href="javascript:login('role3','loginrole')">书记</a></li>
										<li><a id="role4" href="javascript:login('role4','loginrole')">辅导员</a></li>
                                        <li><a id="role5" href="javascript:login('role5','loginrole')">超级管理员</a></li>
									</ul>
								</div>
								<input id="loginrole" type="text" class="form-control" disabled="disabled" required placeholder="请选择角色"/>							<label class="input-group-addon"><a href="" class="errorinfo">忘记密码...</a></label>
							</div>
                            <br />
                            <div class="input-group">
                            	<label for="uname" class="input-group-addon">用户名</label>
                                <input type="text" class="form-control" name="uname" id="uname" required placeholder="请输入用户名"/>
                            </div>
                            <br />
                            <div class="input-group">
                            	<label for="pwd" class="input-group-addon">密&nbsp;&nbsp;&nbsp;码</label>
                                <input type="password" class="form-control" name="pwd" id="pwd" required  placeholder="请输入密码"/>
                            </div>
                            <br />
                            <div class="input-group">
                            	<label for="vcode" class="input-group-addon">验证码</label>
                                <input type="text" class="form-control" name="vcode" id="vcode"  onblur="checkvcode()" required placeholder="请输入右边的验证码"/>
                                <label class="input-group-btn"><a><img src="valiCodeImg.jsp" id="MyCodeImg" onclick="showMyCodeImg(this)"/></a></label>
                                <p id="vcodep" style="color:#F00">请输入验证码</p>
                            </div>
                            <br/>
                            <div class="input-group">
                            	<input type="submit" value="登陆" class="btn btn-success mybtn" />
                                <input type="reset" value="重置" class="btn btn-warning mybtn"/>
                            </div>
                            <br/>
                            <div class="input-group">
                            	<input type="button" value="注册" onClick="showRegisterPage()" class="btn btn-danger user-register" />
                            </div>
                		</form>
                    </div>
                    <div class="panel-footer login-footer">源辰信息 &copy; 版权所有</div>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
	</div>
    
    <div class="register-page">
		<div class="close-button">X</div>
        <div class="register col-lg-6">
            <div class="panel">
                <div class="panel-heading login-top">用户注册</div>
                <div class="panel-body">
                    <form class="form-group col-lg-12" action="loginServlet?op=adminzc"  method="post" role="form">
                        <div class="input-group">
                            <div class="input-group-btn">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">角&nbsp;&nbsp;色<span class="caret"></span></button>
                                <ul class="dropdown-menu">
                                    <li><a id="role1" href="javascript:login('role1','role')">学工处</a></li>
                                    <li><a id="role2" href="javascript:login('role2','role')">保卫处</a></li>
                                    <li><a id="role3" href="javascript:login('role3','role')">书记</a></li>
                                    <li><a id="role4" href="javascript:login('role4','role')">辅导员</a></li>
                                    <li><a id="role5" href="javascript:login('role5','role')">超级管理员</a></li>
                                </ul>
                            </div>
                            <input id="role" type="text" class="form-control" disabled="disabled" required placeholder="请选择角色"/>							<label class="input-group-addon"><a href="javascript:backlogin()" class="errorinfo">我已有账号...</a></label>
                        </div>
                        <br />
                        <div class="input-group">
                            <label for="rname" class="input-group-addon">用户名</label>
                            <input type="text" class="form-control" name="uname" id="rname" required placeholder="请输入用户名" maxlength="12"/ >								<label class="input-group-addon promptinfo">由2-12位的中文、字母、数字和下划线组成</label>
                        </div>
                        <br />
                        <div class="input-group">
                            <label for="rpwd" class="input-group-addon">密&nbsp;&nbsp;&nbsp;码</label>
                            <input type="password" class="form-control" name="rpwd" id="rpwd" required  placeholder="请输入密码" maxlength="16"/>
                            <label class="input-group-addon promptinfo">由6-16位的字母、数字和下划线组成</label>
                        </div>
                        <br />
                        <div class="input-group">
                            <label for="rpwds" class="input-group-addon">确认密码</label>
                            <input type="password" class="form-control" name="rpwds" id="rpwds" required placeholder="请再输入一次密码，以确认" maxlength="16"/>
                            <label class="input-group-addon promptinfo">请再输入一次密码，以确认</label>
                        </div>
                        <br/>
                        <div class="input-group">
                            <label for="email" class="input-group-addon">邮&nbsp;&nbsp;&nbsp;箱</label>
                            <input type="email" class="form-control" name="email" id="email" required placeholder="请输入您的邮箱账号"/>
                            <label class="input-group-addon promptinfo">请输入邮箱账号，以便忘记密码时找回</label>
                        </div>
                        <br/>
                        <div class="input-group">
                            <label for="tel" class="input-group-addon">联系方式</label>
                            <input type="number" class="form-control" name="tel" id="tel" maxlength="12" required placeholder="请输入您联系方式"/>
                            <label class="input-group-addon promptinfo">请输您的联系方式</label>
                        </div>
                        <br/>
                        <div class="input-group">
                            <input type="submit" value="注册" class="btn btn-success user-register" />
                            <input type="reset" value="重置" class="btn btn-danger mybtn"/>
                        </div>
                     </form>
                </div>
                <div class="panel-footer login-footer">源辰信息 &copy; 版权所有</div>
            </div>
        </div>
	</div>
</body>
</html>
