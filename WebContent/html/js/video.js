
var token=
function getVideoInformation(){
	 $.ajax({
         type: "GET",
         url: "api/item/getItemById",
         data: {itemId:$("#itemId").val()},
         dataType: "json",
         success: function(data){
        	 if(data!=null) 
        	 {
 				 
 			 } else {
 				alert("构件信息不存在");
 			}
         }
     });

}