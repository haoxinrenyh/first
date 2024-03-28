var isNotback = true;

function jump(path, name, parentname, obj) {
  /*  document.querySelector("#marklayer").classList.remove("mark-show");
  document.querySelector("#marklayer .loading").innerHTML = "加 载 中 ···"; */
  document.querySelector(".message-success").classList.add("message-hide");
  document.querySelector(".message-error").classList.add("message-hide");

  if (!localStorage.getItem("token")) {
    window.location.href = "/page/login/index.html";
    return;
  }

  //跨栏目跳转 根据地址栏参数修改首页加载的中心区域页面
  if (window.location.search) {
    var url = window.location.search;
    var urlParams = new Object();
    if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for (var i = 0; i < strs.length; i++) {
        urlParams[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
      }
    }
    // console.log(urlParams);
    for (var key in urlParams) {
      /* if (key == "menu" && urlParams[key] == 1) {
        path = "";
        name = "";
        obj = {};
      } */
    }
  }

  if (path) {
    sessionStorage.setItem("path", path);
    sessionStorage.setItem("name", name);
    sessionStorage.setItem("parentname", parentname);
  }
  if (obj) {
    sessionStorage.setItem("obj", JSON.stringify(obj));
  } else {
    sessionStorage.removeItem("obj");
  }

  if (
    (name == "采集市场" && path == "/index.html") ||
    path == "/page/spiderMarket/index.html"
  ) {
    path = "/index.html";
  }

  var parentMenuItem = "";
  for (let i = 0; i < links.length; i++) {
    if (links[i].innerText.trim() == name) {
      if (parentname && parentname != "undefined") {
        if (
          links[
            i
          ].parentElement.parentElement.parentElement.children[0].innerText.trim() ==
          parentname
        ) {
          matchingMenuItem = links[i];
        }
      } else {
        matchingMenuItem = links[i];
      }
    }
    if (links[i].innerText.trim() == parentname) {
      parentMenuItem = links[i];
    }
  }

  changemenu(path, parentMenuItem);

  if (obj) {
    var arr = [];
    for (var key in obj) {
      arr.push({
        key: key,
        value: obj[key],
      });
    }
    var str = "";
    for (let i = 0; i < arr.length; i++) {
      if (i > 0) {
        str += "&";
      }
      str += arr[i].key + "=" + arr[i].value;
    }
    if (arr.length > 0) {
      path += "?" + str;
    }
  }

  if (isNotback) {
    history.pushState("pushState", path, path);
  }

  if (path) {
    if (name == "采集市场" && path == "/index.html") {
      $("#main").load("/page/spiderMarket/index.html");
    } else {
      $("#main").load(path);
    }
  }
}

function changemenu(path, parentMenuItem) {
  if (!path) {
    return;
  }

  for (let i = 0; i < links.length; i++) {
    links[i].classList.remove("active");
  }

  if (matchingMenuItem) {
    matchingMenuItem.classList.add("active");
  }
  window.scrollTo(0, 0); //重置滚动条
}

var routeList = [
  {
    url: "/index.html",
    name: "采集市场",
    parentname: "",
  },
  {
    url: "/page/spiderMarket/templateList.html",
    name: "采集市场",
    parentname: "",
  },
  {
    url: "/page/spiderMarket/templateDetail.html",
    name: "采集市场",
    parentname: "",
  },
  {
    url: "/page/spiderApply/index.html",
    name: "采集应用",
    parentname: "",
  },
  {
    url: "/page/spiderApply/websiteList.html",
    name: "采集应用",
    parentname: "",
  },
  {
    url: "/page/spiderApply/addSource.html",
    name: "采集应用",
    parentname: "",
  },
  {
    url: "/page/spiderApply/seedDetail.html",
    name: "采集应用",
    parentname: "",
  },
  {
    url: "/page/spiderApply/websiteDetail.html",
    name: "采集应用",
    parentname: "",
  },
  {
    url: "/page/spiderApply/fieldKinds.html",
    name: "采集应用",
    parentname: "",
  },
  {
    url: "/page/spiderApply/websiteKinds.html",
    name: "采集应用",
    parentname: "",
  },
  {
    url: "/page/spiderApply/dataKinds.html",
    name: "采集应用",
    parentname: "",
  },
  {
    url: "/page/dataResult/index.html",
    name: "采集数据",
    parentname: "",
  },
  {
    url: "/page/dataResult/statsAnalysis.html",
    name: "采集数据",
    parentname: "",
  },
  {
    url: "/page/spiderTemplate/index.html",
    name: "数据采集模板",
    parentname: "",
  },
  {
    url: "/page/spiderTemplate/template.html",
    name: "数据采集模板",
    parentname: "",
  },
  {
    url: "/page/systemLogs/index.html",
    name: "系统日志",
    parentname: "",
  },
  {
    url: "/page/helpDoc/index.html",
    name: "帮助文档",
    parentname: "",
  },
];

//监听浏览器回退/前进
window.addEventListener(
  "popstate",
  function (e) {
    // console.log(e.target.location.pathname);
    isNotback = false;
    var url = e.target.location.pathname;

    routeList.forEach((item, index) => {
      if (url.indexOf(item.url) > -1) {
        jump(url, item.name, item.parentname);
        isNotback = true;
        return false;
      }
    });
  },
  false
);
