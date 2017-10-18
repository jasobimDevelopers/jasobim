
angular.module('ItemApp',[])
.service('itemInfoService', function itemInfoService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
   }
  var baseUrl="http://jasobim.com.cn";
  var self=this;
  this.getItemDetail=function(id){
	  var deferred = $q.defer();
      console.log("读取构建详细数据");
      var api =baseUrl + '/api/item/getItemById?itemId='+id;
     
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.itemInfo = data;
             
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


