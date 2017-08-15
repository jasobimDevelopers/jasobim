var token=getCookie('token');
angular.module('Demo')
  .service('UserLogService', function UserLogService($http, $q) {
	  
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
    //獲取用户记录列表
    this.getUserLogList= function(pageSize,pageIndex,UserLog,startTime,finishedTime,searchContent) {

        var deferred = $q.defer();
        console.log("读取UserLogList数据");
        var api = 'api/userLog/admin/getUserLogList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex+"&searchContent="+searchContent;
        if(UserLog!==null && UserLog!==undefined && UserLog!==""){
        	api += UserLog;
        }
        if(startTime !== undefined && trimStr(startTime)!== '')
        {
        	api += "&dateStart=" + trimStr(startTime);
        }
        if(finishedTime !== undefined && trimStr(finishedTime)!== '')
        {
        	api += "&dateFinished=" + trimStr(finishedTime);
        }
        $http.get(encodeURI(api))
            .success(function(data, status, headers, config){
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.UserLogList = data;
                }else{
                    alert("数据读取失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
     };
        //////删除用户记录信息
     this.deleteUserLog = function(Id) {
        
        var deferred = $q.defer();
        console.log("删除UserLog数据");
        $http.get('api/userLog/admin/deleteUserLog?userLogId='+Id+'&token='+getCookie('token'))
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.deleteUserLogInfo = data;
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
     //////导出用户足迹表格
	  this.getUserLogExcel =function(startTime,finishedTime,token){
		  var deferred = $q.defer();
		  var api='api/userLog/admin/exportUserLog?&token='+token;
		  if(startTime!=null && startTime!=undefined && startTime!=""){
			  api+='&dateStart='+ startTime;
		  }
		  if(finishedTime!=null && finishedTime!=undefined && finishedTime!=""){
			  api+='&dateFinished='+ finishedTime;
		  }
	 	  $http.get(api)
         .success(function(data, status, headers, config){
             console.log(data);
                 if(data.callStatus == "SUCCEED"){
                     deferred.resolve(data);
                     alert("导出用户足迹信息成功！")
                     self.uploadUserLogInfo = data;
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
