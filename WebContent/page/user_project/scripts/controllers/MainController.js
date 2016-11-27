function MainController($scope,$location,$rootScope) {
  console.log("载入MainController");
  var locationChangeStartOff = $rootScope.$on('$locationChangeStart', locationChangeStart);
  function locationChangeStart(event){
  console.log('路径更改');
   	$scope.currentPath = $location.path().split("/")[$location.path().split("/").length - 1];
  }
  $scope.currentPath = locationChangeStart();
  
}