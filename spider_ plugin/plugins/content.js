// 监听鼠标移入事件
document.addEventListener("mouseover", function (event) {
  var target = event.target;
  target.style.backgroundColor = "rgba(159,219,249,0.5)";
});

// 监听鼠标移出事件
document.addEventListener("mouseout", function (event) {
  var target = event.target;
  target.style.backgroundColor = "";
});

// 禁用点击事件的函数
function disableClickEvent(event) {
  event.stopPropagation();
  event.preventDefault();

  var target = event.target;
  target.style.outline = "2px solid #22895e";

  var xpath = getElementXpath(target);
  console.log(xpath);
  chrome.runtime.sendMessage({
    action: "sendXpath",
    xpath,
  });
}

//给所有元素添加禁用点击事件
function addDisableClickEvent() {
  // 获取所有的元素
  var elements = document.getElementsByTagName("*");

  // 遍历所有元素，禁用点击事件
  for (var i = 0; i < elements.length; i++) {
    elements[i].addEventListener("click", disableClickEvent, true);
  }
}
addDisableClickEvent();

//给所有元素移除禁用点击事件
function removeDisableClickEvent() {
  // 获取所有的元素
  var elements = document.getElementsByTagName("*");

  // 遍历所有元素，禁用点击事件
  for (var i = 0; i < elements.length; i++) {
    elements[i].removeEventListener("click", disableClickEvent, true);
  }
}

chrome.runtime.onMessage.addListener(function (message, sender, sendResponse) {
  //根据单条数据的xpath获取到所有的列表数据
  if (message.action === "fetchList") {
    var xpath = message.xpath;
    if (xpath) {
      fetchList(xpath);
    }
  }
  //根据详情页xpath获取元素内容
  else if (message.action === "fetchDetail") {
    var xpathList = message.xpath;
    var dataList = message.data;

    //有列表数据为开始采集 无则是预览数据
    if (dataList && dataList.length > 0) {
      //发送消息给background.js启动定时采集详情任务
      chrome.runtime.sendMessage({ action: "intervalFetch", data: dataList });
    } else {
      if (xpathList.length > 0) {
        fetchDetail(xpathList, 0);
      }
    }
  } else if (message.action === "getPaginationXpath") {
    // 获取页面中的所有元素
    var elements = document.getElementsByTagName("*");
    // 遍历所有元素
    for (var i = 0; i < elements.length; i++) {
      var element = elements[i];
      // 判断元素是否为分页按钮
      if (isPaginationButton(element)) {
        console.log(element);
        element.style.outline = "2px solid #22895e";
        var xpath = getElementXpath(element);
        chrome.runtime.sendMessage({
          action: "sendPaginationXpath",
          xpath,
        });
      }
    }
  }
  //局部刷新数据
  else if (message.action === "localRefresh") {
    var pageXpath = message.pageXpath;
    var paginationBtn = document.evaluate(
      pageXpath,
      document,
      null,
      XPathResult.FIRST_ORDERED_NODE_TYPE,
      null
    ).singleNodeValue;

    //取消监听禁用所有元素点击事件
    removeDisableClickEvent();

    var pageTimes = 0;

    if (message.page) {
      pageTimes = parseInt(message.page);
    }

    var count = 0;
    //每间隔5秒点击一次分页
    var timer = setInterval(() => {
      //抓取第一页数据 不需要翻页
      if (count > 0 && count < pageTimes + 1) {
        paginationBtn.click();
      }

      setTimeout(() => {
        // 将页面滚动到底部
        var element = document.documentElement;
        element.scrollTop = element.scrollHeight - element.clientHeight;

        var xpath = message.listXpath;
        //抓取数据
        fetchList(xpath, 1);

        //翻页次数累加
        count++;

        if (count >= pageTimes + 1) {
          //清除点击分页的定时器
          clearInterval(timer);
          //添加监听禁用所有元素点击事件
          addDisableClickEvent();

          //抓取结束
          chrome.runtime.sendMessage({
            action: "fetchEnded",
          });
        }
      }, 3000);
    }, 5000);
  }
  //全局刷新数据 分页地址栏更改
  else if (message.action === "globalRefresh") {
    var pageXpath = message.pageXpath;
    var paginationBtn = document.evaluate(
      pageXpath,
      document,
      null,
      XPathResult.FIRST_ORDERED_NODE_TYPE,
      null
    ).singleNodeValue;

    //取消监听禁用所有元素点击事件
    removeDisableClickEvent();

    var pageTimes = 0;
    if (message.page) {
      pageTimes = parseInt(message.page);
    }

    var count = 0;

    var xpath = message.listXpath;
    //抓取数据
    fetchList(xpath, 2);

    //翻页次数累加
    count++;

    setTimeout(() => {
      chrome.runtime.sendMessage({
        action: "initiateInterval",
      });
      paginationBtn.click();
    }, 3000);

    // var timer = setInterval(() => {
    //   //抓取第一页数据 不需要翻页
    //   if (count > 0 && count < pageTimes + 1) {
    //     paginationBtn.click();
    //   }
    //   setTimeout(() => {
    //     //翻页次数累加
    //     count++;
    //   }, 3000);
    // }, 5000);
  }
  //按钮查看更多
  else if (message.action === "btnLoadmore") {
    var pageXpath = message.pageXpath;
    var paginationBtn = document.evaluate(
      pageXpath,
      document,
      null,
      XPathResult.FIRST_ORDERED_NODE_TYPE,
      null
    ).singleNodeValue;

    //取消监听禁用所有元素点击事件
    removeDisableClickEvent();

    var pageTimes = 0;

    if (message.page) {
      pageTimes = parseInt(message.page);
    }

    var count = 0;

    var timer = setInterval(() => {
      //抓取第一页数据 不需要翻页
      if (count > 0 && count < pageTimes + 1) {
        paginationBtn.click();
      }

      setTimeout(() => {
        // 将页面滚动到底部
        var element = document.documentElement;
        element.scrollTop = element.scrollHeight - element.clientHeight;

        var xpath = message.listXpath;
        //抓取数据
        fetchList(xpath, 2);

        //翻页次数累加
        count++;

        if (count >= pageTimes + 1) {
          //清除点击分页的定时器
          clearInterval(timer);
          //添加监听禁用所有元素点击事件
          addDisableClickEvent();

          //抓取结束
          chrome.runtime.sendMessage({
            action: "fetchEnded",
          });
        }
      }, 3000);
    }, 5000);
  }
  //滚动查看更多
  else if (message.action === "scrollLoadmore") {
    var pageTimes = 0;

    if (message.page) {
      pageTimes = parseInt(message.page);
    }

    var count = 0;

    var timer = setInterval(() => {
      // 将页面滚动到底部
      var element = document.documentElement;
      element.scrollTop = element.scrollHeight - element.clientHeight;
      setTimeout(() => {
        var xpath = message.listXpath;
        //抓取数据
        fetchList(xpath, 2);
        //翻页次数累加
        count++;
        if (count >= pageTimes + 1) {
          //清除点击分页的定时器
          clearInterval(timer);

          //抓取结束
          chrome.runtime.sendMessage({
            action: "fetchEnded",
          });
        }
      }, 3000);
    }, 5000);
  }
});

