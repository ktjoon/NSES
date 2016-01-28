/**
 * 차량인식 및 장애차량 단속/메시지 처리
 */

function RecogMarker(marker, type) {
	this.coord	= marker.getPosition();
	this.obj	= new MarkerInfo(marker.obj.mkey, marker.obj.mtitle, '', type);
	
	this.obj.eaddr		= '';
	this.obj.url		= '';
	this.obj.ipaddr		= '';

	if (type == '1' || type == '2') {
		this.obj.eaddr		= marker.obj.eaddr;		// 추가2 - 주소
		this.obj.url		= marker.obj.url;		// 추가3 - CCTV URL
		this.obj.ipaddr		= marker.obj.ipaddr;	// 추가4 - CAM IPADDRESS
	}
	else if (type == '3') {
		this.obj.url		= $.trim(marker.obj.url);			// 추가3 - CCTV URL
		this.obj.ipaddr		= $.trim(marker.obj.cam_hostport);	// 추가4 - CAM IPADDRESS
	}
}

var isDraw = false;
var startX;
var startY;
var endX;
var endY;
var	mst_canvas = null;
var ctx_src = null;
var dst_canvas = null;
var ctx_dst = null;
var moc_canvas = null;
var ctx_moc = null;
var	cap_image = null;

var	g_currAcctSeq = null;
var	g_currRecogMarker = null;
var	geocoderCctv = null;

//
function initRecogCar(acct_dsr_seq, curr_marker) {
	g_currAcctSeq		= acct_dsr_seq;
	g_currRecogMarker	= curr_marker;
}
function initCanvas(pop) {
	mst_canvas = $(pop).find('#mst_canvas').get(0);
	ctx_src = mst_canvas.getContext('2d');

	dst_canvas = $(pop).find('#dst_canvas').get(0);
	ctx_dst = dst_canvas.getContext('2d');

	moc_canvas = $(pop).find('#moc_canvas').get(0);
	ctx_moc = moc_canvas.getContext('2d');
	
	startX	= 0;
	startY	= 0;
	endX	= 0;
	endY	= 0;

	$(mst_canvas).mousedown(function(event) {
		//ctx_src.drawImage(cap_image, 0, 0, mst_canvas.width, mst_canvas.height);
		isDraw = true;
		var eventX=event.offsetX;
		var eventY=event.offsetY;

		startX = eventX;
		startY = eventY;
	});

	$(mst_canvas).mousemove(function(event) {
		if (isDraw == false) {
			return;
		}
		var eventX = event.offsetX;
		var eventY = event.offsetY;

		ctx_src.drawImage(cap_image, 0, 0, mst_canvas.width, mst_canvas.height);
		endX = eventX;
		endY = eventY;
		drawRect(startX, startY, endX, endY);
	});
	  
	$(mst_canvas).mouseup(function(event) {
		isDraw = false;
		var eventX = event.offsetX;
		var eventY = event.offsetY;

		endX = eventX;
		endY = eventY;
		imgCopy(startX, startY, endX, endY);
	});
}

function getRecogWidth() {
	if (startX == endX) 
		return 0;
	return Math.abs(endX - startX);
}
function getRecogHeight() {
	if (startY == endY) 
		return 0;
	return Math.abs(endY - startY);
}

function drawRect(sX, sY, eX, eY) {
	ctx_src.lineWidth = 2;
	ctx_src.strokeStyle = "rgb(255, 0, 0)";
	ctx_src.strokeRect(sX, sY, eX-sX, eY-sY); // 사각형 그리기
}

function imgCopy(sX, sY, eX, eY) {
	var coordX;
	var coordY;
	var width;
	var height;

	if(sX > eX) {
		coordX = sX;
		width = eX - sX;
	} else {
		coordX = eX;
		width = sX - eX;
	}
	
	if(sY > eY) {
		coordY = sY;
		height = eY - sY;
	} else {
		coordY = eY;
		height = sY - eY;
	}
	ctx_dst.clearRect(0, 0, dst_canvas.width, dst_canvas.height);

	dst_canvas.width	= Math.abs(width) * 2;
	dst_canvas.height	= Math.abs(height) * 2;

//	var imgData = ctx_src.getImageData(coordX, coordY, width, height );
//	ctx_dst.putImageData(imgData,0,0);
	ctx_dst.drawImage(mst_canvas, coordX, coordY, width, height, 0, 0, dst_canvas.width, dst_canvas.height);
	
	moc_canvas.width	= dst_canvas.width;
	moc_canvas.height	= dst_canvas.height;

	makeMosaic();
}
function copyToDest() {
	imgCopy(0, 0, mst_canvas.width, mst_canvas.height);
}

