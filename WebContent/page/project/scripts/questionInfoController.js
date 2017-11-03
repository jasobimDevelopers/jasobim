function questionInfoController($scope, $sce,questionInfoService) {
	var name="id";
	$scope.questionInfo="";
	$scope.worklist=[];
	$scope.createUserName="";
    var id = getQueryStringByName(name);
    $scope.name="";
    $scope.questionId="";
    $scope.messageListInfo="";
    $scope.questionImgList=[];
    $scope.questionVoiceList=[];
    $scope.messageImgList=[];
    $scope.messageVoiceList=[];
    $scope.messageList=[];
    $scope.messageLists=[];
    var j=0;
    var k=0;
    var q=0;
    var p=0;
    $scope.getQuestionInfoDetail = function(id) {
		  questionInfoService.getQuestionInfoDetail(id).then(function (result){
		  	$scope.questionInfo = result.data;
		  	if($scope.questionInfo!=""){
		  		if($scope.questionInfo.fileList!=null){
		  			var test=$scope.questionInfo.fileList;
		  			for(var i=0;i<test.length;i++){
		  				if(test[i].split(".")[1]=="wmv" || test[i].split(".")[1]=="mp3"){
		  					$scope.questionVoiceList[j]=test[i];
		  					j++;
		  				}else{
		  					$scope.questionImgList[k]=test[i];
		  					k++;
		  				}
		  			}
		  		}
		  		$scope.questionId=$scope.questionInfo.id;
		  		$scope.getMessageByQuestionId();
		  		 var length=($scope.questionInfo.userId).length;
		  		  $scope.name=($scope.questionInfo.userId).substr(length-2);
		  	  }
		  });
	}
    $scope.getQuestionInfoDetail(id);
    ///////通过id获取构件的详细信息
    $scope.getMessageByQuestionId = function() {
		  questionInfoService.getMessageByquestionId($scope.questionId).then(function (result){
		  	  $scope.messageListInfo = result.data;
		  	if($scope.messageListInfo!=""){
		  		for(var g=0;g<$scope.messageListInfo.length;g++){
		  			if($scope.messageListInfo[g].fileList!=null){
			  			var tests=$scope.messageListInfo[g].fileList;
			  			for(var t=0;t<tests.length;t++){
			  				if(tests[t].split(".")[1]=="wmv" || tests[t].split(".")[1]=="mp3"){
			  					$scope.messageVoiceItem[q]=tests[t];
			  					q++;
			  				}else{
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
	
}
function getQueryStringByName(name){
	 var result = location.search.match(new RegExp("[\?\&]" + name+ "=([^\&]+)","i"));
	
	 if(result == null || result.length < 1){
		 return "";
	 }
	 return result[1];
}

