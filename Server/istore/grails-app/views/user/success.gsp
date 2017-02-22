<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>注册成功</title>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'style_login.css')}" type="text/css">
</head>

<body>
<div id="reg_success">
    <h1>注册成功！</h1>
	<p>激活邮件已经发送到注册邮箱，请去邮箱激活。或者<i><a href="${request.contextPath}/">直接进入首页 →</a></i></p>
</div>
<div class="clear"></div>
</body>
</html>
