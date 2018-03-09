<html>
<head>
    <title>Shell添加界面</title>
</head>
<body>
<form action="/shell/save" method="post">
    <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> -->
    id：<input type="text" name="id" value=""/><br/>
    name：<input type="text" name="name" value=""/><br/>
    fade：<input type="text" name="fade" value=""/><br/>
    enable：<input type="text" name="enable" value=""/><br/>
    <input type="submit" value="添加"/>
</form>
</body>
</html>
