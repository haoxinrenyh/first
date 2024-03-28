var pageNum_2 = 1;
var pageSize_2 = 10;
var isfirst_2 = true;

function getAccountList() {
  $(".marklayer").eq(1).addClass("mark-show");
  // var keyword = $(".search-account").val();
  $.ajax({
    method: "GET",
    url: baseUrl + "/spiderSource/account/page",
    contentType: "application/json",
    dataType: "json",
    data: {
      pageNum: pageNum_2,
      pageSize: pageSize_2,
    },
    success: function (res) {
      $(".marklayer").eq(1).removeClass("mark-show");
      if (res.code == 200) {
        renderAccountList(res.result.list);

        if (res.result.list.length == 0) {
          $(".resource-page .table_empty").eq(1).css("display", "flex");
        } else {
          $(".resource-page .table_empty").eq(1).css("display", "none");
        }

        if (isfirst_2) {
          paginationAccount(res.result.total);
          isfirst_2 = false;
        }
      }
    },
  });
}

getAccountList();

/* function searchAccount() {
  isfirst = true;
  pageNum = 1;
  getAccountList();
}

//回车也可以发送消息
$(".search-compute").keydown(function (e) {
  if (e.keyCode == 13) {
    searchAccount();
  }
}); */

function renderAccountList(data) {
  var strHtml = "";
  data.forEach((item, index) => {
    strHtml += `<tr>
                                  <td>${item.username}</td>
                                  <td>${item.password}</td>
                                  <td>${item.site}</td>
                                  <td>${item.update_time}</td>
                                  <td>${item.create_user_name}</td>
                                  <td>${
                                    item.status == 0 ? "未采集" : "采集中"
                                  }</td>
                                  <td class="center">
                                  <button type="button" class="btn btn-link text-danger" onclick="confirmDeleteAccount(${JSON.stringify(
                                    item
                                  ).replace(/"/g, "&quot;")})">删除</button>|
                                  <button type="button" class="btn btn-link" onclick="editAccount(${JSON.stringify(
                                    item
                                  ).replace(/"/g, "&quot;")})">编辑</button>
                                  </td>
                              </tr>`;
  });
  $(".account-list").html(strHtml);
}

function paginationAccount(total) {
  layui.use("laypage", function () {
    var laypage = layui.laypage;

    //执行一个laypage实例
    laypage.render({
      elem: "pagination-2",
      count: total,
      limit: 10,
      groups: 3,
      layout: ["count", "prev", "page", "next", "skip"],
      jump: function (obj) {
        if (pageNum_2 == obj.curr) {
          return;
        }
        pageNum_2 = obj.curr;
        getAccountList();
      },
      theme: "#0d6efd",
    });
  });
}
var accountId;
function confirmDeleteAccount(item) {
  accountId = item.id;
  $("#deleteAccountModal").modal("show");
}

function submitDeleteAccount() {
  $("#deleteAccountModal").modal("hide");
  $.ajax({
    method: "GET",
    url: baseUrl + "/spiderSource/account/remove",
    contentType: "application/json",
    dataType: "json",
    data: {
      id: accountId,
    },
    success: function (res) {
      if (res.code == 200) {
        isfirst_2 = true;
        getAccountList();
        $(".message-success .message_content").html(res.msg);
        $(".message-success").removeClass("message-hide");
        setTimeout(() => {
          $(".message-success").addClass("message-hide");
        }, 2000);
      }
    },
  });
}

function addAccount() {
  $(".username").val("");
  $(".password").val("");
  $(".site").val("");
  $(".account-form").removeClass("was-validated"); //清除表单校验状态
  $("#addAccountModal").modal("show");
}

var accountrForm = document.querySelector(".account-form");

accountrForm.addEventListener(
  "submit",
  (event) => {
    if (!accountrForm.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      $("#addAccountModal").modal("hide");
      var username = $(".username").val().replace(/\s*/g, "");
      var password = $(".password").val().replace(/\s*/g, "");
      var site = $(".site").val().replace(/\s*/g, "");
      $.ajax({
        method: "POST",
        url: baseUrl + "/spiderSource/account/save",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          username,
          password,
          site,
        }),
        success: function (res) {
          if (res.code == 200) {
            isfirst_2 = true;
            getAccountList();
            $(".message-success .message_content").html("添加成功");
            $(".message-success").removeClass("message-hide");
            setTimeout(() => {
              $(".message-success").addClass("message-hide");
            }, 2000);
          }
        },
      });
    }

    accountrForm.classList.add("was-validated");
  },
  false
);

function editAccount(item) {
  accountId = item.id;
  $(".edit-account-form").removeClass("was-validated"); //清除表单校验状态
  $("#editAccountModal").modal("show");
  $(".new-username").val(item.username);
  $(".new-password").val(item.password);
  $(".new-site").val(item.site);
}

var newAccountrForm = document.querySelector(".edit-account-form");

newAccountrForm.addEventListener(
  "submit",
  (event) => {
    if (!newAccountrForm.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      $("#editAccountModal").modal("hide");
      var username = $(".new-username").val().replace(/\s*/g, "");
      var password = $(".new-password").val().replace(/\s*/g, "");
      var site = $(".new-site").val().replace(/\s*/g, "");
      $.ajax({
        method: "POST",
        url: baseUrl + "/spiderSource/account/update",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          id: accountId,
          username,
          password,
          site,
        }),
        success: function (res) {
          if (res.code == 200) {
            getAccountList();
            $(".message-success .message_content").html(res.msg);
            $(".message-success").removeClass("message-hide");
            setTimeout(() => {
              $(".message-success").addClass("message-hide");
            }, 2000);
          }
        },
      });
    }

    newAccountrForm.classList.add("was-validated");
  },
  false
);
