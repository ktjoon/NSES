/**
 * 공통으로 사용하는 함수
 *
 */

function cmLog(s) {
	if (window.console && window.console.log) {
		window.console.log(s);
	}
}

function cmDebug(s) {
	console.log(s);
}

function toStr(n) {
	var	v = '';
	try {
		if (n != null) {
			v = n.toString();
		}
	} catch(e) { }
	return v;
}

function toInt(s) {
	var	v = 0;
	try {
		if (s != null && s != '') {
			v = parseInt(s.toString());
			if (isNaN(v))
				v = 0;
		}
	} catch(e) { }
	return v;
}

function toDouble(s) {
	var	v = 0;
	try {
		if (s != null && s != '') {
			v = parseFloat(s.toString());
			if (isNaN(v))
				v = 0;
		}
	} catch(e) { }
	return v;
}

function IntToZeroStr(num, len) {
	var str = '' + num;
	while (str.length < len) {
    	str = '0' + str;
	}
	return str;
}

//
// param : YYYY-MM-DD
//
function strToDate(s) {
	var		year, month, day;
	var		dtCurr;
	
	year	= strToInt(s.substring(0, 4));
	month	= strToInt(s.substring(5, 7));
	day		= strToInt(s.substring(8, 10));

	dtCurr = new Date(year, month-1, day);
	//dtCurr.setFullYear(year, month-1, day);

	return dtCurr;
}

function timestampToDateString(tsMSec) {
	var		dt;
	var		str = '';
	
	dt = new Date(tsMSec);
	str = str + dt.getFullYear() + '-';
	str = str + IntToZeroStr(dt.getMonth() + 1, 2) + '-';
	str = str + IntToZeroStr(dt.getDate(), 2) + ' ';
	str = str + IntToZeroStr(dt.getHours(), 2) + ':';
	str = str + IntToZeroStr(dt.getMinutes(), 2) + ':';
	str = str + IntToZeroStr(dt.getSeconds(), 2);

	return str;
}

function makeTelNo(sVal) {
	if (sVal.length < 9)
		return sVal;
	
	if (sVal[0] != '0')
		return sVal;

	var	sTel1, sTel2, sTel3;
	var	prefix;
	var	t1cnt = 0;
	
	sTel3 	= sVal.substring(sVal.length-4, sVal.length);
	sTemp12 = sVal.substring(0, sVal.length-4);

	prefix = sTemp12.substring(0, 2);

	if (prefix == '02') {
		t1cnt = 2;
	}
	else {
		prefix = sTemp12.substring(0, 3);

		switch (prefix) {
			case "011":
			case "016":
			case "017":
			case "018":
			case "019":
			case "010":
			case "080":
			case "031":
			case "032":
			case "033":
			case "041":
			case "042":
			case "043":
			case "051":
			case "052":
			case "053":
			case "054":
			case "055":
			case "061":
			case "062":
			case "063":
			case "064":
				t1cnt = 3;
				break;
			default:
				prefix = sTemp12.substring(0, 4);
				if (prefix == '0505')
					t1cnt = 4;
				break;
		}
	}
	
	if (t1cnt <= 0) {
		return sVal;
	}

	sTel1 = sTemp12.substring(0, t1cnt);
	sTel2 = sTemp12.substring(t1cnt, sTemp12.length);

	return (sTel1 + '-' + sTel2 + '-' + sTel3);
}

/**
 * 주민등록번호 포맷 
 * ex) 123456 - 1234567
 * @param sVal
 */
function makeJumin(sVal) {
	if (sVal == null)
		return;
	
	var sJumin1	= 0; 
	var sJumin2	= 0;
	
	if (sVal.length != 13) 
		return sVal;
	
	try {
		sJumin1	= sVal.substring(0, 6);
		sJumin2	= sVal.substring(6, 13);
		
	} catch (e) { }
	
	return sJumin1 + ' - ' + sJumin2;
}

function subStr(n, len) {
	var	v = '';
	try {
		if (n != null) {
			v = n.toString();
			if (v.length > len) {
				v = v.substring(0, len);
			}
		}
	} catch(e) { }
	return v;
}

/**
 * 
 * @param dateStr = "YYYYMMDDhhmmss"
 * @returns {String}
 */
function makeDateTimeString(dateStr) {

	if (dateStr === undefined)
		return '';
	
	if (dateStr == null)
		return '';
	
	if (dateStr.length < 14)
		return dateStr;

	var		str = '';
	
	str += dateStr.substring(0, 4) + '-';
	str += dateStr.substring(4, 6) + '-';
	str += dateStr.substring(6, 8) + ' ';
	str += dateStr.substring(8, 10) + ':';
	str += dateStr.substring(10, 12) + ':';
	str += dateStr.substring(12, 14);

	return str;
}

function makeDateString(dateStr) {

	if (dateStr === undefined)
		return '';
	
	if (dateStr == null)
		return '';
	
	if (dateStr.length < 8)
		return dateStr;

	var		str = '';
	
	str += dateStr.substring(0, 4) + '-';
	str += dateStr.substring(4, 6) + '-';
	str += dateStr.substring(6, 8);

	return str;
}

function makeDateTimeHM(dateStr) {

	if (dateStr === undefined)
		return '';
	
	if (dateStr == null)
		return '';
	
	if (dateStr.length < 12)
		return dateStr;

	var		str = '';
	
	str += dateStr.substring(0, 4) + '-';
	str += dateStr.substring(4, 6) + '-';
	str += dateStr.substring(6, 8) + ' ';
	str += dateStr.substring(8, 10) + ':';
	str += dateStr.substring(10, 12);

	return str;
}

function makeEmbedPlayer(sId, sURL) {
	var	sHtml = '';
	
//	sHtml += '<object classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" codebase="http://download.videolan.org/pub/videolan/vlc/last/win32/axvlc.cab">';
//	sHtml += '	<param name="src" value="' + sURL + '" />';
//	sHtml += '	<param name="autoplay" value="true" />';
//	sHtml += '	<param name="autostart" value="true" />';
	
	sHtml += '<embed ';
	sHtml += '	type="application/x-vlc-plugin"';
	sHtml += '	pluginspage="http://www.videolan.org"';
	sHtml += '	version="VideoLAN.VLCPlugin.2"';
	sHtml += '	width="100%"';
	sHtml += '	height="100%"';
	sHtml += '	id="vlc_' + sId + '"';
	sHtml += '	loop="yes"';
	sHtml += '	autoplay="yes"';
	sHtml += '	windowless="true"';
	sHtml += '	target="' + sURL + '"';
	sHtml += '>';
	sHtml += '</embed>';
	
//	sHtml += '</object>';

	return sHtml;
}

