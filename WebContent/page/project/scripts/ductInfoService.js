
angular.module('DuctApp',[])
.service('ductInfoService', function ductInfoService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
   }
  var self=this;
  this.getDuctDetail=function(id){
	  var deferred = $q.defer();
      console.log("读取构建详细数据");
      var api = '/jasobim/api/duct/admin/getDuctBySelfId?id='+id;
     
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.ductInfo = data;
             
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


