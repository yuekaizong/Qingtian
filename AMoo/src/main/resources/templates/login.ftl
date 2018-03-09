<html>
<head>
    <title>登录界面</title>
</head>
<body>
    <form action="/login" method="post">
        <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> -->
        用户名：<input type="text" name="username" value="admin"/><br/>
        密码：<input type="text" name="password" value="123456"/><br/>
        <input type="submit" value="登录"/>
    </form>
</body>
</html>
