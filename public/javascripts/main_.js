$(document).ready(function() {


  var dataSourceTypeReport = new kendo.data.DataSource({
    transport: {
      read: function(options) {
        $.ajax({
          type: "GET",
          url: "api/typereport",
          contentType: "application/json; charset=utf-8",
          dataType: 'json',
          //data: JSON.stringify({key: "value"}),
          success: function(data) {
            options.success(data);
          }
        });
      }
    }
  });
  var dataSourceDB = new kendo.data.DataSource({
    transport: {
      read: function(options) {
        $.ajax({
          type: "GET",
          url: "api/listdb",
          contentType: "application/json; charset=utf-8",
          dataType: 'json',
          //data: JSON.stringify({key: "value"}),
          success: function(data) {
            options.success(data);
          }
        });
      }
    }
  });

  var dataSourceTypeMode = new kendo.data.DataSource({
    transport: {
      read: function(options) {
        $.ajax({
          type: "GET",
          url: "api/typemode",
          contentType: "application/json; charset=utf-8",
          dataType: 'json',
          //data: JSON.stringify({key: "value"}),
          success: function(data) {
            options.success(data);
          }
        });
      }
    }
  });
  kendo.culture("ru-RU");
  // create DatePicker from input HTML element
  $("#dateBegin").kendoDatePicker({
    format: 'yyyy-MM-dd'//kendo.culture().calendar.patterns.d
  });



  $("#dateEnd").kendoDatePicker({


    // display month and year in the input
    format: 'yyyy-MM-dd'//kendo.culture().calendar.patterns.d
  });

  $("#enterprise").kendoDropDownList({
    dataTextField: "name",
    dataValueField: "id",
    dataSource: dataSourceDB/*{   // specifies data protocol
      transport: {
      read: {
        type: "get",
            dataType: "json",
            url: "http://localhost:9000/api/listdb"
      }
      }
    }*/,
    index: 0,
    change: onChange
  });

  /*$("#reports").kendoDropDownList({
    dataTextField: "text",
    dataValueField: "value",
    dataSource: reportData,
    index: 0,
    change: onChange
  });*/
  $("#report").kendoDropDownList({
    dataTextField: "name",
    dataValueField: "id",
    dataSource:dataSourceTypeReport/* {
       // specifies data protocol
      transport: {

        read: {
          type: "get",
          dataType: "json",
          url: "http://localhost:9000/api/typereport"
        }
      },

    }*/,
    index: 0,
    change: onChange
  })

  $("#typeMode").kendoDropDownList({
    dataTextField: "name",
    dataValueField: "id",
    dataSource: dataSourceTypeMode,
    index: 0
  });


  function onChange() {
    var value = $("#enterprise").val();
  }


/*
  createChart();
  $(document).bind("kendo:skinChange", createChart);
  $(".options").bind("change", refresh);
*/




});
/*function refresh() {
  var chart = $("#chart").data("kendoChart"),
      type = $("input[name=seriesType]:checked").val(),
      stack = $("#stack").prop("checked");

  for (var i = 0, length = series.length; i < length; i++) {
    series[i].stack = stack;
    series[i].type = type;
  };

  chart.setOptions({
    series: series
  });
}

var series = [{
  name: "Total Visits",
  data: [56000, 63000, 74000, 91000, 117000, 138000],

  // Line chart marker type
  markers: { type: "square" }
}, {
  name: "Unique visitors",
  data: [52000, 34000, 23000, 48000, 67000, 83000]
}];

function createChart() {
  $("#chart").kendoChart({
    title: {
      text: "Site Visitors Stats /thousands/"
    },
    legend: {
      position: "bottom"
    },
    seriesDefaults: {
      type: "column",
      stack: true
    },
    series: series,
    valueAxis: {
      line: {
        visible: false
      }
    },
    categoryAxis: {
      categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun"],
      majorGridLines: {
        visible: false
      }
    },
    tooltip: {
      visible: true,
      format: "{0}"
    }
  });
}
*/