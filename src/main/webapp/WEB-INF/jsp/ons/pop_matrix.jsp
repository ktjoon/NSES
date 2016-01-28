<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>cctv 매트릭스</title>
<meta charset="utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style>

.pop-matrix {
    width: 600px;
    height:576px;
    top:0;
    left:0;
    position:absolute;
}

.pop-content {
    width:100%;
    height:100%;
    text-align:center;
    vertical-align:middle;
    color: white;
    margin:0 auto;
}

.btns {
    margin: 10px 14px 0;
    height: 25px;
}

.btns img {
    float: left;
    margin: 7px 5px 0 0;
}

.matrix {
    width: 572px;
    height: 524px;
    margin: 0 14px;
    overflow: hidden;
}

.matrix > ul {
    list-style: none;
    padding: 0;
}

.matrix > ul > li {
    float: left;
    margin: 0 4px;
}

.cctv {
    background: black;
}

.desc {

}

</style>

<script src="./js/libs/jquery.1.10.js"></script>
<script src="./js/utils/com_codes.js"></script>
<script src="./js/utils/com_network.js"></script>
<script src="./js/utils/com_utils.js"></script>
<script src="./js/vmx_player.js"></script>

<script type="text/javascript">

var	g_cctvList = [];

$(function() {
	/*
	$(".btns img").mousedown(function() {
	    $(this).attr("src",$(this).attr("src").replace(/\_off./g,"_on."));
	});
	$(".btns img").mouseup(function() {
	    $(this).attr("src",$(this).attr("src").replace(/\_on./g,"_off."));
	});
	$(".btns img").mouseout(function() {
	    $(this).attr("src",$(this).attr("src").replace(/\_on./g,"_off."));
	});
	*/

    $(".matrix > ul > li").css("width", $(".matrix").width()/2-8)
        .css("height", $(".matrix").height()/2-5);
    $(".cctv").css("height", $(".matrix").height()/2-30);

	$(".fore").click(function() {
		changeMatrix('2');
		doAction_cctvMatrix('2');
	});
	$(".nine").click(function() {
		changeMatrix('3');
		doAction_cctvMatrix('3');
	});
	$(".sixteen").click(function() {
		changeMatrix('4');
		doAction_cctvMatrix('4');
	});
});

function makeCamPlayer(inx) {
	var	sId = 'vlc_' + inx;
	var	sHtml = '';
	
	cmLog('player id: ' + sId);
	sHtml += '<object classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921"';
	sHtml += '	width="100%"';
	sHtml += '	height="100%"';
	sHtml += '	id="' + sId + '"';
	sHtml += '	>';
	sHtml += '	<param name="MRL" value="" />';
	sHtml += '	<param name="ShowDisplay" value="True" />';
	sHtml += '	<param name="AutoLoop" value="False" />';
	sHtml += '	<param name="AutoPlay" value="True" />';
	sHtml += '	<param name="Volume" value="50" />';
	sHtml += '	<param name="toolbar" value="true" />';
	sHtml += '	<param name="StartTime" value="0" />';
	sHtml += '	<EMBED pluginspage="http://www.videolan.org"';
	sHtml += '		type="application/x-vlc-plugin"';
	sHtml += '		version="VideoLAN.VLCPlugin.2"';
	sHtml += '		width="100%"';
	sHtml += '		height="100%"';
	sHtml += '		toolbar="true"';
	sHtml += '		loop="true"';
	sHtml += '		text="Waiting for video"';
	sHtml += '		name="' + sId + '">';
	sHtml += '	</EMBED>';
	sHtml += '</object>';

	return sHtml;
}
function getVLC(name) {
	if (window.document[name]) {
		return window.document[name];
	}
	if (navigator.appName.indexOf("Microsoft Internet")==-1) {
		if (document.embeds && document.embeds[name])
		return document.embeds[name];
	}

	return document.getElementById(name);
}

function doPlay(idx, url) {
	var	rtsp_url = $.trim(url);
	var vlc = getVLC('vlc_' + idx);
	
	if (rtsp_url == '') {
		return;
	}
	if (vlc == null) {
		return;
	}

	var options = [':rtsp-tcp'];
	vlc.playlist.clear();
	vlc.playlist.add(rtsp_url, '', options);
	vlc.playlist.play();
	vlc.style.width = '100%';
	vlc.style.height = '100%';
}

function doAspectRatio(value) {
	var vlc = getVLC("vlc_1");
	if( vlc )
		vlc.video.aspectRatio = value;
}

function play_all() {
	var	msec = 10;
	for (var i = 0; i < g_cctvList.length; i ++) {
		//doPlay(g_cctvList[i].index, g_cctvList[i].url);
		//g_cctvList[i].play();
		g_cctvList[i].playWait(msec);
		msec += 300;
	}
}

