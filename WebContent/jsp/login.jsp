<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%  
 String importMsg="";  
 if(request.getSession().getAttribute("errorMsg")!=null){  
    importMsg=request.getSession().getAttribute("errorMsg").toString();  
 }  
 request.getSession().setAttribute("msg", "");  
 %> 
	<head> 
		<link href="../css/style.css" rel="stylesheet" type="text/css"/>
		<script src="../js/angular.min.js" type="text/javascript"></script>
		<script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.1.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<base href="<%=basePath%>" />
	</head>
	<body>
		<div id="content">
			<div id="bodys">
				<div id="left">
					
				</div>
					<div id="right">
						<form action="api/user/login" method="post">
							<font id="importMsg" color="red"><%=importMsg%></font><input type="hidden"/>
							<div id="right1">
								<span class="inputtext">用户名：</span><input type="text" name="username" id="username"/>
							</div>
							<div id="right2">
								<span class="inputtext">密&nbsp&nbsp&nbsp码：</span><input type="password" name="password" id="password"/>
							</div>
							<div id="right3">
								<div id="right3-1">
									<input type="checkbox" id="check" /><span id="remeber"><a href="">记住密码</a></span>
								</div>
								<div id="right3-2">
									<span id="forget"><a href="">忘记密码</a></span>
								</div>
							</div>
							<div id="right4">
								<input type="submit" id="submit" name="submit" id="submit" value="登录" />
							</div>
						</form>
					</div>
			</div>
		</div>
	</body>
</html>