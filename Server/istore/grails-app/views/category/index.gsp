<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'popup.css')}" type="text/css">
		<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.form.js')}"></script>
		<script type="text/javascript" src="${resource(dir: 'js', file: 'popup.js')}"></script>
		<title>推送列表</title>
	</head>
	<body>
		  <g:if test="${allCategory}">
			  <table class="table">
			    <thead>
			      <tr>
			        <th>序号</th>
			        <th width="400px;">分类名称</th>
			        <th>创建时间</th>
			        <th>操作</th>
			      </tr>
			    </thead>
			    <tbody id="content_list">
			    	<g:each in="${allCategory }" status="no" var="catelog">
			    	  <tr class="${(no % 2) == 0 ? '' : 'clumndiff'}">
				        <td>${no + 1 }</td>
				        <td>${catelog.name }</td>
				        <td><g:formatDate date="${catelog.dateCreated }"  format="yyyy-MM-dd HH:mm:ss"/></td>
				        <td>
				        	<a href="${contextPath}/category/deletecategory/${catelog.id}">删除</a>  
				        </td>
				      </tr>
			    	</g:each>
			      
			      
			    </tbody>
			  </table>
			  <div id="page">
			  	<div class="pagination">
			 		<g:paginate controller="category" action="index" params="${params}" next="下一页" prev="上一页" total="${total}" max="${params.max}"/>
				</div>
			  </div>
          </g:if>
          <g:else>
          	  <div class="hero-unit jetstrap-highlighted">
	            <div style="color: #FF5432;">现在还没有分类信息</div>
	          </div>
          </g:else>
          <div id="greyLayer"></div>
		  <div class="popup" id="addCategory">
		  	<div class="popHeader">
		  		<div class="popTitle">添加分类</div>
		  		<div class="closeBtn" onclick="closePopup('addCategory')"><a>X</a></div>
		  	</div>
		  	<div class="popBody">
		  		<span>分类名：</span><input type="text" id="category_name" name="category_name" size="300">
		  	</div>
		  	<div class="popFoot">
		  		<input type="button" value="确定" onclick="addCategory();"><input type="button" value="取消" onclick="closePopup('addCategory');">
		  	</div>
		  </div>
		  <script type="text/javascript">
		  		var ajaxLock = 0;
		  		function addCategory(){
			  		if(ajaxLock == 0){
				  		var catName = $.trim($("#category_name").val());
				  		if(catName != ""){
					  		ajaxLock = 1;
				  			$.ajax({
					    	    type: "POST",
					    	    url: contextPath + "/router/addCategory",
					    	    data: {catName:catName},
					    	    dataType:"json",
					    	    success: function(json) {
					    	    	if(json.error == 0){
						    	    	window.location.href=contextPath+"/category"
						    	    }else{
							    	    alert(json.msg);
							    	}
					    	    },
					    	    error:function(){
					    	    	alert("服务器异常，请稍候再试");
					    	    	closePopup('addCategory');
						    	},
					    	    complete:function(){
					    	    	ajaxLock = 0;
					    	    }
					        });
				  		}
			  		}
			  	}
		  </script>
	</body>
</html>
