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
	$scope.questionImgList=[];
	$scope.messageImgItem=[];
	$scope.questionType="全部";
	$scope.questionPority="全部";
	$scope.questionStatu="全部";
	$scope.questionProjectId="全部";
	$scope.projectQuestionOfType=[{name:"安全"},{name:"质量"},{name:"其他"}];
	$scope.questionTitles=["序号","问题类型","问题提交人","问题标题","专业","问题创建时间","问题等级","问题状态","操作"];
	////一般用户等级查询列表
	$scope.projectQuestionOfPriority=[{name:"一般"},{name:"重要"},{name:"紧急"}];
	$scope.projectQuestionOfPrioritys=[{name:"一般"},{name:"重要"},{name:"紧急"}];
	
	$scope.projectQuestionOfStatus=[{name:"待解决"},{name:"已解决"}];
	$scope.percentOfQuestion=[{name:"一般",value:""},{name:"重要",value:""},{name:"紧急",value:""}];
	$scope.questionAddOrUpdate=["添加","更新"];
	$scope.flag="";
	$scope.questionfiles=null;
	$scope.questionTitle="";
	$scope.messageImgList=[];
    $scope.messageVoiceList=[];
    $scope.messageList=[];
    $scope.messageLists=[];
    $scope.messageImgItem=[];
    $scope.messageVoiceItem=[];
    $scope.projectNameList=[];
    $scope.flag=["项目名称","问题类型","问题程度","问题状态"];
	var j=0;
    var k=0;
    var q=0;
    var p=0;
	
	 ////////问题分页回调函数
	  $scope.questionPage = function(iPageCount,iCurrent) {
		  $("#questionPageCode").remove();
		  $("#table-buton-question").append("<div id=\"questionPageCode\"></div>");
		  $("#questionPageCode").createPage({

		      pageCount:iPageCount,

		      current:iCurrent,

		      backFn:function(p){
		    	  
		    	  $scope.getQuestionLists(pageSize,p,question);
		      }
		  });
	  }
	  $scope.getQuestionLists = function(pageSize,pageIndex,question) {
		     
			 if($scope.questionType!="全部"){
				 question+= "questionType=" + $scope.questionType;
			 }
			 if($scope.questionPority!="全部"){
				 question+= "priority=" + $scope.questionPority;
			 }
			 if($scope.questionStatu!="全部"){
				 question+= "state=" + $scope.questionStatu;
			 }
			 if($scope.questionProjectId!="全部"){
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
			 content=document.getElementById("project_question_search").value;
			 if($scope.questionType!="全部"){
				 question+= "questionType=" + $scope.questionType;
			 }
			 if($scope.questionPority!="全部"){
				 question+= "priority=" + $scope.questionPority;
			 }
			 if($scope.questionStatu!="全部"){
				 question+= "state=" + $scope.questionStatu;
			 }
			 if($scope.questionProjectId!="全部"){
				 question+= "projectId=" +$scope.questionProjectId;
			 }
			 QuestionService.getQuestionList(pageSize,pageIndex,question,content).then(function (result){
			  	  $scope.questionList = result.data;
			      $scope.currentPage = result.currentPage;
			      $scope.totalPage = result.totalPage;
			      $scope.questionPage($scope.totalPage,$scope.currentPage);
			      $scope.drawCircle();
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
	 $scope.getQuestionList(pageSize,pageIndex,question);
	 ////////问题重置
	 $scope.resetQuestion = function(){
		 $scope.findQuestionInfo={};
	 }
	
	 /////增加问题
	 $scope.addQuestionByAdmin = function(){
		 if($scope.questionTitle=="质量安全问题添加"){
			 var formData = new FormData();
			 for (var key in $scope.addQuestionInfo) {
				   if($scope.addQuestionInfo[key] != null) {
					   formData.append(key, $scope.addQuestionInfo[key]);
				   }
			 }
	    	 if($scope.questionfiles!==null && $scope.questionfiles.length>0){
				 for(var i = 0; i < $scope.questionfiles.length;i++) {
					 formData.append("file",$scope.questionfiles[i]);
				 }
	    	 }
			 QuestionService.addQuestionByAdmin(formData).then(function(result){
			       $scope.questionInfo=result.data; 
			       $scope.getQuestionLists(pageSize,pageIndex,question);
			       $scope.questionList=[];
			    });
		 }
		 if($scope.questionTitle=="更新"){
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
			       $scope.questionList=[];
			       $scope.getQuestionLists(pageSize,1,question);
			    });
		 }
			 
	 }
	 ////显示问题详细界面
	 $scope.questionChangeClick = function(questionId){
		 $scope.questionImgList=[];
		 $scope.messageListInfo="";
		    QuestionService.findQuestion(questionId).then(function(result){
		      k=0;
		      $scope.findQuestionInfo=result.data;
		      if($scope.questionInfo!=""){
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
			  		$scope.questionId=$scope.findQuestionInfo.id;
			  		$scope.flag=$scope.questionAddOrUpdate[1];
				    $scope.getMessageListByQuestionId(questionId);
			  		var length=($scope.findQuestionInfo.userId).length;
			  		$scope.name=($scope.findQuestionInfo.userId).substr(length-2);
		  	  }
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
	    	 $scope.getQuestionLists(pageSize,$scope.currentPage,question);
	    	 //$scope.questionPage(iPageCount,iCurrent);
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
	     $scope.getQuestionLists(pageSize,pageIndex,question);
	     
	 }
	 /////返回问题列表
	 $scope.returnQuestionlists = function(){
		 //location.reload(true);
		 document.getElementById("detailQuestionHtml").style.display = 'none';
		 document.getElementById("editQuestionHtml").style.display = 'none';
	     document.getElementById("projectDetail_body_questions").style.display='block';
	     
	     $scope.getQuestionLists(pageSize,pageIndex,question);
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
		  QuestionService.getProjectLists(pageSize,-1,project).then(function (result){
		  	  $scope.projectLists = result.data;
		  });
	  }
	////////问题列表的查询
	
	 $scope.getProjectLists(pageSize,pageIndex,project);
	
	/////显示添加页面
	$scope.showAddQuestionHtml=function(){
		 layer.open({
		        type:1,
		        title: '质量安全问题添加',
		        area: ['700px','600px'],
		        btn:['确定','取消'],
		        yes:function(index,layero){
		        	$scope.addQuestionInfo={};
		        	$scope.addQuestionInfo.name=layero.find('#questionTitle')[0].value;
		        	var projectNames=layero.find('#projectName')[0].value;
		        	$scope.questionfiles=layero.find('#questionPics')[0].files;
		        	var questionPority=layero.find('#questionPority')[0].value;
		        	for(var k=0;k<$scope.projectQuestionOfPriority.length;k++){
		        		if($scope.projectQuestionOfPriority[k].name==questionPority){
		        			$scope.addQuestionInfo.questionPority=k;
		        		}
		        	}
		        	var questionType=layero.find('#questionType')[0].value;
		        	for(var i=0;i<$scope.projectQuestionOfType.length;i++){
		        		if($scope.projectQuestionOfType[i].name==questionType){
		        			$scope.addQuestionInfo.questionType=i;
		        		}
		        	}
		        	for(var j=0;j<$scope.projectLists.length;j++){
		        		if($scope.projectLists[j].name==projectNames){
		        			$scope.addQuestionInfo.projectId=$scope.projectLists[j].id;
		        		}
		        	}
		        	$scope.addQuestionInfo.trades=layero.find('#questionPhase')[0].value;
		        	$scope.addQuestionInfo.questionDetail=layero.find('#questionDetail')[0].value;
		        	$scope.questionTitle="质量安全问题添加";
		        	$scope.addQuestionByAdmin();
		        	
		        },
		        content:$("#show03s").html()
		    });
		
	 };
	
	
	/////问题搜索
	$scope.findQuestionLike = function(){
		var content=document.getElementById("project_question_search").value;
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
		  	  if($scope.messageListInfo!=""){
		  		for(var g=0;g<$scope.messageListInfo.length;g++){
					$scope.messageList=[];
		  			$scope.messageImgItem=[];
		  			$scope.messageVoiceItem=[];
					q=0;
					p=0;	
		  			if($scope.messageListInfo[g].fileList!=null){
			  			var tests=$scope.messageListInfo[g].fileList;
			  			for(var t=0;t<tests.length;t++){
			  				if(tests[t].split(".")[1]=="wav" || tests[t].split(".")[1]=="mp3"){
			  					$scope.messageVoiceItem[q]=tests[t];
			  					q++;
			  				}else if(tests[t].split(".")[1]!="dat"){
			  					$scope.messageImgItem[p]=tests[t];
			  					p++;
			  				}
			  			}
			  			$scope.messageList[0]=$scope.messageImgItem;
			  			$scope.messageList[1]=$scope.messageVoiceItem;
			  			$scope.messageList[2]=$scope.messageListInfo[g].id;
			  		}
		  			$scope.messageLists[g]=$scope.messageList;
		  			
		  		}
		  		
		  	  }
		  });
	}
	///////根据问题id添加问题的留言
	$scope.addMessage = function(questionId){
		var formData = new FormData();
		var content=document.getElementById("message_content").value;
		formData.append("content", content);
		formData.append("questionId", questionId);
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
		    });
	}
	///////问题筛选
	$scope.setChoice = function(index,str){
		var self=index;
		if(str=="项目名称"){
			$scope.questionProjectId=index;
		}
		if(str=="问题类型"){
			$scope.questionType=index;
		}
		if(str=="问题程度"){
			$scope.questionPority=index;
		}
		if(str=="问题状态"){
			$scope.questionStatu=index;
		}
		 $scope.getQuestionLists(pageSize,pageIndex,question);
	}
	///问题的百分比添加
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
  
}