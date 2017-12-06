function questionInfoController($scope, $sce,questionInfoService) {
	
	// 修改了返回的对象,以前的无法接收
	
	// 添加单一播放的逻辑
	/*$('.weixinAudio').on('click',function(event) {
	    
	});*/
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
    $scope.messageImgItem=[];
    $scope.messageVoiceItem=[];
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
		  				if(test[i].split(".")[1]=="wav" || test[i].split(".")[1]=="mp3"){
		  					$scope.questionVoiceList[j]=test[i];
		  					j++;
		  				}else if(test[i].split(".")[1]!="dat"){
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
    var baseUrl="http://jasobim.com.cn/";
    $scope.play = function(index){
		var item=$($('.question-wrap')[index]).find('.weixinAudio')[0];
		var $item=$(item);
		if(!item._audio){
			var test=$item.find("audio")[0];
			test.setAttribute('src',baseUrl + $scope.questionVoiceList[index]);	
			//var weixinAudioObj = item.weixinAudio(false);
			item._audio=$item.weixinAudio(false).weixinAudio0;
			test.onloadedmetadata=function(){
				item._audio.updateTotalTime();
			}
			
		}
		item._audio.play();
		$('.weixinAudio').each(function(index,obj){
			if(!$(obj).find("audio")[0].paused&&obj!=item){
				if(obj._audio)
					obj._audio.pause();
			}
		});
	};
	 $scope.plays = function(index,pIndex){
		var indexs=index;
		index=$scope.questionVoiceList.length+index;
		var item=$($('.reply-wrap')[pIndex]).find('.weixinAudio')[indexs];
		var $item=$(item);
		if(!item._audio){
			var test=$item.find("audio")[0];
			test.setAttribute('src',baseUrl + $scope.messageLists[pIndex][1][indexs]);	
			//var weixinAudioObj = item.weixinAudio(false);
			item._audio=$item.weixinAudio(false).weixinAudio0;
			test.onloadedmetadata=function(){
				item._audio.updateTotalTime();
			}
			
		}
		item._audio.play();
		$('.weixinAudio').each(function(index,obj){
			if(!$(obj).find("audio")[0].paused&&obj!=item){
				if(obj._audio)
					obj._audio.pause();
			}
		});
	};

	
	
	
}
function getQueryStringByName(name){
	 var result = location.search.match(new RegExp("[\?\&]" + name+ "=([^\&]+)","i"));
	
	 if(result == null || result.length < 1){
		 return "";
	 }
	 return result[1];
}

