<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'popup.css')}" type="text/css">
		<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.form.js')}"></script>
		<script type="text/javascript" src="${resource(dir: 'js', file: 'popup.js')}"></script>
		<title>商品展示</title>
	</head>
	<body>
		  <g:if test="${allImage}">
			  <table class="table">
			    <thead>
			      <tr>
			        <th>商品名</th>
			        <th>图片</th>
			        <th>上传时间</th>
			        <th width="450px;">描述</th>
			        <th>管理</th>
			      </tr>
			    </thead>
			    <tbody id="content_list">
			    	<g:each in="${allImage }" status="no" var="image">
			    	  <tr class="${(no % 2) == 0 ? '' : 'clumndiff'}">
				        <td>${image.name }</td>
				        <td><img src="${contextPath}/img/upload/${sessionUser.id}/${image.imgName }" style="height:40px;"/></td>
				        <td><g:formatDate date="${image.dateCreated }"  format="yyyy-MM-dd HH:mm:ss"/></td>
				        <td>${image.descriptions }</td>
				        <td>
				        	<a href="${contextPath}/image/editimg/${image.id}">编辑</a>
				        	<a href="${contextPath}/image/deleteimg/${image.id}">删除</a>  
				        </td>
				      </tr>
			    	</g:each>
			      
			      
			    </tbody>
			  </table>
			  <div id="page">
			  	<div class="pagination">
			 		<g:paginate controller="Router" action="index" params="${params}" next="下一页" prev="上一页" total="${total}" max="${params.max}"/>
				</div>
			  </div>
          </g:if>
          <g:else>
          	  <div class="hero-unit jetstrap-highlighted">
	            <h2>商品展示</h2>
	            <div style="color: #FF5432;">您还没有在这个分组创建商品，点击左侧的菜单创建自己的商品</div>
	            <img src="${resource(dir: 'img', file: 'demo.jpg')}" class="jetstrap-selected"><p></p>
	          </div>
          </g:else>
          <!-- 
          <div class="row-fluid">
            <div class="span4">
              <h2>Heading</h2>
              <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
              <p><a class="btn" href="#">View details »</a></p>
            </div>
          </div> -->
          
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
						    	    	$("#showCate").append("<li><a href='"+contextPath+"/router/index/"+json.catId+"'>&nbsp;&gt;&nbsp;"+json.catName+"</a></li>");
						    	    	closePopup('addCategory');
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

			  	function addOrder(){
			  		$.ajax({
			    	    type: "POST",
			    	    url: contextPath + "/app/addOrder/40281d813f712fdd013f713a27ec0000",
			    	    data: {bookDate:'2013-04-05 15:30:00',content:"预订单间一间",customerName:'PC',tel:'13283232223'},
			    	    dataType:"text",
			    	    success: function(json) {
					    	alert(json);
			    	    },
			    	    error:function(){
			    	    	alert("服务器异常，请稍候再试");
				    	},
			    	    complete:function(){
			    	    	ajaxLock = 0;
			    	    }
			        });	
				}
		  </script>
	</body>
</html>
