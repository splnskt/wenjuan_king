document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            surveyData: null,
            //id暂未获取
            pid: '',

        },
        methods: {
            fetchData() {
                var formData = new FormData();
                formData.append('pid', this.pid);
                axios.post('/answer/paper-data', formData)
                    .then(response => {
                        console.log(response.data);
                        this.surveyData = response.data;
                        this.surveyData.data.questions.forEach(question => {
                            Vue.set(question, 'isShow', false); // 初始设置为未展开状态
                        });
                    })
                    .catch(error => {
                        console.error('Error deleting papers:', error);
                    });
            },
            show(index) {
                this.surveyData.data.questions[index].isShow=!this.surveyData.data.questions[index].isShow;
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