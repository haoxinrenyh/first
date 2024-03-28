var pageNum = 1;
var pageSize = 10;
var isfirst = true;

function getTemplateList() {
  $(".marklayer").addClass("mark-show");
  var keyword = $(".search-website").val();
  $.ajax({
    method: "GET",
    url: baseUrl + "/template/page",
    contentType: "application/json",
    dataType: "json",
    data: {
      pageNum,
      pageSize,
      keyword,
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

getTemplateList();

function renderTableList(data) {
  var strHtml = "";
  data.forEach((item, index) => {
    strHtml += `<tr>
                              <td>${item.template_name}</td>
                              <td>${item.create_user_name}</td>
                              <td>${item.create_time}</td>
                              <td><span class="badge ${
                                item.status == 0
                                  ? "text-bg-success"
                                  : "text-bg-danger"
                              }">${
      item.status == 0 ? "已上架" : "已下架"
    }</span></td>
                          </tr>`;
  });
  $(".table-content").html(strHtml);
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
        getTemplateList();
      },
      theme: "#0d6efd",
    });
  });
}
