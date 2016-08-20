/**
 * Created by Zhukov on 20.08.2016.
 */
'use strict';
var  graphUtils = (function(){
    var NoDeviation = 'Без отклонений';
    var Deviation = 'C отклонениями';
    var localDataForDeviation = function (dataSource){
        var data = [];
        var query = new kendo.data.Query(dataSource.data());
        var filters = dataSource.filter();
        var filteredData = query.filter(filters).data;
        var uniqDate =  _.uniq(filteredData,false,function(p){ return p.monthShip} ).map(function(e){return e.monthShip});
        _.each(uniqDate,function (e) {
            var all = _.where(filteredData,{monthShip:e}).length;
            var countPre = _.where(filteredData,{monthShip:e,caclStatus:NoDeviation}).length;
            var _procent = Math.round((countPre/all)*100);
            var myDate = moment(e, "MMM-YYYY","ru");
            data.push({deviation:NoDeviation,monthActualShip:myDate,procent:_procent });
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
                   var countPre = _.where(filteredData,{monthShip:e,customer:p,caclStatus:NoDeviation}).length;
                   var _procent = Math.round((countPre/all)*100);
                   var myDate = moment(e, "MMM-YYYY","ru");
                   data.push({deviation:p,monthActualShip:myDate,procent:_procent });
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
        var uniqDate =  _.uniq(filteredData,false,function(p){ return p.monthShip} ).map(function(e){return e.monthShip});
        _.each(uniqDate,function (e) {
            var uniqDateCustomer =  _.uniq(_.where(filteredData,{monthShip:e}),false,function(p){ return p.reasonDeviation} ).map(function(e){return e.reasonDeviation});
            _.each(uniqDateCustomer,function(p){
                var all = _.where(filteredData,{monthShip:e}).length;
                var countPre = _.where(filteredData,{monthShip:e,reasonDeviation:p}).length;
                var _procent = Math.round((countPre/all)*100);
                var myDate = moment(e, "MMM-YYYY","ru");
                data.push({deviation:p,monthActualShip:myDate,procent:_procent });
            });


            // data.push({deviation:Deviation,monthActualShip:myDate,procent:100-_procent });
        });
        return data;
    }

    return {
        localDataForDeviation:localDataForDeviation,
        localDataForCustomer:localDataForCustomer,
        localDataForReasonDeviation:localDataForReasonDeviation
    }

})();
