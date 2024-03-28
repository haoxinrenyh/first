var pageNum_3 = 1;
var pageSize_3 = 10;
var isfirst_3 = true;

function getAgentList() {
  $(".marklayer").eq(2).addClass("mark-show");
  // var keyword = $(".search-account").val();
  $.ajax({
    method: "GET",
    url: baseUrl + "/spiderSource/agentPool/page",
    contentType: "application/json",
    dataType: "json",
    data: {
      pageNum: pageNum_3,
      pageSize: pageSize_3,
    },
    success: function (res) {
      $(".marklayer").eq(2).removeClass("mark-show");
      if (res.code == 200) {
        renderAgentList(res.result.list);

        if (res.result.list.length == 0) {
          $(".resource-page .table_empty").eq(2).css("display", "flex");
        } else {
          $(".resource-page .table_empty").eq(2).css("display", "none");
        }

        if (isfirst_3) {
          paginationAgent(res.result.total);
          isfirst_3 = false;
        }
      }
    },
  });
}

getAgentList();

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

function renderAgentList(data) {
  var strHtml = "";
  data.forEach((item, index) => {
    strHtml += `<tr>
                                    <td>${item.service_name}</td>
                                    <td>${item.term_day}天</td>
                                    <td>${item.url}</td>
                                    <td>${
                                      item.status == 0 ? "有效" : "过期"
                                    }</td>
                                    <td>${item.create_user_name}</td>
                                    <td>${item.update_time}</td>
                                    <td class="center">
                                    <button type="button" class="btn btn-link text-danger" onclick="confirmDeleteAgentpools(${JSON.stringify(
                                      item
                                    ).replace(/"/g, "&quot;")})">删除</button>|
                                    <button type="button" class="btn btn-link" onclick="editAgent(${JSON.stringify(
                                      item
                                    ).replace(/"/g, "&quot;")})">编辑</button>
                                    </td>
                                </tr>`;
  });
  $(".agentpools-list").html(strHtml);
}

function paginationAgent(total) {
  layui.use("laypage", function () {
    var laypage = layui.laypage;

    //执行一个laypage实例
    laypage.render({
      elem: "pagination-3",
      count: total,
      limit: 10,
      groups: 3,
      layout: ["count", "prev", "page", "next", "skip"],
      jump: function (obj) {
        if (pageNum_3 == obj.curr) {
          return;
        }
        pageNum_3 = obj.curr;
        getAgentList();
      },
      theme: "#0d6efd",
    });
  });
}

var agentId;
function confirmDeleteAgentpools(item) {
  agentId = item.id;
  $("#deleteAgentpoolsModal").modal("show");
}

function submitDeleteAgentpools() {
  $("#deleteAgentpoolsModal").modal("hide");
  $.ajax({
    method: "GET",
    url: baseUrl + "/spiderSource/agentPool/remove",
    contentType: "application/json",
    dataType: "json",
    data: {
      id: agentId,
    },
    success: function (res) {
      if (res.code == 200) {
        isfirst_3 = true;
        getAgentList();
        $(".message-success .message_content").html(res.msg);
        $(".message-success").removeClass("message-hide");
        setTimeout(() => {
          $(".message-success").addClass("message-hide");
        }, 2000);
      }
    },
  });
}

function addAgent() {
  var minDate = new Date().Format("yyyy-MM-dd HH:mm:ss");
  renderLaydate1(minDate);
  $(".server-name").val("");
  $(".effective-date").val("");
  $(".url").val("");
  $(".agent-form").removeClass("was-validated"); //清除表单校验状态
  $("#addAgentModal").modal("show");
}
var agentrForm = document.querySelector(".agent-form");

agentrForm.addEventListener(
  "submit",
  (event) => {
    if (!agentrForm.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      $("#addAgentModal").modal("hide");
      var serverName = $(".server-name").val().replace(/\s*/g, "");
      var term = $(".effective-date").val();
      var url = $(".url").val().replace(/\s*/g, "");
      $.ajax({
        method: "POST",
        url: baseUrl + "/spiderSource/agentPool/save",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          service_name: serverName,
          term,
          url,
          status: 0,
        }),
        success: function (res) {
          if (res.code == 200) {
            isfirst_3 = true;
            getAgentList();
            $(".message-success .message_content").html(res.msg);
            $(".message-success").removeClass("message-hide");
            setTimeout(() => {
              $(".message-success").addClass("message-hide");
            }, 2000);
          }
        },
      });
    }

    agentrForm.classList.add("was-validated");
  },
  false
);

//规定日期返回格式
Date.prototype.Format = function (fmt) {
  var o = {
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "H+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds(), //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    S: this.getMilliseconds(), //毫秒
  };
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(
      RegExp.$1,
      (this.getFullYear() + "").substr(4 - RegExp.$1.length)
    );
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt))
      fmt = fmt.replace(
        RegExp.$1,
        RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length)
      );
  return fmt;
};

function renderLaydate1(minDate) {
  layui.use("laydate", function () {
    var laydate = layui.laydate;

    laydate.render({
      elem: "#validationCustom05",
      type: "datetime",
      min: minDate,
      fullPanel: true,
      theme: "#0f97ff",
      done: function (value, date) {
        console.log(value, date);
        if (value) {
        }
      },
    });
  });
}

function editAgent(item) {
  agentId = item.id;
  var minDate = new Date().Format("yyyy-MM-dd HH:mm:ss");
  renderLaydate2(minDate);

  $(".edit-agent-form").removeClass("was-validated"); //清除表单校验状态
  $("#editAgentModal").modal("show");
  $(".new-server-name").val(item.service_name);
  $(".new-effective-date").val(item.term);
  $(".new-url").val(item.url);
}

var newAgentrForm = document.querySelector(".edit-agent-form");

newAgentrForm.addEventListener(
  "submit",
  (event) => {
    if (!newAgentrForm.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      $("#editAgentModal").modal("hide");
      var serverName = $(".new-server-name").val().replace(/\s*/g, "");
      var term = $(".new-effective-date").val();
      var url = $(".new-url").val().replace(/\s*/g, "");
      $.ajax({
        method: "POST",
        url: baseUrl + "/spiderSource/agentPool/update",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          id: agentId,
          service_name: serverName,
          term,
          url,
        }),
        success: function (res) {
          if (res.code == 200) {
            getAgentList();
            $(".message-success .message_content").html(res.msg);
            $(".message-success").removeClass("message-hide");
            setTimeout(() => {
              $(".message-success").addClass("message-hide");
            }, 2000);
          }
        },
      });
    }

    newAgentrForm.classList.add("was-validated");
  },
  false
);

function renderLaydate2(minDate) {
  layui.use("laydate", function () {
    var laydate = layui.laydate;

    laydate.render({
      elem: "#validationCustom11",
      type: "datetime",
      min: minDate,
      fullPanel: true,
      theme: "#0f97ff",
      done: function (value, date) {
        console.log(value, date);
        if (value) {
        }
      },
    });
  });
}
