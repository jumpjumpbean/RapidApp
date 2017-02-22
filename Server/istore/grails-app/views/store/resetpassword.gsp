<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>修改密码</title>
    <meta name="layout" content="main"/>
</head>
<body>
<div id="code">
    <g:form  controller="store" action="doChangePassword" name="resetpasswordForm">
        <div class="errorMessage">${flash.message}</div>
        <input type="hidden" value="${params.email}" name="email" name="email">
        <p><label>原密码：</label><input name="userPassword" id="userPassword" type="password" class="sr"/></p>
        <p><label>新密码：</label><input name="new_userPassword" id="new_userPassword" type="password" class="sr"/></p>
        <p><label>重复密码：</label><input name="again_userPassword" id="again_userPassword" type="password" class="sr"/></p>
        <p style="margin-top:30px;" >
        	<input class="comm_btn" type="button" onclick="submitResetpasswordForm();" value="重置密码"/>
        </p>
    </g:form>
</div>
<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
<script type="text/javascript" src="${resource(dir: 'js/user', file: 'resetpassword.js')}"></script>
</body>
</html>