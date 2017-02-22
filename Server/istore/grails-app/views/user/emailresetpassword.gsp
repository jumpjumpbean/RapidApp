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
    <g:form  controller="User" action="doEmailresetpassword" name="emailresetpasswordForm">
        <div class="errorMessage">${flash.message}</div>
        <input type="hidden" value="${params.email}" name="email" name="email">
        <input type="hidden" value="${params.c}" name="c" >
        <p><label>新密码：</label><input name="new_userPassword" id="new_userPassword" type="password" class="sr"/></p>
        <p><label>重复密码：</label><input name="again_userPassword" id="again_userPassword" type="password" class="sr"/></p>
        <p><label>验证码：</label><input name="identify" id="identify" type="text" class="sr"/><span id="imageCaptchaDiv"><g:render template="/common/imageCaptcha"></g:render></span></p>
        <p><label>&nbsp;</label><input name="" type="image" src="${resource(dir: 'images', file: 'anniu13.jpg')}" onclick="app.user.submitResetpasswordForm()"/></p>
    </g:form>
</div>
<script type="text/javascript" src="${resource(dir: 'js/user', file: 'resetpassword.js')}"></script>
</body>
</html>