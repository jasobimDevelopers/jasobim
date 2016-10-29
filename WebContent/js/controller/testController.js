myApp.controller("testController",function($scope,testService) {
	$scope.register = function() {
//		var userName = document.getElementById("userName");
//		var password = document.getElementById("password");
//		var realName = document.getElementById("realName");
		
		var data = new FormData();
		data.append("userName", $scope.userName);
		data.append("password", $scope.password);
		data.append("realName", $scope.realName);
		
		var promise = testService.register(data);
	    promise.success(function(data,status,config,headers) {
	    	$scope.result = data;
			
	    });
		
		promise.error(function() {
			alert("error");
	    });
	}
	
	
	$scope.login = function() {
		var promise = testService.login($scope.userName,$scope.password);
	    promise.success(function(data,status,config,headers) {
	    	$scope.loginfo = data;
			
	    });
		
		promise.error(function() {
			alert("error");
	    });
	}
	
	$scope.upload = function() {
		var formData = new FormData();
	    formData.append('file', document.getElementById("myfile").files[0]);
	    formData.append('type',1);
		
		var promise = testService.uplodateFile(formData);
	    promise.success(function(data,status,config,headers) {
	    	$scope.file = data;
			
	    });
		
		promise.error(function() {
			alert("error");
	    });
	}
});