<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<table id="admin_info"></table>
<script>
	$(function(){
		var datagrid;
		var editRow=undefined;
		var op;
		var flag;
		datagrid=$('#admin_info').datagrid({   
		    url:'../adminServlet', 
		    queryParams:{op:"getPageAdminInfo"},
		    fitColumns:true,
		    striped:true,
		    loadMsg:"数据加载中...",
		    pagination:true,
		    rownumbers:true,
		    sortName:'aid',
		    sortOrder:'asc',
		    remoteSort:false,
		    columns:[[   
		        {field:'aids',title:'管理员编号',width:100,align:'center',checkbox:true},   
		        {field:'aid',title:'管理员编号',width:100,align:'center',sortable:true},   
		        {field:'aname',title:'管理员姓名',width:100,align:'center',editor:{type:"text",options:{required:true}}},  
		        {field:'pwd',title:'管理员密码',width:100,align:'center',editor:{type:"text",options:{required:true}}}
		    ]],
		    toolbar:[{   
		        text:"添加",   
		        iconCls:'icon-add',   
		        handler:function(){   
		           op="addAdminInfo"; //当前的操作
		           flag="添加";
		           //先判断又没有正在编辑的行
		           if(editRow!=undefined){//说明有行正在被编辑，则还原当前的修改
		        		datagrid.datagrid("rejectChanges");  //回滚自创建以来或上次调用AcceptChanges，所有的变化数据
		        		datagrid.datagrid("endEdit",editRow); //关闭当前正在编辑的行
		        		editRow=undefined;
		           }else{  //在表格的最前面添加一行
		        	   datagrid.datagrid("insertRow",{index:0,row:{}});
		           	   //打开编辑器
		           	   datagrid.datagrid("beginEdit",0);
		           	   editRow=0;  //记录当前正在被编辑的行
		           }
		        }
		    },{
		    	text:"修改",
		    	iconCls:'icon-edit',
		    	handler:function(){
		    		//获取要修改的行
		    		var rows=datagrid.datagrid("getChecked")[0];
		    		if(rows==undefined){
		    			$.messager.show({title:'温馨提示',msg:'请选择您要修改的管理员信息！',timeout:2000,showType:'slide'});
		    		}else{
		    			op="updateAdminInfo";
		    			flag="修改";
		    			if(editRow!=undefined){ //说明有行正在被编辑，则还原当前的修改
		    				datagrid.datagrid("rejectChanges");  //回滚自创建以来或上次调用AcceptChanges，所有的变化数据
			        		datagrid.datagrid("endEdit",editRow); //关闭当前正在编辑的行
			        		editRow=undefined;
		    			}else{
		    				//获取当前选择行的索引
		    				var index=datagrid.datagrid("getRowIndex",rows);
		    				
		    				datagrid.datagrid("updateRow",{index:index,row:rows});
		    				
		    				//打开编辑器
		    				datagrid.datagrid("beginEdit",index);
		           	   		editRow=index;  //记录当前正在被编辑的行
		    			}
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
    						msg:'请选择您要删除的管理员信息！',
    						timeout:2000, //时间
    						showType:'slide'
    					});
		    		}else{
		    			$.messager.confirm('信息确认', '您确定要删除所选中的管理员信息吗？', function(r){
		    				if (r){
		    					var aids="";
		    					for(var i=0;i<rows.length-1;i++){
		    						aids+=rows[i].aid+",";
		    					}
		    					aids+=rows[i].aid;
		    					console.info(aids);
		    					//将要删除aid 发送到服务器
		    					$.post("../adminServlet",{op:"delAdminInfo",aids:aids},function(data){
				    				if(data==1){ //删除成功
				    					$.messager.show({
				    						title:'删除提示',
				    						msg:'管理员信息删除成功..',
				    						timeout:2000, //时间
				    						showType:'slide'
				    					});
				    					datagrid.datagrid("reload");  //重新加载数据一次
				    				}else{ //添加失败
				    					$.messager.alert('失败提示！','管理员信息删除失败...','error');
				    				}
				    			});
		    				}
		    			});


		    		}
		    	}
		    },{
		    	text:"保存",
		    	iconCls:'icon-save',
		    	handler:function(){
		    		datagrid.datagrid("endEdit",editRow); //关闭当前正在编辑的行
		    		
		    		//获取当前被修改的数据
		    		var rows=datagrid.datagrid("getChanges")[0];
		    		if(rows==undefined){//说明当前没有被编辑
		    			datagrid.datagrid("rejectChanges");  //回滚自创建以来或上次调用AcceptChanges，所有的变化数据
		        		datagrid.datagrid("unselectAll"); //关闭当前正在编辑的行
		        		editRow=undefined;
		    		}else{
		    			rows["op"]=op;
		    			
		    			$.post("../adminServlet",rows,function(data){
		    				data=parseInt( $.trim(data) );
		    				if(data==1){ //添加成功
		    					$.messager.show({
		    						title:'成功提示',
		    						msg:'管理员信息'+flag+'成功..',
		    						timeout:2000, //时间
		    						showType:'slide'
		    					});
		    				}else{ //添加失败
		    					$.messager.alert('失败提示！','管理员信息'+flag+'失败...','error');
		    				}
		    				rows=null;
		    				datagrid.datagrid("reload");  //重新加载数据一次
		    				editRow=undefined;
		    				datagrid.datagrid("rejectChanges");  //回滚自创建以来或上次调用AcceptChanges，所有的变化数据
			        		datagrid.datagrid("unselectAll"); //关闭当前正在编辑的行
		    			});
		    		}
		    	}
		  	},{
		  		text:'撤销',
		  		iconCls:'icon-redo',
		    	handler:function(){
		    		datagrid.datagrid("rejectChanges");  //回滚自创建以来或上次调用AcceptChanges，所有的变化数据
		    		datagrid.datagrid("endEdit",editRow);
		    		editRow=undefined;
		    	}
		  	}]   
		});  
	});
</script>