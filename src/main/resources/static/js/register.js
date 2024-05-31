function checkPasswordStrength() {
    var password = document.getElementById("password").value;
    var strengthBadge = document.getElementById("password-strength");

    // 如果密码为空，则不显示强度提示
    if (password === "") {
        strengthBadge.style.display = "none";
        return;
    }


    var strength = {
        0: "很弱",
        1: "较弱",
        2: "中等",
        3: "较强",
        4: "很强"
    };

    // 密码强度
    var strengthLevel = 0;

    // 如果密码长度大于 8 个字符，则强度加 1
    if (password.length > 8) {
        strengthLevel++;
    }

    // 如果密码中包含大写字母、小写字母、数字和特殊字符中的任意一个，则强度加 1
    if (password.match(/[a-z]+/)) {
        strengthLevel++;
    }
    if (password.match(/[A-Z]+/)) {
        strengthLevel++;
    }
    if (password.match(/[0-9]+/)) {
        strengthLevel++;
    }
    if (password.match(/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/)) {
        strengthLevel++;
    }

    // 设置强度级别及对应的颜色和描述
    strengthBadge.style.display = "block";
    strengthBadge.innerText = strength[strengthLevel];
    strengthBadge.className = "strength-" + strengthLevel;
}

function validateForm() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementsById("confirm_password")[0].value;

    if (password != confirmPassword) {
        alert("密码和确认密码不匹配！");
        return false;
    }
    return true;
}
document.addEventListener("DOMContentLoaded",function(event){
   var app=new Vue({
    el:'#register',
    data:{
        username:'',
        password:''
    },
    
    methods:{
        register:function()
        {
            var formData=new FormData();
            formData.append('username',this.username);
            formData.append('password',this.password);
            console.log('用户名：', this.username);
            console.log('密码：', this.password);
           axios.post('/user/register',formData)
            .then(response=>{
                if(response.data.code==='0')
                    window.location.href = '../pages/login.html';
                else{
                    alert("用户名被占用!尝试其他用户名！");
                }0

            })
            

        }

    }
   })
}
);
