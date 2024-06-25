document.addEventListener("DOMContentLoaded", function (event) {
    // 等待页面加载完成后执行

    // 获取标题元素
    var h1 = document.getElementById("hello");
    var h2 = document.getElementById("welcome");

    // 添加淡入效果的类
    setTimeout(function () {
        h1.classList.add("show");
    }, 500); // 延迟0.5秒显示h1

    setTimeout(function () {
        h2.classList.add("show");
    }, 1500); // 延迟1.5秒显示h2
});
document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            username: '', // 用户名
            password: '', // 密码
            errorMessage: '' // 错误消息
        },
        methods: {

            login: function () {
                // 创建FormData对象，用于存储表单数据
                var formData = new FormData();
                formData.append('username', this.username);
                formData.append('password', this.password);

                // 检查是否被获取
                console.log('用户名：', this.username);
                console.log('密码：', this.password);//ll

                // 发送登录请求到后端
                axios.post('/user/login', formData)
                    .then(response => {
                        // 根据后端返回的结果处理登录逻辑
                        if (response.data.msg === 'ok') {
                            // 登录成功，可以进行跳转或其他操作
                            alert("welcome!");
                            if (response.data.data == 'admin') {
                                window.location.href = '../pages/managerPage.html';
                            } else {
                                window.location.href = '../pages/mainpage.html'; // 示例：跳转到首页
                            }
                        } else {
                            if (response.data.msg === 'user banned') {
                                alert("该用户在黑名单中！");
                            } else {
                                // 登录失败，显示错误消息
                                alert("账户或密码错误！");
                            }
                            this.errorMessage = response.data.message;
                        }
                    })
                    .catch(error => {
                        console.error('Error logging in:', error);
                        this.errorMessage = '登录出错！';
                    });


            }
        }
    });
});
document.addEventListener('mousemove', function (event) {
    var posX = event.clientX / window.innerWidth;
    var tempx = document.body.style.backgroundPositionX;
    document.body.style.backgroundPositionX = (posX * 50 - 200) + 'px';
})