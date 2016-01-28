/**
 * 경로탐색 관리
 */

var rt_directsService 		= null;			// 올레맵 경로탐색 서비스 객체
var	rt_directionsRenderer 	= null;			// 올레맵 경로탐색 렌더 객체
var	func_DServiceCallback 	= new Array();	// 경로탐색 콜백함수 ArrayList
var	rt_dsList 				= new Array();	// 경로탐색 데이터 List
var	rt_sch_type				= '9';			// 경로탐색조건

function initRouteData() {
	rt_directsService = new olleh.maps.DirectionsService(const_map_api_key);
}

/**
 * 필수항목
 * item = {
 * 		dsr_seq: '12345', 
 * 		car_no: '서울1가1234',
 * 		route_no: '', 
 * 		origin: new olleh.maps.UTMK(960487, 1955309.75),
 * 		dest: new olleh.maps.UTMK(960804.5, 1977454),
 * 		result: null, 
 * 		reg_action: false
 * }
 * @param item
 */
// 경로데이터 추가
function addRouteData(item) {
	rt_dsList.push(item);
}

function getRouteData(car_no) {
	var	item = null;
	for (var i = 0; i < rt_dsList.length; i ++) {
		if (rt_dsList[i].car_no == car_no) {
			item = rt_dsList[i];
			break;
		}
	}
	return item;
}

// 경로데이터 전체삭제
function clearRouteData() {
	rt_dsList = [];
	if (rt_directionsRenderer != null) {
		rt_directionsRenderer.setMap(null);
	}
}

// 경로검색시작
function searchRouteData(d_seq, sch_type) {
	cmLog('searchRouteData(dsr_seq: ' + d_seq + ')');

	rt_sch_type = sch_type;
	
	var	aryData = rt_dsList;
	var	drivePriority = 0;
	
	func_DServiceCallback = [];
	
	for (var i = 0; i < aryData.length; i ++) {
		var item = aryData[i];
		var	funcStr = '(function(data) { func_directsService_callback("' + d_seq + '", ' + i + ', data); })';

		func_DServiceCallback[i] = eval(funcStr);

		switch(sch_type) {
		case '0':	drivePriority = olleh.maps.DirectionsDrivePriority.PRIORITY_0;	break;
		case '1':	drivePriority = olleh.maps.DirectionsDrivePriority.PRIORITY_1;	break;
		case '2':	drivePriority = olleh.maps.DirectionsDrivePriority.PRIORITY_2;	break;
		case '3':	drivePriority = olleh.maps.DirectionsDrivePriority.PRIORITY_3;	break;
		case '4':	drivePriority = olleh.maps.DirectionsDrivePriority.PRIORITY_4;	break;
		case '9':	drivePriority = '6'; break;
		}
		
		if (sch_type == '9') {
			directRoute({
					origin : item.origin, 
					destination : item.dest, 
					projection : olleh.maps.DirectionsProjection.UTM_K, 
					travelMode : olleh.maps.DirectionsTravelMode.DRIVING,
					priority : drivePriority
				}, 
				'func_DServiceCallback[' + i + ']'
			);
		}
		else {
			rt_directsService.route({
					origin : item.origin, 
					destination : item.dest, 
					projection : olleh.maps.DirectionsProjection.UTM_K, 
					travelMode : olleh.maps.DirectionsTravelMode.DRIVING,
					priority : drivePriority
				}, 
				'func_DServiceCallback[' + i + ']'
			);
		}
	}
}

function directRoute(up, callback) {
	var url = '/ons/api/route.do';
	var	info = smap.getAvoidParams();
	var params = { 
		rptype: '0',			// up.travelMode
		coordtype: '7', 		// up.projection
		priority: '0',			// up.priority
		sx: Math.round(up.origin.x),
		sy: Math.round(up.origin.y),
		ex: Math.round(up.destination.x),
		ey: Math.round(up.destination.y),
		rtmCount: info.count,
		uX: info.ux,
		uY: info.uy,
		rtmCode: info.degree
	};
	var	func_cb = eval(callback);
	
	net_ajax(url, params, function(data) {
		if (data.retCode == const_ret_ok) {
			func_cb(data.extraData);
		}
		else {
			func_cb('');
		}
	}, function () {
		func_cb('');
	});

}

