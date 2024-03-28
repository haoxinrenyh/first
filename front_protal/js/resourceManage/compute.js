var pageNum_1 = 1;
var pageSize_1 = 10;
var isfirst_1 = true;

function getComputeList() {
  $(".marklayer").eq(0).addClass("mark-show");
  var keyword = $(".search-compute").val();
  $.ajax({
    method: "GET",
    url: baseUrl + "/spiderSource/vps/page",
    contentType: "application/json",
    dataType: "json",
    data: {
      pageNum: pageNum_1,
      pageSize: pageSize_1,
      keyword,
    },
    success: function (res) {
      $(".marklayer").eq(0).removeClass("mark-show");
      if (res.code == 200) {
        renderComputeList(res.result.list);

        if (res.result.list.length == 0) {
          $(".resource-page .table_empty").eq(0).css("display", "flex");
        } else {
          $(".resource-page .table_empty").eq(0).css("display", "none");
        }

        if (isfirst_1) {
          paginationCompute(res.result.total);
          isfirst_1 = false;
        }
      }
    },
  });
}

getComputeList();

function searchCompute() {
  isfirst_1 = true;
  pageNum_1 = 1;
  getComputeList();
}

//回车也可以发送消息
$(".search-compute").keydown(function (e) {
  if (e.keyCode == 13) {
    searchCompute();
  }
});

function renderComputeList(data) {
  var strHtml = "";
  data.forEach((item, index) => {
    strHtml += `<tr>
                                <td>${item.ip}</td>
                                <td><span class="badge ${
                                  item.environment == "测试环境"
                                    ? "text-bg-info"
                                    : "text-bg-success"
                                }">${item.environment}</span></td>
                                <td>${item.configure ? item.configure : ""}</td>
                                <td>${item.cpu_count}%</td>
                                <td>${item.ram_count}%</td>
                                <td>${item.disk_count}%</td>
                                <td>${item.update_time}</td>
                            </tr>`;
  });
  $(".compute-list").html(strHtml);
}

function paginationCompute(total) {
  layui.use("laypage", function () {
    var laypage = layui.laypage;

    //执行一个laypage实例
    laypage.render({
      elem: "pagination-1",
      count: total,
      limit: 10,
      groups: 3,
      layout: ["count", "prev", "page", "next", "skip"],
      jump: function (obj) {
        if (pageNum_1 == obj.curr) {
          return;
        }
        pageNum_1 = obj.curr;
        getComputeList();
      },
      theme: "#0d6efd",
    });
  });
}

function addCompute() {
  jump("/page/resourceManage/addCompute.html", "采集资源监管");
}
