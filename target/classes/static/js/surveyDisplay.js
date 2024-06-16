document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            surveyData: null,
            //id暂未获取
            pid: ''
        },
        methods: {
            fetchData() {
                var formData = new FormData();
                formData.append('pid', this.pid);
                axios.post('/paper/view-template', formData)
                    .then(response => {
                        console.log(response.data);
                        this.surveyData = response.data;
                    })
                    .catch(error => {
                        console.error('Error deleting papers:', error);
                    });
            },
            // 使用模版
            use(pid) {
                window.location.href = '../pages/surveyModify.html?pid=' + pid;
            },
            // 点赞
            like() {
                var formData = new FormData();
                formData.append('pid', this.pid);
                axios.post('/paper/like', formData)
                    .then(response => {
                        console.log(response.data);
                    })
                    .catch(error => {
                        console.error('Error deleting papers:', error);
                    });
            },
        },
        mounted() {
            const urlParams = new URLSearchParams(window.location.search);
            this.pid = urlParams.get('pid');
            if (!this.pid) {
                console.error('未提供问卷ID');
            }
            this.fetchData();
        }
    })
});