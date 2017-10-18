var index;
function SuggestionController($scope,SuggestionService) {
	console.log("载入SuggestionController");
	$scope.resetSuggestion = function(){
		document.getElementById("suggestContent").value="";
	}
	$scope.submitMessage = function(){
		var content=encodeURI(document.getElementById("suggestContent").value);
		SuggestionService.addFeedBack(content).then(function(result)
			{
			$scope.feedBackInfo=result;
		});
		
	}
}