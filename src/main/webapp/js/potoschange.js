/*图片切换*/
$(function () {
    scrollingImg();
});
var nowShow = 2, scrollimgs;
function imgScroll(showNum, titleNum) {
    window.clearInterval(scrollimgs);
    document.getElementById("scrollimg2").className = "tagon" + showNum;
    nowShow = showNum;
    nowShow++;
    if (nowShow > titleNum) { nowShow = 1; }
    scrollingImg();
}
function scrollingImg() {
    if (document.getElementById("scrollimg2") != "" && document.getElementById("scrollimg2") != null) {
        var titleNum = document.getElementById("scrollimg2").getElementsByTagName("dt").length;

        scrollimgs = window.setInterval(function () { imgScroll(nowShow, titleNum) }, 2000);
    }
}