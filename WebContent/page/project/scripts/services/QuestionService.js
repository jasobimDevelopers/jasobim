  var token=getCookie('token');
  angular.module('Demo')
.service('QuestionService', function QuestionService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
    }
  var self=this;
//获取项目列表
  this.getProjectLists = function(pageSize,pageIndex,project) {

      var deferred = $q.defer();
      console.log("读取ProjectList数据");
      var api = 'api/project/admin/getProjectList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
      if(project.name !== undefined && trimStr(project.name) !== '') {
      	api += "&name="+trimStr(project.name);
      }
      if(project.num !== undefined && trimStr(project.num) !== ''){
      	api += "&num="+trimStr(project.num);
      }
      if(project.constructionUnit !==undefined && trimStr(project.constructionUnit) !== ''){
      	api += "&constructionUnit="+ trimStr(project.constructionUnit);
      }
      if(project.buildingUnit !==undefined && trimStr(project.buildingUnit) !== ''){
      	api += "&buildingUnit="+ trimStr(project.buildingUnit);
      }
      if(project.leader !== undefined && trimStr(project.leader) !== '') {
        	api += "&leader="+trimStr(project.leader);
        }
     if(project.place !== undefined && trimStr(project.place) !== ''){
    	api += "&place="+trimStr(project.place);
     }
     if(project.description !==undefined && trimStr(project.description) !== ''){
    	api += "&description="+ trimStr(project.description);
     }
     if(project.designUnit !==undefined && trimStr(project.designUnit) !== ''){
    	api += "&designUnit="+ trimStr(project.designUnit);
     }
     if(project.version !== undefined && trimStr(project.version) !== ''){
     	api += "&version="+trimStr(project.version);
      }
      if(project.startDate !==undefined && trimStr(project.startDate) !== ''){
     	api += "&startDate="+ trimStr(project.startDate);
      }
      if(project.phase !==undefined && trimStr(project.phase) !== ''){
     	api += "&phase="+ trimStr(project.phase);
      }
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.projectLists = data;
             
              }else{
                  alert("数据读取失败");
              }
              
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      return deferred.promise;
      };
  /////增加问题
  this.addQuestionByAdmin = function(formData) {

      var deferred = $q.defer();
      console.log("问题增加");
      $http.post('api/question/addQuestion?token='+getCookie('token'),formData,
    		  {
			  		headers: {'Content-Type':undefined},
			  		transformRequest: angular.identity 
    		  })
          .success(function(data, status, headers, config){
              console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.questionInfo = data;
             
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
   this.deleteProject = function(projectId,token) {
      
      var deferred = $q.defer();
      console.log("删除Project数据");
      $http.get('api/project/admin/deleteProject?projectId='+projectId+'&token='+token)
          .success(function(data, status, headers, config){
              console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.deleteProjectInfo = data;
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
      ///////更新项目信息
      this.updateProject = function(project,token) {
      	var deferred = $q.defer();
      	console.log("更新Project数据");
//	          	var nProject = {};
//	          	nProject.id = Project.id;
//	          	nProject.realName = Project.realName;
      	$http.post('api/project/admin/updateProject?token='+token,project,
      		{
	      		headers: {'Content-Type':undefined},
	            transformRequest: angular.identity 
      		})
          .success(function(data, status, headers, config){
          	console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.updateProjectInfo = data;
                  alert("更新成功")
              }else{
                  alert("数据更新失败("+data.errorCode+")");
              }
              
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      	  return deferred.promise;
      };
     
      ///////查找项目信息
      this.findProject = function(projectId,token) {
      
      var deferred = $q.defer();
      console.log("查找Project数据");
      $http.get('api/project/admin/getProjectDetails?projectId='+projectId+'&token='+token)
          .success(function(data, status, headers, config){
              console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.findProjectInfo = data;
              
              }else{
                  alert("数据查找失败");
              }
              }).error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise;
          };
          //////////////////////////////
          ///////查找项目里的楼栋信息
          this.getBuildingList = function(projectId) {
          
          var deferred = $q.defer();
          console.log("查找Project数据");
          $http.get('api/building/admin/getBuildingByProjectId?projectId='+projectId+"&token="+token)
              .success(function(data, status, headers, config){
                  console.log(data);
                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      self.buildingInfo = data;
                  
                  }else{
                      alert("数据查找失败");
                      }
                      
                  })
                  .error(function(data, status, headers, config){
                      deferred.reject(data);
                  });
              return deferred.promise;
              };
              //////////////////////////////
             
  });
