var userName=getCookie('userName');
var userIcon=getCookie('userIcon');
function HeaderController($scope,DataService,$filter) {
  console.log("载入HeaderController");
  $scope.headerItems = [{name:"用户管理",page:"userList"},{name:"项目管理",page:"projectList"},{name:"质量安全管理",page:"projectQuestionList"},{name:"预制化管理",page:"itemGetList"},{name:"意见反馈",page:"feedbackList"},{name:"产值管理",page:"valueOutputList"},{name:"用户记录",page:"userLogList"}];
 /* $scope.getUserList = function(data){
	  $scope.projectvisible = false;
	  $scope.uservisible = true;
  }
  $scope.getProjectList = function(data){
	  $scope.uservisible = false;
	  $scope.projectvisible = true;
  }*/
  
  console.log(userIcon);
  $scope.userName=userName;
  $scope.userIcon=userIcon;
  $scope.logout = function() {
	  window.location.href = "login";
	  deleteCookie("userName");
	  deleteCookie("token");
	  deleteCookie("userIcon");
	  deleteCookie("password");
  };
  $scope.goPage = function(page,index){
	  window.location.href = page;
	  //document.getElementById("submenu"+index).style.display='block';
  };
}