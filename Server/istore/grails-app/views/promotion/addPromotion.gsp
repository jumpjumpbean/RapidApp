<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>创建优惠信息</title>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui.css')}" type="text/css">
		<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-ui.js')}"></script>
		<script type="text/javascript">
		  $(function() {
			$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
			var dayNames = ['日', '一', '二', '三', '四', '五', '六'];
			var monthNames = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];
		    $( "#startDate" ).datepicker({changeMonth: true,changeYear:true,dateFormat: 'yy-mm-dd',dayNamesMin:dayNames,monthNamesShort:monthNames });
		    $( "#endDate" ).datepicker({changeMonth: true,changeYear:true,dateFormat: 'yy-mm-dd',dayNamesMin:dayNames,monthNamesShort:monthNames });
		    $("#content").bind("focus",function(){
		    	$("#lcontent").html("");
			});
		    $("#content").bind("focus",function(){
		    	$("#lcontent").html("");
			});
		    $("#content").bind("blur",function(){
		    	if($.trim($("#content").val()) == ""){
					  $("#lcontent").html("请填写优惠内容!");
				}
			});
		    $("#startDate").bind("change",function(){
		    	if($("#startDate").val() > $("#endDate").val()){
					  $("#ldate").html("开始日期不能大于结束日期!");
				}else{
					$("#ldate").html("");
				}
			});
		    $("#endDate").bind("change",function(){
		    	if($("#startDate").val() > $("#endDate").val()){
					$("#ldate").html("开始日期不能大于结束日期!");
				}else{
					$("#ldate").html("");
				}
			});
		  });
		  function checkParam(){
			  if($.trim($("#content").val()) == ""){
				  $("#lcontent").html("请填写优惠内容!");
				  return false;
			  }
			  if($("#startDate").val() > $("#endDate").val()){
				  $("#ldate").html("开始日期不能大于结束日期!");
				  return false;
			  }
			  return true;
		  }
	    </script>
	    <style type="text/css">
	    	#push{
	    		margin-left:10px;
	    	}
	    	#push input{
	    		margin-left:5px;
	    	}
	    	#addForm li{
	    		margin-bottom:15px;
	    	}
	    	#addForm label{
	    		margin-left:10px;
	    		display:inline-block;
	    		color:#EE0000;
	    	}
	    </style>
	</head>
	<body>
	        <span style="color: red">${flash.message}</span>
		    <g:form controller="promotion" action="savePromotion" name="addForm">
			    <div style="text-align:left;vertical-align:middle; width:800px;height:600px;margin-top:10px;">
			    	<ul>
			    	<li>
			    		<p>请输入优惠内容：</p>
			      		<textarea cols="250" rows="5" id="content" name="content" maxlength="100" style="width:400px;height:100px;resize: none;"></textarea>
			      		<label id="lcontent"></label>
			      	</li>
			      	<li>
			      		<p>请选择优惠期间：</p>
			      		<input type="text" id="startDate" name="startDate" readonly style="width:180px;" value='<g:formatDate date="${new Date()}"  format="yyyy-MM-dd"/>'> ~ 
			      		<input type="text" id="endDate" name="endDate" readonly style="width:180px;" value='<g:formatDate date="${new Date()}"  format="yyyy-MM-dd"/>'>
			      		<label id="ldate"></label>
			      	</li>
			      	<li>
			      		<p>是否推送此优惠消息：
			      		<span id="push">
				      		<input type="radio" name="ispush" value="1" checked="checked">是
							<input type="radio" name="ispush" value="0" />否
						</span>
						</p>
			      	</li>
			      	</ul>
					<div class="opArea">
						<a href="javascript:;" class="comm_btn" onclick="if(checkParam()){$('#addForm').submit();}">发布</a>
						<a href="/Promotion/history/" class="comm_btn">取消</a>
					</div>
					
			    </div>
			
			</g:form>
	</body>
</html>
