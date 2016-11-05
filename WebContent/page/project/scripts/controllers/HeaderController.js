function HeaderController($scope,DataService,$filter) {
  console.log("载入HeaderController");
  $scope.headerItems = [{name:"用户管理",page:"userList"},{name:"项目管理",page:"projectList"}];
 /* $scope.getUserList = function(data){
	  $scope.projectvisible = false;
	  $scope.uservisible = true;
  }
  $scope.getProjectList = function(data){
	  $scope.uservisible = false;
	  $scope.projectvisible = true;
  }*/
  
}