// 경로탐색결과 화면에 표출
function showRouteData(nsmap, car_no) {
	var	item = getRouteData(car_no);

	if (item == null) {
		alert('경로탐색을 하지 않았거나 실패되었습니다.');
		return;
	}
	if (item.result == null) {
		alert('경로탐색결과가 실패되었습니다.');
		return;
	}
	
	drawDirectionsData(nsmap, item.result);
}

// 경로탐색 상세정보 표시
function showRouteDetailEx(car_no) {
	// show html
	var	rt_data 	= getRouteData(car_no);
	var route_cnt	= 0;
	var req_data	= { car_no: car_no, routes: [] };
	
	try {
		route_cnt		= rt_data.result.result.routes.length;
		req_data.routes	= rt_data.result.result.routes;
	} catch(e) { }
	
	if (route_cnt > 0) {
		var url = '/ons/rtsa/data_list.do';
		var params = { req_data : JSON.stringify(req_data) };
		
		net_ajax(url, params, function (data) {
			var	sHtml = '';

			if (data.retCode == const_ret_ok) {
				sHtml = makeDetailHtml(rt_data.result.result.routes, data.routes);
			}
			
			$('#course_car_no').text(car_no);
			$('.pop-content .course-list').html(sHtml);
		}, onNetCallbackEmptyError);
	}
}

function showRouteDetail(car_no) {
	// show html
	var	item = getRouteData(car_no);
	var	sHtml = '';

	do {
		if (item == null) {
			alert('경로탐색을 하지 않았거나 실패되었습니다.');
			break;
		}
		if (item.result == null) {
			alert('경로탐색결과가 실패되었습니다.');
			break;
		}
		
		sHtml = makeDetailHtml(item.result.result.routes);
	} while ( false );
	
	$('#course_car_no').text(car_no);
	$('.pop-content .course-list').html(sHtml);
}

// 1: 정체 		(red)
// 2: 혼잡 		(yellow)
// 3: 원활 		(green)
// 4: 자료없음	(gray)
// 5: 자료없음	(gray)
function makeDetailHtml(rts, routes) {
	var route_cnt	= 0;
	var node_name	= '';
	var sHtml		= '';
	
	try {
		route_cnt	= routes.length;
	} catch(e) { }
	
	for (var i = 0; i < route_cnt; i ++) {
		var route_img	= './images/road/icon_road' + routes[i].type + '.gif';
		
		if (i <= 0 ) {
			node_name 	= convRouteType(routes[i].type);
		} else {
			node_name	= convNextDist(rts[i].nextdist) + ' 진행 후 ' + subStr(convRouteType(routes[i].type), 9);
		}
		
		sHtml += '<div class="course" onclick="centerPoint(' + routes[i].point.x + ', ' + routes[i].point.y +');">';
		sHtml += '<div class="course-icon">';
		sHtml += '<img src="' + route_img + '" />';
		sHtml += '</div>';
		sHtml += '<div class="course-text" title="' + node_name + '">'+ node_name.substring(0, 18) +'</div>';
		sHtml += '<div class="' + getTrafficColor(routes[i].tdp_type) + '"></div>';
		sHtml += '</div>';
	}
	
	return sHtml;
}

function getTrafficColor(num) {
	//var aryColor	= [3,3,2,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3];
	var sColor		= '';
	var	tdpVal		= toInt(num);
	
	switch (tdpVal) {
	case 1 :
		sColor		= 'speed red';
		break;
	case 2 :
		sColor		= 'speed yellow';
		break;
	case 3 :
		sColor		= 'speed green';
		break;
	default :
		sColor		= 'speed gray';
	}
	
	return sColor;
}

function closeRouteDetail() {
	$('.pop-course').hide();
}

// 경로탐색 콜백함수(index, data:XML)
function func_directsService_callback(d_seq, idx, data){ 
	cmLog('func_directsService_callback(dsr_seq: ' + d_seq + ', index: ' + idx + ') - S');
	var dtResult = null;
	
	try {
		dtResult = rt_directsService.parseRoute(data);
	} catch(e) { alert('경로탐색 결과분석이 실패되었습니다.'); }

	var	bRet = false;

	if (dtResult != null) {
		if (dtResult.errms == 'SUCCESS')
			bRet = true;
	}

	if (bRet == false) {
		rt_dsList[idx].route_no 	= dtResult;
		rt_dsList[idx].reg_action 	= false;
		rt_dsList[idx].result 		= null;
		
		alert('경로탐색이 실패되었습니다.');
		return;
	}

	rt_dsList[idx].route_no 	= dtResult.no;
	rt_dsList[idx].reg_action 	= false;
	rt_dsList[idx].result 		= dtResult;

	cmLog('func_directsService_callback(dsr_seq: ' + d_seq + ', index: ' + idx + ') - E - route_no: ' + dtResult.no);

	doAction_RegRouteData(rt_dsList[idx]);
}

