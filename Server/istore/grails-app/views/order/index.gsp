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
		  <g:if test="${allOrder}">
			  <table class="table">
			    <thead>
			      <tr>
			        <th>序号</th>
			        <th>预定时间</th>
			        <th width="450px;">预定内容</th>
			        <th>联系方式</th>
			        <th>客户姓名</th>
			        <th>操作</th>
			      </tr>
			    </thead>
			    <tbody id="content_list">
			    	<g:each in="${allOrder }" status="no" var="order">
			    	  <tr class="${(no % 2) == 0 ? '' : 'clumndiff'}">
				        <td>${no + 1 }</td>
				        <td><g:formatDate date="${order.bookDate }"  format="yyyy-MM-dd HH:mm:ss"/></td>
				        <td>${order.content }</td>
				        
				        <td>${order.tel }</td>
				        <td>${order.customerName}</td>
				        <td>
				        	<a href="${contextPath}/order/deleteorder/${order.id}">删除</a>  
				        </td>
				      </tr>
			    	</g:each>
			    </tbody>
			  </table>
			  <div id="page">
			  	<div class="pagination">
			 		<g:paginate controller="order" action="${params.action }" params="${params}" next="下一页" prev="上一页" total="${total}" max="${params.max}"/>
				</div>
			  </div>
          </g:if>
          <g:else>
          	  <div class="hero-unit jetstrap-highlighted">
	            <div style="color: #FF5432;">现在还没有预定信息</div>
	          </div>
          </g:else>
	</body>
</html>
