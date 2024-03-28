// 获取当前选项卡
chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
  // 向当前页注入脚本
  chrome.tabs.executeScript(
    tabs[0].id,
    { file: "/assets/js/jquery.js" },
    function () {
      console.log("jquery注入成功");
    }
  );
});

//将本地存储的数据再次渲染到插件弹窗
chrome.storage.sync.get("xpath-list", function (res) {
  if (res["xpath-list"]) {
    $("#xpath-list").val(res["xpath-list"]);
  }
});

chrome.storage.sync.get("xpath-detail", function (res) {
  if (res["xpath-detail"]) {
    var xpathList = res["xpath-detail"];
    var strHtml = "";
    if (xpathList.length > 1) {
      xpathList.forEach((item) => {
        strHtml += `<div class="input-member mb-3">
<input type="text" class="form-control form-control-sm" placeholder="请在网页中点击要获取的数据" value="${item}">
<i class="iconfont icon-daixuanze ready-choose"></i>
</div>`;
      });
      $(".input-member-list").html(strHtml);
    } else {
      $(".input-member-list input").val(xpathList[0]);
    }
  }
});

chrome.storage.sync.get("prev-page", function (res) {
  chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
    // 获取当前标签页的 URL
    var url = tabs[0].url;
    // 解析 URL，获取地址栏路径
    var path = new URL(url).href;
    if (path == res["prev-page"]) {
      $("#jump-next").css("display", "inline-block");
      $("#jump-prev").css("display", "none");
    }
  });
});

chrome.storage.sync.get("next-page", function (res) {
  chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
    // 获取当前标签页的 URL
    var url = tabs[0].url;
    // 解析 URL，获取地址栏路径
    var path = new URL(url).href;
    if (path == res["next-page"]) {
      $("#jump-next").css("display", "none");
      $("#jump-prev").css("display", "inline-block");
    }
  });
});

//自动获取分页按钮
chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
  chrome.tabs.sendMessage(tabs[0].id, {
    action: "getPaginationXpath",
  });
});

var exampleList = []; //单条样本数据 获取详情页链接用于设置详情页xpath
var tempList = []; //变量暂存抓取到的列表数据
var tempDetail = []; //变量暂存抓取到的详情数据

// 监听消息
chrome.runtime.onMessage.addListener(function (message, sender, sendResponse) {
  // console.log(message);
  //接收content.js获取到的xpath
  if (message.action === "sendXpath") {
    $(".nav-tabs .nav-link").each(function (index) {
      if ($(this).hasClass("active")) {
        if (index == 0) {
          $("#nav-list .ready-choose").each(function (index) {
            if ($(this).hasClass("active")) {
              if (index == 1) {
                $("#xpath-pagination").val(message.xpath);
                $("#xpath-pagination").attr("title", message.xpath);
              } else {
                $("#xpath-list").val(message.xpath);
                $("#xpath-list").attr("title", message.xpath);
              }
            }
          });
        } else {
          $("#nav-detail .ready-choose").each(function (index) {
            if ($(this).hasClass("active")) {
              $("#xpath-detail input").eq(index).val(message.xpath);
              $("#xpath-detail input").eq(index).attr("title", message.xpath);
            }
          });
        }
      }
    });
  }
  //接收content.js获取到的列表数据
  else if (message.action === "sendTotalList") {
    // console.log(message.data);
    //取出一条数据 赋值给变量
    exampleList = message.data[0];

    //最终拿到的数据都是所有的数据
    if (message.type == 0) {
      tempList = message.data;
      renderPreviewData(tempList);
      tempList = [];
    } else if (message.type == 1) {
      //合并数组
      tempList = tempList.concat(message.data);
      renderPreviewData(tempList);
    } else {
      tempList = message.data;
      renderPreviewData(tempList);
    }
  }
  //接收content.js获取到的详情数据
  else if (message.action === "sendDetailText") {
    if (message.type == 0) {
      var dataList = [];
      dataList.push(message.data);
      renderDetailData(dataList);
    } else {
      tempDetail.push(message.data);
      renderDetailData(tempDetail);

      chrome.storage.sync.get("prev-page", function (res) {
        if (res["prev-page"]) {
          $.ajax({
            method: "POST",
            url: baseAPI + "/browser/saveInfo",
            contentType: "application/json",
            data: JSON.stringify({
              sole: md5(res["prev-page"]),
              data: { result: message.data },
            }),
            success: function (res) {
              if (res.code == 200) {
                $(".message-success .message_content").html(
                  "详情数据成功插入一条！"
                );
                $(".message-success").removeClass("message-hide");
                setTimeout(() => {
                  $(".message-success").addClass("message-hide");
                }, 2000);
              }
            },
          });
        }
      });
    }
  } else if (message.action === "sendPaginationXpath") {
    $("#xpath-pagination").val(message.xpath);
    $("#xpath-pagination").attr("title", message.xpath);
    $("#pagination-times").val(0);
  }
  //列表数据抓取完毕传输数据
  else if (message.action === "fetchEnded") {
    //获取当前页面的路径
    chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
      // 获取当前标签页的 URL
      var url = tabs[0].url;
      // 解析 URL，获取地址栏路径
      var path = new URL(url).href;

      $.ajax({
        method: "POST",
        url: baseAPI + "/browser/saveList",
        contentType: "application/json",
        data: JSON.stringify({
          sole: md5(path),
          data: tempList,
        }),
        success: function (res) {
          if (res.code == 200) {
            var detailXpath = [];
            $("#xpath-detail input").each(function () {
              if ($(this).val().trim()) {
                detailXpath.push($(this).val());
              }
            });

            //填写的详情字段的xpath才对详情进行采集
            if (detailXpath.length > 0) {
              chrome.tabs.query(
                { active: true, currentWindow: true },
                function (tabs) {
                  chrome.tabs.sendMessage(tabs[0].id, {
                    action: "fetchDetail",
                    xpath: detailXpath,
                    data: tempList,
                  });
                }
              );
            }

            $(".message-success .message_content").html("列表数据保存成功！");
            $(".message-success").removeClass("message-hide");
            setTimeout(() => {
              $(".message-success").addClass("message-hide");
            }, 2000);
          }
        },
      });
    });
  }
  // //接收content.js的消息 更新当前页面
  else if (message.action === "updateTab") {
    // 获取当前标签页的ID
    chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
      // 更新当前标签页的URL
      chrome.tabs.update(tabs[0].id, { url: message.url });
    });
  }
});

