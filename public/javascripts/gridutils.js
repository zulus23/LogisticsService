/**
 * Created by Zhukov on 21.08.2016.
 */


var  gridUtils = (function(){

    var headerFormat = {"class": "table-header-cell",
        style: " overflow: visible; white-space: normal; text-align: center; vertical-align:top; font-size: 10px"};
    var columnFormat  = { "class": "table-cell",         style: "text-align: center; font-size: 11px" };

    var indexGroup = function (dataGroup, findElement){
        for(var i = 0, len = dataGroup.length;  i < len; i++){
            if(dataGroup[i].field === findElement){ return i;}
        }
        return -1;
    }
    var findObject = function (data,currentLevel,value, prevValue){
        for(var i = 0, len = data.length;  i < len; i++){
            if(data[i].level === currentLevel && data[i].value === value && data[i].prevValue === prevValue){
                return true;
            }
        }
        return false;
    }
    var findCountPrevObject = function (data,value,prevValue){
        for(var i = 0, len = data.length;  i < len; i++){
            if(data[i].value === value && data[i].prevValue === prevValue){
                return data[i].prevCount;
            }
        }
        return -1;
    }

    var findPrevObject = function (data,value,prevValue){
        for(var i = 0, len = data.length;  i < len; i++){
            if(data[i].value === value && data[i].prevValue === prevValue){
                return data[i];
            }
        }
        return {};
    }

    var procentPrecisionByCustomer = function (filteredData,field, value, isPrecision) {
        var fieldObjectLocal = {};
        fieldObjectLocal["customer"] = value;
        var countDeviation = _.countBy(_.pluck(_.where(filteredData, fieldObjectLocal), "deviationHide"), function (n) {
            return  n === 0 ? "NoDeviation":"Deviation"
        });
        var deviation_ =  _.isUndefined(countDeviation.Deviation) ? 0:countDeviation.Deviation;
        var nodeviation_ =  _.isUndefined(countDeviation.NoDeviation) ? 0:countDeviation.NoDeviation;
        var countAll =  deviation_ + nodeviation_;

        return isPrecision ? Math.round((nodeviation_ / countAll) * 100) + " %" : Math.round((1 - (nodeviation_ / countAll)) * 100) + " %";
    }


    return{
        /*createGridPlanPrecision: createGridPlanPrecision_,
        createGridPrecisionProduction: createGridPrecisionProduction_,
        createGridAllOrders:createGridAllOrders_,*/
        procentPrecisionByCustomer:procentPrecisionByCustomer,
        indexGroup:indexGroup,
        findObject:findObject,
        findPrevObject:findPrevObject,
        headerFormat:headerFormat,
        columnFormat:columnFormat
    };
})()