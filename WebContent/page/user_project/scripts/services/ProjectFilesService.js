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
      if(projectFiles.typeName!=null){
    	  api+="&typeName="+projectFiles.typeName;
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
  
  /////增加任务单
  this.register = function(Project) {

      var deferred = $q.defer();
      console.log("读取ConstructionTaskService数据");
      $http.post('api/Project/admin/getProjectList?token='+getCookie('token'))
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
  //获取任务单列表
  this.getConstructionTaskList = function(pageSize,pageIndex,constructionTask) {

      var deferred = $q.defer();
      console.log("读取ConstructionTaskList数据");
      var api = 'api/constructionTask/admin/getConstructionTaskList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
      if(constructionTask!=null){
    	  if(constructionTask.companyName !==undefined && trimStr(constructionTask.companyName) !== ''){
    	      api += "&companyName="+ trimStr(constructionTask.companyName);
    	  }
	      if(constructionTask.nextReceivePeopleId !==undefined && trimStr(constructionTask.nextReceivePeopleId) !== ''){
	    	  api += "&nextReceivePeopleId="+ trimStr(constructionTask.nextReceivePeopleId);
	      }
	      if(constructionTask.othersAttention!=undefined && trimStr(constructionTask.othersAttention) !== ''){
	    	  api += "&othersAttention="+ trimStr(constructionTask.othersAttention);
	      }
	      if(constructionTask.id!=undefined && constructionTask.id !== ''){
	    	  api += "&id="+constructionTask.id;
	      }
      }
      
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.constructionTaskList = data;
             
              }else{
                  alert("数据读取失败");
              }
              
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      return deferred.promise;
      };
      //////删除项目信息
   this.deleteConstructionTask = function(ids) {
      
      var deferred = $q.defer();
      console.log("删除任务单数据数据");
      $http.get('api/constructionTask/deleteConstructionTask?id='+ids+'&token='+token)
          .success(function(data, status, headers, config){
              console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.deleteConstructionTaskInfo = data;
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
     
      ///////增加施工任务单信息
      this.addProjectFiles = function(findConstructionTaskInfo,token) {
          var deferred = $q.defer();
          console.log("添加ProjectFiles数据");
          $http.post('api/projectFiles//admin/uploadProjectFiles?token='+token,findConstructionTaskInfo,
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
