function getWebsiteType() {
  $.ajax({
    method: "GET",
    url: baseUrl + "/admin/category/list",
    contentType: "application/json",
    dataType: "json",
    data: {},
    success: function (res) {
      if (res.code == 200) {
        var optionHtml = `<option selected hidden value="">请选择网站类型(必选)</option>`;
        res.result.list.forEach((item) => {
          optionHtml += `<option class="option" value="${item.id}">${item.category_name}</option>`;
        });
        $(".website-type").html(optionHtml);
      }
    },
  });
}
getWebsiteType();

function domainAnalyze() {
  var websiteUrl = $(".website-url").val().replace(/\s*/g, "");
  $.ajax({
    method: "GET",
    url: baseUrl + "/datasourcesite/getDomain",
    contentType: "application/json",
    dataType: "json",
    data: { url: websiteUrl },
    success: function (res) {
      if (res.code == 200) {
        $(".one-domain").val(res.result.one_domain);
        $(".two-domain").val(res.result.two_domain);
      }
    },
  });
}

var sourceForm = document.querySelector(".source-form");

sourceForm.addEventListener(
  "submit",
  (event) => {
    if (!sourceForm.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      var websiteName = $(".website-name").val().replace(/\s*/g, "");
      var websiteUrl = $(".website-url").val().replace(/\s*/g, "");
      var websiteIco = $(".website-logo").val().replace(/\s*/g, "");
      var websiteRemark = $(".website-note").val().replace(/\s*/g, "");
      var newWebsiteType;
      $(".website-type .option").each(function () {
        if ($(this).prop("selected")) {
          newWebsiteType = JSON.parse($(this).attr("value"));
        }
      });

      $.ajax({
        method: "POST",
        url: baseUrl + "/datasourcesite/saveWeb",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          websiteName,
          websiteUrl,
          newWebsiteType,
          websiteIco,
          websiteRemark,
        }),
        success: function (res) {
          if (res.code == 200) {
            $(".message-success .message_content").html(res.msg);
            $(".message-success").removeClass("message-hide");
            setTimeout(() => {
              $(".message-success").addClass("message-hide");
              jump("/page/spiderApply/websiteList.html", "采集应用");
            }, 2000);
          }
        },
      });
    }

    sourceForm.classList.add("was-validated");
  },
  false
);

function rollBack() {
  jump("/page/spiderApply/websiteList.html", "采集应用");
}
