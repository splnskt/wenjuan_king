document.addEventListener("DOMContentLoaded", function (event) {
    var app = new Vue({
        el: '#app',
        data: {
            name: '',
            papers: [],
            currentPage: 1,
            totalPages: 1,
            pageSize: 1
        },
        methods: {
            async fetchData(page) {
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
            search() {
                this.currentPage = 1;
                this.fetchData(this.currentPage);
            },
            prevPage() {
                if (this.currentPage > 1) {
                    this.currentPage--;
                    this.fetchData(this.currentPage);
                }
            },
            nextPage() {
                if (this.currentPage < this.totalPages) {
                    this.currentPage++;
                    this.fetchData(this.currentPage);
                }
            },
            fillPaper(pid) {
                // 跳转到填写问卷页面，并传递问卷ID
                window.location.href = '../pages/surveyFill.html?pid=' + pid;
            },
            sharePaper(pid) {
                var shareUrl = '../pages/surveyFill.html?pid=' + pid;
                // 弹窗显示分享路径
                alert('分享链接：' + shareUrl + '\n请复制链接进行分享。');
            },
        },
        mounted() {
            this.fetchData(this.currentPage);
        },
    });
});