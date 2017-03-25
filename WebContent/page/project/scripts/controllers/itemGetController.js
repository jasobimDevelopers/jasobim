var index;
function ItemGetListController($scope,ItemGetListService) {
	console.log("载入ItemGetListController");
	$scope.resetSuggestion = function(){
		document.getElementById("suggestContent").value="";
	}
	$scope.submitMessage = function(){
		var content=document.getElementById("suggestContent").value;
		SuggestionService.addFeedBack(content).then(function(result)
			{
			$scope.feedBackInfo=result;
		});
		
	}
}