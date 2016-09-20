<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<table id="news_info"></table>
<script>	
	var datagrid;
	
	  $.post("../newsTypeServlet",{op:"getAllNewsType"},function(data){
		  var obj=$("#news_newstype");
		  var obj1=$("#news_update_newstype");
		  var opt;
		  $.each(data.rows,function(index,item){
			  opt="<option value="+item.tid+">"+item.tname+"</option>";
			  obj.append($(opt));
			  obj1.append($(opt));
		  });
	  },"json");

	 datagrid=$('#news_info').datagrid({   
		    url:'../newsServlet', 
		    queryParams:{op:"getPageNewsInfo"},
		    fitColumns:true,
		    striped:true,
		    loadMsg:"数据加载中...",
		    pagination:true,
		    rownumbers:true,
		    columns:[[   
		        {field:'nids',title:'选择',width:100,align:'center',checkbox:true},   
		        {field:'nid',title:'新闻编号',width:100,align:'center',sortable:true},   
		        {field:'title',title:'新闻标题',width:100,align:'center'},  
		        {field:'auth',title:'作者',width:100,align:'center'},
		        {field:'views',title:'浏览次数',width:100,align:'center'},
		        {field:'_operate',title:'操作',width:100,align:'center',
		        	formatter:function(value,rowData,index){
		        		//console.info(JSON.stringify(rowDate));  //将对象转换成字符串
		        		//console.info(JSON.parse(rowData));  //将字符串转换成对象
		        		return '<a class="icon-search1 icon-padding" href="javascript:showNewsDetail(\''+rowData.nid+'\')">详细</a>';
		        	}
		        }
		    ]],
		    toolbar:[{   
		        text:"添加",   
		        iconCls:'icon-add',   
		        handler:function(){   
		          $("#news_add_newsInfo").dialog("open");
		        }
		    },{
		    	text:"修改",
		    	iconCls:'icon-edit',
		    	handler:function(){
					//获取要修改的行
					var rows=datagrid.datagrid("getChecked")[0];
					if(rows==undefined){
						$.messager.show({title:'温馨提示',msg:'请选择您要修改的新闻信息！',timeout:2000,showType:'slide'});
					}else{
						//显示要修改的新闻
						$("#news_update_newsInfo").dialog("open");
						var nid=rows.nid; //获取要修改的id
						var ue1=UE.getEditor('editor1');
						$.post("../newsServlet",{op:"findNewsByNid",nid:nid},function(data){
							var news=data.rows;
							$("#news_update_newstype").val(rows.tid);
							$("#news_update_newstype").val;
							$("#news_title_update").val(news.title);
							$("#news_ndate_update").datebox('setValue',news.ndates);
							$("#news_auth_update").val(news.auth);
							$("#news_weight_update").val(news.weight);
							$("#news_view_update").val(news.views);
							$("#news_pic_update").html(news.pic);
							
							ue1.setContent(news.content);
							var str="";
							
							var pics=news.pic.split(",");
							for(var i=0; i<pics.length;i++){
								str+="<img src='../"+pics[i]+"' width='100px' height='100px' />&nbsp;";
							}
							$("#news_pic_update_info").html($(str));
						},"json");	
																//保存按钮																			
					}
		    	}
		    },{
		    	text:"删除",
		    	iconCls:'icon-remove',
		    	handler:function(){
		    		//获取所有被选中的行
		    		var rows=datagrid.datagrid("getChecked");
		    		if(rows.length<=0){ //说明没有选中任意一行
		    			$.messager.show({
							title:'温馨提示',
							msg:'请选择您要删除的新闻！',
							timeout:2000, //时间
							showType:'slide'
						});
		    		}else{
		    			$.messager.confirm('信息确认', '您确定要删除所选中的新闻吗？', function(r){
		    				if (r){
		    					var nids="";
		    					for(var i=0;i<rows.length-1;i++){
		    						nids+=rows[i].nid+",";
		    					}
		    					nids+=rows[i].nid;
		    					//console.info(tids);
		    					//将要删除tid 发送到服务器
		    					$.post("../newsServlet",{op:"delNewsInfo",nids:nids},function(data){
				    				if(data==1){ //删除成功
				    					$.messager.show({
				    						title:'删除提示',
				    						msg:'新闻删除成功..',
				    						timeout:2000, //时间
				    						showType:'slide'
				    					});
				    					datagrid.datagrid("reload");  //重新加载数据一次
				    				}else{ //添加失败
				    					$.messager.alert('失败提示！','新闻删除失败...','error');
				    				}
				    			});
		    				}
		    			});
		    		}
		    	}
		  	}]   
		}); 

</script>
<style>
	.myinput{
		width:200px;
		border:1px solid #F63;
	}
	
	label{
		padding-right:10px;
	}
</style>

<!-- 添加 -->
<div id="news_add_newsInfo" class="easyui-dialog" title="添加新闻" data-options="fit:true,iconCls:'icon-add',resizable:true,modal:true,closed:true">
	<form action="" style="padding:20px;float:lect;display:inline-block;">
		<label>新闻类型</label>
		<select name="tid" id="news_newstype" class="myinput">
		
		</select>
		<br /><br />
		<label>新闻标题:</label><input type="text" name="title" id="news_title" class="myinput"/><br /><br />
		<label>发布日期:</label><input type="ndate" name="ndate" id="news_ndate" class="easyui-datebox myinput" required/><br /><br />
		<label>新闻作者:</label><input type="text" name="auth" id="news_auth" class="myinput"/><br /><br />
		<label>新闻图片:</label><input type="file" name="pic" id="news_pic" multiple="multiple" onchange="previewMultipleImage(this,'news_pic_show')"/><br /><br />
		<label>新闻权重:</label><input class="easyui-numberbox myinput" name="weight" id="news_weight"/><br /><br />
		<label>新闻内容：</label>
		<div>
			<script id="editor" type="text/javascript" style="width:800px;height:400px;"></script>
		</div>
		<a href="javascript:addNewsInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
	</form>
	<div style="float:right;width:380px; margin-right:20px;">
		<fieldset id="news_pic_show" >
			<legend>图片预览</legend>
			
		</fieldset>
	</div>