//抓取列表数据
function fetchList(xpath, type) {
  //type 0:预抓取 数据展示后清除  1:全局/局部刷新数据需要合并 2.下滑查看更多数据不需要合并
  var element = document.evaluate(
    xpath,
    document,
    null,
    XPathResult.FIRST_ORDERED_NODE_TYPE,
    null
  ).singleNodeValue;
  var parentElement = element.parentElement;
  var children = parentElement.children;

  // console.log(children, "获取所有列表元素");

  var totalList = [];

  for (var i = 0; i < children.length; i++) {
    if (children[i].tagName.toLowerCase() === "script") {
      continue;
    }
    var elementTree = traverse(children[i]);
    var singleList = traverseAndExtractText(elementTree);
    totalList.push(singleList);
  }

  // console.log(totalList, "抓取的列表数据");

  chrome.runtime.sendMessage({
    action: "sendTotalList",
    data: totalList,
    type,
  });
}

//抓取详情数据
function fetchDetail(xpathList, type) {
  //type 0:预抓取  1:正式采集入库
  var detailList = [];
  for (var i = 0; i < xpathList.length; i++) {
    var element = document.evaluate(
      xpathList[i],
      document,
      null,
      XPathResult.FIRST_ORDERED_NODE_TYPE,
      null
    ).singleNodeValue;
    if (element) {
      var text = element.innerHTML;
      detailList.push(text);
    }
  }

  if (detailList.length > 0) {
    chrome.runtime.sendMessage({
      action: "sendDetailText",
      data: detailList,
      type,
    });
  }
}

