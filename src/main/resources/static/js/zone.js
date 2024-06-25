const returnMain = document.getElementById('returnMain');
returnMain.addEventListener('click', function () {
    window.location.href = '../pages/mainpage.html';
});

document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            username: '',
            currentComponent: '', //当前选择的模块,未设置默认界面
            paperCount: 0,
            papers: [],
            currentPage: 1,
            totalPages: 1,
            pageSize: 1,
            batchProcessing: false, // 是否处于批量处理状态
            pidList: [],
            userAvatarUrl: '../default.jpg', // 默认头像路径
            //showModal: false,
        },
        methods: {
            //请求我的问卷列表
            async fetchMyPapers(page) {
                //修改页面大小
                this.pageSize = 5;
                try {
                    const response = await axios.get('/paper/my-papers', {
                        params: {
                            page: page,
                            pageSize: this.pageSize
                        }
                    });
                    var listData = response.data;
                    this.papers = listData.data.papers;
                    this.paperCount = listData.data.paperCount;
                    this.totalPages = listData.data.paperCount / this.pageSize;
                    this.totalPages = Math.ceil(this.totalPages);
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            },
            // 请求我的模版列表
            async fetchMyTemplates(page) {
                //修改页面大小
                this.pageSize = 5;
                try {
                    const response = await axios.get('/paper/my-template', {
                        params: {
                            page: page,
                            pageSize: this.pageSize
                        }
                    });
                    var listData = response.data;
                    this.papers = listData.data.papers;
                    this.totalPages = listData.data.paperCount / this.pageSize;
                    this.totalPages = Math.ceil(this.totalPages);
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            },
            // 显示我的问卷列表
            showPapers() {
                this.currentPage = 1;
                this.fetchMyPapers(this.currentPage);
                this.currentComponent = 'myPapers';
                this.batchProcessing = false;
            },
            // 显示我的模版列表
            showTemplates() {
                this.currentPage = 1;
                this.fetchMyTemplates(this.currentPage);
                this.currentComponent = 'myTemplates';
                this.batchProcessing = false;
            },
            // 显示个人资料
            showUser() {
                this.currentComponent = 'user';
            },

            //页面操作
            prevPage() {
                if (this.currentPage > 1) {
                    this.currentPage--;
                    if (this.currentComponent === 'myPapers') {
                        this.fetchMyPapers(this.currentPage);
                    } else if (this.currentComponent === 'myTemplates') {
                        this.fetchMyTemplates(this.currentPage);
                    };
                }
            },
            nextPage() {
                if (this.currentPage < this.totalPages) {
                    this.currentPage++;
                    if (this.currentComponent === 'myPapers') {
                        this.fetchMyPapers(this.currentPage);
                    } else if (this.currentComponent === 'myTemplates') {
                        this.fetchMyTemplates(this.currentPage);
                    };
                }
            },
            // 分享问卷
            sharePaper(pid) {
                var shareUrl = 'localhost:8080/pages/surveyFill.html?pid=' + pid;
                navigator.clipboard.writeText(shareUrl)
                    .then(() => {
                        alert('分享链接已复制到剪贴板:\n' + shareUrl);
                    })
                    .catch(err => {
                        console.error('复制失败:', err);
                        // 在这里可以提供一个备选方案，例如通过提示用户手动复制链接
                        alert('复制失败，请手动复制链接:\n' + shareUrl);
                    });
            },
            // 填写问卷
            fillPaper(pid) {
                // 跳转到填写问卷页面，并传递问卷ID
                window.location.href = '../pages/surveyFill.html?pid=' + pid;
            },
            // 使用模版
            use(pid) {
                window.location.href = '../pages/templateUse.html?pid=' + pid;
            },
            // 修改问卷
            modifyPaper(pid) {
                window.location.href = '../pages/surveyModify.html?pid=' + pid;
            },
            // 修改模版
            modifyTemplate(pid) {
                window.location.href = '../pages/templateModify.html?pid=' + pid;
            },
            // 批量管理
            changeBatch() {
                this.batchProcessing = !this.batchProcessing;
            },
            // 删除问卷
            deletePapers() {
                if (this.pidList.length === 0) {
                    alert('请选择要删除的问卷！');
                    return;
                };
                var formData = this.pidList;
                axios.post('/paper/delete-paper', formData)
                    .then(response => {
                        console.log(response.data);
                        if (response.data.code === 0) {
                            alert('删除成功！');
                            // 清空选中的问卷数组
                            this.pidList = [];
                            //恢复批量选择
                            this.batchProcessing = false;
                            // 重新显示问卷列表
                            this.showPapers();
                        }
                    })
                    .catch(error => {
                        console.error('Error deleting papers:', error);
                    });
            },
            //显示结果
            viewResult(pid) {
                window.location.href = '../pages/surveyResult.html?pid=' + pid;
            },
            // 其他选项，待编辑
            showOthers() {
                this.currentComponent = 'others';
            },

            // 个人资料
            // showAvatarModal() {
            //     this.showModal = true;
            // },
            // closeModal() {
            //     this.showModal = false;
            // },
            openImageInNewTab() {
                // 打开新窗口显示放大的图片
                window.open(this.userAvatarUrl, '_blank');
            },
            changeAvatar(event) {
                const file = event.target.files[0];

                // 创建一个 FormData 对象，用于将文件数据传输到后端
                let formData = new FormData();
                formData.append('image', file);
                console.log('FormData:', formData);
                // 发送文件到后端的示例 Axios 请求
                axios.post('/user/upload-image', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
                    .then(response => {

                        if (response.data.code == 0) {
                            // 提示用户头像更新成功或其他操作
                            alert('头像更新成功！');
                        }
                    })
                    .catch(error => {
                        console.error('头像上传失败:', error);
                        alert('头像上传失败，请稍后再试。');
                    });

                this.getAvatar();
            },

            getAvatar() {
                axios.post('/user/image')
                    .then(response => {
                        console.log(response.data);
                        if (response.data.data != null) {
                            // 假设后端返回新头像的路径，更新前端显示
                            this.userAvatarUrl = response.data.data;
                        } else;
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            },
            getUsername() {
                axios.post('/user/user-info')
                    .then(response => {
                        console.log(response.data);
                        this.username = response.data.data.username;
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            },
        },
        mounted() {
            this.showPapers();
            this.getAvatar();
            this.getUsername();
        },
    })
});