function drawDetectArea_forMosaic(x1, y1, x2, y2) {
	var	width  = x2 - x1;
	var	height = y2 - y1;
	//var imgData = ctx_dst.getImageData(x1, y1, x2-x1, y2-y1);
	//ctx_moc.putImageData(imgData, x1, y1, x1, y1, x2-x1, y2-y1);
	ctx_moc.drawImage(dst_canvas, x1, y1, width, height, x1, y1, width, height);
}
function makeMosaic() {
	var step = 10;
	var	width = dst_canvas.width;
	var	height = dst_canvas.height;
	var colorData = ctx_dst.getImageData(0, 0, width, height).data;
	var center = step / 2;

	var getColorAt = function(x, y) {
		var index = ((width * y) + x) * 4;
		return "rgb(" + colorData[index] + ", " + colorData[index + 1] + ", " + colorData[index + 2] + ")";
	};

	var defaultRect = function(x, y, clr) {
		//	ctx_src.strokeStyle = clr;
		//	ctx_src.strokeRect(x, y, 10, 10); // 사각형 그리기
		ctx_moc.fillStyle = clr;
		ctx_moc.fillRect(x, y, 10, 10); // 사각형 그리기
	};

	var x = 0, y = 0;
	var color;

	for (x = 0; x < width; x += step) {
		for (y = 0; y < height; y += step) {
			color = getColorAt(x + center, y + center);

			defaultRect(x, y, color);
		}
	}
}

//
//0. SHOW 영역선택 팝업 - { x, y, cctv_key, cctv_name, }
//1. http - CCTV 비디오캡쳐 = http://210.103.55.49/awd/capture - { rtsp_url: cctv_url }
//{
//	 url: "/capture/2015/01/26/C77B2DC2FC8FC0A8640F9582358F1538.jpg",
//	 retMsg: "캡쳐 성공",
//	 retCode: 200
//}
//2. 캡쳐결과 이미지를 draw canvas 
//3. 차량인식 영역선택 및 선택버튼 클릭
//4. http - 차량번호인식   = http://210.103.55.49/awd/recognize - { encoded: base64_img }
// response ==> {"retMsg":"OK","right":944,"car_no":"서울33사5807","retCode":200,"left":662,"top":707,"bottom":773}
//5. 장애차량 상세정보 팝업
//6. 캡쳐정보 및 영역선택 정보 표시
//7. [검색] 차주 차량정보 조회
//8.        기존 증거사진 조회 및 출력
//9. [저장] 증거사진 데이터 저장
//10.[단속] 단속처리
//11.[메시지] SMS전송 처리
//

var	g_bRecogWinPopup = false;

// CCTV 위치를 리버스지오코딩
function cctvCoordToAddr(bWinPop) {
	g_bRecogWinPopup = bWinPop;
	
	if (g_currRecogMarker.obj.eaddr != '') {
		doAction_cctvCapture();
		return;
	}

	var	coord = g_currRecogMarker.coord;
	if (geocoderCctv == null) {
		geocoderCctv = new olleh.maps.Geocoder(const_map_api_key);
	}
	geocoderCctv.geocode({ 
			type: 1, 
			newAddr: 0, 
			isJibun : 1,
			addrcdtype: 1,
			x: coord.x,
			y: coord.y
		}, 
		"reversegeocode_cctvCoordCallback"
	);
}

// 리버스 지오코딩 결과 콜백 함수
function reversegeocode_cctvCoordCallback(data) {
	var geoResult = geocoderCctv.parseGeocode(data);
	if (geoResult['count'] != '0'){
		var infoArr = geoResult['infoarr']; 
		if (infoArr.length > 0) {
			g_currRecogMarker.obj.eaddr = infoArr[0].address;
		}
	}
	doAction_cctvCapture();
}

