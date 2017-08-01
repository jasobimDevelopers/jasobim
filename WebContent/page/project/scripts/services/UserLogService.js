var token=getCookie('token');
angular.module('Demo')
  .service('UserLogService', function UserLogService($http, $q) {
	  
	  var transform = function(data){
          return $.param(data);
      }

    var self=this;
    //獲取用户记录列表
    this.getUserLogList= function(pageSize,pageIndex,UserLog) {

        var deferred = $q.defer();
        console.log("读取UserLogList数据");
        var api = 'api/userLog/admin/getUserLogList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
        if(UserLog.others !== undefined && trimStr(UserLog.others) !== '') {
          	api += "&others="+trimStr(UserLog.others);
          }
          if(UserLog.dates !== undefined && trimStr(UserLog.dates) !== ''){
          	api += "&dates="+trimStr(UserLog.dates);
          }
        $http.get(encodeURI(api))
            .success(function(data, status, headers, config){
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.UserLogList = data;
                }else{
                    alert("数据读取失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
     };
        //////删除用户记录信息
     this.deleteUserLog = function(Id) {
        
        var deferred = $q.defer();
        console.log("删除UserLog数据");
        $http.get('api/userLog/admin/deleteUserLog?userLogId='+Id+'&token='+getCookie('token'))
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.deleteUserLogInfo = data;
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
