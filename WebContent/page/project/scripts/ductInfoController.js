function ductInfoController($scope,ductInfoService) {
	var name="selfId";
	$scope.duct="";
    var id = getQueryStringByName(name);
    $scope.getDuctDetail = function(id) {
		  ductInfoService.getDuctDetail(id).then(function (result){
		  	  $scope.ductInfo = result.data;
		  });
	}
    $scope.getDuctDetail(id);
    ///////通过id获取构件的详细信息
	
	
}
function getQueryStringByName(name){
	 var result = location.search.match(new RegExp("[\?\&]" + name+ "=([^\&]+)","i"));
	
	 if(result == null || result.length < 1){
		 return "";
	 }
	 return result[1];
}

