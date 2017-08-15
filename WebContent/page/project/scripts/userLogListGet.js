var data;
var token="";
var dataSum1=[0,0,0,0,0,0,0,0,0,0,0,0];/////0.Model(模型) 
var dataSum2=[0,0,0,0,0,0,0,0,0,0,0,0];///1.Paper(图纸) 
var dataSum3=[0,0,0,0,0,0,0,0,0,0,0,0];///2.Login(登录) 
var dataSum4=[0,0,0,0,0,0,0,0,0,0,0,0];/// 3.Disclose(交底)  
var dataSum5=[0,0,0,0,0,0,0,0,0,0,0,0];// 4.Prefabricate(预制化)   
var dataSum6=[0,0,0,0,0,0,0,0,0,0,0,0]//5.Question(紧急事项)
var dataSum7=[0,0,0,0,0,0,0,0,0,0,0,0];//6.Notification(通知)
var dataSum8=[0,0,0,0,0,0,0,0,0,0,0,0];//7.Production(产值)
var dataSum9=[0,0,0,0,0,0,0,0,0,0,0,0];//8.Member(班组信息 )
var projectId;
var projectPart;
var systemType;
var index;
var dayList1=[31,28,31,30,31,30,31,31,30,31,30,31];
var dayList2=[31,29,31,30,31,30,31,31,30,31,30,31];
var dayList3=[];
var dateDefault1=["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"];
var dateDefault=[];
var monthsA=[1,3,5,7,8,10,12];
var monthsB=[4,6,9,11];
var day;
var day_flag_1=365;
var day_flag_2=366;
var day_flag=0;
var start_day_flag=365;
var days1=30;
var days2=31;
var days3=28;
var days4=29;
var finished_year_flag;
var start_year_flag;
var sumday=0;
var y_max=100;
function hideUlstst(){
	var idom=document.getElementById("name_divs");
	if(idom.style.display=="none"){
		document.getElementById("li_show_hide1s").style.backgroundImage="url(page/project/images/menuItem2.png)";
		idom.style.display="block";
	}else{
		document.getElementById("li_show_hide1s").style.backgroundImage="url(page/project/images/menuItem1.png)";
		idom.style.display = 'none';
		
	}
}
function hideUlsst(){
	var idom=document.getElementById("type_div_userlogs");
	if(idom.style.display=="none"){
		document.getElementById("li_show_hide2s").style.backgroundImage="url(page/project/images/menuItem2.png)";
		idom.style.display="block";
	}else{
		document.getElementById("li_show_hide2s").style.backgroundImage="url(page/project/images/menuItem1.png)";
		idom.style.display = 'none';
	}
	
}
function hideUlst(){
	var idom=document.getElementById("state_divs");
	if(idom.style.display=="none"){
		document.getElementById("li_show_hide3s").style.backgroundImage="url(page/project/images/menuItem2.png)";
		idom.style.display="block";
	}else{
		idom.style.display = 'none';
		document.getElementById("li_show_hide3s").style.backgroundImage="url(page/project/images/menuItem1.png)";
	}
	
}
function getSumday(startTime,finishedTime){
	if(startTime!="" && finishedTime!=""){
		////////起始时间年月日获取
   		var start_tempStr = startTime.split(" ");
   		var start_dateStr = start_tempStr[0].split("-");
   		var start_year = parseInt(start_dateStr[0],10);
   		var start_month = parseInt(start_dateStr[1], 10);
   		var start_day = parseInt(start_dateStr[2], 10);
   	
   		////////结束时间年月日获取
   		var finished_tempStr = finishedTime.split(" ");
   		var finished_dateStr = finished_tempStr[0].split("-");
   		var finished_year = parseInt(finished_dateStr[0],10);
   		var finished_month =  parseInt(finished_dateStr[1],10);
   		var finished_day =  parseInt(finished_dateStr[2],10);
   		
   		///////当前起始时间年份类型判断
		sumday=0;
		day_flag_1=365;
		day_flag_2=366;
		day_flag=0;
		dayList3=[];
		start_day_flag=day_flag_1;
		var finished_sumday=0;
		var start_sumday=0;
		//////////////闰年时计算天数差
   		if(start_year==finished_year){
   			if((start_year % 4 == 0) && (start_year % 100 != 0 || start_year % 400 == 0)){
   				dayList3=dayList2;
   			}else{
   				dayList3=dayList1;
   			}

   			if(start_month==finished_month){
   				sumday=finished_day-start_day;
   			}else{
   				for(var i=start_month-1;i<finished_month-1;i++){
   					sumday+=dayList3[i];
   			       }
   				sumday=sumday-start_day+finished_day;
   			   
   			}
			return sumday;
   		}else
   		{
   			///////////计算截止时间当年天数
   			if((finished_year % 4 == 0) && (finished_year % 100 != 0 || finished_year % 400 == 0)){
   				for(var k=0;k<finished_month-1;k++){
   	   	   			finished_sumday+=dayList2[k];
   	   			}
   			}else{
   				for(var k=0;k<finished_month-1;k++){
   	   	   			finished_sumday+=dayList1[k];
   	   			}
   			}
   			finished_sumday+=finished_day;
   			//////////计算中间间隔年数的总天数
   			for(var k=start_year;k<finished_year;k++){
   				if((k % 4 == 0) && (k % 100 != 0 || k % 400 == 0)){
   	   				day_flag+=day_flag_2;
   	   			}else{
   	   				day_flag+=day_flag_1;
   	   			}
   			}
   			//////////////////////////计算起始时间当年天数
   			if((start_year % 4 == 0) && (start_year % 100 != 0 || start_year % 400 == 0)){
   				for(var k=0;k<start_month-1;k++){
   	   	   			start_sumday+=dayList2[k];
   	   			}
   			}else{
   				for(var k=0;k<start_month-1;k++){
   	   	   			start_sumday+=dayList1[k];
   	   			}
   			}
   			start_sumday+=start_day;
   		    return (day_flag-start_sumday+finished_sumday);
   		}
   	}
}
function setUserLogTime2(){
	dateDefault=[];
	var startTime=document.getElementById("d4311").value;
	var finishedTime=document.getElementById("d4312").value;
	var urls="api/userLog/getUserLogCountSum?token="+token;
	if(startTime!=""){
		urls=urls+"&startTime="+startTime;
	}
	if(finishedTime!=""){
		urls=urls+"&finishedTime="+finishedTime;
	}
	var start_tempStr = startTime.split(" ");
	var start_dateStr = start_tempStr[0].split("-");
	var start_year = parseInt(start_dateStr[0],10);
	var start_month = parseInt(start_dateStr[1], 10);
	var start_day = parseInt(start_dateStr[2], 10);
	
	var sumday=getSumday(startTime,finishedTime);
	var data_time_x= Math.ceil(sumday/12);
	if(data_time_x==1){
		for(var j=0;j<=sumday;j++){
			dateDefault[j]=(start_month+"/"+(start_day+j));
		}
	}else{
		return;
	}
	dataSum1=[0,0,0,0,0,0,0,0,0,0,0,0];/////0.Model(模型) 
	dataSum2=[0,0,0,0,0,0,0,0,0,0,0,0];///1.Paper(图纸) 
	dataSum3=[0,0,0,0,0,0,0,0,0,0,0,0];///2.Login(登录) 
	dataSum4=[0,0,0,0,0,0,0,0,0,0,0,0];/// 3.Disclose(交底)  
	dataSum5=[0,0,0,0,0,0,0,0,0,0,0,0];// 4.Prefabricate(预制化)   
	dataSum6=[0,0,0,0,0,0,0,0,0,0,0,0]//5.Question(紧急事项)
	dataSum7=[0,0,0,0,0,0,0,0,0,0,0,0];//6.Notification(通知)
	dataSum8=[0,0,0,0,0,0,0,0,0,0,0,0];//7.Production(产值)
	dataSum9=[0,0,0,0,0,0,0,0,0,0,0,0];//8.Member(班组信息 )
	$.ajax({
        type: "GET",
        url: urls,
        dataType:'json',
        data: data,
        success: function(data){
	       	 if(data!=null) 
	       	 {
	       		 y_max=50;
	       		var s=data.data;
	       		for(var i=0;i<s.length;i++){
	       			if(y_max<s[i].num){
	       				y_max=s[i].num;
	       			}
        			var tempStr = s[i].date.split(" ");
        	   		var dateStr = tempStr[0].split("-");
        	   		var day = parseInt(dateStr[2], 10);
        	   		if(s[i].projectPart==0){
        	   			dataSum1[day-start_day]=s[i].num;
        	   			
        	   		}
        	   		if(s[i].projectPart==1){
        	   			dataSum2[day-start_day]=s[i].num;
        	   		}
        	   		if(s[i].projectPart==2){
        	   			dataSum3[day-start_day]=s[i].num;
        	   		}
        	   		if(s[i].projectPart==3){
        	   			dataSum4[day-start_day]=s[i].num;
        	   		}
        	   		if(s[i].projectPart==4){
        	   			dataSum5[day-start_day]=s[i].num;
        	   		}
        	   		if(s[i].projectPart==5){
        	   			dataSum6[day-start_day]=s[i].num;
        	   		}
        	   		if(s[i].projectPart==6){
        	   			dataSum7[day-start_day]=s[i].num;
        	   		}
        	   		if(s[i].projectPart==7){
        	   			dataSum8[day-start_day]=s[i].num;
        	   		}
        	   		if(s[i].projectPart==8){
        	   			dataSum9[day-start_day]=s[i].num;
        	   		}
        		}
	       		y_max=Math.ceil(y_max/10)*10;
	       		runChart(dateDefault);
			 } else {
					alert("数据获取失败！");
			 }
        }
	});
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


function setProjectId(index){
	alert(index);
	var test=document.getElementById("text1").value;
	projectId=test;
	alert(test);
}
function setProjectPartId(test,index){
	projectPart=test;
}
function systemType(test){
	systemType=test;
}

function runChart(dateDefault){
	var myChart = echarts.init(document.getElementById('main_contents'));
	option = {
		    title: {
		        text: ''
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['模型','图纸','登录','交底','预制化','紧急事项','通知','产值','班组信息']
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    toolbox: {
		        feature: {
		            saveAsImage: {}
		        }
		    },
		    xAxis: {
		        type: 'category',
		        boundaryGap: false,
		        data: dateDefault
		    },
		    yAxis: {
		        type: 'value',
		        name: '次数',
		    	min : 0,
		    	max : y_max,
		    },
		    series: [
		        {
		            name:'模型',
		            type:'line',
		            data:dataSum1
		        },
		        {
		            name:'图纸',
		            type:'line',
		            data:dataSum2
		        },
		        {
		            name:'登录',
		            type:'line',
		            data:dataSum3
		        },
		        {
		            name:'交底',
		            type:'line',
		            data:dataSum4
		        },
		        {
		            name:'预制化',
		            type:'line',
		            data:dataSum5
		        },
		        {
		            name:'紧急事项',
		            type:'line',
		            data:dataSum6
		        },
		        {
		            name:'通知',
		            type:'line',
		            data:dataSum7
		        },
		        {
		            name:'产值',
		            type:'line',
		            data:dataSum8
		        },
		        {
		            name:'班组信息',
		            type:'line',
		            data:dataSum9
		        }
		    ]
		};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}
token=getCookie("token"); 
//alert(token);
//	折线图 ending
	$(function () {
		$.ajax({
	        type: "GET",
	        url: "api/userLog/getUserLogCountSum?token="+token,
	        dataType:'json',
	        data: data,
	        success: function(data){
		       	 if(data!=null) 
		       	 {
		       		var s =data.data;
		       		dataSum1=[0,0,0,0,0,0,0,0,0,0,0,0];/////0.Model(模型) 
		       		dataSum2=[0,0,0,0,0,0,0,0,0,0,0,0];///1.Paper(图纸) 
		       		dataSum3=[0,0,0,0,0,0,0,0,0,0,0,0];///2.Login(登录) 
		       		dataSum4=[0,0,0,0,0,0,0,0,0,0,0,0];/// 3.Disclose(交底)  
		       		dataSum5=[0,0,0,0,0,0,0,0,0,0,0,0];// 4.Prefabricate(预制化)   
		       		dataSum6=[0,0,0,0,0,0,0,0,0,0,0,0]//5.Question(紧急事项)
		       		dataSum7=[0,0,0,0,0,0,0,0,0,0,0,0];//6.Notification(通知)
		       		dataSum8=[0,0,0,0,0,0,0,0,0,0,0,0];//7.Production(产值)
		       		dataSum9=[0,0,0,0,0,0,0,0,0,0,0,0];//8.Member(班组信息 )
	        		for(var i=0;i<s.length;i++){
	        			if(s[i].num>y_max){
	        				y_max=s[i].num;
	        			}
	        			var tempStr = s[i].date.split(" ");
	        	   		var dateStr = tempStr[0].split("-");
	        	   		var month = parseInt(dateStr[1], 10);
	        	   		if(s[i].projectPart==0){
	        	   			dataSum1[month-1]=s[i].num;
	        	   		}
	        	   		if(s[i].projectPart==1){
	        	   			dataSum2[month-1]=s[i].num;
	        	   		}
	        	   		if(s[i].projectPart==2){
	        	   			dataSum3[month-1]=s[i].num;
	        	   		}
	        	   		if(s[i].projectPart==3){
	        	   			dataSum4[month-1]=s[i].num;
	        	   		}
	        	   		if(s[i].projectPart==4){
	        	   			dataSum5[month-1]=s[i].num;
	        	   		}
	        	   		if(s[i].projectPart==5){
	        	   			dataSum6[month-1]=s[i].num;
	        	   		}
	        	   		if(s[i].projectPart==6){
	        	   			dataSum7[month-1]=s[i].num;
	        	   		}
	        	   		if(s[i].projectPart==7){
	        	   			dataSum8[month-1]=s[i].num;
	        	   		}
	        	   		if(s[i].projectPart==8){
	        	   			dataSum9[month-1]=s[i].num;
	        	   		}
	        		}
	        		y_max=Math.ceil(y_max/10)*10;
		       		runChart(dateDefault1);
		       		
				 } else {
					alert("数据获取失败！");
				}
	        }
    	});
		
		$("input.jcDate").jcDate({
			IcoClass: "jcDateIco",
			Event: "click",
			Speed: 100,
			Left: 0,
			Top: 28,
			format: "-",
			Timeout: 100
		});
		
		
	});
	