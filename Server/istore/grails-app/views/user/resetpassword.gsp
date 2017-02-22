<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>修改密码</title>
    <meta name="layout" content="blackbg"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'style1.css')}" type="text/css">
</head>

<body>
<div id="lmmc">账户中心</div>
<div id="lmmc1">修改密码</div>
<div id="code">
    <g:form  controller="User" action="doResetpassword" name="resetpasswordForm">
        <div class="errorMessage">${flash.message}</div>
        <dl><span>原密码：</span><input name="userPassword" id="userPassword" type="password" class="sr" /></dl>
        <dl><span>新密码：</span><input name="new_userPassword" id="new_userPassword" type="password" class="sr"/></dl>
        <dl><span>重复密码：</span><input name="again_userPassword" id="again_userPassword" type="password" class="sr"/></dl>
        <dl style="position: relative;"><span>验证码：</span><input name="identify" id="identify" type="text" class="sr"/><label id="imageCaptchaDiv" style="position: absolute;top: 2px;"><g:render template="/common/imageCaptcha"></g:render></label></dl>
        <p><span>&nbsp;</span><input name="" type="image" src="${resource(dir: 'images', file: 'anniu13.jpg')}" onclick="app.user.submitResetpasswordForm()"/></p>
    </g:form>
</div>
<script type="text/javascript" src="${resource(dir: 'js/user', file: 'resetpassword.js')}"></script>
</body>
</html>