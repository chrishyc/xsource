<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>

<!-- modelAttribute="resume"此处resume与map.put("resume", new Resume()) 一致 -->
<form:form action="/account/resource" method="POST" modelAttribute="res">

    <br>
    name: <form:input path="name"/>
    <br>
    <br>
    address: <form:input path="address"/>
    <br>
    <br>
    phone: <form:input path="phone"/>
    <br>
    <input type="submit" value="Submit"/>
</form:form>

</body>
</html>
