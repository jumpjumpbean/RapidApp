<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>账户总览</title>
    <meta name="layout" content="blackbg"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'style1.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.2.custom.min.css')}" type="text/css">
    <style type="text/css">
    .ui-timepicker-div .ui-widget-header { margin-bottom: 8px; } .ui-timepicker-div dl { text-align: left; } .ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; } .ui-timepicker-div dl dd { margin: 0 10px 10px 65px; } .ui-timepicker-div td { font-size: 90%; } .ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; } .ui_tpicker_hour_label,.ui_tpicker_minute_label,.ui_tpicker_second_label, .ui_tpicker_millisec_label,.ui_tpicker_time_label{padding-left:20px}
    </style>
</head>
<body>
<div id="lmmc">账户中心</div>
<div id="lmmc1">账户总览</div>
<div id="anlysis_lm1">
    <div class="anlysis_lm1_1">
        <div class="l"><p>艾薇儿用户</p>
            %{--<img src="${resource(dir: 'images', file: 'system_anlysis_2_tx.jpg')}" width="86" height="86" />--}%
            <g:set var="weixin" value="${com.iwill.Weixin.findByUserBaseinfoAndIsDeleted(user,false,[max:1])?.get(1)}"></g:set>
            <img src="${weixin?.avatar ? request.contextPath + "/" + weixin.avatar.substring(weixin.avatar.indexOf('upload')) : resource(dir: 'images', file: 'system_anlysis_2_tx.jpg')}"
            width="86" height="86" />
        </div>
        <div class="m"><p>账户余额:</p><p>${user?.sum} 元</p><p>
            %{--<img src="${resource(dir: 'images', file: 'system_account_2.jpg')}" width="76" height="32" />--}%
        </p></div>
        <div class="r"><p>往期资产:</p>
            <dd><img src="${resource(dir: 'images', file: 'system_anlysis_2_1.jpg')}" width="17" height="14" align="absmiddle" /> 累计充值: ${user?.totalRecharge} 元</dd>
            <dd><img src="${resource(dir: 'images', file: 'system_anlysis_2_2.jpg')}" width="17" height="14" align="absmiddle" /> 已投放金额: ${user?.consumed} 元</dd>
            <dd><i><a target="_blank" href="${request.contextPath}/price"><img src="${resource(dir: 'images', file: 'system_anlysis_2_3.jpg')}" width="17" height="14" align="absmiddle" /> [推广价格明细]</a></i></dd></div>
        <div class="clear"></div>
    </div>
    <g:form controller="user" action="pandect" name="pandectForm">
        <div class="anlysis_lm1_2"><span>出入明细</span><p>时间：<input name="beginDate" id="beginDate" type="text" class="sr" value="${params.beginDate}"/>-<input name="endDate" id="endDate" type="text" class="sr" value="${params.endDate}"/> <input name="" type="button" class="an" value="查看" onclick="$('#pandectForm').submit()"/></div>
    </g:form>
    <table border="0" cellspacing="0" cellpadding="0" style=" margin:0 auto;margin-top:15px;border:1px #CCC solid; background-color:#f0f0f0; width:825px; height:35px;">
        <tr style=" text-align:left; text-align:center; font-size:14px; font-weight:bold;">
            <td width="150">时间</td>
            <td width="150">点数</td>
            <td width="150">类型</td>
            <td width="373">备注</td>
        </tr>
    </table>
    <table border="0" cellspacing="0" cellpadding="0" style=" margin:0 auto;width:825px; height:55px;">
        <g:each in="${rechargeList}" var="recharge">
            <tr style="text-align:center;color:#2F3031">
                <td width="150" style="border-bottom:1px #ccc solid">${recharge?.rechargeDate?.format("yyyy-MM-dd hh:mm:ss")}</td>
                <td width="150" style="border-bottom:1px #ccc solid">${recharge?.sum}</td>
                <td width="150" style="border-bottom:1px #ccc solid">${recharge?.type == 1?"充值":"扣除"}</td>
                <td width="375" style="border-bottom:1px #ccc solid">[结算] ${recharge.memo}</td>
            </tr>
        </g:each>
    </table>
    <div id="page">

        <div class="pagination">
            <g:paginate controller="user" action="pandect" params="${params}"  next="下一页" prev="上一页" total="${total}"/>
            <g:if test="${total > params.max }">
                <input id="npage" name="" type="text" class="sr3" /> <input type="button" class="an2" value="跳转" onclick="jump();"/>
            </g:if>
        </div>

    </div>
</div>
<script type="text/javascript" src="${resource(dir:'js',file: 'jquery-ui-1.10.2.custom.min.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js',file: 'jquery-ui-timepicker-addon.js')}"></script>
<script type="text/javascript">
    function jump(){
        var strP=/^\d+$/;
        var npage = $("#npage").val();

        if(npage != undefined && strP.test(npage)) {
            var offeset = (npage-1)*${params.max};
            if(offeset > ${total}){
                alert("输入的页码太大，请确认！");
            }else{
                window.location.href = "${request.contextPath }/user/pandect?offset="+offeset;
            }
        }else{
            alert("输入的页码不正确，请确认！");
        }
    }
    $(document).ready(function(){
        $( "#beginDate,#endDate" ).datetimepicker({
            dateFormat:'yy-mm-dd',
            showSecond: true,
            timeFormat: 'HH:mm:ss'
        });

    });
</script>
</body>
</html>