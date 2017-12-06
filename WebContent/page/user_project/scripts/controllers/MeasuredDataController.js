var index;
function MeasuredDataController($scope,MeasuredDataService) {
	console.log("载入MeasuredDataController");
	var measuredData="";
	$scope.measuredDataList=[];
	$scope.MeasuredDataTofind=[];
	$scope.MeasuredDataPassPercent="";
	$scope.totalPage;
	$scope.currentPage;
	var pageSize=10;
	var pageIndex=1;
	$scope.deleteMeasuredDataInfo="";
	$scope.measuredDatasList=[];
	$scope.measuredDataTitles=["序号","项目名","总包单位","检查部位","安装负责人","安装检查人","提交人","提交时间","合格率","操作"];
	$scope.measuredDataInfoTitles=["序号","检查内容","评判标准","实测点数","合格点数","设计标高","定位尺寸","原始数据"];
	$scope.getMeasuredDataList = function(pageSize,pageIndex,measuredData) {
		MeasuredDataService.getMeasuredDataList(pageSize,pageIndex,measuredData).then(function (result){
		  	  $scope.measuredDataList = result.data;
		      $scope.currentPage = result.currentPage;
		      $scope.totalPage = result.totalPage;
		      $scope.measuredDataPage($scope.totalPage,$scope.currentPage);
		  });
	};
	$scope.getMeasuredDatasList = function(measuredDataId,pageSize,pageIndex,measuredDatas){
		MeasuredDataService.getMeasuredDatasList(measuredDataId,pageSize,-1,measuredDatas).then(function(result){
			$scope.measuredDatasList = result.data;
			$scope.currentPage = result.currentPage;
			$scope.totalPage = result.totalPage;
		});
	};
	$scope.deleteMeasuredDataInfo = function(id){
		if(confirm("确定删除？")) {  
			MeasuredDataService.deleteMeasuredDataById(id).then(function(result){
				  $scope.deleteMeasuredDataInfo=result.data;
				  $scope.getMeasuredDataList(pageSize,pageIndex,measuredData);
			});
		}
	}
	 $scope.goMeasuredDataList = function(){
	    	$scope.getMeasuredDataList(pageSize,$scope.currentPage,null);
	    	document.getElementById('measuredDataInfoHtml').style.display='none';
	    	document.getElementById('projectContent').style.display='block';
	    }
	$scope.measuredDataChange = function(index,ids){
		$scope.MeasuredDataTofind=$scope.measuredDataList[index];
		$scope.MeasuredDataPassPercent=Math.round($scope.MeasuredDataTofind.qualifiedDataSum / $scope.MeasuredDataTofind.measuredDataSum * 10000) / 100.00 + "%";
		$scope.MeasuredDataTofind.id=ids;
		$scope.getMeasuredDatasList($scope.MeasuredDataTofind.id,pageSize,pageIndex,null);
		document.getElementById('measuredDataInfoHtml').style.display='block';
    	document.getElementById('projectContent').style.display='none';
    	
	}
////////分页回调函数
	$scope.measuredDataPage = function(iPageCount,iCurrent) {
		  $("#measuredDataPageCode").remove();
		  $("#table-buton_measured").append("<div id=\"measuredDataPageCode\"></div>");
		  
		  $("#measuredDataPageCode").createPage({
			  
		      pageCount:iPageCount,
	
		      current:iCurrent,
	
		      backFn:function(p){
		    	  console.log($scope.ProjectTofind);
		    	  $scope.getMeasuredDataList(pageSize,p,$scope.MeasuredDataTofind);
		    	  
		      }
	
		  });
	 };
	$scope.getMeasuredDataList(pageSize,pageIndex,measuredData);
}