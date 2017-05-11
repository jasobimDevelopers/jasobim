  var token=getCookie('token');
  angular.module('Demo')
.service('ProjectService', function ProjectService($http, $q) {
  
  var transform = function(data){
        return $.param(data);
    }
  var self=this;
  
  //获取项目列表
  this.getProjectList = function(pageSize,pageIndex,project) {

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
   this.deleteProject = function(projectId,token) {
      
      var deferred = $q.defer();
      console.log("删除Project数据");
      $http.get('api/project/admin/deleteProject?projectId='+projectId+'&token='+token)
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
      /////删除交底
      this.deleteVideo = function(projectVideoId,fileId){
    	  var deferred = $q.defer();
          console.log("删除Video数据");
          $http.get('api/video/admin/deleteVideo?id='+projectVideoId+'&token='+token+'&fileid='+fileId)
              .success(function(data, status, headers, config){
                  console.log(data);
                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      self.deleteProjectVideoInfo = data;
                      alert("数据删除成功");
                  }else{
                      alert("数据删除失败");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise;
      }
      ///////更新项目信息
      this.updateProject = function(project,token) {
      	var deferred = $q.defer();
      	console.log("更新Project数据");
      	$http.post('api/project/admin/updateProject?token='+token,project,
      		{
	      		headers: {'Content-Type':undefined},
	            transformRequest: angular.identity 
      		})
          .success(function(data, status, headers, config){
          	console.log(data);
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.updateProjectInfo = data;
                  alert("更新成功")
              }else{
                  alert("数据更新失败("+data.errorCode+")");
              }
              
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      	  return deferred.promise;
      };
      ///////增加项目信息
      this.addProjectByAdmin = function(findProjectInfo,token) {
          var deferred = $q.defer();
          
          
          console.log("添加Project数据");
          $http.post('api/project/admin/addProject?token='+token,findProjectInfo,
          		{
        	  		headers: {'Content-Type':undefined},
        	  		transformRequest: angular.identity 

          		})
              .success(function(data, status, headers, config){

                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      alert("项目基本信息添加成功，请继续添加项目构件信息")
                      self.findProjectInfo = data;
                  
                  }else{
                      alert("数据添加失败("+data.errorCode+")");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          	  return deferred.promise;
          };
      ///////查找项目信息
      this.findProject = function(projectId,token) {
      
	      var deferred = $q.defer();
	      console.log("查找Project数据");
	      $http.get('api/project/admin/getProjectDetails?projectId='+projectId+'&token='+token)
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
                  //alert("数据查找失败");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise;
          };
      //////////////////////////////
      
      ///////获取地下层操作
      this.getBuildingDown = function(projectId,buildingId) {
          
          var deferred = $q.defer();
          console.log("查找Project数据");
          $http.get('api/item/getItemByBase?projectId='+projectId+"&token="+token+"&buildingId="+buildingId)
              .success(function(data, status, headers, config){
                  console.log(data);
                  if(data!=null){
                      deferred.resolve(data);
                      self.buildingDownInfo = data;
                  
                  }else{
                      alert("数据查找失败");
                      }
                      
                  })
                  .error(function(data, status, headers, config){
                      deferred.reject(data);
                  });
              return deferred.promise;
      };
      ///////////////获取楼层总数
      this.getBuildingNum = function(projectId,buildingId) {
          
          var deferred = $q.defer();
          console.log("查找Project数据");
          $http.get('api/item/getItemByBuidlingNum?projectId='+projectId+"&token="+token+"&buildingId="+buildingId)
              .success(function(data, status, headers, config){
                  console.log(data);
                  if(data!=null){
                      deferred.resolve(data);
                      self.buildingNumInfo = data;
                  
                  }else{
                      alert("数据查找失败");
                      }
                      
                  })
                  .error(function(data, status, headers, config){
                      deferred.reject(data);
                  });
              return deferred.promise;
      };
      //////*项目构件信息操作*///////
                  
       ///获取构件的详细信息
      this.getItemById = function(itemId){
        	  var deferred = $q.defer();
              console.log("读取item数据");
              var api = 'api/item/getItemById?token='+getCookie('token')+'&itemId='+itemId;
              $http.get(encodeURI(api))
                  .success(function(data, status, headers, config){
                      if(data.callStatus == "SUCCEED"){
                          deferred.resolve(data);
                          self.itemDetailInfo = data;
                     
                      }else{
                          alert("数据读取失败");
                      }
                      
                  })
                  .error(function(data, status, headers, config){
                      deferred.reject(data);
                  });
              return deferred.promise;
      };
      /////////获取构件信息列表
      this.getItemList = function(projectId,pageSize,pageIndex,sql) {

          var deferred = $q.defer();
          console.log("读取ProjectItemList数据");
          var api = 'api/item/admin/getItemList?token='+getCookie('token')+'&projectId='+projectId + "&pageSize=" + pageSize + "&pageIndex="+pageIndex +"&"+ sql;
          $http.get(encodeURI(api))
              .success(function(data, status, headers, config){
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
      //////////////////////
      ////工程量信息获取
      //获取工程量信息列表
      this.getQuantityList = function(projectId,pageSize,pageIndex,sql) {

      var deferred = $q.defer();
      console.log("读取ProjectQuantityList数据");
      var api = 'api/quantity/getQuantityList?token='+getCookie('token')+'&projectId='+projectId + "&pageSize=" + pageSize + "&pageIndex="+pageIndex + "&"+sql;
      $http.get(encodeURI(api))
          .success(function(data, status, headers, config){
              if(data.callStatus == "SUCCEED"){
                  deferred.resolve(data);
                  self.quantityList = data;
             
              }else{
                  alert("数据读取失败");
              }
              
          })
          .error(function(data, status, headers, config){
              deferred.reject(data);
          });
      return deferred.promise;
      };
      /////获取交底信息列表
      
      this.getVideoList = function(projectId,pageSize,pageIndex,sql) {

          var deferred = $q.defer();
          console.log("读取ProjectVideoList数据");
          var api = 'api/video/admin/getVideoList?token='+getCookie('token')+'&projectId='+projectId + "&pageSize=" + pageSize + "&pageIndex="+pageIndex +"&"+ sql;
          $http.get(encodeURI(api))
              .success(function(data, status, headers, config){
                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      self.videoList = data;
                 
                  }else{
                      alert("数据读取失败");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
              return deferred.promise;
       };
      /////获取图纸信息列表
      
      this.getPaperList = function(projectId,pageSize,pageIndex,sql) {

          var deferred = $q.defer();
          console.log("读取ProjectPaperList数据");
          var api = 'api/paper/admin/getPaperLists?token='+getCookie('token')+'&projectId='+projectId + "&pageSize=" + pageSize + "&pageIndex="+pageIndex +"&"+ sql;
          $http.get(encodeURI(api))
              .success(function(data, status, headers, config){
                  if(data.callStatus == "SUCCEED"){
                      deferred.resolve(data);
                      self.paperList = data;
                 
                  }else{
                      alert("数据读取失败");
                  }
                  
              })
              .error(function(data, status, headers, config){
                  deferred.reject(data);
              });
          return deferred.promise;
          };
          //////删除图纸信息
          this.deleteProjectPaper = function(paperId) {
             
             var deferred = $q.defer();
             console.log("删除Project数据");
             $http.get('api/paper/admin/deletePaper?&token='+token+'&id='+paperId)
                 .success(function(data, status, headers, config){
                     console.log(data);
                     if(data.callStatus == "SUCCEED"){
                         deferred.resolve(data);
                         self.deleteProjectPaperInfo = data;
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
     /////获取问题信息列表
      this.getQuestionList = function(projectId,pageSize,pageIndex,sql) {

          var deferred = $q.defer();
          console.log("读取ProjectMessageList数据");
          var api = 'api/question/admin/getQuestionList?token='+getCookie('token')+'&projectId='+projectId + "&pageSize=" + pageSize + "&pageIndex="+pageIndex+"&"+ sql;
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
          //////删除问题信息
          this.deleteQuestion = function(questionId,projectId) {
             
             var deferred = $q.defer();
             console.log("删除Project数据");
             $http.get('api/question/admin/deleteQuestion?projectId='+projectId+'&token='+getCookie('token')+'&questionId='+questionId)
                 .success(function(data, status, headers, config){
                     console.log(data);
                     if(data.callStatus == "SUCCEED"){
                         deferred.resolve(data);
                         self.deleteQuestionInfo = data;
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
         ///////上传构件信息表
         this.uploadItemFile = function(fileArray,projectId) {
             var deferred = $q.defer();
             
             
             console.log("上传构件数据");
             $http.post('api/item/admin/uploadItem?token='+getCookie('token')+"&projectId="+projectId,fileArray,
            		 {
            	 		headers: {'Content-Type':undefined},
            	 		transformRequest: angular.identity 
            		 })
                 .success(function(data, status, headers, config){
                	 if(data.callStatus != "SUCCEED"){
                		 alert("正在上传构件信息，请耐心等待");
                	 }
                     if(data.callStatus == "SUCCEED"){
                         deferred.resolve(data);
                         alert("上传构件信息成功，请继续上传图纸信息")
                         self.uploadItemInfo = data;
                     
                     }else{
                         alert("数据添加失败("+data.errorCode+")");
                     }
                     
                 })
                 .error(function(data, status, headers, config){
                     deferred.reject(data);
                 });
             return deferred.promise;
             };
            ////////////上传图纸信息
         this.uploadPaperFile = function(fileArray,buildingNum,floorNum,projectId) {
             var deferred = $q.defer();
             
             
             console.log("上传图纸数据");
             $http.post('api/paper/admin/uploadPaper?token='+getCookie('token')+"&buildingNum="+buildingNum+"&floorNum="+floorNum+"&projectId="+projectId,fileArray,
            		 {
            	 		headers: {'Content-Type':undefined},
            	 		transformRequest: angular.identity 
            		 })
                 .success(function(data, status, headers, config){

                     if(data.callStatus == "SUCCEED"){
                         deferred.resolve(data);
                         alert("上传图纸信息成功")
                         self.uploadPaperInfo = data;
                     
                     }else{
                         alert("数据添加失败("+data.errorCode+")");
                     }
                     
                 })
                 .error(function(data, status, headers, config){
                     deferred.reject(data);
                 });
             return deferred.promise;
             };
	          /////////////上传交底信息
             this.uploadVideoFile = function(fileArray,professionType,videoTypeList,projectId) {
                 var deferred = $q.defer();
                 
                 
                 console.log("上传交底数据信息");
                 $http.post('api/video/admin/addVideo?token='+getCookie('token')+"&professionType="+professionType+"&videoType="+videoTypeList+"&projectId="+projectId,fileArray,
                		 {
                	 		headers: {'Content-Type':undefined},
                	 		transformRequest: angular.identity 
                		 })
                     .success(function(data, status, headers, config){

                         if(data.callStatus == "SUCCEED"){
                             deferred.resolve(data);
                             alert("上传交底信息成功")
                             self.uploadVideoInfo = data;
                         
                         }else{
                             alert("数据添加失败("+data.errorCode+")");
                         }
                         
                     })
                     .error(function(data, status, headers, config){
                         deferred.reject(data);
                     });
                 return deferred.promise;
                 };
         /////上传预算的工程量
         this.uploadOtherQuantity = function(formData,projectId){
        	 var deferred = $q.defer();
             console.log("上传构件数据");
             $http.post('api/quantity/admin/uploadQuantity?token='+getCookie('token')+"&projectId="+projectId,formData,
            		 {
            	 		headers: {'Content-Type':undefined},
            	 		transformRequest: angular.identity 
            		 })
                 .success(function(data, status, headers, config){

                     if(data.callStatus == "SUCCEED"){
                         deferred.resolve(data);
                         alert("上传预算工程量信息成功！")
                         self.uploadQuantityInfo = data;
                     
                     }else{
                         alert("数据添加失败("+data.errorCode+")");
                     }
                     
                 })
                 .error(function(data, status, headers, config){
                     deferred.reject(data);
                 });
             return deferred.promise;
         };
         ////////导出工程量
         this.getProjectQuantityExcel= function(projectId){
        	 var deferred = $q.defer();
        	 $http.get('api/quantity/exportQuantity?projectId='+projectId+'&token='+token)
             .success(function(data, status, headers, config){
                 console.log(data);
                     if(data.callStatus == "SUCCEED"){
                         deferred.resolve(data);
                         alert("导出预算工程量信息成功！")
                         self.uploadQuantityInfo = data;
                     
                     }else{
                        // alert("数据导出失败("+data.errorCode+")");
                     }
                     
                 })
                 .error(function(data, status, headers, config){
                     deferred.reject(data);
                 });
             return deferred.promise;
         };
         /////////////上传交底信息
         this.uploadVideoFile = function(fileArray,professionType,projectId) {
            var deferred = $q.defer();
            console.log("上传图纸数据");
            $http.post('api/video/admin/addVideo?token='+getCookie('token')+"&professionType="+professionType+"&projectId="+projectId,fileArray,
           		 {
           	 		headers: {'Content-Type':undefined},
           	 		transformRequest: angular.identity 
           		 })
                .success(function(data, status, headers, config){

                    if(data.callStatus == "SUCCEED"){
                        deferred.resolve(data);
                        alert("上传交底信息成功")
                        self.uploadVideoInfo = data;
                    
                    }else{
                        alert("数据添加失败("+data.errorCode+")");
                    }
                    
                })
                .error(function(data, status, headers, config){
                    deferred.reject(data);
                });
            	return deferred.promise;
          };
      /////交底上传
          this.uploadVideo = function(formData,projectId){
          	 var deferred = $q.defer();
              console.log("上传交底数据");
              $http.post('api/video/admin/addVideo?token='+getCookie('token')+"&projectId="+projectId,formData,
             		 {
             	 		headers: {'Content-Type':undefined},
             	 		transformRequest: angular.identity 
             		 })
                  .success(function(data, status, headers, config){

                      if(data.callStatus == "SUCCEED"){
                          deferred.resolve(data);
                          alert("上传交底文档信息成功！")
                          self.uploadVideoInfo = data;
                      
                      }else{
                          alert("数据添加失败("+data.errorCode+")");
                      }
                      
                  })
                  .error(function(data, status, headers, config){
                      deferred.reject(data);
                  });
              return deferred.promise;
          };
  });
