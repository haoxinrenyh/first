var pageNum = 1;
var pageSize = 10;
var isfirst = true;
var tableList = [];

function getFieldList() {
  $(".marklayer").addClass("mark-show");
  var keyword = $(".search-field").val();
  $.ajax({
    method: "GET",
    url: baseUrl + "/result/note/page",
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
getFieldList();

function searchKeyword() {
  isfirst = true;
  pageNum = 1;
  getFieldList();
}

//回车也可以发送消息
$(".search-field").keydown(function (e) {
  if (e.keyCode == 13) {
    searchKeyword();
  }
});

function renderTableList(data) {
  var strHtml = "";
  data.forEach((item, index) => {
    strHtml += `<tr>
                              <td>${item.english_key}</td>
                              <td>${item.china_key}</td>
                              <td>${item.update_time}</td>
                              <td class="center">
                              <button type="button" class="btn btn-link text-danger" onclick="confirmDelete(${JSON.stringify(
                                item
                              ).replace(/"/g, "&quot;")})">删除</button>|
                              <button type="button" class="btn btn-link" onclick="editField(${JSON.stringify(
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
        getFieldList();
      },
      theme: "#0d6efd",
    });
  });
}

function addField() {
  $("#addModal").modal("show");
  $(".english-name").val("");
  $(".chinese-name").val("");
  $(".field-form").removeClass("was-validated");
}

var fieldForm = document.querySelector(".field-form");

fieldForm.addEventListener(
  "submit",
  (event) => {
    if (!fieldForm.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      $("#addModal").modal("hide");
      var enName = $(".english-name").val().replace(/\s*/g, ""); //去除空格;
      var zhName = $(".chinese-name").val().replace(/\s*/g, "");

      $.ajax({
        method: "POST",
        url: baseUrl + "/result/note/save",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          english_key: enName,
          china_key: zhName,
        }),
        success: function (res) {
          if (res.code == 200) {
            isfirst = true;
            getFieldList();
            $(".message-success .message_content").html(res.msg);
            $(".message-success").removeClass("message-hide");
            setTimeout(() => {
              $(".message-success").addClass("message-hide");
            }, 2000);
          }
        },
      });
    }

    fieldForm.classList.add("was-validated");
  },
  false
);

var fieldId;
function editField(item) {
  fieldId = item.id;
  $("#editModal").modal("show");
  $(".new-english-name").val(item.english_key);
  $(".new-chinese-name").val(item.china_key);
  $(".edit-field-form").removeClass("was-validated");
}

var newFieldForm = document.querySelector(".edit-field-form");

newFieldForm.addEventListener(
  "submit",
  (event) => {
    if (!newFieldForm.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      $("#editModal").modal("hide");
      var enName = $(".new-english-name").val().replace(/\s*/g, ""); //去除空格;
      var zhName = $(".new-chinese-name").val().replace(/\s*/g, "");

      $.ajax({
        method: "POST",
        url: baseUrl + "/result/note/update",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          id: fieldId,
          english_key: enName,
          china_key: zhName,
        }),
        success: function (res) {
          if (res.code == 200) {
            getFieldList();
            $(".message-success .message_content").html(res.msg);
            $(".message-success").removeClass("message-hide");
            setTimeout(() => {
              $(".message-success").addClass("message-hide");
            }, 2000);
          }
        },
      });
    }

    newFieldForm.classList.add("was-validated");
  },
  false
);

function rollBack() {
  jump("/page/spiderApply/websiteList.html", "采集应用");
}

function confirmDelete(item) {
  fieldId = item.id;
  $("#deleteModal").modal("show");
}

function submitDelete() {
  $("#deleteModal").modal("hide");
  $.ajax({
    method: "GET",
    url: baseUrl + "/result/note/remove",
    contentType: "application/json",
    dataType: "json",
    data: {
      id: fieldId,
    },
    success: function (res) {
      if (res.code == 200) {
        //如果当前页的数据只有一条 并且当前页不是第一页 删除后获取的数据的页数减1
        if (pageNum > 1 && tableList.length == 1) {
          pageNum--;
        }
        isfirst = true;
        getFieldList();
        $(".message-success .message_content").html(res.msg);
        $(".message-success").removeClass("message-hide");
        setTimeout(() => {
          $(".message-success").addClass("message-hide");
        }, 2000);
      }
    },
  });
}
