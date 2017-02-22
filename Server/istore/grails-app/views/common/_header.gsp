<%
    String contextPath = request.contextPath;
%>
<div class="logodiv">
	<img  src="${resource(dir: 'img', file: 'logo.png')}">

		<sec:ifLoggedIn>
 		    <p class="navbar-text pull-right">
         您好 <a href="#" class="navbar-link" style="margin-right:10px;"><sec:loggedInUserInfo field="username"/></a>
         <a class="navbar-link" href="${contextPath}/logout">退出</a> |
                 <a class="navbar-link" href="${contextPath}/store/changePassword">修改密码</a>
       </p>
         </sec:ifLoggedIn>
         <sec:ifNotLoggedIn>
          <p class="navbar-text pull-right">
              <a href="${contextPath}/login">登录</a> | <a href="${contextPath}/user/register">注册</a>
          </p>
         </sec:ifNotLoggedIn>
</div>
<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container-fluid">
      <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="brand" href="#"></a>
      <div class="nav-collapse collapse">
        <ul class="nav">
        	<g:if test="${params.controller == 'router'}">
	          <li class="active"><a href="#">商品</a></li>
	        </g:if>
	        <g:else>
	          <li><a href="${contextPath}/router/index">商品</a></li>
	        </g:else>
	        <g:if test="${params.controller == 'category'}">
	          <li class="active"><a href="#">分类管理</a></li>
	        </g:if>
	        <g:else>
	          <li><a href="${contextPath}/category">分类管理</a></li>
	        </g:else>
	        <g:if test="${params.controller == 'push'}">
	          <li class="active"><a href="#">消息推送</a></li>
	        </g:if>
	        <g:else>
	          <li><a href="${contextPath}/push/history">消息推送</a></li>
	        </g:else>
	        <g:if test="${params.controller == 'promotion'}">
	          <li class="active"><a href="#">优惠信息</a></li>
	        </g:if>
	        <g:else>
	          <li><a href="${contextPath}/promotion/history">优惠信息</a></li>
	        </g:else>
	        <g:if test="${params.controller == 'order'}">
	          <li class="active"><a href="javascript:;">预定</a></li>
	        </g:if>
	        <g:else>
	          <li><a href="${contextPath }/order/index">预定</a></li>
	        </g:else>
	        <g:if test="${params.controller == 'app'}">
	          <li class="active"><a href="#">生成APP</a></li>
	        </g:if>
	        <g:else>
	          <li><a href="${contextPath }/appMaker/index">生成APP</a></li>
	        </g:else>
	        <g:if test="${params.controller == 'store'}">
	          <li class="active"><a href="#">商铺管理</a></li>
	        </g:if>
	        <g:else>
	          <li><a href="${contextPath }/store/index">商铺管理</a></li>
	        </g:else>
            <!-- <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li> -->
        </ul>
      </div><!--/.nav-collapse -->
    </div>
  </div>
</div>