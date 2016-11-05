angular.module('Demo')
  .filter('DataFilter', function () {
    return function(inputArray){
        console.log("载入过滤器dataFilter");
        var array = [];
        var partNum = "0";
        var dataIntern = [];
        var startNum=0;
        var preNum = 0;
        for(var i=0;i<inputArray.length;i++){
            var a = inputArray[i].PartID.split("-");
            if(partNum != a[3]){

                if(partNum != "0"){
                    var stakeFrom = inputArray[startNum].Stake.split("~");
                    var stakeTo = inputArray[i].Stake.split("~");

                    array.push({type:partNum,data:dataIntern,unite:inputArray[startNum].PartID+"~"+inputArray[i-1].PartID,stake:stakeFrom[0]+"~"+stakeTo[1],preaudit:preNum+"",preaudit2:(i-startNum)+"",exaaudit:inputArray[startNum].ErectAuditStatus,tensile:inputArray[startNum].TensileState});
                }
                startNum = i;
                partNum = a[3];
                if(inputArray[i].PreAuditStatus == "已完成"){
                    preNum = 1;
                }else{
                    preNum = 0;
                }
                dataIntern = [inputArray[i]];
            }else{
                if(inputArray[i].PreAuditStatus == "已完成"){
                    preNum++;
                }
                dataIntern.push(inputArray[i]);
            }
        }
        if(inputArray.length!=0){
            var stakeFrom = inputArray[startNum].Stake.split("~");
            var stakeTo = inputArray[inputArray.length-1].Stake.split("~");
            array.push({type:partNum,data:dataIntern,unite:inputArray[startNum].PartID+"~"+inputArray[inputArray.length-1].PartID,stake:stakeFrom[0]+"~"+stakeTo[1],preaudit:preNum+"",preaudit2:(inputArray.length-startNum)+"",exaaudit:inputArray[startNum].ErectAuditStatus,tensile:inputArray[startNum].TensileState});  
        }
        return array;
    };
});