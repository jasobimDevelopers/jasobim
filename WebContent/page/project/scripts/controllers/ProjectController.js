////////////////项目详情信息和项目基本信息页面切换
function projectInfo(){
	document.getElementById("include_header").style.display = 'none';
	document.getElementById("containers").style.display = 'none';
	document.getElementById("projectInfoHtml").style.display = 'block';
}

////////////////////////////////
function ProjectController($scope,ProjectService) {
	
	$scope.test = function(index) {
		alert(index);
	}
	
	
	console.log("载入ProjectController");
	$scope.currentPage = 1;
	$scope.ProjectTofind = {};
	$scope.findProjectInfo = {};
	var buildingInfo = {};
	$scope.projectTitles=["序号","项目名称","项目编码","施工单位","项目负责人","设计单位","施工地点","项目简介","建设单位","版本","施工时间","施工周期","操作"];
	$scope.itemTitles=["序号","构件名称","底部高程","系统类型","尺寸","长度","设备类型","所属类别","标高","偏移量","面积","材质","类型名","操作"];
	//////显示增加项目界面
	$scope.showProjectAdd = function(){
		$scope.findProjectInfo = {};
		document.getElementById("addProjectHtml").style.display = 'block';
	    $scope.projectTitle="增加项目";
	}
	//////隐藏增加项目界面
	$scope.hideaddprojectHtml=function(){
		document.getElementById("addProjectHtml").style.display = 'none';
	}
	//////显示更新项目界面
	$scope.projectChangeClick = function(projectId){
	    ProjectService.findProject(projectId,token).then(function(result){
	      $scope.findProjectInfo=result.data;
	      $scope.projectid=projectId;
	     
	      document.getElementById("projectInfoHtml").style.display = 'block';
	      document.getElementById("containers").style.display = 'none';
	      document.getElementById("include_header").style.display = 'none';
	      $scope.projectTitle="更新项目";
	    });
	    $scope.getBuildingList(projectId);
	      $scope.buildingArray=menuArray(buildingInfo.buildingNum);
		  $scope.floorArray=menuArray(buildingInfo.floorNum);
		  $scope.householdArray=menuArray(buildingInfo.householdNum);
	 }
	  
	  ///////分页获取项目列表
	$scope.getProjectList = function(pageSize,pageIndex,project) {
		  ProjectService.getProjectList(pageSize,pageIndex,project).then(function (result){
		  	  $scope.projectList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.projectPage($scope.totalPage,$scope.currentPage);
		  });
	  }
	  ////////分页回调函数
	  $scope.projectPage = function(iPageCount,iCurrent) {
		  $("#projectPageCode").remove();
		  $("#table-buton").append("<div id=\"projectPageCode\"></div>");
		  $("#projectPageCode").createPage({

		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  $scope.getProjectList(pageSize,p,$scope.projectTofind);
		    	  
		      }

		  });
	  }
	  
	  
	  ////显示更新项目界面
	 $scope.ProjectChangeClick = function(ProjectId){
	    ProjectService.findProject(ProjectId,token).then(function(result){
	      $scope.findProjectInfo=result.data;
	      document.getElementById("projectInfoHtml").style.display = 'block';
	      $scope.title="更新项目";
	    });
	 }
	 
	  
	 /////删除项目
	 $scope.deleteProject = function(projectid){
		  	ProjectService.deleteProject(projectid,token).then(function(result){
		       $scope.deleteProjectInfo=result.data;
		       $scope.getProjectList(pageSize,1,$scope.ProjectTofind);
		       
		    });
	 }
	 /////返回项目列表
	 $scope.returnProjectlist = function(){
		 	var project={};
	       document.getElementById("addProjectHtml").style.display = 'none';
	 }
	 //////重置添加项目信息
	 $scope.resetProject = function(){
		 $scope.findProjectInfo = {};
	 }

	 /////增加项目
	 $scope.addProjectByAdmin = function(){
		 if($scope.projectTitle=="增加项目"){
			 findProjectInfo = {};
			 findProjectInfo=$scope.findProjectInfo;
			 ProjectService.addProjectByAdmin(findProjectInfo,token).then(function(result){
			       $scope.addProjectByAdminInfo=result.data;
			       $scope.getProjectList(pageSize,1,$scope.ProjectTofind);
			       
			    });
		 }
		 if($scope.projectTitle=="更新项目")
		{
			 ProjectService.updateProject($scope.findProjectInfo,token).then(function(result){
			       $scope.updateProjectInfo=result.data;
			       $scope.getProjectList(pageSize,1,$scope.ProjectTofind);
			       
			    });
		}
		 
	 }
	 ////////模糊查找项目
	 $scope.projectFind = function() {
		 $scope.currentPage = 1;
		 $scope.getProjectList(pageSize,$scope.currentPage,$scope.ProjectTofind);
	 }
	 $scope.getProjectList(pageSize,$scope.currentPage,$scope.ProjectTofind);

	 $scope.projectInfo = function(){
		 console.log("test");
		 alert("test");
	 }
	 //////////////////////////////项目详情信息
	 $scope.projectInfoHead=[{name:"基本信息"},{name:"构件信息"},{name:"图纸信息"},{name:"工程量信息"},{name:"安全技术交底"},{name:"问题列表"}];
	 $scope.projectPhaseInfo=[{name:"电气"},{name:"给排水"},{name:"暖通"},{name:"消防"}];
	 $scope.projectHouseholdInfo=[{name:"A型"},{name:"B型"},{name:"C型"},{name:"D型"}];
	 $scope.projectQuestionOfType=[{name:"安全"},{name:"质量"},{name:"其他"}];
	 $scope.projectQuestionOfStatus=[{name:"一般"},{name:"重要"},{name:"紧急"}];
	 /////////////////////////
	 /*
	  * 菜单的选择操作
	  * 
	  * 
	 */
	 ////////////////////////
	 $scope.getBuildingList = function(projectId){
		 ProjectService.getBuildingList(projectId).then(function(result){
		       buildingInfo=result.data;    
		    });
	 }
	 function menuArray(total)
	 {
		var sizePerPage = 5;
		var totalPage = Math.ceil(total/sizePerPage);
		var arr = new Array();
		for(var i = 0; i < totalPage; i++)
		{
			arr[i] = new Array();
			for(var j = 0; j < 5; j++) 
			{
				if(i*5+(j+1) <= total) 
				{
					arr[i][j] = i*5+(j+1);
				} else 
				{
					break;
				}
			}
		}
		console.log(arr);
		return arr;
	 }

