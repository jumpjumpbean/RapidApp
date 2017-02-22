<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>创建推送</title>
	</head>
	<body>
	        <span style="color: red">${flash.message}</span>
		    <g:form controller="push" action="savePush" name="addForm">
			    <div style="text-align:left;vertical-align:middle; width:800px;height:600px;margin-top:10px;">
			    	<p>请输入推送内容：</p>
			      		<textarea cols="250" rows="5" id="msgcontent" name="content" maxlength="100" style="width:400px;height:100px;resize: none;"></textarea>
					<div class="opArea">
						<a href="javascript:;" class="comm_btn" onclick="if($.trim($('#msgcontent').val())!=''){$('#addForm').submit();}">推送</a>
						<a href="/push/history/" class="comm_btn">取消</a>
					</div>
			
			    </div>
			
			</g:form>
			<script type="text/javascript">
			    function uploadf(){
			        document.getElementById("uploadForm").submit();
			    }
			</script>
	</body>
</html>
