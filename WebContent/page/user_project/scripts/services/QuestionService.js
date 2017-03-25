  var token=getCookie('token');
  angular.module('Demo')
.service('QuestionService', function QuestionService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
    }
  var self=this;
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
  /////增加问题
  this.addQuestionByAdmin = function(formData) {

      var deferred = $q.defer();
      console.log("问题增加");
      $http.post('api/question/addQuestion?token='+getCookie('token'),formData,
    		  {
			  		headers: {'Content-Type':undefined},
			  		transformRequest: angular.identity 
    		  })
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.questionInfo = data;
                  alert("问题添加成功！");
              }else{
                  alert("数据添加失败");
              }
              
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      return deferred.promise;
      };
   ///////更新问题
      this.updateQuestionByAdmin = function(formData){
    	  var deferred = $q.defer();
          console.log("问题更新");
          $http.post('api/question/admin/updateQuestion?token='+token,formData,
        		  {
    			  		headers: {'Content-Type':undefined},
    			  		transformRequest: angular.identity 
        		  })
              .success(function(data, status, headers, config){
                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      self.questionInfo = data;
                      alert("问题更新成功！");
                  }else{
                      alert("数据更新失败");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise;
      };
  ///////留言增加
      this.addMessage = function(formData){
    	  var deferred = $q.defer();
          console.log("留言增加");
          $http.post('api/message/admin/addMessage?token='+getCookie('token'),formData,
        		  {
    			  		headers: {'Content-Type':undefined},
    			  		transformRequest: angular.identity 
        		  })
              .success(function(data, status, headers, config){
                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      self.messageInfo = data;
                  }else{
                      alert("数据添加失败");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise; 
      };
      ///////删除问题信息
      this.deleteQuestion = function(questionId){
    	  var deferred = $q.defer();
          console.log("删除问题数据");
          $http.get('api/question/admin/deleteQuestionById?questionId='+questionId+'&token='+token)
              .success(function(data, status, headers, config){
                  console.log(data);
                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      self.deleteProjectInfo = data;
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
     
      ///////查找问题信息
      this.findQuestion = function(questionId) {
      
      var deferred = $q.defer();
      console.log("查找Question数据");
      $http.get('api/question/getQuestionDetails?&token='+token+'&questionId='+questionId)
          .success(function(data, status, headers, config){
              console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.findProjectInfo = data;
              }else{
                  alert("数据查找失败");
              }
              }).error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise;
          };
      //////////////////////////////
    ///////问题状态切换
      this.changeQuestionState = function(questionId,state) {
      	var deferred = $q.defer();
      	console.log("更新Project数据");
      	$http.post('api/question/updateQuestionState?token='+token+'&questionId='+questionId+'&state='+state,
      		{
	      		headers: {'Content-Type':undefined},
	            transformRequest: angular.identity 
      		})
          .success(function(data, status, headers, config){
          	console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.updateQuestionInfo = data;
              }else{
                  alert("数据更新失败("+data.errorCode+")");
              }
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      	  return deferred.promise;
      };
      ///////查找项目里的楼栋信息
      this.getBuildingList = function(projectId) {
      
      var deferred = $q.defer();
      console.log("查找Project数据");
      $http.get('api/building/admin/getBuildingByProjectId?projectId='+projectId+"&token="+token)
          .success(function(data, status, headers, config){
              console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.buildingInfo = data;
              
              }else{
                  alert("数据查找失败");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise;
       };
      //////////////////////////////
       /////获取问题信息列表
       this.getQuestionList = function(pageSize,pageIndex,question,content) {

           var deferred = $q.defer();
           console.log("读取ProjectQuestionList数据");
           if(content!=undefined && content!=""){
         	  var api = 'api/question/admin/getQuestionList?token='+getCookie('token')+"&pageSize="+pageSize+"&pageIndex="+pageIndex+"&content="+content+"&"+question;
           }else{
         	  var api = 'api/question/admin/getQuestionList?token='+getCookie('token')+"&pageSize="+pageSize+"&pageIndex="+pageIndex+"&"+question;
           }
           
           $http.get(encodeURI(api))
               .success(function(data, status, headers, config){
                   if(data.callStatus == "SUCCEED"){
                       deferred.resolve(data);
                       self.questionList = data;
                  
                   }else{
                       alert("数据读取失败");
                   }
                   
               })
               .error(function(data, status, headers, config){
                   deferred.reject(data);
               });
           return deferred.promise;
           };
           /////通过用户id获取问题列表
        this.getQuestionListByUserId= function(pageSize,pageIndex) {

        var deferred = $q.defer();
        console.log("读取ProjectQuestionListByUserId数据");
  	  	var api = 'api/question/getQuestionListByUserId?token='+getCookie('token')+"&pageSize="+pageSize+"&pageIndex="+pageIndex;
        $http.get(encodeURI(api))
            .success(function(data, status, headers, config){
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.questionList = data;
               
                }else{
                    alert("数据读取失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
        };
    ///////////////获取问题所对应的留言信息
    this.getMessageListByQuestionId = function(questionId){
    	 var deferred = $q.defer();
          console.log("读取messageOfQuestionList数据");
          var api = 'api/message/getMessageListByQuestionId?token='+getCookie('token')+"&questionId="+questionId;
          $http.get(encodeURI(api))
              .success(function(data, status, headers, config){
                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      self.messageList = data;
                 
                  }else{
                	  console.log("该问题的留言为空")
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise;
    };
    ///////删除留言
    this.deleteMessage = function(messageId){
    	var deferred = $q.defer();
        console.log("删除留言数据");
        $http.get('api/message/deleteMessage?id='+messageId+'&token='+token)
            .success(function(data, status, headers, config){
                console.log(data);
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.deleteMessageInfo = data;
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
    /////////更新留言
    this.updateMessageByAdmin = function(formData){
    	var deferred = $q.defer();
        console.log("更新留言数据");
        $http.post('api/message/admin/updateMessage?token='+getCookie('token'),formData,
      		  {
  			  		headers: {'Content-Type':undefined},
  			  		transformRequest: angular.identity 
      		  })
            .success(function(data, status, headers, config){
                if(data.callStatus == "SUCCEED"){
                    deferred.resolve(data);
                    self.messageInfo = data;
                    alert("留言更新成功")
                }else{
                    alert("数据添加失败");
                }
                
            })
            .error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise; 
    };
    //////查找留言信息
    this.findMessage = function(messageId) {
    
    var deferred = $q.defer();
    console.log("查找Message数据");
    $http.get('api/message/admin/getMessageById?&token='+token+'&id='+messageId)
        .success(function(data, status, headers, config){
            console.log(data);
            if(data.callStatus == "SUCCEED"){
                deferred.resolve(data);
                self.findMessageInfo = data;
            }else{
                alert("数据查找失败");
            }
            }).error(function(data, status, headers, config){
                deferred.reject(data);
            });
        return deferred.promise;
    };
});
