<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="../script/jquery-1.9.1.min.js"></script>
</head>
<body>

<a href="/account/resource">Add New Resume</a>

<table border="1" cellpadding="10" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Address</th>
        <th>Phone</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>

    <c:forEach items="${res}" var="re">
        <tr>
            <td>${re.id}</td>
            <td>${re.name}</td>
            <td>${re.address}</td>
            <td>${re.phone}</td>
            <td><a href="/account/resource/${re.id}">Edit</a></td>
            <td><a href="/account/deleting/${re.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>

<br><br>

</body>
</html>
