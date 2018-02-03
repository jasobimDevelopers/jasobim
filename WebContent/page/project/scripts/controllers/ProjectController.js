////////////////项目详情信息和项目基本信息页面切换
var index;
var projectPicss=null;
function projectInfo(){
	document.getElementById("include_header").style.display = 'none';
	document.getElementById("containers").style.display = 'none';
	document.getElementById("projectInfoHtml").style.display = 'block';
}
var token=getCookie('token');
var xhr;
var ot;//
var oloaded;


function setModel(model) {
	angular.element(model).scope().$parent.$parent.modelFiles = model.files;
}

function setPic(pic) {
	
	var file = pic.files[0];
	projectPicss=file;
	console.log(file);
    if(!/image\/\w+/.test(file.type)){ 
        alert("文件必须为图片！");
        return false; 
    } 
    var reader = new FileReader(); 
    reader.readAsDataURL(file); 
    reader.onload = function(e){ 
        document.getElementById('showId').src = this.result;
        document.getElementById("showId").style.display = 'block';
    } 
}

////////////////////////////////
function ProjectController($scope,ProjectService) {
	$scope.a = 1;
	
	console.log("载入ProjectController");
	$scope.fileTypes="";
	$scope.videoTypes="";
	$scope.videoGrade="";
	var videoBuildingNums="";
	var paperBuildingNums="";
	var paperBuildingNum=1;
	var paperFloorNum=1;
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
	$scope.buildingVideoB=[];
	$scope.buildingVideoF=[];
	$scope.buildingN=[];
	var fileArray=[];
	var fileArray1=[];
	var videoTypeList=[];
	$scope.floorNumArrays=[];
	$scope.buildingNumArrays=[];
	$scope.showOr=[];
	for(var i=0;i<5;i++){
		$scope.showOr[i]=0;
	}
	var introduced=null;
	var projectId=null;
	$scope.number=null;
	$scope.numbers=null;
	$scope.projectTitles=["序号","项目名称","项目编码","施工单位","项目负责人","建设单位","版本","施工时间","施工周期","操作"];
	$scope.projectDetailsInfo=["项目名称","项目编码","施工单位","项目负责人","施工地点","建设单位","设计单位","施工时间","施工周期","版本","项目简介"];
	$scope.itemTitles=["序号","构件名称","底部高程","系统类型","尺寸","长度","设备类型","所属类别","标高","偏移量","面积","材质","类型名"];
	$scope.quantityTitles=["序号","构件名称","系统类型","数值","单位","familyAndType","设备类型","尺寸","设备名称","材质"];
	$scope.quantityTitlesFind=["专业","楼栋号","楼层号","户型"];
	$scope.questionTitles=["序号","问题类型","问题提交人","问题标题","专业","问题创建时间","问题等级","问题状态","操作"];
	$scope.videoTitles=["序号","交底地址","楼栋号","专业","操作"];
	$scope.paperTitles=["序号","图纸信息","楼栋号","楼层","专业","操作"];
	$scope.flag=["工程量页面","构件信息页面","图纸信息页面","问题列表页面","安全交底页面"];
	$scope.quantityTypeTitles=["模型工程量","预算工程量"];
	$scope.phase="all";
	$scope.phases="alls";
	$scope.buildingId="all";
	$scope.buildingIds="alls";
	$scope.floorId="all";
	$scope.floorIds="alls";
	$scope.householdId="all";
	$scope.householdIds="alls";
	$scope.questionType="all";
	$scope.questionPriority="all";
	$scope.questionStatus="all";
	$scope.buildingNumArray=null;
	$scope.floorNumArray=null;
	$scope.buildingNumInfo=null;
	$scope.buildingDownInfo=null;
	$scope.paperTypes=null;
	var item = "";
	var quantity = "";
	var video = "";
	var question="";
	var paper="";
	var nS=null;
	$scope.getDate=function(nS) {     
		   return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');     
	}     
	/////按问题类型搜索
	$scope.setQuestionOfType = function(type,flag){
		$scope.questionType=type;
		$scope.getProjectQuestionList(10,1,question);
	}
	/////按问题级别搜索
	$scope.setQuestionOfPriority = function(priority,flag){
		$scope.questionPriority=priority;
		$scope.getProjectQuestionList(10,1,question);
	}
	/////按问题状态搜索
	$scope.setQuestionOfStatus = function(status,flag){
		$scope.questionStatus=status;
		$scope.getProjectQuestionList(10,1,question);
	}
	/////按专业自动搜索
	$scope.setPhase = function(phase,flag) {
		$scope.phase = phase;
		$scope.phases="allss";
		var allclass=$('.phase_quantity');
		allclass.css("background-color","#fff");
		if(phase=='all'){
			allclass.eq(0).css("background-color","darkgray");
		}
		if(phase!='all'){
			allclass.eq(phase+1).css("background-color","darkgray");
		}
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
			$scope.getProjectVideoList(10,1,video);
		}
		
	}
	/////按楼栋号自动搜索
	$scope.setBuildingNum = function(building,flag,index) {
		for(var i = 0; i < 100; i++) {
			if(i != index) {
				$scope.floors[i] = 'title';
			}
		}
		$scope.buildingId = building;	
		$scope.buildingIds="allss";
		var allclass=$('.buildingNum_quantity');
		allclass.css("background-color","#fff");
		if(building=='all'){
			allclass.eq(0).css("background-color","darkgray");
			allclass.val("title");
			$scope.buildingNumInfo="";
			$scope.buildingDownInfo="";
			$scope.buildingInfo.buildingNum="";
			
		}
		if(building!='all'){
			allclass.eq(1).css("background-color","darkgray");
		}
		if(building=='all' ){
			if(flag=="构件信息页面"){
				 $scope.getProjectItemList($scope.projectid,10,1,item);
			 }
			 if(flag=="工程量页面"){
				 $scope.getProjectQuantityList($scope.projectid,10,1,quantity);
			 }
			 if(flag=="安全交底页面"){
				 $scope.getProjectVideoList(10,1,video);
			 }
			 if(flag=="图纸信息页面"){
				 $scope.getProjectPaperList($scope.projectid,10,1,paper);
			 }
		}else if(building != 'title'){
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
					 if(flag=="安全交底页面"){
						 $scope.getProjectVideoList(10,1,video);
					 }
					 if(flag=="图纸信息页面"){
						 $scope.getProjectPaperList($scope.projectid,10,1,paper);
					 }
				    });
		    });
		}
		
	}
	/////按楼层号自动搜索
	$scope.setFloorNum = function(floor,flag,index) {
		for(var i = 0; i < 100; i++) {
			if(i != index) {
				$scope.floors[i] = 'title';
			}
		}
		
		
		
		
		if($scope.buildingId!="all" && $scope.buildingIds!="alls"){
			$scope.floorId=floor;
			$scope.floorIds="allss";
			if(floor!="title"){
				if(flag=="构件信息页面"){
					$scope.floorId=0;
					$scope.floorId=parseInt(floor)+3;
					$scope.getProjectItemList($scope.projectid,10,1,item);
				}
				if(flag=="工程量页面"){
					if(floor!='all'){
						$scope.floorId=0;
						$scope.floorId=parseInt(floor)+3;
					}
					$scope.getProjectQuantityList($scope.projectid,10,1,quantity);
				}
				if(flag=="图纸信息页面"){
					$scope.floorId=0;
					$scope.floorId=parseInt(floor)+3;
					$scope.getProjectPaperList($scope.projectid,10,1,paper);
				}
			}
		}
		
		
		
		
	}
