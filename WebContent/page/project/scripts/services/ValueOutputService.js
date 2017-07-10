var token=getCookie('token');
angular.module('Demo')
  .service('ValueOutputService', function ValueOutputService($http, $q) {
	  
	  var transform = function(data){
          return $.param(data);
      }

    var self=this;
    //獲取产值列表
    this.getvalueOutputList= function(pageSize,pageIndex,valueOutput) {

        var deferred = $q.defer();
        console.log("读取ValueOutputList数据");
        var api = 'api/ValueOutput/admin/getValueOutputLists?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
        if(valueOutput.others !== undefined && trimStr(valueOutput.others) !== '') {
          	api += "&others="+trimStr(valueOutput.others);
          }
          if(valueOutput.dates !== undefined && trimStr(valueOutput.dates) !== ''){
          	api += "&dates="+trimStr(valueOutput.dates);
          }
        $http.get(encodeURI(api))
            .success(function(data, status, headers, config){
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.valueOutputList = data;
                }else{
                    alert("数据读取失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
     };
        //////删除用户信息
     this.deleteValueOutput = function(Id) {
        
        var deferred = $q.defer();
        console.log("删除ValueOutput数据");
        $http.get('api/ValueOutput/deleteValueOutput?idList='+Id+'&token='+getCookie('token'))
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.deleteValueOutputInfo = data;
                    alert("数据删除成功");
                }else{
                    alert("数据删除失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
     };
});
