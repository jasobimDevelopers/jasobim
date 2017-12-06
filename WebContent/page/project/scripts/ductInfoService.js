
angular.module('DuctApp',[])
.service('ductInfoService', function ductInfoService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
   }
  var self=this;
  this.getDuctDetail=function(id,selfId,projectId){
	  var deferred = $q.defer();
      console.log("读取构建详细数据");
      var api = '/api/duct/admin/getDuctBySelfId?';
      if(id!=null && id!=undefined && id!=''){
    	  api=api+'id='+id
      }else{
    	  if(projectId!=null && projectId!=undefined && projectId!=''){
    		  api=api+'selfId='+selfId+'&projectId='+projectId;
    	  }else{
    		  api=api+'selfId='+selfId;
    	  }
    	  
      }
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


