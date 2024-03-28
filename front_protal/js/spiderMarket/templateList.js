var templateList = [
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
];

function renderTemplate(dataList) {
  var strHtml = "";
  dataList.forEach((item, index) => {
    strHtml += `<div class="market-category-item ${
      index % 2 !== 0 ? "even" : ""
    }">
          <div class="market-category-item-img">
              <img src="${item.images}" alt="">
          </div>
          <div class="market-category-item-content">
              <div class="market-category-item-info">
                  <div class="market-category-item-title">${item.title}</div>
                  <div class="market-category-item-subtitle">${
                    item.subtitle
                  }</div>
              </div>
              <button type="button" class="btn detail-btn" onclick="obtainDetail(${JSON.stringify(
                item
              ).replace(/"/g, "&quot;")})">获取</button>
          </div>
      </div>`;
  });
  $(".template-list").html(strHtml);
}

renderTemplate(templateList);

function rollBack() {
  jump("/page/spiderMarket/index.html", "采集市场");
}

function obtainDetail(item) {
  jump("/page/spiderMarket/templateDetail.html", "采集市场");
}