//列表数据预览
function renderPreviewData(dataList) {
  var headHtml = `<tr><th><div>序号</div></th>`;
  dataList[0].forEach((item, index) => {
    headHtml += `<th><div>字段${
      index + 1
    }<i class="iconfont icon-shanchu delete-list-field"></i></div></th>`;
  });
  headHtml += `</tr>`;
  $(".list-thead").html(headHtml);

  var tbodyHtml = "";
  dataList.forEach((item, index) => {
    var trHtml = `<tr><td>${index + 1}</td>`;
    item.forEach((data) => {
      trHtml += `<td><div title="${
        data.indexOf("<") > -1 ? data.replace(/"/g, "'") : data
      }">${data.indexOf("<") > -1 ? JSON.stringify(data) : data}</div></td>`;
    });
    trHtml += `</tr>`;
    tbodyHtml += trHtml;
  });
  $(".list-tbody").html(tbodyHtml);

  $(".table_mark")
    .eq(0)
    .animate({ scrollTop: $(".list-preview").height() });

  if (dataList.length == 0) {
    $(".table_empty").eq(0).css("display", "flex");
    $(".list-thead").html("");
    $(".list-tbody").html("");
  } else {
    $(".table_empty").eq(0).css("display", "none");
  }

  $(".delete-list-field").each(function (index) {
    $(this).click(function () {
      tempList.forEach((item) => {
        item.splice(index, 1);
      });
      renderPreviewData(tempList);
    });
  });
}

//详情数据
function renderDetailData(dataList) {
  var headHtml = `<tr><th><div>序号</div></th>`;
  dataList[0].forEach((item, index) => {
    headHtml += `<th><div>字段${
      index + 1
    }<i class="iconfont icon-shanchu delete-detail-field"></i></div></th>`;
  });
  headHtml += `</tr>`;
  $(".detail-thead").html(headHtml);

  var tbodyHtml = ``;
  dataList.forEach((item, index) => {
    var trHtml = `<tr><td>${index + 1}</td>`;
    item.forEach((data) => {
      trHtml += `<td><div title="${
        data.indexOf("<") > -1 ? data.replace(/"/g, "'") : data
      }">${data.indexOf("<") > -1 ? JSON.stringify(data) : data}</div></td>`;
    });
    trHtml += `</tr>`;
    tbodyHtml += trHtml;
  });
  $(".detail-tbody").html(tbodyHtml);

  $(".table_mark")
    .eq(1)
    .animate({ scrollTop: $(".detail-preview").height() });

  if (dataList.length == 0) {
    $(".table_empty").eq(1).css("display", "flex");
    $(".detail-thead").html("");
    $(".detail-tbody").html("");
  } else {
    $(".table_empty").eq(1).css("display", "none");
  }

  $(".delete-detail-field").each(function (index) {
    $(this).click(function () {
      tempDetail.splice(index, 1);
      renderDetailData(tempDetail);
    });
  });
}

