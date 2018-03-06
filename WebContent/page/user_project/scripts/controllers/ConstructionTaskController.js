////////////////////////////////
 var token=getCookie('token');

function setConsFiles(model) {
	angular.element(model).scope().$parent.$parent.modelFiles = model.files;
}

function ConstructionTaskController($scope,ConstructionTaskService) {
	$scope.a = 1;
	console.log("载入ConstructionTaskController");
	$scope.currentPage = 1;
	$scope.flagAll=-1;
	var constructionTask;
	$scope.constructionTaskLists="";
	$scope.ConsTofind = {};
	$scope.totalPage=1;
	var pageSize=10;
	var pageIndex=1;
	$scope.constructionTaskIdList="";
	$scope.constructionTaskInfo={};
	$scope.constructionTaskTitles=["序号","项目名称","签发人","任务单状态","当前进度","下一审批人","创建时间","操作"];
	$scope.constructionTaskFlag=["未完成","已完成"];
	$scope.conslogList=[];
	$scope.projectList=[];
	$scope.userList=[];
	$scope.constructionTaskTitle="";
	$scope.addConstructionTaskInfo = {};
	$scope.constructionfiles;
	$scope.constructionTaskUrl="";
	var project;
	var user;

	$scope.getProjectList = function(pageSize,pageIndex,project) {
		ConstructionTaskService.getProjectList(pageSize,pageIndex,project).then(function (result){
		  	  $scope.projectList = result.data;
		  });
	}
	$scope.getUserList = function(pageSize,pageIndex,user) {
		ConstructionTaskService.getUserList(pageSize,pageIndex,user).then(function (result){
		  	  $scope.userList = result.data;
		  });
	}
	$scope.getConstructionTaskList = function(pageSize,pageIndex,constructionTask) {
		ConstructionTaskService.getConstructionTaskList(pageSize,pageIndex,constructionTask).then(function (result){
		  	  $scope.constructionTaskLists = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.projectPage($scope.totalPage,$scope.currentPage);
		  });
	};
	/////////初始化任务单列表和项目列表,签收人列表
	$scope.getConstructionTaskList(pageSize,pageIndex,null);
	$scope.getProjectList(10,-1,null);
	$scope.getUserList(pageSize,pageIndex,null);
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
	 $scope.showAddConsHtml=function(){
		 layer.open({
		        type:1,
		        title: '施工任务单添加',
		        area: ['800px','700px'],
		        btn:['确定','取消'],
		        yes:function(index,layero){
		        	$scope.addConstructionTaskInfo={};
		        	$scope.addConstructionTaskInfo.teamName=layero.find('#teamName')[0].value;
		        	var projectNames=layero.find('#projectName')[0].value;
		        	$scope.addConstructionTaskInfo.companyName=layero.find('#projectName')[0].value;
		        	$scope.constructionfiles=layero.find('#cons_img')[0].files[0];
		        	var userNames=layero.find('#receivedName')[0].value;
		        	for(var i=0;i<$scope.userList.length;i++){
		        		if($scope.userList[i].realName==userNames){
		        			$scope.addConstructionTaskInfo.receiveUserId=$scope.userList[i].id;
		        		}
		        	}
		        	for(var j=0;j<$scope.projectList.length;j++){
		        		if($scope.projectList[j].name==projectNames){
		        			$scope.addConstructionTaskInfo.projectId=$scope.projectList[j].id;
		        		}
		        	}
		        	$scope.addConstructionTaskInfo.taskContent=layero.find('#taskContent')[0].value;
		        	$scope.addConstructionTaskInfo.finishedDate=layero.find('#finishedDate')[0].value;
		        	$scope.addConstructionTaskInfo.rewards=layero.find('#rewards')[0].value;
		        	$scope.addConstructionTaskInfo.detailContent=layero.find('#detailContent')[0].value;
		        	$scope.addConstruction();
		        },
		        content:$("#show03").html()
		    });
		 $scope.constructionTaskTitle="施工任务单添加";
	 };
	 $scope.showEditConsHtml=function(){
		 layer.open({
		        type:1,
		        title: '施工任务单更新',
		        area: '650px',
		        content:$("#show04").html()
		    });
		 $scope.constructionTaskTitle="施工任务单更新";
	 };
	 $scope.test = function(){
		 alert("ceshiyixia");
	 }
	 $scope.addConstruction = function(index){
		 if($scope.constructionTaskTitle=="施工任务单添加"){
			 var formData = new FormData();
			 for (var key in $scope.addConstructionTaskInfo) {
				   if($scope.addConstructionTaskInfo[key] != null) {
					   formData.append(key+"", $scope.addConstructionTaskInfo[key]);
				   }
			 }
			 if($scope.constructionfiles == undefined || $scope.constructionfiles == null) {
				 formData.append("files",null);
			 } else {
				 formData.append("files",$scope.constructionfiles);
			 }
			 console.log(formData.get("companyName"));
			 ConstructionTaskService.addConstructionTask(formData,token).then(function(result){
			       $scope.addConstructionsInfo=result.data; 
			       $scope.getConstructionTaskList(pageSize,pageIndex,null);
			    });
		 }
		 if($scope.constructionTaskTitle=="施工任务单更新")
		{
			 
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
			 ConstructionTaskService.updateProject(formData,token).then(function(result){
				 
			       $scope.updateProjectInfo=result.data;
			       document.getElementById("projectItemInfoAdd").style.display = 'block';
			       $scope.getProjectList(pageSize,1,$scope.ProjectTofind);
			    });
		}
		 
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
		    	  $scope.currentPage=p;
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
	$scope.getConstructionTaskLists = function(pageSize,pageIndex,constructionTask) {
		ConstructionTaskService.getConstructionTaskList(pageSize,pageIndex,constructionTask).then(function (result){
		  	  $scope.constructionTaskInfos = result.data;
		  	  if($scope.constructionTaskInfos!={}){
		  		  if($scope.constructionTaskInfos[0].approvalPeopleNameList!=null){
		  			  for(var i=0;i<$scope.constructionTaskInfos[0].approvalPeopleNameList.length;i++){
		  				  	$scope.conslogitem={"type":"","name":"","idea":"","date":"","note":""};
		  					$scope.conslogitem.type=$scope.constructionTaskInfos[0].approvalPeopleTypeList[i];
		  					$scope.conslogitem.name=$scope.constructionTaskInfos[0].approvalPeopleNameList[i];
		  					$scope.conslogitem.idea=$scope.constructionTaskInfos[0].approvalPeopleIdeaList[i];
		  					$scope.conslogitem.date=$scope.constructionTaskInfos[0].approvalDateList[i];
		  					$scope.conslogitem.note=$scope.constructionTaskInfos[0].approvalPeopleNoteList[i];
		  					$scope.conslogList[i]=$scope.conslogitem;
		  			  }
		  		  }
		  		  
		  	  }
		  	  if($scope.currentPage==1){
		  		$scope.currentPage = result.currentPage;
		  	  }
		  	 $scope.totalPage = result.totalPage;
		      $scope.projectPage($scope.totalPage,$scope.currentPage);
		  });
	};
	$scope.getConstructionTaskListss = function(pageSize,pageIndex,constructionTask) {
		ConstructionTaskService.getConstructionTaskList(pageSize,pageIndex,constructionTask).then(function (result){
		  	  $scope.constructionTaskInfos = result.data;
		  	  if($scope.constructionTaskInfos!={}){
		  		  if($scope.constructionTaskInfos[0].approvalPeopleNameList!=null){
		  			  for(var i=0;i<$scope.constructionTaskInfos[0].approvalPeopleNameList.length;i++){
		  				  	$scope.conslogitem={"type":"","name":"","idea":"","date":"","note":""};
		  					$scope.conslogitem.type=$scope.constructionTaskInfos[0].approvalPeopleTypeList[i];
		  					$scope.conslogitem.name=$scope.constructionTaskInfos[0].approvalPeopleNameList[i];
		  					$scope.conslogitem.idea=$scope.constructionTaskInfos[0].approvalPeopleIdeaList[i];
		  					$scope.conslogitem.date=$scope.constructionTaskInfos[0].approvalDateList[i];
		  					$scope.conslogitem.note=$scope.constructionTaskInfos[0].approvalPeopleNoteList[i];
		  					$scope.conslogList[i]=$scope.conslogitem;
		  			  }
		  		  }
		  		  
		  	  }
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.projectPage($scope.totalPage,pageIndex);
		  });
	};
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
    	$scope.getConstructionTaskLists(pageSize,1,$scope.ConsTofind);
    	document.getElementById('constructionDetailHtml').style.display='block';
    	document.getElementById('projectContent').style.display='none';
    };
    $scope.setConstructionTaskId=function(index){
    	if($scope.constructionTaskIdList==""){
    		$scope.constructionTaskIdList=index;
    	}else{
    		$scope.constructionTaskIdList=$scope.constructionTaskIdList+","+index;
    	}
    };
    $scope.goConstructionList = function(){
    	$scope.ConsTofind = {};
    	$scope.getConstructionTaskListss(pageSize,$scope.currentPage,null);
    	document.getElementById('constructionDetailHtml').style.display='none';
    	document.getElementById('projectContent').style.display='block';
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
