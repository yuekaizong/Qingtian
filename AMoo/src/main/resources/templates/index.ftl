<!DOCTYPE html>
<html>
<head>
    <title>Spring Boot With Gradle</title>
</head>
<body>
<p>你好!</p>
<br/>
单个文件上传：<br/>
<form action="${springMacroRequestContext.contextPath}/index/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="submit" name="提交上传"/>
</form>
<br/>
多个文件上传：
<form action="${springMacroRequestContext.contextPath}/index/uploads" method="post" enctype="multipart/form-data">
    文件1：<input type="file" name="file"/><br/>
    文件2：<input type="file" name="file"/><br/>
    文件3：<input type="file" name="file"/><br/>
    <input type="submit" value="上传多个文件"/>
</form>
</body>
</html>