//CCTV 이미지캡쳐
function doAction_cctvCapture() {
	if (g_bRecogWinPopup == true) {
		return;
	}

	var	cctv_url = g_currRecogMarker.obj.url;
	var url = '/ons/awd/capture.do';
	var params = { cap_url: cctv_url };
	
	net_ajax(url, params, function (data) {
		if (data.retCode == const_ret_ok) {
			var	img_url = '/ons/awd/capimage.do?img_uri=' + encodeURIComponent(data.img_uri);
			cmLog('doAction_cctvCapture: OK- URL:' + img_url);
			cap_image = new Image();
			cap_image.onload = function() {
				ctx_src.drawImage(cap_image, 0, 0, mst_canvas.width, mst_canvas.height);
			};
			cap_image.src = img_url;
		} else {
			cmLog('doAction_cctvCapture: ' + data.retMsg);
		}
	}, onNetCallbackEmptyError);
}
//차량번호 인식
function doAction_carnoRecognize(bWinPopup) {

	var url = '/ons/awd/recognize.do';
	var	img_data = dst_canvas.toDataURL();
	var params = { img_data: img_data };
	var	car_no = '';
	var	srect  = '';
	var	sdate  = '';

	net_ajax(url, params, function (data) {
		if (data.retCode == const_ret_ok) {
			car_no	= data.car_no;
			srect 	= data.left + ',' + data.top + ',' + data.right + ',' + data.bottom;
			sdate 	= data.curr_date;
			drawDetectArea_forMosaic(data.left, data.top, data.right, data.bottom);
		} else {
			sdate 	= data.curr_date;
			//alert(data.retMsg);
			alert('차량번호 인식이 실패되었습니다.');
		}
		recogManage(car_no, srect, sdate, bWinPopup);
	}, onNetCallbackDefaultError);
}

//"car_no":"서울33사5807", "left,top,right,bottom"
function recogManage(car_no, img_rect, cur_date, bWinPopup) {
	var	$pop = null;
	
	if (bWinPopup) {
		$pop = $('.pop-obstcardetail');
		$pop.find('#cam_main').show();
	}
	else {
		$pop = createModalPop('pop-obstcardetail');
	}
	
	enableOwnerCarnoEdit($pop);
	enableSearchBtn($pop);
	disableRecogBtn($pop);
	
	$pop.find('#recog_own_car_no').val(car_no);
	$pop.find('#recog_car_no').val(car_no);
	$pop.find('#recog_img_rect').val(img_rect);
	$pop.find('#recog_curr_dt').val(cur_date);
	
	var cctv_name		= g_currRecogMarker.obj.mtitle;
	var cctv_addr		= g_currRecogMarker.obj.eaddr;
	var img_data1		= mst_canvas.toDataURL();
	var img_data2		= dst_canvas.toDataURL();
	var img_data3		= moc_canvas.toDataURL();
	
	var sCNameHtml 		= '<b>카메라 : ' + cctv_name +'</b>';
	var sCAddrHtml		= '위치 : ' + cctv_addr;
	var sCurrDate		= '촬영시각 : ' + subStr(makeDateTimeHM(cur_date), 16);
	
	$pop.find('#recog_cctv_name').html(sCNameHtml);
	$pop.find('#recog_cctv_addr').html(sCAddrHtml);
	$pop.find('#recog_cctv_currdate').html(sCurrDate);
	
	$pop.find('#recog_img_data1').attr('src', img_data1);
	$pop.find('#recog_img_data2').attr('src', img_data2);
	$pop.find('#recog_img_data3').attr('src', img_data3);
}
// 차주정보 검색
function schRecogCarOwnInfo(pop_thiz) {
	
	var car_no	= pop_thiz.find('#recog_own_car_no').val();
	var dsr_seq	= g_currAcctSeq;
	
	var encog_curr_dt	= pop_thiz.find('#recog_curr_dt').val();
	
	if (car_no == '') {
		alert('차량번호를 입력하세요.');
		return;
	}
	var url = '/ons/recog/list_ajax.do';
	var params = {
		car_no: car_no,
		dsr_seq: dsr_seq,
		curr_dt: encog_curr_dt
	};
	
	net_ajax(url, params, function (data) {
		// Initialize
		var sHtml	= '';
		var own_name	= toStr(data.own_name);
		var tel_no		= toStr(data.tel_no);
		
		pop_thiz.find('.sublist').html('');
		
		pop_thiz.find('#recog_own_name').text(own_name);
		pop_thiz.find('#recog_telno').text(tel_no);
		pop_thiz.find('#recog_sch_dt').text(data.sch_dt);
		
		pop_thiz.find('#crack_stat').val(toInt(data.crack_stat));
		pop_thiz.find('#enforce_timeover_stat').val(toInt(data.enforce_time_over));
		pop_thiz.find('#enforce_stat').val(toInt(data.itemVO.enforce_stat));
		pop_thiz.find('#msg_auto_send').val(toInt(data.msg_auto_send));
		
		var recog_detail	= data.itemVO.items;
		
		for (var i = 0; i < recog_detail.length; i ++) {
			var cctv_name	= recog_detail[i].cctv_name;
			var cctv_addr	= recog_detail[i].poi_addr;
			var	recog_time	= recog_detail[i].recog_time;

			var	url_data1 = getRecogImgUrl(dsr_seq, car_no, recog_time, 1);
			var	url_data2 = getRecogImgUrl(dsr_seq, car_no, recog_time, 2);

			sHtml += '<div class="cam">';
			sHtml += '<div class="camera"><img src="' + url_data1 +'" width="125px" height="97px" /></div>';
			sHtml += '<div class="camera-info">';
			sHtml += '<div title="' + cctv_name + '"><b>카메라 : ' + cctv_name.substring(0, 20) + '</b></div><br />';
			sHtml += '<div title="' + cctv_addr + '">위치 : ' + cctv_addr +'</div><br />';
			sHtml += '촬영시각 :' + makeDateTimeHM(recog_time);
			sHtml += '</div>';
			sHtml += '<div class="mos">';
			sHtml += '<div class="carimg"><img src="' + url_data2 +'" width="105px" height="62px" /></div>';
		//	sHtml += '<div class="bg06btn">모자이크</div>';
			sHtml += '</div>';
			sHtml += '</div>';
		}
			
		pop_thiz.find('.sublist').html(sHtml);
			
		disableRecogBtn(pop_thiz);
		if (data.retCode != const_ret_ok) {
			alert(data.retMsg);
		}
		
		enableSaveBtn(pop_thiz);
		
	}, onNetCallbackDefaultError);

	
}

