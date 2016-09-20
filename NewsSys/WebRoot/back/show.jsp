<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<table id="show_news_info" data-options="fit:true"></table>
<div id="show_news_info_find" style="width:100px, text-align:left">
	<span>标题：</span><input type="text" name="title" id="show_news_title"/>
	<span>日期：</span><input type="ndate" name="ndate" id="news_ndate" class="easyui-datebox myinput" required/>
	<span>类型：</span><select name="typeInfo" id="typeInfo">
		<option value="0">全部</option>
	</select>
	<a href="javascript:findNewsInfoByInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">查询</a>
</div>
<script>
	$(function(){
			 $.post("../newsTypeServlet",{op:"getAllNewsType"},function(data){
				  var obj=$("#typeInfo");
				  var opt;
				  $.each(data.rows,function(index,item){
					  opt="<option value='"+item.tid+"'>"+item.tname+"</option>";
					  obj.append($(opt));
				  });
	 		 },"json");
	  
			$('#show_news_info').datagrid({   
			    url:'../newsServlet', 
			    queryParams:{op:"getPageNewsInfo"},
			    fitColumns:true,
			    striped:true,
			    loadMsg:"数据加载中...",
			    pagination:true,
			    rownumbers:true,
			    sortName:'nid',
		    	sortOrder:'asc',
		    	remoteSort:false,
			    columns:[[   
			        {field:'nids',title:'选择',width:100,align:'center',checkbox:true},   
			        {field:'nid',title:'新闻编号',width:100,align:'center',sortable:true},   
			        {field:'title',title:'新闻标题',width:400,align:'center'},
			        {field:'ndate',title:'新闻日期',width:100,align:'center'},    
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
			    toolbar:"#show_news_info_find"
			});	
	});
	
	//组合查询
	function findNewsInfoByInfo(){
		var title=$.trim( $("#show_news_title").val() ); //标题
		var ndate=$.trim( $("#news_ndate").datebox('getValue') );
		var tid=$.trim( $("#typeInfo").val() );  //类型
		
		$('#show_news_info').datagrid({
			url:'../newsServlet',
			queryParams:{op:"findNewsByInfo",title:title,ndate:ndate,tid:tid}
		});
	
	}
	
	
	

</script>