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
	$scope.projectTitles=["序号","项目名称","施工单位","负责人","施工地点","施工时间","施工周期","操作"];
	$scope.typeNames=["图纸会审","施工组织设计","专项方案","签证资料","人员备案（特殊工种）","花名册","施工日志",
	                  "图纸深化及翻样","施工资料","竣工验收资料","竣工图","材料复检报告","第三方强制检测","样板实施计划","材料进度计划"];
	$scope.projectFilesTitle=["序号","项目名称","文件名","上传时间","操作"];
	$scope.contentTitle="";
	$scope.projectList="";
	$scope.projectFiles="";
	$scope.fileList="";
	$scope.projectId="";
	var projectFiles;
	var projectFilesType;
	var userNames;
	$scope.currentPage = 1;
    $scope.totalPage = 1;
	$scope.addProjectFilesInfo="";
	$scope.projectFilesInfo="";
	$scope.pfName="";
	 ///////分页获取项目列表
	$scope.getProjectList = function(pageSize,pageIndex,project) {
		ProjectFilesService.getProjectList(pageSize,-1,project).then(function (result){
		  	  $scope.projectList = result.data;
		     // $scope.projectPage($scope.totalPage,$scope.currentPage);
		  });
	  }
//////初始化获取项目列表
	$scope.getProjectList(pageSize,$scope.currentPage,$scope.ProjectTofind);
	$scope.getProjectFilesList = function(pageSize,pageIndex,projectFiles) {
		 if(projectFilesType!="" && projectFilesType!=undefined && projectFilesType!=null){
			 projectFiles=("&typeName=" +(projectFilesType-1));
		 }
		 if($scope.projectId!=null && $scope.projectId!="" && $scope.projectId!=undefined){
			 projectFiles+=("&projectId="+$scope.projectId);
		 }
		 if($scope.pfName!=""){
			 projectFiles+=("&test="+$scope.pfName);
		 }
		ProjectFilesService.getProjectFilesList(pageSize,pageIndex,projectFiles).then(function (result){
		  	  $scope.projectFilesList = result.data;
		  	  $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		  	  $scope.pfPage($scope.totalPage,$scope.currentPage);
		  });
	}
	$scope.getProjectFilesListByType = function(index){
		
		projectFilesType=index+1;
		$scope.getProjectFilesList(pageSize,pageIndex,projectFiles);
		$scope.contentTitle=$scope.typeNames[index];
		document.getElementById("test1").style.display = 'none';
		document.getElementById("filecontent").style.display = 'block';		
	}
	$scope.goFiles = function(projectId){
		$scope.projectId=projectId;
		document.getElementById("contentAllFiles").style.display = 'block';
		document.getElementById("content1").style.display = 'none';
	}
	$scope.goBack = function(){
		document.getElementById("test1").style.display = 'block';
		document.getElementById("filecontent").style.display = 'none';
	}
	$scope.gohead = function(){
		document.getElementById("contentAllFiles").style.display = 'none';
		document.getElementById("content1").style.display = 'block';
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
		        	
		        	$scope.fileList=layero.find('#pf_file')[0].files;
		        	userNames=layero.find('#fileType')[0].value;
		        	var projectName=layero.find('#projectName')[0].value;
		        	
		        	for(var j=0;j<$scope.projectList.length;j++){
		        		if($scope.projectList[j].name==projectName){
		        			$scope.projectId=$scope.projectList[j].id;
		        		}
		        	}
		        	$scope.UpladFile();
		        },
		        content:$("#show03").html()
		    });
		 	$scope.projectFileTitle="工程资料上传";
	 };
	//上传文件方法
     $scope.UpladFile=function () {
    	 document.getElementById("process").style.display = 'block';
    	 
    	 var form = new FormData(); // FormData 对象
         var url = "api/projectFiles/admin/uploadProjectFiles"; // 接收上传文件的后台地址 
         if($scope.projectId!=undefined){
        	 form.append("projectId",$scope.projectId);
		 }	
         if(userNames!=undefined){
        	 form.append("typeName",userNames);
		 }
         if($scope.fileList!==null && $scope.fileList.length>0){
			 for(var i = 0; i < $scope.fileList.length;i++) {
				 form.append("fileList",$scope.fileList[i]);
			 }
         }
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
    	 $scope.getProjectFilesList(pageSize,pageIndex,null);
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
					 idList=$scope.projectFilesList[i].id;
				 }else{
					 idList=idList+","+$scope.projectFilesList[i].id; 
				 }
				 
			 }
		  }
		 $scope.deletePf(idList);
	 };
////////项目列表分页回调函数
	  $scope.projectPage = function(iPageCount,iCurrent) {
		  $("#projectFilesPageCode").remove();
		  $("#table-buton-projectFiles").append("<div id=\"projectFilesPageCode\"></div>");
		  
		  $("#projectFilesPageCode").createPage({
			  
		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  console.log($scope.ProjectTofind);
		    	  $scope.getProjectList(pageSize,p,$scope.ProjectTofind);
		    	  
		      }

		  });
	  }
	////////工程资料分页回调函数
	$scope.pfPage = function(iPageCount,iCurrent) {
		  $("#pfPageCode").remove();
		  $("#table-buton_pf").append("<div id=\"pfPageCode\"></div>");
		  
		  $("#pfPageCode").createPage({
			  
		      pageCount:iPageCount,
	
		      current:iCurrent,
	
		      backFn:function(p){
		    	  $scope.getProjectFilesList(pageSize,p,projectFiles);
		    	  
		      }
	
		  });
	 };
	 /////删除工程资料
	 $scope.deletePf = function(ids){
		 if(confirm("确定删除？")) {  
			 ProjectFilesService.deletePf(ids).then(function(result){
		       $scope.projectFilesInfo=result.data;
		       $scope.getProjectFilesList(pageSize,1,projectFiles);   
		    });
		 }
	 }
	
	////////根据文件名查找工程资料文件
	 $scope.findPfLike = function() {
		 $scope.currentPage = 1;
		 $scope.getProjectFilesList(pageSize,$scope.currentPage,$scope.projectFiles);
	 };
	
    
}
