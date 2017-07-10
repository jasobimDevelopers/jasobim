  var token=getCookie('token');
  angular.module('Demo')
.service('ItemGetService', function ItemGetService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
    }
  var self=this;
  this.getItemGetList = function(pageSize,pageIndex,itemGet,content,startTime,finishedTime){
	  
	  var deferred = $q.defer();
	  console.log("读取itemGetList数据");
	  
	  if(content!=undefined && content!=""){
		  var api = 'api/duct/getDuctList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex+"&content="+content+"&"+itemGet;
      }else{
    	  var api = 'api/duct/getDuctList?token='+getCookie('token')+"&pageSize="+pageSize+"&pageIndex="+pageIndex+"&"+itemGet;
      }
	  if(startTime!=null && startTime!=undefined && startTime!=""){
		  api+='&dateStart='+ startTime;
	  }
	  if(finishedTime!=null && finishedTime!=undefined && finishedTime!=""){
		  api+='&dateFinished='+ finishedTime;
	  }
	  $http.get(encodeURI(api))
      .success(function(data, status, headers, config){
          if(data.callStatus == "SUCCEED"){
              deferred.resolve(data);
              self.itemGetList = data;
         
          }else{
              alert("数据读取失败");
          }
      })
      .error(function(data, status, headers, config){
          deferred.reject(data);
      });
	  	return deferred.promise;
	  };
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
      //////导出预制化表格
	  this.getDuctExcel =function(projectId,startTime,finishedTime){
		  var deferred = $q.defer();
		  var api='api/duct/admin/exportDuct?projectId='+projectId+'&token='+token;
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
                      alert("导出预制化构件信息成功！")
                      self.uploadDuctInfo = data;
                  }else{
                     // alert("数据导出失败("+data.errorCode+")");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise;
	  }
  });
