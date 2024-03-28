/**
 * Created by wei.Li on 15/8/24.
 */


/**
 * 可配置的数据类型内容封装
 */
var Type = {

	/**
	 * 匹配 *
	 */
	"All": {
		"name": "All",
		"keyword": "*",
		"set": function (name, value) {
			var b = (value === this.keyword);
			if (b) {
				setChecked(name, this.name);
			}
			return b;
		}
		,
		"get": function (name) {
			return this.keyword;
		}
	},
	/**
	 * 周期性 x-x
	 */
	"Cyclic": {
		"name": "Cyclic",
		"keyword": "-",
		"set": function (name, value) {

			var vr = value.split(this.keyword);
			var b = vr.length === 2;

			if (b) {
				setChecked(name, this.name);
				var id_1 = name + this.name + "_1";
				var id_2 = name + this.name + "_2";
				$("#" + id_1).val(vr[0]);
				$("#" + id_2).val(vr[1]);
			}
			return b;
		},
		"get": function (name) {
			var id_1 = name + this.name + "_1";
			var id_2 = name + this.name + "_2";
			var val1 = $("#" + id_1).val();
			var val2 = $("#" + id_2).val();
			return (val1 && val2) && (val1 + this.keyword + val2);
		}
	},
	/**
	 * 从 x 开始 ,每 x 执行一次
	 */
	"Interval": {
		"name": "Interval",
		"keyword": "/",
		"set": function (name, value) {
			var vr = value.split(this.keyword);
			var b = vr.length === 2;

			if (b) {
				setChecked(name, this.name);
				var id_1 = name + this.name + "_1";
				var id_2 = name + this.name + "_2";
				$("#" + id_1).val(vr[0]);
				$("#" + id_2).val(vr[1]);
			}
			return b;
		},
		"get": function (name) {
			var id_1 = name + this.name + "_1";
			var id_2 = name + this.name + "_2";
			var val1 = $("#" + id_1).val();
			var val2 = $("#" + id_2).val();
			return (val1 && val2) && (val1 + this.keyword + val2);
		}
	},
	/**
	 * 不指定
	 */
	"NotAssigned": {
		"name": "NotAssigned",
		"keyword": "?",
		"set": function (name, value) {
			var b = value === this.keyword;
			if (b) {
				setChecked(name, this.name);
			}
			return b;
		},
		"get": function (name) {
			return this.keyword;
		}
	},
	/**
	 * 本月最后一天
	 */
	"LastDayOfMonth": {
		"name": "LastDayOfMonth",
		"keyword": "L",
		"set": function (name, value) {
			var b = value === this.keyword;
			if (b) {
				setChecked(name, this.name);
			}

			return b;
		},
		"get": function (name) {
			return this.keyword;
		}
	},
	/**
	 * 每月 x 号最近的那个工作日
	 */
	"RecentDays": {
		"name": "RecentDays",
		"keyword": "W",
		"set": function (name, value) {
			var b = value[value.length - 1] === this.keyword;
			if (b) {
				setChecked(name, this.name);
				$("#" + name + this.name + "_1").val(value.substring(0, value.length - 1));
			}
			return b;
		},
		"get": function (name) {
			var id_1 = name + this.name + "_1";
			var val = $("#" + id_1).val();
			return val && (val + this.keyword);
		}
	},
	/**
	 * 本月最后一个工作日
	 */
	"LastDayOfMonthRecentDays": {
		"name": "LastDayOfMonthRecentDays",
		"keyword": "LW",
		"set": function (name, value) {
			var b = value === this.keyword;
			if (b) {
				setChecked(name, this.name);
			}
			return b;
		},
		"get": function (name) {
			return this.keyword;
		}
	},
	/**
	 * 第 x 周的星期 x
	 */
	"WeeksOfWeek": {
		"name": "WeeksOfWeek",
		"keyword": "#",
		"set": function (name, value) {
			var vr = value.split(this.keyword);
			var b = vr.length === 2;

			if (b) {
				setChecked(name, this.name);
				var id_1 = name + this.name + "_1";
				var id_2 = name + this.name + "_2";
				$("#" + id_1).val(vr[0]);
				$("#" + id_2).val(vr[1]);
			}
			return b;
		},
		"get": function (name) {
			var id_1 = name + this.name + "_1";
			var id_2 = name + this.name + "_2";
			var val1 = $("#" + id_1).val();
			var val2 = $("#" + id_2).val();
			return (val1 && val2) && (val1 + this.keyword + val2);
		}
	},
	/**
	 * 本月最后一个星期 x
	 */
	"LastWeekOfMonth": {
		"name": "LastWeekOfMonth",
		"keyword": "L",
		"set": function (name, value) {
			var length = value.length;
			var b = length > 1 && value[length - 1] === this.keyword;
			if (b) {
				setChecked(name, this.name);
				$("#" + name + this.name + "_1").val(value.substring(0, length - 1));
			}
			return b;
		},
		"get": function (name) {
			var id_1 = name + this.name + "_1";
			var val = $("#" + id_1).val();
			return val && (val + this.keyword);
		}
	},
	/**
	 * 指定
	 */
	"Assigned": {
		"name": "Assigned",
		"keyword": ",",
		"set": function (name, value) {
			var b = value || undefined;

			if (value) {
				value = (value.indexOf(",") === -1) ? value : value.split(",");

				setChecked(name, this.name);
				var id_1 = name + this.name + "_1";
				var $2 = $("#" + id_1 + " .cbx");
				$2.each(function(i,item){
					if(item.value==value){
						item.checked = true;
					}else{
						item.checked = false;
					}
				})
				// $2.val(value);
				// $2.trigger("chosen:updated");
			}
			return b;
		},
		"get": function (name) {
			var id_1 = name + this.name + "_1";
			var $allCbx = $("#" + id_1+" .cbx:checked");
			var id_array=new Array();
			$allCbx.each(function(){
				id_array.push($(this).val());//向数组中添加元素
			});
			var val1 = id_array.join(',');//将数组元素连接起来以构建一个字符串
			// var val1 = $("#" + id_1).val();
			return val1 || undefined;
		}
	}
};

