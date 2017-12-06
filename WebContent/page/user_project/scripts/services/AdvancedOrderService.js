  var token=getCookie('token');
  angular.module('Demo')
.service('AdvancedOrderService', function AdvancedOrderService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
    }
  var self=this;
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
  this.getAdvancedOrderList = function(pageSize,pageIndex,advancedOrder){
	  var deferred = $q.defer();
      console.log("读取AdvancedOrderList数据");
      var api = 'api/advancedOrder/admin/getAdvancedOrderList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
      
     /* if(constructionTask.phase  && ){
     	api += "&phase="+ trimStr(constructionTask.phase);
      }*/
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.advancedOrderList = data;
             
              }else{
                  alert("数据读取失败");
              }
              
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      return deferred.promise;
  };
  ///////增加施工任务单信息
  this.addAdvancedOrder = function(addAdvancedOrder,token) {
      var deferred = $q.defer();
      console.log("添加addAdvancedOrder数据");
      $http.post('api/advancedOrder/addAdvancedOrder?token='+token,addAdvancedOrder,
      		{
    	  		headers: {'Content-Type':undefined},
    	  		transformRequest: angular.identity 

      		})
          .success(function(data, status, headers, config){

              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.addAdvancedOrder = data;
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
  this.getAdvancedOrderById = function(pageSize,pageIndex,advancedOrder){
	  var deferred = $q.defer();
      console.log("读取AdvancedOrderList数据");
      var api = 'api/advancedOrder/admin/getAdvancedOrderList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
      if(advancedOrder !==undefined && advancedOrder !== '' && advancedOrder !== 'null' && advancedOrder !== null){
    	  if(advancedOrder.id !==undefined && advancedOrder.id !== ''){
           	api += "&id="+ advancedOrder.id;
            }
      }
      
     /* if(constructionTask.phase  && ){
     	api += "&phase="+ trimStr(constructionTask.phase);
      }*/
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.advancedOrderInfo = data;
             
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
  //////删除预付单信息
   this.deleteAdvancedOrder = function(ids) {
      var deferred = $q.defer();
      console.log("删除预付单数据");
      $http.get('api/advancedOrder/deleteAdvancedOrder?id='+ids+'&token='+token)
          .success(function(data, status, headers, config){
              console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.deleteAdvancedOrderInfo = data;
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
   ///////导出预付单
      this.exportAdvancedOrder= function(advancedOrderId){
	       var deferred = $q.defer();
	  	 $http.get('api/advancedOrder/exportAdvancedOrder?id='+advancedOrderId+'&token='+token)
	       .success(function(data, status, headers, config){
	           console.log(data);
	               if(data.callStatus == "SUCCEED"){
	                   deferred.resolve(data);
	                   alert("导出预付单信息成功！")
	                   self.advancedOrderUrlInfo = data;
	               
	               }else{
	                  alert("数据导出失败("+data.errorCode+")");
	               }
	               
	           })
	           .error(function(data, status, headers, config){
	               deferred.reject(data);
	           });
	       return deferred.promise;
	    };
	  

  });
