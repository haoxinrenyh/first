var pageNum = 1;
var pageSize = 10;
var isfirst = true;
var category_id = null;
// var esindex = null;

function getWebsiteType() {
  $.ajax({
    method: "GET",
    url: baseUrl + "/admin/category/list",
    contentType: "application/json",
    dataType: "json",
    data: {},
    success: function (res) {
      if (res.code == 200) {
        var strHtml = ``;
        if (Object.keys(res.result).length > 0) {
          res.result.list.forEach((item, index) => {
            strHtml += `<span value="${item.id}">${item.category_name}</span>`;
          });
        }
        $(".website-type").html(strHtml);

        category_id = $(".website-type span").eq(0).attr("value");
        $(".website-type span").eq(0).addClass("active");

        // getDataType();
        getResultList();

        $(".website-type span").each(function () {
          $(this).click(function () {
            $(".website-type span").each(function () {
              $(this).removeClass("active");
            });
            $(this).addClass("active");
            category_id = $(this).attr("value");
            pageNum = 1;
            isfirst = true;
            $(".table_mark").animate({ scrollLeft: 0 });
            getResultList();
          });
        });
      }
    },
  });
}
getWebsiteType();

function getDataType() {
  $.ajax({
    method: "GET",
    url: baseUrl + "/spiderflow/websitetype",
    contentType: "application/json",
    dataType: "json",
    data: {},
    success: function (res) {
      if (res.status == 200) {
        var strHtml = ``;
        res.data.forEach((item, index) => {
          strHtml += `<span  value="${item.esindex}">${item.typename}</span>`;
        });
        $(".data-type").html(strHtml);

        esindex = $(".data-type span").eq(0).attr("value");
        $(".data-type span").eq(0).addClass("active");
        getResultList();

        $(".data-type span").each(function () {
          $(this).click(function () {
            $(".data-type span").each(function () {
              $(this).removeClass("active");
            });
            $(this).addClass("active");
            esindex = $(this).attr("value");
            pageNum = 1;
            isfirst = true;
            getResultList();
          });
        });
      }
    },
  });
}

var dataList = [];
function getResultList() {
  checkboxValue = [];
  $(".marklayer").addClass("mark-show");
  var keyword = $(".search-result").val();
  $.ajax({
    method: "GET",
    url: baseUrl + "/result/page",
    contentType: "application/json",
    dataType: "json",
    data: {
      pageNum,
      pageSize,
      category_id,
      keyword,
      // esindex,
    },
    success: function (res) {
      $(".marklayer").removeClass("mark-show");
      if (res.code == 200) {
        if (Object.keys(res.result).length > 0 || res.result.data.length > 0) {
          renderResultList(
            res.result.tableTitles,
            res.result.tableNames,
            res.result.data
          );
        }
        dataList = res.result.data;

        if (
          Object.keys(res.result).length == 0 ||
          res.result.data.length == 0
        ) {
          $(".result-page .table_empty").eq(0).css("display", "flex");
        } else {
          $(".result-page .table_empty").eq(0).css("display", "none");
        }

        if (isfirst) {
          pagination(res.result.total);
          isfirst = false;
        }
      }
    },
  });
}

function searchKeyword() {
  isfirst = true;
  pageNum = 1;
  $(".table_mark").animate({ scrollLeft: 0 });
  getResultList();
}

//回车也可以发送消息
$(".search-result").keydown(function (e) {
  if (e.keyCode == 13) {
    searchKeyword();
  }
});

