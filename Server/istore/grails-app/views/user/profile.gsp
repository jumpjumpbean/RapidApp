<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>完善资料</title>
    <meta name="layout" content="blackbg"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'style1.css')}" type="text/css">
</head>
<body>
<div id="lmmc">账户中心</div>
<div id="lmmc1">完善资料</div>
<div id="profile">
    <g:form controller="user" action="saveProfile" name="profileForm">
        <p><label>真实姓名：</label><input name="userRealname" value="${userDetail?.userRealname}" type="text" class="sr1" /></p>
        <p><label>联系地址：</label><input name="userAddress" value="${userDetail?.userAddress}" type="text" class="sr1" /></p>
        <p><label>联系电话：</label><input name="userPhone" value="${userDetail?.userPhone}" type="text" class="sr1" /></p>
        <p><label>手机号码：</label><input name="userCellphone" value="${userDetail?.userCellphone}" type="text" class="sr1" /></p>
        <p><label>公司名称：</label><input name="company" value="${userDetail?.company}" type="text" class="sr1" /></p>
        <p><label>公司简介：</label><textarea name="companyDesc" class="sr2">${userDetail?.companyDesc}</textarea></p>
        <p style="margin-top:50px;"><label>&nbsp;</label><input name="" type="image" src="${resource(dir: 'images', file: 'anniu13.jpg')}" onclick="submitProfileForm()"/>
    </g:form>
</div>
<script type="text/javascript">
    function submitProfileForm(){
        $("#profileForm").submit();
    }
</script>
</body>
</html>