  var token=getCookie('token');
  angular.module('Demo')
.service('CodeInformationService', function CodeInformationService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
    }
  var self=this;
  
/////获取图纸信息列表
  
  this.getCodePapers = function(projectId,pageSize,pageIndex,sql) {

      var deferred = $q.defer();
      console.log("读取ProjectPaperList数据");
      var api = 'api/paper/codeInformation/getPapers?token='+token+ "&pageSize=" + pageSize + "&pageIndex="+pageIndex +"&"+ sql;
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.paperList = data;
             
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
