var filterList = [
  "生活方式",
  "百度百科",
  "社交媒体",
  "网络视频",
  "新闻资讯",
  "博客智库",
  "论坛贴吧",
  "搜索引擎",
  "电商网站",
];

function renderFilterLsit(dataList) {
  var strHtml = `<span class="filter-item active">全部</span>`;
  dataList.forEach((item, index) => {
    strHtml += `<span class="filter-item">${item}</span>`;
  });

  $(".filter-list").html(strHtml);

  $(".filter-item").each(function (index) {
    $(this).click(function () {
      $(".filter-item").each(function () {
        $(this).removeClass("active");
      });
      $(this).addClass("active");
    });
  });
}
renderFilterLsit(filterList);

var marketList = [
  {
    type: "网络视频",
    markets: [
      {
        images: "/images/jingdong.png",
        title: "京东【自主售后】物流信息获取",
        subtitle: "根据【客户期望】，获取京东【自主售后】订单物流信息",
      },
      {
        images: "/images/jingdong.png",
        title: "京东插旗备注",
        subtitle: "批量给京东订单插旗备注",
      },
      {
        images: "/images/jingdong.png",
        title: "京东【自主售后】物流信息获取",
        subtitle: "根据【客户期望】，获取京东【自主售后】订单物流信息",
      },
      {
        images: "/images/jingdong.png",
        title: "京东插旗备注",
        subtitle: "批量给京东订单插旗备注",
      },
      {
        images: "/images/jingdong.png",
        title: "京东【自主售后】物流信息获取",
        subtitle: "根据【客户期望】，获取京东【自主售后】订单物流信息",
      },
      {
        images: "/images/jingdong.png",
        title: "京东插旗备注",
        subtitle: "批量给京东订单插旗备注",
      },
    ],
  },
  {
    type: "新闻资讯",
    markets: [
      {
        images: "/images/douyin.png",
        title: "飞鸽发消息 (web)",
        subtitle: "循环向指定订单发送消息，支持【专接人工客服】",
      },
      {
        images: "/images/douyin.png",
        title: "抖音插旗备注",
        subtitle: "对抖音已付款订单插旗备注",
      },
      {
        images: "/images/douyin.png",
        title: "飞鸽发消息 (web)",
        subtitle: "循环向指定订单发送消息，支持【专接人工客服】",
      },
      {
        images: "/images/douyin.png",
        title: "抖音插旗备注",
        subtitle: "对抖音已付款订单插旗备注",
      },
      {
        images: "/images/douyin.png",
        title: "飞鸽发消息 (web)",
        subtitle: "循环向指定订单发送消息，支持【专接人工客服】",
      },
      {
        images: "/images/douyin.png",
        title: "抖音插旗备注",
        subtitle: "对抖音已付款订单插旗备注",
      },
    ],
  },
];

function renderMarkets(dataList) {
  var strHtml = "";
  dataList.forEach((item, index) => {
    var childHtml = "";
    item.markets.forEach((data, curr) => {
      childHtml += `<div class="market-category-item ${
        curr % 2 !== 0 ? "even" : ""
      }">
        <div class="market-category-item-img">
            <img src="${data.images}" alt="">
        </div>
        <div class="market-category-item-content">
            <div class="market-category-item-info">
                <div class="market-category-item-title">${data.title}</div>
                <div class="market-category-item-subtitle">${
                  data.subtitle
                }</div>
            </div>
            <button type="button" class="btn detail-btn" onclick="obtainDetail(${JSON.stringify(
              data
            ).replace(/"/g, "&quot;")})">获取</button>
        </div>
    </div>`;
    });
    strHtml += `<div class="market-category">
    <div class="market-category-top">
        <div class="market-category-title">${item.type}</div>
        <div class="market-category-more" onclick="toMore(${JSON.stringify(
          item
        ).replace(/"/g, "&quot;")})">更多</div>
    </div>
    <div class="market-category-bottom">${childHtml}</div>
</div>`;
  });
  $(".market-list").html(strHtml);
}

renderMarkets(marketList);

function toMore(item) {
  jump("/page/spiderMarket/templateList.html", "采集市场");
}

function obtainDetail(item) {
  jump("/page/spiderMarket/templateDetail.html", "采集市场");
}

function submitFeedback() {
  $("#feedbackModal").modal("show");
}

function controlPopover() {
  setTimeout(() => {
    $(".rnav-menu").remove();
  }, 200);
}
