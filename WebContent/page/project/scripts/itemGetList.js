function hideUlst(){
	var idom=document.getElementById("name_div");
	if(idom.style.display=="none"){
		document.getElementById("li_show_hide1").style.backgroundImage="url(page/project/images/menuItem2.png)";
		idom.style.display="block";
	}else{
		document.getElementById("li_show_hide1").style.backgroundImage="url(page/project/images/menuItem1.png)";
		idom.style.display = 'none';
		
	}
	
}
function hideUls(){
	var idom=document.getElementById("type_div");
	if(idom.style.display=="none"){
		document.getElementById("li_show_hide2").style.backgroundImage="url(page/project/images/menuItem2.png)";
		idom.style.display="block";
	}else{
		document.getElementById("li_show_hide2").style.backgroundImage="url(page/project/images/menuItem1.png)";
		idom.style.display = 'none';
	}
	
}
function hideUl(){
	var idom=document.getElementById("state_div");
	if(idom.style.display=="none"){
		document.getElementById("li_show_hide3").style.backgroundImage="url(page/project/images/menuItem2.png)";
		idom.style.display="block";
	}else{
		idom.style.display = 'none';
		document.getElementById("li_show_hide3").style.backgroundImage="url(page/project/images/menuItem1.png)";
	}
	
}
function setProject(index){
	index.style.color=="red";
	var test=index.value;
}