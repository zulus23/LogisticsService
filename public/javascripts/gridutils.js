/**
 * Created by Zhukov on 21.08.2016.
 */


var  gridUtils = (function(){

    var headerFormat = {"class": "table-header-cell",
        style: " overflow: visible; white-space: normal; text-align: center; vertical-align:top; font-size: 10px"};
    var columnFormat  = { "class": "table-cell",         style: "text-align: center; font-size: 11px" };
   /* var names = _.sortBy(_.uniq(_.pluck(dataSourceDB.data(), "customer")), function(n) { return n; });

    function createMultiSelect(element) {
        element.removeAttr("data-bind");

        element.kendoMultiSelect({
            dataSource: _.sortBy(_.uniq(_.pluck(dataSourceDB.data(), "customer")), function(n) { return n; }),
            change: function(e) {
                var filter = { logic: "or", filters: [] };
                var values = this.value();
                $.each(values, function(i, v) {
                    filter.filters.push({field: "customer", operator: "eq", value: v });
                });

                dataSourceDB.filter(filter);
            }
        });
    }*/


  function createGridPlanPrecision(datasourceDB) {
      var names = _.sortBy(_.uniq(_.pluck(dataSourceDB.data(), "customer")), function(n) { return n; });

      function createMultiSelect(element) {
          element.removeAttr("data-bind");

          element.kendoMultiSelect({
              dataSource: _.sortBy(_.uniq(_.pluck(dataSourceDB.data(), "customer")), function(n) { return n; }),
              change: function(e) {
                  var filter = { logic: "or", filters: [] };
                  var values = this.value();
                  $.each(values, function(i, v) {
                      filter.filters.push({field: "customer", operator: "eq", value: v });
                  });

                  dataSourceDB.filter(filter);
              }
          });
      }


      $("#gridView").kendoGrid({
          toolbar: ["excel"],
          excel: {
              allPages: true
          },

          dataSource: dataSourceDB,
          height: '100%',
          groupable: true,
          sortable: true,
          filterable: true,
          //resizable:true,
          pageable: {
              pageSizes: true
          },
          columns: [
              {
                  field: "orderNumber",
                  title: "№ Заказа",
                  width: "70px",
                  headerAttributes: headerFormat,
                  attributes: columnFormat,
                  groupable: false
              },
              {
                  field: "orderLine",
                  title: "№ Строки",
                  width: "60px",
                  headerAttributes: headerFormat,
                  attributes: columnFormat,
                  filterable: false,
                  groupable: false
              },
              {
                  field: "manager",
                  title: "Менеджер",
                  width: "80px",
                  headerAttributes: headerFormat,
                  attributes: columnFormat,
                  aggregates: ["count"],
                  groupHeaderTemplate: "Менеджер: #=value#: #=count# : (#=  Math.round((count/calcAll(data,field,value))*100)#%): Count : #=count#"
              },
              {
                  field: "customer",
                  title: "Клиент",
                  width: "180px",
                  aggregates: ["count"],
                  headerAttributes: headerFormat,
                  attributes: columnFormat,
                  filterable: {
                      ui: createMultiSelect,
                      extra: false
                  },
                  /*groupHeaderTemplate: "Клиент: #=value# : #=count#: (#= Math.round((count/calcAll(data,field,value))*100)#%)"*/
                  groupHeaderTemplate: "Клиент: #=value# : #=count#: (#= calcAll(data,field,value,count)#)"
                  //groupHeaderTemplate: "Клиент: #=value# : #=count#"
              },
              {
                  field: "itemDescription",
                  title: "Наименование заказа",
                  width: "130px",
                  headerAttributes: headerFormat,
                  attributes: columnFormat,
                  filterable: false,
                  groupable: false
              },
              {
                  field: "monthShip",
                  title: "Месяц отгрузки",
                  width: "50px",
                  headerAttributes: headerFormat,
                  attributes: columnFormat,
                  groupable: false,
                  aggregates: ["count"],
                  groupHeaderTemplate: "Месяц отгрузки: #=value# : (#= calcAll(data,field,value)#)"
                  //groupHeaderTemplate: "Месяц отгрузки: #=value# : (#= count#)"
              },
              {
                  field: "datePlanShipFormat", title: "Дата доставки план", width: "80px",
                  headerAttributes: headerFormat, attributes: columnFormat, filterable: false, groupable: false
              },
              {
                  field: "dateActualShipFormat", title: "Дата доставки актуал.", width: "80px",
                  headerAttributes: headerFormat, attributes: columnFormat, filterable: false, groupable: false
              },
              {
                  field: "deviation",
                  title: "Отклонение",
                  width: "65px",
                  headerAttributes: headerFormat,
                  attributes: columnFormat,
                  groupable: false
              },
              {
                  field: "reasonDeviation",
                  title: "Причина отклонений",
                  width: "65px",
                  headerAttributes: headerFormat,
                  attributes: columnFormat,
                  groupable: true,
                  aggregates: ["count"],
                  groupHeaderTemplate: "Причина отклонений: #=value#: #=count# : (#= calcAll(data,field,value,count)#)"
              },

              {
                  field: "calcStatus",
                  title: "Состояние",
                  width: "80px",
                  aggregates: ["count"],
                  headerAttributes: headerFormat,
                  attributes: columnFormat,
                  //groupHeaderTemplate: "Состояние: #=value#: #=count# : (#= Math.round((count/calcAll(data,field,value))*100)#%)"
                  groupHeaderTemplate: "Состояние: #=value#: #=count# : (#= calcAll(data,field,value,count)#)"
                  //groupHeaderTemplate: "Состояние: #=value#: #=count# "
              }

          ],
          change: function(e) {

              var query = new kendo.data.Query(dataSourceDB.data());
              var filters = dataSourceDB.filter();
              filteredData = query.filter(filters).data;

              allDataCount = filteredData.length;

              if(dataSourceDB.group().length > 0 ){
                  graphUtils.createBarGraph(dataSourceDB.group()[0].field,dataSourceDB)
                  //createGraph(dataSourceDB.group()[0].field);
              } else {
                  gridUtils.createBarGraph("",dataSourceDB);
              }

              /*var chart = $("#chart1").data("kendoChart");
               chart.dataSource.filter(dataSourceDB.filter());*/
          }
      });
  };
  function createGridPrecisionProduction() {
        $("#gridView").kendoGrid({
            toolbar: ["excel"],
            excel: {
                allPages: true
            },

            //dataSource: dataSourceDB,
            height: '100%',
            groupable: true,
            sortable: true,
            filterable: true,
            //resizable:true,
            pageable: {
                pageSizes: true
            },
            columns: [
                {
                    field: "orderNumber",
                    title: "№ Заказа",
                    width: "70px",
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    groupable: false
                },
                {
                    field: "orderLine",
                    title: "№ Строки",
                    width: "60px",
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    filterable: false,
                    groupable: false
                },
                {
                    field: "manager",
                    title: "Менеджер",
                    width: "80px",
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    aggregates: ["count"],
                    groupHeaderTemplate: "Менеджер: #=value#: #=count# : (#=  Math.round((count/calcAll(data,field,value))*100)#%): Count : #=count#"
                },
                {
                    field: "customer",
                    title: "Клиент",
                    width: "180px",
                    aggregates: ["count"],
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    /*filterable: {
                     ui: createMultiSelect,
                     extra: false
                     },*/
                    /*groupHeaderTemplate: "Клиент: #=value# : #=count#: (#= Math.round((count/calcAll(data,field,value))*100)#%)"*/
                    groupHeaderTemplate: "Клиент: #=value# : #=count#: (#= calcAll(data,field,value,count)#)"
                    //groupHeaderTemplate: "Клиент: #=value# : #=count#"
                },
                {
                    field: "itemDescription",
                    title: "Наименование заказа",
                    width: "130px",
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    filterable: false,
                    groupable: false
                },
                {
                    field: "monthShip",
                    title: "Месяц отгрузки",
                    width: "50px",
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    groupable: false,
                    aggregates: ["count"],
                    groupHeaderTemplate: "Месяц отгрузки: #=value# : (#= calcAll(data,field,value)#)"
                    //groupHeaderTemplate: "Месяц отгрузки: #=value# : (#= count#)"
                },
                {
                    /*field: "dateCreateOrderFormat",*/ title: "Дата начала производства", width: "80px",
                    headerAttributes: headerFormat, attributes: columnFormat, filterable: false, groupable: false
                },
                {
                    field: "dateBeginProductionFormat", title: "Дата начала производства в плане сутки.", width: "80px",
                    headerAttributes: headerFormat, attributes: columnFormat, filterable: false, groupable: false
                },
                {
                    field: "deviation",
                    title: "Отклонение",
                    width: "65px",
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    groupable: false
                },
                {
                    field: "reasonDeviation",
                    title: "Причина отклонений",
                    width: "65px",
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    groupable: true,
                    aggregates: ["count"],
                    groupHeaderTemplate: "Причина отклонений: #=value#: #=count# : (#= calcAll(data,field,value,count)#)"
                },

                {
                    field: "calcStatus",
                    title: "Состояние",
                    width: "80px",
                    aggregates: ["count"],
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    //groupHeaderTemplate: "Состояние: #=value#: #=count# : (#= Math.round((count/calcAll(data,field,value))*100)#%)"
                    groupHeaderTemplate: "Состояние: #=value#: #=count# : (#= calcAll(data,field,value,count)#)"
                    //groupHeaderTemplate: "Состояние: #=value#: #=count# "
                }

            ]
        });
    };
  function createGridAllOrder(datasourceDB) {

      var names = _.sortBy(_.uniq(_.pluck(dataSourceDB.data(), "customer")), function(n) { return n; });

      function createMultiSelect(element) {
          element.removeAttr("data-bind");

          element.kendoMultiSelect({
              dataSource: _.sortBy(_.uniq(_.pluck(dataSourceDB.data(), "customer")), function(n) { return n; }),
              change: function(e) {
                  var filter = { logic: "or", filters: [] };
                  var values = this.value();
                  $.each(values, function(i, v) {
                      filter.filters.push({field: "customer", operator: "eq", value: v });
                  });

                  dataSourceDB.filter(filter);
              }
          });
      }


        $("#gridView").kendoGrid({
            toolbar: ["excel"],
            excel: {
                allPages: true
            },

            dataSource: dataSourceDB,
            height: '100%',
            groupable: true,
            sortable: true,
            filterable: true,
            //resizable:true,
            pageable: {
                pageSizes: true
            },
            columns: [
                {
                    field: "orderNumber",
                    title: "№ Заказа",
                    width: "70px",
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    groupable: false
                },
                {
                    field: "orderLine",
                    title: "№ Строки",
                    width: "60px",
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    filterable: false,
                    groupable: false
                },
                {
                    field: "manager",
                    title: "Менеджер",
                    width: "80px",
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    aggregates: ["count"],
                    groupHeaderTemplate: "Менеджер: #=value#: #=count# : (#=  Math.round((count/calcAll(data,field,value))*100)#%): Count : #=count#"
                },
                {
                    field: "customer",
                    title: "Клиент",
                    width: "180px",
                    aggregates: ["count"],
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    filterable: {
                        ui: createMultiSelect,
                        extra: false
                    },
                    /*groupHeaderTemplate: "Клиент: #=value# : #=count#: (#= Math.round((count/calcAll(data,field,value))*100)#%)"*/
                    groupHeaderTemplate: "Клиент: #=value# : #=count#: (#= calcAll(data,field,value,count)#)"
                    //groupHeaderTemplate: "Клиент: #=value# : #=count#"
                },
                {
                    field: "itemDescription",
                    title: "Наименование заказа",
                    width: "130px",
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    filterable: false,
                    groupable: false
                },
                {
                    field: "monthShip",
                    title: "Месяц отгрузки",
                    width: "50px",
                    headerAttributes: headerFormat,
                    attributes: columnFormat,
                    groupable: false,
                    aggregates: ["count"],
                    groupHeaderTemplate: "Месяц отгрузки: #=value# : (#= calcAll(data,field,value)#)"
                    //groupHeaderTemplate: "Месяц отгрузки: #=value# : (#= count#)"
                },
                { field: "dateCreateOrderFormat", title: "Дата открытия заказа",width: "80px",
                    headerAttributes: headerFormat,attributes: columnFormat,filterable: false,groupable: false
                },
                { field: "dateBeginProductionFormat", title: "Дата начала производства",width: "80px",
                    headerAttributes: headerFormat,attributes: columnFormat,filterable: false,groupable: false
                },
                { field: "dateBeginProductionFormat", title: "Дата начала производства в план сутки",width: "80px",
                    headerAttributes: headerFormat,attributes: columnFormat,filterable: false,groupable: false
                },
                { field: "dateCreateOrderFormat", title: "Дата сдачи заказа на склад",width: "80px",
                    headerAttributes: headerFormat,attributes: columnFormat,filterable: false,groupable: false
                },
                { field: "dateCreateOrderFormat", title: "Дата поступления на склад",width: "80px",
                    headerAttributes: headerFormat,attributes: columnFormat,filterable: false,groupable: false
                },
                {
                    field: "datePlanShipFormat", title: "Дата отгрузки план", width: "80px",
                    headerAttributes: headerFormat, attributes: columnFormat, filterable: false, groupable: false
                },
                {
                    field: "dateActualShipFormat", title: "Дата отгрузки факт.", width: "80px",
                    headerAttributes: headerFormat, attributes: columnFormat, filterable: false, groupable: false
                },
                {
                    /*field: "datePlanShipFormat",*/ title: "Дата доставки план", width: "80px",
                    headerAttributes: headerFormat, attributes: columnFormat, filterable: false, groupable: false
                },
                {
                    /*field: "dateBeginProductionFormat",*/ title: "Дата доставки актуал.", width: "80px",
                    headerAttributes: headerFormat, attributes: columnFormat, filterable: false, groupable: false
                },
                {
                    /*field: "dateBeginProductionFormat",*/ title: "Дата доставки факт.", width: "80px",
                    headerAttributes: headerFormat, attributes: columnFormat, filterable: false, groupable: false
                }


            ]
        });
    };

    var  createGridPlanPrecision_ = function(datasourceDB){
       createGridPlanPrecision(datasourceDB);
    }
    var  createGridPrecisionProduction_ = function(){
        createGridPrecisionProduction();
    }
    var  createGridAllOrders_ = function(datasourceDB){
        createGridAllOrder(datasourceDB);
    }
    return{
        createGridPlanPrecision: createGridPlanPrecision_,
        createGridPrecisionProduction: createGridPrecisionProduction_,
        createGridAllOrders:createGridAllOrders_
    };
})()