<%
    String contextPath = request.contextPath;
%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="IStore"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<link rel="shortcut icon" href="${resource(dir: 'img', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'img', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'img', file: 'apple-touch-icon-retina.png')}">
		<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.9.1.min.js')}"></script>
		<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
	    <!--[if lt IE 9]>
	      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js">
	      </script>
	    <![endif]-->
	    <!-- Le fav and touch icons -->
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-responsive.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'common.css')}" type="text/css">
		<style>
	      body { 
	      padding-top: 0px; 
	      padding-bottom: 60px; 
	      }
	      
	      @media (max-width: 980px) {
	        /* Enable use of floated navbar text */
	        .navbar-text.pull-right {
	          float: none;
	          padding-left: 5px;
	          padding-right: 5px;
	        }
	      }
	    </style>
		
		
		<g:layoutHead/>
		<r:layoutResources />
		<script type="text/javascript" >
            var contextPath = '${contextPath}';
            function popupDiv(divId){
            	setObjectLayerRange(divId);
                setGreyLayerRange();
                $("#"+divId).slideDown();
            }
            function closePopup(divId){
           		$("#greyLayer").hide();
           	    $("#"+divId).slideUp();
            }
            $(function(){
                var hoverObj = $("#content_list");
                if(hoverObj){
                    var oldClass = "";
					$("#content_list tr").hover(
						function(){
							oldClass = $(this).attr("class");
							$(this).attr("class","hoverTR");
						},function(){
							$(this).attr("class",oldClass);
							oldClass = "";
						});
                }
            });
        </script>
	</head>
	<body>
		<g:render template="/common/header"></g:render>
      <div class="row-fluid">
        <div class="span3">
          <div class="well sidebar-nav">
          <g:if test="${params.controller == 'order'}">
            <ul class="nav nav-list">
              	<li class="nav-header">预约信息</li>
          		<g:if test="${params.action == 'index'}">
          			<li class="active"><a href="javascript:;">&nbsp;&gt;&nbsp;今日预约</a></li>
          			<li><a href="${contextPath }/order/history">&nbsp;&gt;&nbsp;历史信息</a></li>
          		</g:if>
          		<g:else>
          			<li><a href="${contextPath }/order/index">&nbsp;&gt;&nbsp;今日预约</a></li>
          			<li class="active"><a href="javascript:;">&nbsp;&gt;&nbsp;历史信息</a></li>
          		</g:else>
          	</ul>
          </g:if>
          <g:if test="${params.controller == 'push'}">
            <ul class="nav nav-list">
              	<li class="nav-header">推送信息</li>
          		<g:if test="${params.action == 'addPush'}">
          			<li class="active"><a href="javascript:;">&nbsp;&gt;&nbsp;新建推送</a></li>
          			<li><a href="${contextPath }/push/history">&nbsp;&gt;&nbsp;推送列表</a></li>
          		</g:if>
          		<g:else>
          			<li><a href="${contextPath }/push/addPush">&nbsp;&gt;&nbsp;新建推送</a></li>
          			<li class="active"><a href="javascript:;">&nbsp;&gt;&nbsp;推送列表</a></li>
          		</g:else>
          	</ul>
          </g:if>
          <g:if test="${params.controller == 'promotion'}">
            <ul class="nav nav-list">
              	<li class="nav-header">优惠信息</li>
          		<g:if test="${params.action == 'addPromotion'}">
          			<li class="active"><a href="javascript:;">&nbsp;&gt;&nbsp;新增优惠</a></li>
          			<li><a href="${contextPath }/promotion/history">&nbsp;&gt;&nbsp;优惠信息汇总</a></li>
          		</g:if>
          		<g:else>
          			<li><a href="${contextPath }/promotion/addPromotion">&nbsp;&gt;&nbsp;新增优惠</a></li>
          			<li class="active"><a href="javascript:;">&nbsp;&gt;&nbsp;优惠信息汇总</a></li>
          		</g:else>
          	</ul>
          </g:if>
          <g:if test="${params.controller == 'category'}">
            <ul class="nav nav-list">
              	<li class="nav-header">分类管理</li>
              	<li><a href="javascript:;" onclick="popupDiv('addCategory')">&nbsp;&gt;&nbsp;添加分类</a></li>
	            <li class="active"><g:link controller="category" action="index">&nbsp;&gt;&nbsp;分类列表</g:link></li>
            </ul>
          </g:if>
          <g:if test="${params.controller == 'router'}">
            <ul class="nav nav-list">
              	<li class="nav-header">商品管理</li>
              	<li title="创建自己的分类，便于管理大量的商品"><a href="javascript:;" onclick="popupDiv('addCategory')">&nbsp;&gt;&nbsp;添加分类</a></li>
	            <li title="创建自己的商品"><g:link controller="image" action="index">&nbsp;&gt;&nbsp;添加商品</g:link></li>
            </ul>
            <ul class="nav nav-list" id="showCate">
              	<li class="nav-header">商品展示</li>
              	<g:each in="${allCategory }" var="category">
              		<g:if test="${category.id.toString() == catId?.toString()}">
              			<li class="active"><a href="javascript:;">&nbsp;&gt;&nbsp;${category.name }</a></li>
              		</g:if>
              		<g:else>
              			<li><a href="${contextPath }/router/index/${category.id}">&nbsp;&gt;&nbsp;${category.name }</a></li>
              		</g:else>
              	</g:each>
          	</ul>
          </g:if>
          <g:if test="${params.controller == 'store'}">
            <ul class="nav nav-list" id="showCate">
              	<li class="nav-header">商品管理</li>
              	<g:if test="${params.action == 'index'}">
          			<li class="active"><a href="javascript:;">&nbsp;&gt;&nbsp;店铺详情</a></li>
          			<li><a href="${contextPath }/store/changePassword">&nbsp;&gt;&nbsp;修改密码</a></li>
          		</g:if>
          		<g:else>
          			<li><a href="${contextPath }/store/index">&nbsp;&gt;&nbsp;店铺详情</a></li>
          			<li class="active"><a href="javascript:;">&nbsp;&gt;&nbsp;修改密码</a></li>
          		</g:else>
          	</ul>
          </g:if>
          </div><!--/.well -->
        </div><!--/span-->
        <div class="span9">
          <g:layoutBody/>
        </div><!--/span-->
      </div><!--/row-->
		
		<g:render template="/common/footer"></g:render>
		<g:javascript library="application"/>
		<r:layoutResources />
	</body>
</html>
