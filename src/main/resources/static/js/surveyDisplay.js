document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            surveyData: null,
            //id暂未获取
            pid: ''
        },
        methods: {
            getPid() {
                return this.pid;
            },
            view() {
                var formData=new FormData();
                formData.append('pid', this.pid);
                console.log('问卷id:', this.pid);

                axios.post("/paper/view-paper", formData)
                    .then(response => {
                        this.surveyData = response.data;
                    })
                    .catch(error => {
                        console.error('Error fetching questionnaire:', error);
                    });
            }
        }
    })
});