function hideaddprojectHtml(){
	$("#addProjectHtml").css("display","none");
}
function showaddProjectHtml(){
	$("#addProjectHtml").css("display","block");
}
function ProjectController($scope,ProjectService) {
  console.log("载入ProjectController");

  $scope.projectTitles=["序号","项目名称","项目编码","施工单位","项目负责人","设计单位","施工地点","项目简介","建设单位","版本","施工时间","施工周期"];
  ProjectService.getProjectList().then(function (result){
		$scope.ProjectList = result;
  })
  $scope.ProjectChangeClick = function(ProjectId){
  	ProjectService.deleteProject(ProjectId).then(function(result){

  	})
  }
  
  
}