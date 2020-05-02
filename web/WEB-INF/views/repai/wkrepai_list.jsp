<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>报修申请</title>
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
	        title:'任务列表',
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method:"post",
	        url:"get_list?t="+new Date().getTime(),
	        //idField:'id',
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        //sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        //与实体类中属性名一一对应
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'stuName',title:'申请人姓名',width:100, sortable: true},
 		        {field:'floorNo',title:'所在楼栋',width:100},
 		        {field:'dormNo',title:'所在寝室',width:80},
 		        {field:'phone',title:'联系电话',width:120},
 		        {field:'retype',title:'维修类别',width:70},
 		        {field:'state',title:'任务状态',width:100},
 		        {field:'starttime',title:'发布时间',width:150},
 		        {field:'discr',title:'具体描述',width:200}

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
	    //接取任务
	    $("#receive").click(function(){
			var selectRows = $("#dataList").datagrid("getSelections");
			var selectLength = selectRows.length;
			if(selectLength == 0){
				$.messager.alert("消息提醒", "请至少选择一个任务!", "warning");
			} else{
				stuRepais = [];
				//将选中的行遍历,获取每一行
				$(selectRows).each(function(i, row){
					stuRepais[i]={
						"id":row.id,
						"stuName":row.stuName,
						"floorNo":row.floorNo,
						"dormNo":row.dormNo,
						"phone":row.phone,
						"retype":row.retype,
						"state":row.state,
						"starttime":row.starttime,
						"discr":row.discr
					};
				});
				$.messager.confirm("消息提醒", "确认接取选中的任务吗？", function(r){
					if(r){
						$.ajax({
							type: "post",
							url: "receive",
							data: JSON.stringify(stuRepais),
							dataType:"json",
							contentType:"application/json;charset=utf-8",
							success: function(data){
								if(data.type == "success"){
									$.messager.alert("消息提醒","接取成功!","info");
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
	    	title: "添加维修申请",
	    	width: 350,
	    	height: 540,
	    	iconCls: "icon-add",
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
										$("#add_stuName").textbox('setValue', "");
										$("#add_floorNo").textbox('setValue', "");
										$("#add_dormNo").textbox('setValue', "");
										$("#add_phone").textbox('setValue', "");
										$("#add_retype").textbox('setValue', "");
										$("#add_discr").textbox('setValue', "");
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
				$("#add_stuName").textbox('setValue', "");
				$("#add_floorNo").textbox('setValue', "");
				$("#add_dormNo").textbox('setValue', "");
				$("#add_phone").textbox('setValue', "");
				$("#add_retype").textbox('setValue', "");
				$("#add_discr").textbox('setValue', "");
			}
	    });

	  	
	  	//编辑楼栋信息
	  	$("#editDialog").dialog({
	  		title: "修改楼栋信息",
	    	width: 350,
			<c:if test="${userType=='1'}">
	    	height: 380,
			</c:if>
			<c:if test="${userType=='4'}">
			height: 150,
			</c:if>
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
										//清空原表格数据（不需要，因为每次都要回显数据）

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
				$("#edit_name").textbox('setValue', selectRow.name);
				$("#edit_workNo").textbox('setValue', selectRow.workNo);
				$("#edit_phone").textbox('setValue', selectRow.phone);
				$("#edit_gender").textbox('setValue', selectRow.gender);
				$("#edit_floorNo").textbox('setValue', selectRow.floorNo);
				$("#edit_password").textbox('setValue', selectRow.password);
			}
	    });

	  	/*//搜索按钮点击事件
	  	$("#search-btn").on("click",function () {
			$("#dataList").datagrid('reload',{
				name:$("#search-name").val(),
				workNo:$("#search-workNo").val()
			});
		});*/


		$.extend($.fn.validatebox.defaults.rules, {
			phoneNum: { //验证手机号
				validator: function(value, param){
					b = /^1[3-8]+\d{9}$/.test(value);
					return b;
				},
				message: '请输入正确的手机号码。'
			},
			telNum:{ //既验证手机号，又验证座机号
				validator: function(value, param){
					b = /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^(()|(\d{3}\-))?(1[358]\d{9})$)/.test(value);

					return b;
				},
				message: '请输入正确的电话号码。'
			}
		});

		//下拉框通用属性
		$("#add_floorNo,#edit_floorNo").combobox({
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
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div>
			<a id="receive" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">接取任务</a>
		</div>
	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px;">  
   		<form id="addForm" method="post">
	    	<table id="addTable" border=0 cellpadding="8" >

	    		<tr>
	    			<td>申请人:</td>
	    			<td ><input id="add_stuName" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="stuName" data-options="required:true, missingMessage:'请填写姓名'" /></td>
	    		</tr>
				<tr>
					<td>联系电话:</td>
					<td><input id="add_phone" class="easyui-textbox" name="phone" style="width: 200px;" data-options="prompt:'请输入正确的电话号码',validType:'phoneNum'" /></td>
				</tr>
				<tr>
					<td>维修类别:</td>
					<td>
						<select id="add_retype" style="width: 200px; height: 30px;" type="text" name="retype" data-options="required:true, missingMessage:'选择维修类别'" >
								<option>门窗</option>
								<option>用水</option>
								<option>电灯</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>具体描述:</td>
					<td ><input id="add_discr" style="width: 200px; height: 180px;" class="easyui-textbox" type="text" name="discr" data-options="multiline:true,required:true, missingMessage:'请详细描述设施损坏情况'" /></td>
				</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 修改窗口 -->
	<div id="editDialog" style="padding: 10px">
    	<form id="editForm" method="post">
			<input type="hidden" name="id" id="edit-id">
	    	<table id="editTable" cellpadding="8" >
				<c:if test="${userType=='1'}">
				<tr>
					<td>工号:</td>
					<td ><input id="edit_workNo" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="workNo" data-options="required:true, missingMessage:'请填写工号'" /></td>
				</tr>
				<tr>
					<td>姓名:</td>
					<td ><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写姓名'" /></td>
				</tr>
				<tr>
					<td>性别:</td>
					<td><select id="edit_gender" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="gender"><option value="男" selected>男</option><option value="女">女</option></select></td>
				</tr>
				<tr>
					<td>电话:</td>
					<td><input id="edit_phone" class="easyui-textbox" name="phone" style="width: 200px;" data-options="prompt:'请输入正确的电话号码码',validType:'phoneNum'" /></td>
				</tr>
				<tr>
					<td>楼栋编号:</td>
					<td>
						<select id="edit_floorNo" style="width: 200px; height: 30px;" type="text" name="floorNo" data-options="required:true, missingMessage:'请填写楼栋编号'" >
							<c:forEach items="${floorList}" var="flo">
								<option>${flo.floorNo}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				</c:if>
				<tr>
					<td>密码:</td>
					<td><input id="edit_password" style="width: 200px; height: 30px;" class="easyui-textbox" type="password" name="password" data-options="required:true, missingMessage:'请填写密码'" /></td>
				</tr>
	    	</table>
	    </form>
	</div>
	
	
</body>
</html>