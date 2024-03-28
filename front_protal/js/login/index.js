function changeLogin(type) {
  if (type == 1) {
    // $(".login-password").css("display", "none");
    // $(".login-qrcode").css("display", "block");
    getQrcode();
  } else {
    $(".login-password").css("display", "block");
    $(".login-qrcode").css("display", "none");
  }
}

// changeLogin(1);

var timer;
var interval;
function getQrcode() {
  $(".qr-code")
    .html(`<div class="qr_loading refresh"><i class="iconfont icon-shuaxin"></i>
                <div>正在生成二维码</div>
              </div>`);
  $.ajax({
    method: "GET",
    url: baseUrl + "/wechatLogin/loginQRCode",
    contentType: "application/json",
    success: function (res) {
      if (res.code == 200) {
        $(".qr-code").html(
          `<img class="qrcode-img" src="${res.result.qrcodeUrl}" alt="">`
        );

        interval = setInterval(() => {
          judgeLogin(res.result.code);
        }, 1000);

        timer = setTimeout(() => {
          clearInterval(interval);
          $(".qr-code").html(`<div class="refresh" onclick="getQrcode()">
                <i class="iconfont icon-shuaxin"></i>
                <div>二维码已过期，点击刷新</div>
              </div>`);
        }, 1000 * 60 * 5);
      }
    },
  });
}

function judgeLogin(code) {
  $.ajax({
    method: "GET",
    url: baseUrl + "/wechatLogin/checkLogin",
    contentType: "application/json",
    data: { code },
    success: function (res) {
      if (res.code == 200) {
        clearTimeout(timer);
        clearInterval(interval);
        window.location.replace("/index.html");
        localStorage.token = res.token;
        token = res.token;
      }
    },
  });
}

function login() {
  var username = $(".username").val().replace(/\s*/g, ""); //去除空格;
  var password = $(".password").val().replace(/\s*/g, "");
  if (username && password) {
    var formData = new FormData();
    formData.append("username", username);
    formData.append("password", password);
    $.ajax({
      method: "POST",
      url: baseUrl + "/login",
      data: formData,
      contentType: false,
      processData: false,
      success: function (res) {
        if (res.code == 200) {
          localStorage.setItem("token", res.token);
          window.location.replace("/index.html");
        } else {
          $(".message-error .message_content").html(res.msg);
          $(".message-error").removeClass("message-hide");
          setTimeout(() => {
            $(".message-error").addClass("message-hide");
          }, 2000);
        }
      },
    });
  } else {
    $(".message-error .message_content").html("请输入用户名和密码");
    $(".message-error").removeClass("message-hide");
    setTimeout(() => {
      $(".message-error").addClass("message-hide");
    }, 2000);
  }
}
