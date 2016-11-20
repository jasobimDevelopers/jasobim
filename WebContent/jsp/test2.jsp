<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ page import="com.my.spring.model.*" %>
 --%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="st" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
</head>
<body>
    <fmt:setLocale value="zh_cn" />
    <form action="<st:url value="/post/addUser"></st:url>" method="post">
        <c:forEach items="${users}" var="user">
            User:${user.name}<br/>
            Create time:<fmt:formatDate value="${user.createTime}"/><br/>
            Is girl:
            <c:choose>
                <c:when test="${user.girl}">Yes</c:when>
                <c:when test="${!user.girl}">No</c:when>
                <c:otherwise>N/A</c:otherwise>
            </c:choose>
            <br/>
            Checkboxs:
            <c:forEach items="${user.cbx}" var="item">
                ${item},
            </c:forEach>
            <br/>
            Age:${user.age}<br/>
            E-mail:${user.email}<br/>
            <hr/>
        
            <table style="width:100%;border:1px solid #ccc;">
                <thead>
                    <tr style="text-align:left; background-color:#eee;">
                        <th>Company name</th>
                        <th>User</th>
                        <th>Create time</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${user.customers}" var="item">
                    <tr>
                        <td>${item.company}</td>
                        <td>${item.user.name}</td>
                        <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <hr/>
        </c:forEach>
        
        User name:
        <input type="text" name="name" id="name" /><br/>
        Is girl:
        <input type="radio" name="girl" id="isGirl" value="true" checked="checked" /><label for="isGirl">Yes</label>
        <input type="radio" name="girl" id="noGirl" value="false" /><label for="noGirl">No</label><br/>
        Checkboxs:
        <input type="checkbox" name="cbx" id="cbx1" value="1" /><label for="cbx1">1</label>
        <input type="checkbox" name="cbx" id="cbx2" value="2" /><label for="cbx2">2</label>
        <input type="checkbox" name="cbx" id="cbx3" value="3" /><label for="cbx3">3</label>
        <br/>
        Age:<input type="text" name="age" id="age" /><br/>
        E-mail:<input type="text" name="email" id="email" /><br/>
        Create time:
        <input type="text" name="createTime" id="createTime" /><br/>
        Company:
        <input type="text" name="customers[0].company" id="customers[0].company" /><br/>
        <input type="submit" value="add" />
        <sf:errors path="*"></sf:errors>
    </form>
    <hr/>
</body>
</html>