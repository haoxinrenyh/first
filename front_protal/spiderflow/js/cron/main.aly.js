//---------公共方法----------
function base64Img2Blob(code) {
    var parts = code.split(';base64,');
    var contentType = parts[0].split(':')[1];
    var raw = window.atob(parts[1]);
    var rawLength = raw.length;
    var uInt8Array = new Uint8Array(rawLength);
    for (var i = 0; i < rawLength; ++i) {
        uInt8Array[i] = raw.charCodeAt(i);
    }
    return new Blob([ uInt8Array ], {
        type : contentType
    });
}
//---------header----------
function showErrorBox(msg){//错误提示框
    $("body").append("<div class='alert alert-danger alert-box' id='errorbox'></div>");
    var errorbox = $('#errorbox');
    errorbox.text(msg);
    errorbox.fadeIn();
    setTimeout(function() {
        errorbox.fadeOut();
    }, 3000)
}
function showSuccessBox(msg){//成功提示框
    $("body").append("<div class='alert alert-success alert-box' id='successbox'></div>");
    var successbox = $('#successbox');
    successbox.text(msg);
    successbox.fadeIn();
    setTimeout(function() {
        successbox.fadeOut();
    }, 2000)
}
function copyStr(str) {//复制文本
    var oInput = document.createElement('textarea');
    oInput.style.position = 'absolute';
    oInput.style.opacity = '0';
    oInput.value = str;
    document.body.appendChild(oInput);
    oInput.select();
    document.execCommand("Copy");
    document.body.removeChild(oInput);
}






