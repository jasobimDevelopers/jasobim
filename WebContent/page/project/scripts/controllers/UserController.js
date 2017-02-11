var token=getCookie('token');
var userIcon=getCookie('userIcon');
var userName=getCookie('userName');

function showUserIcon() {
	var file = document.getElementById('inputicon').files[0];
	console.log(file);
    if(!/image\/\w+/.test(file.type)){ 
        alert("文件必须为图片！");
        return false; 
    } 
    var reader = new FileReader(); 
    reader.readAsDataURL(file); 
    reader.onload = function(e){ 
        document.getElementById('showId').src = this.result;
    } 
}

function UserController($scope,UserService) {
	
  console.log("载入UserController");
 
  $scope.currentPage = 1;
  $scope.userTofind = {};
  var user="";
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
	  var widths=document.body.offsetWidth+"px";
	  $(".allUser").css("width",widths);
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
	 if(confirm("确定删除？")) {  
		 UserService.deleteUser(userid,token).then(function(result){
		       $scope.deleteUserInfo=result.data;
		       $scope.getUserList(pageSize,$scope.currentPage,$scope.userTofind);
		       
		    });
     }  
	  	
 }
 /////返回用户列表
 $scope.returnUserlist = function(){
	       $scope.getUserList(pageSize,pageIndex,user);
	       document.getElementById("addUserHtml").style.display = 'none';
 }
 //////重置添加用户信息
 $scope.resetUser = function(){
	 document.getElementById('showId').src = "";
	 document.getElementById('inputicon').value="";
	 $scope.findUserInfo = {};
 }

 	function checkMobile(){
	    var sMobile = document.getElementById("tel").value;
	    if(!(/^1[3|4|5|8|7][0-9]\d{4,8}$/.test(sMobile))){
	        alert("不是完整的11位手机号或者正确的手机号");
	        return false;
	    }else{
	    	return true;
	    }
	}
	function checkEMail(){
	    var temp = document.getElementById("inputadress");
	    //对电子邮件的验证
	    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	    if(!myreg.test(temp.value)){
	        alert('请输入有效的E_mail！');
	        return false;
	    }else{
	    	return true;
	    }
	}
	//////用户重名验证
	$scope.sameName = function(){
		var username=document.getElementById("inputname").value;
	}
 /////增加用户
 $scope.addUserByAdmin = function(){
	 if($scope.title=="增加用户"){
		 document.getElementById('showId').src = "";
		 document.getElementById('inputicon').value="";
		 findUserInfo = {};
		 if(!/image\/\w+/.test(document.querySelector('input[type=file]').files[0].type)){ 
				 document.getElementById('showId').src = "";
				 document.getElementById('inputicon').value="";
				 alert("文件必须为图片！");
				 return false; 
		 }else{
			 var userIcons = document.querySelector('input[type=file]').files[0];
		 }
		 var formDatas=new FormData();
		 formDatas.append('file',userIcons);
		 if(checkMobile()==true )
		 {
			 if(checkEMail()==true){
				 for(var key in $scope.findUserInfo){
					 if($scope.findUserInfo[key] != null) {
						   formDatas.append(key, $scope.findUserInfo[key]);
					   }
				 }

				 UserService.addUserByAdmin(formDatas,token).then(function(result){
				       $scope.addUserByAdminInfo=result.data;
				       $scope.getUserList(pageSize,1,$scope.userTofind);
				       document.getElementById("addUserHtml").style.display = 'none';
				    });
			 }
			
		 }
		
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
		 formData.append('email',$scope.findUserInfo.email);
		 formData.append('tel',$scope.findUserInfo.tel);
		 if($scope.findUserInfo.userType != null && ($scope.findUserInfo.userType==0 ||$scope.findUserInfo.userType ==1)) {
			 formData.append('userType',$scope.findUserInfo.userType);
		 }
		 
		 UserService.updateUser(formData,token).then(function(result){
		       $scope.updateUserInfo=result.data;
		       $scope.getUserList(pageSize,1,$scope.userTofind);
		       document.getElementById("addUserHtml").style.display = 'none';
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
