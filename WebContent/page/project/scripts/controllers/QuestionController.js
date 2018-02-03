var index;
function QuestionController($scope,QuestionService) {
	console.log("载入QuestionController");
	var j=0;
    var k=0;
    var q=0;
    var p=0;
	var project="";
	var question="";
	var pageSize=10;
	$scope.findQuestionInfo = {};
	$scope.questionImgList=[];
	$scope.messageImgItem=[];
	var pageIndex=1;
	$scope.questionSelfId="";
	var questionId="";
	var content=null;
	$scope.currentPage = 1;
	$scope.findQuestionInfo = {};
	$scope.questionType="all";
	$scope.questionPority="all";
	$scope.questionStatu="all";
	$scope.questionProjectId="all";
	$scope.questionTitles=["序号","问题类型","问题提交人","问题标题","专业","问题创建时间","问题等级","问题状态","操作"];
	$scope.projectQuestionOfType=[{name:"安全"},{name:"质量"},{name:"其他"}];
	$scope.projectQuestionOfPriority=[{name:"一般"},{name:"重要"},{name:"紧急"}];
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
		$scope.getProjectLists(pageSize,-1,project);
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
     $scope.drawCircle = function() {
			  document.getElementById("myStat").attributes["data-percent"].value = $scope.questionList[0].sortPercent;
		      document.getElementById("myStat").attributes["data-text"].value = $scope.questionList[0].sortPercent + "%";
		      document.getElementById("myStat1").attributes["data-percent"].value = $scope.questionList[0].importantPercent;
		      document.getElementById("myStat1").attributes["data-text"].value = $scope.questionList[0].importantPercent + "%";
		      document.getElementById("myStat2").attributes["data-percent"].value = $scope.questionList[0].urgentPercent;
		      document.getElementById("myStat2").attributes["data-text"].value = $scope.questionList[0].urgentPercent + "%";
		      $('#myStat').circliful();
		  	  $('#myStat1').circliful();
		  	  $('#myStat2').circliful();
		 }
	  /////////////////////初始化问题列表数据
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

			      $scope.currentPage = result.currentPage;
			      $scope.totalPage = result.totalPage;
			      $scope.questionPage($scope.totalPage,$scope.currentPage);
			      $scope.drawCircle();
			      
			  });
		  }
	  /////////////////////返回问题列表
	  $scope.getQuestionListss = function(pageSize,pageIndex,question) {
		     
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

			      $scope.currentPage = result.currentPage;
			      $scope.totalPage = result.totalPage;
			      $scope.questionPage($scope.totalPage,$scope.currentPage);
			      
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
		 var title_problem=document.getElementById("inputpassword").value;////问题标题
		 var charse=document.getElementById("inputnick").value;///问题所属专业
		 if(title_problem==null || title_problem==undefined || title_problem==""){
			 alert("问题标题不能为空！");
			 return;
		 }
		 if(charse==null || charse==undefined || charse==""){
			 alert("问题所属专业不能为空！");
			 return;
		 }
		 if($scope.flag=="添加"){
			 var formData = new FormData();
			 for (var key in $scope.findQuestionInfo) {
				   if($scope.findQuestionInfo[key] != null) {
					   formData.append(key, $scope.findQuestionInfo[key]);
				   }
			 }
			 QuestionService.addQuestionByAdmin(formData).then(function(result){
			       $scope.questionInfo=result.data; 
			       $scope.getQuestionList(pageSize,1,question);
			    });
		 }
		 if($scope.flag=="更新"){
			 var formData = new FormData();
			 for (var key in $scope.findQuestionInfo) {
				   if($scope.findQuestionInfo[key] != null) {
					   formData.append(key, $scope.findQuestionInfo[key]);
				   }
			 }
			 formData.append('file',null);
			 QuestionService.updateQuestionByAdmin(formData).then(function(result){
			       $scope.questionInfo=result.data; 
			       $scope.getQuestionList(pageSize,1,question);
			      
			    });
		 }
			 
	 }
	 ////显示问题详细界面
	 $scope.questionChangeClick = function(questionId){
		    QuestionService.findQuestion(questionId).then(function(result){
		    	k=0;
		    	$scope.questionImgList=[];
			    $scope.findQuestionInfo=result.data;
		    	if($scope.findQuestionInfo.fileList!=null){
		  			var test=$scope.findQuestionInfo.fileList;
		  			for(var i=0;i<test.length;i++){
		  				if(test[i].split(".")[1]=="wav" || test[i].split(".")[1]=="mp3"){
		  				}else if(test[i].split(".")[1]!="dat"){
		  					$scope.questionImgList[k]=test[i];
		  					k++;
		  				}
		  			}
		  		}
		      $scope.findQuestionInfo=result.data;
		      $scope.flag=$scope.questionAddOrUpdate[1];
		      $scope.getMessageListByQuestionId(questionId);
		      document.getElementById("detailQuestionHtml").style.display = 'block';
			  document.getElementById("projectDetail_body_questions").style.display='none';
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
	 //////问题状态切换
	 $scope.questionStateChange = function(questionId,state){
		 if(state==1){
			 state=0;
		 }else{
			 state=1;
		 }
	     QuestionService.changeQuestionState(questionId,state).then(function(result){
	    	 $scope.getQuestionList(pageSize,pageIndex,question);
		    });
		 }
	 /////返回问题列表
	 $scope.returnQuestionlists = function(){
		 $scope.messageListInfo="";
		 document.getElementById("detailQuestionHtml").style.display = 'none';
	     document.getElementById("projectDetail_body_questions").style.display='block';
	     $scope.getQuestionListss(pageSize,pageIndex,question);
	    
	 }
	
	 ////隐藏问题添加页面
	 $scope.hideAddQuestionHtml = function(){
		 $scope.findQuestionInfo={};
		 document.getElementById("addQuestionHtml").style.display = 'none';
	     document.getElementById("projectDetail_body_questions").style.display='block';
	 }
	
	 //////隐藏留言更新页面
	 $scope.hideAddQuestionHtmls=function(){
		 $scope.messageInfo="";
		 document.getElementById("detailQuestionHtml").style.display = 'none';
		 document.getElementById("projectDetail_body_questions").style.display='block';
	 }
	 /////返回留言列表
	 $scope.returnMessagelist = function(){
		 $scope.messageInfo="";
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
		 if(confirm("确定删除？")) {  
			 QuestionService.deleteQuestion(questionId).then(function (result){
			  	  $scope.deleteQuestionInfo = result.data;
			  	  $scope.getQuestionList(pageSize,pageIndex,question);
			  });
		 }
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
	$scope.setQuestionPority = function(pority){
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
		if(confirm("确定删除？")) {  
			QuestionService.deleteMessage(messageId).then(function (result){
			  	  $scope.deleteMessageInfo = result.data;
			  	  $scope.getMessageListByQuestionId(questionId);
			  });
		}
	}
	//////修改留言
	$scope.changeMessage = function(messageId,questionId){
		QuestionService.findMessage(messageId).then(function(result){
		      $scope.findMessageInfo=result.data;
		      $scope.getMessageListByQuestionId(questionId);
		      document.getElementById("addMessageHtml").style.display = 'block';
		    });
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
	
}

function chooseImgMessage() {
	document.getElementById("imgMessge").click();
}