window.onload = function () {
  // 自动采集详情页数据
  chrome.storage.sync.get("prev-page", function (res) {
    if (
      res["prev-page"] &&
      res["prev-page"].split("?")[0] !== window.location.href.split("?")[0]
    ) {
      chrome.storage.sync.get("xpath-detail", function (res) {
        console.log("循环抓取详情页数据");
        fetchDetail(res["xpath-detail"], 1);
      });
    }
  });
};

// 判断元素是否为分页按钮的函数
function isPaginationButton(element) {
  // 判断元素的文本内容是否包含"下一页"、"下页"等关键词
  var keywords = ["下一页", "下页"];
  for (var i = 0; i < keywords.length; i++) {
    if (
      element.children.length == 0 &&
      !["script", "style"].includes(element.tagName.toLowerCase())
    ) {
      if (element.innerHTML.trim().includes(keywords[i])) {
        return true;
      }

      if (element.getAttribute("title")) {
        if (element.getAttribute("title").trim().includes(keywords[i])) {
          return true;
        }
      }
    }
  }

  return false;
}

// 获取选中元素的 XPath
function getElementXpath(element) {
  var xpath = "";
  while (element && element.nodeType === 1) {
    var id = getElementId(element);
    var tagName = element.tagName.toLowerCase();
    var index = getElementIndex(element);
    // xpath = "/" + tagName + "[" + index + "]" + xpath;
    xpath = "/" + (index == 1 ? tagName : tagName + "[" + index + "]") + xpath;
    element = element.parentNode;
  }
  return xpath;
}

function getElementId(element) {
  if (element.id) {
    return element.id;
  }
  var siblings = element.parentNode.children;
  for (var i = 0; i < siblings.length; i++) {
    if (siblings[i] === element) {
      return i + 1;
    }
  }
  return null;
}

// 获取元素在父节点中的索引
function getElementIndex(element) {
  var index = 1;
  var previousSibling = element.previousSibling;
  while (previousSibling) {
    if (
      previousSibling.nodeType === 1 &&
      previousSibling.tagName === element.tagName
    ) {
      index++;
    }
    previousSibling = previousSibling.previousSibling;
  }
  return index;
}

// 获取元素的选择器
function getElementSelector(element) {
  if (element.id) {
    return `#${element.id}`;
  }
  if (element.tagName === "BODY") {
    return "body";
  }
  const selector = [];
  let currentElement = element;
  while (currentElement.parentElement) {
    const tagName = currentElement.tagName.toLowerCase();
    const index =
      Array.from(currentElement.parentElement.children).indexOf(
        currentElement
      ) + 1;
    selector.unshift(`${tagName}:nth-child(${index})`);
    // if (currentElement.id) {
    //   break;
    // }
    currentElement = currentElement.parentElement;
  }
  return selector.join(" > ");
}

//复制到剪切板
function copyToClipboard(text) {
  var input = document.createElement("textarea");
  input.value = text;
  document.body.appendChild(input);
  input.select();
  document.execCommand("copy");
  document.body.removeChild(input);
}

//根据网页的相对路径获取绝对路径
function relativePathToFullPath(url) {
  var a = document.createElement("A");
  a.href = url;
  url = a.href;
  return url;
}

//判断是否存在被标签包裹以外的内容
function hasUnwrappedContent(element) {
  // 获取元素的子节点列表
  var childNodes = element.childNodes;
  // console.log(childNodes);
  // 遍历子节点列表，判断是否存在被标签包裹以外的内容
  for (var i = 0; i < childNodes.length; i++) {
    var node = childNodes[i];
    // 判断节点类型是否为文本节点 并且该文本节点不是该元素内部的唯一节点
    if (node.nodeType === 3 && childNodes.length > 1) {
      //去除换行符和空格
      if (removeChar(node.nodeValue)) {
        return true;
      }
    }
  }

  return false;
}

//递归遍历元素
function traverse(element, depth = 0) {
  // 创建一个包含当前元素的对象
  var result = {
    element: element,
    depth: depth,
  };

  // 遍历元素的子元素
  var children = element.children;
  if (children.length > 0) {
    // 递归遍历子元素的关系，并将结果作为当前元素的子元素
    result.children = [];
    for (var i = 0; i < children.length; i++) {
      var child = children[i];
      // 判断子元素标签名是否为 换行
      if (!["br"].includes(child.tagName.toLowerCase())) {
        var childResult = traverse(child, depth + 1);
        result.children.push(childResult);
      }
    }
  }

  return result;
}

//去除换行符、水平制表符和文字左右空格
function removeChar(str) {
  return str.replace(/\n/g, "").replace(/\t/g, "").trim();
}

