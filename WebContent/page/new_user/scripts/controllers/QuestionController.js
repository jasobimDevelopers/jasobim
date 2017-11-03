////////////////项目详情信息和项目基本信息页面切换
var index;
function QuestionController($scope,QuestionService) {
	console.log("载入QuestionController");
	var project="";
	var question="";
	var pageSize=10;
	var pageIndex=1;
	$scope.questionSelfId="";
	var questionId="";
	var userId="";
	var content=null;
	$scope.currentPage = 1;
	$scope.findQuestionInfo = {};
	$scope.questionType="all";
	$scope.questionPority="all";
	$scope.questionStatu="all";
	$scope.questionProjectId="all";
	$scope.questionTitles=["序号","问题类型","问题提交人","问题标题","专业","问题创建时间","问题等级","问题状态","操作"];
	$scope.projectQuestionOfType=[{name:"安全"},{name:"质量"},{name:"其他"}];
	////一般用户等级查询列表
	$scope.projectQuestionOfPriority=[{name:"一般"},{name:"重要"},{name:"紧急"}];
	$scope.projectQuestionOfPrioritys=[{name:"一般"},{name:"重要"},{name:"紧急"}];
	
	$scope.projectQuestionOfStatus=[{name:"待解决"},{name:"已解决"}];
	$scope.percentOfQuestion=[{name:"一般",value:""},{name:"重要",value:""},{name:"紧急",value:""}];
	$scope.questionAddOrUpdate=["添加","更新"];
	$scope.flag="";
	$scope.restStyle = function (a,b,c,d) {
		a ? document.getElementById("questionOfProject").style.display = 'none' :'';
		b ? document.getElementById("questionOfType").style.display = 'none' : '';
		c ? document.getElementById("questionOfPority").style.display = 'none': '';
		d ? document.getElementById("questionOfStatu").style.display = 'none': '';
	}
	$scope.showProjects=function(){
		$scope.getProjectLists(pageSize,pageIndex,project);
		$scope.restStyle(false,true,true,true);
		var idom = document.getElementById("questionOfProject");
		console.log(idom.style.display);
		if(idom.style.display == 'none') {
			idom.style.display = 'block';
		} else {
			idom.style.display = 'none';
		}
	}
	
	
	$scope.showQuestionType=function(){
		$scope.restStyle(true,false,true,true);
		var idom = document.getElementById("questionOfType");
		if(idom.style.display == 'none') {
			idom.style.display = 'block';
		} else {
			idom.style.display = 'none';
		}
	}
	$scope.showQuestionPority=function (){
		$scope.restStyle(true,true,false,true);
		var idom = document.getElementById("questionOfPority");
		if(idom.style.display == 'none') {
			idom.style.display = 'block';
		} else {
			idom.style.display = 'none';
		}
	}
	$scope.showQuestionStatu=function (){
		$scope.restStyle(true,true,true,false);
		var idom = document.getElementById("questionOfStatu");
		if(idom.style.display == 'none') {
			idom.style.display = 'block';
		} else {
			idom.style.display = 'none';
		}
	}
	 ////////问题分页回调函数
	  $scope.questionPage = function(iPageCount,iCurrent) {
		  $("#questionsPageCode").remove();
		  $("#table-buton10").append("<div id=\"questionsPageCode\"></div>");
		  $("#questionsPageCode").createPage({

		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  $scope.getQuestionList(pageSize,p,question);
		      }
		  });
	  }


	  $scope.getQuestionLists = function(pageSize,pageIndex,question) {
		     
			 if($scope.questionType!="all"){
				 question+= "questionType=" + $scope.questionType;
			 }
			 if($scope.questionPority!="all"){
				 question+= "priority=" + $scope.questionPority;
			 }
			 if($scope.questionStatu!="all"){
				 question+= "state=" + $scope.questionStatu;
			 }
			 if($scope.questionProjectId!="all"){
				 question+= "projectId=" +$scope.questionProjectId;
			 }
			 QuestionService.getQuestionList(pageSize,pageIndex,question,null).then(function (result){
			  	  $scope.questionList = result.data;
			  	  if($scope.questionList[0].roleFlag==1){
			  		  ////总经理等级查询列表
			  		$scope.projectQuestionOfPrioritys=[{name:"重要"},{name:"紧急"}];
			  	  }
			  	  if($scope.questionList[0].roleFlag==2){
			  		 ////投资方等级查询列表
			  		$scope.projectQuestionOfPrioritys=[{name:"一般"}];
			  	  }
			      $scope.currentPage = result.currentPage;
			      $scope.totalPage = result.totalPage;
			      $scope.questionPage($scope.totalPage,$scope.currentPage);

			      $scope.refreshCircle();
			  });
		  }
	   
	  	////////////////////////问题列表分页获取
		$scope.getQuestionList = function(pageSize,pageIndex,question) {
			 content=document.getElementById("paperList_find").value;
			 if($scope.questionType!="all"){
				 question+= "questionType=" + $scope.questionType;
			 }
			 if($scope.questionPority!="all"){
				 question+= "priority=" + $scope.questionPority;
			 }
			 if($scope.questionStatu!="all"){
				 question+= "state=" + $scope.questionStatu;
			 }
			 if($scope.questionProjectId!="all"){
				 question+= "projectId=" +$scope.questionProjectId;
			 }
			 QuestionService.getQuestionList(pageSize,pageIndex,question,content).then(function (result){
			  	  $scope.questionList = result.data;
			      $scope.currentPage = result.currentPage;
			      $scope.totalPage = result.totalPage;
			      $scope.questionPage($scope.totalPage,$scope.currentPage);
			      $scope.refreshCircle();
			  });
		  }
		 ////////////////////////我的问题列表分页获取
		 $scope.getQuestionListOfMy = function() {
			 QuestionService.getQuestionListByUserId(pageSize,pageIndex).then(function (result){
			  	  $scope.questionList = result.data;
			      $scope.currentPage = result.currentPage;
			      $scope.totalPage = result.totalPage;
			      $scope.questionPage($scope.totalPage,$scope.currentPage);
			      document.getElementById("changeQuestion").style.display = 'block';
			  });
		  }
	 /////初始化获取问题列表
	 $scope.getQuestionLists(pageSize,pageIndex,question);
	 ////////问题重置
	 $scope.resetQuestion = function(){
		 $scope.findQuestionInfo={};
	 }
	
	 /////增加问题
	 $scope.addQuestionByAdmin = function(){
		 if($scope.flag=="添加"){
			 var formData = new FormData();
			 for (var key in $scope.findQuestionInfo) {
				   if($scope.findQuestionInfo[key] != null) {
					   formData.append(key, $scope.findQuestionInfo[key]);
				   }
			 }
			/* var fileCode="";
			 var fileCode=document.getElementById("codeImg").files;*/
			 var file="";
	    	 file=document.getElementById("inputicon").files;
	    	/* if(fileCode!=null && fileCode.length>0){
	    		 formData.append("fileCode",fileCode[0]);
	    	 }*/
	    	 if(file!==null && file.length>0){
				 for(var i = 0; i < file.length;i++) {
					 formData.append("file",file[i]);
				 }
	    	 }
			 QuestionService.addQuestionByAdmin(formData).then(function(result){
			       $scope.questionInfo=result.data; 
			      // $scope.removeState();
			       $scope.questionList=[];
			      // $scope.appendDiv();
//			       $scope.getQuestionList(pageSize,1,question);
//			       $scope.refreshData();
			       //window.navigate(location);
			       
			    });
		 }
		 if($scope.flag=="更新"){
			 var file="";
			 var formData = new FormData();
			 for (var key in $scope.findQuestionInfo) {
				   if($scope.findQuestionInfo[key] != null && key!="questionDate") {
					   formData.append(key, $scope.findQuestionInfo[key]);
				   }
			 }
			 formData.append('file',null);
			 QuestionService.updateQuestionByAdmin(formData).then(function(result){
			       $scope.questionInfo=result.data; 
			       //$scope.removeState();
			       $scope.questionList=[];
			      // $scope.appendDiv();
			       $scope.getQuestionList(pageSize,1,question);
//			       $scope.refreshData();
			       //window.navigate(location);
			      
			    });
		 }
			 
	 }
	 
	 ////显示问题详细界面
	 $scope.questionChangeClick = function(questionId){
		    QuestionService.findQuestion(questionId).then(function(result){
		      $scope.findQuestionInfo=result.data;
		      $scope.flag=$scope.questionAddOrUpdate[1];
		      $scope.getMessageListByQuestionId(questionId);
		      document.getElementById("detailQuestionHtml").style.display = 'block';
			  document.getElementById("projectDetail_body_questions").style.display='none';
		    });
		 }
	 ////显示问题修改界面
	 $scope.questionChangeClicks = function(questionId,userId){
		 	
		    QuestionService.findQuestion(questionId).then(function(result){
		      var userid=result.data.userid;
		      if(userid==1){
		    	  $scope.findQuestionInfo=result.data;
			      $scope.flag=$scope.questionAddOrUpdate[1];
			      $scope.getMessageListByQuestionId(questionId);
			      document.getElementById("editQuestionHtml").style.display = 'block';
				  document.getElementById("projectDetail_body_questions").style.display='none';
		      }else{
		    	alert("你无权修改此问题！");  
		      }
		    });
     }
	 //////问题状态切换
	 $scope.questionStateChange = function(questionId,state){
		 if(state==1){
			 state=0;
		 }else{
			 state=1;
		 }
	     QuestionService.changeQuestionState(questionId,state).then(function(result){
	    	 $scope.getQuestionList(pageSize,pageIndex,question);
	    	 //window.navigate(location);

		    });
		 }
	 /////显示增加界面
	 $scope.showAddQuestionHtml = function(){
		 $scope.findQuestionInfo={};
		 $scope.flag=$scope.questionAddOrUpdate[0];
		 $scope.getProjectLists(pageSize,pageIndex,project);
		 document.getElementById("addQuestionHtml").style.display = 'block';
	     document.getElementById("projectDetail_body_questions").style.display='none';
	 }
	 /////返回问题列表
	 $scope.returnQuestionlist = function(){
		 
		 $scope.findQuestionInfo={};
		 document.getElementById("addQuestionHtml").style.display = 'none';
	     document.getElementById("projectDetail_body_questions").style.display='block';
	     $scope.getQuestionList(pageSize,pageIndex,question);
	     
	 }
	 /////返回问题列表
	 $scope.returnQuestionlists = function(){
		 //location.reload(true);
		 document.getElementById("detailQuestionHtml").style.display = 'none';
		 document.getElementById("editQuestionHtml").style.display = 'none';
	     document.getElementById("projectDetail_body_questions").style.display='block';
	     
	     $scope.getQuestionList(pageSize,pageIndex,question);
	 }
	
	 ////隐藏问题添加页面
	 $scope.hideAddQuestionHtml = function(){
		 $scope.findQuestionInfo={};
		 document.getElementById("addQuestionHtml").style.display = 'none';
	     document.getElementById("projectDetail_body_questions").style.display='block';
	 }
	
	 //////隐藏留言更新页面
	 $scope.hideAddQuestionHtmls=function(){
		 document.getElementById("detailQuestionHtml").style.display = 'none';
		 document.getElementById("projectDetail_body_questions").style.display='block';
	 }
	 /////返回留言列表
	 $scope.returnMessagelist = function(){
		 document.getElementById("addMessageHtml").style.display = 'none';
		 $scope.getMessageListByQuestionId($scope.questionSelfId);
	 }
	 /////重置留言内容
	 $scope.resetMessage= function(){
		 document.getElementById("inputContent").value = "";
	 }
	 //////留言更新
	 $scope.updateMessageByAdmin = function(messageId,questionId){
		 var formData = new FormData();
		 var content=document.getElementById("inputContent").value;
		 formData.append('questionId',questionId);
		 formData.append('id',messageId);
		 formData.append('content',content);
		 QuestionService.updateMessageByAdmin(formData).then(function(result){
		       $scope.messageInfo=result.data; 
		       $scope.getMessageListByQuestionId($scope.questionSelfId);
		      
		    });
	 }
	 ///////问题删除
	 $scope.deleteQuestion = function(questionId){
		 QuestionService.deleteQuestion(questionId).then(function (result){
		  	  $scope.deleteQuestionInfo = result.data;
		  	  $scope.getQuestionLists(pageSize,pageIndex,question);
		  });
	  }

	 ///////分页获取项目列表
	$scope.getProjectLists = function(pageSize,pageIndex,project) {
		  QuestionService.getProjectLists(pageSize,pageIndex,project).then(function (result){
		  	  $scope.projectLists = result.data;
		  });
	  }
	////////问题列表的查询
	/////通过项目名称查询
	$scope.setProjectName = function(projectId){
		$scope.questionProjectId=projectId;
		$scope.questionType="all";
		$scope.questionPority="all";
		$scope.questionStatu="all";
		$scope.getQuestionList(pageSize,pageIndex,question);
		document.getElementById("questionOfProject").style.display = 'none';
		
	}
	/////通过问题的类型查询
	$scope.setQuestionType = function(type){
		$scope.questionType=type;
		$scope.questionPority="all";
		$scope.questionStatu="all";
		$scope.questionProjectId="all";
		$scope.getQuestionList(pageSize,pageIndex,question);
		document.getElementById("questionOfType").style.display = 'none';
		
	}
	/////通过问题的等级查询
	$scope.setQuestionPority = function(it){
		if(it.pority.name=="一般"){
			pority=0;
		}
		if(it.pority.name=="重要"){
			pority=1;
		}
		if(it.pority.name=="紧急"){
			pority=2;
		}
		$scope.questionPority=pority;
		$scope.questionType="all";
		$scope.questionStatu="all";
		$scope.questionProjectId="all";
		$scope.getQuestionList(pageSize,pageIndex,question);
		document.getElementById("questionOfPority").style.display = 'none';
		
	}
	////通过问题的状态查询
	$scope.setQuestionStatu = function(statu){
		$scope.questionStatu=statu;
		$scope.questionType="all";
		$scope.questionPority="all";
		$scope.questionProjectId="all";
		$scope.getQuestionList(pageSize,pageIndex,question);
		document.getElementById("questionOfStatu").style.display = 'none';
		
	}
	/////问题搜索
	$scope.search = function(){
		var content=document.getElementById("paperList_find").value;
		QuestionService.getQuestionList(pageSize,pageIndex,question,content).then(function (result){
		  	  $scope.questionList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.questionPage($scope.totalPage,$scope.currentPage);
		      
		  });
	};
	//////通过问题id获取该问题的所有留言
	$scope.getMessageListByQuestionId = function(questionId){
		$scope.questionSelfId=questionId;
		QuestionService.getMessageListByQuestionId(questionId).then(function (result){
		  	  $scope.messageListInfo = result.data;
		  });
	}
	///////根据问题id添加问题的留言
	$scope.addMessage = function(questionId){
		var formData = new FormData();
		var content=document.getElementById("messageArea").value;
		formData.append("content", content);
		formData.append("questionId", questionId);
		/*formData.append("file","");*/
		QuestionService.addMessage(formData).then(function (result){
		  	  $scope.addMessageInfo = result.data;
		  	  $scope.getMessageListByQuestionId(questionId);
		  });
	}
	///////删除留言
	$scope.deleteMessage = function(messageId,questionId){
		QuestionService.deleteMessage(messageId).then(function (result){
		  	  $scope.deleteMessageInfo = result.data;
		  	  $scope.getMessageListByQuestionId(questionId);
		  });
	}
	//////修改留言
	$scope.changeMessage = function(messageId,questionId){
		QuestionService.findMessage(messageId).then(function(result){
		      $scope.findMessageInfo=result.data;
		      $scope.getMessageListByQuestionId(questionId);
		      document.getElementById("addMessageHtml").style.display = 'block';
			 // document.getElementById("projectDetail_body_questions").style.display='none';
		    });
	}
   //////移除、加载圆饼统计模块
	$scope.refreshCircle = function() {
		$scope.removeState();
		$scope.appendDiv();
		document.getElementById("myStats").attributes["data-percent"].value = $scope.questionList[0].sortPercent;
	    document.getElementById("myStats1").attributes["data-percent"].value = $scope.questionList[0].importantPercent;
	    document.getElementById("myStats2").attributes["data-percent"].value = $scope.questionList[0].urgentPercent;
	    $('#myStats').circliful();
	  	$('#myStats1').circliful();
	  	$('#myStats2').circliful();	
	}
	$scope.removeState = function(){
		
		$("#myStats").remove();
		$("#myStats1").remove();
		$("#myStats2").remove();
		
	}
	/////加载
	$scope.appendDiv = function(){
		$("#dataShow").append("<div id=\"myStats\" data-dimension=\"180\"  data-text=\"" + $scope.questionList[0].sortPercent +"%\" data-info=\"一般\" data-width=\"30\" data-fontsize=\"38\" data-percent=\"50\" data-fgcolor=\"#F55852\" data-bgcolor=\"#FDD4D2\" data-fill=\"#ddd\"></div>"
				+"<div id=\"myStats1\" data-dimension=\"180\"  data-text=\"" + $scope.questionList[0].importantPercent + "%\" data-info=\"重要\" data-width=\"30\" data-fontsize=\"38\" data-percent=\"33\" data-fgcolor=\"#FDDA3D\" data-bgcolor=\"#FBF9E6\" data-fill=\"#ddd\"></div>"
				+"<div id=\"myStats2\" data-dimension=\"180\"  data-text=\"" + $scope.questionList[0].urgentPercent + "%\" data-info=\"紧急\" data-width=\"30\" data-fontsize=\"38\" data-percent=\"17\" data-fgcolor=\"#9DED55\" data-bgcolor=\"#E5FFD1\" data-fill=\"#ddd\"></div>");
	}
}