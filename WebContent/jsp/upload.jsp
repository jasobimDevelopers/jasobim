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
			 <form action="${pageContext.request.contextPath}/servlet/UploadHandleServlet" enctype="multipart/form-data" method="post">
        			上传用户：<input type="text" name="username"><br/>
       				 上传文件1：<input type="file" name="file1"><br/>
        			上传文件2：<input type="file" name="file2"><br/>
        			<input type="submit" value="提交">
   			 </form>
		</div>
    </body>
</html>