/////////////////
	 /*
	  * 
	  * 构件信息操作
	  * 
	  * 
	  * 
	 */
	 ////////////////////////构件信息分页获取
	 $scope.getProjectItemList = function(projectId,pageSize,pageIndex,item) {
		  ProjectService.getItemList(projectId,pageSize,pageIndex,item).then(function (result){
			  $scope.getBuildingList(projectId);
			 
		  	  $scope.projectItemList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.projectPage($scope.totalPage,$scope.currentPage);
		  });
		  $scope.buildingArray=menuArray(buildingInfo.buildingNum);
		  $scope.floorArray=menuArray(buildingInfo.floorNum);
		  $scope.householdArray=menuArray(buildingInfo.householdNum);
	  }
////////分页回调函数
	  $scope.projectPage = function(iPageCount,iCurrent) {
		  $("#projectPageCode").remove();
		  $("#table-buton").append("<div id=\"projectPageCode\"></div>");
		  $("#projectPageCode").createPage({

		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  $scope.getProjectItemList(projectId,pageSize,p,$scope.buildingInfo);
		    	  
		      }

		  });
	  }
	 
/////////////////
	 /*
	  * 
	  * 图纸列表信息操作
	  * 
	  * 
	  * 
	 */
	 ////////////////////////图纸列表信息分页获取
	 $scope.getProjectPaperList = function(pageSize,pageIndex,paper) {
		  ProjectService.getPaperList(pageSize,pageIndex,paper).then(function (result){
		  	  $scope.projectPaperList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.paperPage($scope.totalPage,$scope.currentPage);
		  });
	  }