//적용
function onClick_setApplyData(pop_thiz) {
	var bApply	= confirm('적용하시겠습니까?');
	
	if (bApply) {
		var crack_stat				= pop_thiz.find('#crack_stat').val();
		var enforce_timeover_stat	= pop_thiz.find('#enforce_timeover_stat').val();
		var enforce_stat			= pop_thiz.find('#enforce_stat').val();
		var msg_auto_send			= pop_thiz.find('#msg_auto_send').val();
		
		var car_no			= pop_thiz.find('#recog_own_car_no').val();
		var tel_no			= pop_thiz.find('#recog_telno').text();
		var curr_dt			= pop_thiz.find('#recog_curr_dt').val();
		var recog_car_no	= pop_thiz.find('#recog_car_no').val();
		var recog_img_rect	= pop_thiz.find('#recog_img_rect').val();
		var img_data1		= mst_canvas.toDataURL();
		var img_data2		= dst_canvas.toDataURL();
		var img_data3		= moc_canvas.toDataURL();
		
		var cctv_coord	= g_currRecogMarker.coord;
		var cctv_key	= g_currRecogMarker.obj.mkey;
		var cctv_name	= g_currRecogMarker.obj.mtitle;
		var cctv_type	= g_currRecogMarker.obj.etype;
		var cctv_addr	= g_currRecogMarker.obj.eaddr;
		
		var dsr_seq		= g_currAcctSeq;
		//var flag		= 1;
		
		var url = '/ons/recog/reg_action.do';
		var params = {
			dsr_seq: dsr_seq,
			own_car_no: car_no,
			recog_time: curr_dt,
			cctv_key: cctv_key,
			recog_type: cctv_type,
			cctv_name: cctv_name,
			poi_x: cctv_coord.x,
			poi_y: cctv_coord.y,
			poi_addr: cctv_addr,
			recog_img_data1: img_data1,
			recog_img_data2: img_data2,
			recog_img_data3: img_data3,
			recog_car_no: recog_car_no,
			recog_img_rect: recog_img_rect,
			own_tel_no: tel_no
		};
		
		net_ajax(url, params, function (data) {
			if (data.retCode == const_ret_ok) {
				disableOwnerCarnoEdit(pop_thiz);
				
				disableSearchBtn(pop_thiz);
				disableSaveBtn(pop_thiz);
				
				enableRecogBtn(pop_thiz, crack_stat, enforce_timeover_stat, enforce_stat);
				
				//if (tel_no == '') {
				//	disableSendMsg(pop_thiz);
					
				//} else {
				
				if (tel_no.length > 0) {
					if (msg_auto_send > 0) {
						var conf	= confirm('메세지 자동전송 기능이 활성화 되어 있습니다.\n메세지를 전송하시겠습니까?');
						
						if (conf) {
							onClick_setRecogUpdate(pop_thiz, 3);
						}
					}
					return;
				}
				alert('적용되었습니다.');
				
			} else {
				alert(data.retMsg);
			}
		}, onNetCallbackDefaultError);
	}
	
}