</div>



<!-- 修改 -->
<div id="news_update_newsInfo" class="easyui-dialog" title="修改新闻" data-options="fit:true,iconCls:'icon-edit',resizable:true,modal:true,closed:true">
	<form action="" style="padding:20px;float:lect;display:inline-block;">
		<label>新闻类型:</label><select name="tid" id="news_update_newstype" class="myinput">
		
		</select>
		<br /><br />
		<label>新闻标题:</label><input type="text" name="title" id="news_title_update" class="myinput" /><br /><br />
		<label>发布日期:</label><input type="ndate" name="ndate" id="news_ndate_update" class="easyui-datebox myinput" required/><br /><br />
		<label>新闻作者:</label><input type="text" name="auth" id="news_auth_update" class="myinput"/><br /><br />
		<label>新闻图片:</label><input type="file" name="pic" id="news_pic_update" multiple="multiple" onchange="previewMultipleImage(this,'news_pic_update_info')"/><br /><br />
		<label>新闻权重:</label><input class="myinput" name="weight" id="news_weight_update" /><br /><br />
		<label>浏览次数:</label><input class="myinput" name="view" id="news_view_update" /><br /><br />
		<label>新闻内容:</label>
		<div id="news_content_update">
			<script id="editor1" type="text/javascript" style="width:800px;height:400px;"></script>
		</div><br /><br />
		<a href="javascript:updateNewsInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">保存修改</a>
	</form>
	<div style="float:right;width:380px;margin-right:20px; margin-top:20px;" >
		<fieldset id="news_pic_update_info" >
			
			
		</fieldset>
	</div>
</div>


<script>
	var ue=UE.getEditor('editor');
	
	function addNewsInfo(){
		var tname=$("#news_newstype option:selected").text();
		var tid=$("#news_newstype").val();
		var title=$("#news_title").val();
		var auth=$("#news_auth").val();
		var ndate=$("#news_ndate").datebox('getValue');
		var weight=$("#news_weight").val();
		var content=ue.getContent();
		
		$.ajaxFileUpload({
			url:"../newsServlet?op=addNewsInfo",
			secureuri:false,
			fileElementId:"news_pic",
			dataType:"json",
			data:{tid:tid,title:title,auth:auth,ndate:ndate,weight:weight,content:content},
			success:function(data,status){
				if(parseInt($.trim(data))==1){//说明成功
					$.messager.show({title:'成功提示',msg:'新闻信息添加成功！',timeout:2000,showType:'slide'});
					$("#news_add_newsInfo").dialog("close");
					$("#news_info").datagrid("reload");
					$("#news_title").val("");
					$("#news_auth").val("");
					$("#news_ndate").datebox('setValue','');
					$("#news_weight").val("0");
					$("#news_pic").val("");
					$("#news_pic_show").html("");
					ue.getContent();
					
				}else{
					$.messager.alert("失败提示","新闻信息添加失败！","error");
				}
			},
			error:function(data,status,e){
				$.messager.alert("错误提示！","新闻信息添加有误！\n"+e,"error");
			}
		});
	}
	
	
	
	
	//新闻修改
	var ue1=UE.getEditor('editor1');
	function updateNewsInfo(){
		var rows=datagrid.datagrid("getChecked")[0];
		var nid=rows.nid;
		var tname=$("#news_update_newstype").val();
		var tid=$("#news_update_newstype").val();
		var title=$("#news_title_update").val();
		var ndate=$("#news_ndate_update").datebox('getValue');
		var auth=$("#news_auth_update").val();
		var weight=$("#news_weight_update").val();
		var view=$("#news_view_update").val();
		var content=ue1.getContent();
		
		
		
		$.ajaxFileUpload({
			url:"../newsServlet?op=updateNewsInfo",
			secureuri:false,
			fileElementId:"news_pic_update",
			dataType:"json",
			data:{nid:nid,tid:tid,title:title,auth:auth,ndate:ndate,weight:weight,content:content},
			success:function(data,status){
				if(parseInt($.trim(data))==1){//说明成功
					$.messager.show({title:'成功提示',msg:'新闻信息修改成功！',timeout:2000,showType:'slide'});
					$("#news_update_newsInfo").dialog("close");
					$("#news_info").datagrid("reload");
				}else{
					$.messager.alert("失败提示","新闻信息修改失败！","error");
				}
			},
			error:function(data,status,e){
				$.messager.alert("错误提示！","新闻信息修改有误！\n"+e,"error");
			}
		});
	}
		/*op="addNewsInfo";  //当前操作
		
		$.post("../newsServlet",
		{
			op:"addNewsInfo",
			tid:tid,
			tname:tname,
			title:title,
			auth:auth,
			ndate:ndate,
			weight:weight,
			content:content
		},function(data){
			if(data==1){ //添加成功
				$.messager.show({
					title:'成功提示',
					msg:'新闻添加成功..',
					timeout:2000, //时间
					showType:'slide'
				});
				datagrid.datagrid("reload");  //重新加载数据一次
			}else{ //添加失败
				$.messager.alert('失败提示！','新闻添加失败...','error');
			}
		});*/
	
</script>