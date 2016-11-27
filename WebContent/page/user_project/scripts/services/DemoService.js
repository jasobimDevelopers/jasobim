angular.module('Demo')
  .service('DataService', function DemoService($http, $q) {
    this.dataList = function(tender,pieces,unite) {
        var deferred = $q.defer();
        console.log("读取data数据");
        $http.get('api/user/getUserList?token = '+tender+'&pieces='+pieces+'&unite='+unite)
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.msg == "成功"){
                    deferred.resolve(data.data);
                }else{
                    alert("数据读取失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
        };
});