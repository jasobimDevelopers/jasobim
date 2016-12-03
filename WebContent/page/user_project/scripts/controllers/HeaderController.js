var userName=getCookie('userName');
var userIcon=getCookie('userIcon');
function HeaderController($scope,DataService,$filter) {
	
  console.log("载入HeaderController");
  $scope.headerItems = [{name:"项目管理",page:"user_projectList"},{name:"质量安全管理",page:"user_projectQuestionList"},{name:"意见反馈",page:"suggesttionList"},{name:"关于我们",page:"aboutList"}];
 /* $scope.getUserList = function(data){
	  $scope.projectvisible = false;
	  $scope.uservisible = true;
  }
  $scope.getProjectList = function(data){
	  $scope.uservisible = false;
	  $scope.projectvisible = true;
  }*/
  $scope.userName=userName;
  $scope.userIcon=userIcon;
  
  $scope.logout = function() {
	  window.location.href = "login";
  }
  
}