/////////////////
	 /*
	  * 
	  * 工程量信息操作
	  * 
	  * 
	  * 
	 */
	 ////////////////////////工程量信息分页获取
	 $scope.getProjectQuantityList = function(pageSize,pageIndex,quantity) {
		  ProjectService.getQuantityList(pageSize,pageIndex,quantity).then(function (result){
		  	  $scope.projectQuantityList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.quantityPage($scope.totalPage,$scope.currentPage);
		  });
	  }
/////////////////
	 /*
	  * 
	  * 安全技术交底操作
	  * 
	  * 
	  * 
	 */
	 ////////////////////////安全交底分页获取
	 $scope.getProjectVideoList = function(pageSize,pageIndex,video) {
		  ProjectService.getVideoList(pageSize,pageIndex,video).then(function (result){
		  	  $scope.projectVideoList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.videoPage($scope.totalPage,$scope.currentPage);
		  });
	  }
/////////////////
	 /*
	  * 
	  * 问题列表操作
	  * 
	  * 
	  * 
	 */
	 ////////////////////////问题列表分页获取
	 $scope.getProjectQuestionList = function(pageSize,pageIndex,question) {
		  ProjectService.getQuestionList(pageSize,pageIndex,question).then(function (result){
		  	  $scope.projectQuestionList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.questionPage($scope.totalPage,$scope.currentPage);
		  });
	  }
	 
	 
	 $scope.projectInfoChange = function(projectType){
		 if(projectType == "基本信息"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'block';
			 document.getElementById("projectItemInfoHtml").style.display = 'none';
			 document.getElementById("projectPaperInfoHtml").style.display = 'none';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'none';
			 document.getElementById("projectVideoInfoHtml").style.display = 'none';
			 document.getElementById("projectQuestionInfo").style.display = 'none';
		 }
		 if(projectType == "构件信息"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'none';
			 document.getElementById("projectItemInfoHtml").style.display = 'block';
			 document.getElementById("projectPaperInfoHtml").style.display = 'none';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'none';
			 document.getElementById("projectVideoInfoHtml").style.display = 'none';
			 document.getElementById("projectQuestionInfo").style.display = 'none';
			 var item=null;
			 $scope.getProjectItemList($scope.findProjectInfo.id,pageSize,1,item);
			
			 
		 }
		 if(projectType == "图纸信息"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'none';
			 document.getElementById("projectItemInfoHtml").style.display = 'none';
			 document.getElementById("projectPaperInfoHtml").style.display = 'block';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'none';
			 document.getElementById("projectVideoInfoHtml").style.display = 'none';
			 document.getElementById("projectQuestionInfo").style.display = 'none';
			 var paper=null;
			 $scope.getProjectPaperList($scope.findProjectInfo.id,pageSize,1,paper);
		 }
		 if(projectType == "工程量信息"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'none';
			 document.getElementById("projectItemInfoHtml").style.display = 'none';
			 document.getElementById("projectPaperInfoHtml").style.display = 'none';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'block';
			 document.getElementById("projectVideoInfoHtml").style.display = 'none';
			 document.getElementById("projectQuestionInfo").style.display = 'none';
			 var quantity=null;
			 $scope.getProjectQuantityList($scope.findProjectInfo.id,pageSize,1,quantity);
		 }
		 if(projectType == "安全技术交底"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'none';
			 document.getElementById("projectItemInfoHtml").style.display = 'none';
			 document.getElementById("projectPaperInfoHtml").style.display = 'none';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'none';
			 document.getElementById("projectVideoInfoHtml").style.display = 'block';
			 document.getElementById("projectQuestionInfo").style.display = 'none';
			 var video=null;
			 $scope.getProjectVideoList($scope.findProjectInfo.id,pageSize,1,video);
		 }
		 if(projectType == "问题列表"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'none';
			 document.getElementById("projectItemInfoHtml").style.display = 'none';
			 document.getElementById("projectPaperInfoHtml").style.display = 'none';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'none';
			 document.getElementById("projectVideoInfoHtml").style.display = 'none';
			 document.getElementById("projectQuestionInfo").style.display = 'block';
			 var question=null;
			 $scope.getProjectQuestionList($scope.findProjectInfo.id,pageSize,1,question);
		 }
	 }
	
	 
	 
	 
	 
	 
	 
}