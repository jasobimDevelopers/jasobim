function constructionTaskController($scope,constructionTaskService) {
	var name="id";
	$scope.constructionTask="";
	var approvalPeopleType;
	var approvalPeopleName;
	var approvalPeopleNote;
	var approvalPeopleIdea;
	var approvalUpdateDate;
	$scope.worklist=[];
	$scope.fileUrls=[];
	$scope.createUserName="";
    var id = getQueryStringByName(name);
    $scope.getConstructionTaskDetail = function(id) {
		  constructionTaskService.getConstructionTaskDetail(id).then(function (result){
		  	  $scope.constructionTask = result.data;
		  	  
		  	if($scope.constructionTask!=""){
		  		var length=($scope.constructionTask.createUserName).length;
		  		if($scope.constructionTask.fileUrlList!=null){
		  			for(var j=0;j<$scope.constructionTask.fileUrlList.length;j++){
		  				if($scope.constructionTask.fileUrlList[j].split(".")[1]!="dat"){
		  					$scope.fileUrls=$scope.constructionTask.fileUrlList;
		  				}
		  			}
		  			
		  		}
		  		$scope.createUserName=($scope.constructionTask.createUserName).substr(length-2);
		  		  if($scope.constructionTask.approvalPeopleTypeList!=null){
		  			  for(var i=0;i<$scope.constructionTask.approvalPeopleTypeList.length;i++){
		  				approvalPeopleType=$scope.constructionTask.approvalPeopleTypeList[i];
		  				approvalPeopleName=$scope.constructionTask.approvalPeopleNameList[i];
		  				approvalPeopleNote=$scope.constructionTask.approvalPeopleNoteList[i];
		  				approvalPeopleIdea=$scope.constructionTask.approvalPeopleIdeaList[i];
		  				approvalUpdateDate=$scope.constructionTask.approvalDateList[i];
		  				$scope.worklist[i]=[approvalPeopleType,approvalPeopleName,approvalPeopleIdea,approvalUpdateDate,approvalPeopleNote];
		  			  }
		  		  }
		  	  }
		  });
	}
    $scope.getConstructionTaskDetail(id);
    ///////通过id获取构件的详细信息
	
	
}
function getQueryStringByName(name){
	 var result = location.search.match(new RegExp("[\?\&]" + name+ "=([^\&]+)","i"));
	
	 if(result == null || result.length < 1){
		 return "";
	 }
	 return result[1];
}

