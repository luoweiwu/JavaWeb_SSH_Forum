<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href="res/layui/css/layui.css">
<link rel="stylesheet" href="res/css/global.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<script src="res/layui/layui.js"></script>
<style>
</style>
</head>
<body>
	<div class="dvhead">
		<div class="dvlogo">
			<a href="default.jsp" target="_parent" fount-size="34px">论坛</a>
		</div>+
		<div class="dvsearch">Cynical丶Gary</div>
		<div class="nav-user" style="top: 0px; right: 100px;">

			<!--描述：登录的样子-->
			<s:if test="#session.user!=null">
				<a class="avatar" href="">
					<img src="${pageContext.request.contextPath }/<s:property value="#session.user.image"/>">
					
					<cite>
						<s:property value="#session.user.username" />
					</cite>
				</a>
								<div class="nav">
					<a href="${pageContext.request.contextPath }/UserAction_logout" target="_parent">
						<i class="iconfont icon-tuichu" style="top: 0; font-size: 22px;"></i>
						退出
					</a>
				</div>
			</s:if>
			<!--描述：未登录的样子-->
			<s:if test="#session.user==null">
				<a class="iconfont icon-touxiang layui-hide-xs" style="margin-top: 4px; display: inline-block;"> </a>
				<div class="nav" style="font-size: 14px; color: white; margin-top: -5px; margin-left: 1px;" />
				<a href="login.jsp" target="_parent">登录</a>
				<a href="register.jsp" target="_parent">注册</a>
			</s:if>
		</div>
	</div>
</body>
</html>