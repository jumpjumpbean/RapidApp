
var submitResetpasswordForm = function()
{
    result =  $("#resetpasswordForm").validate().form();
    if ( result )
    {
        $("#resetpasswordForm").submit();
    }else{
    }
}


$(document).ready(function ()
{
    var validRules = {
    	userPassword:{
            required:true
        },
        new_userPassword:{
            required:true
        },
        again_userPassword:{
            required:true,
            equalTo:"#new_userPassword"
        }
    };
    var validMessages = {
    	userPassword:{
            required:"原密码不能为空!"
        },
        new_userPassword:{
            required:"新密码不能为空!"
        },
        again_userPassword:{
            required:"重复密码不能为空!",
            equalTo:"新密码和重复密码不一致!"
        }
    };
    if($( '#userPassword' )){
        validRules['userPassword'] = {
            required:true
        };
        validMessages['userPassword'] = {
            required:"原密码不能为空!"
        };
    }
    $( '#resetpasswordForm' ).validate
    (
        {
            rules:validRules,
            messages:validMessages
            
        }
    );

} );
