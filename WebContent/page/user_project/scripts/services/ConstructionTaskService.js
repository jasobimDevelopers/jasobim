  var token=getCookie('token');
  angular.module('Demo')
.service('ConstructionTaskService', function ConstructionTaskService($http, $q) {
  
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
  //////获取用户姓名列表
  this.getUserList = function(pageSize,pageIndex,user){
	  var deferred = $q.defer();
	  console.log("读取用户姓名列表");
	  var api = 'api/user/admin/getUserList?token='+getCookie('token') + '&pageSize=' + pageSize + '&pageIndex='+pageIndex;
	  $http.get(encodeURI(api))
	  		.success(function(data, status, headers, config){
	  			if(data.callStatus == "SUCCEED"){
	  				deferred.resolve(data);
	  				self.userList = data;
	  			}else{
	  				alert("数据获取失败");	
	  			}
	  		})
	  		.error(function(data, status, headers, config){
	  			deferred.reject(data);
	  		});
	  return deferred.promise;
  }
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
      ///////增加施工任务单信息
      this.addConstructionTask = function(findConstructionTaskInfo,token) {
          var deferred = $q.defer();
          console.log("添加ConstructionTask数据");
          $http.post('api/constructionTask/admin/addConstructionTask?token='+token,findConstructionTaskInfo,
          		{
        	  		headers: {'Content-Type':undefined},
        	  		transformRequest: angular.identity 

          		})
              .success(function(data, status, headers, config){

                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      self.findConstructionTaskInfo = data;
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
     ///////导出施工任务单
	   this.exportConstructionTask= function(constructionTaskId){
	       var deferred = $q.defer();
	  	 $http.get('api/constructionTask/exportConstructionTaskById?id='+constructionTaskId+'&token='+token)
	       .success(function(data, status, headers, config){
	           console.log(data);
	               if(data.callStatus == "SUCCEED"){
	                   deferred.resolve(data);
	                   alert("成功导出施工任务单！")
	                   self.constructionTaskUrlInfo = data;
	               
	               }else{
	                  // alert("数据导出失败("+data.errorCode+")");
	               }
	               
	           })
	           .error(function(data, status, headers, config){
	               deferred.reject(data);
	           });
	       return deferred.promise;
	    };
		  
  });
