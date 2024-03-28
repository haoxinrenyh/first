
//编辑cron
function cron() {
    parent.layer.open({
        type: 2,
        area: ['50%', '50%'],
        fixed: false, //不固定
        maxmin: true,
        content: getRealPath() + '/spiderFlowSkip/cron?cron=' +$("#cron").val()
    });
}
//获取数据类型
function websitetype() {
    $.ajax({
        url : getRealPath() + '/spiderflow/websitetype',
        type : 'post',
        dataType : 'json',
        async :false,
        success : function(res){
            if(res.status == 200){
               var typeList =  res.data;
                for(var i =0; i < typeList.length; i++){
                   var op = "<option value='"+typeList[i]["id"]+"'>"+typeList[i]["typename"]+"</option>";
                    if(data_type_id && data_type_id == typeList[i]["id"]){
                        op = "<option selected value='"+typeList[i]["id"]+"'>"+typeList[i]["typename"]+"</option>";
                    }
                    $("#data_type_id").append(op)
                }
                //$('#data_type_id').searchableSelect();
            }
        }
    })
}

$(function () {
    websitetype();
//    customerlist();
//	for(let ii = 0;ii<customerlistdata.length;ii++){
//		var op = '';
//        	   op = "<option value='"+customerlistdata[ii]["credit_code"]+"'>"+customerlistdata[ii]["customer_short_name"]+"</option>";  
//        $("#customlable").append(op);
//	}

})


//获取客户标签
function customerlist() {
    $.ajax({
        url : getRealPath() + '/spiderflow/customerlist',
        type : 'post',
        dataType : 'json',
        async :false,
        success : function(res){
        	console.info(res);
               let resdata =  res.data;
               customerlistdata = resdata;
            }
    })
}

//获取客户标签
//function customerlist() {
//    $.ajax({
//        url : getRealPath() + '/spiderflow/customerlist',
//        type : 'post',
//        dataType : 'json',
//        async :false,
//        success : function(res){
//        	console.info(res);
//               var customerlist =  res.data;
//                for(var i =0; i < customerlist.length; i++){
//                   var op = "<option value='"+customerlist[i]["credit_code"]+"'>"+customerlist[i]["customer_short_name"]+"</option>";
//                    $("#customlable").append(op)
//                }
//            }
//    })
//}





function onlyNumber(obj) {
    //先把非数字的都替换掉，除了数字和
    obj.value = obj.value.replace(/[^\d]/g, '');
    //前两位不能是0加数字
    obj.value = obj.value.replace(/^0\d[0-9]*/g, '');
    //必须保证第一个为数字而不是.
    obj.value = obj.value.replace(/^\./g, '');
}