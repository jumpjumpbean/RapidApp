<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>istore</title>
</head>
<body>
<span style="color: red">${flash.message}</span>
<g:form controller="User" action="doEmailforgetpassword"  name="forgetpasswordForm" >
    <table>
                <tr>
                    <td>
                        邮箱
                    </td>
                    <td>
                        <input type="text" name="email" id="email" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                    <g:submitButton name="确定"></g:submitButton>
                </td>
            </tr>
    </table>
</g:form>
<script type="text/javascript">
    function login(){
//        var loginFormObj = $( '#login_form');
//
//        loginFormObj[ 0 ].submit();
        document.getElementById("login_form").submit();
    }
</script>
</body>
</html>