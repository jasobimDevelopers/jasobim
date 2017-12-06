  var token=getCookie('token');
  angular.module('Demo')
.service('MeasuredDataService', function MeasuredDataService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
    }
  var self=this;
//获取任务单列表
  this.getMeasuredDataList = function(pageSize,pageIndex,measuredData) {

      var deferred = $q.defer();
      console.log("读取MeasuredDataList数据");
      var api = 'api/measuredData/getMeasuredDataList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
      /*if(constructionTask!=null){
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
      }*/
      
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.measuredDataList = data;
             
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
      this.getMeasuredDatasList = function(measuredDataId,pageSize,pageIndex,measuredData) {

          var deferred = $q.defer();
          console.log("读取MeasuredDatasList数据");
          var api = 'api/measuredDatas/getMeasuredDatasList?token='+getCookie('token') + "&pageSize=" 
          + pageSize + "&pageIndex="+ pageIndex + "&measuredDataId=" +measuredDataId;
          $http.get(encodeURI(api))
              .success(function(data, status, headers, config){
                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      self.measuredDatasList = data;
                 
                  }else{
                      alert("数据读取失败");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise;
      };
      /////////删除实测实量
      
      this.deleteMeasuredDataById = function(measuredDataId){
    	  var deferred = $q.defer();
    	  console.log("删除measuredData数据");
    	  $http.get('api/measuredData/deleteMeasuredData?&token='+token+'&id='+measuredDataId)
          .success(function(data, status, headers, config){
              console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.deleteMeasuredDataInfo = data;
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
