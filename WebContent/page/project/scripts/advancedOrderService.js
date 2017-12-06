
angular.module('AdvancedOrderApp',[])
.service('advancedOrderService', function advancedOrderService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
   }
  var baseUrl="http://jasobim.com.cn";
  var self=this;
  this.getAdvancedOrderDetail=function(id){
	  var deferred = $q.defer();
      console.log("读取预付单详细数据");
      var api =baseUrl + '/api/advancedOrder/getAdvancedOrderById?id='+id+"&weixin=weixin";
     
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.advancedOrder = data;
             
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


