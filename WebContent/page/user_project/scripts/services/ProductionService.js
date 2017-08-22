  var token=getCookie('token');
  angular.module('Demo')
.service('ProductionService', function ProductionService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
  }
  var self=this;
  this.getProjectNameList()
  {
	  var deferred = $q.defer();
      console.log("读取ProjectList数据");
      $http.post('api/project/admin/getProjectList?token='+getCookie('token'))
          .success(function(data, status, headers, config){
              console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.ProjectList = data;
             
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
