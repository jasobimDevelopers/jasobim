var token=getCookie('token');
var userIcon=getCookie('token');
var userName=getCookie('userName');
function UserController($scope,UserService) {
	
  console.log("载入UserController");
  $scope.currentPage = 1;
  $scope.userTofind = {};
  $scope.findUserInfo = {};
  $scope.userTitles=["序号","用户名","真实姓名","头像","权限","邮箱","电话","注册日期","操作"];
  $scope.roleList=[{name:"超级管理员"},{name:"管理员"}];
  ///////分页获取用户列表
  $scope.getUserList = function(pageSize,pageIndex,user) {
	  UserService.getUserList(pageSize,pageIndex,user).then(function (result){
	  	  $scope.userList = result.data;
	      $scope.currentPage = result.currentPage;
	      $scope.totalPage = result.totalPage;
	      $scope.userPage($scope.totalPage,$scope.currentPage);
	  });
  }
  ////////分页回调函数
  $scope.userPage = function(iPageCount,iCurrent) {
	  $("#userPageCode").remove();
	  $("#table-buton").append("<div id=\"userPageCode\"></div>");
	  $("#userPageCode").createPage({

	      pageCount:iPageCount,

	      current:iCurrent,

	      backFn:function(p){
	    	  $scope.getUserList(pageSize,p,$scope.userTofind);
	    	  
	      }

	  });
  }
  //////显示用户添加界面
  $scope.showaddUser = function() {
	  $scope.findUserInfo = {};
	  document.getElementById("addUserHtml").style.display = 'block';
      $scope.title="增加用户";
  }
  //////隐藏 用户添加界面
  $scope.hideadduserHtml= function(){
		$scope.findUserInfo = {};
		document.getElementById("addUserHtml").style.display = 'none';
	}
  ///////更新用户
  $scope.updateUser = function(user){
  	UserService.updateUser(user,token).then(function(result){
       $scope.updateUserInfo=result.data;
    });
  }
  ////显示更新用户界面
 $scope.userChangeClick = function(userId){
    UserService.findUser(userId,token).then(function(result){
      $scope.findUserInfo=result.data;
      document.getElementById("addUserHtml").style.display = 'block';
      $scope.title="更新用户";
    });
 }
 /////删除用户
 $scope.deleteUser = function(userid){
	  	UserService.deleteUser(userid,token).then(function(result){
	       $scope.deleteUserInfo=result.data;
	       $scope.getUserList(pageSize,$scope.currentPage,$scope.userTofind);
	       
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
		 findUserInfo = {};
		 findUserInfo=$scope.findUserInfo;
		 UserService.addUserByAdmin(findUserInfo,token).then(function(result){
		       $scope.addUserByAdminInfo=result.data;
		       $scope.getUserList(pageSize,1,$scope.userTofind);
		       
		    });
	 }
	 if($scope.title=="更新用户")
	{
		 var formData = new FormData();
		 formData.append('id', $scope.findUserInfo.id);
		 var userIcon = document.querySelector('input[type=file]').files[0];
		 //var test=document.getElementById("inputicon").value;
		 formData.append('file',userIcon);
		 formData.append('userName',$scope.findUserInfo.userName);
		 formData.append('realName',$scope.findUserInfo.realName);
		 formData.append('eamil',$scope.findUserInfo.email);
		 formData.append('tel',$scope.findUserInfo.tel);
		 if($scope.findUserInfo.userType != null && ($scope.findUserInfo.userType==0 ||$scope.findUserInfo.userType ==1)) {
			 formData.append('userType',$scope.findUserInfo.userType);
		 }
		 
		 UserService.updateUser(formData,token).then(function(result){
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
