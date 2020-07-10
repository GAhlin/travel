/*
* 表单校验：
* 		1、用户名:单词字符，长度8到20位
* 		2、密码：单词字符，长度8到20位
* 		3、email:邮件格式
* 		4、姓名：非空
* 		5、手机号：手机号码格式
* 		6、出生日期：非空
* 		7、验证码：非空
* */
//校验用户名
function checkUsername() {
    //1、获取用户名值
    let username = $("#username").val();
    //2、定义正则
    let reg_username = /^\w{8,20}$/;
    //3、判断，给出提示信息
    let flag = reg_username.test(username);
    if (flag) {
        //用户名合法
        $("#username").css("border", "");
    } else {
        //用户名非法,加一个红色边框
        $("#username").css("border", "1.3px solid red");
    }
    return flag;
}

//校验密码
function checkPassword() {
    //1、获取密码值
    let password = $("#password").val();
    //2、定义正则
    let reg_password = /^\w{8,20}$/;
    //3、判断，给出提示信息
    let flag = reg_password.test(password);
    if (flag) {
        //密码合法
        $("#password").css("border", "");
    } else {
        //密码非法,加一个红色边框
        $("#password").css("border", "1.3px solid red");
    }
    return flag;
}

//校验名字
function checkName() {
    //1、获取邮箱
    let name = $("#name").val();

    //2、判断是否为空
    let flag = null;
    if (name == "" || name == null ) {
        $("#name").css("border", "1.3px solid red");
        flag = false;
    } else {
        $("#name").css("border", "");
        flag = true;
    }
    return flag
}

//校验邮箱
function checkEmail() {
    //1、获取邮箱
    let email = $("#email").val();
    //2、定义正则
    let reg_email = /^\w+@\w+\.\w+$/;
    //3、判断
    let flag = reg_email.test(email);
    if (flag) {
        $("#email").css("border", "");
    } else {
        $("#email").css("border", "1.3px solid red");
    }
    return flag
}

//校验手机号
function checkPhone() {
    let telephone = $("#telephone").val();
    let reg_telephone = /^[1][3,4,5,7,8][0-9]{9}$/;
    let flag = reg_telephone.test(telephone);
    if (flag) {
        $("#telephone").css("border", "");
    } else {
        $("#telephone").css("border", "1.3px solid red");
    }
    return flag;
}

$(function () {
    //当表单提交时，调用所有的校验方法
    $("#registerForm").submit(function () {
        //1、发送数据到服务器
        if (checkUsername() && checkPassword() && checkEmail()&& checkName() && checkPhone()) {
            //校验通过，发送ajax请求，提交表单的数据

            $.post("user/register",$(this).serialize(),function (data) {
                //处理服务器响应的数据 data

                if(data.flag){
                    //注册成功，跳转成功页面
                    location.href="register_ok.html";
                }else{
                    //注册失败,给errorMsg添加提示信息
                    $("#errorMsg").html(data.errorMsg);
                }
            });
        }

        //2、跳转页面
        return false;
        //如果这个方法没有返回值，或者返回为true,则表单提交，如果返回为false，则表单不提交
    });

    //当某一个组件失去焦点时，调用对应的校验方法
    $("#username").blur(checkUsername);
    $("#password").blur(checkPassword);
    $("#email").blur(checkEmail);
    $("#name").blur(checkName);
    $("#telephone").blur(checkPhone);
});