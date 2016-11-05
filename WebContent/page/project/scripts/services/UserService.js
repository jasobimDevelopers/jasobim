var token=getCookie('token');
angular.module('Demo')
  .service('UserService', function UserService($http, $q) {

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
    //獲取用戶列表
    this.getUserList = function() {

        var deferred = $q.defer();
        console.log("读取UserList数据");
        $http.get('api/user/admin/getUserList?token='+getCookie('token'))
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
     this.deleteUser = function(userId) {
        
        var deferred = $q.defer();
        console.log("删除User数据");
        $http.get('api/user/admin/deleteUser?userId='+userId+'&token='+getCookie('token'))
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
//存储cookies
function setCookie(name,value,expiredays) 
{ 
  var argv = setCookie.arguments; 
  var argc = setCookie.arguments.length; 
  var LargeExpDate = new Date (); 
  var expires = (argc > 2) ? argv[2] : expiredays ; 
  if(expires!=null) 
  { 
      LargeExpDate.setTime(LargeExpDate.getTime() + (expires*1000*3600*24));         
  } 
  document.cookie = name + "=" + escape (value)+((expires == null) ? "" : ("; expires=" +LargeExpDate.toGMTString())); 
}

//获取cookies
function getCookie(Name) 
{ 
  var search = Name + "=" 
  if(document.cookie.length > 0) 
  { 
      offset = document.cookie.indexOf(search) 
      if(offset != -1) 
      { 
          offset += search.length 
          end = document.cookie.indexOf(";", offset) 
          if(end == -1) end = document.cookie.length 
          return unescape(document.cookie.substring(offset, end)) 
      } 
      else return "" 
  } 
} 

//删除cookies
function deleteCookie(name) 
{ 
  var expdate = new Date(); 
  expdate.setTime(expdate.getTime() - (86400 * 1000 * 1)); 
  setCookie(name, "", expdate); 
}