var token=getCookie('token');

function FeedbackController($scope,FeedbackService) {
	
  console.log("载入FeedbackController");
  
  $scope.currentPage = 1;
  $scope.feedbackLists={};
  $scope.feedbackTofind={};
  $scope.deleteFeedbackInfo={};
  var pageSize=10;
  var pageIndex=1;
  var feedbackid=null;
  var feedback="";
  $scope.feedbackTitles=["序号","用户名","反馈内容","时间","操作"];
  ///////分页获取用户列表
  $scope.getFeedbackList = function(pageSize,pageIndex,feedback) {
	  FeedbackService.getFeedbackList(pageSize,pageIndex,feedback).then(function (result){
	  	  $scope.feedbackList = result.data;
	      $scope.currentPage = result.currentPage;
	      $scope.totalPage = result.totalPage;
	      $scope.feedbackPage($scope.totalPage,$scope.currentPage);
	  });
  }
  $scope.getFeedbackList(pageSize,pageIndex,feedback);
  ////////分页回调函数
  $scope.feedbackPage = function(iPageCount,iCurrent) {
	  $("#feedbackPageCode").remove();
	  $("#table-buton1").append("<div id=\"feedbackPageCode\"></div>");
	  $("#feedbackPageCode").createPage({

	      pageCount:iPageCount,

	      current:iCurrent,

	      backFn:function(p){
	    	  $scope.getFeedbackList(pageSize,p,$scope.feedbackTofind);
	    	  
	      }

	  });
  }
 /////删除用户
 $scope.deleteFeedback = function(feedbackid){
	 if(confirm("确定删除？")) {  
		 FeedbackService.deleteFeedback(feedbackid,token).then(function(result){
		       $scope.deleteFeedbackInfo=result.data;
		       $scope.getFeedbackList(pageSize,$scope.currentPage,$scope.feedbackTofind);
		       
		    });
     }  
	  	
 };
 //////搜索
 $scope.feedbackFind = function(){
	 $scope.currentPage = 1;
	 $scope.getFeedbackList(pageSize,$scope.currentPage,$scope.feedbackTofind);
 }

}