function getPageList() {
  chrome.storage.sync.get("prev-page", function (res) {
    if (res["prev-page"]) {
      $.ajax({
        method: "GET",
        url: baseAPI + "/browser/getData",
        contentType: "application/json",
        data: {
          sole: md5(res["prev-page"]),
        },
        success: function (res) {
          if (res.code == 200) {
            if (
              res.result.listData.data &&
              res.result.listData.data.length > 0
            ) {
              tempList = res.result.listData.data;
              exampleList = tempList[0];
              renderPreviewData(tempList);
            }

            if (res.result.infoData && res.result.infoData.length > 0) {
              var dataList = res.result.infoData;
              dataList.forEach((item) => {
                tempDetail.push(item.data.result);
              });
              renderDetailData(tempDetail);
            }

            setInterval(() => {
              console.log(res.result);
            }, 3000);
          }
        },
      });
    }
  });
}

getPageList();

//获取单条数据样本所有的兄弟元素
document.getElementById("fetch-list").addEventListener("click", function () {
  var listXpath = $("#xpath-list").val().trim();
  if (listXpath) {
    chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
      chrome.tabs.sendMessage(tabs[0].id, {
        action: "fetchList",
        xpath: listXpath,
      });
    });
    //保存列表xpath配置
    chrome.storage.sync.set({ "xpath-list": listXpath });
    $(".message-success .message_content").html("列表配置保存成功！");
    $(".message-success").removeClass("message-hide");
    setTimeout(() => {
      $(".message-success").addClass("message-hide");
    }, 2000);
  }
});

//校验数据格式是否为url
function validateUrl(str) {
  var reg =
    /^(?:(http|https|ftp):\/\/)?((?:[\w-]+\.)+[a-z0-9]+)((?:\/[^/?#]*)+)?(\?[^#]+)?(#.+)?$/i;
  if (reg.test(str)) {
    return true;
  }
  return false;
}

//进入下一级页面
document.getElementById("jump-next").addEventListener("click", function () {
  //使用样本数据 设置详情xpath
  if (exampleList.length > 0) {
    var nextPageUrl = "";
    for (var i = 0; i < exampleList.length; i++) {
      if (validateUrl(exampleList[i])) {
        chrome.storage.sync.set({ "next-page": exampleList[i] });

        // 获取当前标签页的ID
        chrome.tabs.query(
          { active: true, currentWindow: true },
          function (tabs) {
            // 获取当前标签页的 URL
            var url = tabs[0].url;
            // 解析 URL，获取地址栏路径
            var path = new URL(url).href;
            //本地存储 初始页面路径
            chrome.storage.sync.set({ "prev-page": path });

            // 更新当前标签页的URL
            chrome.tabs.update(tabs[0].id, { url: exampleList[i] });

            $("#jump-next").css("display", "none");
            $("#jump-prev").css("display", "inline-block");
          }
        );
        break;
      }
    }
  }
});

//回到上一级页面
document.getElementById("jump-prev").addEventListener("click", function () {
  // 获取当前标签页的ID
  chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
    // 更新当前标签页的URL
    chrome.storage.sync.get("prev-page", function (res) {
      chrome.tabs.update(tabs[0].id, { url: res["prev-page"] });

      $("#jump-next").css("display", "inline-block");
      $("#jump-prev").css("display", "none");
    });
  });
});

//清除列表页数据和缓存
document.getElementById("clear-list").addEventListener("click", function () {
  $(".table_empty").eq(0).css("display", "flex");
  $(".list-thead").html("");
  $(".list-tbody").html("");
  $("#xpath-list").val("");
  $("#xpath-list").removeAttr("title");

  chrome.storage.sync.remove("xpath-list");
  chrome.storage.sync.remove("next-page");
  chrome.storage.sync.remove("prev-page");

  $(".message-success .message_content").html("清除成功！");
  $(".message-success").removeClass("message-hide");
  setTimeout(() => {
    $(".message-success").addClass("message-hide");
  }, 2000);
});

//根据xpath获取详情页的内容
document.getElementById("fetch-detail").addEventListener("click", function () {
  var detailXpath = [];
  $("#xpath-detail input").each(function () {
    if ($(this).val().trim()) {
      detailXpath.push($(this).val());
    }
  });
  if (detailXpath.length > 0) {
    chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
      chrome.tabs.sendMessage(tabs[0].id, {
        action: "fetchDetail",
        xpath: detailXpath,
        data: [],
      });
    });
    //保存详情xpath配置
    chrome.storage.sync.set({ "xpath-detail": detailXpath });
    $(".message-success .message_content").html("详情配置保存成功！");
    $(".message-success").removeClass("message-hide");
    setTimeout(() => {
      $(".message-success").addClass("message-hide");
    }, 2000);
  }
});

