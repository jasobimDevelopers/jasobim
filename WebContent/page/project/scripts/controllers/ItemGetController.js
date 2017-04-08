var index;
function hideUlst(){
	var idom=document.getElementById("name_div");
	if(idom.style.display=="none"){
		document.getElementById("li_show_hide1").style.backgroundImage="url(page/project/images/menuItem2.png)";
		idom.style.display="block";
	}else{
		document.getElementById("li_show_hide1").style.backgroundImage="url(page/project/images/menuItem1.png)";
		idom.style.display = 'none';
		
	}
	
}
function hideUls(){
	var idom=document.getElementById("type_div");
	if(idom.style.display=="none"){
		document.getElementById("li_show_hide2").style.backgroundImage="url(page/project/images/menuItem2.png)";
		idom.style.display="block";
	}else{
		document.getElementById("li_show_hide2").style.backgroundImage="url(page/project/images/menuItem1.png)";
		idom.style.display = 'none';
	}
	
}
function hideUl(){
	var idom=document.getElementById("state_div");
	if(idom.style.display=="none"){
		document.getElementById("li_show_hide3").style.backgroundImage="url(page/project/images/menuItem2.png)";
		idom.style.display="block";
	}else{
		idom.style.display = 'none';
		document.getElementById("li_show_hide3").style.backgroundImage="url(page/project/images/menuItem1.png)";
	}
	
}
function setProject(index){
	index.style.color=="red";
	var test=index.value;
}
function ItemGetController($scope,ItemGetService) {
	console.log("载入ItemGetController");
	var pageSize=10;
	var pageIndex=1;
	var itemGet="";
	var project="";
	$scope.name=null;
	$scope.state=null;
	$scope.projectId=null;
	$scope.itemGetList="";
	$scope.itemNameList=["风管","桥架","弯头"];
	$scope.itemState=["初始化","出库","安装","完成"];
	 ////////////////////////预制化构件列表分页获取
	 $scope.getItemGetList = function(pageSize,pageIndex,itemGet) {
		 var content=document.getElementById("search_input").value;
		 if($scope.projectId!=null && $scope.projectId!=undefined){
			 itemGet+= "projectId=" + $scope.projectId;
		 }
		 if($scope.name!=null && $scope.name!=undefined){
			 itemGet+= "&name=" + $scope.name;
		 }
		 if($scope.state!=null && $scope.state!=undefined){
			 itemGet+= "&state=" + $scope.state;
		 }
		 ItemGetService.getItemGetList(pageSize,pageIndex,itemGet,content).then(function (result){
		  	  $scope.itemGetList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.itemGetPage($scope.totalPage,$scope.currentPage);
		  });
	  };

	
	////////预制化构建分页回调函数
	  $scope.itemGetPage = function(iPageCount,iCurrent) {
		  $("#itemsPageCode").remove();
		  $("#table-buton112").append("<div id=\"itemsPageCode\"></div>");
		  $("#itemsPageCode").createPage({

		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  $scope.getItemGetList(pageSize,p,itemGet);
		      }
		  });
	  };
	  ///////分页获取项目列表
	$scope.getProjectLists = function(pageSize,pageIndex,project) {
		ItemGetService.getProjectLists(pageSize,pageIndex,project).then(function (result){
		  	  $scope.projectLists = result.data;
		  });
	};
	/////初始化获取问题列表
    $scope.getItemGetList(pageSize,pageIndex,itemGet);
    $scope.getProjectLists(pageSize,pageIndex,project);
    $scope.findItem= function(){
    	  $scope.getItemGetList(pageSize,pageIndex,itemGet);
    };
    $scope.setProjectName = function(temp){
    	$scope.projectId=temp;
    	$scope.itemGetList="";
    	$scope.getItemGetList(pageSize,pageIndex,itemGet);
    }
    $scope.setType = function(temp){
    	$scope.name=temp;
    	$scope.itemGetList="";
    	$scope.getItemGetList(pageSize,pageIndex,itemGet);
    }
    $scope.setState = function(temp){
    	$scope.state=temp;
    	$scope.itemGetList="";
    	$scope.getItemGetList(pageSize,pageIndex,itemGet);
    }
    
}