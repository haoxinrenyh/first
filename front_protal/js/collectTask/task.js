if (window.location.search) {
  var url = window.location.search;
  var urlParmas = new Object();
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      urlParmas[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
    }
  }
  // console.log(urlParmas);
  if (detailId) {
    $(".task-name").html(urlParmas.name);
    $(".page-name").html("任务编辑");
  }
}

function getSiteList() {
  $.ajax({
    method: "GET",
    url: baseUrl + "/admin/webCategory",
    contentType: "application/json",
    dataType: "json",
    data: {},
    success: function (res) {
      if (res.code == 200) {
        var strHtml = `<option selected hidden value="">请选择采集站点(必选)</option>`;
        res.result.forEach((item, index) => {
          strHtml += `<option class="option" value="${item.id}">${item.website_name}</option>`;
        });
        $("#spider_website").html(strHtml);
      }
    },
  });
}
getSiteList();

function getTemplateList() {
  $.ajax({
    method: "GET",
    url: baseUrl + "/template/list",
    contentType: "application/json",
    dataType: "json",
    data: {},
    success: function (res) {
      if (res.code == 200) {
        var strHtml = `<option selected hidden value="">请选择采集模板</option>`;
        res.result.forEach((item, index) => {
          strHtml += `<option class="option" value="${item.id}">${item.template_name}</option>`;
        });

        $("#seed_template").html(strHtml);

        if (template_id) {
          $("#seed_template").val(template_id); //选择模板展示选中的值
        }
      }
    },
  });
}
getTemplateList();

function pickTemplate() {
  var selectDom = document.querySelector("#seed_template");
  var index = selectDom.selectedIndex; //获取选中项的索引
  var templateId = $("#seed_template .option")
    .eq(index - 1)
    .attr("value");
  getTemplateXml(templateId);
}

function getTemplateXml(id) {
  $.ajax({
    method: "GET",
    url: baseUrl + "/template/info",
    contentType: "application/json",
    dataType: "json",
    data: {
      id,
    },
    success: function (res) {
      if (res.code == 200) {
        editor.setXML(res.result.xml);
        $(".xml-container textarea").val(editor.getXML());
      }
    },
  });
}

function editCron() {
  $("#seedDetailModal").modal("hide");
  layer.open({
    type: 1,
    area: ["50%", "50%"],
    offset: "auto",
    maxmin: true,
    content: $("#corn-dialog"),
    cancel: function () {
      $("#seedDetailModal").modal("show");
    },
  });
  var parentCron = $("#cron").val();
  if (parentCron) {
    $("#result").val(parentCron);
    $("#result").change();
  } else {
    $("#result").val("* * * * * ? *");
  }
}

$("#transmit").on("click", function () {
  $("#seedDetailModal").modal("show");
  var value = $("#result").val();
  if (value) {
    // if (!detailId) {
    $("#cron").val(value);
    layer.close(layer.index);
    // } else {
    //   $.ajax({
    //     method: "POST",
    //     url: getRealPath() + "/spiderflow/save",
    //     contentType: "application/json",
    //     dataType: "json",
    //     data: JSON.stringify({
    //       id: detailId,
    //       cron: value,
    //     }),
    //     success: function (res) {
    //       if (res.code == 200) {
    //         $("#cron").val(value);
    //         layer.close(layer.index);
    //         $(".message-success .message_content").html(res.msg);
    //         $(".message-success").removeClass("message-hide");
    //         setTimeout(() => {
    //           $(".message-success").addClass("message-hide");
    //         }, 2000);
    //       }
    //     },
    //   });
    // }
  }
});
