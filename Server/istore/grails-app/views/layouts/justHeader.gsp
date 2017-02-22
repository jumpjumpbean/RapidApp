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
        </script>
	</head>
	<body>
		<g:render template="/common/header"></g:render>
		
		<div class="container-fluid">
      <div class="row-fluid">
        <g:layoutBody/>
      </div><!--/row-->
		
		<g:render template="/common/footer"></g:render>
		<g:javascript library="application"/>
		<r:layoutResources />
	</body>
</html>
