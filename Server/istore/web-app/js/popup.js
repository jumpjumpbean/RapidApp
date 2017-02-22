function setGreyLayerRange( params ){
	if ( typeof params == 'undefined' ) { params = {}; }
	//var isDisable = typeof params[ 'enableGreyLayer' ] != 'undefined' ? !params[ 'enableGreyLayer' ] : true;
	var greyLayerId = !params[ 'greyLayerElemId' ] ? 'greyLayer' : params[ 'greyLayerElemId' ];
	var zIndexVal = !params[ 'zIndexValue' ] ? 1010 : params[ 'zIndexValue' ];

	//disableScroll( isDisable );

	var greyheight = $(document).height();
	var greywidth = $(document).width();

    var greyLayerObj = $( '#' + greyLayerId );

    if ( !greyLayerObj.length )
    {
    	greyLayerObj = $( document.createElement( 'div' ) );
    	greyLayerObj.attr( 'id', greyLayerId );
    	greyLayerObj.attr( 'frameborder', 0 );
    	greyLayerObj.addClass( 'greyLayerDefault' );
    	$( 'body' ).append( greyLayerObj );
    }
    greyLayerObj.height(greyheight);
    greyLayerObj.width(greywidth);

    greyLayerObj.css('z-index', zIndexVal);
    greyLayerObj.show();
}

function setObjectLayerRange(layerName) {
    var obj = $("#" + layerName);
    var x = ($(window).width() - obj.width()) / 2;
    var y = $(document).scrollTop()
    + ((document.documentElement
        && document.documentElement.clientHeight || document.body.offsetHeight) - $(
        obj).height()) / 2;
    obj.css("top", y).css("left", x);
}

function disableScroll(hidden) {
    if (hidden)
    {
        $("body").css("overflow-y", "hidden").css("overflow-x", "hidden");
    } else {
        $("body").css("overflow-y", "").css("overflow-x", "");
    }
}

function changeAvatar(type){
	$("#uploadType").val(type);
	$("#upload_img").unbind('change');
	$("#save_pic").unbind("click");
	if(type=="headimg") {
		$("#preview-pane .preview-container").css("width","200px");
		$("#preview-pane .preview-container").css("height","260px");	
		$("#upload_img").bind("change",function(){
			showPreview("headimg");
		});
		$("#save_pic").bind("click",function(){
			savePic("headimg");
		});
	} else if(type=="bgimg") {
		$("#preview-pane .preview-container").css("width","200px");
		$("#preview-pane .preview-container").css("height","38px");
		$("#upload_img").bind("change",function(){
			showPreview("bgimg");
		});
		$("#save_pic").bind("click",function(){
			savePic("bgimg");
		});
	} else if(type=="logo") {
		$("#preview-pane .preview-container").css("width","");
		$("#preview-pane .preview-container").css("height","");
		$("#upload_img").bind("change",function(){
			showPreview(type);
		});
		$("#save_pic").bind("click",function(){
			savePic(type);
		});
	} else if(type=="discovery"){
		$("#preview-pane .preview-container").css("width","200px");
		$("#preview-pane .preview-container").css("height","260px");
		$("#upload_img").bind("change",function(){
			showPreview(type);
		});
		$("#save_pic").bind("click",function(){
			saveDiscovery();
		});
		
	}
	setObjectLayerRange("uploadlayer");
    setGreyLayerRange();
    $("#uploadlayer").slideDown();
}

var jcrop_api;
function initJcrop() {
    var boundx,
        boundy,
        $preview = $('#preview-pane'),
        $pcnt = $('#preview-pane .preview-container'),
        $pimg = $('#preview-pane .preview-container img'),
        xsize = $pcnt.width(),
        ysize = $pcnt.height();
    
    jcrop_api = $('#upimage').Jcrop({
      bgFade: true,
      bgOpacity: .5,
      setSelect: [ 0, 0, 200, 260 ],
      onChange: updatePreview,
      onSelect: updatePreview,
      aspectRatio: xsize/ysize,
      boxWidth: 500,
      boxHeight: 400
    },function(){
      // Use the API to get the real image size
      var bounds = this.getBounds();
      boundx = bounds[0];
      boundy = bounds[1];
      // Store the API in the jcrop_api variable
      jcrop_api = this;
      // Move the preview into the jcrop container for css positioning
      //$preview.appendTo(jcrop_api.ui.holder);
    });
    
    function updatePreview(c)
    {
      if (parseInt(c.w) > 0)
      {
        var rx = xsize / c.w;
        var ry = ysize / c.h;

        $pimg.css({
          width: Math.round(rx * boundx) + 'px',
          height: Math.round(ry * boundy) + 'px',
          marginLeft: '-' + Math.round(rx * c.x) + 'px',
          marginTop: '-' + Math.round(ry * c.y) + 'px'
        });
        
        $('#x1').val(c.x);
        $('#y1').val(c.y);
        $('#x2').val(c.x2);
        $('#y2').val(c.y2);
      }
    };
}

