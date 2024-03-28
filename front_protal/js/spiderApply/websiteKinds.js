var pageNum = 1;
var pageSize = 10;
var isfirst = true;
var tableList = [];

function getWebsiteType() {
  $(".marklayer").addClass("mark-show");
  var keyword = $(".search-category").val();
  $.ajax({
    method: "GET",
    url: baseUrl + "/admin/category/page",
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
        tableList = res.result.list;
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
getWebsiteType();

function searchKeyword() {
  isfirst = true;
  pageNum = 1;
  getWebsiteType();
}

//回车也可以发送消息
$(".search-category").keydown(function (e) {
  if (e.keyCode == 13) {
    searchKeyword();
  }
});

function renderTableList(data) {
  var strHtml = "";
  data.forEach((item, index) => {
    strHtml += `<tr>
                              <td>${item.category_name}</td>
                              <td>${item.username}</td>
                              <td>${item.create_time}</td>
                              <td class="center">
                              <button type="button" class="btn btn-link text-danger" onclick="confirmDelete(${JSON.stringify(
                                item
                              ).replace(/"/g, "&quot;")})">删除</button>|
                              <button type="button" class="btn btn-link" onclick="editWebsite(${JSON.stringify(
                                item
                              ).replace(/"/g, "&quot;")})">编辑</button>
                              </td>
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
        getWebsiteType();
      },
      theme: "#0d6efd",
    });
  });
}

function addWebsiteType() {
  $("#addModal").modal("show");
  $(".website-type").val("");
  $(".website-type-form").removeClass("was-validated");
}

var websiteTypeForm = document.querySelector(".website-type-form");

websiteTypeForm.addEventListener(
  "submit",
  (event) => {
    if (!websiteTypeForm.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      $("#addModal").modal("hide");
      var name = $(".website-type").val().replace(/\s*/g, ""); //去除空格;

      $.ajax({
        method: "POST",
        url: baseUrl + "/admin/category/save",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          category_name: name,
        }),
        success: function (res) {
          if (res.code == 200) {
            isfirst = true;
            getWebsiteType();
            $(".message-success .message_content").html(res.msg);
            $(".message-success").removeClass("message-hide");
            setTimeout(() => {
              $(".message-success").addClass("message-hide");
            }, 2000);
          }
        },
      });
    }

    websiteTypeForm.classList.add("was-validated");
  },
  false
);

var websiteTypeId;
function editWebsite(item) {
  websiteTypeId = item.id;
  $("#editModal").modal("show");
  $(".new-website-type").val(item.category_name);
  $(".edit-website-type-form").removeClass("was-validated");
}

var newWebsiteTypeForm = document.querySelector(".edit-website-type-form");

newWebsiteTypeForm.addEventListener(
  "submit",
  (event) => {
    if (!newWebsiteTypeForm.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      $("#editModal").modal("hide");
      var name = $(".new-website-type").val().replace(/\s*/g, ""); //去除空格;

      $.ajax({
        method: "POST",
        url: baseUrl + "/admin/category/update",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          id: websiteTypeId,
          category_name: name,
        }),
        success: function (res) {
          if (res.code == 200) {
            getWebsiteType();
            $(".message-success .message_content").html(res.msg);
            $(".message-success").removeClass("message-hide");
            setTimeout(() => {
              $(".message-success").addClass("message-hide");
            }, 2000);
          }
        },
      });
    }

    newWebsiteTypeForm.classList.add("was-validated");
  },
  false
);

function rollBack() {
  jump("/page/spiderApply/websiteList.html", "采集应用");
}

function confirmDelete(item) {
  websiteTypeId = item.id;
  $("#deleteModal").modal("show");
}

function submitDelete() {
  $("#deleteModal").modal("hide");
  $.ajax({
    method: "GET",
    url: baseUrl + "/admin/category/remove",
    contentType: "application/json",
    dataType: "json",
    data: {
      id: websiteTypeId,
    },
    success: function (res) {
      if (res.code == 200) {
        //如果当前页的数据只有一条 并且当前页不是第一页 删除后获取的数据的页数减1
        if (pageNum > 1 && tableList.length == 1) {
          pageNum--;
        }
        isfirst = true;
        getWebsiteType();
        $(".message-success .message_content").html(res.msg);
        $(".message-success").removeClass("message-hide");
        setTimeout(() => {
          $(".message-success").addClass("message-hide");
        }, 2000);
      }
    },
  });
}