// 경로탐색 지도에 표출하는 함수
function drawDirectionsData(nsmap, res_data) {
	if (rt_directionsRenderer == null) {
		rt_directionsRenderer = new olleh.maps.DirectionsRenderer({
            directions: res_data,
            map: nsmap.getMap(), 
            keepView: true,
            offMarkers: false,
            offPolylines: false
        });
	}
	else {
		rt_directionsRenderer.setDirections(res_data);
	}
	rt_directionsRenderer.setMap(nsmap.getMap());
}

// 최적경로전송을 위한 DB저장
function doAction_RegRouteData(item) {
	function onAjaxResult(data) {
		if (data.retCode == const_ret_ok) {
			item.reg_action = true;
		}
	}

	cmLog('doAction_RegRouteData(dsr_seq: ' + item.dsr_seq + ', route_no: ' + item.route_no + ')');

	var url = '/ons/route/reg_action.do';
	var params = {
		dsr_seq: item.dsr_seq, 
		car_id: item.car_id,
		car_no: item.car_no,
		route_no: item.route_no, 
		sch_type: rt_sch_type, 
		sch_detail_data: JSON.stringify(item.result),
		sch_cctv_data: '',
		sch_traffic_data: ''
	};

	net_ajax(url, params, onAjaxResult, onNetCallbackEmptyError);
}


// 사각내의 좌표
RectCoord = function () {
	//
	this.srt_x	= 0;	// 시작좌표 (X) - UTMK
	this.srt_y	= 0;	// 시작좌표 (Y)
	this.end_x	= 0;	// 끝좌표 (X)
	this.end_y	= 0;	// 끝좌표 (Y)
	this.count	= 0;	// 계산된 좌표 갯수
};

RectCoord.prototype = {
	startWGS84: function (lat, lng) {
		var	utmk = new olleh.maps.UTMK.valueOf(new olleh.maps.LatLng(lat, lng));
		var	x = utmk.x;
		var	y = utmk.y;
		this.srt_x	= x;
		this.srt_y	= y;
		this.end_x	= x;
		this.end_y	= y;
		this.count	= 0;
	},
	adjustWGS84: function (add_pos) {
		this.srt_x	= this.srt_x - add_pos;
		this.srt_y	= this.srt_y - add_pos;
		this.end_x	= this.end_x + add_pos;
		this.end_y	= this.end_y + add_pos;
	},
	recalcWGS84: function (lat, lng) {
		var	utmk = new olleh.maps.UTMK.valueOf(new olleh.maps.LatLng(lat, lng));
		var	x = utmk.x;
		var	y = utmk.y;
		
		if (this.srt_x > x) {
			this.srt_x = x;
		} else if (this.srt_x < x) {
			if (this.end_x < x) {
				this.end_x = x;
			}
		}
		
		if (this.srt_y > y) {
			this.srt_y = y;
		} else if (this.srt_y < y) {
			if (this.end_y < y) {
				this.end_y = y;
			}
		}
		
		this.count ++;
	},
	getStartX: function () {
		return this.srt_x;
	},
	getStartY: function () {
		return this.srt_y;
	},
	getEndX: function () {
		return this.end_x;
	},
	getEndY: function () {
		return this.end_y;
	},
	getCount: function () {
		return this.count;
	},
	getBounds: function () {
		var	lbCoord = this.getStartUTMK();
		var	rtCoord = this.getEndUTMK();
		return new olleh.maps.Bounds(lbCoord, rtCoord);
	},
	getStartWGS84: function () {
		return new olleh.maps.LatLng.valueOf(new olleh.maps.UTMK(this.srt_x, this.srt_y));
	},
	getEndWGS84: function () {
		return new olleh.maps.LatLng.valueOf(new olleh.maps.UTMK(this.end_x, this.end_y));
	},
	getStartUTMK: function () {
		return new olleh.maps.UTMK(this.srt_x, this.srt_y);
	},
	getEndUTMK: function () {
		return new olleh.maps.UTMK(this.end_x, this.end_y);
	},
	clear: function () {
		this.srt_x	= 0;
		this.srt_y	= 0;
		this.end_x	= 0;
		this.end_y	= 0;
	}
};


