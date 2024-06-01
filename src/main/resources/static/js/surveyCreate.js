document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            title: '',
            startTime: '',
            endTime: '',
            status: 0,
            questions: [] //问题数组
        },
        methods: {
            //加问题
            addQuestion() {
                this.questions.push({
                    questionType: 1,
                    questionTitle: '',
                    questionOption: []
                }); // 添加一个新问题
            },
            //更新
            updateOptions(index) {
                // 根据问题类型重置问题对象的属性
                switch (this.questions[index].questionType) {
                    case "1": // 单选
                        break;
                    case "2": // 多选
                        break;
                    case "3": // 填空
                        var newOption = [{ content: '' }];
                        this.questions[index].questionOption = newOption; // 清空选项
                        break;
                    default:
                        break;
                }
            },
            //删问题
            removeQuestion(index) {
                this.questions.splice(index, 1); // 删除指定索引的问题
            },
            //加选项
            addOption(questionIndex) {
                this.questions[questionIndex].questionOption.push({ content: '' });
            },
            //删选项
            removeOption(questionIndex, optionIndex) {
                this.questions[questionIndex].questionOption.splice(optionIndex, 1);
            },
            submit: function () {

                // 遍历问题数组，提取每个问题对象的 questionOptions 属性的 content 值，并存储到新的字符串数组中
                this.questions.forEach(question => {
                        var newQuestionOptions = [];
                        question.questionOption.forEach(option => {
                            newQuestionOptions.push(option.content);
                        });
                    question.questionOption = newQuestionOptions;
                });

                var formData = {
                    title: this.title,
                    startTime: this.startTime,
                    endTime: this.endTime,
                    status: this.status,
                    questions: this.questions
                };

                console.log('问卷标题:', this.title);
                console.log('开始时间:', this.startTime);
                console.log('结束时间:', this.endTime);
                console.log('状态:', this.status);
                console.log('问卷内容:', this.questions);

                // 发送 POST 请求
                axios.post('/paper/update-paper', formData)
                    .then(response => {
                        // 处理后端返回的响应
                        var getData = response.data;
                        console.log('code:', data.code);
                        console.log('msg:', data.msg);
                        console.log('data:', data.data);

                        //成功后跳转，首页
                        if(getData.data===0){
                        window.location.href = '../pages/mainpage.html';
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            }
        }
    });

});