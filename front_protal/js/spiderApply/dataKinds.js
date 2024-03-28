var pageNum = 1;
var pageSize = 10;
var isfirst = true;
var tableList = [];

function getDataType() {
  $(".marklayer").addClass("mark-show");
  // var keyword = $(".search-category").val();
  $.ajax({
    method: "GET",
    url: baseUrl + "/spiderflow/websitetype/page",
    contentType: "application/json",
    dataType: "json",
    data: {
      pageNum,
      pageSize,
      // keyword,
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
getDataType();

// function searchKeyword() {
//   isfirst = true;
//   pageNum = 1;
//   getDataType();
// }

// //回车也可以发送消息
// $(".search-category").keydown(function (e) {
//   if (e.keyCode == 13) {
//     searchKeyword();
//   }
// });

function renderTableList(data) {
  var strHtml = "";
  data.forEach((item, index) => {
    strHtml += `<tr>
                              <td>${item.typename}</td>
                              <td>${item.esindex}</td>
                              <td>${item.estype}</td>
                              <td>${item.kafka_queue_name}</td>
                              <td>${item.bloomname}</td>
                              <td>${item.updatetime}</td>
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
        getDataType();
      },
      theme: "#0d6efd",
    });
  });
}

function addWebsiteType() {
  $("#addModal").modal("show");
  $(".type-name").val("");
  $(".esindex").val("");
  $(".kafka-queue").val("");
  $(".bloomname").val("");
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
      var typename = $(".type-name").val().replace(/\s*/g, ""); //去除空格;
      var esindex = $(".esindex").val().replace(/\s*/g, "");
      var kafka_queue_name = $(".kafka-queue").val().replace(/\s*/g, "");
      var bloomname = $(".bloomname").val().replace(/\s*/g, "");

      $.ajax({
        method: "POST",
        url: baseUrl + "/spiderflow/websitetype/save",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          typename,
          esindex,
          kafka_queue_name,
          bloomname,
        }),
        success: function (res) {
          if (res.code == 200) {
            isfirst = true;
            getDataType();
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

var dataTypeId;
function editWebsite(item) {
  dataTypeId = item.id;
  $("#editModal").modal("show");
  $(".new-type-name").val(item.typename);
  $(".new-esindex").val(item.esindex);
  $(".new-kafka-queue").val(item.kafka_queue_name);
  $(".new-bloomname").val(item.bloomname);
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
      var typename = $(".new-type-name").val().replace(/\s*/g, ""); //去除空格;
      var esindex = $(".new-esindex").val().replace(/\s*/g, "");
      var kafka_queue_name = $(".new-kafka-queue").val().replace(/\s*/g, "");
      var bloomname = $(".new-bloomname").val().replace(/\s*/g, "");

      $.ajax({
        method: "POST",
        url: baseUrl + "/spiderflow/websitetype/save",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          id: dataTypeId,
          typename,
          esindex,
          kafka_queue_name,
          bloomname,
        }),
        success: function (res) {
          if (res.code == 200) {
            getDataType();
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
  dataTypeId = item.id;
  $("#deleteModal").modal("show");
}

function submitDelete() {
  $("#deleteModal").modal("hide");
  $.ajax({
    method: "GET",
    url: baseUrl + "/spiderflow/websitetype/remove",
    contentType: "application/json",
    dataType: "json",
    data: {
      id: dataTypeId,
    },
    success: function (res) {
      if (res.code == 200) {
        //如果当前页的数据只有一条 并且当前页不是第一页 删除后获取的数据的页数减1
        if (pageNum > 1 && tableList.length == 1) {
          pageNum--;
        }
        isfirst = true;
        getDataType();
        $(".message-success .message_content").html(res.msg);
        $(".message-success").removeClass("message-hide");
        setTimeout(() => {
          $(".message-success").addClass("message-hide");
        }, 2000);
      }
    },
  });
}
