var websiteId, websiteName;
if (window.location.search) {
  var url = window.location.search;
  var urlParmas = new Object();
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      urlParmas[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
    }
  }
  // console.log(urlParmas);
  websiteId = urlParmas.id;
  websiteName = urlParmas.name;
  $(".websiteName").html(websiteName);
}

var pageNum = 1;
var pageSize = 10;
var seedStatus = -1;
var seedLevel = 0;
var collectType = -1;
var isfirst = true;

function getWebsiteInfo() {
  $(".marklayer").addClass("mark-show");
  var keyword = $(".search-task").val();
  $.ajax({
    method: "GET",
    url: baseUrl + "/admin/webInfo",
    contentType: "application/json",
    dataType: "json",
    data: {
      pageNum,
      pageSize,
      webId: websiteId,
      keyword,
      status: seedStatus,
      level: seedLevel,
      type: collectType,
    },
    success: function (res) {
      $(".marklayer").removeClass("mark-show");
      if (res.code == 200) {
        renderTableList(res.result.list);

        if (res.result.list.length == 0) {
          $(".table_empty").css("display", "flex");
        } else {
          $(".table_empty").css("display", "none");
        }

        if (isfirst) {
          pagination(res.result.total);
          isfirst = false;
        }
      }
    },
  });
}
getWebsiteInfo();

function searchKeyword() {
  isfirst = true;
  pageNum = 1;
  getWebsiteInfo();
}

//回车也可以发送消息
$(".search-task").keydown(function (e) {
  if (e.keyCode == 13) {
    searchKeyword();
  }
});

function renderTableList(data) {
  var strHtml = "";
  data.forEach((item, index) => {
    strHtml += `<tr>
                              <td>${item.seed_name}</td>
                              <td>${
                                item.spider_level == 1
                                  ? "高"
                                  : item.spider_level == 2
                                  ? "中"
                                  : "低"
                              }</td>
                              <td>${item.spider_day_count}</td>
                              <td>${item.spider_count}</td>
                              <td>${
                                item.type == 0 ? "网站采集" : "文件采集"
                              }</td>
                              <td><span class="badge ${
                                item.seed_status == 1
                                  ? "text-bg-success"
                                  : "text-bg-danger"
                              }">${
      item.seed_status == 0 ? "已关闭" : "已开启"
    }</span></td>
                              <td>${item.create_date}</td>
                              <td class="center">
                              <button type="button" class="btn btn-link text-danger" onclick="confirmDelete(${JSON.stringify(
                                item
                              ).replace(/"/g, "&quot;")})">删除</button>|
                              <button type="button" class="btn btn-link   ${
                                item.seed_status == 0
                                  ? "text-success"
                                  : "text-danger"
                              }" onclick="controlTask(${JSON.stringify(
      item
    ).replace(/"/g, "&quot;")})">${
      item.seed_status == 0 ? "启动" : "关闭"
    }</button>|
                              <button type="button" class="btn btn-link" onclick="editTask(${JSON.stringify(
                                item
                              ).replace(/"/g, "&quot;")})">编辑</button>
                              </td>
                          </tr>`;
  });
  $(".table-seed").html(strHtml);
}

function pickseedStatus() {
  var selectDom = document.querySelector(".task-status");
  var index = selectDom.selectedIndex; //获取选中项的索引
  seedStatus = $(".task-status .option")
    .eq(index - 1)
    .attr("value");
  isfirst = true;
  pageNum = 1;
  getWebsiteInfo();
}

function pickPriority() {
  var selectDom = document.querySelector(".task-priority");
  var index = selectDom.selectedIndex; //获取选中项的索引
  seedLevel = $(".task-priority .option")
    .eq(index - 1)
    .attr("value");
  isfirst = true;
  pageNum = 1;
  getWebsiteInfo();
}

function pickCollectType() {
  var selectDom = document.querySelector(".collect-type");
  var index = selectDom.selectedIndex; //获取选中项的索引
  collectType = $(".collect-type .option")
    .eq(index - 1)
    .attr("value");
  isfirst = true;
  pageNum = 1;
  getWebsiteInfo();
}

function pagination(total) {
  layui.use("laypage", function () {
    var laypage = layui.laypage;

    //执行一个laypage实例
    laypage.render({
      elem: "pagination",
      count: total,
      limit: 10,
      groups: 3,
      layout: ["count", "prev", "page", "next", "skip"],
      jump: function (obj) {
        if (pageNum == obj.curr) {
          return;
        }
        pageNum = obj.curr;
        getWebsiteInfo();
      },
      theme: "#0d6efd",
    });
  });
}

var taskId;
function confirmDelete(item) {
  taskId = item.id;
  $("#deleteModal").modal("show");
}

function submitDelete() {
  $("#deleteModal").modal("hide");
  $.ajax({
    method: "GET",
    url: baseUrl + "/spiderflow/removeSpiderFlow",
    contentType: "application/json",
    dataType: "json",
    data: {
      id: taskId,
      is_del: 1,
    },
    success: function (res) {
      if (res.code == 200) {
        isfirst = true;
        getWebsiteInfo();
        $(".message-success .message_content").html("删除成功");
        $(".message-success").removeClass("message-hide");
        setTimeout(() => {
          $(".message-success").addClass("message-hide");
        }, 2000);
      }
    },
  });
}

var updateSeedStatus;
function controlTask(item) {
  taskId = item.id;
  if (item.seed_status == 1) {
    updateSeedStatus = 0;
    $("#controlModal .col-form-label").html("确定要关闭吗？");
  } else {
    updateSeedStatus = 1;
    $("#controlModal .col-form-label").html("确定要开启吗？");
  }
  $("#controlModal").modal("show");
}

function submitControl() {
  $("#controlModal").modal("hide");
  $.ajax({
    method: "POST",
    url: baseUrl + "/spiderflow/updateSpiderStatus",
    contentType: "application/json",
    dataType: "json",
    data: JSON.stringify({
      id: taskId,
      seed_status: updateSeedStatus,
    }),
    success: function (res) {
      if (res.code == 200) {
        getWebsiteInfo();
        $(".message-success .message_content").html(res.msg);
        $(".message-success").removeClass("message-hide");
        setTimeout(() => {
          $(".message-success").addClass("message-hide");
        }, 2000);
      }
    },
  });
}

function addTask() {
  window.open(
    "/page/spiderApply/seedDetail.html?id=" + websiteId + "&name=" + websiteName
  );
}

function rollBack() {
  jump("/page/spiderApply/websiteList.html", "采集应用");
}

function editTask(item) {
  window.open(
    "/page/spiderApply/seedDetail.html?id=" +
      websiteId +
      "&name=" +
      websiteName +
      "&taskId=" +
      item.id +
      "&taskName=" +
      item.seed_name
  );
}
