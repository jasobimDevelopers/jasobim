var userName=getCookie('userName');
var userIcon=getCookie('userIcon');
function HeaderController($scope,DataService,$filter) {
	
  console.log("载入HeaderController");
  $scope.headerItems = [{name:"项目管理",page:"user_projectList"},{name:"质量安全管理",page:"user_projectQuestionList"},{name:"二维码专区",page:"codeInformationList"},{name:"意见反馈",page:"suggesttionList"},{name:"关于我们",page:"aboutList"}];
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
  $scope.headCssChange= function(index){
	  $(".head_li").css("background-color","");
	  $(".head_li").eq(index).css("background-color","gray");
	  $(".head_li").eq(index).css("background","url(../images/headBottom_bk.png no-repeat)");
	  $(".head_li").eq(index).css("opacity","0.5");
	  if(index==0){
		  document.getElementById("projectInfoHtml").style.display = 'none';
	      document.getElementById("containers").style.display = 'block';
	      document.getElementById("include_header").style.display = 'block';
	  }
	  if(index==1){
		  
	  }
	  
  }
  $scope.goPage = function(page){
	  window.location.href = page;
	  //document.getElementById("submenu"+index).style.display='block';
  };

  $scope.logout = function() {
	  window.location.href = "login";
	  deleteCookie("userName");
	  deleteCookie("token");
	  deleteCookie("userIcon");
	  deleteCookie("password");
  }
  
}