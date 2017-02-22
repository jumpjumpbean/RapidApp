<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="justHeader"/>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'image.css')}" type="text/css">
		<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.form.js')}"></script>
		<script type="text/javascript" src="${resource(dir: 'js', file: 'popup.js')}"></script>
		<title>生成APP</title>
	</head>
	<body>
	        <span style="color: red">${flash.message}</span>
	        <div class="imageArea">
			    <g:uploadForm controller="appMaker" action="uploadIcon" name="uploadForm">
				    <div class="imgpreview">
				    	<div class="menu">
				    		<span>上传图标</span>
				    	</div>
				        <dl>
				      		<input type="file" id="imagefile" title="选择图片" name="imagefile" class="hidefile" style="width:106px;cursor: pointer;" onchange="AjaxUploadIcon();" hidefocus>
				      	
			      	        <div><input type="button" id="selectImg" value="选择图片" onclick="$('#imagefile').click();"/></div>
			      	        <g:if test="${app }">
			      	        	<img id="preImg" src="${contextPath}/img/upload/${app.user.id}/appIcon" ${imgstyle }></img>
			      	        </g:if>
			      	        <g:else>
			      	        	<img id="preImg"></img>
			      	        </g:else>
			      	        
				      	</dl>
				
				    </div>
				</g:uploadForm>
			</div>
			<div class="desArea">
				<g:form controller="appMaker" action="saveInfo" name="saveForm">
					<div class="app_des"><span>APP信息:</span></div>
					<input type="hidden" id="imgFileName" name="imgFileName" value="${app?.icon }">
					<ul>
						<li>APP名称：</li>
						<li><input type="text" size="300" name="appName" id="appName" maxlength="50" value="${app?.name }"></li>
						<li>选择模板：</li>
						<li>
							<g:select name="appTemplate" from="${1..3}" value="${app?.template }" />
						</li>
					</ul>
					<div class="opArea">
						<a href="javascript:;" onclick="$('#saveForm').submit();">生成APP</a>
						<a href="${contextPath }/router/index" >返回</a>
					</div>
				</g:form>
			</div>
			<script type="text/javascript">
			    
			</script>
	</body>
</html>