/**
 * @type {*[]} 秒-年对象
 */
var TimeObject = [

	{
		radioName: "secondType",
		min: 0,
		max: 59
	},
	{
		radioName: "minuteType",
		min: 0,
		max: 59
	},
	{
		radioName: "hourType",
		min: 0,
		max: 23
	},
	{
		radioName: "dayType",
		min: 1,
		max: 31
	},
	{
		radioName: "monthType",
		min: 1,
		max: 12
	},
	{
		radioName: "weekType",
		min: 1,
		max: 7
	},
	{
		radioName: "yearType",
		min: 2015,
		max: 2100
	}
];

/**
 * @type {*[]} 周英文描述正则-数字描述
 */
var WEEK_DESCRIBE = [
	{
		RegExp: new RegExp("MON", "g"),
		WeekNum: 1
	}, {
		RegExp: new RegExp("THU", "g"),
		WeekNum: 2
	}, {
		RegExp: new RegExp("WED", "g"),
		WeekNum: 3
	}, {
		RegExp: new RegExp("THU", "g"),
		WeekNum: 4
	}, {
		RegExp: new RegExp("FRI", "g"),
		WeekNum: 5
	}, {
		RegExp: new RegExp("SAT", "g"),
		WeekNum: 6
	}, {
		RegExp: new RegExp("SUN", "g"),
		WeekNum: 7
	}
];
/**
 * @param name 单选框按钮 name
 * @returns {String}
 */
var getChecked = function (name) {
	return $("input[name='" + name + "']:checked").val();
};

/**
 * @param name 单选框按钮 name
 * @param value 单选框 待设置的值
 * @returns {String}
 */
var setChecked = function (name, value) {
	$("input[name='" + name + "'][value='" + value + "']").prop("checked", true);
};



