var Login = function () {
    
    return {
        //main function to initiate the module
        init: function () {
        	
           $('.login-form').validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                }
	            },

	            messages: {
	                username: {
	                    required: "用户名不能为空"
	                },
	                password: {
	                    required: "密码不能为空"
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	                $('.alert-error', $('.login-form')).show();
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	            	//验证通过
	            	var ism = $("#remember").attr('checked');
	            	if(ism){
	            		$.cookie("curr_user",$("input[name='username']").val()+","+$("input[name='password']").val());
	            	}else{
	            		$.cookie("curr_user",null);
	            	}
	            	form.submit();
	            }
	        });
            var user_cookie = $.cookie("curr_user");
            if(user_cookie!=null && user_cookie != "" && user_cookie !="null"){
            	$("#checkbox_div").html('<input type="checkbox" name="remember" checked="checked" id="remember" style="margin-left:0px;" value="1" />记住我');
            	var arr = user_cookie.split(',');
            	$("input[name='username']").val(arr[0]);
            	$("input[name='password']").val(arr[1]);
            }else{
            	$("#checkbox_div").html('<input type="checkbox" name="remember" id="remember" style="margin-left:0px;" value="1" />记住我');
            }
	        $('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
	                	if($("#remember").attr('checked')){
		            		$.cookie("curr_user",$("input[name='username']").val()+","+$("input[name='password']").val());
		            	}
	                	$('.login-form').submit();
	                }else{
	            		$.cookie("curr_user",null);
	            	}
	                return false;
	            }
	        });

        }

    };

}();