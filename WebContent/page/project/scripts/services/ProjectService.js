  var token=getCookie('token');
  angular.module('Demo')
.service('ProjectService', function ProjectService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
    }
  var self=this;

  /////注册用户
  this.register = function(Project) {

      var deferred = $q.defer();
      console.log("读取ProjectList数据");
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
  //獲取用戶列表
  this.getProjectList = function(pageSize,pageIndex,Project) {

      var deferred = $q.defer();
      console.log("读取ProjectList数据");
      var api = 'api/Project/admin/getProjectList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
      if(Project.ProjectName !== undefined && trimStr(Project.ProjectName) != '') {
      	api += "&ProjectName="+trimStr(Project.ProjectName);
      }
      if(Project.realName !== undefined && trimStr(Project.realName) != ''){
      	api += "&realName="+trimStr(Project.realName);
      }
      if(Project.email !==undefined && trimStr(Project.email) != ''){
      	api += "&email="+ trimStr(Project.email);
      }
      if(Project.tel !==undefined && trimStr(Project.tel) != ''){
      	api += "&tel="+ trimStr(Project.tel);
      }
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
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
      //////删除用户信息
   this.deleteProject = function(ProjectId) {
      
      var deferred = $q.defer();
      console.log("删除Project数据");
      $http.get('api/Project/admin/deleteProject?ProjectId='+ProjectId+'&token='+getCookie('token'))
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
      ///////更新用户信息
      this.updateProject = function(Project,token) {
      	var deferred = $q.defer();
      	console.log("更新Project数据");
      	delete Project.registerDate;
//	          	var nProject = {};
//	          	nProject.id = Project.id;
//	          	nProject.realName = Project.realName;
      	$http.post('api/Project/admin/updateProject?token='+token,Project,
      		{
      			headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
      			transformRequest: transform
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
      ///////增加用户信息
      this.addProjectByAdmin = function(findProjectInfo,token) {
          var deferred = $q.defer();
          
          
          console.log("查找Project数据");
          $http.post('api/Project/admin/addProject?token='+token,findProjectInfo,
          		{
          			headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
          			transformRequest: transform
          		})
              .success(function(data, status, headers, config){

                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      alert("添加成功")
                      self.findProjectInfo = data;
                  
                  }else{
                      alert("数据添加失败("+data.errorCode+")");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise;
          };
      ///////查找用户信息
      this.findProject = function(ProjectId,token) {
      
      var deferred = $q.defer();
      console.log("查找Project数据");
      $http.get('api/Project/admin/getProjectDetails?ProjectId='+ProjectId+'&token='+token)
          .success(function(data, status, headers, config){
              console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.findProjectInfo = data;
              
              }else{
                  alert("数据查找失败");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise;
          };
  });
