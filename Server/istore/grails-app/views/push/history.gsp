<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.form.js')}"></script>
		<title>推送列表</title>
	</head>
	<body>
		  <g:if test="${allPush}">
			  <table class="table">
			    <thead>
			      <tr>
			        <th>序号</th>
			        <th>创建时间</th>
			        <th width="550px;">推送内容</th>
			        <th>操作</th>
			      </tr>
			    </thead>
			    <tbody id="content_list">
			    	<g:each in="${allPush }" status="no" var="pmsg">
			    	  <tr class="${(no % 2) == 0 ? '' : 'clumndiff'}">
				        <td>${no + 1 }</td>
				        <td><g:formatDate date="${pmsg.dateCreated }"  format="yyyy-MM-dd HH:mm:ss"/></td>
				        <td>${pmsg.content }</td>
				        <td>
				        	<a href="${contextPath}/push/deletepush/${pmsg.id}">删除</a>  
				        </td>
				      </tr>
			    	</g:each>
			      
			      
			    </tbody>
			  </table>
			  <div id="page">
			  	<div class="pagination">
			 		<g:paginate controller="push" action="history" params="${params}" next="下一页" prev="上一页" total="${total}" max="${params.max}"/>
				</div>
			  </div>
          </g:if>
          <g:else>
          	  <div class="hero-unit jetstrap-highlighted">
	            <div style="color: #FF5432;">现在还没有推送信息</div>
	          </div>
          </g:else>
	</body>
</html>
