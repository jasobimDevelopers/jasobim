var data;
var dataSum1=[0,0,0,0,0,0,0,0,0,0,0,0];///初始化
var dataSum2=[0,0,0,0,0,0,0,0,0,0,0,0];///出库
var dataSum3=[0,0,0,0,0,0,0,0,0,0,0,0];///安装
var dataSum4=[0,0,0,0,0,0,0,0,0,0,0,0];///完成
var dataTimeList=[];
dataTimeList=["2017-01","2017-02","2017-03","2017-04","2017-05","2017-06","2017-07","2017-08","2017-09","2017-10","2017-11","2017-12"];
	
//	折线图 ending
	$(function () {
		$.ajax({
	        type: "GET",
	        url: "api/duct/getDuctStateSum",
	        dataType:'json',
	        data: data,
	        success: function(data){
	       	 if(data!=null) 
	       	 {
	       		for(var i=0;i<data.data.length;i++){
	       			
	       			for(var j=0;j<12;j++){
	       				if(data.data[i].state==0){
	       					if(data.data[i].month===dataTimeList[j]){
	       						dataSum1[j]=data.data[i].sumDate;
		       				}
       					}
	       				if(data.data[i].state==1){
	       					if(data.data[i].month==dataTimeList[j]){
	       						dataSum2[j]=data.data[i].sumDate;
		       				}
       					}
	       				if(data.data[i].state==2){
	       					if(data.data[i].month==dataTimeList[j]){
	       						dataSum3[j]=data.data[i].sumDate;
		       				}
       					}
	       				if(data.data[i].state==3){
	       					if(data.data[i].month==dataTimeList[j]){
	       						dataSum4[j]=data.data[i].sumDate;
		       				}
       					}
	       				
	       			}
	       			
	       		}
	       		var myChart = echarts.init(document.getElementById('main_contents'));
	    		option = {
	    			title: {
	    				text: ''
	    			},
	    			tooltip: {
	    				trigger: 'axis'
	    			},
	    			legend: {
	    				data:['库存数量','安装数量','出库数量','完成数量']
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
	    				data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
	    			},

	    			yAxis:{
	    				type: 'value',
	    				scale: true,
	    				name: '个数',
	    				max: 6000,
	    				min: 0
	    			},
	    			series: [
	    				{
	    					name:'库存数量',
	    					type:'line',
	    					data:dataSum1
	    				},
	    				{
	    					name:'安装数量',
	    					type:'line',
	    					data:dataSum3
	    				},
	    				{
	    					name:'出库数量',
	    					type:'line',
	    					data:dataSum2
	    				},
	    				{
	    					name:'完成数量',
	    					type:'line',
	    					data:dataSum4
	    				},

	    			]
	    		};
	    		// 使用刚指定的配置项和数据显示图表。
	    		myChart.setOption(option);
				 } else {
					alert("测试失败！");
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
	