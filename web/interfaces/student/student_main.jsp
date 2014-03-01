<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>学生界面</title>
	</head>
	<frameset rows="23%,*" frameborder="yes">
		<frame src="public/top.faces">
		<frameset cols="153,*" frameborder="yes">
		<frame src="student/student_menu.faces">
		<frame src="student/student_fill_big.jsp" name="student_right">
		</frameset>
	</frameset>


	<body>
	</body>
</html>