/**
 * 거리 계산 1000m 이상 km 표
 * @param n
 * @returns {String}
 */
function convNextDist(n) {
	var dist		= toStr(n);
	var convDist = '';
	
	var kmeter	= '';
	var meter	= '';
	
	if (dist.length > 3) {
		kmeter	= dist.substring(0, dist.length -3);
		meter	= dist.substring(dist.length -3, dist.length);
		
		convDist	= kmeter + 'km ' + meter + 'm';
	} else {
		convDist = dist + 'm';
	}
	
	return convDist;
}

function convRouteType(s) {
	var str	= '';
	switch (toInt(s)) {
		case 0 : str = '안내없음'; break;
		case 1 : str = '직진'; break;
		case 2 : str = '1시 방향 우회전'; break;
		case 3 : str = '2시 방향 우회전'; break;
		case 4 : str = '우회전'; break;
		case 5 : str = '4시 방향 우회전'; break;
		case 6 : str = '5시 방향 우회전'; break;
		case 7 : str = '7시 방향 우회전'; break;
		case 8 : str = '8시 방향 우회전'; break;
		case 9 : str = '좌회전'; break;
		case 10 : str = '10시 방향 좌회전'; break;
		case 11 : str = '11시 방향 좌회전'; break;
		case 12 : str = '직진 방향에 고가도로 진입'; break;
		case 13 : str = '오른쪽 방향에 고가도로 진입'; break;
		case 14 : str = '왼쪽 방향에 고가도로 진입'; break;
		case 15 : str = '지하차도'; break;
		case 16 : str = '오른쪽 방향에 고가도로 옆 도로'; break;
		case 17 : str = '왼쪽 방향에 고가도로 옆 도로'; break;
		case 18 : str = '오른쪽 방향에 지하차도 옆 도로'; break;
		case 19 : str = '왼쪽 방향에 지하차도 옆 도로'; break;
		case 20 : str = '오른쪽 도로'; break;
		case 21	: str = '왼쪽 도로'; break;
		case 22	: str = '직진 방향에 고속도로 진입'; break;
		case 23	: str = '오른쪽 방향에 고속도로 진입'; break;
		case 24	: str = '왼쪽 방향에 고속도로 진입'; break;
		case 25	: str = '직진 방향에 도시고속도로 진입'; break;
		case 26	: str = '오른쪽 방향에 도시고속도로 진입'; break;
		case 27	: str = '왼쪽 방향에 도시고속도로 진입'; break;
		case 28	: str = '오른쪽 방향에 고속도로 출구'; break;
		case 29	: str = '왼쪽 방향에 고속도로 출구'; break;
		case 30	: str = '오른쪽 방향에 도시고속도로 출구'; break;
		case 31	: str = '왼쪽 방향에 도시고속도로 출구'; break;
		case 32	: str = '분기점에서 직진'; break;
		case 33	: str = '분기점에서 오른쪽'; break;
		case 34	: str = '분기점에서 왼쪽'; break;
		case 35	: str = 'U-turn'; break;
		case 36	: str = '무발성 직진'; break;
		case 37	: str = '터널'; break;
		case 38	: str = '없음'; break;
		case 39	: str = '없음'; break;
		case 40	: str = '로터리에서 1시 방향'; break;
		case 41	: str = '로터리에서 2시 방향'; break;
		case 42	: str = '로터리에서 3시 방향'; break;
		case 43	: str = '로터리에서 4시 방향'; break;
		case 44	: str = '로터리에서 5시 방향'; break;
		case 45	: str = '로터리에서 6시 방향'; break;
		case 46	: str = '로터리에서 7시 방향'; break;
		case 47	: str = '로터리에서 8시 방향'; break;
		case 48	: str = '로터리에서 9시 방향'; break;
		case 49	: str = '로터리에서 10시 방향'; break;
		case 50	: str = '로터리에서 11시 방향'; break;
		case 51	: str = '로터리에서 12시 방향'; break;
		case 999 : str = '출발지'; break;
		case 1000 : str = '경유지'; break;
		case 1001 : str = '목적지'; break;	
	}
	
	return str;
}

function centerPoint(x, y) {
	var coord	= new olleh.maps.UTMK(x, y);
	smap.setCenter(coord);
	//alert(coord);
}