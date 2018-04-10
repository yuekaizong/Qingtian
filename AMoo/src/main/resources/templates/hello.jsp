<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Spring Boot With Gradle</title>
</head>
<body>
    <p>你好!</p>
    <p>现在的时间是：<%=new Date()%></p>
    <c:out value="利用 JSTL 输出"/>
</body>
</html>