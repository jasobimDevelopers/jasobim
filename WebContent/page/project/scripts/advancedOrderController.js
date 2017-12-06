function advancedOrderController($scope,advancedOrderService) {
	var name="id";
	$scope.advancedOrder="";
	var approvalPeopleType;
	var approvalPeopleName;
	var approvalPeopleNote;
	var approvalPeopleIdea;
	var approvalUpdateDate;
	$scope.worklist=[];
	$scope.fileUrls=[];
	$scope.name="";
    var id = getQueryStringByName(name);
    $scope.getAdvancedOrderDetail = function() {
		  advancedOrderService.getAdvancedOrderDetail(id).then(function (result){
		  	  $scope.advancedOrder = result.data;
		  	  if($scope.advancedOrder!=""){
		  		  var length=($scope.advancedOrder.createUserName).length;
		  		  if($scope.advancedOrder.contentFilesId!=null){
		  			for(var j=0;j<$scope.advancedOrder.contentFilesId.length;j++){
		  				if($scope.advancedOrder.contentFilesId[j].split(".")[1]!="dat"){
		  					$scope.fileUrls=$scope.advancedOrder.contentFilesId;
		  				}
		  			}
		  		  }
		  		  $scope.name=($scope.advancedOrder.createUserName).substr(length-2);
		  		  if($scope.advancedOrder.approvalPeopleType!=null){
		  			  for(var i=0;i<$scope.advancedOrder.approvalPeopleType.length;i++){
		  				approvalPeopleType=$scope.advancedOrder.approvalPeopleType[i];
		  				approvalPeopleName=$scope.advancedOrder.approvalPeopleName[i];
		  				approvalPeopleNote=$scope.advancedOrder.approvalPeopleNote[i];
		  				approvalPeopleIdea=$scope.advancedOrder.approvalPeopleIdea[i];
		  				approvalUpdateDate=$scope.advancedOrder.approvalUpdateDate[i];
		  				$scope.worklist[i]=[approvalPeopleType,approvalPeopleName,approvalPeopleIdea,approvalUpdateDate,approvalPeopleNote];
		  			  }
		  		  }
		  	  }
		  });
	}
    $scope.getAdvancedOrderDetail();
    ///////通过id获取构件的详细信息
	
	
}
function getQueryStringByName(name){
	 var result = location.search.match(new RegExp("[\?\&]" + name+ "=([^\&]+)","i"));
	
	 if(result == null || result.length < 1){
		 return "";
	 }
	 return result[1];
}

worklist=[{name:"xyx",age:24,sex:"man"},{name:"alice",age:23,sex:"women"}];