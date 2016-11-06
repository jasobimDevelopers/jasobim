var token=getCookie('token');
function hideadduserHtml(){
	$("#addUserHtml").css("display","none");
}

function UserController($scope,UserService) {
	
  console.log("载入UserController");
  $scope.currentPage = 1;
  $scope.userTofind = {};
  $scope.userTitles=["序号","id","用户名","真实姓名","权限","邮箱","电话","注册日期","操作"];
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
	    	  alert(p);
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
  
///用户注册
  $scope.register=function(){
//	  console.log($scope.findUserInfo);
//	  UserService.
  }
//  if(UserService.userList){
//	 $scope.userList = UserService.userList.data;
//     $scope.currentPage = UserService.userList.currentPage;
//     $scope.totalPage = UserService.userList.currentPage;
//  }else{
//  	UserService.getUserList().then(function (result){
//  	  $scope.userList = result.data;
//      $scope.currentPage = result.currentPage;
//      $scope.totalPage = result.currentPage;
//  	})
//  }
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
