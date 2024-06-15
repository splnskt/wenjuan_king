document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            pid: '',
            title: '',
            startTime: '',
            endTime: '',
            status: 0,
            questions: [] //问题数组
        },
        methods: {
            async fetchData() {
                try {
                    const response = await axios.get('/paper/view-paper', {
                        params: {
                            pid: this.pid,
                        }
                    });
                    var surveyData = response.data;
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
                this.title = surveyData.data.title;
                this.startTime = surveyData.data.startTime;
                this.endTime = surveyData.data.endTime;
                this.status = surveyData.data.status;
                surveyData.data.questions.forEach((question) => {
                    question.questionOption = question.questionOption.map(option => ({ content: option }));
                    this.questions.push({
                        questionType: question.questionType,
                        questionTitle: question.questionTitle,
                        questionOption: question.questionOption,
                    });
                });
            },
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
                    pid: this.pid,
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

                // 发送 PUT 请求
                axios.put('/paper/update-paper', formData)
                    .then(response => {
                        // 处理后端返回的响应
                        var getData = response.data;
                        console.log('code:', getData.code);
                        console.log('msg:', getData.msg);
                        console.log('data:', getData.data);

                        //成功后跳转，首页
                        if (getData.data === 0) {
                            alert("创建成功！");
                            window.location.href = '../pages/mainpage.html';
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            }
        },
        mounted() {
            // 点击修改问卷
            const urlParams = new URLSearchParams(window.location.search);
            this.pid = urlParams.get('pid');
            if (!this.pid) {
                console.error('未提供问卷ID');
            }
            this.fetchData();
        }
    })
});
document.addEventListener("DOMContentLoaded", function (event) {
    var Items = new Vue({
        el: '.navbar',
        data: {
            navItems: [
                { id: 1, text: '主页', link: '../pages/mainpage.html' },
                { id: 2, text: '联系我们', link: '../pages/contact.html' },
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
                { text: '我的问卷', link: '../pages/Mysurveys.html' },

                { text: '数据统计', link: '../pages/DataAnalyze.html' },
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