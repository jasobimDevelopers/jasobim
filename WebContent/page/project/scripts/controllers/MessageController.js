function UserController($scope,UserService) {
  console.log("载入UserController");

  $scope.userTitles=["用户名","真实姓名","权限","邮箱","电话","注册日期","操作"];

  if(UserService.userList){
	 $scope.userList = UserService.userList.data;
     $scope.currentPage = UserService.userList.currentPage;
     $scope.totalPage = UserService.userList.currentPage;
  }else{
  	UserService.getUserList().then(function (result){
  	  $scope.userList = result.data;
      $scope.currentPage = result.currentPage;
      $scope.totalPage = result.currentPage;
  	})
  }
  $scope.updateUser = function(user,token){
  	UserService.updateUser(user,token).then(function(result){
       $scope.updateUserInfo=result.data;
    });
  
 $scope.findUser = function(userId,token){
    UserService.findUser(userId,token).then(function(result){
      $scope.findUserInfo=result.data;
    });
 }

  /*$scope.setItem = function(currentPage,totalPage){
    $scope.items = [];
    if(currentPage<7){
      for(int i=1;i<8;i++){
             $scope.items.add(i);
      }
    }else{
     
    }
  */
}