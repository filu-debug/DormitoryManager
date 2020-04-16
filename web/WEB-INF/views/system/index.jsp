<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <c:if test="${userType==1}">
        <title>学生宿舍管理系统 管理员后台</title>
    </c:if>
    <c:if test="${userType!=1}">
        <title>学生宿舍管理系统</title>
    </c:if>
    <link rel="shortcut icon" href="favicon.ico"/>
	<link rel="bookmark" href="favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="../easyui/css/default.css" />
    <link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css" />
    <script type="text/javascript" src="../easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='../easyui/js/outlook2.js'> </script>
    <%--管理员列表页--%>
    <c:if test="${userType=='1'}">
    <script type="text/javascript">
	 var _menus = {"menus":[
						{"menuid":"1","icon":"","menuname":"楼宇管理",
							"menus":[
								{"menuid":"21","menuname":"楼宇列表","icon":"icon-house","url":"../floor/list"},
							]
						},
						{"menuid":"2","icon":"","menuname":"学生信息管理",
							"menus":[
									{"menuid":"21","menuname":"学生列表","icon":"icon-user-student","url":"../student/list"},
								]
						},
						{"menuid":"3","icon":"","menuname":"用户信息管理",
							"menus":[
									{"menuid":"31","menuname":"用户列表","icon":"icon-user-student","url":"../user/list"},
								]
						},
                         {"menuid":"4","icon":"","menuname":"工人管理",
                             "menus":[
                                 {"menuid":"31","menuname":"工人列表","icon":"icon-user-student","url":"../worker/list"},
                             ]
                         },
						{"menuid":"5","icon":"","menuname":"楼管管理",
							"menus":[
									{"menuid":"43","menuname":"楼管列表","icon":"icon-book-open","url":"../houseparent/list"}
								]
						},
                         {"menuid":"6","icon":"","menuname":"宿舍管理",
                             "menus":[
                                 {"menuid":"43","menuname":"宿舍列表","icon":"icon-book-open","url":"../dorm/list"}
                             ]
                         },
						{"menuid":"7","icon":"","menuname":"系统管理",
							"menus":[
							        {"menuid":"51","menuname":"系统设置","icon":"icon-set","url":"SystemServlet?method=toAdminPersonalView"},
								]
						}
				]};


    </script>
    </c:if>
    <%--学生列表页--%>
    <c:if test="${userType=='2'}">
        <script type="text/javascript">
            var _menus = {"menus":[
                    {"menuid":"2","icon":"","menuname":"个人",
                        "menus":[
                            {"menuid":"21","menuname":"我的信息","icon":"icon-user-student","url":"../student/list"},
                        ]
                    },
                    {"menuid":"7","icon":"","menuname":"报修",
                        "menus":[
                            {"menuid":"51","menuname":"报修申请","icon":"icon-set","url":"../sturepai/list"},
                        ]
                    }
                ]};

        </script>
    </c:if>
    <%--工人列表页--%>
    <c:if test="${userType=='3'}">
        <script type="text/javascript">
            var _menus = {"menus":[
                    {"menuid":"2","icon":"","menuname":"个人",
                        "menus":[
                            {"menuid":"21","menuname":"我的信息","icon":"icon-user-student","url":"../worker/list"},
                        ]
                    },
                    {"menuid":"7","icon":"","menuname":"报修信息",
                        "menus":[
                            {"menuid":"51","menuname":"任务列表","icon":"icon-set","url":"../repai/list"},
                        ]
                    }
                ]};
        </script>
    </c:if>
    <%--楼管列表页--%>
    <c:if test="${userType=='4'}">
        <script type="text/javascript">
            var _menus = {"menus":[
                    {"menuid":"2","icon":"","menuname":"个人",
                        "menus":[
                            {"menuid":"21","menuname":"我的信息","icon":"icon-user-student","url":"../houseparent/list"},
                        ]
                    },
                    {"menuid":"7","icon":"","menuname":"报修管理",
                        "menus":[
                            {"menuid":"51","menuname":"报修请求列表","icon":"icon-set","url":"../hprepai/list"},
                        ]
                    }
                ]};

        </script>
    </c:if>

</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
	<noscript>
		<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
		    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <c:if test="${userType=='1'}">
        <span style="float:right; padding-right:20px;" class="head"><span style="color:red; font-weight:bold;">${user.username}&nbsp;</span>您好&nbsp;&nbsp;&nbsp;<a href="SystemServlet?method=LoginOut" id="loginOut">安全退出</a></span>
        </c:if>
        <c:if test="${userType=='2'}">
            <span style="float:right; padding-right:20px;" class="head"><span style="color:red; font-weight:bold;">${user.stuName}&nbsp;</span>您好&nbsp;&nbsp;&nbsp;<a href="SystemServlet?method=LoginOut" id="loginOut">安全退出</a></span>
        </c:if>
        <c:if test="${userType=='3'}">
            <span style="float:right; padding-right:20px;" class="head"><span style="color:red; font-weight:bold;">${user.name}&nbsp;</span>您好&nbsp;&nbsp;&nbsp;<a href="SystemServlet?method=LoginOut" id="loginOut">安全退出</a></span>
        </c:if>
        <c:if test="${userType=='4'}">
            <span style="float:right; padding-right:20px;" class="head"><span style="color:red; font-weight:bold;">${user.name}&nbsp;</span>您好&nbsp;&nbsp;&nbsp;<a href="SystemServlet?method=LoginOut" id="loginOut">安全退出</a></span>
        </c:if>
        <span style="padding-left:10px; font-size: 16px; ">学生宿舍管理系统</span>
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">

    </div>
    <div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
	<div id="nav" class="easyui-accordion" fit="true" border="false">
		<!--  导航内容 -->

	</div>
	
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<jsp:include page="welcome.jsp" />
		</div>
    </div>
	
	
</body>
</html>