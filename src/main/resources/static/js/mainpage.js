
document.addEventListener("DOMContentLoaded", function (event) {
  var Items = new Vue({
    el: '.navbar',
    data: {
      navItems: [
        { id: 1, text: '主页', link: '../pages/mainpage.html' },
        { id: 2, text: '联系我们', link: '../pages/aboutUs.html' },
        { id: 3, text: '使用说明', link: '../pages/help.html' },
      ]

    },
    methods:
    {
      Jump(index) {
        window.location.href = this.navItems[index].link;
      }



    }

  });
})
document.addEventListener("DOMContentLoaded", function (event) {
  var options = new Vue({
    el: '.personalZone',
    data: {
      showOptions: false,
      options: [
        { text: '问卷中心', link: '../pages/surveyList.html' },
        { text: '创建问卷', link: '../pages/surveyCreate.html' },
        { text: '模版中心', link: '../pages/templateList.html' },
        { text: '个人中心', link: '../pages/Zone.html' },

      ]

    },
    methods:
    {
      highlightOption(index) {
        // 处理选项悬停时的特效
        // 这里可以添加处理选项悬停时的特效的代码
      },
      hideOptions() {
        this.showOptions = false;
      },

      Jump(index) {
        window.location.href = this.options[index].link;
        event.stopPropagation();
      }
    }

  });
})
document.addEventListener("DOMContentLoaded", function (event) {
  var photo = new Vue({
    el: '.swiper-container',
    methods:
    {
      GotoRecomandation: function () {
        window.location.href = '../pages/login.html';
      }
    }
  });
});
document.addEventListener("DOMContentLoaded", function (event) {
  var mySwiper = new Swiper('.swiper-container', {
    loop: true,
    autoplay: {
      delay: 3000, // 自动播放间隔时间，单位为毫秒
    },
    pagination: {
      el: '.swiper-pagination',
    },
    // 添加其他配置项
  });

  var swiperContainer = document.querySelector('.swiper-container');

  swiperContainer.addEventListener('mouseenter', function () {
    mySwiper.autoplay.stop();
  });

  swiperContainer.addEventListener('mouseleave', function () {
    mySwiper.autoplay.start();
  });

})


// 处理登录事件
document.addEventListener("DOMContentLoaded", function (event) {
  var a = new Vue({
    el: '#userLog',
    data: {
      isLogin: false,
    },
    methods: {
      fetchIsLogin() {
        axios.post('/user/image')
          .then(response => {
            console.log(response.data);
            if (response.data.code == -1) {
              this.isLogin = false;
            }
            else {
              this.isLogin = true;
            }
          })
          .catch(error => {
            console.error('Error deleting papers:', error);
          });
      },

      // 登出
      logoutButton() {
        axios.post('/logout')
          .then(response => {
            console.log(response.data);
            if (response.data.code == 0) {
              this.isLogin = false;
            }
          })
          .catch(error => {
            console.error('Error deleting papers:', error);
          });
        this.fetchIsLogin();
      },
    },
    mounted() {
      this.fetchIsLogin();
    },
  });
});





