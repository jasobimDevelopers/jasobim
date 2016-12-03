
//存储cookies
function setCookie(name,value,expiredays) 
{ 
    var argv = setCookie.arguments; 
    var argc = setCookie.arguments.length; 
    var LargeExpDate = new Date (); 
    var expires = (argc > 2) ? argv[2] : expiredays ; 
    if(expires!=null) 
    { 
        LargeExpDate.setTime(LargeExpDate.getTime() + (expires*1000*3600*24));         
    } 
    document.cookie = name + "=" + escape (value)+((expires == null) ? "" : ("; expires=" +LargeExpDate.toGMTString())); 
}

// 获取cookies
function getCookie(Name) 
{ 
    var search = Name + "=" 
    if(document.cookie.length > 0) 
    { 
        offset = document.cookie.indexOf(search) 
        if(offset != -1) 
        { 
            offset += search.length 
            end = document.cookie.indexOf(";", offset) 
            if(end == -1) end = document.cookie.length 
            return unescape(document.cookie.substring(offset, end)) 
        } 
        else return "" 
    } 
} 

// 删除cookies
function deleteCookie(name) 
{ 
    var expdate = new Date(); 
    expdate.setTime(expdate.getTime() - (86400 * 1000 * 1)); 
    setCookie(name, "", expdate); 
}

function login(){
	 $.ajax({
         type: "GET",
         url: "api/user/login",
         data: {username:$("#username").val(), password:$("#password").val()},
         dataType: "json",
         success: function(data){
        	 if(data.token!=null) 
        	 {
        		/* setCookie('userIcon',data.data.userIcon);
        		 setCookie('userName',data.data.userName);*/
 				 setCookie('token',data.token);
 				 setCookie('userName',data.data.userName);
 				 setCookie('userIcon',data.data.userIconUrl);
 				 if(data.data.userType==0){
 					window.location.href="userList";
 				 }
 				 else{
 					window.location.href="user_projectList";
 				 }
 			 } else {
 				alert("用户名和密码错误，请重新输入！");
 			}
         }
     });

}