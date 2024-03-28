// Chrome插件中的background.js和content.js是两个不同的脚本文件，分别用于不同的功能。

// background.js：这个脚本文件是插件的后台脚本，它在插件安装后一直运行，并且可以在插件的整个生命周期内访问Chrome浏览器的API。它通常用于处理插件的全局事件、管理插件的状态、与其他页面通信等。background.js可以访问插件的所有资源和功能，但不能直接访问当前打开的页面的DOM。

// content.js：这个脚本文件是在插件注入到当前打开的页面中运行的，它可以直接访问和操作当前页面的DOM和样式。content.js通常用于对当前页面进行修改、添加功能、监听页面事件等。它只能访问当前页面的资源和功能，不能直接访问插件的其他资源和功能。

// 总结来说，background.js主要用于处理插件的全局事件和管理插件的状态，而content.js主要用于在当前页面中操作DOM和样式。

chrome.runtime.onInstalled.addListener(function () {
  console.log("插件已被安装");
});

chrome.runtime.onSuspend.addListener(function () {
  console.log("插件已被卸载");
});

// 监听来自content.js的消息
// chrome.runtime.onMessage.addListener(function (request, sender, sendResponse) {
//   if (request.action == "disablePlugin") {
//     // 关闭插件
//     chrome.browserAction.disable();
//   } else if (request.action == "enablePlugin") {
//     // 开启插件
//     chrome.browserAction.enable();
//   }
// });

// 接收来自 content.js 的消息
chrome.runtime.onMessage.addListener(function (message, sender, sendResponse) {
  if (message.action === "intervalFetch") {
    var dataList = message.data;
    //每隔5秒间隔打开详情页
    for (let i = 0; i < dataList.length; i++) {
      setTimeout(() => {
        for (let j = 0; j < dataList[i].length; j++) {
          //如果是url格式
          if (validateUrl(dataList[i][j])) {
            chrome.runtime.sendMessage({
              action: "updateTab",
              url: dataList[i][j],
            });
          }
        }
      }, i * 5000); // 使用i作为延时的倍数，实现逐个延时执行

      // if (i == dataList.length - 1) {
      //   document.querySelector(".refresh-meta").remove();
      // }
    }
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

// 创建自定义菜单项
// chrome.runtime.onInstalled.addListener(function () {
//   chrome.contextMenus.create({
//     id: "copySelector",
//     title: "Copy Selector",
//     contexts: [
//       "page",
//       "frame",
//       "selection",
//       "link",
//       "editable",
//       "image",
//       "video",
//       "audio",
//     ],
//     documentUrlPatterns: ["*://*/*"],
//   });

//   chrome.contextMenus.create({
//     id: "copyXpath",
//     title: "Copy Xpath",
//     contexts: ["all"],
//   });

//   chrome.contextMenus.create({
//     id: "copyPageXpath",
//     title: "copy PageXpath",
//     contexts: ["all"],
//   });

//   // 添加更多自定义菜单项...
// });

// chrome.contextMenus.onClicked.addListener(function (info, tab) {
//   // console.log(info, tab);
//   if (info.menuItemId === "copyXpath") {
//     chrome.tabs.sendMessage(tab.id, { action: "copyXpath" });
//   } else if (info.menuItemId === "copySelector") {
//     chrome.tabs.sendMessage(tab.id, { action: "copySelector" });
//   } else if (info.menuItemId === "copyPageXpath") {
//     chrome.tabs.sendMessage(tab.id, { action: "copyPageXpath" });
//   }
// });
