/**
 * Created by ljzhang on 2017/9/26.
 */
 var baseUrl="http://www.pm25.in/";
	
function itemInfoController($scope,itemInfoService) {
    var name="id";
    $scope.item="";
    var id = getQueryStringByName(name);
    $scope.getItemDetail = function(id) {
        itemInfoService.getItemDetail(id).then(function (result){
            $scope.itemInfo = result.data;
        });
    }
    $scope.getItemDetail(id);
    ///////通过id获取构件的详细信息


}
function getQueryStringByName(name){
    var result = location.search.match(new RegExp("[\?\&]" + name+ "=([^\&]+)","i"));

    if(result == null || result.length < 1){
        return "";
    }
    return result[1];
}
$(function(){


            $.ajax({

                type: "GET",

                url: baseUrl+"api/querys/aqi_details.json?token=5j1znBVAsnSf5xQyNQyq&city=nanjing",

                data: {},

                dataType: "json",

                success: function(data){
				  console.log(data.data);
                  var getdatas=data;
                }
            });
});