//递归取出元素内容
function traverseAndExtractText(data, result = []) {
  //舍弃数据判断
  //button、script标签 元素的display属性为none 元素的visibility属性为hidden  清空子标签
  if (
    ["button", "script"].includes(data.element.tagName.toLowerCase()) ||
    data.element.style.display == "none" ||
    data.element.style.visibility == "hidden"
  ) {
    data.children = [];
  }

  //获取数据判断
  if (data.element) {
    //任何元素中只要包含background-image
    if (data.element.style.backgroundImage) {
      var bgImage = data.element.style.backgroundImage;
      if (bgImage.includes("url")) {
        var bgUrl = bgImage.split("url(")[1].split(")")[0];
        result.push(bgUrl);
      }
    }
    //如果当前标签是a标签
    else if (data.element.tagName.toLowerCase() == "a") {
      //这时候直接获取a标签内部文字进行拼接
      if (data.element.innerHTML) {
        result.push(removeChar(data.element.innerHTML));
      }

      //获取a标签的href
      if (data.element.getAttribute("href")) {
        result.push(relativePathToFullPath(data.element.getAttribute("href")));
      }

      if (hasUnwrappedContent(data.element)) {
        var childNodes = data.element.childNodes;
        for (var i = 0; i < childNodes.length; i++) {
          var node = childNodes[i];
          // 判断节点类型是否为文本节点 并且该文本节点不是该元素内部的唯一节点
          if (node.nodeType === 3 && childNodes.length > 1) {
            //去除换行符和空格
            if (removeChar(node.nodeValue)) {
              var text = removeChar(node.nodeValue);
              result.push(text);
            }
          }
        }
      }
    }
    //img、video标签直接取src即可
    else if (["img", "video"].includes(data.element.tagName.toLowerCase())) {
      if (data.element.getAttribute("src")) {
        result.push(relativePathToFullPath(data.element.getAttribute("src")));
      }
    }
    //除数组中以外的元素
    // ["p","span","li","i","h1","h2","h3","h4","h5","td","dt","dd","time","font","b","strong","em"].includes(data.element.tagName.toLowerCase())
    else if (
      ![
        "a",
        "img",
        "video",
        "div",
        "ul",
        "ol",
        "table",
        "thead",
        "tbody",
        "tr",
        "iframe",
        "dl",
        "form",
      ].includes(data.element.tagName.toLowerCase())
    ) {
      //如果没有子元素 或者 子元素中不包含 div、img、video、a、i标签 直接获取元素内部文字
      if (
        !data.children ||
        (data.children &&
          data.children.length > 0 &&
          !data.children.some((item) =>
            ["div", "img", "video", "a", "i"].includes(
              item.element.tagName.toLowerCase()
            )
          ))
      ) {
        if (data.element.innerHTML) {
          result.push(removeChar(data.element.innerHTML));
        }
      }

      //如果元素中存在多个节点 并且元素内部中存在不被标签包裹的文字
      if (hasUnwrappedContent(data.element)) {
        if (data.element.innerHTML) {
          result.push(removeChar(data.element.innerHTML));
        }
        var childNodes = data.element.childNodes;
        for (var i = 0; i < childNodes.length; i++) {
          var node = childNodes[i];
          // 判断节点类型是否为文本节点 并且该文本节点不是该元素内部的唯一节点
          if (node.nodeType === 3 && childNodes.length > 1) {
            //去除换行符和空格
            if (removeChar(node.nodeValue)) {
              var text = removeChar(node.nodeValue);
              result.push(text);
            }
          }
        }
      }
    }
    //div标签
    else if (data.element.tagName.toLowerCase() == "div") {
      //如果没有子标签 也直接获取内部文字
      if (!data.children) {
        if (data.element.innerHTML) {
          result.push(removeChar(data.element.innerHTML));
        }
      } else {
        if (hasUnwrappedContent(data.element)) {
          var childNodes = data.element.childNodes;
          for (var i = 0; i < childNodes.length; i++) {
            var node = childNodes[i];
            // 判断节点类型是否为文本节点 并且该文本节点不是该元素内部的唯一节点
            if (node.nodeType === 3 && childNodes.length > 1) {
              //去除换行符和空格
              if (removeChar(node.nodeValue)) {
                var text = removeChar(node.nodeValue);
                result.push(text);
              }
            }
          }
        }
      }
    }
  }

  // 遍历子节点
  if (data.children && data.children.length > 0) {
    for (var i = 0; i < data.children.length; i++) {
      traverseAndExtractText(data.children[i], result);
    }
  }

  return result;
}
