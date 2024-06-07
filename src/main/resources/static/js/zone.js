document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            currentComponent: '', //当前选择的模块,未设置默认界面
            papers: [],
            currentPage: 1,
            totalPages: 1,
            pageSize: 1,
            batchProcessing: false, // 是否处于批量处理状态
            pidList: [],
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
                    this.totalPages = listData.data.paperCount / this.pageSize;
                    if (listData.data.paperCount % this.pageSize != 0) {
                        this.totalPages = this.totalPages + 1;
                    }
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            },
            // 显示我的问卷列表
            showPapers() {
                this.currentPage = 1;
                this.fetchMyPapers(this.currentPage);
                this.currentComponent = 'myPapers';
            },
            prevPage() {
                if (this.currentPage > 1) {
                    this.currentPage--;
                    this.fetchMyPapers(this.currentPage);
                }
            },
            nextPage() {
                if (this.currentPage < this.totalPages) {
                    this.currentPage++;
                    this.fetchMyPapers(this.currentPage);
                }
            },
            // 填写问卷
            fillPaper(pid) {
                // 跳转到填写问卷页面，并传递问卷ID
                window.location.href = '../pages/surveyFill.html?pid=' + pid;
            },
            // 修改问卷
            modifyPaper(pid) {
                window.location.href = '../pages/surveyModify.html?pid=' + pid;
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
                        if (response.data.data === 0) {
                            alert('删除成功！');
                            // 清空选中的问卷数组
                            this.pidList = [];
                            // 重新显示问卷列表
                            this.showPapers();
                        }
                    })
                    .catch(error => {
                        console.error('Error deleting papers:', error);
                    });
            },
            // 其他选项，待编辑
            showOthers() {
                this.currentComponent = 'others';
            }
        },

    })
})