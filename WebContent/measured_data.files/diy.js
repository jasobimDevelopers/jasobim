var projectName;
var leaderUnit;
var checkPart;
var checkUser;
var checkType="0,1,2,3,4,5,6,7";//0.检查内容 1.评判标准 2.实测点数 3.合格点数 4.设计标高 5.定位尺寸 6.原始数据 7.备份
var checkContent="0,1,2,3,3,4,4,5,6,7,8,9,10,10,11,11,12,12";//0.户内强电箱 1.户内弱电箱 2.盒子墙面的垂直度 3.开关 4.厨房插座 5.客厅或卧室插座
							///6.阳台插座 7.闭路电视 8.红外幕帘 9.网络电话 10.手动报警按钮 11.客厅空调插座 12.插座
var measuredData;//实测点数
var qualifiedData;////合格点数
var measuredDataSum;///实测点数总计
var qualifiedDataSum;///合格点数总计
var checkTemplate;//评判标准
var designLevel="1800,500,0,1300,150~200,1500,0,300,1500,300,0,300,800,0,2200,0,300,0";//设计标高
var locationSize="0,0,0,标高,门边,0,0,0,0,0,0,0,0,0,0,0,0,0";//定位尺寸
var leaderName;
var constructor;
var quantiter;
var submiteUser;
var submiteUser;
var	celiangUser;
var	checkMoreUser;
function submit(){
	projectName=document.getElementById('project_name').value;
	leaderUnit="上海嘉实（集团）有限公司";
	checkPart=document.getElementById('project_part').value;
	document.getElementById('project_name').value=document.getElementById('project_name').value;
	document.getElementById('project_part').value=document.getElementById('project_part').value;
	var sum11=0;var sum12=0;var sum21=0;var sum22=0;var sum31=0;
	var sum32=0;var sum41=0;var sum42=0;var sum51=0;var sum52=0;
	var sum61=0;var sum62=0;var sum71=0;var sum72=0;var sum81=0;
	var sum82=0;var sum91=0;var sum92=0;var sum101=0;var sum102=0;
	var sum111=0;var sum112=0;var sum121=0;var sum122=0;var sum131=0;
	var sum132=0;var sum141=0;var sum142=0;var sum151=0;var sum152=0;
	var sum161=0;var sum162=0;var sum171=0;var sum172=0;var sum181=0;
	var sum182=0;
	var text1=document.getElementsByName('sum1_data');
	var text2=document.getElementsByName('sum2_data');
	var text3=document.getElementsByName('sum3_data');
	var text4=document.getElementsByName('sum4_data');
	var text5=document.getElementsByName('sum5_data');
	var text6=document.getElementsByName('sum6_data');
	var text7=document.getElementsByName('sum7_data');
	var text8=document.getElementsByName('sum8_data');
	var text9=document.getElementsByName('sum9_data');
	var text10=document.getElementsByName('sum10_data');
	var text11=document.getElementsByName('sum11_data');
	var text12=document.getElementsByName('sum12_data');
	var text13=document.getElementsByName('sum13_data');
	var text14=document.getElementsByName('sum14_data');
	var text15=document.getElementsByName('sum15_data');
	var text16=document.getElementsByName('sum16_data');
	var text17=document.getElementsByName('sum17_data');
	var text18=document.getElementsByName('sum18_data');
	var text1a="";var text2a="";var text3a="";var text4a="";
	var text5a="";var text6a="";var text7a="";var text8a="";
	var text9a="";var text10a="";var text11a="";var text12a="";
	var text13a="";var text14a="";var text15a="";var text16a="";
	var text17a="";var text18a="";
	var flag=0;
	for(var s in text1){
		flag++;
		if(flag<=12){
			if(text1[s].value!=""){
				var strss=parseInt(s)+1+":"+text1[s].value;
				if(text1a==""){
					text1a=strss;
				}else{
					text1a+=","+strss
				}
		    }
		}
		if((parseInt(text1[s].value)-1800)>=(-5) && (parseInt(text1[s].value-1800))<=5 && text1[s].value!=""){
			sum12++;
		}else if(text1[s].value!=undefined && text1[s].value!=""){
			sum11++
		}
	}
	flag=0;
	for(var q in text2){
		flag++;
		if(flag<=12){
			if(text2[q].value!=""){
				
				var strss=parseInt(q)+1+":"+text2[q].value;
				if(text2a==""){
					text2a=strss;
				}else{
					text2a+=","+strss
				}
			}
		}
		if((parseInt(text2[q].value)-500)>=(-5) && (parseInt(text2[q].value)-500)<=5 && text2[q].value!=""){
			sum22++;
		}else if(text2[q].value!=undefined && text2[q].value!=""){
			sum21++
		}
	}
	flag=0;
	for(var p in text3){
		flag++;
		if(flag<=12){
			if(text3[p].value!=""){
				
				var strss=parseInt(p)+1+":"+text3[p].value;
				if(text3a==""){
					text3a=strss;
				}else{
					text3a+=","+strss
				}
			}
		}
		if(text3[p].value>=(-1) && text3[p].value<=1 && text3[p].value!=""){
			sum32++;
		}else if(text3[p].value!=undefined && text3[p].value!=""){
			sum31++
		}
	}
	flag=0;
	for(var w in text4){
		flag++;
		if(flag<=12){
			if(text4[w].value!=""){
				
				var strss=parseInt(w)+1+":"+text4[w].value;
				if(text4a==""){
					text4a=strss;
				}else{
					text4a+=","+strss
				}
			}
		}
		if((parseInt(text4[w].value)-1300)>=(-5) && (parseInt(text4[w].value)-1300)<=5 && text4[w].value!=""){
			sum42++;
		}else if(text4[w].value!=undefined && text4[w].value!=""){
			sum41++
		}
	}
	flag=0;
	for(var w in text5){
		flag++;
		if(flag<=12){
			if(text5[w].value!=""){
				
				var strss=parseInt(w)+1+":"+text5[w].value;
				if(text5a==""){
					text5a=strss;
				}else{
					text5a+=","+strss
				}
				
			}
		}
		if((parseInt(text5[w].value)-150)>=(-5) && (parseInt(text5[w].value)-200)<=5 && text5[w].value!=""){
			sum52++;
		}else if(text5[w].value!=undefined && text5[w].value!=""){
			sum51++
		}
	}
	flag=0;
	for(var w in text6){
		flag++;
		if(flag<=12){
			if(text6[w].value!=""){
				var strss=parseInt(w)+1+":"+text6[w].value;
				if(text6a==""){
					text6a=strss;
				}else{
					text6a+=","+strss
				}
			}
		}
		if((parseInt(text6[w].value)-1300)>=(-5) && (parseInt(text6[w].value)-1300)<=5 && text6[w].value!=""){
			sum62++;
		}else if(text6[w].value!=undefined && text6[w].value!=""){
			sum61++
		}
	}
	flag=0;
	for(var w in text7){
		flag++;
		if(flag<=12){
			if(text7[w].value!=""){
				var strss=parseInt(w)+1+":"+text7[w].value;
				if(text7a==""){
					text7a=strss;
				}else{
					text7a+=","+strss
				}
			}
		}
		if(text7[w].value>=(-5) && text7[w].value<=5 && text7[w].value!=""){
			sum72++;
		}else if(text7[w].value!=undefined && text7[w].value!=""){
			sum71++
		}
	}
	flag=0;
	for(var w in text8){
		flag++;
		if(flag<=12){
			if(text8[w].value!=""){
				var strss=parseInt(w)+1+":"+text8[w].value;
				if(text8a==""){
					text8a=strss;
				}else{
					text8a+=","+strss
				}
			}
		}
		if((parseInt(text8[w].value)-850)>=(-5) && (parseInt(text8[w].value)-850)<=5 && text8[w].value!=""){
			sum82++;
		}else if(text8[w].value!=undefined && text8[w].value!=""){
			sum81++
		}
	}
	flag=0;
	for(var w in text9){
		flag++;
		if(flag<=12){
			if(text9[w].value!=""){
				var strss=parseInt(w)+1+":"+text9[w].value;
				if(text9a==""){
					text9a=strss;
				}else{
					text9a+=","+strss
				}
			}
		}
		if((parseInt(text9[w].value)-1300)>=(-5) && (parseInt(text9[w].value)-1300)<=5 && text9[w].value!=""){
			sum92++;
		}else if(text9[w].value!=undefined && text9[w].value!=""){
			sum91++
		}
	}
	flag=0;
	for(var w in text10){
		flag++;
		if(flag<=12){
			if(text10[w].value!=""){
				var strss=parseInt(w)+1+":"+text10[w].value;
				if(text10a==""){
					text10a=strss;
				}else{
					text10a+=","+strss
				}
			}
		}
		if((parseInt(text10[w].value)-850)>=(-5) && (parseInt(text10[w].value)-850)<=5 && text10[w].value!=""){
			sum102++;
		}else if(text10[w].value!=undefined && text10[w].value!=""){
			sum101++
		}
	}
	flag=0;
	for(var w in text11){
		flag++;
		if(flag<=12){
			if(text11[w].value!=""){
				var strss=parseInt(w)+1+":"+text11[w].value;
				if(text11a==""){
					text11a=strss;
				}else{
					text11a+=","+strss
				}
			}
		}
		if((parseInt(text11[w].value)-2400)>=(-5) && (parseInt(text11[w].value)-2400)<=5 && text11[w].value!=""){
			sum112++;
		}else if(text11[w].value!=undefined && text11[w].value!=""){
			sum111++
		}
	}
	flag=0;
	for(var w in text12){
		flag++;
		if(flag<=12){
			if(text12[w].value!=""){
				var strss=parseInt(w)+1+":"+text12[w].value;
				if(text12a==""){
					text12a=strss;
				}else{
					text12a+=","+strss
				}
			}
		}
		if((parseInt(text12[w].value)-750)>=(-5) && (parseInt(text12[w].value)-750)<=5 && text12[w].value!=""){
			sum122++;
		}else if(text12[w].value!=undefined && text12[w].value!=""){
			sum121++
		}
	}
	flag=0;
	for(var w in text13){
		flag++;
		if(flag<=12){
			if(text13[w].value!=""){
				var strss=parseInt(w)+1+":"+text13[w].value;
				if(text13a==""){
					text13a=strss;
				}else{
					text13a+=","+strss
				}
			}
		}
		if((parseInt(text13[w].value)-750)>=(-5) && (parseInt(text13[w].value)-750)<=5 && text13[w].value!=""){
			sum132++;
		}else if(text13[w].value!=undefined && text13[w].value!=""){
			sum131++
		}
	}
	flag=0;
	for(var w in text14){
		flag++;
		if(flag<=12){
			if(text14[w].value!=""){
				var strss=parseInt(w)+1+":"+text14[w].value;
				if(text14a==""){
					text14a=strss;
				}else{
					text14a+=","+strss
				}
			}
		}
		if((parseInt(text14[w].value)-500)>=(-5) && (parseInt(text14[w].value)-500)<=5 && text14[w].value!=""){
			sum142++;
		}else if(text14[w].value!=undefined && text14[w].value!=""){
			sum141++
		}
	}
	flag=0;
	for(var w in text15){
		flag++;
		if(flag<=12){
			if(text15[w].value!=""){
				var strss=parseInt(w)+1+":"+text15[w].value;
				if(text15a==""){
					text15a=strss;
				}else{
					text15a+=","+strss
				}
			}
		}
		if((parseInt(text15[w].value)-2500)>=(-5) && (parseInt(text15[w].value)-2500)<=5 && text15[w].value!=""){
			sum152++;
		}else if(text15[w].value!=undefined && text15[w].value!=""){
			sum151++
		}
	}
	flag=0;
	for(var w in text16){
		flag++;
		if(flag<=12){
			if(text16[w].value!=""){
				var strss=parseInt(w)+1+":"+text16[w].value;
				if(text16a==""){
					text16a=strss;
				}else{
					text16a+=","+strss
				}
			}
		}
		if((parseInt(text16[w].value)-300)>=(-5) && (parseInt(text16[w].value)-300)<=5 && text16[w].value!=""){
			sum162++;
		}else if(text16[w].value!=undefined && text16[w].value!=""){
			sum161++
		}
	}
	flag=0;
	for(var w in text17){
		flag++;
		if(flag<=12){
			if(text17[w].value!=""){
				var strss=parseInt(w)+1+":"+text17[w].value;
				if(text17a==""){
					text17a=strss;
				}else{
					text17a+=","+strss
				}
			}
		}
		if((parseInt(text17[w].value)-300)>=(-5) && (parseInt(text17[w].value)-300)<=5 && text17[w].value!=""){
			sum172++;
		}else if(text17[w].value!=undefined && text17[w].value!=""){
			sum171++
		}
	}
	flag=0;
	for(var w in text18){
		flag++;
		if(flag<=12){
			if(text18[w].value!=""){
				var strss=parseInt(w)+1+":"+text18[w].value;
				if(text18a==""){
					text18a=strss;
				}else{
					text18a+=","+strss
				}
			}
		}
		if(text18[w].value>=(-5) && text18[w].value<=5 && text18[w].value!=""){
			sum182++;
		}else if(text18[w].value!=undefined && text18[w].value!=""){
			sum181++
		}
	}
	measuredData=1+"a"+text1a;
	measuredData=measuredData+"-"+2+"a"+text2a
				+"-"+3+"a"+text3a
				+"-"+4+"a"+text4a
				+"-"+5+"a"+text5a
				+"-"+6+"a"+text6a
				+"-"+7+"a"+text7a
				+"-"+8+"a"+text8a
				+"-"+9+"a"+text9a
				+"-"+10+"a"+text10a
				+"-"+11+"a"+text11a
				+"-"+12+"a"+text12a
				+"-"+13+"a"+text13a
				+"-"+14+"a"+text14a
				+"-"+15+"a"+text15a
				+"-"+16+"a"+text16a
				+"-"+17+"a"+text17a
				+"-"+18+"a"+text18a;
	
	document.getElementById('sum11').value=(sum11+sum12);
	document.getElementById('sum12').value=sum12;
	document.getElementById('sum21').value=(sum21+sum22);
	document.getElementById('sum22').value=sum22;
	document.getElementById('sum31').value=(sum31+sum32);
	document.getElementById('sum32').value=sum32;
	document.getElementById('sum41').value=(sum41+sum42);
	document.getElementById('sum45').value=(sum42+sum52);
	document.getElementById('sum51').value=(sum51+sum52);
	document.getElementById('sum61').value=(sum61+sum62);
	document.getElementById('sum67').value=(sum62+sum72);
	document.getElementById('sum71').value=(sum71+sum72);
	document.getElementById('sum81').value=(sum82+sum81);
	document.getElementById('sum82').value=sum82;
	document.getElementById('sum92').value=sum92;
	document.getElementById('sum91').value=(sum91+sum92);

	document.getElementById('sum102').value=sum102;
	document.getElementById('sum101').value=(sum101+sum102);
	document.getElementById('sum112').value=sum112;
	document.getElementById('sum111').value=(sum111+sum112);

	document.getElementById('sum122').value=sum122;
	document.getElementById('sum121').value=(sum121+sum122);

	document.getElementById('sum1314').value=sum132+sum142;
	document.getElementById('sum131').value=(sum131+sum132);
	document.getElementById('sum141').value=(sum142+sum141);

	document.getElementById('sum151').value=(sum152+sum151);
	document.getElementById('sum1516').value=(sum152+sum162);
	document.getElementById('sum161').value=(sum162+sum161);

	document.getElementById('sum171').value=(sum172+sum171);
	document.getElementById('sum1718').value=(sum172+sum182);
	document.getElementById('sum181').value=(sum182+sum181);
	var all_data=(sum11+sum12)+(sum21+sum22)+(sum31+sum32)+(sum41+sum42)
	+(sum51+sum52)+(sum61+sum62)+(sum71+sum72)+(sum82+sum81)+(sum91+sum92)+(sum101+sum102)+(sum111+sum112)
	+(sum121+sum122)+(sum131+sum132)+(sum142+sum141)+(sum152+sum151)+(sum162+sum161)+(sum172+sum171)+(sum182+sum181);
	document.getElementById('all_data').value=all_data;
	measuredDataSum=all_data;

	var all_pass=sum12+sum22+sum32+sum42+sum52+sum62+sum72+sum82+sum92+sum102+sum112+sum122+sum132+sum142+sum152+sum162+sum172+sum182;
	qualifiedDataSum=all_pass;
	document.getElementById('all_pass').value=all_pass;
	var pass_percent=Math.round(all_pass / all_data * 10000) / 100.00 + "%";
	document.getElementById('pass_percent').value=pass_percent;
	document.getElementById('leader_name').value=document.getElementById('leader_name').value;

	document.getElementById('construtor_name').value=document.getElementById('construtor_name').value;
	document.getElementById('quantiter_name').value=document.getElementById('quantiter_name').value;
	checkUser=document.getElementById('quantiter_name').value;
	leaderName=document.getElementById('leader_name').value;
	constructor=document.getElementById('construtor_name').value;
	quantiter=document.getElementById('quantiter_name').value;
	document.getElementById('submit_celiang').value=document.getElementById('submit_celiang').value;
	document.getElementById('submit_user').value=document.getElementById('submit_user').value;
	document.getElementById('fucha_user').value=document.getElementById('fucha_user').value;
	submiteUser=document.getElementById('submit_user').value;
	celiangUser=document.getElementById('submit_celiang').value;
	checkMoreUser=document.getElementById('fucha_user').value;
	postData();
}
var baseUrl="http://jasobim.com.cn/";
var webToken="jasobim";
//var baseUrl="http://localhost:8080/jasobim/";
function postData(){
	$.ajax({
         type: "POST",
         url: baseUrl+"api/measuredData/addMeasuredData",
         data: 
         {
         	checkUser:checkUser,leaderName:leaderName,constructor:constructor,
         	quantiter:quantiter,submiteUser:submiteUser,celiangUser:celiangUser,
         	checkMoreUser:checkMoreUser,qualifiedDataSum:qualifiedDataSum,
         	measuredDataSum:measuredDataSum,measuredData:measuredData,
         	designLevel:designLevel,checkType:checkType,checkContent:checkContent,
         	projectName:projectName,leaderUnit:leaderUnit,checkPart:checkPart,
         	designLevel:designLevel,locationSize:locationSize,webToken:webToken
         	},
         dataType: "json",
         success: function(data){
        	alert("提交成功！");
         }
     });
}
 