//init
$(function () {

	var initFlg=true;
	var $result = $("#result");

	function getDateList() {
		if (!$result.val()) {
			$result.focus();
			showErrorBox("请输入cron表达式！");
			return false;
		} else {
			$('#runTime').empty();
			$.ajax({
				type : "POST",
				dataType : "json",
				data : {
					cron : $result.val()
				},
				url : getRealPath() + "/spiderflow/recent5TriggerTime",
				async : false,
				success : function(data) {
					if (data) {
						for (var i = 0; i < data.length; i++) {
							var html = "<tr><td>" + data[i] + "</td></tr>";
							$('#runTime').append(html);
						}
					}
				}
			});
		}
	}

	/**
	 * 重置 cron 串内容
	 */
	var reset = function () {
		var r = '';
		TimeObject.forEach(function (v) {
			var radioName = v.radioName;
			var value = Type[getChecked(radioName)].get(radioName);
			$('input[name=v_'+radioName+']').val(value);
			value = value || "?";
			r += value + " ";
		});

		if (r) {
			if($result.val()!=r.trim()){
				$result.val(r.trim());
				if(initFlg==false){
					getDateList();
				}
			}
		}

	};

	/**
	 * 反解析
	 */
	var analysis = function () {
		var r = $result.val().trim().replace(/，/g, ',').replace(/\s+/g, ' ').toLocaleUpperCase();
		WEEK_DESCRIBE.forEach(function (v) {
			r = r.replace(v.RegExp, v.WeekNum);
		});
		$result.val(r);
		var vr = r.split(' ');
		if (vr.length === 6) {
			vr.push("?");
		}
		vr.forEach(function (v, i) {
			var timeObject = TimeObject[i];
			var radioName = timeObject.radioName;
			for (var o in  Type) {
				var b = Type[o].set(radioName, v);
				if (b) {
					break;
				}
			}
		});
	};


	/**
	 * 下拉框填充
	 */
	TimeObject.forEach(function (v) {
		var radioName = v.radioName;
		var min = v.min;
		var max = v.max;
		var idAssigned = radioName + Type.Assigned.name + "_1";
		var $currChosen = $("#" + idAssigned);
		if ($currChosen) {
			for (; min <= max; min++) {
				var option = "<div class='block'><input type='checkbox' class='cbx' name='"+idAssigned+"' value='" + min + "'/>" + min + "</div>";
				$currChosen.append(option);
			}
			var $cbxChosen = $(".cbx");

			$cbxChosen.change(function () {
				reset();
			});

		}
	});


	//绑定事件
	var $tabContentInput = $(".tab-content");

	$tabContentInput.find("input[type=radio]").change(function () {
		reset();
	});
	$tabContentInput.find("input[type=number]").keyup(function () {
		reset();
	});

	$result.change(function () {
		var strs = $result.val().split(' ');
		for (var i = 0; i < strs.length; i++) {
			switch (i) {
				case 0:
					$('input[name=v_secondType]').val(strs[i]);
					break;
				case 1:
					$('input[name=v_minuteType]').val(strs[i]);
					break;
				case 2:
					$('input[name=v_hourType]').val(strs[i]);
					break;
				case 3:
					$('input[name=v_dayType]').val(strs[i]);
					break;
				case 4:
					$('input[name=v_monthType]').val(strs[i]);
					break;
				case 5:
					$('input[name=v_weekType]').val(strs[i]);
					break;
				case 6:
					$('input[name=v_yearType]').val(strs[i]);
					break;
			}
		}
		analysis();
		getDateList();
	});

	$("#analysis").click(function () {
		analysis()
	});

/*	reset();
	$result.val("* * * * * ? *");
	$('input[name=v_secondType]').val("*");
	$('input[name=v_minuteType]').val("*");
	$('input[name=v_hourType]').val("*");
	$('input[name=v_dayType]').val("*");
	$('input[name=v_monthType]').val("*");
	$('input[name=v_weekType]').val("?");
	$('input[name=v_yearType]').val("*");*/
	// analysis();
	initFlg=false;
});

