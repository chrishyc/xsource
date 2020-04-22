<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<h1>Login Page</h1>
<!--表单提交目标链接和方法post-->
<form action="/account/login" method="get">
    <input type="text" name="name" placeholder="用户名">
    <input type="password" name="password" placeholder="密码">
    <input type="submit" value="登录">
</form>
</html>
