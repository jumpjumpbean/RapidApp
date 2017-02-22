<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.form.js')}"></script>
		<title>优惠信息汇总</title>
	</head>
	<body>
		  <g:if test="${allpromotion}">
			  <table class="table">
			    <thead>
			      <tr>
			        <th>序号</th>
			        <th width="450px;">优惠内容</th>
			        <th>开始日期</th>
			        <th>结束日期</th>
			        <th>创建时间</th>
			        <th>操作</th>
			      </tr>
			    </thead>
			    <tbody id="content_list">
			    	<g:each in="${allpromotion }" status="no" var="promotion">
			    	  <tr class="${(no % 2) == 0 ? '' : 'clumndiff'}">
				        <td>${no + 1 }</td>
				        <td>${promotion.content }</td>
				        <td><g:formatDate date="${promotion.beginDate }"  format="yyyy-MM-dd"/></td>
				        <td><g:formatDate date="${promotion.endDate }"  format="yyyy-MM-dd"/></td>
				        <td><g:formatDate date="${promotion.dateCreated }"  format="yyyy-MM-dd HH:mm:ss"/></td>
				        <td>
				        	<a href="${contextPath}/promotion/deletepromotion/${promotion.id}">删除</a>  
				        </td>
				      </tr>
			    	</g:each>
			      
			      
			    </tbody>
			  </table>
			  <div id="page">
			  	<div class="pagination">
			 		<g:paginate controller="promotion" action="history" params="${params}" next="下一页" prev="上一页" total="${total}" max="${params.max}"/>
				</div>
			  </div>
          </g:if>
          <g:else>
          	  <div class="hero-unit jetstrap-highlighted">
	            <div style="color: #FF5432;">现在还没有优惠信息</div>
	          </div>
          </g:else>
	</body>
</html>
