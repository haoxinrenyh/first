var baseAPI = "http://192.168.71.52:8031";

//jq 请求拦截器
$.ajaxSetup({
  contentType: "application/x-www-form-urlencoded;charset=utf-8",
  complete: function (XMLHttpRequest, textStatus) {
    //通过XMLHttpRequest取得响应结果
    var res = XMLHttpRequest.responseText;
    try {
      var jsonData = JSON.parse(res);
      if (jsonData.code != 200) {
        $(".message-error .message_content").html(jsonData.msg);
        $(".message-error").removeClass("message-hide");
        setTimeout(() => {
          $(".message-error").addClass("message-hide");
        }, 2000);
      }
    } catch (e) {}
  },
  error(xhr, status, error) {},
  beforeSend: function (xhr) {
    //可以设置自定义标头
    // xhr.setRequestHeader("token", token);
  },
});
