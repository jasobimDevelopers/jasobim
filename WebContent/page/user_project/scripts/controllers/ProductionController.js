var index;
function ProductionController($scope,ProductionService) {
	console.log("载入ProductionController");
	$scope.project_name_list="";
	$scope.building_num=0;
	$scope.project_part=["主楼","人防和地库"];
	$scope.profession_type=["电气安装","给排水安装","地库通风安装","公共项安装","消防水安装"];
	$scope.table_title="项目安装工程进度表";
	
	$scope.getProjectNameList = function(){
		ProductionService.getProjectNameList().then(function (result){
			$scope.project_name_list = result.data;
		  });
	};
	$scope.getProjectNameList();
	
	
}