/////按地下层号自动搜索
	$scope.setFloorDownNum = function(floor,flag) {
		$scope.floorIds="allss";
		var allclass=$('.floorNums_quantity');
		var allclass_others=$('.floorNum_quantity');
		allclass_others.css("background-color","#fff");
		allclass.css("background-color","#fff");
		if(parseInt(floor)==1){
			allclass.eq(0).css("background-color","darkgray");
			$scope.floorId=3;
		}
		if(parseInt(floor)==2){
			allclass.eq(1).css("background-color","darkgray");
			$scope.floorId=2;
		}
		if(floor!="title"){
			//$('.floorNum_quantity')[parseInt(floor)/10].value(floor);
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
		
		
	}
	//////按户型自动搜索
	$scope.setHouseholdNum = function(household,flag) {
		$scope.householdId=household;
		$scope.householdIds="allss";
		var allclass=$('.household_quantity');
		allclass.css("background-color","#fff");
		if(household=='all'){
			allclass.eq(0).css("background-color","darkgray");
		}
		if(household!='all'){
			allclass.eq(household+1).css("background-color","darkgray");
		}
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
		  $scope.buildings[i] ="title";
		  $scope.buildingN[i] ="title";
		  $scope.buildingVideoB[i] ="title";
		  $scope.buildingVideoF[i] ="title";
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
	      document.getElementById("projectDetail_head").style.display = "block";
	      document.getElementById("containers").style.display = 'none';
	      document.getElementById("include_header").style.display = 'none';
	      ///////
	      document.getElementById("projectSelfInfoHtml").style.display = 'block';
	      document.getElementById("projectItemInfoHtml").style.display = 'none';
	      document.getElementById("projectPaperInfoHtml").style.display = 'none';
	      document.getElementById("projectQuantityInfoHtml").style.display = 'none';
	      document.getElementById("projectVideoInfoHtml").style.display = 'none';
	      document.getElementById("projectQuestionInfo").style.display = 'none';
	      $scope.projectTitle="更新项目";
	    });
	    $scope.getBuildingList(projectId);
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
	       $scope.findProjectInfo={};
	 }
	 //////重置添加项目信息
	 $scope.resetProject = function(){
		 $scope.findProjectInfo = {};
	 };
	 /////删除交底
	 $scope.deleteVideo = function(projectVideoId,fileId){
			ProjectService.deleteVideo(projectVideoId,fileId).then(function(result){
			       $scope.deleteProjectVideoInfo=result.data;
			       $scope.getProjectVideoList(10,1,video);
			    });
	 };
	 

	 /////增加项目
	 $scope.addProjectByAdmin = function(){
		 if($scope.projectTitle=="增加项目"){

			 var formData = new FormData();
			 var i=0;
			 ////////////////////////////////
			 if($scope.findProjectInfo.name==null || $scope.findProjectInfo.name==undefined){
				 alert($scope.projectDetailsInfo[0]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.num==null || $scope.findProjectInfo.num==undefined){
				 alert($scope.projectDetailsInfo[1]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.constructionUnit==null || $scope.findProjectInfo.constructionUnit==undefined){
				 alert($scope.projectDetailsInfo[2]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.leader==null || $scope.findProjectInfo.leader==undefined){
				 alert($scope.projectDetailsInfo[3]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.place==null || $scope.findProjectInfo.place==undefined){
				 alert($scope.projectDetailsInfo[4]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.buildingUnit==null || $scope.findProjectInfo.buildingUnit==undefined){
				 alert($scope.projectDetailsInfo[5]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.designUnit==null || $scope.findProjectInfo.designUnit==undefined){
				 alert($scope.projectDetailsInfo[6]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.startDate==null || $scope.findProjectInfo.startDate==undefined){
				 alert($scope.projectDetailsInfo[7]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.phase==null || $scope.findProjectInfo.phase==undefined){
				 alert($scope.projectDetailsInfo[8]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.version==null || $scope.findProjectInfo.version==undefined){
				 alert($scope.projectDetailsInfo[9]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.description==null || $scope.findProjectInfo.description==undefined){
				 alert($scope.projectDetailsInfo[10]+"不能为空");
				 return ;
			 }
			///////////////////////////
			 for (var key in $scope.findProjectInfo) {
				   if($scope.findProjectInfo[key] != null) {
					   formData.append(key, $scope.findProjectInfo[key]);
				   }
			 }
			 if($scope.modelFiles == undefined || $scope.modelFiles == null) {
				 formData.append('modelFile',null);
			 } else {
				 for(var i = 0; i < $scope.modelFiles.length;i++) {
					 formData.append('modelFile',$scope.modelFiles[i]);
				 }
			 }
			 if(projectPicss == undefined || projectPicss == null) {
				 formData.append('picFile',null);
			 } else {
				 formData.append('picFile',projectPicss);
			 }
			 ProjectService.addProjectByAdmin(formData,token).then(function(result){
			       $scope.addProjectByAdminInfo=result.data; 
			       $scope.projectid=result.data.id;
			       $scope.getProjectList(pageSize,1,$scope.ProjectTofind);
			       document.getElementById("containers").style.display = 'none';
				   document.getElementById("include_header").style.display = 'none';
				   document.getElementById("showId").style.display = 'none';
			       document.getElementById("projectItemInfoAdd").style.display = 'block';
			       document.getElementById("picFile").value="";
			       
			       document.getElementById("projectDetail_head").style.display = "none";
			    });
		 }
		 if($scope.projectTitle=="更新项目")
		{
			 
			 var formData = new FormData();
			 //////////////////
			 if($scope.findProjectInfo.name==null || $scope.findProjectInfo.name==undefined){
				 alert($scope.projectDetailsInfo[0]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.num==null || $scope.findProjectInfo.num==undefined){
				 alert($scope.projectDetailsInfo[1]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.constructionUnit==null || $scope.findProjectInfo.constructionUnit==undefined){
				 alert($scope.projectDetailsInfo[2]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.leader==null || $scope.findProjectInfo.leader==undefined){
				 alert($scope.projectDetailsInfo[3]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.place==null || $scope.findProjectInfo.place==undefined){
				 alert($scope.projectDetailsInfo[4]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.buildingUnit==null || $scope.findProjectInfo.buildingUnit==undefined){
				 alert($scope.projectDetailsInfo[5]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.designUnit==null || $scope.findProjectInfo.designUnit==undefined){
				 alert($scope.projectDetailsInfo[6]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.startDate==null || $scope.findProjectInfo.startDate==undefined){
				 alert($scope.projectDetailsInfo[7]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.phase==null || $scope.findProjectInfo.phase==undefined){
				 alert($scope.projectDetailsInfo[8]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.version==null || $scope.findProjectInfo.version==undefined){
				 alert($scope.projectDetailsInfo[9]+"不能为空");
				 return ;
			 }
			 if($scope.findProjectInfo.description==null || $scope.findProjectInfo.description==undefined){
				 alert($scope.projectDetailsInfo[10]+"不能为空");
				 return ;
			 }else if($scope.findProjectInfo.description.length>1000){
				 alert($scope.projectDetailsInfo[10]+"最多1000字");
			 }
			 ///////////////////////////////
			 for (var key in $scope.findProjectInfo) {
				   if($scope.findProjectInfo[key] != null) {
					   formData.append(key, $scope.findProjectInfo[key]);
				   }
			 }
			 if($scope.modelFiles == undefined || $scope.modelFiles == null) {
				 formData.append('modelFile',null);
			 } else {
				 for(var i = 0; i < $scope.modelFiles.length;i++) {
					 formData.append('modelFile',$scope.modelFiles[i]);
				 }
			 }
			 /*if($scope.modelFiles == undefined || $scope.modelFiles == null) {
				 formData.append('modelFile',null);
			 } else {
				 var filett=document.getElementById("testfile").files;
				 for(var i = 0; i < filett.length;i++) {
					 formData.append('modelFile',filett[i]);
				 }
				 
			 }*/
			 //var picfile=document.getElementById("picFile").files[0];
			 if(projectPicss == undefined || projectPicss == null) {
				 formData.append('picFile',null);
			 } else {
				 formData.append('picFile',projectPicss);
			 }
			 ProjectService.updateProject(formData,token).then(function(result){
				 
			       $scope.updateProjectInfo=result.data;
			       //document.getElementById("projectItemInfoAdd").style.display = 'block';
			       $scope.getProjectList(pageSize,1,$scope.ProjectTofind);
			       document.getElementById("showId").style.display = 'none';
			       document.getElementById("picFile").value="";
			    });
		}
	 }
	 ////////模糊查找项目
	 $scope.projectFind = function() {
		 $scope.currentPage = 1;
		 $scope.getProjectList(pageSize,$scope.currentPage,$scope.ProjectTofind);
	 }
	 //////初始化获取项目列表
	 $scope.getProjectList(pageSize,$scope.currentPage,$scope.ProjectTofind);

	 $scope.projectInfo = function(){
		 console.log("test");
		 alert("test");
	 }
	 //////////////////////////////项目详情信息
	 $scope.projectInfoHead=[{name:"基本信息"},{name:"构件信息"},{name:"图纸信息"},{name:"工程量信息"},{name:"安全技术交底"},{name:"问题列表"}];
	 $scope.projectPhaseInfo=[{name:"电气"},{name:"暖通"},{name:"给排水"},{name:"消防"}];
	 $scope.projectPhaseInfos=[{name:"电气"},{name:"暖通"},{name:"给排水"},{name:"消防"},{name:"建筑"},{name:"装饰"},{name:"结构"}];
	 $scope.projectVideoType=[{name:"质量"},{name:"安全"},{name:"技术"}];
	 $scope.projectHouseholdInfo=[{name:"公共部位"},{name:"N户型"},{name:"Q户型"},{name:"Q户型反"},{name:"N户型反"}];
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
		 if($scope.buildingArray == undefined || $scope.buildingArray.length==0) {
			 $scope.buildingArray=menuArray($scope.buildingInfo.buildingNum);
		 }
		 $scope.buildingDownArray=menuArray($scope.buildingDownInfo);
		 if($scope.floorArray == undefined || $scope.floorArray.length==0) {
			 $scope.floorArray=menuArray($scope.buildingNumInfo-$scope.buildingDownInfo);
		 }
		 
		 
		  
		 if($scope.phase!="all") {
			 item+= "&professionType=" + $scope.phase;
		 }
		 if($scope.phase=="all"){
			 item+= "&professionType=" + $scope.flagAll;
		 }
		 if($scope.buildingId!="all"){
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
		 if($scope.buildingArray == undefined || $scope.buildingArray.length == 0) {
			 if($scope.buildingInfo!=undefined){
				 $scope.buildingArray=menuArray($scope.buildingInfo.buildingNum);
			 }
		 }
		
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
	  //////////////图纸信息删除功能
	  $scope.deletePaper=function(paperId){
		  ProjectService.deleteProjectPaper(paperId).then(function(result){
		       $scope.deleteProjectPaperInfo=result.data;
		       $scope.getProjectPaperList($scope.projectid,pageSize,1,paper);
		       
		    });
	  }
	 ////////////////////////图纸列表信息分页获取
	 $scope.getProjectPaperList = function(projectId,pageSize,pageIndex,paper) {
		 if($scope.buildingArray == undefined || $scope.buildingArray.length ==0) {
			 if($scope.buildingInfo!=undefined){
				 $scope.buildingArray=menuArray($scope.buildingInfo.buildingNum);
			 }
		 }
		 $scope.buildingDownArray=menuArray($scope.buildingDownInfo);
		 if($scope.floorArray == undefined || $scope.floorArray.length ==0) {
			 $scope.floorArray=menuArray($scope.buildingNumInfo-$scope.buildingDownInfo);
		 }
		 
		 
//		  $scope.floorArray=menuArray($scope.buildingNumInfo-$scope.buildingDownInfo);
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
		 
		 if($scope.buildingArray == undefined || $scope.buildingArray.length == 0) {
			 if($scope.buildingInfo!=undefined){
				 $scope.buildingArray=menuArray($scope.buildingInfo.buildingNum);
			 }
		 }
		 $scope.buildingDownArray=menuArray($scope.buildingDownInfo);
		 if($scope.floorArray == undefined || $scope.floorArray.length == 0) {
			 if($scope.buildingInfo!=undefined){
			 $scope.floorArray=menuArray($scope.buildingNumInfo-$scope.buildingDownInfo);
			 }
		 }

//		 $scope.floorArray=menuArray($scope.buildingNumInfo-$scope.buildingDownInfo);
		 
		 
		 var text=-1;
		
		  
		 if($scope.phase!="all") {
			 quantity+= "professionType=" + $scope.phase;
			 $scope.showOr[0]=1;
		 }else if($scope.phases!="alls"){
			 quantity+= "professionType=" + text;
			 $scope.showOr[0]=1;
		 }
		 if($scope.buildingId!="all"){
			 quantity+= "&buildingNum=" + $scope.buildingId;
			 $scope.showOr[1]=1;
		 }else if($scope.buildingIds!="alls"){
			 quantity+= "&buildingNum=" + text;
			 $scope.showOr[1]=1;
		 }
		 if($scope.floorId!="all"){
			 quantity+= "&floorNum=" + $scope.floorId;
			 $scope.showOr[2]=1;
		 }else if($scope.floorIds!="alls"){
			 quantity+= "&floorNum=" + text;
			 $scope.showOr[2]=1;
		 }
		 if($scope.householdId!="all"){
			 quantity+= "&householdNum=" + $scope.householdId;
			 $scope.showOr[3]=1;
		 }else if($scope.householdIds!="alls"){
			 quantity+= "&householdNum=" + text;
			 $scope.showOr[3]=1;
		 }
		 $scope.quantityTitles=["序号","构件名称","系统类型","数值","单位","familyAndType","设备类型","尺寸","设备名称","材质","来源"];
		 for(var j=0;j<4;j++){
			 if($scope.showOr[j]==1){
				 $scope.quantityTitles.push($scope.quantityTitlesFind[j]);
			 }
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
		    	  $scope.getProjectQuestionList(pageSize,p,question);
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
		    	  $scope.getProjectVideoList(pageSize,p,video);
		      }
		  });
	  }
	  //////交底分页获取
	 $scope.getProjectVideoList = function(pageSize,pageIndex,video) {
		 video="";
		 if($scope.buildingArray == undefined || $scope.buildingArray.length ==0) {
			 if($scope.buildingInfo!=undefined){
				 $scope.buildingArray=menuArray($scope.buildingInfo.buildingNum);
			 }
			 
		 }
		 if($scope.phase!="all") {
			 video+= "professionType=" + $scope.phase;
		 }
		 if($scope.buildingId!="all"){
			 video+= "&buildingNum=" + $scope.buildingId;
		 }
		  ProjectService.getVideoList($scope.projectid,pageSize,pageIndex,video).then(function (result){
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
		  	  for(var i=0;i<$scope.projectQuestionList.length;i++){
		  		$scope.projectQuestionList[i].questionDate=$scope.getDate($scope.projectQuestionList[i].questionDate);
		  	  }
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
		 ProjectService.getProjectQuantityExcel($scope.projectid).then(function (result){
		  	  $scope.quantityUrl = result.data;
		  });
	 }
	 $scope.projectInfoChange = function(projectType){
		    $scope.phase="all";
			$scope.test="title";
			$scope.phases="alls";
			$scope.buildingId="all";
			$scope.buildingIds="alls";
			$scope.floorId="all";
			$scope.floorIds="alls";
			$scope.householdId="all";
			$scope.householdIds="alls";
			$scope.questionType="all";
			$scope.questionPriority="all";
			$scope.questionStatus="all";
			$scope.buildingNumArray=null;
			$scope.floorNumArray=null;
			$scope.buildingNumInfo=null;
			$scope.buildingDownInfo=null;
			$scope.buildingNumInfo=[];
			$scope.buildingDownInfo=[];
			$scope.buildingArray=[];
			$scope.buildingDownArray=[];
			$scope.floorArray=[];
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
			 $scope.getProjectQuantityExcel();
		 }
		 if(projectType == "安全技术交底"){
			 document.getElementById("projectSelfInfoHtml").style.display = 'none';
			 document.getElementById("projectItemInfoHtml").style.display = 'none';
			 document.getElementById("projectPaperInfoHtml").style.display = 'none';
			 document.getElementById("projectQuantityInfoHtml").style.display = 'none';
			 document.getElementById("projectVideoInfoHtml").style.display = 'block';
			 document.getElementById("projectQuestionInfo").style.display = 'none';
			 var video=null;
			 $scope.getProjectVideoList(pageSize,1,video);
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
		 if(document.getElementById("qiaojia").files[0]!==null){
			 fileArray[0]=document.getElementById("qiaojia").files[0];	
		 }
		 //电缆桥架配件
		 if(document.getElementById("qiaojia_fujian").files[0]!==null){
			 fileArray[1]=document.getElementById("qiaojia_fujian").files[0];	
		 }
		 //电气设备
		 if(document.getElementById("dianqi_shebei").files[0]!==null){
			 fileArray[2]=document.getElementById("dianqi_shebei").files[0];	 
		 }
		 //风管	
		 if(document.getElementById("fengguan").files[0]!==null){
			 fileArray[3]=document.getElementById("fengguan").files[0];	
		 }
		 //风管配件
		 if(document.getElementById("fengguan_peijian").files[0]!==null){
			 fileArray[4]=document.getElementById("fengguan_peijian").files[0];	
		 }
		 //风管附件
		 if(document.getElementById("fengguan_fujian").files[0]!==null){
			 fileArray[5]=document.getElementById("fengguan_fujian").files[0];	
		 }
		 //风管末端
		 if(document.getElementById("fengguan_moduan").files[0]!==null){
			 fileArray[6]=document.getElementById("fengguan_moduan").files[0];	
		 }
		 //机械设备
		 if(document.getElementById("jixie_shebei").files[0]!==null){
			 fileArray[7]=document.getElementById("jixie_shebei").files[0];		
		 }
		 //管道
		 if(document.getElementById("guandao").files[0]!==null){
			 fileArray[8]=document.getElementById("guandao").files[0];	
		 }
		 //管件
		 if(document.getElementById("guanjian").files[0]!==null){
			 fileArray[9]=document.getElementById("guanjian").files[0];		
		 }
		 //管道附件
		 if(document.getElementById("guandao_fujian").files[0]!==null){
			 fileArray[10]=document.getElementById("guandao_fujian").files[0];	
		 }
		 //卫浴装置
		 if(document.getElementById("weiyu_zhuangzhi").files[0]!==null){
			 fileArray[11]=document.getElementById("weiyu_zhuangzhi").files[0];	
		 }
		 //消防栓
		 if(document.getElementById("xiaofangshuan").files[0]!==null){
			 fileArray[12]=document.getElementById("xiaofangshuan").files[0];	
		 }
		 //喷淋
		 if(document.getElementById("penlin").files[0]!==null){
			 fileArray[13]=document.getElementById("penlin").files[0];
		 }		 
		
//		 console.log(typeof(fileArray));
		 var formData = new FormData();
		 for(var i=0;i<fileArray.length;i++){
			 formData.append("fileList",fileArray[i]);
		 }
		 ProjectService.uploadItemFile(formData,$scope.projectid).then(function(result){
		       $scope.uploadItemInfo=result.data;	 
		       document.getElementById("projectPaperInfoAdd").style.display = 'block';
		    });
	 }
	 ////////////上传图纸信息
	 //////
	 $scope.floorNumArray=menuArray(40);
	 $scope.buildingNumArray=menuArray(20);
	 for(var i=1;i<41;i++){
		 if(i==1){
			 $scope.floorNumArrays[i-1]="请输入楼层号";
		 }else{
			 $scope.floorNumArrays[i-1]="F"+(i-1);
		 }
		 
		
	 }
	 for(var j=0;j<20;j++){
		 if(j==0){
			 $scope.buildingNumArrays[j]="请输入楼栋号";
		 }else{
			 $scope.buildingNumArrays[j]="#"+j;
		 }
		 
	 }
	 
	 $scope.setBuildingNumber = function(x) {
		 $scope.number=x;
	 }
	 $scope.setFloorNumber = function(y) {
		 $scope.numbers=y;
	 }
	 $scope.uploadPaperFile=function(){
		 var building_val=document.getElementById("buildingN");
		 var floor_val=document.getElementById("floorN");
		 if(building_val.value!=0 && floor_val.value!=0){
			 var formData = new FormData();
			 if(document.getElementById("paper_dianqi").files[0]!=undefined){
				 fileArray1[0]=document.getElementById("paper_dianqi").files[0];
			 }
			 if(document.getElementById("paper_nuantong").files[0]!=undefined){
				 fileArray1[1]=document.getElementById("paper_nuantong").files[0];
			 }
			 if(document.getElementById("paper_geipaishui").files[0]!=undefined){
				 fileArray1[2]=document.getElementById("paper_geipaishui").files[0];
			 }
			 if(document.getElementById("paper_xiaofang").files[0]!=undefined){
				 fileArray1[3]=document.getElementById("paper_xiaofang").files[0];
			 }
			 for(var i=0;i<fileArray1.length;i++){
				 formData.append("fileList",fileArray1[i]);
			 }
			 ProjectService.uploadPaperFile(formData,building_val.value,floor_val.value,$scope.projectid).then(function(result){
				       $scope.uploadPaperFile=result.data;	 
				       document.getElementById("projectVideoInfoAdd").style.display = 'block';
				       document.getElementById("containers").style.display = 'none';
				    }); 
		 }else{
			 alert("请输入图纸对应的楼栋号和楼层号！")
		 }
		 
		 
	 }
	 ////////////////////安全交底上传
	 $scope.uploadVideoFile=function(){
			
		
			 var formData = new FormData();
			 var professionType=null;
			 var allType=$(".geipaishui_type");
			
			 
			 
			 
			 if(document.getElementById("video_zhiliang").files[0]!=undefined){
				 fileArray1[0]=document.getElementById("video_zhiliang").files[0];
				 professionType=0;
				 videoTypeList[0] = allType[0].value;
			 }
			 if(document.getElementById("video_anquan").files[0]!=undefined){
				 fileArray1[1]=document.getElementById("video_anquan").files[0];
				 professionType=1;
				 videoTypeList[1] = allType[1].value;
			 }
			 if(document.getElementById("video_jishu").files[0]!=undefined){
				 fileArray1[2]=document.getElementById("video_jishu").files[0];
				 professionType=2;
				 videoTypeList[2] = allType[2].value;
			 }
			
			 for(var i=0;i<fileArray1.length;i++){
				 formData.append("fileList",fileArray1[i]);
			 }
			 if(professionType!=null){
				 ProjectService.uploadVideoFile(formData,professionType,videoTypeList,$scope.projectid).then(function(result){
				       $scope.uploadPaperFile=result.data;	      
				       
				    });
			 }
		 }
	 	///////交底上传信息重置
	     $scope.resetVideoFile=function(){
	    	 document.getElementById("video_dianqi").value=null;
	    	 document.getElementById("video_nuantong").value=null;
	    	 document.getElementById("video_geipaishui").value=null;
	    	 document.getElementById("video_xiaofang").value=null;
	     }
	     //////构件信息重置
	     $scope.resetItemFile=function(){
			 document.getElementById("qiaojia").value=null;	
			 document.getElementById("qiaojia_fujian").value=null;	
			 document.getElementById("dianqi_shebei").value=null;	
			 document.getElementById("fengguan").value=null;	
			 document.getElementById("fengguan_peijian").value=null;	
			 document.getElementById("fengguan_fujian").value=null;	
			 document.getElementById("fengguan_moduan").value=null;	
			 document.getElementById("jixie_shebei").value=null;	
			 document.getElementById("guanjian").value=null;	
			 document.getElementById("guandao_fujian").value=null;	
			 document.getElementById("weiyu_zhuangzhi").value=null;	
			 document.getElementById("xiaofangshuan").value=null;	
			 document.getElementById("penlin").value=null;	
			 document.getElementById("gongchengliang").value=null;	
	     }
	     ///////图纸信息重置
	     $scope.resetPaperFile=function(){
			 document.getElementById("paper_dianqi").value=null;	
			 document.getElementById("paper_nuantong").value=null;	
			 document.getElementById("paper_geipaishui").value=null;	
			 document.getElementById("paper_xiaofang").value=null;	
			 document.getElementById("buildingN").value=null;	
			 document.getElementById("floorN").value=null;	
	     }
		 
	    //////返回项目列表
	     $scope.returnProject=function(){
	    	 $scope.findProjectInfo = {};
	 		document.getElementById("addProjectHtml").style.display = 'none';
	 	    document.getElementById("containers").style.display = 'block';
	 	    document.getElementById("include_header").style.display = 'block';
	 	    document.getElementById("projectVideoInfoAdd").style.display = 'none';
	 	    document.getElementById("projectPaperInfoAdd").style.display = 'none';
	 	    document.getElementById("projectItemInfoAdd").style.display = 'none';
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
						   $scope.getProjectQuantityList($scope.findProjectInfo.id,pageSize,1,quantity);
			     });
				
			 }
	     }
	     //////////////交底上传
	     $scope.importVideo = function(){
	    	 var file="";
	    	 file=document.getElementById("video_import").files;
	    	 if(file!==null && file.length>0){
				 var formData = new FormData();
				 for(var i = 0; i < file.length;i++) {
					 formData.append("fileList",file[i]);
					 if($scope.fileTypes!=""){
						 formData.append("videoType",$scope.fileTypes);
					 }else{
						 formData.append("videoType",3);
					 }
				 }
				 if($scope.videoTypes!=""){
					 formData.append("professionType",$scope.videoTypes);
				 }else{
					 formData.append("videoType",0);
				 }
				 if($scope.videoGrade!=""){
					 formData.append("videoGrade",$scope.videoGrade);
				 }else{
					 formData.append("videoGrade",1);
				 }
				 if(videoBuildingNums!=""){
					 formData.append("buildingNum",videoBuildingNums);
				 }else{
					 formData.append("buildingNum",6);
				 }
				 
				 formData.append("videoType",0);
				 ProjectService.uploadVideo(formData,$scope.findProjectInfo.id).then(function(result){
					       $scope.uploadVideos=result.data;	 
					       var video=null;
						   $scope.getProjectVideoList(pageSize,1,video);
			     });
				
			 }
	     }
	     
	     ////////////////////图纸上传
	     $scope.importPaper = function(){
	    	 var file="";
	    	 file=document.getElementById("paper_import").files;
	    	 if(file!==null && file.length>0){
				 var formData = new FormData();
				 for(var i = 0; i < file.length;i++) {
					 formData.append("fileList",file[i]);
				 }
				 if($scope.paperTypes!=null){
					 formData.append("professionType",$scope.paperTypes);
				 }
				 var paper_buildingNum=document.getElementById("buildingNum_input").value;
				 var paper_floorNum=document.getElementById("floorNum_input").value;
				 if(paper_buildingNum!=undefined && paper_buildingNum!=""){
					 paperBuildingNum=paper_buildingNum;
				 }
				 if(paper_floorNum!=undefined && paper_floorNum!=""){
					 paperFloorNum=paper_floorNum
				 }
				 ProjectService.uploadPaperFile(formData,paperBuildingNum,paperFloorNum,$scope.findProjectInfo.id).then(function(result){
					       $scope.uploadPapers=result.data;	 
					       var paper=null;
						   $scope.getProjectPaperList($scope.projectid,10,1,paper);
			     });
				
			 }
	     }
	     ////////图纸赋值
	     $scope.setPaperType = function(index){
	    	 $scope.paperTypes=index.professionType;
	     }
	     ////////交底赋值
	     $scope.set_buildingNum = function(num){
	    	 videoBuildingNums=num;
	     }
	     $scope.setVideoType = function(index){
	    	 $scope.videoTypes=index.videoTypes;
	     }
	     $scope.setFileType = function(index){
	    	 $scope.fileTypes=index.videoFiles;
	     }
	     $scope.setVideoGrade = function(index){
	    	 $scope.videoGrade=index.videoGrade;
	     }
	     
	     //////返回项目列表按钮
	     $scope.returnProjects = function(){
	    	 document.getElementById("projectInfoHtml").style.display = 'none';
		     document.getElementById("containers").style.display = 'block';
		     document.getElementById("include_header").style.display = 'block';
	     }
	   //上传文件方法
	     $scope.UpladFile=function (test) {
	    	 var process=document.getElementById("process");
	    	 process.style.display="block";
	    	 var form = new FormData(); // FormData 对象
	    	 if(test=='video'){
	    		 var fileObj = document.getElementById("video_import").files; // js 获取文件对象
		         var url = "api/video/admin/addVideo"; // 接收上传文件的后台地址 
		         if($scope.fileTypes!=""){
		        	 form.append("videoType",$scope.fileTypes); // 文件对象
		         }
		         if($scope.videoGrade!=""){
		        	 form.append("videoGrade",$scope.videoGrade);
				 }else{
					 form.append("videoGrade",1);
				 }
		         if($scope.videoTypes!=""){
		        	 form.append("professionType",$scope.videoTypes);
		         }
	    	 }
	         if(test=='paper'){
	        	 var fileObj = document.getElementById("paper_import").files; // js 获取文件对象
		         var url = "api/paper/admin/uploadPaper"; // 接收上传文件的后台地址 
		         if($scope.paperTypes!=null){
		        	 form.append("professionType",$scope.paperTypes);
				 }
		         var paper_buildingNum=document.getElementById("buildingNum_input").value;
				 var paper_floorNum=document.getElementById("floorNum_input").value;
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
	         ////////////
	         form.append("projectId",$scope.findProjectInfo.id);
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
	         
	          var progressBar = document.getElementById("progressBar");
	          var percentageDiv = document.getElementById("percentage");
	          // event.total是需要传输的总字节，event.loaded是已经传输的字节。如果event.lengthComputable不为真，则event.total等于0
	          if (evt.lengthComputable) {//
	              progressBar.max = evt.total;
	              progressBar.value = evt.loaded;
	              percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
	          }
	         
	         var time = document.getElementById("time");
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
	          alert("上传成功！");
	     }
	     //上传失败
	     function uploadFailed(evt) {
	         alert("上传失败！");
	     }
	       //取消上传
	     $scope.cancleUploadFile=function(){
	         xhr.abort();
	     }// JavaScript Document
}