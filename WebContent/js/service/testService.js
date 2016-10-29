myApp.service("testService",function($http) {
	
	this.login = function(userName,password) {
		var promise = $http({
			method: 'GET',
			url: "api/user/login?username=" + userName + "&password=" + password
		});
		return promise;
	};
	
	this.register = function(data) {
		return $http({
			method: 'POST',
			url: "api/user/register",
			headers: {
				'Content-Type': undefined
			},
			data: data
		});
	}
	
	this.uplodateFile = function(data) {
		var promise = $http({
			method: 'POST',
			url: "api/test/testUploadFile",
			headers: {
				'Content-Type': undefined
			},
		   data:data
		});
		return promise;
	}
	
});