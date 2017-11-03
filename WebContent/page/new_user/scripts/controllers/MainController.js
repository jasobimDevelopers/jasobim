function MainController($scope,$location,$rootScope) {
  console.log("载入MainController");
  var locationChangeStartOff = $rootScope.$on('$locationChangeStart', locationChangeStart);
  function locationChangeStart(event){
  console.log('路径更改');
   	$scope.currentPath = $location.path().split("/")[$location.path().split("/").length - 1];
  }
  $scope.currentPath = locationChangeStart();
  $scope.resetSuggestion = function(){
	  $("#suggestContent").val("");
	}
	$scope.addSuggestion = function(){
		alert("提交成功！谢谢您的意见,我们会尽快改进！")
	}
}