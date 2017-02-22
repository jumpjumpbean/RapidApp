<%
    String contextPath = request.contextPath;
%>
<!doctype html>
<html>
  
  <head>
    <title>登录</title>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}" type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'common.css')}" type="text/css">
	<style type="text/css">
		body {
			text-align: center;
			}
		#center { margin:60px auto;}
	</style>
  </head>
  
  <body>
  <form action="${contextPath}/j_spring_security_check" method="POST" id="login_form">
    <div class="well" id="center">
      <div class="hero-unit" style="width:400px;" id="center">
       	<div style="color: red">${flash.message}</div>
        <p class="text-success welcome_title">Welcome to join Istore</p>
        <dl>
            <div class="notice_lable"><span>邮箱:</span></div>
            <input type="text" class="user" value="" name="j_username" id="loginUsername"
                   onkeydown="onKeyDownFun(this)"
                   onblur="onBlurFun(this)"
                   onchange="$(this).val( $.trim( $(this).val() ));"/>
        </dl>
        <dl>
            <div class="notice_lable"><span>密码:</span></div>
            <input type="password" class="pass" value="" name="j_password" id="loginPassword"
                   onkeydown="onKeyDownFun(this)"
                   onblur="onBlurFun(this)"
                   onkeypress="if(event.keyCode==13) {login();return false;}"/>
        </dl>
        <a class="btn btn-primary" onclick="login();">登录</a>
        <a class="btn" href="${request.contextPath}/user/register">注册</a><br>
        <a class="btn btn-link">忘记密码</a> 
      </div>
    </div>
    </form>
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
    function login(){
        document.getElementById("login_form").submit();
    }
</script>
</body>
</html>
