<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>关于我们 编辑页面</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <!-- 基本操作控件,保留 -->
        <link href="${pageContext.request.contextPath}/css/bootstrap-3.2.0/css/bootstrap.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/bootstrap-box.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/bootstrap-page.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/myStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/js/validator/jquery.validator.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/js/datapacker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/global.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/body.css" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jsviews.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/loadTmpl.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
        <script	type="text/javascript" src="${pageContext.request.contextPath}/js/validator/jquery.validator.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/validator/local/zh_CN.js"></script>
		<!-- 富文本编辑依赖文件,不用建议删除 -->
    	<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.config.js"></script>
    	<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.all.min.js"></script>
    	<script type="text/javascript" charset="utf-8" src="../ueditor/lang/zh-cn/zh-cn.js"></script>
    	<!-- 日期操作控件 -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/datapacker/js/bootstrap-datetimepicker.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/datapacker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
        <!-- ajax上传控件 -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
        <!-- 自定义函数 -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/xa3ti.js"></script>
        <!-- <script type="text/javascript">
    		//富文本处理全局参数
        	var ue;
        	
			$(function(){
				
				var tid=getUrlParam("id");
				if(tid==""){
					alert("id参数不能为空");
					return;
				}
				$("#tid").val(tid);
				var url ='../cms/aboutus/findAboutusById';
				if(tid!=null && tid!=0 && tid!=""){
					$.ajax({
						url:url,
						type:'post',
						dataType:'json',
						data:{
							modelId:tid
						},
						success:function(data){
							if(data.code == 1){
								/********************加载数据*****************************/
								   $("#pic").val(data.object.pic);
								   $("#myImageShow").attr('src',xa3ti.imgbaseurl+$("#pic").val());
								   $("#myImageShow").attr('width','100px');
								   $("#myImageShow").attr('height','100px');
								   $("#versionno").val(data.object.versionno);
								   $("#tel").val(data.object.tel);
								   $("#content").val(data.object.content);
								/********************加载数据*****************************/
								/********************加载字典数据*****************************/
								        								/********************加载字典数据*****************************/
							}else{
								alert(data.message);
							}
						}
					});
				}
				
				/** 富文本操作预留,需要可打开此注释
				ue = UE.getEditor('myEditor');*/
				/** 日期处理预留,需要可打开此注释
				$("#startTime").datetimepicker({minView: "month",format: 'yyyy-mm-dd',language : 'zh-CN',autoclose: true});
				$("#endTime").datetimepicker({minView: "month",format: 'yyyy-mm-dd',language : 'zh-CN',autoclose: true});*/
				
				$("#demo_31").validator({
					valid:function(form){
						/** 图片上传处理预留,有图片上传可打开此注释
			    		if(imgUrl.length == 0){
							alert("请上传图片!");
							return;
						}
						if(height&&width&&(height != v_height || width != )){
							alert("为保证终端显示效果,该图片像素建议为"+v_height+"px*"+v_width+"px,请重新上传!");
							return;
						}*/
					    var tid=$("#tid").val();
						var submit_url = "";
						if(typeof(tid)=="undefined" || tid == "" || tid == "0"){
							//新增
							tid = 0;
							submit_url = "../cms/aboutus/saveAboutus";
						}
						else{
							submit_url = "../cms/aboutus/updateAboutus";
						}
			        	/** 富文本操作预留,如需要可打开此注释
						var content=UE.getEditor('myEditor').getContent();*/
						$.ajax({
							url:submit_url,
							type:"POST",
							data:{
								  pic:$("#pic").val(),
								  versionno:$("#versionno").val(),
								  tel:$("#tel").val(),
								  content:$("#content").val(),
								  tid:tid
							},
							success:function(data){
								if(data.code==1){
									window.location.href="aboutusList.jsp";
								}
								else{
									alert(data.message);
								}
							}
						});
					}
				}).on("click","#saveOrupdate",function(e){
					$(e.delegateTarget).trigger("validate");
				});
				// 图片上传处理预留,有图片上传可打开此注释
				$(document).on('change','#uploadPhotoFile',function(){
					$.ajaxFileUpload({
						url:'../cms/aboutus/photoUpload',
						secureuri:false,
						fileElementId:'uploadPhotoFile',
						type:'POST',
						dataType: 'json',			
						success: function (data, status){
							if(data.code){
							 	$("#pic").val(data.object.picturePath);
							    $("#myImageShow").attr('src',xa3ti.imgbaseurl+data.object.picturePath);
								$("#myImageShow").attr('width','100px');
								$("#myImageShow").attr('height','100px');
								height = data.object.height;
								width = data.object.width;
								$("#imagePx").html("宽:"+width+"px,高:"+height+"px");
								/* if(height != v_height || width != v_width){
									alert("为保证终端显示效果,该图片像素建议为"+v_height+"px*"+v_width+"px,请重新上传!");
								} */
							}
							else{
								alert(data.message);
							}
						},
						error: function (data, status, e){
							alert(data);
						}
					});
				});
			});
        </script> -->
    </head>
    <body>
    	<section>
      	<form id="demo_31" class="form-inline">
        	<div class="col-xs-12">
				<div class="span1">
					<span class="badge badge-info">上传结果反馈    &gt;</span>
				</div>
              	<div class="box">
					<div class="box-header">
						<br>
							<input type="button" value="确定" id="saveOrupdate" class="btn btn-success">
							<a href="#" onclick="document.location.href='upload.jsp';" class="btn btn-info" >返回</a>
					</div>
					<div class="tab" id="usercontent">
						<input type="hidden" id="tid"/>
						<input type="hidden" id="versionno"/>
						
					</div>
				</div>
			</div>
		</form>
		</section>
		<div>
			<span>信息提示</span><input type="text" name="result" id="" value=" ${message}"/>
		</div>
    </body>
</html>