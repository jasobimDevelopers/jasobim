var index;
function CodeInformationController($scope,CodeInformationService) {
	console.log("载入CodeInformationController");
	$scope.titlePaper="图纸区";
	$scope.titleVideo="交底区";
	$scope.getPapers = function(flag){
		if(flag=="建筑"){
			$scope.titlePaper="建筑";
		}
		if(flag=="暖通"){
			$scope.titlePaper="暖通";
		}
		if(flag=="给排水"){
			$scope.titlePaper="给排水";
		}
		if(flag=="消防"){
			$scope.titlePaper="消防";
		}
		if(flag=="电气"){
			$scope.titlePaper="电气";
		}
		$(".filePaper").css("display","none");
		$(".fileVideo").css("display","none");
		$(".paperDetail").css("display","block");
		
	}
	$scope.getVideos = function(flag){
		if(flag=="安全"){
			$scope.titleVideo="安全交底";
		}
		if(flag=="质量"){
			$scope.titleVideo="质量交底";
		}
		if(flag=="技术"){
			$scope.titleVideo="技术交底";
		}
		$(".filePaper").css("display","none");
		$(".fileVideo").css("display","none");
		$(".videoDetail").css("display","block");
	}
	$scope.returnPaperList= function(){
		$scope.titlePaper="图纸区";
		$scope.titleVideo="交底区";
		$(".paperDetail").css("display","none");
		$(".filePaper").css("display","block");
		$(".fileVideo").css("display","block");
	}
	$scope.returnVideoList= function(){
		$scope.titlePaper="图纸区";
		$scope.titleVideo="交底区";
		$(".videoDetail").css("display","none");
		$(".filePaper").css("display","block");
		$(".fileVideo").css("display","block");
	}
}