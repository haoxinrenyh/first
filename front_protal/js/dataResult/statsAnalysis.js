function renderChartOne(title, column_value, line_value) {
  var chartDom = document.getElementById("polylines-chart");
  var myChart = echarts.init(chartDom);
  var option;

  option = {
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow",
      },
    },
    grid: {
      left: "3%",
      right: "4%",
      bottom: "3%",
      containLabel: true,
    },
    xAxis: [
      {
        type: "category",
        data: title,
        axisTick: {
          alignWithLabel: true,
        },
      },
    ],
    yAxis: [
      {
        type: "value",
      },
      {
        type: "value",
      },
    ],
    series: [
      // {
      //   name: "有效站点",
      //   type: "bar",
      //   barWidth: "20",
      //   color: ["#6f7bd8"],
      //   data: column_value,
      // },
      {
        name: "采集数据量",
        type: "line",
        smooth: true,
        symbol: "none",
        color: ["#41c69f"],
        data: line_value,
      },
    ],
  };

  option && myChart.setOption(option);
}

function renderChartTwo(data) {
  var chartDom = document.getElementById("cirque-chart-one");
  var myChart = echarts.init(chartDom);
  var option;

  option = {
    tooltip: {
      trigger: "item",
      formatter: function (params) {
        // console.log(params);
        return params.name + "：" + (params.value * 100).toFixed(2) + "%";
      },
    },
    legend: {
      // show: false,
      orient: "vertical",
      x: "right",
      y: "center",
    },
    series: [
      {
        // name: "Access From",
        type: "pie",
        radius: ["50%", "70%"],
        center: ["40%", "50%"],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: "center",
        },
        labelLine: {
          show: false,
        },
        data: data,
      },
    ],
  };

  option && myChart.setOption(option);
}

function renderChartThree(data) {
  var chartDom = document.getElementById("cirque-chart-two");
  var myChart = echarts.init(chartDom);
  var option;

  option = {
    tooltip: {
      trigger: "item",
      formatter: function (params) {
        // console.log(params);
        return params.name + "：" + (params.value * 100).toFixed(2) + "%";
      },
    },
    legend: {
      // show: false,
      orient: "vertical",
      x: "right",
      y: "center",
    },
    // color: ["#f0883b", "#65c2f9", "#2e65f6"],
    series: [
      {
        // name: "Access From",
        type: "pie",
        radius: ["50%", "70%"],
        center: ["40%", "50%"],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: "center",
        },
        labelLine: {
          show: false,
        },
        data: data,
      },
    ],
  };

  option && myChart.setOption(option);
}

function renderChartFour() {
  var chartDom = document.getElementById("cirque-chart-three");
  var myChart = echarts.init(chartDom);
  var option;

  option = {
    tooltip: {
      trigger: "item",
    },
    legend: {
      show: false,
      orient: "vertical",
      top: "3%",
      left: "left",
    },
    color: ["#3688f7", "#8abbf9", "#4e99f7", "#a7cefb", "#6baaf8"],
    series: [
      {
        // name: "Access From",
        type: "pie",
        radius: ["50%", "70%"],
        avoidLabelOverlap: false,
        label: {
          show: true,
          position: "outside",
          color: "inherit",
        },
        labelLine: {
          show: true,
        },
        data: [
          { value: 1048, name: "微信" },
          { value: 335, name: "政务" },
          { value: 880, name: "新闻" },
          { value: 180, name: "客户端" },
          { value: 580, name: "网站" },
        ],
      },
    ],
  };

  option && myChart.setOption(option);
}

function analysisData() {
  $.ajax({
    method: "GET",
    url: baseUrl + "/result/statistic",
    contentType: "application/json",
    dataType: "json",
    data: {},
    success: function (res) {
      if (res.code == 200) {
        var data = res.result;
        $(".update-time-value").html(data.create_time);

        $(".file_gather_count").html(data.minio_count);
        $(".file_save_count").html(data.minio_size);
        $(".disk_count").html(data.minio_disk_size.toFixed(2) + "GB");

        $(".gather_count").html(data.es_count);
        $(".gather_count_ratio").html(
          (data.es_count_ratio * 100).toFixed(2) + "%"
        );
        $(".gather_count_status").html(
          data.es_count_ratio >= 0 ? "增加" : "减少"
        );

        if (data.es_count_ratio >= 0) {
          $(".gather_count_ratio").addClass("badge-soft-success");
        } else {
          $(".gather_count_ratio").addClass("badge-soft-danger");
        }

        $(".gather_day_count").html(data.es_count_day);
        $(".gather_day_count_ratio").html(
          (data.es_count_day_ratio * 100).toFixed(2) + "%"
        );
        $(".gather_day_count_status").html(
          data.es_count_day_ratio >= 0 ? "增加" : "减少"
        );

        if (data.es_count_day_ratio >= 0) {
          $(".gather_day_count_ratio").addClass("badge-soft-success");
        } else {
          $(".gather_day_count_ratio").addClass("badge-soft-danger");
        }

        $(".data_size").html(data.es_size.toFixed(2));
        $(".data_size_ratio").html((data.es_size_ratio * 100).toFixed(2) + "%");
        $(".data_size_status").html(data.es_size_ratio >= 0 ? "增加" : "减少");

        if (data.es_size_ratio >= 0) {
          $(".data_size_ratio").addClass("badge-soft-success");
        } else {
          $(".data_size_ratio").addClass("badge-soft-danger");
        }

        renderChartOne(data.group_json.title, "", data.group_json.value);

        $(".yesterday_gather_count").html(data.es_count_yesterday);
        $(".yesterday_gather_count_ratio").html(
          (data.es_count_yesterday_ratio * 100).toFixed(2) + "%"
        );

        if (data.es_count_yesterday_ratio >= 0) {
          $(".yesterday_gather_trend").addClass("text-success");
          $(".yesterday_gather_trend .iconfont").addClass("icon-xiaosanjiaoup");
        } else {
          $(".yesterday_gather_trend").addClass("text-danger");
          $(".yesterday_gather_trend .iconfont").addClass(
            "icon-xiaosanjiaodown"
          );
        }

        $(".week_gather_count").html(data.es_count_week);
        $(".week_gather_count_ratio").html(
          (data.es_count_week_ratio * 100).toFixed(2) + "%"
        );

        if (data.es_count_week_ratio >= 0) {
          $(".week_gather_trend").addClass("text-success");
          $(".week_gather_trend .iconfont").addClass("icon-xiaosanjiaoup");
        } else {
          $(".week_gather_trend").addClass("text-danger");
          $(".week_gather_trend .iconfont").addClass("icon-xiaosanjiaodown");
        }
        $(".today_gather_count").html(data.es_count_today);
        renderChartTwo(data.category_json);
        renderChartThree(data.type_json);
      }
    },
  });
}
analysisData();

function rollBack() {
  jump("/page/dataResult/index.html", "采集数据");
}
