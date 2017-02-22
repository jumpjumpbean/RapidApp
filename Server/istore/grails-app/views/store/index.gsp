<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>完善资料</title>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'store.css')}" type="text/css">
</head>
<body>
<div id="profile">
    <g:form controller="store" action="saveProfile" name="profileForm">
   		<p><label>登录帐号：</label>${loginUser?.userEmail}</p>
        <p><label>店铺名称：</label><input name="userName" value="${loginUser?.userName}" type="text" class="sr1" maxlength="80"/></p>
        <p><label>店铺地址：</label><input name="userAddress" value="${loginUser?.userAddress}" type="text" class="sr1"  maxlength="120"/></p>
        <p><label>联系电话：</label><input name="userPhone" value="${loginUser?.userPhone}" type="text" class="sr1"  maxlength="30"/></p>
        <p><label>店铺简介：</label><textarea name="storeDesc" class="sr2" cols="100" rows="5" maxlength="200">${loginUser?.storeDesc}</textarea></p>
        <p style="margin-top:30px;" >
        	<input name="" class="comm_btn" type="button" onclick="submitProfileForm()" value="保存修改"/>
        	<a href="${contextPath }/store/changePassword" class="comm_btn">修改密码</a>
        </p>
    </g:form>
</div>
<script type="text/javascript">
    function submitProfileForm(){
        $("#profileForm").submit();
    }
</script>
</body>
</html>