// 단속처리/메시지전송
function onClick_setRecogUpdate(pop_thiz, flag) {
	var car_no			= pop_thiz.find('#recog_own_car_no').val();
	var tel_no			= pop_thiz.find('#recog_telno').text();
	var curr_dt			= pop_thiz.find('#recog_curr_dt').val();
	var recog_car_no	= pop_thiz.find('#recog_car_no').val();
	var recog_img_rect	= pop_thiz.find('#recog_img_rect').val();
	var img_data1		= mst_canvas.toDataURL();
	var img_data2		= dst_canvas.toDataURL();
	var img_data3		= moc_canvas.toDataURL();
	
	var cctv_coord	= g_currRecogMarker.coord;
	var cctv_key	= g_currRecogMarker.obj.mkey;
	var cctv_name	= g_currRecogMarker.obj.mtitle;
	var cctv_type	= g_currRecogMarker.obj.etype;
	var cctv_addr	= g_currRecogMarker.obj.eaddr;
	
	var dsr_seq		= g_currAcctSeq;
	
	// 2: 단속처리, 3: 메세지전송 
	
	var url = '/ons/recog/update_action.do';
	var params = {
		own_car_no: car_no,
		own_tel_no: tel_no,
		dsr_seq: dsr_seq,
		cctv_key: cctv_key,
		recog_type: cctv_type,
		cctv_name: cctv_name,
		poi_x: cctv_coord.x,
		poi_y: cctv_coord.y,
		poi_addr: cctv_addr,
		recog_img_data1: img_data1,
		recog_img_data2: img_data2,
		recog_img_data3: img_data3,
		recog_car_no: recog_car_no,
		recog_img_rect: recog_img_rect,
		recog_time: curr_dt,
		recog_flag: flag
	};
	
	net_ajax(url, params, function (data) {
		if (data.retCode == const_ret_ok) {
			if (flag == 2) {
				disableObstCar(pop_thiz);
				alert('단속처리 되었습니다.');
				
			} else if (flag == 3) {
				alert('메세지가 전송되었습니다.');
			}
		} else {
			alert(data.retMsg);
		}
	}, onNetCallbackDefaultError);
}

function showRecogMosaic(parent) {
	$img_parent = $(parent);
	if ($img_parent.find('#recog_img_data2').css('display') != 'none') {
		$img_parent.find('#recog_img_data2').hide();
		$img_parent.find('#recog_img_data3').show();
	}
	else {
		$img_parent.find('#recog_img_data2').show();
		$img_parent.find('#recog_img_data3').hide();
	}
}
//캡쳐이미지 URL
function getRecogImgUrl(dsr_seq, car_no, recog_time, img_data) {
	var url = '/ons/recog/image.do';
	
	url += '?dsr_seq=' + dsr_seq;
	url += '&car_no=' + encodeURIComponent(car_no);
	url += '&recog_time=' + recog_time;
	url += '&img_data=' + img_data;
	
	return url;
}

