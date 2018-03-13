
<!DOCTYPE html>
<html>
<head>
    <title>Spring Boot With Gradle</title>
</head>
<body>
    <p>你好!</p>
    <p>现在的时间是：</p>
    <#setting number_format="currency"/>
    <#assign answer=42/>
    ${answer}
    ${answer?string}
    ${answer?string.number}
    ${answer?string.currency}
    ${answer?string.percent}
    ${answer}
    <br>

    ${springMacroRequestContext.contextPath}
</body>
</html>