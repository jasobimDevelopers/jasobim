  var token=getCookie('token');
  angular.module('Demo')
.service('ProjectFilesService', function ProjectFilesService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
    }
  var self=this;
  
  
  /////获取所有项目名称
//获取项目列表
  this.getProjectList = function(pageSize,pageIndex,project) {

      var deferred = $q.defer();
      console.log("读取ProjectList数据");
      var api = 'api/project/admin/getProjectList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.projectList = data;
             
              }else{
                  alert("数据读取失败");
              }
              
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      return deferred.promise;
      };
  //获取项目工程文件列表
  this.getProjectFilesList = function(pageSize,pageIndex,projectFiles) {
      var deferred = $q.defer();
      console.log("读取ProjectFilesList数据");
      var api = 'api/projectFiles/admin/getProjectFilesLists?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
      if(projectFiles!=null && projectFiles!=undefined){
    	  api+=projectFiles;
      }
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.projectFilesList = data;
              }else{
                  alert("数据读取失败");
              }
              
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      return deferred.promise;
  };
  
  
      //////删除项目工程资料
   this.deletePf = function(ids) {
      
      var deferred = $q.defer();
      console.log("删除项目工程资料文件数据");
      $http.get('api/projectFiles/admin/deleteProjectFiles?id='+ids+'&token='+token)
          .success(function(data, status, headers, config){
              console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.deleteProjectFilesInfo = data;
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
     
      ///////上传工程资料
      this.addProjectFiles = function(findProjectFilesInfo,token) {
          var deferred = $q.defer();
          console.log("添加ProjectFiles数据");
          $http.post('api/projectFiles/admin/uploadProjectFiles?token='+token,findProjectFilesInfo,
          		{
        	  		headers: {'Content-Type':undefined},
        	  		transformRequest: angular.identity 

          		})
              .success(function(data, status, headers, config){

                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      self.findProjectFilesInfo = data;
                      alert("添加成功！");
                  }else{
                      alert("数据添加失败("+data.errorCode+")");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          	  return deferred.promise;
          };     
     
		  
  });
