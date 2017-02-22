<%@ page contentType="text/html"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>欢迎加入Istore</title>
</head>

<body>
<p>您好: ${userName},</p>
<div> 
<p>欢迎注册Istore帐号，点击下面链接激活您的账号.</p>
    <a href="${website}/user/activeUser?email=${email}&c=${randomCode}">${website}/user/activeUser?email=${email}&c=${randomCode}</a>
<p> 如果链接无法点击,请复制链接到您的浏览器地址栏打开。</p>
    此为系统邮件，请勿回复！
</div>
</body>
</html>



