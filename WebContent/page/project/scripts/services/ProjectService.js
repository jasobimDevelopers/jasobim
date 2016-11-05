angular.module('Demo')
  .service('ProjectService', function ProjectService($http, $q) {

  
    var self=this;
   
    //獲取项目列表
    this.getProjectList = function(token) {

        var deferred = $q.defer();
        console.log("读取ProjectList数据");
        $http.get('api/project/getProjectList?token='+token)
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.projectList = data;

                }else{
                    alert("数据读取失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
        };
        //////删除项目信息
     this.deleteProject = function(projectId,token,modelId) {
        
        var deferred = $q.defer();
        console.log("删除Project数据");
        $http.get('api/project/deleteProject?id='+projectId+'&token='+token+'&modelid='+modelId)
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.deleteProjectInfo = data;

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
        this.updateProject = function(project,token) {
        
        var deferred = $q.defer();
        console.log("更新Project数据");
        $http.post('api/project/updateProject',project)
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.updateProjectInfo = data;

                }else{
                    alert("数据更新失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
        };
        ///////根据Id查找用户信息
        this.findProject = function(projectId,token) {
        
        var deferred = $q.defer();
        console.log("查找Project数据");
        $http.get('api/project/getProjectDetails?projectId='+projectId+'&token='+token)
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
 /* ///////模糊查找用户信息
        this.findProjectByLike = function(project,token) {
        
        var deferred = $q.defer();
        console.log("查找Project数据");
        $http.get('api/project/findProjectLike?=name'+project.name+'&num='+project.num)
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
        };*/
});