var token=getCookie('token');
function showaddUser(){
	$("#addUserHtml").css("display","block");
}
function hideadduserHtml(){
	$("#addUserHtml").css("display","none");
}
function UserController($scope,UserService) {
  console.log("载入UserController");

  $scope.userTitles=["序号","用户名","真实姓名","权限","邮箱","电话","注册日期","操作"];

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
  $scope.updateUser = function(user){
  	UserService.updateUser(user,token).then(function(result){
       $scope.updateUserInfo=result.data;
    });
  }
 $scope.userChangeClick = function(userId){
    UserService.findUser(userId,token).then(function(result){
      $scope.findUserInfo=result.data;
    });
 }
 $scope.deleteUser = function(userid){
	  	UserService.deleteUser(userid,token).then(function(result){
	       $scope.deleteUserInfo=result.data;
	    });
 }
}

//存储cookies
function setCookie(name,value,expiredays) 
{ 
  var argv = setCookie.arguments; 
  var argc = setCookie.arguments.length; 
  var LargeExpDate = new Date (); 
  var expires = (argc > 2) ? argv[2] : expiredays ; 
  if(expires!=null) 
  { 
      LargeExpDate.setTime(LargeExpDate.getTime() + (expires*1000*3600*24));         
  } 
  document.cookie = name + "=" + escape (value)+((expires == null) ? "" : ("; expires=" +LargeExpDate.toGMTString())); 
}

//获取cookies
function getCookie(Name) 
{ 
  var search = Name + "=" 
  if(document.cookie.length > 0) 
  { 
      offset = document.cookie.indexOf(search) 
      if(offset != -1) 
      { 
          offset += search.length 
          end = document.cookie.indexOf(";", offset) 
          if(end == -1) end = document.cookie.length 
          return unescape(document.cookie.substring(offset, end)) 
      } 
      else return "" 
  } 
} 

//删除cookies
function deleteCookie(name) 
{ 
  var expdate = new Date(); 
  expdate.setTime(expdate.getTime() - (86400 * 1000 * 1)); 
  setCookie(name, "", expdate); 
}