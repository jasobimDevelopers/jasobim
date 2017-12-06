  var token=getCookie('token');
  angular.module('Demo')
.service('NormativefilesService', function NormativefilesService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
    }
  var self=this;
  /////获取规范信息列表
  this.getNormativeFilesList = function(pageSize,pageIndex,normative,content) {

      var deferred = $q.defer();
      console.log("读取normativeFilesList数据");
      if(content!=undefined && content!=""){
    	  var api = 'api/normativefiles/getNormativefilesLists?token='+getCookie('token')+"&pageSize="+pageSize+"&pageIndex="+pageIndex+"&content="+content+"&"+normative;
      }else{
    	  var api = 'api/normativefiles/getNormativefilesLists?token='+getCookie('token')+"&pageSize="+pageSize+"&pageIndex="+pageIndex+"&"+normative;
      }
      
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.normativeList = data;
             
              }else{
                  alert("数据读取失败");
              }
              
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      return deferred.promise;
  };
  //////删除规范文件
  this.deleteNormativeFile = function(id,fileId){
      
      var deferred = $q.defer();
      console.log("删除规范数据");
      $http.get('api/normativefiles/deleteNormativefiles?id='+id+'&token='+token+'&fileId='+fileId)
          .success(function(data, status, headers, config){
              console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.deleteNormativeFilesInfo = data;
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
