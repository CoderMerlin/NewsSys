 $(function(){
	 $('#index_content').tabs('add',{
		title:'迈腾科技信息',
		selected:true,
		closable:false,
		href:"mt.jsp"
	 });
	 
	 $("#menu-tree").tree({
		onClick:function(node){
			var obj=$('#index_content');
			if(node.id=="admin_manager"){  //说明是管理员信息管理
				//判断管理员信息管理界面是否打开
				if(obj.tabs("exists","管理员信息管理")){  //则选中
					obj.tabs("select","管理员信息管理");
				}else{
					obj.tabs('add',{
						title:'管理员信息管理',
						closable:true,
						fit:true,
						href:"admin.jsp"
						});
				}
			}else if(node.id=="type_manager"){  //说明是新闻类型信息管理
				//判断新闻类型管理界面是否打开
				if(obj.tabs("exists","新闻类型管理")){  //则选中
					obj.tabs("select","新闻类型管理");
				}else{
					obj.tabs('add',{
						title:'新闻类型管理',
						closable:true,
						fit:true,
						href:"newstype.jsp"
					});
				}
			}else if(node.id=="add_newsInfo"){  //说明是添加新闻
				//判断添加新闻界面是否打开
				if(obj.tabs("exists","添加新闻")){  //则选中
					obj.tabs("select","添加新闻");
				}else{
					obj.tabs('add',{
						title:'添加新闻',
						closable:true,
						fit:true,
						href:"news.jsp"
					});
				}
			}else if(node.id=="show_news_info"){  //说明是浏览新闻
				//判断浏览新闻界面是否打开
				if(obj.tabs("exists","浏览新闻")){  //则选中
					obj.tabs("select","浏览新闻");
				}else{
					obj.tabs('add',{
						title:'浏览新闻',
						closable:true,
						fit:true,
						href:"show.jsp"
					});
				}
			}else if(node.id=="users_manager"){  //说明是会员信息管理
				//判断会员信息是否打开
				if(obj.tabs("exists","会员信息管理")){  //则选中
					obj.tabs("select","会员信息管理");
				}else{
					obj.tabs('add',{
						title:'会员信息管理',
						closable:true,
						fit:true,
						href:"users.jsp"
					});
				}
			}else if(node.id=="change_pwd"){  //说明是修改密码
				//判断修改密码是否打开
				    var pwd=prompt("请输入您的密码","");
				    if (pwd!=null && pwd!="") {
				       $.post("../loginServlet?d="+new Date(),{op:"checkadminpwd",pwd:pwd},function(data){
				    	   if(data==1){ //密码是正确的
				    		   $("#changepwd").dialog("open");
				    	   }else{
				    		   alert("密码错误！");
				    	   }
				       });
				}
			}
		} 
	 });
 });
 
 
//注销管理员
 function loginOut(){
 	$.post("../loginServlet?d="+new Date(),{op:"adminloginout"},function(data){
 		if(data==1){//成功
 			location.href='../login.jsp';
 		}else{//说明失败
 			alert("注销失败....");
 		}
 	});

 }
 
 //修改管理员密码
 function changeadminpwd(){
	 var pwd=$.trim($("#changepwda").val());
	 var rpwd=$.trim($("#rchangepwd").val());
	 if(pwd==rpwd){
		 $.post("../loginServlet?d="+new Date(),{op:"changeadminpwd",pwd:pwd},function(data){
			 if(data==1){//成功
		 			alert("修改成功...");
		 			$("#changepwd").dialog("close");
					$("#news_info").datagrid("reload");
		 		}else{//说明失败
		 			alert("注销失败....");
		 			$("#changepwd").dialog("close");
		 	 }
		 });
	 }
 }
 

 