var token=getCookie('token');
var index;
function hideUlstsb(){
	var idom=document.getElementById("name_div");
	if(idom.style.display=="none"){
		document.getElementById("li_show_hide1").style.backgroundImage="url(page/project/images/menuItem2.png)";
		idom.style.display="block";
	}else{
		document.getElementById("li_show_hide1").style.backgroundImage="url(page/project/images/menuItem1.png)";
		idom.style.display = 'none';
		
	}
}
function hideUls(){
	var idom=document.getElementById("type_div_userlog");
	if(idom.style.display=="none"){
		document.getElementById("li_show_hide2").style.backgroundImage="url(page/project/images/menuItem2.png)";
		idom.style.display="block";
	}else{
		document.getElementById("li_show_hide2").style.backgroundImage="url(page/project/images/menuItem1.png)";
		idom.style.display = 'none';
	}
	
}
function hideUl(){
	var idom=document.getElementById("state_div");
	if(idom.style.display=="none"){
		document.getElementById("li_show_hide3").style.backgroundImage="url(page/project/images/menuItem2.png)";
		idom.style.display="block";
	}else{
		idom.style.display = 'none';
		document.getElementById("li_show_hide3").style.backgroundImage="url(page/project/images/menuItem1.png)";
	}
	
}
function setProject(index){
	index.style.color=="red";
	var test=index.value;
}
////////////////////////
function UserLogController($scope,UserLogService) {
	
  console.log("载入UserLogController");
  $scope.currentPage = 1;
  $scope.UserLogLists={};
  $scope.UserLogTofind={};
  $scope.deleteUserLogInfo={};
  var pageSize=10;
  var pageIndex=1;
  var UserLogid=null;
  $scope.projectId=-2;
  $scope.projectPart=-2;
  $scope.systemType=-2;
  var searchContent=null;
  var item_temp1="";
  var item_temp2="";
  var item_temp3="";
  var UserLog="";
  var idList="";
  var project="";
  $scope.startTime="";
  $scope.finishedTime="";
  $scope.projectLists="";
  $scope.projectPartList=["模型区域","图纸区域","登录区域","交底区域","预制化区域","紧急事项区域","通知区域","产值区域","班组信息区域","施工任务单","预付单区域"];
  $scope.phoneSystem=["苹果系统","安卓系统"];
  $scope.userLogTitles=["序号","用户名","项目名称","功能区域","操作时间","系统类型","文件内容","版本","操作"];
  	//////搜索
  $scope.UserLogFind = function(){
 	 $scope.currentPage = 1;
 	 $scope.getUserLogList(pageSize,$scope.currentPage,$scope.UserLogTofind,searchContent);
  };
  ///////分页获取项目列表
  $scope.getProjectLists = function(pageSize,pageIndex,project) {
	  UserLogService.getProjectLists(pageSize,-1,project).then(function (result){
		  	  $scope.projectLists = result.data;
		  });
	};
  $scope.getProjectLists(pageSize,pageIndex,project);
  ///////分页获取用户列表
  $scope.getUserLogList = function(pageSize,pageIndex,UserLog,startTime,finishedTime,searchContent) {
	  if($scope.projectId!=null && $scope.projectId!=undefined && $scope.projectId!=-2){
		  UserLog+= "&projectId=" + $scope.projectId;
	  }
	  if($scope.projectPart!=null && $scope.projectPart!=undefined && $scope.projectPart!=-2){
		  UserLog+= "&projectPart=" + $scope.projectPart;
	  }
	  if($scope.systemType!=null && $scope.systemType!=undefined && $scope.systemType!=-2){
		  UserLog+= "&systemType=" + $scope.systemType;
	  }
	  UserLogService.getUserLogList(pageSize,pageIndex,UserLog,$scope.startTime,$scope.finishedTime,searchContent).then(function (result){
	  	  $scope.userLogList = result.data;
	      $scope.currentPage = result.currentPage;
	      $scope.totalPage = result.totalPage;
	      $scope.UserLogPage($scope.totalPage,$scope.currentPage);
	  });
  }
  $scope.getUserLogList(pageSize,pageIndex,UserLog,$scope.startTime,$scope.finishedTime,searchContent);
  
  ////////分页回调函数
  $scope.UserLogPage = function(iPageCount,iCurrent) {
	  $("#userLogPageCode").remove();
	  $("#table-buton1112").append("<div id=\"userLogPageCode\"></div>");
	  $("#userLogPageCode").createPage({

	      pageCount:iPageCount,

	      current:iCurrent,

	      backFn:function(p){
	    	  $scope.getUserLogList(pageSize,p,UserLog,$scope.startTime,$scope.finishedTime,searchContent);
	      }
	  });
  }
 /////删除用户记录
 $scope.deleteUserLog = function(UserLogid){
	 if(confirm("确定删除？")) {  
		 UserLogService.deleteUserLog(UserLogid,token).then(function(result){
		       $scope.deleteUserLogInfo=result.data;
		       $scope.getUserLogList(pageSize,$scope.currentPage,$scope.UserLogTofind,$scope.startTime,$scope.finishedTime,searchContent);
		    });
     }  
 };
 /////////导出用户足迹表格
 $scope.getUserLogExcel=function(){
		UserLogService.getUserLogExcel($scope.startTime,$scope.finishedTime,token).then(function (result){
		  	  $scope.userLogUrl = result.data;
		});
 }
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
	       $scope.getUserLogList(pageSize,$scope.currentPage,$scope.UserLogTofind,$scope.startTime,$scope.finishedTime,searchContent);
	    });
 };
 $scope.showList = function(){
	$scope.projectId=-2;
	$scope.projectPart=-2;
	$scope.systemType=-2;
	var teststs1=document.getElementById("leftss");
 	teststs1.style.display="block";
 	var teststs1=document.getElementById("leftss_data");
 	teststs1.style.display="none";
 	var teststs1=document.getElementById("data_get");
 	teststs1.style.border="none";
 	var teststs=document.getElementById("item_list");
 	teststs.style.borderBottom="1px solid #2d64b3";
 	var searchUserlog=document.getElementById("search_userlog");
 	searchUserlog.style.display="block";
 	var idomst=document.getElementById("userlog_table_2");
 	idomst.style.display="block";
 	
 	var idoms1=document.getElementById("userlog_table_1");
 	idoms1.style.display="block";
 	var idoms1=document.getElementById("userlog_table_2");
 	idoms1.style.display="block";
 	var idoms2=document.getElementById("content_twos");
 	idoms2.style.display="none";
 	
 };
 $scope.showData = function(){
	$scope.projectId=-2;
	$scope.projectPart=-2;
	$scope.systemType=-2;
	var teststs1=document.getElementById("leftss");
 	teststs1.style.display="none";
 	var teststs1=document.getElementById("leftss_data");
 	teststs1.style.display="block";
 	var testst=document.getElementById("item_list");
 	testst.style.border="none";
 	var testst1=document.getElementById("data_get");
 	testst1.style.borderBottom="1px solid #2d64b3";
 	
 	var idomst=document.getElementById("userlog_table_2");
 	idomst.style.display="none";
 	var searchUserlog=document.getElementById("search_userlog");
 	searchUserlog.style.display="none";
 	var idomst=document.getElementById("userlog_table_1");
 	idomst.style.display="none";
 	var idom2=document.getElementById("content_twos");
 	idom2.style.display="block";
 };
 ////////
 $scope.setProjectName = function(temp,index){
 		$scope.projectId=temp;
		if(item_temp1!==index && item_temp1!==""){
			var test=document.getElementById("item"+item_temp1);
			test.style.color="";
		}else{
			item_temp1=index;
	     	var tests=document.getElementById("item"+index);
	     	tests.style.color="red";
	     	$scope.userLogList="";
		}
		item_temp1=index;
 	var tests=document.getElementById("item"+index);
 	tests.style.color="red";
 	$scope.userLogList="";	
 	$scope.getUserLogList(pageSize,pageIndex,UserLog,$scope.startTime,$scope.finishedTime,searchContent);
 	//$scope.getDuctExcel();
 };
 $scope.setProjectPart = function(temp,index){
	$scope.projectPart=index;
 	if(item_temp2!==index && item_temp2!==""){
			var test=document.getElementById("items"+item_temp2);
     	test.style.color="";
		}else{
			item_temp2=index;
     	var tests=document.getElementById("items"+index);
     	tests.style.color="red";
     	$scope.userLogList="";
		}
 	item_temp2=index;
 	var test=document.getElementById("items"+index);
 	test.style.color="red";
 	$scope.userLogList="";
 	$scope.getUserLogList(pageSize,pageIndex,UserLog,$scope.startTime,$scope.finishedTime,searchContent);
 };
 $scope.setSystemType = function(temp,index){
	 if(temp=="苹果系统"){
		 $scope.systemType=0;
	 }
	 if(temp=="安卓系统"){
		 $scope.systemType=1;
	 }
 	if(item_temp3!==index && item_temp3!==""){
			var test=document.getElementById("itemss"+item_temp3);
     	test.style.color="";
		}else{
			item_temp3=index;
     	var tests=document.getElementById("itemss"+index);
     	tests.style.color="red";
     	$scope.userLogList="";
		}
 	item_temp3=index;
 	var test=document.getElementById("itemss"+index);
 	test.style.color="red";
 	$scope.userLogList="";
 	$scope.getUserLogList(pageSize,pageIndex,UserLog,$scope.startTime,$scope.finishedTime,searchContent);
 };
 $scope.getUserLogExcel();
 $scope.setTime = function(){
		$scope.startTime=document.getElementById("d4311s").value;
		$scope.finishedTime=document.getElementById("d4312s").value;
		$scope.getUserLogExcel();
 };
 $scope.setUserLogTime = function(){
		$scope.startTime=document.getElementById("d4311s").value;
		$scope.finishedTime=document.getElementById("d4312s").value;
		$scope.getUserLogList(pageSize,pageIndex,UserLog,$scope.startTime,$scope.finishedTime,searchContent);
 };
 /////////search搜索
 $scope.findItem= function(){
	 var searchContent=document.getElementById("search_input").value;
	 $scope.getUserLogList(pageSize,pageIndex,UserLog,$scope.startTime,$scope.finishedTime,searchContent);
 };
 $scope.getUserLogExcelOfTime=function(){
		$scope.getUserLogExcel();
		//$("#ductDownload").append("<div id=\"projectPageCode\"></div>");
		$("#ductDownload").append("<a href=\"{{userLogUrl}}\" target=\"_blank\" download=\"{{quantityExcel.originName}}\"><span>导出</span></a>");
	}
}
