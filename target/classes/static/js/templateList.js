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
                    const response = await axios.get('填入模版地址', {
                        params: {
                            name: this.name,
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
            viewPaper(pid) {
                // 跳转到查看问卷页面，并传递问卷ID
                window.location.href = '../pages/surveyDisplay.html?pid=' + pid;
              }
        },
        mounted() {
            this.fetchData(this.currentPage);
        },
    });
});