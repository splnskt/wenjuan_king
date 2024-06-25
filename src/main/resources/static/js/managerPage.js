const returnMain = document.getElementById('returnMain');
returnMain.addEventListener('click', function () {
    axios.post('/user/logout')
        .then(response => {
            console.log(response.data);
            if (response.data.code == 0) {
                this.isLogin = false;
            }
        })
        .catch(error => {
            console.error('Error deleting papers:', error);
        });
    window.location.href = '../pages/mainpage.html';
});

document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            currentComponent: '', //当前选择的模块,未设置默认界面
            name: null,
            papers: [],
            users: [],
            currentPage: 1,
            totalPages: 1,
            pageSize: 1,
            batchProcessing: false, // 是否处于批量处理状态
            pidList: [],
            uidList: [],
            userAvatarUrl: '../default.jpg', // 默认头像路径
            //showModal: false,
        },
        methods: {
            //请求问卷列表
            async fetchPapers(page) {
                //修改页面大小
                this.pageSize = 5;
                try {
                    const response = await axios.get('/paper/paper-lists', {
                        params: {
                            name: this.name,
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
            // 请求用户列表
            async fetchUsers(page) {
                //修改页面大小
                this.pageSize = 5;
                try {
                    const response = await axios.get('/user/all-user', {
                        params: {
                            page: page,
                            pageSize: this.pageSize
                        }
                    });
                    var listData = response.data;
                    this.users = listData.data.users;
                    this.totalPages = listData.data.userCount / this.pageSize;
                    this.totalPages = Math.ceil(this.totalPages);
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            },
            // 显示问卷列表
            showPapers() {
                this.currentPage = 1;
                this.fetchPapers(this.currentPage);
                this.currentComponent = 'paperList';
                this.batchProcessing = false;
                this.uidList = [];
                this.pidList = [];
            },
            // 显示用户列表
            showUsers() {
                this.currentPage = 1;
                this.fetchUsers(this.currentPage);
                this.currentComponent = 'userList';
                this.batchProcessing = false;
                this.uidList = [];
                this.pidList = [];
            },
            // 显示个人资料
            showUser() {
                this.currentComponent = 'user';
            },

            //页面操作
            prevPage() {
                if (this.currentPage > 1) {
                    this.currentPage--;
                    if (this.currentComponent === 'paperList') {
                        this.fetchPapers(this.currentPage);
                    } else if (this.currentComponent === 'userList') {
                        this.fetchUsers(this.currentPage);
                    };
                }
            },
            nextPage() {
                if (this.currentPage < this.totalPages) {
                    this.currentPage++;
                    if (this.currentComponent === 'paperList') {
                        this.fetchPapers(this.currentPage);
                    } else if (this.currentComponent === 'userList') {
                        this.fetchUsers(this.currentPage);
                    };
                }
            },
            // 批量管理
            changeBatch() {
                this.batchProcessing = !this.batchProcessing;
            },
            // 加入黑名单
            black(uid) {
                var formData = new FormData();
                formData.append('uid', uid);
                axios.post('/user/ban-user', formData)
                    .then(response => {
                        console.log(response.data);
                        // 重新显示问卷列表
                        this.showUsers();
                    })
                    .catch(error => {
                        console.error('Error deleting papers:', error);
                    });
            },
            // 解除黑名单
            cancel(uid) {
                var formData = new FormData();
                formData.append('uid', uid);
                axios.post('/user/unban-user', formData)
                    .then(response => {
                        console.log(response.data);
                        // 重新显示问卷列表
                        this.showUsers();
                    })
                    .catch(error => {
                        console.error('Error deleting papers:', error);
                    });
            },
            //删除用户
            deleteUsers() {
                if (this.uidList.length === 0) {
                    alert('请选择要删除的用户！');
                    return;
                };
                var formData = this.uidList;
                axios.post('/user/delete-user', formData)
                    .then(response => {
                        console.log(response.data);
                        if (response.data.code === 0) {
                            alert('删除成功！');
                            // 清空选中的问卷数组
                            this.uidList = [];
                            //恢复批量选择
                            this.batchProcessing = false;
                            // 重新显示问卷列表
                            this.showUsers();
                        }
                    })
                    .catch(error => {
                        console.error('Error deleting papers:', error);
                    });
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
                var url = '../pages/surveyResult.html?pid=' + pid;
                window.open(url, '_blank'); 
                // '_blank'参数告诉浏览器在新窗口或标签页中打开链接
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
                        console.error('Error deleting papers:', error);
                    });
            },
        },
        mounted() {
            this.showUsers();
            this.getAvatar();
        },
    })
});
