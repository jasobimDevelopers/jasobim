  var token=getCookie('token');
  angular.module('Demo')
.service('SuggestionService', function SuggestionService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
    }
  var self=this;
  this.addFeedBack = function(content){
	  
	  var deferred = $q.defer();
      
      
      console.log("添加feedBack数据");
      $http.post('api/feedback/addFeedback?token='+token+'&content='+content,
        		{
  	      		headers: {'Content-Type':undefined},
  	            transformRequest: angular.identity 
        		})
    
          .success(function(data, status, headers, config){

              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.feedBackInfo = data;
                  alert("提交成功！谢谢您的意见,我们会尽快改进！")
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
