
function ductInfoController($scope,ductInfoService) {
	var name="id";
	var name1="s";
	var name2="p";
	$scope.duct="";
    var id = getQueryStringByName(name);
    var selfId = getQueryStringByName(name1);
    var projectId = getQueryStringByName(name2);
    $scope.getDuctDetail = function(id,selfId,projectId) {
		  ductInfoService.getDuctDetail(id,selfId,projectId).then(function (result){
		  	  $scope.ductInfo = result.data;
		  });
	}
    $scope.getDuctDetail(id,selfId,projectId);
    ///////通过id获取构件的详细信息
	
	
}
function getQueryStringByName(name){
	 var result = location.search.match(new RegExp("[\?\&]" + name+ "=([^\&]+)","i"));
	
	 if(result == null || result.length < 1){
		 return "";
	 }
	 return result[1];
}

