function ProjectController($scope,ProjectService) {
	console.log("载入ProjectController");
	$scope.currentPage = 1;
	$scope.userTofind = {};
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
	      $scope.title="更新用户";
	    });
	 }
	  
	  ///////分页获取用户列表
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
	  //////显示用户添加界面
	  $scope.showaddUser = function() {
		  $scope.findUserInfo = {};
		  document.getElementById("addUserHtml").style.display = 'block';
	      $scope.title="增加用户";
	  }
	  
	  ////显示更新用户界面
	 $scope.userChangeClick = function(userId){
	    UserService.findUser(userId,token).then(function(result){
	      $scope.findUserInfo=result.data;
	      document.getElementById("addUserHtml").style.display = 'block';
	      $scope.title="更新用户";
	    });
	 }
	 
	 ///////更新用户
	  $scope.updateUser = function(user){
	  	UserService.updateUser(user,token).then(function(result){
	       $scope.updateUserInfo=result.data;
	    });
	  }
	  
	 /////删除用户
	 $scope.deleteUser = function(userid){
		  	UserService.deleteUser(userid,token).then(function(result){
		       $scope.deleteUserInfo=result.data;
		       $scope.getUserList();
		       
		    });
	 }
	 /////返回用户列表
	 $scope.returnUserlist = function(){
		       $scope.getUserList();
		       document.getElementById("addUserHtml").style.display = 'none';
	 }
	 //////重置添加用户信息
	 $scope.resetUser = function(){
		 $scope.findUserInfo = {};
	 }

	 /////增加用户
	 $scope.addUserByAdmin = function(){
		 if($scope.title=="增加用户"){
			 findUserInfo=$scope.findUserInfo;
			 UserService.addUserByAdmin(findUserInfo,token).then(function(result){
			       $scope.addUserByAdminInfo=result.data;
			       $scope.getUserList(pageSize,1,$scope.userTofind);
			       
			    });
		 }
		 if($scope.title=="更新用户")
		{
			 UserService.updateUser($scope.findUserInfo,token).then(function(result){
			       $scope.updateUserInfo=result.data;
			       $scope.getUserList(pageSize,1,$scope.userTofind);
			       
			    });
		}
		 
	 }
	 ////////模糊查找用户
	 $scope.find = function() {
		 $scope.currentPage = 1;
		 $scope.getUserList(pageSize,$scope.currentPage,$scope.userTofind);
	 }
	 $scope.getUserList(pageSize,$scope.currentPage,$scope.userTofind);
}