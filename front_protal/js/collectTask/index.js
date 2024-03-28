var website_id;
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
  if (urlParmas.id && urlParmas.name) {
    website_id = urlParmas.id;
    $(".breadcrumb-list").html(
      ` <li class="breadcrumb-item">采集任务管理</li>` +
        `<li class="breadcrumb-item active page-name" aria-current="page">${urlParmas.name}</li>`
    );
  }
} else {
  website_id = "";
}

function getSiteList() {
  $.ajax({
    method: "GET",
    url: baseUrl + "/admin/webCategory",
    contentType: "application/json",
    dataType: "json",
    data: {},
    success: function (res) {
      if (res.code == 200) {
        var strHtml = ` <option selected hidden>请选择站点</option>
       <option class="option" value="">全部</option>`;
        res.result.forEach((item, index) => {
          strHtml += `<option class="option" value="${item.id}">${item.website_name}</option>`;
        });
        $(".task-site").html(strHtml);

        if (website_id) {
          $(".task-site").val(website_id);
        }
      }
    },
  });
}
getSiteList();

var pageNum = 1;
var pageSize = 10;
var taskLevel = 0;
var taskStatus = -1;
var spiderType = -1;
var isfirst = true;

function getTaskList() {
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
      keyword,
      webId: website_id,
      level: taskLevel,
      status: taskStatus,
      type: spiderType,
    },
    success: function (res) {
      $(".marklayer").removeClass("mark-show");
      if (res.code == 200) {
        renderTaskList(res.result.list);

        if (res.result.list.length == 0) {
          $(".task-page .table_empty").css("display", "flex");
        } else {
          $(".task-page .table_empty").css("display", "none");
        }

        if (isfirst) {
          pagination(res.result.total);
          isfirst = false;
        }
      }
    },
  });
}

getTaskList();

function searchKeyword() {
  isfirst = true;
  pageNum = 1;
  getTaskList();
}

//回车也可以发送消息
$(".search-task").keydown(function (e) {
  if (e.keyCode == 13) {
    searchKeyword();
  }
});

function renderTaskList(data) {
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
      item.seed_status == 1 ? "已启动" : "已关闭"
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
      item.seed_status == 0 ? "开启" : "关闭"
    }</button>|
                              <button type="button" class="btn btn-link" onclick="editTask(${JSON.stringify(
                                item
                              ).replace(/"/g, "&quot;")})">编辑</button>
                              </td>
                          </tr>`;
  });
  $(".table-task").html(strHtml);
}

function pickWebsite() {
  var selectDom = document.querySelector(".task-site");
  var index = selectDom.selectedIndex; //获取选中项的索引
  website_id = $(".task-site .option")
    .eq(index - 1)
    .attr("value");
  $(".page-name").html(
    $(".task-site .option")
      .eq(index - 1)
      .html()
  );
  isfirst = true;
  pageNum = 1;
  getTaskList();
}

function pickStatus() {
  var selectDom = document.querySelector(".task-status");
  var index = selectDom.selectedIndex; //获取选中项的索引
  taskStatus = $(".task-status .option")
    .eq(index - 1)
    .attr("value");
  isfirst = true;
  pageNum = 1;
  getTaskList();
}

function pickPriority() {
  var selectDom = document.querySelector(".priority");
  var index = selectDom.selectedIndex; //获取选中项的索引
  taskLevel = $(".priority .option")
    .eq(index - 1)
    .attr("value");
  isfirst = true;
  pageNum = 1;
  getTaskList();
}

function pickCollectType() {
  var selectDom = document.querySelector(".collect-type");
  var index = selectDom.selectedIndex; //获取选中项的索引
  spiderType = $(".collect-type .option")
    .eq(index - 1)
    .attr("value");
  isfirst = true;
  pageNum = 1;
  getTaskList();
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
        getTaskList();
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
    data: { id: taskId, is_del: 1 },
    success: function (res) {
      if (res.code == 200) {
        isfirst = true;
        getTaskList();
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
  window.open("/page/collectTask/task.html");
}

function editTask(item) {
  window.open(
    "/page/collectTask/task.html?id=" + item.id + "&name=" + item.seed_name
  );
}

var updateTaskStatus;
function controlTask(item) {
  taskId = item.id;
  if (item.seed_status == 1) {
    updateTaskStatus = 0;
    $("#controlModal .col-form-label").html("确定要关闭吗？");
  } else {
    updateTaskStatus = 1;
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
      seed_status: updateTaskStatus,
    }),
    success: function (res) {
      if (res.code == 200) {
        getTaskList();
        $(".message-success .message_content").html(res.msg);
        $(".message-success").removeClass("message-hide");
        setTimeout(() => {
          $(".message-success").addClass("message-hide");
        }, 2000);
      }
    },
  });
}
