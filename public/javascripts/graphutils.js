/**
 * Created by Zhukov on 20.08.2016.
 */
'use strict';
var  graphUtils = (function(){

  /*  var test = [{"deviation":"1","dateActualShip":new Date("2016/01/01"),"procent":73},
                {"deviation":"2","dateActualShip":new Date("2016/01/01"),"procent":9},
                {"deviation":"3","dateActualShip":new Date("2016/01/01"),"procent":18},
                {"deviation":"1","dateActualShip":new Date("2016/03/01"),"procent":12},
                {"deviation":"2","dateActualShip":new Date("2016/03/01"),"procent":80},
                {"deviation":"3","dateActualShip":new Date("2016/03/01"),"procent":5},
                {"deviation":"4","dateActualShip":new Date("2016/07/01"),"procent":7},
                {"deviation":"5","dateActualShip":new Date("2016/03/01"),"procent":2},
                {"deviation":"3","dateActualShip":new Date("2016/05/01"),"procent":50},
                {"deviation":"3","dateActualShip":new Date("2016/05/01"),"procent":3},
                {"deviation":"4","dateActualShip":new Date("2016/05/01"),"procent":1},
                {"deviation":"3","dateActualShip":new Date("2016/05/01"),"procent":46},
                {"deviation":"5","dateActualShip":new Date("2016/04/01"),"procent":6},
                {"deviation":"2","dateActualShip":new Date("2016/04/01"),"procent":80},
                {"deviation":"1","dateActualShip":new Date("2016/04/01"),"procent":3},
                {"deviation":"3","dateActualShip":new Date("2016/04/01"),"procent":11},
                {"deviation":"6","dateActualShip":new Date("2016/06/01"),"procent":43},
                {"deviation":"1","dateActualShip":new Date("2016/06/01"),"procent":10},
                {"deviation":"2","dateActualShip":new Date("2016/06/01"),"procent":14},
                {"deviation":"4","dateActualShip":new Date("2016/06/01"),"procent":32},
                {"deviation":"7","dateActualShip":new Date("2016/07/01"),"procent":17},
                {"deviation":"2","dateActualShip":new Date("2016/07/01"),"procent":68},
                {"deviation":"1","dateActualShip":new Date("2016/07/01"),"procent":2},
                {"deviation":"4","dateActualShip":new Date("2016/07/01"),"procent":12},
                {"deviation":"2","dateActualShip":new Date("2016-08-01"),"procent":7},
                {"deviation":"6","dateActualShip":new Date("2016/08/01"),"procent":93}
                ];
*/
    var NODEVIATION = 'Без отклонений';
    var DEVIATION = 'С отклонением';
    var localDataForDeviation = function (dataSource){
        var data = [];
        var query = new kendo.data.Query(dataSource.data());
        var filters = dataSource.filter();
        var filteredData = query.filter(filters).data;
        var uniqDate =  _.uniq(filteredData,false,function(p){ return p.monthShip} ).map(function(e){return e.monthShip});
        _.each(uniqDate,function (e) {
            var all = _.where(filteredData,{monthShip:e}).length;
            var countPre = _.where(filteredData,{monthShip:e,calcStatus:NODEVIATION}).length;
            var _procent = Math.round((countPre/all)*100);
            var myDate = moment(e, "MMM-YYYY","ru");
            data.push({deviation:NODEVIATION,dateActualShip:myDate.toDate(),procent:_procent });
            // data.push({deviation:Deviation,monthActualShip:myDate,procent:100-_procent });
        });
        return data;
    };
    var localDataForCustomer = function(dataSource){
        var data = [];
        var query = new kendo.data.Query(dataSource.data());
        var filters = dataSource.filter();
        var filteredData = query.filter(filters).data;
        var uniqDate =  _.uniq(filteredData,false,function(p){ return p.monthShip} ).map(function(e){return e.monthShip});
        _.each(uniqDate,function (e) {
            var uniqDateCustomer =  _.uniq(_.where(filteredData,{monthShip:e}),false,function(p){ return p.customer} ).map(function(e){return e.customer});
               _.each(uniqDateCustomer,function(p){
                   var all = _.where(filteredData,{monthShip:e,customer:p}).length;
                   var countPre = _.where(filteredData,{monthShip:e,customer:p,calcStatus:NODEVIATION}).length;
                   var _procent = Math.round((countPre/all)*100);
                   var myDate = moment(e, "MMM-YYYY","ru");
                   data.push({deviation:p,dateActualShip:myDate.toDate(),procent:_procent });
               });


            // data.push({deviation:Deviation,monthActualShip:myDate,procent:100-_procent });
        });
        return data;
    };
    var localDataForReasonDeviation = function(dataSource){

        /*----------------*/
        var data = [];
        var query = new kendo.data.Query(dataSource.data());
        var filters = dataSource.filter();
        var filteredData = query.filter(filters).data;
        var uniqDate =  _.uniq(filteredData,false,function(p){ return p.monthShip} ).map(function(e){
            var p ={monthShip:e.monthShip,nornalizeGroupDate:e.nornalizeGroupDate};
            return p;
        });
        _.each(uniqDate,function (e) {
            var uniqDateCustomer =  _.uniq(_.where(filteredData,{monthShip:e.monthShip}),false,function(p){ return p.reasonDeviation} ).map(function(e){return e.reasonDeviation});
            _.each(uniqDateCustomer,function(p){
                var all = _.where(filteredData,{monthShip:e.monthShip}).length;
                var countPre = _.where(filteredData,{monthShip:e.monthShip,reasonDeviation:p}).length;
                var _procent = Math.round((countPre/all)*100);
                var myDate = moment(e, "MMM-YYYY");

                 data.push({deviation:"\""+p+"\"",dateActualShip:e.nornalizeGroupDate/*myDate.toDate()*/,procent:_procent });
            });


            // data.push({deviation:Deviation,monthActualShip:myDate,procent:100-_procent });
        });
        return data;
    }

    function graphForAll(_datasource) {
        $("#chartView").kendoChart({
            dataSource: _datasource,
            legend: {
                visible: true,
                position: "top"
            },
            series: [/*{
             type: "column",
             field: "procent",

             },*/{
                type: "column",
                field: "procent",
                tooltip: {
                    visible: true,
                    template: "#= kendo.format('{0}%', value) #"
                },
                labels: {
                    visible: true,
                    background: "transparent"
                }
            }, {
                type: "line",
                field: "procent",
                tooltip: {
                    visible: true,
                    template: "#= kendo.format('{0}%', value) #"
                },
                visibleInLegend: false

            }],

            valueAxis: {

                labels: {

                    min: 0,
                    max: 100,
                    format: "{0}%"
                }
            },
            categoryAxis: {

                baseUnit: "months",
                field: "dateActualShip",
                labels: {

                    template: "#= kendo.format('{0:MMMM-yyyy}', new Date(value)) #"

                }
            }/*,
             tooltip: {
             visible: true,
             template: "#:value#%"
             }*/
        });
    }

    /* =========================================================*/
    function graphForAll_(_datasource) {
        $("#chartView").kendoChart({
            dataSource: _datasource,
            legend: {
                visible: true,
                position: "top"
            },
            series: [/*{
             type: "column",
             field: "procent",

             },*/{
                type: "column",
                field: "procent",
                tooltip: {
                    visible: true,
                    template: "#= kendo.format('{0}%', value) #"
                },
                labels: {
                    visible: true,
                    background: "transparent"
                },
                categoryField: "dateActualShip"
            }/*, {
                type: "line",
                field: "procent",
                tooltip: {
                    visible: true,
                    template: "#= kendo.format('{0}%', value) #"
                },
                visibleInLegend: false,
                categoryField: "dateActualShip"

            }*/
            ],

            valueAxis: {

                labels: {

                    min: 0,
                    max: 100,
                    format: "{0}%"
                }
            },
            categoryAxis: {

                baseUnit: "months",

                labels: {

                    template: "#= kendo.format('{0:MMMM-yyyy}', new Date(value)) #"

                }
            }/*,
             tooltip: {
             visible: true,
             template: "#:value#%"
             }*/
        });
    }
    /* ---------------------------------------------------------*/
    function graphForReasonDeviation(_datasource) {




        $("#chartView").kendoChart({
            dataSource:_datasource,
            legend: {
                visible: true,
                position: "top"
            },
           /* seriesDefaults: {
                type: "column",
                labels: {
                    visible: true,
                    background: "transparent"
                }
            },*/
            series: [/*{
             type: "column",
             field: "procent",

             },*/{
                type: "line",
                field: "procent",
                tooltip: {
                    visible: true,
                    template: "#= kendo.format('{0}%', value) #"
                },
                labels: {
                    visible: true,
                    background: "transparent"
                },
                categoryField: "dateActualShip"


            }],

            valueAxis: {

                labels: {

                    min: 0,
                    max: 100,
                    format: "{0}%"
                }
            },
            categoryAxis: {

                baseUnit: "months",
                 labels: {
                    template: "#= kendo.format('{0:MMMM-yyyy}', new Date(value)) #"
                }

            }
        });
    }

    var createBarGraph =  function (groupField,datasource){

        var _datasource =  createLocalDatasourceForgraph(groupField,datasource);
        /*datasource_.filter(dataSourceDB.filter());
         var visibleLegend = !!dataSourceDB.filter()? (dataSourceDB.filter().filters.length > 0 ? true : false): false ;*/
        if(groupField === 'reasonDeviation' ){
            graphForReasonDeviation(_datasource);
        } else {
         graphForAll_(_datasource);
        }
    };
    function createLocalDatasourceForgraph(groupField,datasource){

        if(groupField === 'customer') {var mydata =  graphUtils.localDataForCustomer(datasource);};
        if(groupField === 'calcStatus') {var mydata =  graphUtils.localDataForDeviation(datasource);};
        if(groupField === 'reasonDeviation') {var mydata =  graphUtils.localDataForReasonDeviation(datasource);};
        if(groupField === '') {var mydata =  graphUtils.localDataForDeviation(datasource);};
         var _datasource =
            new kendo.data.DataSource({
                data:mydata,
                group: {
                    field: "deviation"
                },
                sort: [
                    { field: "dateActualShip", dir: "asc" }
                ],
                schema: {
                    model: {
                        fields: {
                            deviation:{type: "string"},
                            dateActualShip: {
                                type: "date"
                            },
                            procent:{type: "number"}
                        }
                    }
                }
            });
        return _datasource;
    }

    return {
        localDataForDeviation:localDataForDeviation,
        localDataForCustomer:localDataForCustomer,
        localDataForReasonDeviation:localDataForReasonDeviation,
        createBarGraph:createBarGraph,
        WITHDEVIATION:DEVIATION,
        WITHOUTDEVIATION:NODEVIATION
    }

})();