function changeMatrix(matrix_type) {
	var	cls_name;
	cls_name = ".fore";
	$(cls_name).attr("src", $(cls_name).attr("src").replace(/\_on./g,"_off."));
	cls_name = ".nine";
	$(cls_name).attr("src", $(cls_name).attr("src").replace(/\_on./g,"_off."));
	cls_name = ".sixteen";
	$(cls_name).attr("src", $(cls_name).attr("src").replace(/\_on./g,"_off."));

	if (matrix_type == '3') {
		$(".matrix > ul > li").css("width", $(".matrix").width()/3-8)
							  .css("height", $(".matrix").height()/3-5);
		$(".cctv").css("height", $(".matrix").height()/3-30);

		cls_name = ".nine";
		$(cls_name).attr("src", $(cls_name).attr("src").replace(/\_off./g,"_on."));
	}
	else if (matrix_type == '4') {
		$(".matrix > ul > li").css("width", $(".matrix").width()/4-8)
							  .css("height", $(".matrix").height()/4-5);
		$(".cctv").css("height", $(".matrix").height()/4-30);

		cls_name = ".sixteen";
		$(cls_name).attr("src", $(cls_name).attr("src").replace(/\_off./g,"_on."));
	}
	else {
		$(".matrix > ul > li").css("width", $(".matrix").width()/2-8)
							  .css("height", $(".matrix").height()/2-5);
		$(".cctv").css("height", $(".matrix").height()/2-30);

		cls_name = ".fore";
		$(cls_name).attr("src", $(cls_name).attr("src").replace(/\_off./g,"_on."));
	}
}
function doAction_cctvMatrix(matrix_type) {
	var url = '/ons/user/update_action.do';
	var params = {cctv_mat : matrix_type};
	
	net_ajax(url, params, function (data) {
		opener.g_userCfg.cctv_mat = matrix_type;
		cmLog('user-config/updata_action(cctv_mat: ' + matrix_type + ')');
	}, onNetCallbackEmptyError);
}

$(document).ready(function() {
	changeMatrix(opener.g_userCfg.cctv_mat);

	//var	sHtml = makeEmbedPlayer('rtsp://218.239.202.211/StdCh1');
	
	//$('#cctv_1').html(sHtml);
	//sHtml = '';
	//sHtml += '<video id="video_cctv" width="100%" height="100%" src="http://clips.vorwaerts-gmbh.de/VfE_html5.mp4" autoplay>';
	//sHtml += '</video>';
	//for (var i = 0; i < 16; i ++)
	//	$('#cctv_' + (i+1)).html(sHtml);
	
	var	aryList = [];
	
	try {
		aryList = opener.getCctvCastRect();
	} catch(e) { }

	g_cctvList = [];
	
	for (var i = 0; i < 16; i ++) {
		var	marker = aryList[i];
		var	inx = i + 1;
		var	cctv_id = '#cctv_' + inx;
		var	desc_id = '#desc_' + inx;

		if (i >= aryList.length) {
			$(cctv_id).html('');
			$(desc_id).text('');
			continue;
		}
		
		/*
		var	info = { index: inx, title: marker.obj.mtitle, url: marker.obj.url };

		g_cctvList.push(info);
		
		$(cctv_id).html(makeCamPlayer(inx));
		$(desc_id).text(marker.obj.mtitle);
		*/
		var	idx 	= getVmxMediaKey();
		var	player 	= new VmxPlayer(idx, marker.obj.url);

		g_cctvList.push(player);
		
		$(cctv_id).html(player.makeHtml(false));
		$(desc_id).text(marker.obj.mtitle);
	}

	setTimeout('play_all()', 1500);
});

</script>

</head>
<body style="background: #50536A">
    <!-- cctv 팝업 클래스 : pop-cctv -->
    <div class="pop-matrix lp">
        <div class="pop-content">
            <div class="btns">
                <img class="fore activebtn" src="./images/btn_cctv4_off.png" alt="4" />
                <img class="nine activebtn" src="./images/btn_cctv9_off.png" alt="9" />
                <img class="sixteen activebtn" src="./images/btn_cctv16_off.png" alt="16" />
            </div>
            <div class="matrix">
                <ul>
                    <li>
                        <div class="cctv" id="cctv_1">cctv</div>
                        <div class="desc" id="desc_1">소금밭사거리1</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_2">cctv</div>
                        <div class="desc" id="desc_2">소금밭사거리2</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_3">cctv</div>
                        <div class="desc" id="desc_3">소금밭사거리3</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_4">cctv</div>
                        <div class="desc" id="desc_4">소금밭사거리4</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_5">cctv</div>
                        <div class="desc" id="desc_5">소금밭사거리5</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_6">cctv</div>
                        <div class="desc" id="desc_6">소금밭사거리6</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_7">cctv</div>
                        <div class="desc" id="desc_7">소금밭사거리7</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_8">cctv</div>
                        <div class="desc" id="desc_8">소금밭사거리8</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_9">cctv</div>
                        <div class="desc" id="desc_9">소금밭사거리9</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_10">cctv</div>
                        <div class="desc" id="desc_10">소금밭사거리10</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_11">cctv</div>
                        <div class="desc" id="desc_11">소금밭사거리11</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_12">cctv</div>
                        <div class="desc" id="desc_12">소금밭사거리12</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_13">cctv</div>
                        <div class="desc" id="desc_13">소금밭사거리13</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_14">cctv</div>
                        <div class="desc" id="desc_14">소금밭사거리14</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_15">cctv</div>
                        <div class="desc" id="desc_15">소금밭사거리15</div>
                    </li>
                    <li>
                        <div class="cctv" id="cctv_16">cctv</div>
                        <div class="desc" id="desc_16">소금밭사거리16</div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>
