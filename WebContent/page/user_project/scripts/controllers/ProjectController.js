
    ////////////////////////////
var index;
var projectss;
function projectInfo(){
	document.getElementById("include_header").style.display = 'none';
	document.getElementById("containers").style.display = 'none';
	document.getElementById("projectInfoHtml").style.display = 'block';
}
//function selectValue(index){
//	var obj=$(".myselect");
//	for(i=0;i<obj.length;i++){
//	        obj[i].selected = false;
//	}
//	obj[index].selected=true;
//}

function setModel(model) {
	angular.element(model).scope().$parent.$parent.modelFiles = model.files[0];
}
function setPic(pic) {
	angular.element(pic).scope().$parent.$parent.picFiles = pic.files[0];
}

////////////////////////////////
function ProjectController($scope,ProjectService) {
	$scope.a = 1;
	
	console.log("载入ProjectController");
	$scope.currentPage = 1;
	$scope.flagAll=-1;
	$scope.selectedFloor="";
	$scope.selectedBuilding="";
	$scope.ProjectTofind = {};
	$scope.findProjectInfo = {};
	var buildingInfo = {};
	$scope.quantityUrl="";
	$scope.building = [];
	$scope.floors =[];
	$scope.buildings=[];
	$scope.professionType=-2;
	$scope.buildingVideoB=[];
	$scope.buildingVideoF=[];
	$scope.buildingN=[];
	$scope.uploadVideoFiles="";
	$scope.uploadPaperFiles="";
	var videoTypeList=[];
	$scope.videofiles=[];
	var videoTypeList="";
	$scope.addVideoInfo={};
	$scope.floorNumArrays=[];
	$scope.buildingNumArrays=[];
	$scope.showOr=[];
	var progressBar;
    var percentageDiv;
    var uploadTime;
    
	
	////////////////
	var itemName="";
	var quantityName="";
	var paperName="";
	var videoName="";
	var questionName="";
	///////////////
	var introduced=null;
	var projectId=null;
	$scope.number=null;
	$scope.numbers=null;
	$scope.quantityTypeTitles=["模型工程量","预算工程量"];
	$scope.projectTitles=["序号","项目名称","施工单位","负责人","施工地点","施工时间","施工周期","操作"];
	$scope.itemTitles=["序号","构件名称","底部高程","系统类型","尺寸","长度","设备类型","所属类别","标高","偏移量","面积","材质","类型名"];
	$scope.quantityTitles=["序号","构件名称","系统类型","数值","单位","设备类型","尺寸","设备名称","材质"];
	$scope.quantityTitlesFind=["专业","楼栋号","楼层号","户型"];
	$scope.questionTitles=["序号","问题类型","问题提交人","问题标题","专业","问题创建时间","问题等级","问题状态"];
	$scope.videoTitles=["序号","交底地址","楼栋号","专业","操作"];
	$scope.paperTitles=["序号","图纸信息","楼栋号","楼层","专业","操作"];
	///////////////
	$scope.flag=["工程量页面","构件信息页面","图纸信息页面","问题列表页面","安全交底页面"];
	$scope.quantity_phase="全部";
	$scope.video_phase="全部";
	$scope.paper_phase="全部";
	$scope.paper_building_num="全部";
	$scope.paper_floor="全部";
	$scope.item_phase="全部";
	$scope.question_phase="全部";
	$scope.paper_phase="全部";
	$scope.quantity="全部";
	$scope.paper="全部";
	$scope.video="全部";
	$scope.question="全部";
	$scope.questionPo="全部";
	
	/////////////////////
	$scope.name="all";
	$scope.test="title";
	$scope.phases="alls";
	$scope.buildingId="all";
	$scope.buildingIds="alls";
	$scope.floorId="all";
	$scope.floorIds="alls";
	$scope.householdId="all";
	$scope.householdIds="alls";
	$scope.questionType="全部";
	$scope.questionPriority="全部";
	$scope.questionStatus="全部";
	$scope.buildingNumArray=null;
	$scope.floorNumArray=null;
	$scope.buildingNumInfo=null;
	$scope.buildingDownInfo=null;
	$scope.videoTypes="";
	$scope.addVideoInfo={};
	$scope.video_buildingNum="全部";
	$scope.fileTypes="";
	var item = "";
	var quantity = "";
	var video = "";
	var question="";
	var paper="";
	var project="";
	$scope.findLike=function(){
		var content=document.getElementById("project_search").value;
		$scope.getProjectListLike(10,-1,project,content);
	}
	$scope.getProjectListLike = function(pageSize,pageIndex,project,content) {
		  ProjectService.getProjectLists(pageSize,pageIndex,project,content).then(function (result){
		  	  $scope.projectList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.projectPage($scope.totalPage,$scope.currentPage);
		  });
	}
	 $scope.setVideoType = function(index){
    	 $scope.videoTypes=index.videoTypes;
     }
     $scope.setFileType = function(index){
    	 $scope.fileTypes=index.videoFiles;
     }
     //////重置问题搜索
     $scope.resetProject = function(){
    	 $scope.ProjectTofind = {};
     }
     
	
	//////问题状态搜索
	$scope.setQuestionState = function(index){
		$scope.questionStatus="全部";
		$scope.questionStatus = index.questionStatus;
		for(var i=0;i<$scope.projectQuestionOfStatus.length;i++){
			if($scope.questionStatus==$scope.projectQuestionOfStatus[i].name){
				$scope.questionStatus=i;
			}
		}
		$scope.getProjectQuestionList($scope.findprojectId,10,1,question);
	}
	/////图纸按照楼栋搜索
	$scope.setBuildingNum = function(index,flag){
		if(flag=="图纸信息页面"){
			$scope.paper_building_num = index.paper_building_num;
			for(var i=0;i<$scope.paperBuildingNum.length;i++){
				if($scope.paper_building_num==$scope.paperBuildingNum[i].name){
					$scope.paper_building_num=i+1;
				}
			}
			$scope.getProjectPaperList($scope.findprojectId,10,1,paper);
		}
	}
/////图纸按照楼层搜索
	$scope.setFloorNum = function(index,flag){
		if(flag=="图纸信息页面"){
			$scope.paper_floor = index.paper_floor;
			for(var i=0;i<$scope.paperFloorNum.length;i++){
				if($scope.paper_floor==$scope.paperFloorNum[i].name){
					$scope.paper_floor=i+1;
				}
			}
			$scope.getProjectPaperList($scope.findprojectId,10,1,paper);
		}
	}
	/////按专业自动搜索
	$scope.setPhase = function(index,flag) {
		
		if(flag=="构件信息页面"){
			$scope.professionType=-2;
			$scope.item_phase = index.item_phase;
			for(var i=0;i<$scope.projectPhaseInfo.length;i++){
				if($scope.item_phase==$scope.projectPhaseInfo[i].name){
					$scope.professionType=i;
				}
			}
			$scope.getProjectItemList($scope.findprojectId,10,1,item);
		}
	    if(flag=="工程量页面"){
	    	$scope.professionType=-2;
	    	$scope.quantity_phase = index.quantity_phase;
			for(var i=0;i<$scope.projectPhaseInfo.length;i++){
				if($scope.quantity_phase==$scope.projectPhaseInfo[i].name){
					$scope.professionType=i;
				}
			}
			$scope.getProjectQuantityList($scope.findprojectId,10,1,quantity);
		}
		if(flag=="图纸信息页面"){
			$scope.professionType=-2;
			$scope.paper_phase = index.paper_phase;
			for(var i=0;i<$scope.projectPhaseInfo.length;i++){
				if($scope.paper_phase==$scope.projectPhaseInfo[i].name){
					$scope.professionType=i;
				}
			}
			$scope.getProjectPaperList($scope.findprojectId,10,1,paper);
		}
		if(flag=="安全交底页面"){
			$scope.professionType=-2;
			$scope.video_phase = index.video_phase;
			//$scope.buildingNum=$scope.video_buildingNum;
			for(var i=0;i<$scope.projectQuestionType.length;i++){
				if($scope.video_phase==$scope.projectQuestionType[i].name){
					$scope.professionType=i;
				}
			}
			$scope.getProjectVideoList($scope.findprojectId,10,1,video);
		}
		if(flag=="问题列表页面"){
			$scope.questionType="全部";
			$scope.question_phase = index.question_phase;
			for(var i=0;i<$scope.projectQuestionOfType.length;i++){
				if($scope.question_phase==$scope.projectQuestionOfType[i].name){
					$scope.questionType=i;
				}
			}
			$scope.getProjectQuestionList($scope.findprojectId,10,1,question);
		}
		
	}
	
	
		
		
		
		
		
	
	
	
	////////通过构件名称搜索构件
	$scope.findItemLike = function(){
		itemName=document.getElementById("project_item_search").value;
		$scope.getProjectItemList($scope.findprojectId,10,1,item);
	}
	////////通过构件名称搜索工程量
	$scope.findQuantityLike = function(){
		quantityName=document.getElementById("project_quantity_search").value;
		$scope.getProjectQuantityList($scope.findprojectId,10,1,quantity);
	}
	////////通过图纸名称搜索
	$scope.findPaperLike = function(){
		paperName=document.getElementById("project_paper_search").value;
		$scope.getProjectPaperList($scope.findprojectId,10,1,paper);
	}
	$scope.findVideoLike = function(){
		videoName=document.getElementById("project_video_search").value;
		$scope.getProjectVideoList($scope.findprojectId,10,1,video);
	}
	$scope.findQuestionLike = function(){
		questionName=document.getElementById("project_question_search").value;
		$scope.getProjectQuestionList($scope.findprojectId,10,1,video);
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
	 $scope.projectChange = function(projectId){
		
	    ProjectService.findProject(projectId,token).then(function(result){
	      $scope.findProjectInfo=result.data;
	      $scope.findprojectId=$scope.findProjectInfo.id;
	      document.getElementById("projectInfoHtml").style.display = 'block';
	      document.getElementById("projectContent").style.display = "none";
	      $scope.title="更新项目";
	      
	    });
	    $scope.getBuildingList(projectId);
	 }
	 
	 
	  
	 /////删除项目
	 $scope.deleteProject = function(projectid){
		 if(confirm("确定删除？")) {  
		  	ProjectService.deleteProject(projectid,token).then(function(result){
		       $scope.deleteProjectInfo=result.data;
		       $scope.getProjectList(pageSize,1,$scope.ProjectTofind);
		       
		    });
		 }
	 }
	 /////返回项目列表
	 $scope.returnProjectlist = function(){
		 	var project={};
	       document.getElementById("addProjectHtml").style.display = 'none';
	 }
	 //////重置添加项目信息
	 $scope.resetProject = function(){
		 $scope.ProjectTofind = {};
	 }
	 
	 

	
	 ////////模糊查找项目
	 $scope.projectFind = function() {
		 $scope.currentPage = 1;
		 $scope.getProjectList(pageSize,$scope.currentPage,$scope.ProjectTofind);
	 }
	 //////初始化获取项目列表
	 $scope.getProjectList(pageSize,$scope.currentPage,$scope.ProjectTofind);
	 
	
	 //////////////////////////////项目详情信息
	 $scope.projectInfoHead=[{name:"基本信息"},{name:"构件信息"},{name:"图纸信息"},{name:"工程量信息"},{name:"安全技术交底"},{name:"问题列表"}];
	 $scope.projectPhaseInfo=[{name:"电气"},{name:"暖通"},{name:"给排水"},{name:"消防"}];
	 $scope.projectPhaseInfos=[{name:"电气"},{name:"暖通"},{name:"给排水"},{name:"消防"},{name:"建筑"},{name:"装饰"},{name:"结构"}];
	 $scope.projectQuestionType=[{name:"质量"},{name:"安全"},{name:"技术"}];
	 $scope.projectHouseholdInfo=[{name:"公共部位"},{name:"N户型"},{name:"Q户型"},{name:"Q户型反"},{name:"N户型反"}];
	 $scope.projectQuestionOfType=[{name:"安全"},{name:"质量"},{name:"其他"}];
	 $scope.projectQuestionOfPriority=[{name:"一般"},{name:"重要"},{name:"紧急"}];
	 $scope.projectQuestionOfStatus=[{name:"待解决"},{name:"已解决"}];
	 $scope.paperBuildingNum=["1#","2#","3#","4#","5#","6#","7#","8#","9#","10#","11#","12#","13#","14#","15#","16#","17#","18#","19#","20#"];
	 $scope.paperFloorNum=["1F","2F","3F","4F","5F","6F","7F","8F","9F","10F","11F","12F","13F","14F","15F","16F","17F","18F","19F","20F"];
	 /////////////////////////
	 /*
	  * 菜单的选择操作
	  * 
	  * 
	 */
	 ////////////////////////
	 /*获取该项目的楼栋信息*/
	 $scope.getBuildingList = function(projectId){
		 ProjectService.getBuildingList($scope.findprojectId).then(function(result){
		       $scope.buildingInfo=result.data;    
		    });
	 }
	 /*获取该项目的楼层信息*/
	 $scope.getBuildingNum = function(projectId,buildingId){
		 ProjectService.getBuildingNum($scope.findprojectId,buildingId).then(function(result){
			 $scope.buildingNumInfo=result;    
		    });
	 }
	 /*获取该项目的楼层地下层信息*/
	 $scope.getBuildingDown = function(projectId,buildingId){
		 ProjectService.getBuildingDown($scope.findprojectId,buildingId).then(function(result){
			 $scope.buildingDownInfo=result;    
		    });
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
		 
		 /*if($scope.phase!="all") {
			 item+= "&professionType=" + $scope.phase;
		 }*/
		 if(itemName!=""){
			 item+= "&name=" +itemName;
		 }
		 
		 if($scope.item_phase=="全部"){
			 //item+= "&professionType=" + $scope.flagAll;
		 }else if($scope.professionType!=-2){
			 item+= "&professionType=" + $scope.professionType;
		 }
		 
		/* if($scope.buildingId!="all"){
			 item+= "&buildingNum=" + $scope.buildingId;
		 }
		 if($scope.buildingId=="all"){
			 item+= "&buildingNum=" + $scope.flagAll;
		 }
		 
		 if($scope.floorId!="all"){
			 item+= "&floorNum=" + $scope.floorId;
		 }
		 if($scope.floorId=="all"){
			 item+= "&floorNum=" + $scope.flagAll;
		 }
		 if($scope.householdId!="all"){
			 item+= "&householdNum=" + $scope.householdId;
		 }
		 if($scope.householdId=="all"){
			 item+= "&householdNum=" + $scope.flagAll;
		 }*/
		  ProjectService.getItemList($scope.findprojectId,pageSize,pageIndex,item).then(function (result){
		  	  $scope.projectItemList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.itemPage($scope.totalPage,$scope.currentPage);
		  });
	  }
	 ////////////////////////初始化构件信息分页获取
	 $scope.InitProjectItemList = function(projectId,pageSize,pageIndex) {
		  ProjectService.getItemList($scope.findprojectId,pageSize,pageIndex,item).then(function (result){
		  	  $scope.projectItemList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.itemPage($scope.totalPage,$scope.currentPage);
		  });
	  }
	  ////////////////构件信息查看
	 $scope.itemChangeClick= function(itemId){
		 ProjectService.getItemById($scope.findprojectId,itemId).then(function (result){
		  	  $scope.itemDetailInfo = result.data;
		  });
	 }
	 ///////////////构件信息删除
	 
	 
	  ////////分页回调函数
	  $scope.itemPage = function(iPageCount,iCurrent) {
		  $("#itemPageCode").remove();
		  $("#table-buton2").append("<div id=\"itemPageCode\"></div>");
		  $("#itemPageCode").createPage({

		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  $scope.getProjectItemList($scope.findprojectId,pageSize,p,item);
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
		    	  $scope.getProjectPaperList($scope.findprojectId,pageSize,p,paper);
		      }
		  });
	  }
	  //////////////图纸信息删除功能
	  $scope.deletePaper=function(paperId){
		  if(confirm("确定删除？")) {  
			  ProjectService.deleteProjectPaper(paperId).then(function(result){
			       $scope.deleteProjectPaperInfo=result.data;
			       $scope.getProjectPaperList($scope.findprojectId,10,1,null);
			    });
		  }
	  }
	 ////////////////////////图纸列表信息分页获取
	 $scope.getProjectPaperList = function(projectId,pageSize,pageIndex,paper) {
		 
		 if(paperName!=""){
			 paper+= "content=" + paperName;
		 }
		 if($scope.paper_building_num=="全部"){
			 
		 }else{
			 paper+= "&buildingNum=" + $scope.paper_building_num;
		 }
		 if($scope.paper_floor=="全部"){
			 
		 }else{
			 paper+= "&floorNum=" +$scope.paper_floor;
		 }
		 if($scope.paper_phase=="全部"){
			 //paper+= "&professionType=" + $scope.flagAll;
		 }else if($scope.professionType!=-2){
			 paper+= "&professionType=" + $scope.professionType;
		 }
		  ProjectService.getPaperList($scope.findprojectId,pageSize,pageIndex,paper).then(function (result){
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
		 
		
		 
		 
		 
		 if(quantityName!=""){
			 quantity+= "name=" + quantityName;
		 }
		 if($scope.quantity_phase=="全部"){
			 quantity+= "&professionType=" + $scope.flagAll;
		 }else if($scope.professionType!=-2){
			 quantity+= "&professionType=" + $scope.professionType;
		 }
		  ProjectService.getQuantityList($scope.findprojectId,pageSize,pageIndex,quantity).then(function (result){
		  	  $scope.projectQuantityList = result.data;
		  	  $scope.getProjectQuantityExcel();
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
		    	  $scope.getProjectQuantityList($scope.findprojectId,pageSize,p,quantity);
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
		    	  $scope.getProjectQuestionList($scope.findprojectId,pageSize,p,question);
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
		    	  $scope.getProjectVideoList($scope.findprojectId,pageSize,p,video);
		      }
		  });
	  }
	  //////交底分页获取
	 $scope.getProjectVideoList = function(projectId,pageSize,pageIndex,video) {
		 if(videoName!=""){
			 video+= "originName=" + videoName;
		 }
		 if($scope.video_phase=="全部"){
			 //video+= "&professionType=" + $scope.flagAll;
		 }else if($scope.professionType!=-2){
			 video+= "&professionType=" + $scope.professionType;
		 }
		 if($scope.video_buildingNum=="全部"){
			 
		 }else{
			 video+= "&buildingNum=" + $scope.video_buildingNum;
		 }
		  ProjectService.getVideoList($scope.findprojectId,pageSize,pageIndex,video).then(function (result){
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
	 $scope.getProjectQuestionList = function(projectId,pageSize,pageIndex,question) {
		 if(questionName!=""){
			 question+= "content=" + questionName;
		 }
		
		 if($scope.questionType!="全部") {
			 question+= "questionType=" + $scope.questionType;
		 }
		 if($scope.questionPriority!="全部"){
			 question+= "&priority=" + $scope.questionPriority;
		 }
		 if($scope.questionStatus!="全部"){
			 question+= "&state=" + $scope.questionStatus;
		 }
		  ProjectService.getQuestionList($scope.findprojectId,pageSize,pageIndex,question).then(function (result){
		  	  $scope.projectQuestionList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.questionPage($scope.totalPage,$scope.currentPage);
		  });
	  }
	 /////删除问题
	 $scope.deleteQuestion = function(questionid){
		 if(confirm("确定删除？")) {  
		  	ProjectService.deleteQuestion(questionid,$scope.projectid).then(function(result){
		       $scope.deleteQuestionInfo=result.data;
		       $scope.getProjectQuestionList(pageSize,1,question);
		       
		    });
		 }
	 }
	 /////导出工程量
	 $scope.getProjectQuantityExcel=function(){
		 ProjectService.getProjectQuantityExcel($scope.findprojectId).then(function (result){
		  	  $scope.quantityUrl = result.data;
		  });
	 }

	
	
	 ///////图纸上传
	 $scope.uploadPaperFile=function(){
		 var building_val=$scope.addPaperInfo.buildingNum;
		 var floor_val=$scope.addPaperInfo.floorNum;
		 if(building_val!=undefined && floor_val!=undefined){
			 var formData = new FormData();
			 if($scope.constructionfiles!=null && $scope.constructionfiles!=undefined && $scope.constructionfiles!=""){
				 for(var i=0;i<$scope.constructionfiles.length;i++){
					 formData.append("fileList",$scope.constructionfiles[i]);
				 }
				 
			 }
			 if($scope.professionType!=null && $scope.professionType!=undefined && $scope.professionType!=""){
				 formData.append("professionType",$scope.addPaperInfo.professionType);
			 }
			 ProjectService.uploadPaperFile(formData,building_val,floor_val,$scope.findprojectId).then(function(result){
				       $scope.uploadPaperFiles=result.data;	 
				       $scope.getProjectPaperList($scope.findprojectId,10,1,null);
				    }); 
		 }else{
			 alert("请输入图纸对应的楼栋号和楼层号！");
		 }
		 
		 
	 }
	
	 $scope.showPaperBox = function(){
		 layer.open({
		        type:1,
		        title: '图纸上传',
		        area: ['500px','500px'],
		        btn:['上传','取消'],
		        yes:function(index,layero){
		        	layero.find('#process')[0].style.display="block"; 
		        	$scope.addPaperInfo={};
		        	$scope.addPaperInfo.buildingNum=layero.find('#paper_buildingNums')[0].value;
		        	$scope.addPaperInfo.professionType=layero.find('#paper_profession_type')[0].value;
		        	$scope.constructionfiles=layero.find('#paper_files')[0].files;
		        	var floorNum=layero.find('#paper_floors')[0].value;
		        	if(floorNum!=undefined && floorNum!=null){
		        		$scope.addPaperInfo.floorNum=parseInt(floorNum);
		        	}
		        	progressBar=layero.find('#progressBar')[0];
		        	percentageDiv=layero.find('#percentage')[0];
		        	uploadTime=layero.find('#time')[0];
		        	$scope.UpladFile("paper");
		        },
		        content:$("#show_paper").html()
		    });
	 }
	
	
	 /////删除交底
	 $scope.deleteVideo = function(projectVideoId,fileId){
		 if(confirm("确定删除？")) {  
			ProjectService.deleteVideo(projectVideoId,fileId).then(function(result){
			       $scope.deleteProjectVideoInfo=result.data;
			       $scope.getProjectVideoList($scope.findprojectId,10,1,null);
			    });
		 }
	 };
	 ////////////////////安全交底上传
	 $scope.uploadVideoFile=function(){
			
		
			 var formData = new FormData();
			 var professionType=null;
			 for(var i=0;i<$scope.videofiles.length;i++){
				 formData.append("fileList",$scope.videofiles[i]);
			 }
			 if($scope.addVideoInfo.videofileType!=undefined && $scope.addVideoInfo.videofileType!="" && $scope.addVideoInfo.videofileType!=null){
				 videoTypeList=$scope.addVideoInfo.videofileType;
			 }
			 if($scope.addVideoInfo.videoType!=undefined && $scope.addVideoInfo.videoType!="" && $scope.addVideoInfo.videoType!=null){
				 professionType=$scope.addVideoInfo.videoType;
			 }
			 ProjectService.uploadVideoFile(formData,professionType,videoTypeList,$scope.findprojectId).then(function(result){
			       $scope.uploadVideoFiles=result.data;	      
			       $scope.getProjectVideoList($scope.findprojectId,10,1,null);
			    });
		 }
	 /////显示交底添加页面
	 $scope.showVideo = function(){
		 layer.open({
		        type:1,
		        title: '交底上传',
		        area: ['500px','500px'],
		        btn:['上传','取消'],
		        yes:function(index,layero){
		        	layero.find('#process2')[0].style.display="block"; 
		        	$scope.addVideoInfo={};
		        	$scope.addVideoInfo.videoType=layero.find('#videoType')[0].value;
		        	$scope.addVideoInfo.videofileType=layero.find('#videofileType')[0].value;
		        	$scope.videofiles=layero.find('#videofiles')[0].files;
		        	$scope.addVideoInfo.buildingNum=layero.find('#video_buildingNum')[0].value;
		        	progressBar=layero.find('#progressBar2')[0];
		        	percentageDiv=layero.find('#percentage2')[0];
		        	uploadTime=layero.find('#time2')[0];
		        	$scope.UpladFile("video");
		        },
		        content:$("#show_video").html()
		    });
	 }
	
	 	
	     /////////////////其他工程量的导入
	     $scope.importQuantity = function(){
	    	 var file="";
	    	 file=document.getElementById("biangeng_import").files;
	    	 if(file!==null && file.length>0){
				 var formData = new FormData();
				 for(var i = 0; i < file.length;i++) {
					 formData.append("file",file[i]);
				 }
				 
				 ProjectService.uploadOtherQuantity(formData,$scope.findProjectInfo.id).then(function(result){
					       $scope.uploadOtherQuantitys=result.data;	 
					       var quantity=null;
						   $scope.getProjectQuantityList($scope.findprojectId,pageSize,1,quantity);
			     });
				
			 }
	     }
	     
	    
	     //////返回项目列表按钮
	     $scope.returnProjects = function(){
	    	 document.getElementById("projectInfoHtml").style.display = 'none';
		     document.getElementById("projectContent").style.display = 'block';
		     $scope.findProjectInfo={};
	     };
	    
	     //上传文件方法
	     $scope.UpladFile=function (test) {
	    	 var form = new FormData(); // FormData 对象
	    	 if(test=='video'){
	    		 var fileObj = $scope.videofiles; // js 获取文件对象
		         var url = "api/video/admin/addVideo"; // 接收上传文件的后台地址 
		         if($scope.addVideoInfo.videofileType!=undefined && $scope.addVideoInfo.videofileType!=null){
		        	 form.append("videoType",$scope.addVideoInfo.videofileType); // 文件对象
		         }
		         if($scope.addVideoInfo.videoType!=""){
		        	 form.append("professionType",$scope.addVideoInfo.videoType);
		         }
		         if($scope.addVideoInfo.buildingNum!="" && $scope.addVideoInfo.buildingNum!=undefined){
		        	 form.append("buildingNum",$scope.addVideoInfo.buildingNum);
		         }
		         
	    	 }
	         if(test=='paper'){
	        	 var fileObj = $scope.constructionfiles; // js 获取文件对象
		         var url = "api/paper/admin/uploadPaper"; // 接收上传文件的后台地址 
		         if($scope.addPaperInfo.professionType!=null){
		        	 form.append("professionType",$scope.addPaperInfo.professionType);
				 }
		         var paper_buildingNum=$scope.addPaperInfo.buildingNum;
				 var paper_floorNum=$scope.addPaperInfo.floorNum;
				 if(paper_floorNum!=undefined && paper_floorNum!=""){
					 form.append("floorNum",paper_floorNum);
				 }
			 	 if(paper_buildingNum!=undefined && paper_buildingNum!=""){
			 		 form.append("buildingNum",paper_buildingNum);
				 }
	         }
	         if(fileObj!==null && fileObj.length>0){
				 for(var i = 0; i < fileObj.length;i++) {
					 form.append("fileList",fileObj[i]);
				 }
	         }
	         
	         form.append("projectId",$scope.findprojectId);
	         form.append("token",token);
	         xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
	         xhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
	         xhr.onload = uploadComplete; //请求完成
	         xhr.onerror =  uploadFailed; //请求失败
	         xhr.upload.onprogress = progressFunction;//【上传进度调用方法实现】
	         xhr.upload.onloadstart = function(){//上传开始执行方法
	             ot = new Date().getTime();   //设置上传开始时间
	             oloaded = 0;//设置上传开始时，以上传的文件大小为0
	         };
	         xhr.send(form); //开始上传，发送form数据
	     };
	     //上传进度实现方法，上传过程中会频繁调用该方法
	     function progressFunction(evt) {
	         
	          // event.total是需要传输的总字节，event.loaded是已经传输的字节。如果event.lengthComputable不为真，则event.total等于0
	          if (evt.lengthComputable) {//
	              progressBar.max = evt.total;
	              progressBar.value = evt.loaded;
	              percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
	          }
	         
	         var time = uploadTime;
	         var nt = new Date().getTime();//获取当前时间
	         var pertime = (nt-ot)/1000; //计算出上次调用该方法时到现在的时间差，单位为s
	         ot = new Date().getTime(); //重新赋值时间，用于下次计算
	         
	         var perload = evt.loaded - oloaded; //计算该分段上传的文件大小，单位b       
	         oloaded = evt.loaded;//重新赋值已上传文件大小，用以下次计算

	         //上传速度计算
	         var speed = perload/pertime;//单位b/s
	         var bspeed = speed;
	         var units = 'b/s';//单位名称
	         if(speed/1024>1){
	             speed = speed/1024;
	             units = 'k/s';
	         }
	         if(speed/1024>1){
	             speed = speed/1024;
	             units = 'M/s';
	         }
	         speed = speed.toFixed(1);
	         //剩余时间
	         var resttime = ((evt.total-evt.loaded)/bspeed).toFixed(1);
	         time.innerHTML = '，速度：'+speed+units+'，剩余时间：'+resttime+'s';
	            if(bspeed==0)
	             time.innerHTML = '上传已取消';
	     }
	     //上传成功响应
	     function uploadComplete(evt) {
	      //服务断接收完文件返回的结果
	      //    alert(evt.target.responseText);
	    	  $scope.getProjectPaperList($scope.findprojectId,10,1,paper);
	    	  $scope.getProjectVideoList($scope.findprojectId,10,1,video);
	          alert("上传成功！");
	     }
	     //上传失败
	     function uploadFailed(evt) {
	         alert("上传失败！");
	     }
	       //取消上传
	     $scope.cancleUploadFile=function(){
	         xhr.abort();
	     }
	   
}
