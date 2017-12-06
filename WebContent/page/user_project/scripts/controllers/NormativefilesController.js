var index;
function NormativefilesController($scope,NormativefilesService) {
	console.log("载入NormativefilesController");
	var pageSize=10;
	var pageIndex=1;
	var normative="";
	var content;
	$scope.startTime="";
	$scope.finishedTime=""; 
	$scope.normativeType="all";
	$scope.normativefiles=[];
	
	$scope.normativeFilesNameList=[{name:"建筑施工规范"},{name:"建筑设计规范"},{name:"市政路桥规范"},{name:"造价参考资料"},{name:"国际参考资料"},{name:"当地参考资料"},{name:"学习辅助资料"},{name:"未分类资料"},{name:"人防工程资料"},{name:"水利电力通讯"}];
	$scope.normativeFilesNameLists=["建筑施工规范","建筑设计规范","市政路桥规范","造价参考资料","国际参考资料","当地参考资料","学习辅助资料","未分类资料","人防工程资料","水利电力通讯"];
	
	////////////////////////规范列表分页获取
	$scope.getNormativeFilesList = function(pageSize,pageIndex,normative) {
		 content=document.getElementById("normative_search").value;
		 if($scope.normativeType!="all"){
			 normative+= "studyType=" + $scope.normativeType;
		 }
	/*	 if($scope.questionPority!="all"){
			 question+= "priority=" + $scope.questionPority;
		 }*/
		
		 NormativefilesService.getNormativeFilesList(pageSize,pageIndex,normative,content).then(function (result){
		  	  $scope.normativeList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.normativePage($scope.totalPage,$scope.currentPage);
		  });
	  };
	  //////文件名称模糊查找
	  $scope.findNormativeFilesLike = function(){
		  $scope.getNormativeFilesList(pageSize,pageIndex,normative);
	  }
	  ////////问题分页回调函数
	  $scope.normativePage = function(iPageCount,iCurrent) {
		  $("#normativePageCode").remove();
		  $("#table-buton-normative").append("<div id=\"normativePageCode\"></div>");
		  $("#normativePageCode").createPage({

		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  $scope.getNormativeFilesList(pageSize,p,normative);
		      }
		  });
	  };
	  /////初始化规范列表
	  $scope.getNormativeFilesList(pageSize,pageIndex,null);
	 
	  //////删除规范文件
	  $scope.deleteNormativeFiles = function(id,fileId){
		  NormativefilesService.deleteNormativeFile(id,fileId).then(function(result){
			  $scope.getNormativeFilesList(pageSize,pageIndex,null);
		  });
	  };
	  ////通过文件类型筛选规范文件
	  $scope.setNorType = function(studyType){
		  $scope.normativeType=studyType;
		  $scope.getNormativeFilesList(pageSize,pageIndex,normative);
	  }
}