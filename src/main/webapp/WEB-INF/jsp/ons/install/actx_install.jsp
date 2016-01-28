<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
﻿<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=no; target-densitydpi=medium-dpi" />

<script src="../js/libs/jquery.1.10.js"></script>
<script src="../js/libs/PluginDetect_VLC.js"></script>
<script src="../js/utils/com_utils.js"></script>

<script type="text/javascript">

//
function closeWindow(ver) {
	opener.g_vlcVersion = ver;
	//alert('close');
	window.close();
}
function appDownload() {
	//var installURL = 'http://210.103.55.49/install/vlc-2.1.5-win32.exe';
	var installURL = '<c:out value="${vlc_url }"></c:out>';
	document.location.href = installURL;
}

function checkInstall() {
	var	version = PluginDetect.getVersion('vlc');
	if (version == null) {
		setTimeout('checkInstall()', 2000);
		return;
	}
	
	closeWindow(version);
}

function clientResizeTo(width, height) {
	width += 16;
	height += 68;
	window.resizeTo(width, height);
	var iLeft = (screen.availWidth - width) >> 1;
	var iTop = (screen.availHeight - height) >> 1;
	window.moveTo(iLeft, iTop);
}
function appInstall() {
	// 1. 플러그인 설치여부 확인
	// 2. 설치되어 있으면 closeWindow()
	// 3. 설치되어 있지 않으면 install()
	// 4. 타이머 2000ms (2초) - 플러그인 설치여부 확인 (설치되면 closeWindow)

	var	version = PluginDetect.getVersion('vlc');
	if (version == null) {
		appDownload();
		// checkInstall();
		document.getElementById('ifrmchk').src = 'actx_check.jsp';
		return;
	}
	closeWindow(version);
}
function doIFrameInit() {
	var objFrame = document.getElementById("ifrmchk");
	var objDoc = objFrame .contentWindow || objFrame .contentDocument;
	var	version = objDoc.getVersion();
	
	if (version == null) {
		setTimeout(function() {
			document.getElementById('ifrmchk').src = 'actx_check.jsp';
		}, 1000);
		return;
	}
	
	closeWindow(version);
}

$(document).ready(function(){
	//clientResizeTo(400, 150);
	appInstall();
});

</script>
</head>

<body style="background: #50536A;">
<div id="option" class="message" style="color:white; width:520px; height:130px; text-align: center; vertical-align:middle;">
	<div style="margin:70px 0 0 0;"><span style="color: orange; font-size:20px;">'미디어플레이어'</span> 설치를 위해<br /> <span style="color: red; font-size:20px;">실행</span> 버튼을 클릭해주세요</div>
</div>

<iframe id="ifrmchk" src="" onload="doIFrameInit();" width="0" height="0"></iframe>
</body>
</html>