function enableRecogBtn(pop_thiz, crack_stat, timeover_stat, enforce_stat) {
//	cmLog(crack_stat + ', ' + timeover_stat + ' , ' + enforce_stat);
//	if (crack_stat > 0 && timeover_stat > 0 && enforce_stat <= 0) {
//		pop_thiz.find('#btn_recog_obstcar').css('background', '');
//		pop_thiz.find('#btn_recog_obstcar').css('cursor', '');
//		pop_thiz.find('#btn_recog_obstcar').css('pointer-events', '');
//	} else {
//		pop_thiz.find('#btn_recog_obstcar').css('background', 'url("./images/btn_bg05_on.png")');
//		pop_thiz.find('#btn_recog_obstcar').css('cursor', 'default');
//		pop_thiz.find('#btn_recog_obstcar').css('pointer-events', 'none');
//	}
//	
//	pop_thiz.find('#btn_recog_sendmsg').css('background', '');
//	pop_thiz.find('#btn_recog_sendmsg').css('cursor', '');
//	pop_thiz.find('#btn_recog_sendmsg').css('pointer-events', '');
	
	if (enforce_stat <= 0) {
		pop_thiz.find('#btn_recog_obstcar').css('background', '');
		pop_thiz.find('#btn_recog_obstcar').css('cursor', '');
		pop_thiz.find('#btn_recog_obstcar').css('pointer-events', '');
	} else {
		pop_thiz.find('#btn_recog_obstcar').css('background', 'url("./images/btn_bg05_on.png")');
		pop_thiz.find('#btn_recog_obstcar').css('cursor', 'default');
		pop_thiz.find('#btn_recog_obstcar').css('pointer-events', 'none');
	}
	pop_thiz.find('#btn_recog_sendmsg').css('background', '');
	pop_thiz.find('#btn_recog_sendmsg').css('cursor', '');
	pop_thiz.find('#btn_recog_sendmsg').css('pointer-events', '');
}


function disableRecogBtn(pop_thiz) {
	disableSaveBtn(pop_thiz);
	disableObstCar(pop_thiz);
	disableSendMsg(pop_thiz);
}

function disableSearchBtn(pop_thiz) {
	pop_thiz.find('#btn_recog_search').css('background', "url('./images/btn_bg05_on.png')");
	pop_thiz.find('#btn_recog_search').css('cursor', 'default');
	pop_thiz.find('#btn_recog_search').css('pointer-events', 'none');
}

function disableSendMsg(pop_thiz) {
	pop_thiz.find('#btn_recog_sendmsg').css('background', "url('./images/btn_bg05_on.png')");
	pop_thiz.find('#btn_recog_sendmsg').css('cursor', 'default');
	pop_thiz.find('#btn_recog_sendmsg').css('pointer-events', 'none');
}

function disableObstCar(pop_thiz) {
	pop_thiz.find('#btn_recog_obstcar').css('background', "url('./images/btn_bg05_on.png')");
	pop_thiz.find('#btn_recog_obstcar').css('cursor', 'default');
	pop_thiz.find('#btn_recog_obstcar').css('pointer-events', 'none');
}

function disableSaveBtn(pop_thiz) {
	pop_thiz.find('#btn_recog_save').css('background', "url('./images/btn_bg05_on.png')");
	pop_thiz.find('#btn_recog_save').css('cursor', 'default');
	pop_thiz.find('#btn_recog_save').css('pointer-events', 'none');
}
function disableOwnerCarnoEdit(pop_thiz) {
	pop_thiz.find('#recog_own_car_no').attr('readonly', true);
	pop_thiz.find('#recog_own_car_no').css('background', 'gray');
}

//
function enableSearchBtn(pop_thiz) {
	pop_thiz.find('#btn_recog_search').css('background', '');
	pop_thiz.find('#btn_recog_search').css('cursor', 'default');
	pop_thiz.find('#btn_recog_search').css('pointer-events', '');
}
function enableSaveBtn(pop_thiz) {
	pop_thiz.find('#btn_recog_save').css('background', '');
	pop_thiz.find('#btn_recog_save').css('cursor', 'default');
	pop_thiz.find('#btn_recog_save').css('pointer-events', '');
}
function enableObstcarBtn(pop_thiz) {
	pop_thiz.find('#btn_recog_obstcar').css('background', '');
	pop_thiz.find('#btn_recog_obstcar').css('cursor', 'default');
	pop_thiz.find('#btn_recog_obstcar').css('pointer-events', '');
}
function enableOwnerCarnoEdit(pop_thiz) {
	pop_thiz.find('#recog_own_car_no').attr('readonly', false);
	pop_thiz.find('#recog_own_car_no').css('background', 'white');
}
