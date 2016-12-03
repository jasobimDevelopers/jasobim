var userName=getCookie('userName');
var userIcon=getCookie('userIcon');
function HeaderController($scope,DataService,$filter) {
  console.log("载入HeaderController");
  $scope.headerItems = [{name:"用户管理",page:"userList"},{name:"项目管理",page:"projectList"},{name:"质量安全管理",page:"projectQuestionList"},{name:"意见反馈",page:"suggesttionList"},{name:"关于我们",page:"aboutList"}];
 /* $scope.getUserList = function(data){
	  $scope.projectvisible = false;
	  $scope.uservisible = true;
  }
  $scope.getProjectList = function(data){
	  $scope.uservisible = false;
	  $scope.projectvisible = true;
  }*/
  $scope.userName=userName;
  $scope.userIcon="files/userIcons/"+userIcon;
}