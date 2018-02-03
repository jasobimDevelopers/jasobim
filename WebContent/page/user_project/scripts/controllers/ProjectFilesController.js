////////////////////////////////
 var token=getCookie('token');

function setConsFiles(model) {
	angular.element(model).scope().$parent.$parent.modelFiles = model.files;
}

function ProjectFilesController($scope,ProjectFilesService) {
	console.log("载入ProjectFilesController");
	$scope.currentPage=1;
	$scope.totalPage=1;
	var pageSize=10;
	var pageIndex=1;
	$scope.projectFilesList="";
	$scope.typeNames=["图纸会审","施工组织设计","专项方案","签证资料","人员备案（特殊工种）","花名册","施工日志",
	                  "图纸深化及翻样","施工资料","竣工验收资料","竣工图","材料复检报告","第三方强制检测","样板实施计划","材料进度计划"];
	$scope.projectFilesTitle=["序号","项目名称","文件名","上传时间","操作"];
	$scope.contentTitle="";
	$scope.projectList="";
	$scope.fileList="";
	$scope.projectId;
	var projectFiles;
	var projectFilesType;
	var userNames;
	$scope.addProjectFilesInfo="";
	 ///////分页获取项目列表
	$scope.getProjectList = function(pageSize,pageIndex,project) {
		ProjectFilesService.getProjectList(pageSize,pageIndex,project).then(function (result){
		  	  $scope.projectList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      //$scope.projectPage($scope.totalPage,$scope.currentPage);
		  });
	  }
//////初始化获取项目列表
	$scope.getProjectList(pageSize,$scope.currentPage,$scope.ProjectTofind);
	$scope.getProjectFilesList = function(pageSize,pageIndex,projectFiles) {
		 if(projectFilesType!="" && projectFilesType!=undefined && projectFilesType!=null){
			 projectFiles+= "&typeName=" +projectFilesType-1;
		 }
		ProjectFilesService.getProjectFilesList(pageSize,pageIndex,projectFiles).then(function (result){
		  	  $scope.projectFilesList = result.data;
		  });
	}
	$scope.getProjectFilesListByType = function(index){
		
		projectFilesType=index+1;
		$scope.getProjectFilesList(pageSize,pageIndex,projectFiles);
		$scope.contentTitle=$scope.typeNames[index];
		document.getElementById("test1").style.display = 'none';
		document.getElementById("filecontent").style.display = 'block';
		
	}
	$scope.goBack = function(){
		document.getElementById("test1").style.display = 'block';
		document.getElementById("filecontent").style.display = 'none';
	}
	/////////初始化任务单列表和项目列表,签收人列表
	//$scope.getConstructionTaskList(pageSize,pageIndex,null);
	//$scope.getProjectList(10,-1,null);
	//$scope.getUserList(pageSize,pageIndex,null);
	//////全选
	$scope.set_checkboxall = function(parentid){
		 var PID = document.getElementById(parentid);
	     var cb = PID.getElementsByTagName("input");
	         
		 if(!cb[0].checked){
			 for(var i=0;i<cb.length;i++){
				 if(cb[i].type == "checkbox"){
					 cb[i].checked = 1;
				 }
			 }
			 
		 }else{
			 for(var i=0;i<cb.length;i++){
				 if(cb[i].type == "checkbox"){
					 cb[i].checked = 0;
				 }
			 } 
		 }
	 };
	 var teamNames="";
	 var projectName="";
	 $scope.showAddPfHtml=function(){
		 layer.open({
		        type:1,
		        title: '工程资料上传',
		        area: ['600px','500px'],
		        btn:['确定','取消'],
		        yes:function(index,layero){
		        	
		        	$scope.fileList=layero.find('#pf_file')[0].files[0];
		        	userNames=layero.find('#fileType')[0].value;
		        	var projectName=layero.find('#projectName')[0].value;
		        	
		        	for(var j=0;j<$scope.projectList.length;j++){
		        		if($scope.projectList[j].name==projectName){
		        			$scope.projectId=$scope.projectList[j].id;
		        		}
		        	}
		        	$scope.addProjectFiles()
		        },
		        content:$("#show03").html()
		    });
		 	$scope.projectFileTitle="工程资料上传";
	 };
	 
	 $scope.addProjectFiles = function(){
			 var formData = new FormData();
			 if($scope.fileList!=""){
				 formData.append("fileList",$scope.fileList);
			 }
			 if($scope.projectId!=undefined){
				 formData.append("projectId",$scope.projectId);
			 }	  
			 if(userNames!=undefined){
				 formData.append("typeName",userNames);
			 }
			 ProjectFilesService.addProjectFiles(formData,token).then(function(result){
			       $scope.addProjectFilesInfo=result.data; 
			       $scope.getProjectFilesList(pageSize,pageIndex,null);
			    });
	 }
	 $scope.delete_choice=function(parentid){
		 var PID = document.getElementById(parentid);
	     var cb = PID.getElementsByTagName("input");
	     idList="";
		 for(var i=0;i<cb.length;i++){
			 if(cb[i].checked==1){
				 if(idList==""){
					 idList=$scope.constructionTaskLists[i].id;
				 }else{
					 idList=idList+","+$scope.constructionTaskLists[i].id; 
				 }
				 
			 }
		  }
		 $scope.deleteConstructionTask(idList);
	 };
	////////分页回调函数
	$scope.projectPage = function(iPageCount,iCurrent) {
		  $("#constructionPageCode").remove();
		  $("#table-buton_cons").append("<div id=\"constructionPageCode\"></div>");
		  
		  $("#constructionPageCode").createPage({
			  
		      pageCount:iPageCount,
	
		      current:iCurrent,
	
		      backFn:function(p){
		    	  console.log($scope.ConsTofind);
		    	  $scope.getConstructionTaskList(pageSize,p,$scope.ConsTofind);
		    	  
		      }
	
		  });
	 };
	 /////删除任务单
	 $scope.deleteConstructionTask = function(ids){
		 if(confirm("确定删除？")) {  
			 ConstructionTaskService.deleteConstructionTask(ids).then(function(result){
		       $scope.constructionTasks=result.data;
		       $scope.getConstructionTaskList(pageSize,1,constructionTask);   
		    });
		 }
	 }
	
	////////模糊查找施工任务单
	 $scope.findConsLike = function() {
		 $scope.currentPage = 1;
		 $scope.getConstructionTaskList(pageSize,$scope.currentPage,$scope.ConsTofind);
	 };
	
	 //////重置施工任务单搜索
    $scope.resetCon = function(){
   	 	$scope.ConsTofind = {};
    };
   
    $scope.constructionTaskChange = function(index){
    	$scope.ConsTofind.id=index;
    	$scope.getConstructionTaskLists(pageSize,$scope.currentPage,$scope.ConsTofind);
    	document.getElementById('constructionDetailHtml').style.display='block';
    	document.getElementById('projectContent').style.display='none';
    };
    
    /////导出任务单
    $scope.exportConstructionTask=function(){
    	var PID = document.getElementById('app');
	     var cb = PID.getElementsByTagName("input");
		 for(var i=0;i<cb.length;i++){
			 if(cb[i].checked==1){
				 ConstructionTaskService.exportConstructionTask($scope.constructionTaskLists[i].id).then(function (result){
				  	  $scope.constructionTaskUrl = result.data;
				  	document.getElementById('exportDiv').style.display='block';
				  });
				 break;
			 }
		  }
		
	 };
}
