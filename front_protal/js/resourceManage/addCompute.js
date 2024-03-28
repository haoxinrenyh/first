function checkIPvalid() {
  var regexIP =
    /^((25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))$/g;
  var ipAddress = $(".ipAddress").val().replace(/\s*/g, "");
  if (regexIP.test(ipAddress)) {
    $(".ipAddress").removeClass("is-invalid");
    $(".ipAddress").next().css("display", "none");
    return true;
  } else {
    $(".ipAddress").addClass("is-invalid");
    $(".ipAddress").next().css("display", "block");
    return false;
  }
}

var computeForm = document.querySelector(".compute-form");

computeForm.addEventListener(
  "submit",
  (event) => {
    if (checkIPvalid()) {
      if (!computeForm.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
      } else {
        var ipAddress = $(".ipAddress").val().replace(/\s*/g, "");
        var webPort = $(".webPort").val().replace(/\s*/g, "");
        var rootCode = $(".rootCode").val().replace(/\s*/g, "");
        var note = $(".compute-note").val().replace(/\s*/g, "");
        var configure = $(".machine-config").val().replace(/\s*/g, "");
        var environment;
        $(".compute-environment input").each(function () {
          if ($(this).prop("checked")) {
            environment = $(this).attr("value");
          }
        });
        $.ajax({
          method: "POST",
          url: baseUrl + "/spiderSource/vps/save",
          contentType: "application/json",
          dataType: "json",
          data: JSON.stringify({
            ip: ipAddress,
            port: webPort,
            password: rootCode,
            remark: note,
            environment,
            configure,
          }),
          success: function (res) {
            if (res.code == 200) {
              $(".message-success .message_content").html("添加成功");
              $(".message-success").removeClass("message-hide");
              setTimeout(() => {
                jump("/page/resourceManage/index.html", "采集资源监管");
                $(".message-success").addClass("message-hide");
              }, 2000);
            }
          },
        });
      }

      computeForm.classList.add("was-validated");
    }
  },
  false
);

function rollBack() {
  jump("/page/resourceManage/index.html", "采集资源监管");
}