function initJcrop2() {
    var boundx,
        boundy,
        $preview = $('#preview-pane'),
        $pcnt = $('#preview-pane .preview-container'),
        $pimg = $('#preview-pane .preview-container img'),
        xsize = $pcnt.width(),
        ysize = $pcnt.height();
    
    jcrop_api = $('#upimage').Jcrop({
      bgFade: true,
      bgOpacity: .5,
      setSelect: [ 0, 0, 200, 200 ],
      onChange: updatePreview,
      onSelect: updatePreview,
      boxWidth: 500,
      boxHeight: 400,
      minSize:[60,60]
    },function(){
      // Use the API to get the real image size
      var bounds = this.getBounds();
      boundx = bounds[0];
      boundy = bounds[1];
      // Store the API in the jcrop_api variable
      jcrop_api = this;
      // Move the preview into the jcrop container for css positioning
      //$preview.appendTo(jcrop_api.ui.holder);
    });
    
    function updatePreview(c)
    {
      if (parseInt(c.w) > 0)
      {
        var rx = xsize / c.w;
        var ry = ysize / c.h;

        $pimg.css({
          width: Math.round(rx * boundx) + 'px',
          height: Math.round(ry * boundy) + 'px',
          marginLeft: '-' + Math.round(rx * c.x) + 'px',
          marginTop: '-' + Math.round(ry * c.y) + 'px'
        });
        
        $('#x1').val(c.x);
        $('#y1').val(c.y);
        $('#x2').val(c.x2);
        $('#y2').val(c.y2);
      }
    };
}

var uplock = 0;
function AjaxUploadPic() {
	if($("#imagefile").val() == "") {
	    return false;
	}
	if(uplock == 0){
		uplock = 1;
		$('#uploadForm').ajaxSubmit({
		   	type: "post",  
		    dataType: "html",
		    success: function(json) {
		    	var map = getMapFromJsonStr(json);
		    	if(map.error == 0){
		    		$("#selectImg").val("重新选择图片");
		    		$("#preImg").attr("src",contextPath+map.imgurl)
		    		$("#preImg").attr("style",map.style)
		    		$("#imgWidth").val(map.imgWidth)
		    		$("#imgHeight").val(map.imgHeight)
		    		$("#imgFileName").val(map.imgName)
				}else if(map.error == 1){
					alert("图片处理错误，请重新上传");
				}
			},
		    error:function(){
		       	alert("图片上传过程中发生错误!");
			},
			complete:function(){
				uplock = 0;
			}
		});
	}
}

function AjaxUploadIcon() {
	if($("#imagefile").val() == "") {
	    return false;
	}
	if(uplock == 0){
		uplock = 1;
		$('#uploadForm').ajaxSubmit({
		   	type: "post",  
		    dataType: "html",
		    success: function(json) {
		    	var map = getMapFromJsonStr(json);
		    	if(map.error == 0){
		    		$("#selectImg").val("重新选择图片");
		    		$("#preImg").attr("src",contextPath+map.imgurl)
		    		$("#preImg").attr("style",map.style)
		    		$("#imgFileName").val(map.imgName)
				}else if(map.error == 1){
					alert("图片处理错误，请重新上传");
				}
			},
		    error:function(){
		       	alert("图片上传过程中发生错误!");
			},
			complete:function(){
				uplock = 0;
			}
		});
	}
}

function getMapFromJsonStr(json){
	var arr = json.substring(1,json.length-1).split(",");
	var temp;
	var map = {};
	$.each(arr,function(key,val){
		val = $.trim(val);
		temp = val.split(":");
		if(temp.length == 2){
			map[temp[0]] = temp[1];
    	}
    });
	return map;
}

function paramCheck(){
	var res = true;
	var name = $.trim($('#prdName').val());
	if($('#imgFileName').val() == ''){
		alert("请上传商品图片！");
		res = false;
	}else if(name == ""){
		alert("请填写商品名称！");
		res = false;
	}
	return res;
}

function resizeImg(width, height) {
	if(width <=500 && height <= 400) {
		
	} else {
		if(width/height>500/400) {
			$("#upimage").css("width","500px");
			$("#upimage").css("height","auto");
		} else {
			$("#upimage").css("width","auto");
			$("#upimage").css("height","400px");
		} 
	}
}
function showUpload() {
	$("#upload_img").click();
}

function showPreview(page) {
	AjaxUploadPic(page);
}

function cancelUpload(){
	$(".img_body").show();
	$(".headimg_preview").hide();
	$("#greyLayer").hide();
    $("#uploadlayer").slideUp();
    jcrop_api.destroy();
}

function savePic(uploadType){
	if(uplock == 0){
		uplock = 1;
		$("#x1").val(Math.round($("#x1").val()));
		$("#y1").val(Math.round($("#y1").val()));
		$("#x2").val(Math.round($("#x2").val()));
		$("#y2").val(Math.round($("#y2").val()));
		$("#uploadType").val(uploadType);
		$('#saveFrm').ajaxSubmit({
		   	type: "post",  
		    dataType: "json", 
		    success: function(json) {
		    	if(json){
		    		cancelUpload();
		    		if(uploadType == 'headimg'){
		    			$("#head_img").attr("src",json.url);
		    		}else if(uploadType == 'bgimg'){
		    			$("#bgimg").attr("src",json.url);
		    		}else if(uploadType == 'logo'){
		    			$("#logo_img").attr("src",json.url);
		    		}
				}
		    	$("#uploadType").val("");
			},
		    error:function(){
		       	alert("图片上传过程中发生错误!");
			},
			complete:function(){
				uplock = 0;
			}
		});
	}
}

function saveDiscovery(){
	if(uplock == 0){
		uplock = 1;
		$('#discoveryFrm').ajaxSubmit({
		   	type: "post",  
		    dataType: "json", 
		    success: function(json) {
		    	if(json.error == 0){
		    		cancelUpload();
				}
			},
		    error:function(){
		       	alert("图片上传过程中发生错误!");
			},
			complete:function(){
				uplock = 0;
			}
		});
	}
}