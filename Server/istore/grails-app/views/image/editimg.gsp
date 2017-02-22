<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>编辑图片</title>
	</head>
	<body>
	        <span style="color: red">${flash.message}</span>
		    <g:uploadForm controller="image" action="upload" name="uploadForm">
			    <div style="text-align:center;vertical-align:middle; width:800px;height:600px;margin-top:50px;">
			        <dl>
			      		<input type="file" id="imagefile" name="imagefile" title="选择图片" class="hidefile" style="width:106px;cursor: pointer;" hidefocus>
			      	
		      	        <span><input type="button" value="上传" onclick="uploadf();" class="an3" style="margin-left:80px;"/></span>
			      	</dl>
			
			    </div>
			
			</g:uploadForm>
			<script type="text/javascript">
			    function uploadf(){
			        document.getElementById("uploadForm").submit();
			    }
			</script>
	</body>
</html>
