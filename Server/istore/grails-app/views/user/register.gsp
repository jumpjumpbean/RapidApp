<%
    String contextPath = request.contextPath;
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Istore</title>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'common.css')}" type="text/css">
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.9.1.min.js')}"></script>
    <g:render template="/common/contextJS"></g:render>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/user', file: 'common.js')}"></script>
    	<style type="text/css">
		body {
			text-align: center;
			}
		#center { margin:60px auto;}
	</style>
</head>

<body>
<div class="well" id="reg">
    <g:form controller="User" action="doRegister" name="registerForm">
        <div class="errorMessage">${flash.message}</div>
        <div class="hero-unit" style="position:relative;" id="center">
	        <dl>
                <div class="notice_lable"><span>店铺名称</span></div>
                <input type="text" class="in1" value="" name="userName" id="userName"
                       onkeydown="onKeyDownFun(this)"
                       onblur="onBlurFun(this)"
                       onchange="$(this).val( $.trim( $(this).val() ));"/>

            </dl>
	        <dl>
                <div class="notice_lable"><span>您的邮箱</span></div>
                <input type="text" class="in1" value="" name="userEmail" id="userEmail"
                       onkeydown="onKeyDownFun(this)"
                       onblur="onBlurFun(this)"/>
            </dl>
	        <dl>
                <div class="notice_lable"><span>设置密码</span></div>
                <input type="password" class="in1" value="" name="userPassword" id="userPassword"
                       onkeydown="onKeyDownFun(this)"
                       onblur="onBlurFun(this)"/>
            </dl>
	        <dl><input type="submit" class="btn btn-primary" value="注册" onclick="app.user.submitRegisterForm();"/></dl>
	        <span style="font-size:12px;color:grey;">点击注册，表示您已阅读并同意《本网站的服务条款》</span>
	        <p><a href="${contextPath}/login">已有帐号？<i>立即登录 →</i></a></p>
        </div>
    </g:form>
</div>
<div class="clear"></div>
<script type="text/javascript">
    function onBlurFun(elem){
        var item = $(elem);
        if(item.val() == ""){
            item.parent().find(".label").removeClass("input-focus");
        }
    }
    function onFocusFun(elem){
        var item = $(elem);
        if(item.val() != ""){
            item.parent().find(".label").removeClass("input-focus").addClass("input-focus");
        }
    }
    function onKeyDownFun(elem){
        var item = $(elem);
        if(item.val() == ""){
            item.parent().find(".label").removeClass("input-focus").addClass("input-focus");
        }
    }
</script>
</body>
</html>
