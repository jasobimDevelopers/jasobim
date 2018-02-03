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
  $scope.systemTypelist = [{"id":0,"name":"安装人员"},{"id":1,"name":"土建人员"}];
  $scope.userTypeList = [{"id":0,"name":"管理员"},{"id":1,"name":"普通用户"},{"id":2,"name":"投资方"},{"id":3,"name":"项目人员/项目负责人"}];
  $scope.currentPage = 1;
  $scope.userTofind = {};
  $scope.projectLists={};
  var project={};
  var pageSize=10;
  var pageIndex=1;
  $scope.ProjectTofind={};
  var user="";
  var projectNums=null;
  var test="";
  var temp=1;
  $scope.findUserInfo = {};
  $scope.userTitles=["序号","用户名","真实姓名","头像","权限","邮箱","电话","注册日期","操作"];
  ///////其他
  $scope.roleList=[{name:"管理员"},{name:"普通用户"},{name:"投资方"},{name:"项目人员/项目负责人"},
                   {name:"项目经理"},{name:"常务经理"},{name:"土建负责人"},{name:"BIM工程师"}];
  /////项目人员职称
  $scope.roleList1=[{name:"技术员"},{name:"材料员"},{name:"质量员"},{name:"安全员"},{name:"施工员"},{name:"资料员"},
                    {name:"机管员"},{name:"钢筋翻样员"},{name:"木工翻样员"},{name:"技术负责人"},{name:"安装负责人"},];
  /////公司人员职称
  $scope.roleList2=[{name:"质安科"},{name:"生产科"},{name:"技术科"},{name:"预算科"},{name:"材料科"},{name:"总经理"},{name:"副总经理"}];
  $scope.roleList3=[{name:"技术科长"},{name:"技术主管"},{name:"技术员"},{name:"技术员"},{name:"预算主管"},
                    {name:"总经理"},{name:"副总经理"},{name:"质安科长"},{name:"预算员"},
                    {name:"预算科长"},{name:"生产经理"},{name:"市场助理"},{name:"质量员"}];
  $scope.choice = function(ss){
	  test=ss;
	  if(projectNums!=null){
		  projectNums=projectNums+","+ss;
	  }else{
		  projectNums=projectNums+ss;
	  }
	  
  };
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
	  //////初始化获取项目列表
	  $scope.getProjectLists(pageSize,-1,project);
	  $scope.findUserInfo.systemType=0;
	  $scope.findUserInfo.userType=3;
	  document.getElementById("addUserHtml").style.display = 'block';
      $scope.title="增加用户";
  }
  //////隐藏 用户添加界面
  $scope.hideadduserHtml= function(){
		$scope.findUserInfo = {};
		document.getElementById("addUserHtml").style.display = 'none';
		document.getElementById("banzuxinxi").style.display="none";
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
      document.getElementById("addUserHtml").style.display = 'block';
      $scope.findUserInfo=result.data;
      if($scope.findUserInfo.userType==3){
    	  document.getElementById("banzuxinxi").style.display = 'block'; 
      }
      //////初始化获取项目列表
      if($scope.findUserInfo.projectList!=null){
    	  var test=$scope.findUserInfo.projectList.split(",");
      }
      $scope.getProjectLists(pageSize,-1,project);
      document.getElementById("inputpasswords").value = $scope.findUserInfo.password;
      for(var i=0;i<$scope.projectLists.length;i++){
    	  if(test!=undefined && test!=null){
    		  for(var j=0;j<test.length;j++){
        		  if($scope.projectLists[i].id==test[j]){
        			  var PID = document.getElementById("checkBox");
        			  var cb = PID.getElementsByTagName("input");
        			  if(cb[i].type == "checkbox"){
        				 cb[i].checked =true;
        			  }
        		  }
        	  }
    	  }
      }
     
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
		 var pass2=document.getElementById("inputpasswords").value;
		 if(pass2!=$scope.findUserInfo.password){
			 alert("两次的密码不一致！");
			 return;
		 }
		 findUserInfo = {};
	     var userIcons = document.querySelector('input[type=file]').files[0];
		 var formDatas=new FormData();
		 formDatas.append('projectList',projectNums);
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
				       document.getElementById("banzuxinxi").style.display="none";
				       document.getElementById("inputpasswords").value="";
					   document.getElementById("inputUserType").value="";
					   projectNums="";
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
		 if(projectNums!=null && projectNums!='null' && projectNums!=''){
			 formData.append('projectList',projectNums);
		 }
		 
		 formData.append('userName',$scope.findUserInfo.userName);
		 formData.append('realName',$scope.findUserInfo.realName);
		 formData.append('email',$scope.findUserInfo.email);
		 formData.append('password',$scope.findUserInfo.password);
		 formData.append('tel',$scope.findUserInfo.tel);
		 formData.append('workName',$scope.findUserInfo.workName);
		 formData.append('userType',$scope.findUserInfo.userType);
		 formData.append('teamInformation',$scope.findUserInfo.teamInformation)
		 UserService.updateUser(formData,token).then(function(result){
		       $scope.updateUserInfo=result.data;
		       $scope.getUserList(pageSize,1,$scope.userTofind);
		       document.getElementById("addUserHtml").style.display = 'none';
		       document.getElementById("banzuxinxi").style.display="none";
		    });
	}
	 
 }
 ///////分页获取项目列表
$scope.getProjectLists = function(pageSize,pageIndex,project) {
	  UserService.getProjectLists(pageSize,pageIndex,project).then(function (result){
	  	  $scope.projectLists = result.data;
	  });
  }
 ////////模糊查找用户
 $scope.find = function() {
	 $scope.currentPage = 1;
	 $scope.getUserList(pageSize,$scope.currentPage,$scope.userTofind);
 }
 $scope.getUserList(pageSize,$scope.currentPage,$scope.userTofind);
 var index="";
 /*$scope.setUserType = function(userType,index){
	 var fanxiBox = $(".min input:checkbox");
	 if(userType=="投资方"){
		 temp=2;
	 }else if(userType=="超级管理员"){
		 temp=0;
	 }
	 if(this.checked || this.checked=='checked'){

	       fanxiBox.removeAttr("checked");
	       //这里需注意jquery1.6以后必须用prop()方法
	       //$(this).attr("checked",true);
	       $(this).prop("checked", true);
	     }
	 document.getElementById("inputUserType").value=userType;
 }*/
//点击确定保存结果
$scope.submitUserType = function(){
	document.getElementById("gray").style.display = 'none';
	document.getElementById("popup").style.display = 'none';
};
$scope.resetUserType = function(){
	var fanxiBox = $(".min input:checkbox");
	fanxiBox.prop("checked", false);
	$scope.findUserInfo.userType="";
};
$scope.setUserType = function(index,name){
	
	if(index[0].name=="技术员"){
		document.getElementById("banzuxinxi").style.display="block";
	}
	$scope.findUserInfo.workName=name;
}


}
