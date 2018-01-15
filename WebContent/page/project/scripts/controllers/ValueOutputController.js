var token=getCookie('token');

function ValueOutputController($scope,ValueOutputService) {
	
  console.log("载入ValueOutputController");
  $scope.currentPage = 1;
  $scope.valueOutputLists={};
  $scope.valueOutputTofind={};
  $scope.deletevalueOutputInfo={};
  var pageSize=10;
  var pageIndex=1;
  var valueOutputid=null;
  var valueOutput="";
  $scope.valueOutputTitle="";
  $scope.addValueOutputs="";
  var idList="";
  $scope.valueOutputTitles=["序号","项目名","总产值","已完成","时间","操作"];
  	//////搜索
  $scope.valueOutputFind = function(){
 	 $scope.currentPage = 1;
 	 $scope.getvalueOutputList(pageSize,$scope.currentPage,$scope.valueOutputTofind);
  };
  ///////分页获取用户列表
  $scope.getvalueOutputList = function(pageSize,pageIndex,valueOutput) {
	  ValueOutputService.getvalueOutputList(pageSize,pageIndex,valueOutput).then(function (result){
	  	  $scope.valueOutputList = result.data;
	      $scope.currentPage = result.currentPage;
	      $scope.totalPage = result.totalPage;
	      $scope.valueOutputPage($scope.totalPage,$scope.currentPage);
	  });
  }
  $scope.getvalueOutputList(pageSize,pageIndex,valueOutput);
  
  ////////分页回调函数
  $scope.valueOutputPage = function(iPageCount,iCurrent) {
	  $("#valueOutputPageCode").remove();
	  $("#table-buton111").append("<div id=\"valueOutputPageCode\"></div>");
	  $("#valueOutputPageCode").createPage({

	      pageCount:iPageCount,

	      current:iCurrent,

	      backFn:function(p){
	    	  $scope.getvalueOutputList(pageSize,p,$scope.valueOutputTofind);
	    	  
	      }

	  });
  }
 /////删除用户
 $scope.deletevalueOutput = function(valueOutputid){
	 if(confirm("确定删除？")) {  
		 ValueOutputService.deleteValueOutput(valueOutputid,token).then(function(result){
		       $scope.deletevalueOutputInfo=result.data;
		       $scope.getvalueOutputList(pageSize,$scope.currentPage,$scope.valueOutputTofind);
		       
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
				 idList=$scope.valueOutputList[i].id;
			 }else{
				 idList=idList+","+$scope.valueOutputList[i].id; 
			 }
			 
		 }
	  }
	 ValueOutputService.deleteValueOutput(idList,token).then(function(result){
	       $scope.deletevalueOutputInfo=result.data;
	       $scope.getvalueOutputList(pageSize,$scope.currentPage,$scope.valueOutputTofind);
	       
	    });
 };
 $scope.showValueOutputAddHtml = function(){
	/////显示添加页面
	 layer.open({
	        type:1,
	        title: '每月产值添加',
	        area: ['500px','400px'],
	        btn:['确定','取消'],
	        yes:function(index,layero){
	        	$scope.addValueOutputInfo={};
	        	$scope.addValueOutputInfo.others=layero.find('#projectName')[0].value;
	        	$scope.addValueOutputInfo.projectId=parseInt(layero.find('#projectId')[0].value);
	        	$scope.addValueOutputInfo.num=Number(layero.find('#num')[0].value);
	        	$scope.addValueOutputInfo.finished=Number(layero.find('#finished')[0].value);
	        	$scope.valueOutputTitle="产值添加";
	        	$scope.addValueOutputByAdmin();
	        },
	        content:$("#show_valueOutput").html()
	    });
			
 };
 $scope.addValueOutputByAdmin = function(){
	 if($scope.valueOutputTitle=="产值添加"){
		 var formData = new FormData();
		 for (var key in $scope.addValueOutputInfo) {
			   if($scope.addValueOutputInfo[key] != null) {
				   formData.append(key, $scope.addValueOutputInfo[key]);
			   }
		 }
    	 ValueOutputService.addValueOutputByAdmin(formData).then(function(result){
		       $scope.addValueOutputs=result.data; 
		       $scope.getvalueOutputList(pageSize,pageIndex,valueOutput);
		    });
	 }
 };
}
