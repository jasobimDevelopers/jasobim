
angular.module('QuestionInfoApp',[])
.service('questionInfoService', function questionInfoService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
   }
  var baseUrl="http://jasobim.com.cn/";
  var self=this;
  this.getQuestionInfoDetail=function(id){
	  var deferred = $q.defer();
      console.log("读取任务单详细数据");
      var api =baseUrl + '/api/question/getQuestionDetails?questionId='+id+"&weixin=weixin";
     
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.questionInfo = data;
              }else{
                 // alert("数据读取失败");
              }
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      return deferred.promise;
  };
  this.getMessageByquestionId=function(id){
	  var deferred = $q.defer();
      console.log("读取任务单详细数据");
      var api =baseUrl + '/api/message/getMessageListByQuestionId?questionId='+id+"&weixin=weixin";
     
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.questionInfo = data;
              }else{
                 // alert("数据读取失败");
              }
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      return deferred.promise;
  };
 
    
});


