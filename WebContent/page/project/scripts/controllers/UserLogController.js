var token=getCookie('token');

function UserLogController($scope,UserLogService) {
	
  console.log("载入UserLogController");
  $scope.currentPage = 1;
  $scope.UserLogLists={};
  $scope.UserLogTofind={};
  $scope.deleteUserLogInfo={};
  var pageSize=10;
  var pageIndex=1;
  var UserLogid=null;
  var UserLog="";
  var idList="";
  $scope.userLogTitles=["序号","用户名","项目名称","功能区域","操作时间","系统类型","版本","操作"];
  	//////搜索
  $scope.UserLogFind = function(){
 	 $scope.currentPage = 1;
 	 $scope.getUserLogList(pageSize,$scope.currentPage,$scope.UserLogTofind);
  };
  ///////分页获取用户列表
  $scope.getUserLogList = function(pageSize,pageIndex,UserLog) {
	  UserLogService.getUserLogList(pageSize,pageIndex,UserLog).then(function (result){
	  	  $scope.userLogList = result.data;
	      $scope.currentPage = result.currentPage;
	      $scope.totalPage = result.totalPage;
	      $scope.UserLogPage($scope.totalPage,$scope.currentPage);
	  });
  }
  $scope.getUserLogList(pageSize,pageIndex,UserLog);
  
  ////////分页回调函数
  $scope.UserLogPage = function(iPageCount,iCurrent) {
	  $("#userLogPageCode").remove();
	  $("#table-buton1112").append("<div id=\"userLogPageCode\"></div>");
	  $("#userLogPageCode").createPage({

	      pageCount:iPageCount,

	      current:iCurrent,

	      backFn:function(p){
	    	  $scope.getUserLogList(pageSize,p,$scope.UserLogTofind);
	    	  
	      }

	  });
  }
 /////删除用户
 $scope.deleteUserLog = function(UserLogid){
	 if(confirm("确定删除？")) {  
		 UserLogService.deleteUserLog(UserLogid,token).then(function(result){
		       $scope.deleteUserLogInfo=result.data;
		       $scope.getUserLogList(pageSize,$scope.currentPage,$scope.UserLogTofind);
		       
		    });
     }  
	  	
 };
 
 $scope.set_checkboxall = function(parentid){
	 var PID = document.getElementById(parentid);
     var cb = PID.getElementsByTagName("input");
         
	 var flag=0;
	 var flagnum=cb.length;
	 for(var i=0;i<cb.length;i++){
		 if(cb[i].type == "checkbox"){
			 if(cb[i].checked ==1){
				 flag++;
			 }
		 }
	 }
	 if(flag>=0 && flag<flagnum){
		 for(var i=0;i<cb.length;i++){
			 if(cb[i].type == "checkbox"){
				 cb[i].checked = 1;
			 }
		 }
	 }
	 if(flag==flagnum){
		 for(var i=0;i<cb.length;i++){
			 if(cb[i].type == "checkbox"){
				 cb[i].checked = 0;
			 }
		 }
		 
	 }
	
	 
 };
 $scope.delete_choice=function(parentid){
	 var PID = document.getElementById(parentid);
     var cb = PID.getElementsByTagName("input");
     idList="";
	 for(var i=0;i<cb.length;i++){
		 if(cb[i].checked==1){
			 if(idList==""){
				 idList=$scope.userLogList[i].id;
			 }else{
				 idList=idList+","+$scope.userLogList[i].id; 
			 }
			 
		 }
	  }
	 UserLogService.deleteUserLog(idList,token).then(function(result){
	       $scope.deleteUserLogInfo=result.data;
	       $scope.getUserLogList(pageSize,$scope.currentPage,$scope.UserLogTofind);
	       
	    });
 };
}
