/**
 * Created by Zhukov on 18.08.2016.
 */
var level1 = [{}]
var prevLevelValue = [];
var prevLevelCount = [];
function calcAll(data,field,value){
    //tree[-1] = {prevLevel : -1,level: -1,value:"",countAll:allDataCount/*dataSourceDB.total()*/};
    prevLevelCount[-1] = allDataCount;/*dataSourceDB.total();*/
    prevLevelValue[-1] = "";
    var levelTree = indexGroup(dataSourceDB.group(),field);
    prevLevelValue[levelTree] = data.value;
    prevLevelCount[levelTree] = data.count;
    if(!findObject(level1,levelTree,data.value,prevLevelValue[levelTree-1])){
        level1.push({prevLevel : levelTree - 1,level: levelTree,value:data.value, prevValue:prevLevelValue[levelTree - 1],
            countAll:data.count, prevCount:prevLevelCount[levelTree - 1]});
    }
    return findCountPrevObject(level1,data.value,prevLevelValue[levelTree-1]);

    var fieldObject = {};
    fieldObject[field]=value;
    var count = 0;
    if ( indexGroup(dataSourceDB.group(),field) === 0 ){
        count = _.groupBy(_.pluck(filteredData,field),function(n){return "count"}).count.length;
    } else {
        count = _.groupBy(_.pluck(_.where(filteredData,fieldObject),field),function(n){return "count"}).count.length;
    }
    //var count = temp.count.length;//_.groupBy(_.pluck(_.where(dataSourceDB.data(),{field:value}),field),function(n){return "count"}).count.length

    return count;
}

function indexGroup(dataGroup, findElement){
    for(var i = 0, len = dataGroup.length;  i < len; i++){
        if(dataGroup[i].field === findElement){ return i;}
    }
    return -1;
}
function findObject(data,currentLevel,value, prevValue){
    for(var i = 0, len = data.length;  i < len; i++){
        if(data[i].level === currentLevel && data[i].value === value && data[i].prevValue === prevValue){
            return true;
        }
    }
    return false;
}
function findCountPrevObject(data,value,prevValue){
    for(var i = 0, len = data.length;  i < len; i++){
        if(data[i].value === value && data[i].prevValue === prevValue){
            return data[i].prevCount;
        }
    }
    return -1;
}