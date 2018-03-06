var index;
function AdvancedOrderController($scope,AdvancedOrderService) {
	console.log("载入AdvancedOrderController");
	$scope.a = 1;
	console.log("载入AdvancedOrderController");
	$scope.currentPage = 1;
	$scope.flagAll=-1;
	var advancedOrder="";
	var project="";
	var user="";
	$scope.advancedOrderLists="";
	$scope.currentPage=1;
	$scope.totalPage=1;
	$scope.OrderTofind = {};
	$scope.advancedOrderInfos=[];
	$scope.advancedOrderInfo=[];
	$scope.advancedOrderfiles="";
	$scope.advancedOrderfiles2="";
	$scope.advancedOrderUrl="";
	var pageSize=10;
	var pageIndex=1;
	$scope.orderTofind = {};
	$scope.conslogitem;
	$scope.conslogList=[];
	$scope.projectList =[];
	$scope.userList=[];
	 ///////分页获取项目列表
	var nums;
	var names;
	var provinces;
	var persons;
	var addresss;
	var datas;
	$scope.getUserList = function(pageSize,pageIndex,user) {
		AdvancedOrderService.getUserList(pageSize,pageIndex,user).then(function (result){
		  	  $scope.userList = result.data;
		  });
	}
	$scope.getProjectList = function(pageSize,pageIndex,project) {
		AdvancedOrderService.getProjectList(pageSize,pageIndex,project).then(function (result){
		  	  $scope.projectList = result.data;
		  });
	}
	$scope.orderInfosFlag=["未完成","已完成"];
	$scope.advancedOrderTitles=["序号","项目名称","项目负责人","施工部位","下一审批人","状态","创建时间","操作"];
	$scope.monthList=["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"];
	$scope.getAdvancedOrderLists = function(pageSize,pageIndex,advancedOrder) {
		AdvancedOrderService.getAdvancedOrderList(pageSize,pageIndex,advancedOrder).then(function (result){
		  	  $scope.advancedOrderInfos = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.projectPage($scope.totalPage,$scope.currentPage);
		  });
	};
	$scope.getAdvancedOrderListss = function(pageSize,pageIndex,advancedOrder) {
		AdvancedOrderService.getAdvancedOrderList(pageSize,pageIndex,advancedOrder).then(function (result){
		  	  $scope.advancedOrderInfos = result.data;
		  	  if(pageIndex==1){
		  		 $scope.currentPage = result.currentPage; 
		  	  }
		      $scope.totalPage = result.totalPage;
		      $scope.projectPage($scope.totalPage,$scope.currentPage);
		  });
	};
	$scope.getAdvancedOrderById = function(pageSize,pageIndex,advancedOrder) {
		AdvancedOrderService.getAdvancedOrderById(pageSize,pageIndex,advancedOrder).then(function (result){
		  	  $scope.advancedOrderInfo = result.data;
		  	  if($scope.advancedOrderInfo!={}){
		  		  if($scope.advancedOrderInfo[0].approvalPeopleName!=null){
		  			  for(var i=0;i<$scope.advancedOrderInfo[0].approvalPeopleName.length;i++){
		  				  	$scope.conslogitem={"type":"","name":"","idea":"","date":"","note":""};
		  					$scope.conslogitem.type=$scope.advancedOrderInfo[0].approvalPeopleType[i];
		  					$scope.conslogitem.name=$scope.advancedOrderInfo[0].approvalPeopleName[i];
		  					$scope.conslogitem.idea=$scope.advancedOrderInfo[0].approvalPeopleIdea[i];
		  					$scope.conslogitem.date=$scope.advancedOrderInfo[0].approvalUpdateDate[i];
		  					$scope.conslogitem.note=$scope.advancedOrderInfo[0].approvalPeopleNote[i];
		  					$scope.conslogList[i]=$scope.conslogitem;
		  			  }
		  		  }
		  		  
		  	  }
		     
		  });
	};
	////////模糊查找预付单
	 $scope.findOrderLike = function() {
		 $scope.currentPage = 1;
		 $scope.getAdvancedOrderList(pageSize,$scope.currentPage,$scope.OrderTofind);
	 };
	$scope.findLike=function(){
		var content=document.getElementById("project_search").value;
		$scope.getAdvancedOrderLike(10,-1,project,content)
	};
	$scope.getProjectListLike = function(pageSize,pageIndex,project,content) {
		  ProjectService.getProjectLists(pageSize,pageIndex,project,content).then(function (result){
		  	  $scope.projectList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.projectPage($scope.totalPage,$scope.currentPage);
		  });
	};
	 //////重置施工任务单搜索
    $scope.resetCon = function(){
   	 	$scope.ConsTofind = {};
    };
   
    $scope.advancedOrderChange = function(index){
    	$scope.orderTofind.id=index;
    	$scope.getAdvancedOrderById(pageSize,1,$scope.orderTofind);
    	document.getElementById('advancedOrderDetailHtml').style.display='block';
    	document.getElementById('projectContent').style.display='none';
    }
    $scope.setAdvancedOrderId=function(index){
    	if($scope.AdvancedOrderIdList==""){
    		$scope.AdvancedOrderIdList=index;
    	}else{
    		$scope.AdvancedOrderIdList=$scope.AdvancedOrderIdList+","+index;
    	}
    }
    $scope.goOrderList = function(){
    	$scope.conslogList=[];
    	$scope.getAdvancedOrderListss(pageSize,$scope.currentPage,null);
    	document.getElementById('advancedOrderDetailHtml').style.display='none';
    	document.getElementById('projectContent').style.display='block';
    }
	//////全选
	$scope.set_checkboxall = function(parentid){
		 var PID = document.getElementById(parentid);
	     var cb = PID.getElementsByTagName("input");
	         
		 if(!cb[0].checked){
			 for(var i=0;i<cb.length;i++){
				 if(cb[i].type == "checkbox"){
					 cb[i].checked = 1;
				 }
			 }
			 
		 }else{
			 for(var i=0;i<cb.length;i++){
				 if(cb[i].type == "checkbox"){
					 cb[i].checked = 0;
				 }
			 } 
		 }
	 };
	 $scope.delete_choice=function(parentid){
		 var PID = document.getElementById(parentid);
	     var cb = PID.getElementsByTagName("input");
	     idList="";
		 for(var i=0;i<cb.length;i++){
			 if(cb[i].checked==1){
				 if(idList==""){
					 idList=$scope.AdvancedOrderLists[i].id;
				 }else{
					 idList=idList+","+$scope.AdvancedOrderLists[i].id; 
				 }
				 
			 }
		  }
		 $scope.deleteAdvancedOrder(idList);
	 };
	/////删除任务单
	 $scope.deleteAdvancedOrder = function(ids){
		 if(confirm("确定删除？")) {  
			 AdvancedOrderService.deleteAdvancedOrder(ids).then(function(result){
		       $scope.advancedOrders=result.data;
		       $scope.getAdvancedOrderLists(pageSize,pageIndex,null); 
		    });
		 }
	 }
	$scope.getAdvancedOrderList = function(pageSize,pageIndex,advancedOrder) {
		AdvancedOrderService.getAdvancedOrderList(pageSize,pageIndex,advancedOrder).then(function (result){
		  	  $scope.advancedOrderInfos = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.projectPage($scope.totalPage,$scope.currentPage);
		  });
	}
	$scope.getAdvancedOrderLists(pageSize,pageIndex,null);
	$scope.getProjectList(10,-1,null);
	$scope.getUserList(pageSize,pageIndex,null);
	////////分页回调函数
	$scope.projectPage = function(iPageCount,iCurrent) {
		  $("#advancedOrderPageCode").remove();
		  $("#table-buton_advancedOrder").append("<div id=\"advancedOrderPageCode\"></div>");
		  
		  $("#advancedOrderPageCode").createPage({
			  
		      pageCount:iPageCount,
	
		      current:iCurrent,
	
		      backFn:function(p){
		    	  console.log($scope.ProjectTofind);
		    	  $scope.currentPage=p;
		    	  $scope.getAdvancedOrderList(pageSize,p,advancedOrder);
		    	  
		      }
	
		  });
	 };
	 $scope.addAdvancedOrder = function(){
		 if($scope.advancedOrderTitle=="预付单添加"){
			 var formData = new FormData();
			 for (var key in $scope.addAdvancedOrderInfo) {
				   if($scope.addAdvancedOrderInfo[key] != null) {
					   formData.append(key+"", $scope.addAdvancedOrderInfo[key]);
				   }
			 }
			 if($scope.advancedOrderfiles == undefined || $scope.advancedOrderfiles == null || $scope.advancedOrderfiles=="") {
				 formData.append("contentFiles",null);
			 } else {
				 formData.append("contentFiles",$scope.advancedOrderfiles);
			 }
			 /*if($scope.advancedOrderfiles2 == undefined || $scope.advancedOrderfiles2 == null || $scope.advancedOrderfiles2=="") {
				 formData.append("photoOfFinished",null);
			 } else {
				 formData.append("photoOfFinished",$scope.advancedOrderfiles2);
			 }*/
			 AdvancedOrderService.addAdvancedOrder(formData,token).then(function(result){
			       $scope.addAdvancedOrderInfo=result.data; 
			       $scope.getAdvancedOrderLists(pageSize,pageIndex,null);
			    });
		 }
	 };
	 $scope.showAddOrderHtml=function(){
		 layer.open({
		        type:1,
		        title: '预付单添加',
		        area: ['700px','600px'],
		        btn:['确定','取消'],
		        yes:function(index,layero){
		        	$scope.addAdvancedOrderInfo={};
		        	$scope.addAdvancedOrderInfo.leader=layero.find('#leaderName')[0].value;
		        	var projectNames=layero.find('#projectName')[0].value;
		        	var month=layero.find('#month')[0].value;
		        	for(var k=0;k<12;k++){
		        		if(month==$scope.monthList[k]){
		        			$scope.addAdvancedOrderInfo.month=k+1;
		        		}
		        	}
		        	$scope.addAdvancedOrderInfo.projectName=projectNames;
		        	$scope.addAdvancedOrderInfo.nextReceivePeopleId=layero.find('#userName')[0].value;
		        	$scope.addAdvancedOrderInfo.constructPart=layero.find('#constructPart')[0].value;
		        	$scope.advancedOrderfiles=layero.find('#order_img')[0].files[0];
		        	//$scope.advancedOrderfiles2=layero.find('#order_img_finished')[0].files[0];
		        	for(var j=0;j<$scope.projectList.length;j++){
		        		if($scope.projectList[j].name==projectNames){
		        			$scope.addAdvancedOrderInfo.projectId=$scope.projectList[j].id;
		        		}
		        	}
		        	$scope.addAdvancedOrderInfo.quantityDes=layero.find('#quantityDes')[0].value;
		        	$scope.advancedOrderTitle="预付单添加";
		        	$scope.addAdvancedOrder();
		        },
		        content:$("#show04").html()
		    });
		 
	 };
	 //////导出预付单
    $scope.exportAdvancedOrder=function(){
	    	var PID = document.getElementById('app');
		     var cb = PID.getElementsByTagName("input");
			 for(var i=0;i<cb.length;i++){
				 if(cb[i].checked==1){
					 AdvancedOrderService.exportAdvancedOrder($scope.advancedOrderInfos[i].id).then(function (result){
					  	  $scope.advancedOrderUrl = result.data;
					  	  document.getElementById('exportDivs').style.display='block';
					  });
					 break;
				 }
			  }
			
		 };
	 
}