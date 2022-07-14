// mainpage tab버튼
$(document).ready(function () {
    $(".tabBtn>li").click(function () {
        var tNum = $(this).index();
        $(this).addClass("active")
            .siblings("li").removeClass("active");
        $(".tabList>div").eq(tNum).css("display", "block")
            .siblings().css("display", "none");
    })
})

$(document).ready(function () {
    $(".dateForTab1>li").click(function () {
        var sNum = $(this).index();
        $(this).addClass("active")
            .siblings("li").removeClass("active");
        $(".tabList>.tab1>ul").eq(sNum).css("display", "block")
            .siblings("ul").css("display", "none");
    })
})
$(document).ready(function () {
    $(".dateForTab2>li").click(function () {
        var sNum = $(this).index();
        $(this).addClass("active")
            .siblings("li").removeClass("active");
        $(".tabList>.tab2>ul").eq(sNum).css("display", "block")
            .siblings("ul").css("display", "none");
    })
})
$(document).ready(function () {
    $(".dateForTab3>li").click(function () {
        var sNum = $(this).index();
        $(this).addClass("active")
            .siblings("li").removeClass("active");
        $(".tabList>.tab3>ul").eq(sNum).css("display", "block")
            .siblings("ul").css("display", "none");
    })
})
$(document).ready(function () {
    $(".dateForTab4>li").click(function () {
        var sNum = $(this).index();
        $(this).addClass("active")
            .siblings("li").removeClass("active");
        $(".tabList>.tab4>ul").eq(sNum).css("display", "block")
            .siblings("ul").css("display", "none");
    })
})


// 페이징
$(document).ready(function () {
    $(".page_nation>.pageNum").click(function () {
        $(this).addClass("active")
            .siblings("a").removeClass("active");
    })
})

// 팝업
$(document).ready(function () {
    $(".quick_menu").click(function () {
        $("#mainpopup").show();
    });
    $(".close").click(function () {
        $("#mainpopup").hide();
    });
    $(".popup_btn").click(function () {
        $("#popup").show();
    });
    $(".close").click(function () {
        $("#popup").hide();
    });
});

// 자세히 보기
$(document).ready(function () {
    $(".block_more_btn").click(function () {
        $(".more_table").css("display", "block");
    })
})

// 상세기술

$(document).ready(function () {

    var SkillColor = ["#004685", "#125da2", "#1279d9", "#138afa", "#4faaff"];

    for (i = 0; i < SkillColor.length; i++) {
        $("#real>div").eq(i).children(".more_btn").css("background", SkillColor[i]);
    }
    // 버튼 클릭시
    $(".skill_list_btn>li").click(function (e) {
        e.preventDefault();
        var sSkill = $(this).index();
        console.log(SkillColor[sSkill]);

        //원배경색 생성
        $(".introduce_skill .circle").css("background", SkillColor[sSkill])
        $("#real>div").eq(sSkill).stop().fadeIn(300).siblings().stop().fadeOut(0);
        var w = $(".realGood>div").eq(sSkill).width() / 2;
        var moveX;
        if (sSkill == 0) {
            moveX = 1000 - w;
        } else {
            moveX = 1000 - w;
        }
        $(".realGood>div").eq(sSkill).stop().animate({
            "right": moveX + "px",
            "opacity": 1
        }, 500).siblings().stop().animate({
            "right": "-200px",
            "opacity": 0
        }, 500)
        $(".rightCircle>div").eq(sSkill).stop().fadeIn(300).css({ "transform": "rotate(-150deg)" }).siblings().css({
            "display": "none",
            "transform": "rotate(90deg)"
        });
        $(this).addClass("active").siblings("li").removeClass("active");
    });

    // 오른쪽 카테고리 클릭시
    $(".skill_list>li").click(function (e) {
        e.preventDefault();
        var sSkill = $(this).index();
        console.log(SkillColor[sSkill]);

        //원배경색 생성
        $(".introduce_skill .circle").css("background", SkillColor[sSkill])
        $("#real>div").eq(sSkill).stop().fadeIn(500).siblings().stop().fadeOut(300);
        var w = $(".realGood>div").eq(sSkill).width() / 2;
        var moveX;
        if (sSkill == 0) {
            moveX = 900 - w;
        } else {
            moveX = 900 - w;
        }
        $(".realGood>div").eq(sSkill).stop().animate({
            "right": moveX + "px",
            "opacity": 1
        }, 500).siblings().stop().animate({
            "right": "-200px",
            "opacity": 0
        }, 500)
        $(".rightCircle>div").eq(sSkill).stop().fadeIn(500).css({ "transform": "rotate(-150deg)" }).siblings().css({
            "display": "none",
            "transform": "rotate(90deg)"
        });
        // $(this).addClass("active").siblings("li").removeClass("active");
    })
})

// 퀵메뉴
$(document).ready(function () {
    // $(".quick_menu").click(function() {
    //     $(".quick_menu_info").stop().animate({
    //         width: "900px",
    //         height: "700px",
    //         opacity: 1
    //     }, 500)
    // })
    // $(".quick_close").click(function() {
    //     $(".quick_menu_info").stop().animate({
    //         width: "0px",
    //         height: "0px",
    //         opacity: 0
    //     }, 500)
    // });
    $(".quick_menu").click(function () {
        $(".tooltip").show();
        $(".tooltip_close").stop().animate({
            bottom: "160px"
        }, 500)
    })
    $(".tooltip_close").click(function () {
        $(".tooltip").hide();
        $(".tooltip_close").stop().animate({
            bottom: "50px"
        }, 500)
    });
})

// 원본증명 이용안내 가이드팝업창

$(function () {
    //선택된 배너가 누구인지를 체크할 변수 만들기
    var showBanner = 0;
    //배너의 개수 알아내기
    var bCount = $(".guide_banner>li").length;
    console.log(bCount);

    //배너애니
    function moveBanner() {
        $(".guide_banner").stop().animate({
            marginLeft: -showBanner * 100 + "%"
        }, 300);

        $(".guide_page>li").eq(showBanner).addClass("active")
            .siblings().removeClass("active");
    }

    //guide_page
    $(".guide_page>li").click(function () {
        showBanner = $(this).index();
        moveBanner();
    })

    //오른쪽 버튼
    $(".g_rightBtn").click(function () {
        if (showBanner < (bCount - 1)) {
            showBanner++;
        }
        moveBanner();
    })

    $(".g_leftBtn").click(function () {
        if (showBanner > 0) {
            showBanner--;
        }
        moveBanner();
    })
})

