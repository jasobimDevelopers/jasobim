angular.module('Demo')
  .service('UserService', function UserService($http, $q) {

  
    var self=this;
    //用户登录
    this.login = function(userName,password) {

        var deferred = $q.defer();
        console.log("用户登录");
        $http.get('api/user/admin/getUserList?userName='+username+'&password='+password)
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.userInfo = data;

                }else{
                    alert("用户登录失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
        };
    //獲取用戶列表
    this.getUserList = function(token) {

        var deferred = $q.defer();
        console.log("读取UserList数据");
        $http.get('api/user/admin/getUserList?token='+token)
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
        //////删除用户信息
     this.deleteUser = function(userId,token) {
        
        var deferred = $q.defer();
        console.log("删除User数据");
        $http.get('api/user/admin/deleteUser?userId='+userId+'&token='+token)
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.deleteUserInfo = data;

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
        $http.post('api/user/update',user)
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.updateUserInfo = data;

                }else{
                    alert("数据更新失败");
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
});