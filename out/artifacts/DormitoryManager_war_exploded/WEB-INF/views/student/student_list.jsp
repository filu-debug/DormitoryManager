<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>学生列表</title>
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
	        title:'学生列表',
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method:"post",
	        url:"get_list?t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        //与实体类中属性名一一对应
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'stuNo',title:'学号',width:130, sortable: true},
 		        {field:'stuName',title:'姓名',width:130},
 		        {field:'gender',title:'性别',width:130},
 		        {field:'age',title:'年龄',width:130},
 		        {field:'college',title:'学院',width:130},
 		        {field:'major',title:'专业',width:130},
 		        {field:'clazz',title:'班级',width:130},
 		        {field:'dormNo',title:'宿舍',width:130},
 		        {field:'floorNo',title:'楼栋',width:130, sortable: true}
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
            	$.messager.confirm("消息提醒", "即将删除该学生，确认继续？", function(r){
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
	    	title: "添加学生",
	    	width: 400,
	    	height: 580,
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
										$("#add_stuNo").textbox('setValue', "");
										$("#add_stuName").textbox('setValue', "");
										$("#add_gender").textbox('setValue', "");
										$("#add_age").textbox('setValue', "");
										$("#add_college").textbox('setValue', "");
										$("#add_major").textbox('setValue', "");
										$("#add_clazz").textbox('setValue', "");
										$("#add_dormNo").textbox('setValue', "");
										$("#add_floorNo").textbox('setValue', "");
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
				$("#add_stuNo").textbox('setValue', "");
				$("#add_stuName").textbox('setValue', "");
				$("#add_age").textbox('setValue', "");
				$("#add_college").textbox('setValue', "");
				$("#add_major").textbox('setValue', "");
				$("#add_clazz").textbox('setValue', "");
				$("#add_password").textbox('setValue', "");
			}
	    });

	  	
	  	//编辑学生信息
	  	$("#editDialog").dialog({
	  		title: "修改学生信息",
	    	width: 400,
	    	height: 580,
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
										//清空原表格数据（不需要，因为每次都要回显学生数据）

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
			//打开修改窗口之前，回显该学生的信息
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit-id").val(selectRow.id);
				$("#edit_stuNo").textbox('setValue', selectRow.stuNo);
				$("#edit_stuName").textbox('setValue', selectRow.stuName);
				$("#edit_gender").textbox('setValue', selectRow.gender);
				$("#edit_age").textbox('setValue', selectRow.age);
				$("#edit_college").textbox('setValue', selectRow.college);
				$("#edit_major").textbox('setValue', selectRow.major);
				$("#edit_clazz").textbox('setValue', selectRow.clazz);
				$("#edit_dormNo").textbox('setValue', selectRow.dormNo);
				$("#edit_floorNo").textbox('setValue', selectRow.floorNo);
				$("#edit_password").textbox('setValue', selectRow.password);
			}
	    });

	  	//搜索按钮点击事件
	  	$("#search-btn").on("click",function () {
			$("#dataList").datagrid('reload',{
				stuNo:$("#search-stuNo").val(),
				stuName:$("#search-stuName").val()
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
			学号：<input id="search-stuNo" class="easyui-textbox"/>
			姓名：<input id="search-stuName" class="easyui-textbox"/>
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		</div>

	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px;">  
   		<form id="addForm" method="post">
	    	<table id="addTable" border=0 cellpadding="8" >
				<tr>
					<td>学号:</td>
					<td><input id="add_stuNo" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="stuNo" data-options="required:true, missingMessage:'请填写学生学号'" /></td>
				</tr>
	    		<tr>
	    			<td>姓名:</td>
	    			<td ><input id="add_stuName" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="stuName" data-options="required:true, missingMessage:'请填写学生姓名'" /></td>
	    		</tr>
				<tr>
					<td>性别:</td>
					<td><select id="add_gender" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="gender"><option value="男" selected>男</option><option value="女">女</option></select></td>
				</tr>
				<tr>
					<td>年龄:</td>
					<td ><input id="add_age" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="age" data-options="required:true, missingMessage:'请填写年龄'" /></td>
				</tr>
				<tr>
					<td>学院:</td>
					<td ><input id="add_college" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="college" data-options="required:true, missingMessage:'请填写学院'" /></td>
				</tr>
				<tr>
					<td>专业:</td>
					<td ><input id="add_major" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="major" data-options="required:true, missingMessage:'请填写专业'" /></td>
				</tr>
				<tr>
					<td>班级:</td>
					<td ><input id="add_clazz" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="clazz" data-options="required:true, missingMessage:'请填写班级'" /></td>
				</tr>
				<tr>
					<td>所属宿舍:</td>
					<td>
						<select id="add_dormNo" style="width:200px;" class="easyui-combobox" name="dormNo" data-options="required:true, missingMessage:'请选择所属宿舍'" >
							<c:forEach items="${dormList}" var="dorm">
								<option>${dorm.dormNo}</option>
							</c:forEach>
						</select>
					</td>
					<%--<td>
						<input class="mytype" type="radio" name="dormType" value='男' checked/>男寝
						<input class="mytype" type="radio" name="dormType" value='女'/>女寝
					</td>--%>
				</tr>
				<tr>

					<td>所属楼栋:</td>
					<td>
						<select id="add_floorNo" style="width:200px;" class="easyui-combobox" name="floorNo" data-options="required:true, missingMessage:'请选择所属楼栋'" >
							<c:forEach items="${floorList}" var="floor">
								<option>${floor.floorNo}</option>
							</c:forEach>
							<%--<c:if test="${dormType.equals('男')}">
								<c:forEach items="${boyFloors}" var="floor">
									<option>${floor.floorNo}</option>
								</c:forEach>
							</c:if>
							<c:if test="${dormType.equals('女')}">
								<c:forEach items="${girlDorms}" var="floor">
									<option>${floor.floorNo}</option>
								</c:forEach>
							</c:if>--%>
						</select>
					</td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input id="add_password" style="width: 200px; height: 30px;" class="easyui-textbox" type="password" name="password" data-options="required:true, missingMessage:'请填写密码'" /></td>
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
					<td>学号:</td>
					<td><input id="edit_stuNo" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="stuNo" data-options="required:true, missingMessage:'请填写学生学号'" /></td>
				</tr>
				<tr>
					<td>姓名:</td>
					<td ><input id="edit_stuName" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="stuName" data-options="required:true, missingMessage:'请填写学生姓名'" /></td>
				</tr>
				<tr>
					<td>性别:</td>
					<td><select id="edit_gender" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="gender"><option value="男" selected>男</option><option value="女">女</option></select></td>
				</tr>
				<tr>
					<td>年龄:</td>
					<td ><input id="edit_age" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="age" data-options="required:true, missingMessage:'请填写年龄'" /></td>
				</tr>
				<tr>
					<td>学院:</td>
					<td ><input id="edit_college" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="college" data-options="required:true, missingMessage:'请填写学院'" /></td>
				</tr>
				<tr>
					<td>专业:</td>
					<td ><input id="edit_major" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="major" data-options="required:true, missingMessage:'请填写专业'" /></td>
				</tr>
				<tr>
					<td>班级:</td>
					<td ><input id="edit_clazz" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="clazz" data-options="required:true, missingMessage:'请填写班级'" /></td>
				</tr>
				<tr>
					<td>所属宿舍:</td>
					<td>
						<select id="edit_dormNo" style="width:200px;" class="easyui-combobox" name="dormNo" data-options="required:true, missingMessage:'请选择所属宿舍'" >
							<c:forEach items="${dormList}" var="dorm">
								<option>${dorm.dormNo}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>

					<td>所属楼栋:</td>
					<td>
						<select id="edit_floorNo" style="width:200px;" class="easyui-combobox" name="floorNo" data-options="required:true, missingMessage:'请选择所属楼栋'" >
							<c:forEach items="${floorList}" var="floor">
								<option>${floor.floorNo}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input id="edit_password" style="width: 200px; height: 30px;" class="easyui-textbox" type="password" name="password" data-options="required:true, missingMessage:'请填写密码'" /></td>
				</tr>
	    	</table>
	    </form>
	</div>
	
	
</body>
</html>