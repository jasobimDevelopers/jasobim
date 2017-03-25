//存储cookies
function setCookie(name,value,expiredays) 
{ 
  var argv = setCookie.arguments; 
  var argc = setCookie.arguments.length; //实参的个数
  var LargeExpDate = new Date (); 
  var expires = (argc > 2) ? argv[2] : expiredays ; 
  if(expires!=null) 
  { 
      LargeExpDate.setTime(LargeExpDate.getTime() + (expires*1000*3600*24));         
  } 
  document.cookie = name + "=" + escape (value)+((expires == null) ? "" : ("; expires=" +LargeExpDate.toGMTString())); 
}

//获取cookies
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

//删除cookies
function deleteCookie(name) 
{ 
  var expdate = new Date(); 
  expdate.setTime(expdate.getTime() - (86400 * 1000 * 1)); 
  setCookie(name, "", expdate); 
}
var token=getCookie('token');