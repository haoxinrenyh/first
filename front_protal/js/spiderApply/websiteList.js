(function () {
  var _DC = function () {
    this.pageNum = 1;
    this.pageSize = 10;
    this.isfirst = true;
    this.sort_field = "";
    this.sort_type = 1;
    this.website_type = null;
    this.log_error = null;
    this.tableList = [];
  };

  _DC.prototype.getWebsiteType = function () {
    $.ajax({
      method: "GET",
      url: baseUrl + "/admin/category/list",
      contentType: "application/json",
      dataType: "json",
      data: {},
      success: function (res) {
        if (res.code == 200) {
          var strHtml = `<span vlaue="" class="active">全部</span>`;
          var optionHtml = `<option selected hidden value="">请选择网站类型(必选)</option>`;
          if (Object.keys(res.result).length > 0) {
            res.result.list.forEach((item, index) => {
              strHtml += `<span value="${item.id}">${item.category_name}</span>`;
              optionHtml += `<option class="option" value="${item.id}">${item.category_name}</option>`;
            });
          }
          $(".website-type").eq(0).html(strHtml);
          $(".website-type").eq(1).html(optionHtml);

          $(".website-type span").each(function () {
            $(this).click(function () {
              $(".website-type span").each(function () {
                $(this).removeClass("active");
              });
              $(this).addClass("active");
              DC.website_type = $(this).attr("value");
              DC.isfirst = true;
              DC.pageNum = 1;
              DC.getWebsiteList();
            });
          });
        }
      },
    });
  };

  _DC.prototype.renderWebsiteList = function (data) {
    var strHtml = "";
    data.forEach((item, index) => {
      strHtml += `<tr>
                              <td><a class="websiteName pointer" onclick="toDetail(${JSON.stringify(
                                item
                              ).replace(/"/g, "&quot;")})">${
        item.website_name
      }</a></td>
                              <td><a href="${
                                item.website_url
                              }" target="_blank">${item.website_url}</a></td>
                              <td>智能模式</td>
                              <td class="center">${
                                item.category_name ? item.category_name : "—"
                              }</td>
                              <td>${item.create_user}</td>
                              <td>${item.create_date}</td>
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
    $(".table-website").html(strHtml);

    $(".table-website .websiteName").each(function (index) {
      if (DC.tableList[index].log_error == 1) {
        $(this).after(
          `<span class="warn-icon"><img src="/images/warn.svg"></span>`
        );
      }
    });
  };

  _DC.prototype.pagination = function (total) {
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
          // console.log(DC.pageNum);
          if (DC.pageNum == obj.curr) {
            return;
          }
          DC.pageNum = obj.curr;
          DC.getWebsiteList();
        },
        theme: "#0d6efd",
      });
    });
  };

  _DC.prototype.getWebsiteList = function () {
    $(".marklayer").addClass("mark-show");
    var keyword = $(".search-website").val();
    DC.tableList = [];
    $.ajax({
      method: "POST",
      url: baseUrl + "/admin/list",
      contentType: "application/json",
      dataType: "json",
      data: JSON.stringify({
        pageNum: DC.pageNum,
        pageSize: DC.pageSize,
        keyword,
        website_type: DC.website_type,
        sort_type: DC.sort_type,
        sort_field: 3, //根据创建时间排序
        log_error: DC.log_error,
      }),
      success: function (res) {
        $(".marklayer").removeClass("mark-show");
        if (res.code == 200) {
          DC.tableList = res.result.pageInfo.list;
          DC.renderWebsiteList(res.result.pageInfo.list);

          if (res.result.pageInfo.list.length == 0) {
            $(".website-page .table_empty").css("display", "flex");
          } else {
            $(".website-page .table_empty").css("display", "none");
          }

          if (DC.isfirst) {
            DC.pagination(res.result.pageInfo.total);
            DC.isfirst = false;
          }
        }
      },
    });
  };

  window.DC = new _DC();
})();

DC.getWebsiteType();
DC.getWebsiteList();

function searchKeyword() {
  DC.isfirst = true;
  DC.pageNum = 1;
  DC.getWebsiteList();
}

//回车也可以发送消息
$(".search-website").keydown(function (e) {
  if (e.keyCode == 13) {
    searchKeyword();
  }
});

function pickSort() {
  var selectDom = document.querySelector(".sort-type");
  var index = selectDom.selectedIndex; //获取选中项的索引
  DC.sort_type = $(".sort-type .option")
    .eq(index - 1)
    .attr("value");
  DC.isfirst = true;
  DC.pageNum = 1;
  DC.getWebsiteList();
}

function pickSite() {
  var selectDom = document.querySelector(".alert-site");
  var index = selectDom.selectedIndex; //获取选中项的索引
  DC.log_error = $(".alert-site .option")
    .eq(index - 1)
    .attr("value");

  if (DC.log_error) {
    DC.log_error = JSON.parse(DC.log_error);
  }
  DC.isfirst = true;
  DC.pageNum = 1;
  DC.getWebsiteList();
}

var websiteId;
function confirmDelete(item) {
  websiteId = item.id;
  $("#deleteModal").modal("show");
}

function submitDelete() {
  $("#deleteModal").modal("hide");
  $.ajax({
    method: "GET",
    url: baseUrl + "/admin/removeWeb",
    contentType: "application/json",
    dataType: "json",
    data: { id: websiteId },
    success: function (res) {
      if (res.code == 200) {
        //如果当前页的数据只有一条 并且当前页不是第一页 删除后获取的数据的页数减1
        if (DC.pageNum > 1 && DC.tableList.length == 1) {
          DC.pageNum--;
        }
        DC.isfirst = true;
        DC.getWebsiteList();
        $(".message-success .message_content").html(res.msg);
        $(".message-success").removeClass("message-hide");
        setTimeout(() => {
          $(".message-success").addClass("message-hide");
        }, 2000);
      }
    },
  });
}

function fieldManage() {
  jump("/page/spiderApply/fieldKinds.html", "采集应用");
}

function websiteTypeManage() {
  jump("/page/spiderApply/websiteKinds.html", "采集应用");
}

function dataTypeManage() {
  jump("/page/spiderApply/dataKinds.html", "采集应用");
}

function addSource() {
  jump("/page/spiderApply/addSource.html", "采集应用");
}

function toDetail(item) {
  jump("/page/spiderApply/websiteDetail.html", "采集应用", "", {
    id: item.id,
    name: item.website_name,
  });
}

function editWebsite(item) {
  websiteId = item.id;
  $(".edit-website-form").removeClass("was-validated");
  $("#editWebsiteModal").modal("show");
  $(".website-name").val(item.website_name);
  $(".website-url").val(item.website_url);
  $(".edit-website-form .website-type .option").each(function () {
    $(this).removeAttr("selected");
  });
  $(".edit-website-form .website-type .option").each(function () {
    if ($(this).attr("value") == item.new_website_type) {
      $(this).attr("selected", true);
    }
  });
}

var newWebsiteForm = document.querySelector(".edit-website-form");

newWebsiteForm.addEventListener(
  "submit",
  (event) => {
    if (!newWebsiteForm.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      $("#editWebsiteModal").modal("hide");
      var websiteName = $(".website-name").val().replace(/\s*/g, "");
      var websiteUrl = $(".website-url").val().replace(/\s*/g, "");
      var websiteType;
      $(".website-type .option").each(function () {
        if ($(this).prop("selected")) {
          websiteType = $(this).attr("value");
        }
      });

      $.ajax({
        method: "POST",
        url: baseUrl + "/datasourcesite/updateWeb",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          id: websiteId,
          websiteName,
          websiteUrl,
          newWebsiteType: websiteType,
        }),
        success: function (res) {
          if (res.code == 200) {
            DC.getWebsiteList();
            $(".message-success .message_content").html(res.msg);
            $(".message-success").removeClass("message-hide");
            setTimeout(() => {
              $(".message-success").addClass("message-hide");
            }, 2000);
          }
        },
      });
    }

    newWebsiteForm.classList.add("was-validated");
  },
  false
);

function rollBack() {
  jump("/page/spiderApply/index.html", "采集应用");
}
