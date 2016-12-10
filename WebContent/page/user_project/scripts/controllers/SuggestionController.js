var index;
function SuggestionController($scope,SuggestionService) {
	console.log("载入SuggestionController");
	$scope.resetSuggestion = function(){
		document.getElementById("suggestContent").value="";
	}
	$scope.addSuggestion = function(){
		alert("提交成功！谢谢您的意见,我们会尽快改进！")
	}
}