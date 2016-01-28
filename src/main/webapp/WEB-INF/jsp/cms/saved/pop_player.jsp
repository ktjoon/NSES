<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="영상플레이어" var="sHtmlTitle"></c:set>

<!DOCTYPE html>
<html lang="ko">
<head>
<title><c:out value="${sHtmlTitle }"></c:out></title>
<meta charset="utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

<style>

.pop-matrix {
    width: 500px;
    height:476px;
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

.matrix {
    width: 472px;
    height: 424px;
    margin: 0 14px;
    overflow: hidden;
}
.cctv {
    background: black;
    width: 100%;
    height: 100%;
}

.desc {

}

</style>

<c:import url="../include/common_js.jsp"></c:import>

<script type="text/javascript">
var	g_media_url = '';

function getVLC(name)
{
    if (window.document[name])
    {
        return window.document[name];
    }
    if (navigator.appName.indexOf("Microsoft Internet")==-1)
    {
        if (document.embeds && document.embeds[name])
            return document.embeds[name];
    }
    else // if (navigator.appName.indexOf("Microsoft Internet")!=-1)
    {
        return document.getElementById(name);
    }
}

function doPlay() {
	var vlc = getVLC("vlc");
	if ( vlc ) {
		var options = [":rtsp-tcp"];
		vlc.playlist.clear();
		vlc.playlist.add(g_media_url, "", options);
		vlc.playlist.play();
	}
}
function doStop() {
	var vlc = getVLC("vlc");
	if (vlc) {
		vlc.playlist.stop();
	}
}

$(document).ready(function() {
	var	svraddr = '${params.server }';
	var	uri = '${params.uri }';

	g_media_url = 'http://' + svraddr + uri;

	setTimeout('doPlay()', 1000);
	//$('#id_player').html(sHtml);
	//play(url);
	//sHtml = '';
	//sHtml += '<video id="video_cctv" width="100%" height="100%" src="http://clips.vorwaerts-gmbh.de/VfE_html5.mp4" autoplay>';
	//sHtml += '</video>';
	//$('#id_player').html(sHtml);
});

</script>

</head>
<body>
<object classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921"
	width="470"
	height="420"
	id="vlc"
	>
	<param name="MRL" value="" />
	<param name="ShowDisplay" value="True" />
	<param name="AutoLoop" value="False" />
	<param name="AutoPlay" value="True" />
	<param name="Volume" value="50" />
	<param name="toolbar" value="true" />
	<param name="StartTime" value="0" />
	<EMBED pluginspage="http://www.videolan.org"
		type="application/x-vlc-plugin"
		version="VideoLAN.VLCPlugin.2"
		width="470"
		height="420"
		toolbar="true"
		loop="true"
		text="Waiting for video"
		name="vlc">
	</EMBED>
</object>
<br>
<input type=button value="Play" onClick="doPlay()">&nbsp;&nbsp;
<input type=button value="Stop" onClick="doStop()">
</body>
</html>