function renderResultList(headDataZh, headDataEn, bodyData) {
  var tableHead = ``;
  var tableHeadHide = ``; //隐藏的英文表头

  if (bodyData.length > 0) {
    tableHead += `<th scope="col"><input class="form-check-input pickAll" type="checkbox" value=""  onclick="checkboxAllOnclick(this)"></th>`;
    headDataZh.forEach((item) => {
      tableHead += `<th scope="col">${item}</th>`;
    });
    tableHead += `<th scope="col">操作</th>`;
  }
  $(".thead-result tr").html(tableHead);

  if (bodyData.length > 0) {
    tableHeadHide += `<th scope="col"><input class="form-check-input pickAll" type="checkbox" value=""  onclick="checkboxAllOnclick(this)"></th>`;
    headDataEn.forEach((item) => {
      tableHeadHide += `<th scope="col">${item}</th>`;
    });
    tableHeadHide += `<th scope="col">操作</th>`;
  }
  $(".thead-result-hide tr").html(tableHeadHide);

  if (bodyData.length > 0) {
    var trDom = "<tr>";
    for (var i = 0; i < headDataZh.length + 2; i++) {
      trDom += `<td></td>`;
    }
    trDom = trDom + "</tr>";
  }

  $(".table-result").html("");

  var strHtml = "";
  bodyData.forEach((item, index) => {
    $(".table-result").append(trDom);

    $(".table-result tr")
      .eq(index)
      .find("td")
      .eq(0)
      .html(
        `<input class="form-check-input pickOne" type="checkbox" value="" onclick="checkboxOnclick(this,${index})">`
      );

    item.forEach((data) => {
      $(".thead-result-hide th").each(function (curr) {
        if (data.key == $(this).html()) {
          if (typeof data.value == "string" && data.value) {
            data.value = data.value.replace(/"/g, "'");
          }
          var child = `<div title="${data.value}">${
            data.value ? data.value : "—"
          }</div>`;
          $(".table-result tr").eq(index).find("td").eq(curr).html(child);
        }
      });
    });

    $(".table-result tr")
      .eq(index)
      .find("td")
      .eq(headDataZh.length + 1)
      .html(
        `
        <button type="button" class="btn btn-link" onclick="viewOriginal(${JSON.stringify(
          item
        ).replace(/"/g, "&quot;")})">查看原文</button>
        <button type="button" class="btn btn-link export-btn" onclick="exportArticle(${JSON.stringify(
          item
        ).replace(
          /"/g,
          "&quot;"
        )},${index})"><a class="export-acticle">导出</a></button>
        <button type="button" class="btn btn-link" onclick="previewFile(${JSON.stringify(
          item
        ).replace(/"/g, "&quot;")})">查看附件</button>
        <button type="button" class="btn btn-link" onclick="downloadFile(${JSON.stringify(
          item
        ).replace(
          /"/g,
          "&quot;"
        )},${index})"><a class="download-file">下载附件</a></button>
        `
      );
  });

  $(".thead-result-hide th").each(function (index) {
    if ($(this).html() == "type") {
      $(".table-result tr").each(function () {
        if (
          $(this)
            .find("div")
            .eq(index - 1)
            .html() == "1"
        ) {
          $(this)
            .find("div")
            .eq(index - 1)
            .html("文件采集");
          $(this)
            .find("div")
            .eq(index - 1)
            .attr("title", "文件采集");
        } else if (
          $(this)
            .find("div")
            .eq(index - 1)
            .html() == "2"
        ) {
          $(this)
            .find("div")
            .eq(index - 1)
            .html("网站采集");
          $(this)
            .find("div")
            .eq(index - 1)
            .attr("title", "网站采集");
        }
      });
    }
  });
}

function exportArticle(item, index) {
  var articleId = "";
  var esindex = "";
  item.forEach((data) => {
    if (data.key == "article_public_id") {
      articleId = data.value;
    }
    if (data.key == "es_index") {
      esindex = data.value;
    }
  });
  $(".export-acticle")
    .eq(index)
    .attr(
      "href",
      baseUrl + "/result/downloadFile?ids=" + articleId + "&esindex=" + esindex
    );
}

function previewFile(item) {
  var filesList = {};
  item.forEach((data) => {
    if (data.key == "attachmentData") {
      filesList = data.value;
    }
  });

  if (Object.keys(filesList).length > 0) {
    //json转对象
    filesList = eval("(" + filesList + ")");

    for (key in filesList) {
      $(".file-classify").each(function () {
        if (key == $(this).attr("type")) {
          if (filesList[key].length > 0) {
            $(this).css("display", "block");
            var strHtml = "";
            filesList[key].forEach((item) => {
              strHtml += `<div class="file"><a href="${item}" target="_blank">${item}</a></div>`;
            });
            $(this).find(".file-list").html(strHtml);
          } else {
            $(this).css("display", "none");
          }
        }
      });
    }

    var classifyStatus = [];
    $(".file-classify").each(function () {
      if ($(this).css("display") == "none") {
        classifyStatus.push(false);
      } else {
        classifyStatus.push(true);
      }
    });

    if (classifyStatus.indexOf(true) == -1) {
      $(".result-page .table_empty").eq(1).css("display", "flex");
    } else {
      $(".result-page .table_empty").eq(1).css("display", "none");
    }

    $("#previewModal").modal("show");
  } else {
    $(".message-error .message_content").html("暂无附件！");
    $(".message-error").removeClass("message-hide");
    setTimeout(() => {
      $(".message-error").addClass("message-hide");
    }, 2000);
  }
}

function viewOriginal(item) {
  var sourceUrl = "";
  item.forEach((data) => {
    if (data.key == "source_url") {
      sourceUrl = data.value;
    }
  });
  window.open(sourceUrl);
}

function downloadFile(item, index) {
  var articleId = "";
  var esindex = "";
  item.forEach((data) => {
    if (data.key == "article_public_id") {
      articleId = data.value;
    }
    if (data.key == "es_index") {
      esindex = data.value;
    }
  });
  $(".download-file")
    .eq(index)
    .attr(
      "href",
      baseUrl + "/result/downloadZip?id=" + articleId + "&esindex=" + esindex
    );
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
        $(".table_mark").animate({ scrollLeft: 0 });
        getResultList();
      },
      theme: "#0d6efd",
    });
  });
}

