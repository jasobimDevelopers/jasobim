function findPs(){
	var userName=$("#username").val();
	var tel=$("#tel").val();
	var email=$("#email").val();
	var realName=$("#realname").val();
	var targetS=document.getElementById("four1");
	var targetF=document.getElementById("four2");
	$.ajax({
         type: "GET",
         url: "api/user/findPass",
         data: {userName:userName, tel:tel, email:email, realName:realName},
         dataType: "json",
         success: function(data){
        	
 				 if(data.data!=null){
 					
 					alert("您的密码已重置为：123456");
 				 }
 				 else{
 					alert("信息输入有误！")
 				 }
 			 
         }
     });

}