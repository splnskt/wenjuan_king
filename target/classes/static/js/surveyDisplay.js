document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            surveyData: null,
            //id暂未获取
            pid: ''
        },
        methods: {
            async fetchData() {
                try {
                    const response = await axios.get('模版地址', {
                        params: {
                            pid: this.pid,
                        }
                    });
                    this.surveyData = response.data;
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            },
            use(pid){
                window.location.href = '../pages/surveyModify.html?pid=' + pid;
            },
        },
        mounted(){
            const urlParams = new URLSearchParams(window.location.search);
            this.pid = urlParams.get('pid');
            if (!this.pid) {
                console.error('未提供问卷ID');
            }
            this.fetchData();
        }
    })
});