//清除详情页数据和缓存
document.getElementById("clear-detail").addEventListener("click", function () {
  $(".table_empty").eq(1).css("display", "flex");
  $(".detail-thead").html("");
  $(".detail-tbody").html("");
  $("#xpath-detail input").each(function () {
    $(this).val("");
    $(this).removeAttr("title");
  });
  $("#xpath-detail .input-member").eq(0).nextAll().remove();

  addClick();

  chrome.storage.sync.remove("xpath-detail");

  $(".message-success .message_content").html("清除成功！");
  $(".message-success").removeClass("message-hide");
  setTimeout(() => {
    $(".message-success").addClass("message-hide");
  }, 2000);
});

//开始采集
document.getElementById("start-fetch").addEventListener("click", function () {
  $(".ready-choose").each(function (index) {
    $(this).removeClass("active");
  });

  $(".radio-member input").each(function (index) {
    if ($(this).prop("checked")) {
      // getPageList();
      //页面数据局部刷新
      if (index == 0) {
        var listXpath = $("#xpath-list").val().trim();
        var pageXpath = $("#xpath-pagination").val().trim();
        var page = $("#pagination-times").val().trim();
        if (listXpath && pageXpath && page) {
          chrome.tabs.query(
            { active: true, currentWindow: true },
            function (tabs) {
              chrome.tabs.sendMessage(tabs[0].id, {
                action: "localRefresh",
                listXpath,
                pageXpath,
                page,
              });
            }
          );
        }
      }
      //页面地址栏发生改变
      else if (index == 1) {
        var listXpath = $("#xpath-list").val().trim();
        var pageXpath = $("#xpath-pagination").val().trim();
        var page = $("#pagination-times").val().trim();
        if (listXpath && pageXpath && page) {
          chrome.tabs.query(
            { active: true, currentWindow: true },
            function (tabs) {
              chrome.tabs.sendMessage(tabs[0].id, {
                action: "globalRefresh",
                listXpath,
                pageXpath,
                page,
              });
            }
          );
        }
      }
      //按钮查看更多
      else if (index == 2) {
        var listXpath = $("#xpath-list").val().trim();
        var pageXpath = $("#xpath-pagination").val().trim();
        var page = $("#pagination-times").val().trim();
        if (listXpath && pageXpath && page) {
          chrome.tabs.query(
            { active: true, currentWindow: true },
            function (tabs) {
              chrome.tabs.sendMessage(tabs[0].id, {
                action: "btnLoadmore",
                listXpath,
                pageXpath,
                page,
              });
            }
          );
        }
      }
      //滚动查看更多
      else if (index == 3) {
        var listXpath = $("#xpath-list").val().trim();
        var page = $("#pagination-times").val().trim();
        if (listXpath && page) {
          chrome.tabs.query(
            { active: true, currentWindow: true },
            function (tabs) {
              chrome.tabs.sendMessage(tabs[0].id, {
                action: "scrollLoadmore",
                listXpath,
                page,
              });
            }
          );
        }
      }
    }
  });
});

//给输入框中的指针图标添加点击事件
$("#nav-list .ready-choose").each(function () {
  $(this).click(function () {
    if ($(this).hasClass("active")) {
      $(this).removeClass("active");
    } else {
      $("#nav-list .ready-choose").each(function () {
        $(this).removeClass("active");
      });
      $(this).addClass("active");
    }
  });
});

function addClick() {
  $("#nav-detail .ready-choose").each(function () {
    $(this)
      .off("click")
      .on("click", function () {
        if ($(this).hasClass("active")) {
          $(this).removeClass("active");
        } else {
          $("#nav-detail .ready-choose").each(function () {
            $(this).removeClass("active");
          });
          $(this).addClass("active");
        }
      });
  });
}

addClick();

//详情页采集配置 新增
document.getElementById("add-config").addEventListener("click", function () {
  var strHtml = `<div class="input-member mb-3">
  <input type="text" class="form-control form-control-sm" placeholder="请在网页中点击要获取的数据">
  <i class="iconfont icon-daixuanze ready-choose"></i>
</div>`;
  $(".input-member-list").append(strHtml);
  addClick();
});

//翻页方式切换
$(".radio-member input").each(function (index) {
  $(this).click(function () {
    if (index == 3) {
      $(".form-option").eq(2).remove();
    } else {
      if ($(".form-option").length < 4) {
        var child = $(`<div class="form-option input-member mt-2">
        <label for="" class="form-label">翻页按钮xpath：</label>
        <input type="text" class="form-control form-control-sm" id="xpath-pagination"
            placeholder="请在网页中点击翻页按钮位置"></input>
        <i class="iconfont icon-daixuanze ready-choose"></i>
    </div>`);
        $(".form-option").eq(1).append(child);
        setTimeout(() => {
          //自动获取分页按钮
          chrome.tabs.query(
            { active: true, currentWindow: true },
            function (tabs) {
              chrome.tabs.sendMessage(tabs[0].id, {
                action: "getPaginationXpath",
              });
            }
          );
        }, 10);
      }
    }
  });
});
