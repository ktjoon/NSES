/**
 * 회피지점 관리
 */

var geocoderAvoid = null;
var	g_lastAvoidCoord = null;

function checkAvoid(bCheck) {
	if (bCheck) {
		$("#avoidBtn").css("box-shadow", "0px 1px 3px #FA9126");
		$("#avoidBtn .text").css("background-color", "RGBA(250, 145, 38, 0.8)")
							.css("border-top-color", "#FA9126")
							.css("border-left-color", "#FA9126")
							.css("border-bottom-color", "#FA9126");
		$("#avoidBtn .img").css("border-top-color", "#FA9126")
							.css("border-right-color", "#FA9126")
							.css("border-bottom-color", "#FA9126");
		$("#layer_container").css("cursor", "crosshair");
	}
	else {
		$("#avoidBtn").css("box-shadow", "0px 1px 3px #000000");
		$("#avoidBtn .text").css("background-color", "rgba(0, 0, 0, 0.8)")
							.css("border-top-color", "#000000")
							.css("border-left-color", "#000000")
							.css("border-bottom-color", "#000000");
		$("#avoidBtn .img").css("border-top-color", "#000000")
							.css("border-right-color", "#000000")
							.css("border-bottom-color", "#000000");
		$("#layer_container").css("cursor", "default");
	}
	
	g_isAvoid = bCheck;
}

// 회피지점 리스트 마커 표시
function showListAvoid() {
	
	function onAjaxResult(data) {
		if (data.retCode == const_ret_ok) {
			smap.clearAvoid();
			for (var i = 0; i < data.items.length; i ++) {
				var	item = data.items[i];
				var	dataobj = new MarkerInfo(item.road_seq, item.road_title, item.reg_dt, item.degree_type, '', item.road_type);
				smap.makeAvoid(item.poi_x, item.poi_y, dataobj);
			}
		} else {
			alert(data.retMsg);
		}
	}

	var url = '/ons/road/list_ajax.do';
	var params = {	};

	net_ajax(url, params, onAjaxResult, onNetCallbackEmptyError);
}

// 회피지점을 등록하기 위하여 MapClick
function onMapClick_avoidPoint(coord) {
	g_lastAvoidCoord = coord;
	
	if (geocoderAvoid == null) {
		geocoderAvoid = new olleh.maps.Geocoder(const_map_api_key);
	}
	geocoderAvoid.geocode({ 
			type: 1, 
			newAddr: 1, 
			x: coord.x,
			y: coord.y
		}, 
		"reversegeocode_setAvoidCallback"
	);
}

// 리버스 지오코딩 결과 콜백 함수
function reversegeocode_setAvoidCallback(data) {
	var	new_addr = '';
	var geoResult = geocoderAvoid.parseGeocode(data);
	if (geoResult['count'] != '0'){
		var infoArr = geoResult['infoarr']; 
		if (infoArr.length > 0) {
			new_addr = infoArr[0].address; 
		}
	}
	showPopupRegAvoid(new_addr);
}

// 회피지점 설정 팝업
function showPopupRegAvoid(addr) {
	//$pop = createPop($pop_setavoid);
	$pop = createModalPop('pop-setavoid');
	$pop.find(".pop-content .mid .condition ul li").mousedown(function() {
		$pop.find("#avoid-reason").text($(this).text());
		$pop.find("#avoid_type").val($(this).val());
	});

	$pop.find("#avoid_addr").val(addr);
}
// 회피지점 설정
function setAvoid(pop) {
	if (g_lastAvoidCoord == null)
		return;

	var	coord 		= g_lastAvoidCoord;
	var	title  		= $pop.find("#avoid_addr").val();
	var	road_type	= '1';
	var	degree_type	= pop.find("#avoid_type").val();
	
	cmLog('degree_type: ' + degree_type + ', title: ' + title);

	function onAjaxResult(data) {
		if (data.retCode == const_ret_ok) {
			var	itemobj = new MarkerInfo(data.resKey, title, data.extraData, degree_type, '', road_type);
			smap.makeAvoid(coord.x, coord.y, itemobj);
			alert("회피지점이 설정 되었습니다.");
			removePop(pop);
			checkAvoid(false);
		} else {
			alert(data.retMsg);
		}
	}

	var url = '/ons/road/reg_action.do';
	var params = {
		road_title: title, 
		road_type: road_type, 
		degree_type: degree_type, 
		poi_x: coord.x,
		poi_y: coord.y
	};

	net_ajax(url, params, onAjaxResult, onNetCallbackDefaultError);
}
function showPopupDelAvoid(road_seq) {
	//$pop = createPop($pop_delavoid);
	$pop = createModalPop('pop-delavoid');
	$pop.find('#del_avoid_key').val(road_seq);
}
// 회피지점 삭제
function delAvoid(pop) {
	var ukey = pop.find('#del_avoid_key').val();
	function onAjaxResult(data) {
		if (data.retCode == const_ret_ok) {
			smap.removeAvoid(ukey);
			showMarkerDetail('');
			removePop(pop);
			alert("회피지점이 삭제 되었습니다.");
		} else {
			alert(data.retMsg);
		}
	}

	var url = '/ons/road/del_action.do';
	var params = {
		road_seq: ukey
	};
	
	net_ajax(url, params, onAjaxResult, onNetCallbackDefaultError);
}
// 회피지점 초기화
function clearAvoid(pop) {
	function onAjaxResult(data) {
		if (data.retCode == const_ret_ok) {
			smap.clearAvoid();
			removePop(pop);
			if (g_lastShowType == TYPE_AVOID)
				showMarkerDetail('');
			alert("회피지점이 초기화 되었습니다.");
		} else {
			alert(data.retMsg);
		}
	}

	var url = '/ons/road/clear_action.do';
	var params = { };
	
	net_ajax(url, params, onAjaxResult, onNetCallbackDefaultError);
}
