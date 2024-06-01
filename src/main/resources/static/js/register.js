document.addEventListener("DOMContentLoaded", function(event) {
    var app = new Vue({
        el: '#register',
        data: {
            username: '',
            password: '',
            confirmPassword: '',
            passwordStrength: ''
        },
        methods: {
            // 密码强度检测
            checkPasswordStrength: function() {
                var password = this.password;
                var strengthBadge = document.getElementById("password-strength");

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

                var strengthLevel = 0;

                if (password.length > 8) {
                    strengthLevel++;
                }
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

                strengthBadge.style.display = "block";
                strengthBadge.innerText = strength[strengthLevel];
                strengthBadge.className = "strength-" + strengthLevel;
            },
            // 确认密码和注册
            validateAndRegister: function() {
                if (this.password !== this.confirmPassword) {
                    alert("密码和确认密码不匹配！");
                } else {
                    // 如果密码和确认密码匹配，则执行注册逻辑
                    this.register();
                }
            },
            // 注册
            register: function() {
                var formData = new FormData();
                formData.append('username', this.username);
                formData.append('password', this.password);
                axios.post('/user/register', formData)
                    .then(response => {
                        if (response.data.code === 0)
                            window.location.href = '../pages/login.html';
                        else {
                            alert("用户名被占用!尝试其他用户名！");
                        }
                    });
            }
        }
    });
});