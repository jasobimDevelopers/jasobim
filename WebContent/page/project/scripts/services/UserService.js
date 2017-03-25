var token=getCookie('token');
angular.module('Demo')
  .service('UserService', function UserService($http, $q) {
	  
	  var transform = function(data){
          return $.param(data);
      }

//	var username=document.getElementById("username");
//	var password=document.getElementById("password");
    var self=this;
   /* //用户登录
    this.login = function(userName,password) {

        var deferred = $q.defer();
        console.log("用户登录");
        $http.get('api/user/login?userName='+username+'&password='+password)
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.loginInfo = data;

                }else{
                    alert("用户登录失败");
                }
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
        };*/
    /////注册用户
    this.register = function(user) {

        var deferred = $q.defer();
        console.log("读取UserList数据");
        $http.post('api/user/admin/getUserList?token='+getCookie('token'))
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.userList = data;
               
                }else{
                    alert("数据读取失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
        };
    //獲取用戶列表
    this.getUserList = function(pageSize,pageIndex,user) {

        var deferred = $q.defer();
        console.log("读取UserList数据");
        var api = 'api/user/admin/getUserList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
        if(user.userName !== undefined && trimStr(user.userName) != '') {
        	api += "&userName="+trimStr(user.userName);
        }
        if(user.realName !== undefined && trimStr(user.realName) != ''){
        	api += "&realName="+trimStr(user.realName);
        }
        if(user.email !==undefined && trimStr(user.email) != ''){
        	api += "&email="+ trimStr(user.email);
        }
        if(user.tel !==undefined && trimStr(user.tel) != ''){
        	api += "&tel="+ trimStr(user.tel);
        }
        $http.get(encodeURI(api))
            .success(function(data, status, headers, config){
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.userList = data;
               
                }else{
                    alert("数据读取失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
        };
        //////删除用户信息
     this.deleteUser = function(userId) {
        
        var deferred = $q.defer();
        console.log("删除User数据");
        $http.get('api/user/admin/deleteUser?userId='+userId+'&token='+getCookie('token'))
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.deleteUserInfo = data;
                    alert("数据删除成功");
                }else{
                    alert("数据删除失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
        };
        ///////更新用户信息
        this.updateUser = function(user,token) {
        	var deferred = $q.defer();
        	console.log("更新User数据");
        	$http.post('api/user/admin/updateUser?token='+token,user,
        			{
//    			headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
//    			transformRequest: transform
        		headers: {'Content-Type':undefined},
                transformRequest: angular.identity 
    		})
            .success(function(data, status, headers, config){
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.updateUserInfo = data;
                    alert("更新成功")
                }else{
                    alert("数据更新失败("+data.errorCode+")");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        	/*$http({
        		method: 'POST',
    			url: 'api/user/admin/updateUser?token='+token,
    			headers: {
    				'Content-Type': undefined
    			},
    			transformRequest: angular.identity,
    			data:user
    		   
        		  }).success(function(d) {
        		    //请求成功
        			  if(data.callStatus == "SUCCEED"){
                        deferred.resolve(data);
                        self.updateUserInfo = data;
                        alert("更新成功")
                    }else{
                        alert("数据更新失败("+data.errorCode+")");
                    }
        		  }).error(function(data, status) {
        			  deferred.reject(data);
        		  });*/
        return deferred.promise;
        };
        ///////增加用户信息
        this.addUserByAdmin = function(findUserInfo,token) {
            var deferred = $q.defer();
            console.log("查找User数据");
            $http.post('api/user/admin/addUser?token='+token,findUserInfo,
            		{
//            			headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
//            			transformRequest: transform
            			headers: {'Content-Type':undefined},
            			transformRequest: angular.identity 
            		})
                .success(function(data, status, headers, config){
  
                    if(data.callStatus == "SUCCEED"){
                        deferred.resolve(data);
                        alert("添加成功")
                        self.findUserInfo = data;
                    
                    }else{
                        alert("数据添加失败("+data.errorCode+")");
                    }
                    
                })
                .error(function(data, status, headers, config){
                    deferred.reject(data);
                });
            return deferred.promise;
            };
        ///////查找用户信息
        this.findUser = function(userId,token) {
        
        var deferred = $q.defer();
        console.log("查找User数据");
        $http.get('api/user/admin/getUserDetails?userId='+userId+'&token='+token)
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.findUserInfo = data;
                
                }else{
                    alert("数据查找失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
        };
      //获取项目列表
        this.getProjectLists = function(pageSize,pageIndex,project) {

            var deferred = $q.defer();
            console.log("读取ProjectList数据");
            var api = 'api/project/admin/getProjectList?token='+getCookie('token') + "&pageSize=" + pageSize + "&pageIndex="+pageIndex;
            if(project.name !== undefined && trimStr(project.name) !== '') {
            	api += "&name="+trimStr(project.name);
            }
            if(project.num !== undefined && trimStr(project.num) !== ''){
            	api += "&num="+trimStr(project.num);
            }
            if(project.constructionUnit !==undefined && trimStr(project.constructionUnit) !== ''){
            	api += "&constructionUnit="+ trimStr(project.constructionUnit);
            }
            if(project.buildingUnit !==undefined && trimStr(project.buildingUnit) !== ''){
            	api += "&buildingUnit="+ trimStr(project.buildingUnit);
            }
            if(project.leader !== undefined && trimStr(project.leader) !== '') {
              	api += "&leader="+trimStr(project.leader);
              }
           if(project.place !== undefined && trimStr(project.place) !== ''){
          	api += "&place="+trimStr(project.place);
           }
           if(project.description !==undefined && trimStr(project.description) !== ''){
          	api += "&description="+ trimStr(project.description);
           }
           if(project.designUnit !==undefined && trimStr(project.designUnit) !== ''){
          	api += "&designUnit="+ trimStr(project.designUnit);
           }
           if(project.version !== undefined && trimStr(project.version) !== ''){
           	api += "&version="+trimStr(project.version);
            }
            if(project.startDate !==undefined && trimStr(project.startDate) !== ''){
           	api += "&startDate="+ trimStr(project.startDate);
            }
            if(project.phase !==undefined && trimStr(project.phase) !== ''){
           	api += "&phase="+ trimStr(project.phase);
            }
            $http.get(encodeURI(api))
                .success(function(data, status, headers, config){
                    if(data.callStatus == "SUCCEED"){
                        deferred.resolve(data);
                        self.projectLists = data;
                   
                    }else{
                        alert("数据读取失败");
                    }
                    
                })
                .error(function(data, status, headers, config){
                    deferred.reject(data);
                });
            return deferred.promise;
            };
});
