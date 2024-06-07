document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            currentComponent: '', //未设置默认界面
            papers:[],
            currentPage: 1,
            totalPages: 1,
            pageSize: 1
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
            showPapers(){
                currentPage=1;
                this.fetchMyPapers(this.currentPage);
                this.currentComponent='myPapers';
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
            fillPaper(pid) {
                // 跳转到填写问卷页面，并传递问卷ID
                window.location.href = '../pages/surveyFill.html?pid=' + pid;
            },
            modifyPaper(pid){
                window.location.href = '../pages/surveyModify.html?pid=' + pid;
            },
            showOthers(){
                this.currentComponent='others';
            }
        },

    })
})