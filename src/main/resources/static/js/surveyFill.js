document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            surveyData: null,
            pid,
            answers: []
        },
        methods: {
            async fetchData() {
                try {
                    const response = await axios.get('/paper/view-paper?id=4askfj1093jfi9348oueir932', {
                        params: {
                            pid: this.pid,
                        }
                    });
                    this.surveyData = response.data;
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
                this.surveyData.data.questions.forEach(() => {
                    this.answers.push({
                        qid: question.qid,
                        questionType: question.questionType,
                        answerContent: null
                    });
                });
            },
            electOption(index, option) {
                // 单选题只能选择一个选项，因此每次选择时，先清空答案数组中对应问题的选项
                this.answers[index].answerContent = option;
            },
            toggleOption(index, option) {
                // 多选题可以选择多个选项，因此需要判断当前选项是否已经存在于答案数组中
                const indexInAnswers = this.answers[index].answerContent.indexOf(option);
                if (indexInAnswers !== -1) {
                    // 如果当前选项已经存在于答案数组中，则移除它
                    this.answers[index].answerContent.splice(indexInAnswers, 1);
                } else {
                    // 如果当前选项不存在于答案数组中，则添加它
                    this.answers[index].answerContent.push(option);
                }
            },
            submit() {
                var formData={
                    pid:this.pid,
                    answers:this.answers
                }
                console.log('答案:', this.answers);

                axios.post('/answer/commit-paper', formData)
                    .then(response => {
                        // 处理后端返回的响应
                        var getData = response.data;
                        console.log('code:', data.code);
                        console.log('msg:', data.msg);

                        //成功后跳转，首页
                        if(getData.code===0){
                            alert("提交成功！");
                        window.location.href = '../pages/mainpage.html';
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            }
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