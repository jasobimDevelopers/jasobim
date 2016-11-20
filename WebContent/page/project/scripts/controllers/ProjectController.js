////////////////项目详情信息和项目基本信息页面切换
var index;
function projectInfo(){
	document.getElementById("include_header").style.display = 'none';
	document.getElementById("containers").style.display = 'none';
	document.getElementById("projectInfoHtml").style.display = 'block';
}
function selectValue(index){
	var obj=$(".myselect");
	for(i=0;i<obj.length;i++){
	        obj[i].selected = false;
	}
	//obj[index].selected=true;
}

function setModel(model) {
	angular.element(model).scope().$parent.$parent.modelFiles = model.files[0];
}
function setPic(pic) {
	angular.element(pic).scope().$parent.$parent.picFiles = pic.files[0];
}

////////////////////////////////
function ProjectController($scope,ProjectService) {
	console.log("载入ProjectController");
	$scope.currentPage = 1;
	$scope.ProjectTofind = {};
	$scope.findProjectInfo = {};
	var buildingInfo = {};
	$scope.building = [];
	$scope.floors =[];
	var fileArray=[];
	var introduced=null;
	var projectId=null;
	$scope.projectTitles=["序号","项目名称","项目编码","施工单位","项目负责人","设计单位","施工地点","项目简介","建设单位","版本","施工时间","施工周期","操作"];
	$scope.itemTitles=["序号","构件名称","底部高程","系统类型","尺寸","长度","设备类型","所属类别","标高","偏移量","面积","材质","类型名","操作"];
	$scope.quantityTitles=["序号","构件名称","专业","系统类型","数值","单位","楼栋号","楼层号","单元号","户型","familyAndType","设备类型","尺寸","设备名称","材质"];
	$scope.questionTitles=["序号","问题类型","问题提交人","问题标题","专业","内容","问题创建时间","问题等级","问题状态","操作"];
	$scope.videoTitles=["序号","交底地址","楼栋号","专业","操作"];
	$scope.paperTitles=["序号","图纸信息","楼栋号","楼层","专业","操作"];
	$scope.flag=["工程量页面","构件信息页面","图纸信息页面","问题列表页面","安全交底页面"];
	$scope.phase="all";
	$scope.buildingId="all";
	$scope.floorId="all";
	$scope.householdId="all";
	$scope.questionType="all";
	$scope.questionPriority="all";
	$scope.questionStatus="all";
	$scope.buildingNumInfo=null;
	$scope.buildingDownInfo=null;
	var item = "";
	var quantity = "";
	var video = "";
	var question="";
	var paper="";
	/////按问题类型搜索
	$scope.setQuestionOfType = function(type,flag){
		$scope.questionType=type;
		$scope.getProjectQuestionList($scope.projectid,10,1,question);
	}
	/////按问题级别搜索
	$scope.setQuestionOfPriority = function(priority,flag){
		$scope.questionPriority=priority;
		$scope.getProjectQuestionList($scope.projectid,10,1,question);
	}
	/////按问题状态搜索
	$scope.setQuestionOfStatus = function(status,flag){
		$scope.questionStatus=status;
		$scope.getProjectQuestionList($scope.projectid,10,1,question);
	}
	/////按专业自动搜索
	$scope.setPhase = function(phase,flag) {
		$scope.phase = phase;
		if(flag=="构件信息页面"){
			$scope.getProjectItemList($scope.projectid,10,1,item);
		}
		if(flag=="工程量页面"){
			$scope.getProjectQuantityList($scope.projectid,10,1,quantity);
		}
		if(flag=="图纸信息页面"){
			$scope.getProjectPaperList($scope.projectid,10,1,paper);
		}
		if(flag=="安全交底页面"){
			$scope.getProjectVideoList($scope.projectid,10,1,video);
		}
		
	}
	/////按楼栋号自动搜索
	$scope.setBuildingNum = function(building,flag) {
		$scope.buildingId = building;	
		selectValue(building);
	   // $scope.getBuildingNum($scope.projectid,building);
		ProjectService.getBuildingNum($scope.projectid,building).then(function(result){
			 $scope.buildingNumInfo=result;  

			 ProjectService.getBuildingDown($scope.projectid,building).then(function(result){
				 $scope.buildingDownInfo=result;   
				 if(flag=="构件信息页面"){
					 $scope.getProjectItemList($scope.projectid,10,1,item);
				 }
				 if(flag=="工程量页面"){
					 $scope.getProjectQuantityList($scope.projectid,10,1,quantity);
				 }
				 if(flag="安全交底页面"){
					 $scope.getProjectVideoList($scope.projectid,10,1,video);
				 }
				 if(flag=="图纸信息页面"){
					 $scope.getProjectPaperList($scope.projectid,10,1,paper);
				 }
			    });
			 
	    });
	}
	/////按楼层号自动搜索
	$scope.setFloorNum = function(floor,flag) {
		$scope.floorId=floor;
		if(flag=="构件信息页面"){
			$scope.getProjectItemList($scope.projectid,10,1,item);
		}
		if(flag=="工程量页面"){
			$scope.getProjectQuantityList($scope.projectid,10,1,quantity);
		}
		if(flag=="图纸信息页面"){
			$scope.getProjectPaperList($scope.projectid,10,1,paper);
		}
	}
	//////按户型自动搜索
	$scope.setHouseholdNum = function(household,flag) {
		$scope.householdId=household;
		if(flag=="构件信息页面"){
			$scope.getProjectItemList($scope.projectid,10,1,item);
		}
		if(flag=="工程量页面"){
			$scope.getProjectQuantityList($scope.projectid,10,1,quantity);
		}
		if(flag=="图纸信息页面"){
			
		}
	}
	

	//////显示增加项目界面
	
	
	
	
	
	for(var i = 0 ; i < 100;i++) {
		  $scope.building[i] = "title";
		  $scope.floors[i] = "title";
	 }
	
	$scope.showProjectAdd = function(){
		$scope.findProjectInfo = {};
		document.getElementById("addProjectHtml").style.display = 'block';
	    document.getElementById("containers").style.display = 'none';
	    document.getElementById("include_header").style.display = 'none';
	    $scope.projectTitle="增加项目";
	    
	}
	//////隐藏增加项目界面
	$scope.hideaddprojectHtml=function(){
		document.getElementById("addProjectHtml").style.display = 'none';
	}
	//////显示更新项目界面
	$scope.projectChangeClick = function(projectId){
	    ProjectService.findProject(projectId,token).then(function(result){
	      $scope.findProjectInfo=result.data;
	      $scope.projectid=projectId;
	     
	      document.getElementById("projectInfoHtml").style.display = 'block';
	      document.getElementById("containers").style.display = 'none';
	      document.getElementById("include_header").style.display = 'none';
	      $scope.projectTitle="更新项目";
	    });
	    $scope.getBuildingList(projectId);
	    //	    $scope.getBuildingDown(projectId);
	    //	    $scope.buildingArray=menuArray(buildingInfo.buildingNum);
	 }
	////////分页回调函数
	  $scope.projectPage = function(iPageCount,iCurrent) {
		  $("#projectPageCode").remove();
		  $("#table-buton1").append("<div id=\"projectPageCode\"></div>");
		  
		  $("#projectPageCode").createPage({
			  
		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  console.log($scope.ProjectTofind);
		    	  $scope.getProjectList(pageSize,p,$scope.ProjectTofind);
		    	  
		      }

		  });
	  }
	  
	  ///////分页获取项目列表
	$scope.getProjectList = function(pageSize,pageIndex,project) {
		  ProjectService.getProjectList(pageSize,pageIndex,project).then(function (result){
		  	  $scope.projectList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.projectPage($scope.totalPage,$scope.currentPage);
		  });
	  }
	  
	  
	  ////显示更新项目界面
	 $scope.ProjectChangeClick = function(ProjectId){
	    ProjectService.findProject(ProjectId,token).then(function(result){
	      $scope.findProjectInfo=result.data;
	      document.getElementById("projectInfoHtml").style.display = 'block';
	      $scope.title="更新项目";
	    });
	 }
	 
	  
	 /////删除项目
	 $scope.deleteProject = function(projectid){
		  	ProjectService.deleteProject(projectid,token).then(function(result){
		       $scope.deleteProjectInfo=result.data;
		       $scope.getProjectList(pageSize,1,$scope.ProjectTofind);
		       
		    });
	 }
	 /////返回项目列表
	 $scope.returnProjectlist = function(){
		 	var project={};
	       document.getElementById("addProjectHtml").style.display = 'none';
	 }
	 //////重置添加项目信息
	 $scope.resetProject = function(){
		 $scope.findProjectInfo = {};
	 }
	 
	 

	 /////增加项目
	 $scope.addProjectByAdmin = function(){
		 if($scope.projectTitle=="增加项目"){
			 findProjectInfo = {};
			 findProjectInfo=$scope.findProjectInfo;
			 ProjectService.addProjectByAdmin(findProjectInfo,token).then(function(result){
			       $scope.addProjectByAdminInfo=result.data;
			       $scope.getProjectList(pageSize,1,$scope.ProjectTofind);
			       
			    });
		 }
		 if($scope.projectTitle=="更新项目")
		{
			 alert($scope.test);
			 var formData = new FormData();
			 for (var key in $scope.findProjectInfo) {
				   if($scope.findProjectInfo[key] != null) {
					   formData.append(key, $scope.findProjectInfo[key]);
				   }
			 }
	

			 if($scope.modelFiles == undefined || $scope.modelFiles == null) {
				 formData.append('modelFile',null);
			 } else {
				 formData.append('modelFile',$scope.modelFiles);
			 }
			 if($scope.picFiles == undefined || $scope.picFiles == null) {
				 formData.append('picFile',null);
			 } else {
				 formData.append('picFile',$scope.picFiles);
			 }
			 

			 ProjectService.updateProject(formData,token).then(function(result){
			       $scope.updateProjectInfo=result.data;
			       $scope.getProjectList(pageSize,1,$scope.ProjectTofind);
			    });
		}
		 
	 }
	 ////////模糊查找项目
	 $scope.projectFind = function() {
		 $scope.currentPage = 1;
		 $scope.getProjectList(pageSize,$scope.currentPage,$scope.ProjectTofind);
	 }
	 $scope.getProjectList(pageSize,$scope.currentPage,$scope.ProjectTofind);

	 $scope.projectInfo = function(){
		 console.log("test");
		 alert("test");
	 }
	 //////////////////////////////项目详情信息
	 $scope.projectInfoHead=[{name:"基本信息"},{name:"构件信息"},{name:"图纸信息"},{name:"工程量信息"},{name:"安全技术交底"},{name:"问题列表"}];
	 $scope.projectPhaseInfo=[{name:"电气"},{name:"暖通"},{name:"给排水"},{name:"消防"}];
	 $scope.projectHouseholdInfo=[{name:"A型"},{name:"B型"},{name:"C型"},{name:"D型"},{name:"E型"}];
	 $scope.projectQuestionOfType=[{name:"安全"},{name:"质量"},{name:"其他"}];
	 $scope.projectQuestionOfPriority=[{name:"一般"},{name:"重要"},{name:"紧急"}];
	 $scope.projectQuestionOfStatus=[{name:"待解决"},{name:"已解决"}];
	 /////////////////////////
	 /*
	  * 菜单的选择操作
	  * 
	  * 
	 */
	 ////////////////////////
	 /*获取该项目的楼栋信息*/
	 $scope.getBuildingList = function(projectId){
		 ProjectService.getBuildingList(projectId).then(function(result){
		       $scope.buildingInfo=result.data;    
		    });
	 }
	 /*获取该项目的楼层信息*/
	 $scope.getBuildingNum = function(projectId,buildingId){
		 ProjectService.getBuildingNum(projectId,buildingId).then(function(result){
			 $scope.buildingNumInfo=result;    
		    });
	 }
	 /*获取该项目的楼层地下层信息*/
	 $scope.getBuildingDown = function(projectId,buildingId){
		 ProjectService.getBuildingDown(projectId,buildingId).then(function(result){
			 $scope.buildingDownInfo=result;    
		    });
	 }
	 function menuArray(total)
	 {
		var sizePerPage = 10;
		var totalPage = Math.ceil(total/sizePerPage);
		var arr = new Array();
		for(var i = 0; i < totalPage; i++)
		{
			
			arr[i] = new Array();
			for(var j = 0; j < 10; j++) 
			{
				if(i*10+(j+1) <= total) 
				{
					arr[i][j] = i*10+(j+1);
				} else 
				{
					break;
				}
			}
		}
		console.log(arr);
		return arr;
	 }

	 /////////////////
	 /*
	  * 
	  * 构件信息操作
	  * 
	  * 
	  * 
	 */
	 ////////////////////////构件信息分页获取
	 $scope.getProjectItemList = function(projectId,pageSize,pageIndex,item) {
		  
		  $scope.buildingArray=menuArray($scope.buildingInfo.buildingNum);
		 
		  $scope.buildingDownArray=menuArray($scope.buildingDownInfo);
		  $scope.floorArray=menuArray($scope.buildingNumInfo-$scope.buildingDownInfo-1);
		 if($scope.phase!="all") {
			 item+= "&professionType=" + $scope.phase;
		 }
		 if($scope.buildingId!="all"){
			 item+= "&buildingNum=" + $scope.buildingId;
		 }
		 if($scope.floorId!="all"){
			 item+= "&floorNum=" + $scope.floorId;
		 }
		 if($scope.householdId!="all"){
			 item+= "&householdNum=" + $scope.householdId;
		 }
		  ProjectService.getItemList(projectId,pageSize,pageIndex,item).then(function (result){
		  	  $scope.projectItemList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.itemPage($scope.totalPage,$scope.currentPage);
		  });
	  }
	 ////////////////////////初始化构件信息分页获取
	 $scope.InitProjectItemList = function(projectId,pageSize,pageIndex) {
		  
		  $scope.buildingArray=menuArray($scope.buildingInfo.buildingNum);
		  ProjectService.getItemList(projectId,pageSize,pageIndex,item).then(function (result){
		  	  $scope.projectItemList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.itemPage($scope.totalPage,$scope.currentPage);
		  });
	  }
	  ////////////////构件信息查看
	 $scope.itemChangeClick= function(itemId){
		 ProjectService.getItemById($scope.projectid,itemId).then(function (result){
		  	  $scope.itemDetailInfo = result.data;
		  });
	 }
	 ///////////////构件信息删除
	 
	 
	  ////////分页回调函数
	  $scope.itemPage = function(iPageCount,iCurrent) {
		  $("#itemPageCode").remove();
		  $("#table-buton").append("<div id=\"itemPageCode\"></div>");
		  $("#itemPageCode").createPage({

		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  $scope.getProjectItemList($scope.projectid,pageSize,p,item);
		      }
		  });
	  }
	 
	  /////////////////
	 /*
	  * 
	  * 图纸列表信息操作
	  * 
	  * 
	  * 
	 */
	  ////////图纸分页回调函数
	  $scope.paperPage = function(iPageCount,iCurrent) {
		  $("#paperPageCode").remove();
		  $("#table-buton6").append("<div id=\"paperPageCode\"></div>");
		  $("#paperPageCode").createPage({

		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  $scope.getProjectPaperList($scope.projectid,pageSize,p,paper);
		      }
		  });
	  }
	 ////////////////////////图纸列表信息分页获取
	 $scope.getProjectPaperList = function(projectId,pageSize,pageIndex,paper) {
		 $scope.buildingArray=menuArray($scope.buildingInfo.buildingNum);
		 
		  $scope.buildingDownArray=menuArray($scope.buildingDownInfo);
		  $scope.floorArray=menuArray($scope.buildingNumInfo-$scope.buildingDownInfo-1);
		 if($scope.phase!="all") {
			 paper+= "professionType=" + $scope.phase;
		 }
		 if($scope.buildingId!="all"){
			 paper+= "&buildingNum=" + $scope.buildingId;
		 }
		 if($scope.floorId!="all"){
			 paper+= "&floorNum=" + $scope.floorId;
		 }
		  ProjectService.getPaperList(projectId,pageSize,pageIndex,paper).then(function (result){
		  	  $scope.projectPaperList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.paperPage($scope.totalPage,$scope.currentPage);
		  });
	  }
	 /////////////////
	 /*
	  * 
	  * 工程量信息操作
	  * 
	  * 
	  * 
	 */
	 ////////////////////////工程量信息分页获取
	 $scope.getProjectQuantityList = function(projectId,pageSize,pageIndex,quantity) {
		
		  $scope.buildingArray=menuArray($scope.buildingInfo.buildingNum);
		  $scope.floorArray=menuArray($scope.buildingNumInfo);
		  $scope.buildingDownArray=menuArray($scope.buildingDownInfo);
		 if($scope.phase!="all") {
			 quantity+= "professionType=" + $scope.phase;
		 }
		 if($scope.buildingId!="all"){
			 quantity+= "&buildingNum=" + $scope.buildingId;
		 }
		 if($scope.floorId!="all"){
			 quantity+= "&floorNum=" + $scope.floorId;
		 }
		 if($scope.householdId!="all"){
			 quantity+= "&householdNum=" + $scope.householdId;
		 }
		  ProjectService.getQuantityList($scope.projectid,pageSize,pageIndex,quantity).then(function (result){
		  	  $scope.projectQuantityList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.quantityPage($scope.totalPage,$scope.currentPage);
		  });
	  }
	 /////////////////
	 ////////工程量分页回调函数
	  $scope.quantityPage = function(iPageCount,iCurrent) {
		  $("#quantityPageCode").remove();
		  $("#table-buton3").append("<div id=\"quantityPageCode\"></div>");
		  $("#quantityPageCode").createPage({

		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  $scope.getProjectQuantityList($scope.projectid,pageSize,p,quantity);
		      }
		  });
	  }
	  ////////问题分页回调函数
	  $scope.questionPage = function(iPageCount,iCurrent) {
		  $("#questionPageCode").remove();
		  $("#table-buton4").append("<div id=\"questionPageCode\"></div>");
		  $("#questionPageCode").createPage({

		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  $scope.getProjectQuestionList($scope.projectid,pageSize,p,question);
		      }
		  });
	  }
	 /*
	  * 
	  * 安全技术交底操作
	  * 
	  * 
	  * 
	 */
	 ////////////////////////安全交底分页获取
	  ////////交底分页回调函数
	  $scope.videoPage = function(iPageCount,iCurrent) {
		  $("#videoPageCode").remove();
		  $("#table-buton5").append("<div id=\"videoPageCode\"></div>");
		  $("#videoPageCode").createPage({

		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  $scope.getProjectVideoList($scope.projectid,pageSize,p,video);
		      }
		  });
	  }
	  //////交底分页获取
	 $scope.getProjectVideoList = function(pageSize,pageIndex,video) {
		 
		 $scope.buildingArray=menuArray($scope.buildingInfo.buildingNum);
		 if($scope.phase!="all") {
			 video+= "professionType=" + $scope.phase;
		 }
		 if($scope.buildingId!="all"){
			 video+= "&buildingNum=" + $scope.buildingId;
		 }
		  ProjectService.getVideoList(pageSize,pageIndex,video).then(function (result){
		  	  $scope.videoList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.videoPage($scope.totalPage,$scope.currentPage);
		  });
	  }
	 /////////////////
	 /*
	  * 
	  * 问题列表操作
	  * 
	  * 
	  * 
	 */
	 ////////////////////////问题列表分页获取
	 $scope.getProjectQuestionList = function(pageSize,pageIndex,question) {
		 if($scope.questionType!="all") {
			 question+= "questionType=" + $scope.questionType;
		 }
		 if($scope.questionPriority!="all"){
			 question+= "&priority=" + $scope.questionPriority;
		 }
		 if($scope.questionStatus!="all"){
			 question+= "&state=" + $scope.questionStatus;
		 }
		  ProjectService.getQuestionList($scope.projectid,pageSize,pageIndex,question).then(function (result){
		  	  $scope.projectQuestionList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.questionPage($scope.totalPage,$scope.currentPage);
		  });
	  }
	 /////删除问题
	 $scope.deleteQuestion = function(questionid){
		  	ProjectService.deleteQuestion(questionid,$scope.projectid).then(function(result){
		       $scope.deleteQuestionInfo=result.data;
		       $scope.getProjectQuestionList(pageSize,1,question);
		       
		    });
	 }
	 
	 $scope.projectInfoChange = function(projectType){
		 if(projectType == "基本信息"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'block';
			 document.getElementById("projectItemInfoHtml").style.display = 'none';
			 document.getElementById("projectPaperInfoHtml").style.display = 'none';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'none';
			 document.getElementById("projectVideoInfoHtml").style.display = 'none';
			 document.getElementById("projectQuestionInfo").style.display = 'none';
		 }
		 if(projectType == "构件信息"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'none';
			 document.getElementById("projectItemInfoHtml").style.display = 'block';
			 document.getElementById("projectPaperInfoHtml").style.display = 'none';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'none';
			 document.getElementById("projectVideoInfoHtml").style.display = 'none';
			 document.getElementById("projectQuestionInfo").style.display = 'none';
			 var item=null;
			 $scope.InitProjectItemList($scope.findProjectInfo.id,pageSize,1,item);
			
			 
		 }
		 if(projectType == "图纸信息"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'none';
			 document.getElementById("projectItemInfoHtml").style.display = 'none';
			 document.getElementById("projectPaperInfoHtml").style.display = 'block';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'none';
			 document.getElementById("projectVideoInfoHtml").style.display = 'none';
			 document.getElementById("projectQuestionInfo").style.display = 'none';
			 var paper=null;
			 $scope.getProjectPaperList($scope.findProjectInfo.id,pageSize,1,paper);
		 }
		 if(projectType == "工程量信息"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'none';
			 document.getElementById("projectItemInfoHtml").style.display = 'none';
			 document.getElementById("projectPaperInfoHtml").style.display = 'none';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'block';
			 document.getElementById("projectVideoInfoHtml").style.display = 'none';
			 document.getElementById("projectQuestionInfo").style.display = 'none';
			 var quantity=null;
			 $scope.getProjectQuantityList($scope.findProjectInfo.id,pageSize,1,quantity);
		 }
		 if(projectType == "安全技术交底"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'none';
			 document.getElementById("projectItemInfoHtml").style.display = 'none';
			 document.getElementById("projectPaperInfoHtml").style.display = 'none';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'none';
			 document.getElementById("projectVideoInfoHtml").style.display = 'block';
			 document.getElementById("projectQuestionInfo").style.display = 'none';
			 var video=null;
			 $scope.getProjectVideoList($scope.findProjectInfo.id,pageSize,1,video);
		 }
		 if(projectType == "问题列表"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'none';
			 document.getElementById("projectItemInfoHtml").style.display = 'none';
			 document.getElementById("projectPaperInfoHtml").style.display = 'none';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'none';
			 document.getElementById("projectVideoInfoHtml").style.display = 'none';
			 document.getElementById("projectQuestionInfo").style.display = 'block';
			 var question=null;
			 $scope.getProjectQuestionList(pageSize,1,question);
		 }
	 }
	 $scope.uploadItemFile=function(){
		 
		 //电缆桥架
		 fileArray[0]=document.getElementById("qiaojia").files[0];	
		 //电缆桥架配件
		 fileArray[1]=document.getElementById("qiaojia_fujian").files[0];	  
		 //电气设备
		 fileArray[2]=document.getElementById("dianqi_shebei").value;	  
		 //风管	
		 fileArray[3]=document.getElementById("fengguan").value;			  
		 //风管配件
		 fileArray[4]=document.getElementById("fengguan_peijian").value;			 
		 //风管附件
		 fileArray[5]=document.getElementById("fengguan_fujian").value;			  
		 //风管末端
		 fileArray[6]=document.getElementById("fengguan_moduan").value;			  
		 //机械设备
		 fileArray[7]=document.getElementById("jixie_shebei").value;			  
		 //管道
		 fileArray[8]=document.getElementById("guandao").value;			  
		 //管件
		 fileArray[9]=document.getElementById("guanjian").value;			  
		 //管道附件
		 fileArray[10]=document.getElementById("guandao_fujian").value;			  
		 //卫浴装置
		 fileArray[11]=document.getElementById("weiyu_zhuangzhi").value;			  
		 //消防栓
		 fileArray[12]=document.getElementById("xiaofangshuan").value;			  
		 //喷淋
		 fileArray[13]=document.getElementById("penlin").value;			 
		 //工程量
		 fileArray[14]=document.getElementById("gongchengliang").value;	
//		 console.log(typeof(fileArray));
		 var formData = new FormData();
		 formData.append("fileList",fileArray[0]);
		 formData.append("fileList",fileArray[1]);
		 ProjectService.uploadItemFile(formData).then(function(result){
		       $scope.uploadItemInfo=result.data;
		     
		       
		    });
	 }
	 
}