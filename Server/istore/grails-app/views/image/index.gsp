<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="justHeader"/>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'image.css')}" type="text/css">
		<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.form.js')}"></script>
		<script type="text/javascript" src="${resource(dir: 'js', file: 'popup.js')}"></script>
		<title>商品编辑</title>
	</head>
	<body>
	        <span style="color: red">${flash.message}</span>
	        <div class="imageArea">
			    <g:uploadForm controller="image" action="upload" name="uploadForm">
				    <div class="imgpreview">
				    	<div class="menu">
				    		<span>图片预览区</span>
				    	</div>
				        <dl>
				      		<input type="file" id="imagefile" title="选择图片" name="imagefile" class="hidefile" style="width:106px;cursor: pointer;" onchange="AjaxUploadPic();" hidefocus>
				      	
			      	        <div><input type="button" id="selectImg" value="选择图片" onclick="$('#imagefile').click();"/></div>
			      	        <g:if test="${img }">
			      	        	<img id="preImg" src="${contextPath}/img/upload/${img.user.id}/${img.imgName}" ${imgstyle }></img>
			      	        </g:if>
			      	        <g:else>
			      	        	<img id="preImg"></img>
			      	        </g:else>
			      	        
				      	</dl>
				
				    </div>
				</g:uploadForm>
			</div>
			<div class="desArea">
				<g:form controller="image" action="saveimg" name="saveForm">
					<div class="pro_des"><span>商品详细信息</span></div>
					<input type="hidden" id="imgId" name="imgId" value="${img?.id }">
					<input type="hidden" id="imgWidth" name="imgWidth" value="${img?.width }">
					<input type="hidden" id="imgHeight" name="imgHeight" value="${img?.height }">
					<input type="hidden" id="imgFileName" name="imgFileName" value="${img?.imgName }">
					<ul>
						<li>商品名称：</li>
						<li><input type="text" size="300" name="prdName" id="prdName" maxlength="50" value="${img?.name }"></li>
						<li>所属分组：</li>
						<li>
							<g:select name="imgCategory" from="${categoryList }" optionKey="id" optionValue="name" value="${img?.category?.id }"/>
						</li>
						<li>商品描述：</li>
						<li>
							<textarea rows="4" cols="100" style="resize:none;" name="prdDesc" maxlength="120">${img?.descriptions }</textarea>
						</li>
					</ul>
					<div class="opArea">
						<a href="javascript:;" onclick="if(paramCheck()){$('#saveForm').submit();}">保存</a>
						<a href="${contextPath }/router/index/${img?.category?.id }" >返回</a>
					</div>
				</g:form>
			</div>
			<script type="text/javascript">
			    
			</script>
	</body>
</html>
