var token=getCookie('token');
angular.module('Demo')
  .service('FeedbackService', function FeedbackService($http, $q) {
	  
	  var transform = function(data){
          return $.param(data);
      }

    var self=this;
    //獲取用戶列表
    this.getFeedbackList = function(pageSize,pageIndex,feedback) {

        var deferred = $q.defer();
        console.log("读取FeedbackList数据");
        var api = 'api/feedback/admin/getFeedbackList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
        if(feedback.userName !== undefined && trimStr(feedback.userName) !== '') {
          	api += "&userName="+trimStr(feedback.userName);
          }
          if(feedback.content !== undefined && trimStr(feedback.content) !== ''){
          	api += "&content="+trimStr(feedback.content);
          }
          /*if(project.startDate !==undefined && trimStr(project.startDate) !== ''){
         	api += "&startDate="+ trimStr(project.startDate);
          }*/
        $http.get(encodeURI(api))
            .success(function(data, status, headers, config){
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.feedbackList = data;
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
     this.deleteFeedback = function(feedbackId) {
        
        var deferred = $q.defer();
        console.log("删除Feedback数据");
        $http.get('api/feedback/admin/deleteFeedback?feedbackId='+feedbackId+'&token='+getCookie('token'))
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.deleteFeedbackInfo = data;
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
