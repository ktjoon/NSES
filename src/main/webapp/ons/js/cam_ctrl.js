/**
 * 
 */

//http://192.168.25.201/ptz.cgi?cmd=movecont&spd=0,0

var cctv_moveUri	= '/ptz.cgi?cmd=movecont&spd=';
var cctv_zoomUri	= '/ptz.cgi?cmd=zoomcont&dir=';
// base_url?cmd=zoomcont&dir=zoom_dir
// zoom_dir :  0(stop), 1(In), -1(Out)


var CAM_PAN_STOP	= 0;
var CAM_TILT_STOP	= 0;

var oCamStopTimeout = null;

//
function handleStateChange() 
{
	/// do nothing
	if(xmlHttp.readyState == 4) 
	{
		// received OK
		if(xmlHttp.status == 200) 
		{
		}
	}
	else
	{
		// wait...
	}
}

function createXMLHttpRequest() 
{
	if (window.ActiveXObject) 
	{
		// Internet Explorer
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	else if (window.XMLHttpRequest) 
	{
		// Firefox, Safari,...
		xmlHttp = new XMLHttpRequest();
	}
}

function doRequestUsingGETProcSend(QueryString)
{
	try {
		
    createXMLHttpRequest();
 
	run_mode = QueryString;
    ///alert("HTTP Request String \n" + QueryString);
 
    // Change Privilege for firefox's "same origin security policy"
    if (window.netscape && window.netscape.security.PrivilegeManager.enablePrivilege)
	netscape.security.PrivilegeManager.enablePrivilege('UniversalBrowserRead');
 
    // Query Request
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.open("GET", QueryString, true);
 
    // HTTP Streaming, keep the connection.
    //if( mode >= 3 )
   // xmlHttp.setRequestHeader("Keep-Alive", "");   
	//    xmlHttp.setRequestHeader("Content-Type", "image/mpeg");
// There is a fix for cache problem.
	xmlHttp.setRequestHeader("Cache-Control", "no-cache");   
	xmlHttp.setRequestHeader("If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT");   	
//
    xmlHttp.send(null);
 
//	sendDone = 1;
	} catch(e) { }
}

function ptz_exec_cmd(domain, str)
{
	var ptzCmdStr = domain + '/ptz.cgi?cmd=' + str;
	doRequestUsingGETProcSend(ptzCmdStr);
	document.getElementById("cmd_ex").value = ptzCmdStr;
}

function ptz_zoom_stop(svr)
{
	ptzCmdStr = "zoomcont&dir=0";
	ptz_exec_cmd(svr, ptzCmdStr);
}

//
function disableCctvCtrl(pop) {
	$(pop).find('.rc-area').css('pointer-events', 'none');
}
function hideCctvCtrl(pop) {
	$(pop).find('.rc-area').css('pointer-events', 'none');
	$(pop).find('.rc-area').hide();
}
function hideCctvZoomCtrl(pop) {
	$(pop).find('.zoom').css('pointer-events', 'none');
	$(pop).find('.zoom').hide();
}

function moveLeft(pop) {
	if (oCamStopTimeout != null)
		return;
	var sUrl = 'http://' + $(pop).find('#cctv_addr').val() + cctv_moveUri;
	$('#iframetest').attr('src', sUrl + -10 +"," + 0 );
	moveStop(sUrl);
}
function moveRight(pop) {
	if (oCamStopTimeout != null)
		return;
	var sUrl = 'http://' + $(pop).find('#cctv_addr').val() + cctv_moveUri;
	$('#iframetest').attr('src', sUrl + 10 +"," + 0 );
	moveStop(sUrl);
}
function moveUp(pop) {
	if (oCamStopTimeout != null)
		return;
	var sUrl = 'http://' + $(pop).find('#cctv_addr').val() + cctv_moveUri;
	$('#iframetest').attr('src', sUrl + 0 +"," + 10 );
	moveStop(sUrl);
}
function moveDown(pop) {
	if (oCamStopTimeout != null)
		return;
	var sUrl = 'http://' + $(pop).find('#cctv_addr').val() + cctv_moveUri;
	$('#iframetest').attr('src', sUrl + 0 +"," + -10 );
	moveStop(sUrl);
}

//
var m_zoom=50;

var pan_step=50;
var tilt_step=50;
var zoom_step=50;
 
var MAX_ZOOM_SPD=7;


//
function camZoomIn(pop) {
	if (oCamStopTimeout != null)
		return;

	var	saddr = 'http://' + $(pop).find('#cctv_addr').val();
	var	zs = ( m_zoom * MAX_ZOOM_SPD ) / 100;

	zs = Math.max(1,Math.round(zs));

	var	ptzCmdStr = "zoomcont&ds=1"+","+zs;

	ptz_exec_cmd(saddr, ptzCmdStr);

	zoomStop(saddr);
}
function camZoomOut(pop) {
	if (oCamStopTimeout != null)
		return;

	var	saddr = 'http://' + $(pop).find('#cctv_addr').val();
	var	zs = ( m_zoom * MAX_ZOOM_SPD ) / 100;

	zs = Math.max(1,Math.round(zs));

	var	ptzCmdStr = "zoomcont&ds=-1"+","+zs;

	ptz_exec_cmd(saddr, ptzCmdStr);

	zoomStop(saddr);
}

function moveStop(url) {
	oCamStopTimeout = setTimeout(function() {
		$('#iframetest').attr('src', url + CAM_PAN_STOP, CAM_TILT_STOP);
		clearTimeout(oCamStopTimeout);
		oCamStopTimeout = null;
	}, 1000);
}
function zoomStop(svr) {
	oCamStopTimeout = setTimeout(function() {
		clearTimeout(oCamStopTimeout);
		oCamStopTimeout = null;
		ptz_zoom_stop(svr);
	}, 500);
}
