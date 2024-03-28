function getUserInfo() {
  $.ajax({
    method: "POST",
    url: baseUrl + "/user/info",
    contentType: "application/json",
    dataType: "json",
    data: {},
    success: function (res) {
      if (res.code == 200) {
        $(".username").html(res.result.user_username);
        $(".realname").html(res.result.user_name ? res.result.user_name : "无");
        if (res.result.user_power == 1) {
          $(".charter").html("管理员");
        } else {
          $(".charter").html("用户");
        }
        $(".department").html(
          res.result.institution_name ? res.result.institution_name : "无"
        );
        $(".register-time").html(res.result.dateTime);
      }
    },
  });
}
getUserInfo();

/* function viewPassword() {
  $(".password").html(password);
  $(".show-code").addClass("hide");
  $(".hide-code").removeClass("hide");
}

function hidePassword() {
  $(".password").html("********");
  $(".show-code").removeClass("hide");
  $(".hide-code").addClass("hide");
} */

function editPassword() {
  $(".old-password").val("");
  $(".new-password").val("");
  $(".confirm-password").val("");
  $("#edit-password-form").removeClass("was-validated");
  $(".old-password").removeClass("is-invalid");
  $(".old-password").next().css("display", "none");
  $(".confirm-password").removeClass("is-invalid");
  $(".confirm-password").next().css("display", "none");
  $("#editPasswordModal").modal("show");
}

function confirmPassword() {
  var newPassword = $(".new-password").val().replace(/\s*/g, "");
  var confirmPassword = $(".confirm-password").val().replace(/\s*/g, "");
  if (newPassword && confirmPassword) {
    if (newPassword == confirmPassword) {
      $(".confirm-password").removeClass("is-invalid");
      $(".confirm-password").next().css("display", "none");
      return true;
    } else {
      $(".confirm-password").addClass("is-invalid");
      $(".confirm-password").next().css("display", "block");
      return false;
    }
  }
}

var newPasswordForm = document.querySelector(".edit-password-form");

newPasswordForm.addEventListener(
  "submit",
  (event) => {
    var password = $(".old-password").val().replace(/\s*/g, "");
    var newPassword = $(".new-password").val().replace(/\s*/g, "");
    if (password && confirmPassword()) {
      $.ajax({
        method: "POST",
        url: baseUrl + "/user/changePassword",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
          password,
          newPassword,
        }),
        success: function (res) {
          if (res.code == 200) {
            $("#editPasswordModal").modal("hide");
            $(".message-success .message_content").html(
              "密码修改成功，请重新登录吧！"
            );
            $(".message-success").removeClass("message-hide");
            setTimeout(() => {
              $(".message-success").addClass("message-hide");
              localStorage.removeItem("token");
              window.location.replace("/page/login/index.html");
            }, 2000);
          }
        },
      });
    } else {
      // $(".old-password").addClass("is-invalid");
      // $(".old-password").next().css("display", "block");
    }
  },
  false
);
