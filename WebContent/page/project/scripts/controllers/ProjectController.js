function ProjectController($scope,ProjectService) {
	console.log("载入ProjectController");
	$scope.currentPage = 1;
	$scope.ProjectTofind = {};
	$scope.projectTitles=["序号","项目名称","项目编码","施工单位","项目负责人","设计单位","施工地点","项目简介","建设单位","版本","施工时间","施工周期","操作"];
	//////显示增加项目界面
	$scope.showProjectAdd = function(){
		document.getElementById("projectInfo").style.display = 'block';
	    $scope.projecttitle="增加项目";
	}
	//////显示更新项目界面
	$scope.projectChangeClick = function(projectId){
	    ProjectService.findProject(projectId,token).then(function(result){
	      $scope.findProjectInfo=result.data;
	      document.getElementById("addProjectHtml").style.display = 'block';
	      $scope.title="更新项目";
	    });
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
		    	  alert(p);
		    	  $scope.getProjectList(pageSize,p,$scope.projectTofind);
		    	  
		      }

		  });
	  }
	  //////显示项目添加界面
	  $scope.showaddProject = function() {
		  $scope.findProjectInfo = {};
		  document.getElementById("addProjectHtml").style.display = 'block';
	      $scope.title="增加项目";
	  }
	  
	  ////显示更新项目界面
	 $scope.ProjectChangeClick = function(ProjectId){
	    ProjectService.findProject(ProjectId,token).then(function(result){
	      $scope.findProjectInfo=result.data;
	      document.getElementById("addProjectHtml").style.display = 'block';
	      $scope.title="更新项目";
	    });
	 }
	 
	 ///////更新项目
	  $scope.updateProject = function(Project){
	  	ProjectService.updateProject(Project,token).then(function(result){
	       $scope.updateProjectInfo=result.data;
	    });
	  }
	  
	 /////删除项目
	 $scope.deleteProject = function(Projectid){
		  	ProjectService.deleteProject(Projectid,token).then(function(result){
		       $scope.deleteProjectInfo=result.data;
		       $scope.getProjectList();
		       
		    });
	 }
	 /////返回项目列表
	 $scope.returnProjectlist = function(){
		       $scope.getProjectList();
		       document.getElementById("addProjectHtml").style.display = 'none';
	 }
	 //////重置添加项目信息
	 $scope.resetProject = function(){
		 $scope.findProjectInfo = {};
	 }

	 /////增加项目
	 $scope.addProjectByAdmin = function(){
		 if($scope.title=="增加项目"){
			 findProjectInfo=$scope.findProjectInfo;
			 ProjectService.addProjectByAdmin(findProjectInfo,token).then(function(result){
			       $scope.addProjectByAdminInfo=result.data;
			       $scope.getProjectList(pageSize,1,$scope.ProjectTofind);
			       
			    });
		 }
		 if($scope.title=="更新项目")
		{
			 ProjectService.updateProject($scope.findProjectInfo,token).then(function(result){
			       $scope.updateProjectInfo=result.data;
			       $scope.getProjectList(pageSize,1,$scope.ProjectTofind);
			       
			    });
		}
		 
	 }
	 ////////模糊查找项目
	 $scope.find = function() {
		 $scope.currentPage = 1;
		 $scope.getProjectList(pageSize,$scope.currentPage,$scope.ProjectTofind);
	 }
	 $scope.getProjectList(pageSize,$scope.currentPage,$scope.ProjectTofind);
}