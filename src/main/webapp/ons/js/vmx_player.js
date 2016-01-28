/**
 * 미디어 플레이어
 */

VmxPlayer = function (key, url) {
	this.index	= key;
	this.url	= url;
};

VmxPlayer.prototype = {
	setKey: function (inx) {
		this.index	= inx;
	},
	makeHtml: function (wless) {
		return vmxMakeHtml(this.index, wless);
	},
	play: function () {
		this.playDirect(this.url);
	},
	playWait: function (msec) {
		var	player = this;
		setTimeout(function() {
			player.play();
		}, msec);
	},
	playDirect: function (url) {
		var	rtsp_url = $.trim(url);
		var vlc = this.getObject();
		
		if (rtsp_url == '' || vlc == null) {
			return;
		}

		var options = [':rtsp-tcp'];
		vlc.playlist.clear();
		vlc.playlist.add(rtsp_url, '', options);
		vlc.playlist.play();
		vlc.style.width = '100%';
		vlc.style.height = '100%';
	},
	checkPlayer: function () {
		var vlc = this.getObject();
		if (vlc == null) {
			return false;
		}

		var	bRet = false;
		try {
			vlc.playlist.clear();
			bRet = true;
		} catch(e) { bRet = false; }
		
		return bRet;
	},
	stop: function () {
		var vlc = this.getObject();
		
		if (vlc == null) {
			return;
		}
		vlc.playlist.stop();
	},
	getObject: function () {
		var	sId = vmxGetHtmlId(this.index);
		return vmxGetHtmlObject(sId);
	},
	clear: function () {
		this.index = 0;
	}
};

//
var	g_vmxKeyIndex	= 0;

function getVmxMediaKey() {
	g_vmxKeyIndex ++;
	return g_vmxKeyIndex;
}

//
function vmxGetHtmlId(inx) {
	return 'vlc_' + inx;
}
function vmxMakeHtml(inx, wless) {
	var	sId = vmxGetHtmlId(inx);
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
	if (wless) {
		sHtml += '	windowless="true"';
	}
	sHtml += '		text="Waiting for video"';
	sHtml += '		name="' + sId + '">';
	sHtml += '	</EMBED>';
	sHtml += '</object>';

	return sHtml;
}
function vmxGetHtmlObject(name) {
	if (window.document[name]) {
		return window.document[name];
	}
	if (navigator.appName.indexOf("Microsoft Internet")==-1) {
		if (document.embeds && document.embeds[name])
		return document.embeds[name];
	}

	return document.getElementById(name);
}
function vmxPlay(idx, url) {
	var	sId = vmxGetHtmlId(idx);
	var vlc = vmxGetHtmlObject(sId);
	var	rtsp_url = $.trim(url);
	
	if (rtsp_url == '' || vlc == null) {
		return;
	}

	var options = [':rtsp-tcp'];
	vlc.playlist.clear();
	vlc.playlist.add(rtsp_url, '', options);
	vlc.playlist.play();
	vlc.style.width = '100%';
	vlc.style.height = '100%';
}

function vmxStop(idx) {
	var	sId = vmxGetHtmlId(idx);
	var vlc = vmxGetHtmlObject(sId);
	
	if (vlc == null) {
		return;
	}
	try {
		vlc.playlist.stop();
	} catch(e) { }
}
