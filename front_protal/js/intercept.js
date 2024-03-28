 var baseUrl = window.location.origin + "/spider_factory";
// var baseUrl = "http://192.168.71.52:35209/spider_factory";
//var baseUrl = "http://spider.dingxincloud.com/bupt";
var staticPath = "";
var token = localStorage.getItem("token");

//配置白名单
var whitelist = [
  "/page/login/index.html",
  "/page/spiderApply/seedDetail.html",
  "/page/collectTask/task.html",
];

//jq 请求拦截器
$.ajaxSetup({
  // cache: false,
  // timeout: 5000,
  contentType: "application/x-www-form-urlencoded;charset=utf-8",
  complete: function (XMLHttpRequest, textStatus) {
    //通过XMLHttpRequest取得响应结果
    var res = XMLHttpRequest.responseText;
    try {
      var jsonData = JSON.parse(res);
      var currentPath = window.location.pathname;
      if (whitelist.indexOf(currentPath) == -1) {
        if (jsonData.code == 200 || jsonData.status == 200) {
          return;
        } else {
          $(".message-error .message_content").html(jsonData.msg);
          $(".message-error").removeClass("message-hide");
          setTimeout(() => {
            $(".message-error").addClass("message-hide");
          }, 2000);

          //登录过期返回登录页
          if (jsonData.code == 1201) {
            localStorage.removeItem("token");
            window.location.replace("/page/login/index.html");
          }
        }
      }
    } catch (e) {}
  },
  beforeSend: function (xhr) {
    //可以设置自定义标头
    xhr.setRequestHeader("token", token);
  },
});
