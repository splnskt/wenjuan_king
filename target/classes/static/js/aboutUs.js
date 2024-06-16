
const returnButton = document.getElementById('returnButton');

// 添加点击事件监听器
returnButton.addEventListener('click', function() {
    // 使用 window.location.href 导航到主页的 URL
    window.location.href = '../pages/mainpage.html'; // 替换成你的主页路径
});