$(".collect-type span").each(function () {
  $(this).click(function () {
    $(".collect-type span").each(function () {
      $(this).removeClass("active");
    });
    $(this).addClass("active");
  });
});

function toDetail(item) {
  jump("/page/dataResult/detail.html", "采集结果管理", "", {
    id: item.article_public_id,
    esindex: item.esindex,
  });
}

function statsAnalysis() {
  jump("/page/dataResult/statsAnalysis.html", "采集结果管理");
}

function batchExport() {
  var idList = [];
  checkboxValue.forEach((item, index) => {
    if (item == true) {
      dataList[index].forEach((data) => {
        if (data.key == "article_public_id") {
          idList.push(data.value);
        }
      });
    }
  });
  // console.log(idList);
  if (idList.length > 0) {
    $(".batch_export").attr(
      "href",
      baseUrl + "/result/downloadFile?ids=" + idList.join(",")
    );
  } else {
    $(".message-error .message_content").html("请选择你需要导出的数据！");
    $(".message-error").removeClass("message-hide");
    setTimeout(() => {
      $(".message-error").addClass("message-hide");
    }, 2000);
  }
}

function exportAll() {
  $(".export_all").attr(
    "href",
    baseUrl + "/result/downloadFile?category_id=" + category_id
  );
}

//所有单选选中 全选选中
var checkboxValue = []; //所有选择框的状态
function checkboxOnclick(checkbox, index) {
  checkboxValue = [];
  $(".pickOne").each(function () {
    checkboxValue.push(this.checked);
  });
  if (checkboxValue.indexOf(false) == -1) {
    $(".pickAll").prop("checked", true);
  } else {
    $(".pickAll").prop("checked", false);
  }

  if ($(".pickOne").eq(index).prop("checked")) {
    checkboxValue[index] = true;
  } else {
    checkboxValue[index] = false;
  }
}

//全选选中 所有单选选中
function checkboxAllOnclick(checkboxAll) {
  checkboxValue = [];
  if (checkboxAll.checked) {
    $(".pickOne").each(function () {
      $(this).prop("checked", true);
      checkboxValue.push(true);
    });
  } else {
    $(".pickOne").each(function () {
      $(this).prop("checked", false);
      checkboxValue.push(false);
    });
  }
}
