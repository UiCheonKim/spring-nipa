// 메인페이지 탭버튼
$(document).ready(function() {
    $(".tabBtn>li").click(function() {
        var tNum = $(this).index();
        $(this).addClass("active")
            .siblings("li").removeClass("active");
        $(".tabList>div").eq(tNum).css("display", "block")
            .siblings().css("display", "none");
    })
})

// 팝업
$(document).ready(function() {
    $(".popup_btn").click(function() {
        $("#popup").show();
    });
    $(".close").click(function() {
        $("#popup").hide();
    });
});

window.addEventListener("DOMContentLoaded", (event) => {
    console.log("DOM fully loaded and parsed");
});