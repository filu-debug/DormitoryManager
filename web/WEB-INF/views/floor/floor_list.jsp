<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>楼宇列表</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/css/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		var table;
		
		//datagrid初始化 (easyui框架函数，将后台数据填充到ID为dataList的table中)
	    $('#dataList').datagrid({ 
	        title:'楼宇列表',
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method:"post",
	        url:"get_list?t="+new Date().getTime(),
	        singleSelect:false,//是否单选
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        sortOrder:'DESC',
	        remoteSort: false,
	        //与实体类中属性名一一对应
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'floorNo',title:'楼栋编号',width:150, sortable: true},
 		        {field:'floorType',title:'类型',width:150},
 		        {field:'floorManager',title:'楼管',width:100}
 		        
	 		]], 
	        toolbar: "#toolbar"
	    }); 
	    //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    }); 
	    //设置工具类按钮
	    $("#add").click(function(){
	    	table = $("#addTable");
	    	$("#addDialog").dialog("open");
	    });
	    //修改
	    $("#edit").click(function(){
	    	table = $("#editTable");
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    //删除
	    $("#delete").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	var selectLength = selectRows.length;
        	if(selectLength == 0){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var ids = [];
            	//将选中的行遍历，取出每行的ID
            	$(selectRows).each(function(i, row){
            		ids[i] = row.id;
            	});
            	$.messager.confirm("消息提醒", "即将删除该楼栋，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "delete",
							data: {ids: ids},
							dataType:"json",
							success: function(data){
								if(data.type == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
									$("#dataList").datagrid("uncheckAll");
								} else{
									$.messager.alert("消息提醒",data.msg,"warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	    
	  	//设置添加窗口
	    $("#addDialog").dialog({
	    	title: "添加楼栋",
	    	width: 350,
	    	height: 300,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
				
	    		{
					text:'添加',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							var data = $("#addForm").serialize();
							$.ajax({
								type: "post",
								url: "add",
								data: data,
								dataType:"json",
								success: function(data){
									if(data.type == "success"){
										$.messager.alert("消息提醒",data.msg,"info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_floorNo").textbox('setValue', "");
										$("#add_floorType").textbox('setValue', "");
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("消息提醒",data.msg,"warning");
										return;
									}
								}
							});
						}
					}
				},
				
			],
			onClose: function(){
				$("#add_floorNo").textbox('setValue', "");
				$("#add_floorType").textbox('setValue', "");
			}
	    });

	  	
	  	//编辑楼栋信息
	  	$("#editDialog").dialog({
	  		title: "修改楼栋信息",
	    	width: 350,
	    	height: 300,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'提交',
					plain: true,
					iconCls:'icon-edit',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							var data = $("#editForm").serialize();
							
							$.ajax({
								type: "post",
								url: "edit",
								data: data,
								dataType: "json",
								success: function(data){
									if(data.type == "success"){
										$.messager.alert("消息提醒",data.msg,"info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//清空原表格数据（不需要，因为每次都要回显楼栋数据）

										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
							  			$('#dataList').datagrid("uncheckAll");
										
									} else{
										$.messager.alert("消息提醒",data.msg,"warning");
										return;
									}
								}
							});
						}
					}
				},
			],
			//打开修改窗口之前，回显该楼栋的信息
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit-id").val(selectRow.id);
				$("#edit_floorNo").textbox('setValue', selectRow.floorNo);
				$("#edit_floorType").textbox('setValue', selectRow.floorType);
				$("#edit_floorManager").textbox('setValue', selectRow.floorManager);
			}
	    });

	  	//搜索按钮点击事件
	  	$("#search-btn").on("click",function () {
			$("#dataList").datagrid('reload',{
				floorNo:$("#search-floorNo").val(),
				floorType:$("#search-floorType").val()
			});
		});

		//下拉框通用属性
		$("#add_floorManager,#edit_floorManager").combobox({
			width: "200",
			height: "30",
			valueField: "id",
			textField: "name",
			multiple: false, //不可多选
			editable: false, //不可编辑
			//method: "post",
		});

	});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div>
			<a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a>
			楼栋编号：<input id="search-floorNo" class="easyui-textbox"/>
			类型：<input id="search-floorType" class="easyui-textbox"/>
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		</div>

	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px;">  
   		<form id="addForm" method="post">
	    	<table id="addTable" border=0 cellpadding="8" >
				<tr>
					<td>楼栋编号:</td>
					<td><input id="add_floorNo" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="floorNo" data-options="required:true, missingMessage:'请填写楼栋编号'" /></td>
				</tr>
	    		<tr>
	    			<td>类型:</td>
	    			<td ><input id="add_floorType" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="floorType" data-options="required:true, missingMessage:'请填写类型'" /></td>
	    		</tr>
	    		<tr>
	    			<td>楼管:</td>
	    			<td>
						<select id="add_floorManager" style="width:200px;" class="easyui-combobox" name="floorManager" data-options="required:true, missingMessage:'请填写楼管'" >
							<c:forEach items="${HpList}" var="hp">
								<option>${hp.workNo}</option>
							</c:forEach>
						</select>
					</td>
	    		</tr>
	    		
	    	</table>
	    </form>
	</div>
	
	<!-- 修改窗口 -->
	<div id="editDialog" style="padding: 10px">
    	<form id="editForm" method="post">
			<input type="hidden" name="id" id="edit-id">
	    	<table id="editTable" cellpadding="8" >

				<tr>
					<td>楼栋编号:</td>
					<td><input id="edit_floorNo" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="floorNo" data-options="required:true, missingMessage:'请填写楼栋编号'" /></td>
				</tr>
				<tr>
					<td>类型:</td>
					<td ><input id="edit_floorType" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="floorType" data-options="required:true, missingMessage:'请填写楼栋类型'" /></td>
				</tr>
				<tr>
					<td>楼管:</td>
					<td>
						<select id="edit_floorManager" class="easyui-combobox"  style="width: 200px;" name="floorManager" data-options="required:true, missingMessage:'请填写楼管'" >
							<c:forEach items="${HpList}" var="hp">
								<option>${hp.workNo}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
	    	</table>
	    </form>
	</div>
	
	
</body>
</html>