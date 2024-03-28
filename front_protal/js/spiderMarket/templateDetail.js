const tooltipTriggerList = document.querySelectorAll(
  '[data-bs-toggle="tooltip"]'
);
const tooltipList = [...tooltipTriggerList].map(
  (tooltipTriggerEl) => new bootstrap.Tooltip(tooltipTriggerEl)
);

$(".tab-nav-item").each(function (index) {
  $(this).click(function () {
    $(".tab-nav-item").each(function () {
      $(this).removeClass("active");
    });
    $(this).addClass("active");

    // 获取需要监听滚动的元素
    var element = document.querySelector(".template_info");
    // 监听滚动事件
    element.removeEventListener("scroll", handleScroll);

    var tab_1_height = $("#template_description").height();
    var tab_2_height = $("#field_preview").height();
    var tab_3_height = $("#params_preview").height();
    var tab_4_height = $("#template_recommend").height();
    var interval1 = tab_1_height;
    var interval2 = tab_1_height + tab_2_height;
    var interval3 = tab_1_height + tab_2_height + tab_3_height;
    var interval4 = tab_1_height + tab_2_height + tab_3_height + tab_4_height;

    if (index == 0) {
      $(".template_info").animate({ scrollTop: 0 + "px" }, 500);
    } else if (index == 1) {
      $(".template_info").animate({ scrollTop: interval1 + "px" }, 500);
    } else if (index == 2) {
      $(".template_info").animate({ scrollTop: interval2 + "px" }, 500);
    } else {
      $(".template_info").animate({ scrollTop: interval3 + "px" }, 500);
    }
    setTimeout(() => {
      // 获取需要监听滚动的元素
      var element = document.querySelector(".template_info");
      // 监听滚动事件
      element.addEventListener("scroll", handleScroll);
    }, 600);
  });
});

$("#field_preview .frame_item_menu").each(function () {
  $(this).click(function () {
    $("#field_preview .frame_item_menu").each(function () {
      $(this).removeClass("active");
    });
    $(this).addClass("active");
    $("#field_preview .frame_item_content_title").html($(this).html());
  });
});

$("#params_preview .frame_item_menu").each(function () {
  $(this).click(function () {
    $("#params_preview .frame_item_menu").each(function () {
      $(this).removeClass("active");
    });
    $(this).addClass("active");
    $("#params_preview .frame_item_content_title").html($(this).html());
  });
});

function toBackback() {
  jump("/page/spiderMarket/index.html", "采集市场");
}

function rollBack() {
  jump("/page/spiderMarket/templateList.html", "采集市场");
}

// 获取需要监听滚动的元素
var element = document.querySelector(".template_info");
// 监听滚动事件
element.addEventListener("scroll", handleScroll);

function handleScroll(event) {
  // console.log(e.target.scrollTop, "距顶部距离");
  // console.log(e.target.clientHeight, "可视区高度");
  // console.log(e.target.scrollHeight, "滚动条总高度");
  if (event.target.scrollTop > 300) {
    $(".template_intro_bottom").css("display", "none");
    $(".scroll-hidden").css("display", "none");
    $(".template_intro_info_tip").css("display", "none");
    $(".scroll-show").css("display", "flex");
    $(".template_info").css("height", "calc(100vh - 272px)");
  } else {
    $(".template_intro_bottom").css("display", "block");
    $(".scroll-hidden").css("display", "flex");
    $(".template_intro_info_tip").css("display", "block");
    $(".scroll-show").css("display", "none");
    $(".template_info").css("height", "calc(100vh - 444px)");
  }

  var tab_1_height = $("#template_description").height();
  var tab_2_height = $("#field_preview").height();
  var tab_3_height = $("#params_preview").height();
  var tab_4_height = $("#template_recommend").height();
  var interval1 = tab_1_height;
  var interval2 = tab_1_height + tab_2_height;
  var interval3 = tab_1_height + tab_2_height + tab_3_height;
  var interval4 = tab_1_height + tab_2_height + tab_3_height + tab_4_height;

  if (event.target.scrollTop >= 0 && event.target.scrollTop < interval1) {
    $(".tab-nav-item").each(function () {
      $(this).removeClass("active");
    });
    $(".tab-nav-item").eq(0).addClass("active");
  } else if (
    event.target.scrollTop >= interval1 &&
    event.target.scrollTop < interval2
  ) {
    $(".tab-nav-item").each(function () {
      $(this).removeClass("active");
    });
    $(".tab-nav-item").eq(1).addClass("active");
  } else if (
    event.target.scrollTop >= interval2 &&
    event.target.scrollTop < interval3
  ) {
    $(".tab-nav-item").each(function () {
      $(this).removeClass("active");
    });
    $(".tab-nav-item").eq(2).addClass("active");
  } else if (
    event.target.scrollTop >= interval3 &&
    event.target.scrollTop < interval4
  ) {
    $(".tab-nav-item").each(function () {
      $(this).removeClass("active");
    });
    $(".tab-nav-item").eq(3).addClass("active");
  }
}

function collectTemplate(index) {
  if ($(".collect").eq(index).find("span").html() == "收藏") {
    $(".collect").each(function () {
      $(this).find("i").prop("class", "iconfont icon-jushoucanggift");
      $(this).find("span").html("已收藏");
    });
  } else {
    $(".collect").each(function () {
      $(this).find("i").prop("class", "iconfont icon-jushoucang");
      $(this).find("span").html("收藏");
    });
  }
}

function likeTemplate(index) {
  if ($(".like-up").eq(index).find("i").hasClass("icon-dianzan_kuai")) {
    $(".like-up").each(function () {
      $(this).find("i").prop("class", "iconfont icon-dianzan");
    });
    var currentLike = $(".like-up").eq(index).find(".like-count").html();
    $(".like-up").each(function () {
      $(this)
        .find(".like-count")
        .html(currentLike - 1);
    });
  } else {
    $(".like-up").each(function () {
      $(this).find("i").prop("class", "iconfont icon-dianzan_kuai");
    });
    var currentLike = JSON.parse(
      $(".like-up").eq(index).find(".like-count").html()
    );
    $(".like-up").each(function () {
      $(this)
        .find(".like-count")
        .html(currentLike + 1);
    });
  }
}
