<%@ page contentType="text/html"%>
    <!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Welcome to work.com.mm</title>
    </head>

    <body>您好 ${userName},
    点击下面的链接重置您的密码
    <a href="${website}/user/emailresetpassword?email=${email}&c=${randomCode}">${website}/user/emailresetpassword?email=${email}&c=${randomCode}</a>
    </body>
    </html>



