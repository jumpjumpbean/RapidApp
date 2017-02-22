
app.user.submitRegisterForm = function()
{
//    $("#registerForm").validate().settings.ignore = 'disabled';
    result =  $("#registerForm").validate().form();
    if ( result )
    {
        $("#registerForm").submit();
    }else{
    }
}

jQuery.validator.addMethod
(
    'emailExist',
    function( value, element )
    {
        var result = false;

        if ( !value ) { return result; }


        var url = app.contextPath + '/ajax/validEmailIsExisted';
        var emailData = { 'email': value };

        $.ajax(
            {
                'async': false,
                'type': 'post',
                'url': url,
                'timeout': '1000',
                'dataType': 'text',
                'data': emailData,
                beforeSend: function( xhr )
                {
                    //show loading();
                },
                success: function( res, textStatus )
                {
                    result = !( res - 0 )
                },
                complete: function( xhr, textStatus )
                {
                    //hide loading();
                },
                error: function( xhr, textStatus, errorThrown )
                {
                    // process error
                    result = false;
                    msg = textStatus;
                }
            } );
        return result;
    },
    "Email已经被使用"
);




$(document).ready(function ()
{
    $( '#registerForm' ).validate
    (
        {
            rules:{
                userEmail:{
                    emailExist: true,
                    required:true,
                    email:true
                },
                userPassword:{
                    required:true,
                    rangelength:[6, 32]
                },
                userName:{required:true}
            },
            messages:{
                userEmail:{
                    required:"Email不能为空!",
                    email:"Email格式不合法！",
                    emailExist:'Email已经被注册！'
                },
                userPassword:{
                    required:"密码不能为空！",
                    rangelength:"密码长度必须在6-32之间!"
                },
                userName:{
                    required:"名称不能为空!"
                }
            }
        }
    );


} );
