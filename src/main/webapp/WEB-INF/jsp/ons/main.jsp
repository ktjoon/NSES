<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set value="${userConfigVO }" var="userConfigData"></c:set>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<link rel="stylesheet" type="text/css" href="./css/main.css">
<script src="./js/libs/jquery.1.10.js"></script>
<script src="./js/libs/jquery-ui-1.11.2.js"></script>
<script src="./js/libs/PluginDetect_VLC.js"></script>
<script src="http://api.ktgis.com:10080/v3/olleh/mapAPI.js?key=T2xsZWhNYXBFWDAwMjE6dm5kOGlkMTY4ag==&module=Map,Geocoder,Directions"></script>
<script src="http://api.ktgis.com:10080/v3/olleh/mapsSpatialAPI.js?key=T2xsZWhNYXBFWDAwMjE6dm5kOGlkMTY4ag=="></script>
<script src="./js/utils/com_codes.js"></script>
<script src="./js/utils/com_network.js"></script>
<script src="./js/utils/com_utils.js"></script>

<script src="./js/nses_map.js"></script>
<script src="./js/nses_shape.js"></script>

<script src="./js/avoid_data.js"></script>
<script src="./js/acct_list.js"></script>
<script src="./js/objs_list.js"></script>
<script src="./js/route_list.js"></script>
<script src="./js/recog_car.js"></script>
<script src="./js/cam_ctrl.js"></script>
<script src="./js/vmx_player.js"></script>
<script src="./js/biz_codeinfo.js"></script>

<script type="text/javascript">
	var	$pop_setavoid;
	var $pop_delavoid;
	var $pop_cctv;
	var $pop_obstmanage;
	var $pop_disdetail;
	var $pop_obstcardetail;
	var $pop_mosaic;
	var $pop_sendcourse;
	var $pop_msg;
	
	// add by ktjoon 2016-01
	var $pop_sms;
	var isSmsOpen = false;

	//사용자설정정보(탐색조건 및 CCTV매트릭스설정)
	var	g_userCfg = {
		sch_type: '<c:out value="${userConfigVO.sch_type }"></c:out>',
		cctv_mat: '<c:out value="${userConfigVO.cctv_mat }"></c:out>', 
		save_sec: '<c:out value="${userConfigVO.save_sec }"></c:out>'
	};

	var smap = new NsesMap();
	var	acctList = new AcctList();

	//회피지점 설정 버튼 on/off 설정 변수. 초깃값은 off
	var g_isAvoid = false;
	var	g_lastClickCoord = null;
	var	g_lastClickMarker = null;
	var	g_lastShowType = 0;
	var	g_vlcVersion = null;

	//
	var	g_isFirstClick = false;			// 반경/투망감시 첫번째 클릭여부
	var	g_isRadiusMode = false;			// 반경표시 on/off 플래그
	var	g_isRectMode = false;			// 투망감시 on/off 플래그
	var	oline = null;
	var	mcircle = null;
	var	mrect = null;
	
	//
	var	g_tmMovingWcar = null;

	//
	//올레맵 제외한 이벤트 설정들
	$(function() {
		
		//상단 검색조건을 새로 선택시 발생 이벤트
		$("#route_search_type ul li").mousedown(function() {
			$("#route_search_type span").text($(this).text());
			
			var sSchType	= $(this).val();
			var url = '/ons/user/update_action.do';
			var params = {sch_type : sSchType};
			
			g_userCfg.sch_type	= sSchType;
			
			net_ajax(url, params, function (data) {
				cmLog('user-config/updata_action(sch_type: ' + g_userCfg.sch_type + ')');
			}, onNetCallbackEmptyError);
			
			routeSearch(false);
		});

		$("#search-box").keyup(function(e) {
			if (e.keyCode != 13) {
				return;
			}

			$("#content .search-div ul").css("height", "auto")
										.css("opacity", "1")
										.css("padding", "5px 10px 0 10px");
			$("#content .search-div ul li").css("opacity", "1")
										   .css("height", "auto");

			var sSchText = $(this).val();
			if (sSchText == '') {
				return;
			}

			searchObjectData(sSchText);
		});

		$("#bearing").click(function() {
			g_isFirstClick = false;
			g_isRadiusMode = !g_isRadiusMode;

			if (g_isRadiusMode) {
				$(this).attr("src", "./images/btn_bearing_on.png");
				$("#layer_container").css("cursor", "crosshair");
			} else {
				mcircle.clear();
				
				$(this).attr("src", "./images/btn_bearing.png");
				$("#layer_container").css("cursor", "default");
			}
		});

		//회피지점 설정 버튼 클릭 시
		$("#avoidBtn").click(function() {
			g_isAvoid = !g_isAvoid;

			checkAvoid(g_isAvoid);
		});

		//초기화 버튼 클릭 시
		$("#clearBtn").click(function() {
			//createPop($pop_clearavoid);
			createModalPop('pop-clearavoid');
		});

		//투망감시 버튼 클릭 시
		$("#castBtn").click(function() {
			g_isFirstClick 	= false;
			g_isRectMode	= !g_isRectMode;

			if (g_isRectMode) {
				$("#layer_container").css("cursor", "crosshair");
			} else {
				oline.clear();
				mrect.clear();

				$("#layer_container").css("cursor", "default");
			}
		});

		//장애제거 버튼 클릭 시
		$("#terminateBtn").click(function() {
			if(smap.getAcctCurSel() == '') {
				alert("재난정보를 선택해주세요.");
				return;
			}
			
			var dsr_seq	= smap.getAcctCurSel();
			var url = '/ons/terminate/list_ajax.do';
			var params = {dsr_seq : dsr_seq};
			
			net_ajax(url, params, function (data) {
				if (data.retCode == const_ret_ok) {
						
					var recog_detail	= data.itemVO.items;
					var sHtml	= '';
					
					for (var i = 0; i < recog_detail.length; i ++) {
						sHtml += '<tr>';
						sHtml += '<td>';
						sHtml += i+1;
						sHtml += '</td>';
						sHtml += '<td>';
						sHtml += recog_detail[i].ownCarNo;
						sHtml += '</td>';
						sHtml += '<td>';
						sHtml += recog_detail[i].sregDt;
						sHtml += '</td>';
						sHtml += '<td>';
						sHtml += recog_detail[i].cctvName;
						sHtml += '</td>';
						sHtml += '<td>';
						sHtml += recog_detail[i].terminateYn;
						sHtml += '</td>';
						sHtml += '<td class="last"><span class="detail" style="border: 1px solid #9D9EA0; padding: 3px 12px; cursor: pointer; border-radius: 5px;" ';
						sHtml += 'onclick="onClick_obstmanageDetail(\'' + recog_detail[i].ownCarNo + '\');">상세</span></td>';
					}
					$('#obstmanage_title').siblings().remove();	
					$('.pop-obstmanage table').append(sHtml);
				} else {
					alert(data.retMsg);
				}
			}, onNetCallbackEmptyError); 
			
			$pop = createPop($pop_obstmanage);
		});

		//장애차량 상세정보 팝업 띄우기.
		$(".pop-obstmanage .detail").click(function() {
			createPop($pop_obstcardetail);

			//장애차량 상세팝업 안의 버튼들에 대한 이벤트 추가
			$(".cam .mos .bg06btn").click(function() {
				createPop($pop_mosaic);
			});
			$(".car-btns .sendmsg").click(function() {
				createPop($pop_msg);
			});
		});

		//모자이크 팝업 띄우기 이벤트
		$(".cam .mos .bg06btn").click(function() {
			createPop($pop_mosaic);
		});

		//장애차량 상세정보의 메시지 버튼 이벤트
		$(".car-btns .sendmsg").click(function() {
			createPop($pop_msg);
		});
		

		//btns 클래스 내부의 img 태그에 on, off 효과를 준다.
		$(".btns img").mousedown(function() {
			$(this).attr("src",$(this).attr("src").replace(/\_off./g,"_on."));
		});
		$(".btns img").mouseup(function() {
			$(this).attr("src",$(this).attr("src").replace(/\_on./g,"_off."));
		});
		$(".btns img").mouseout(function() {
			$(this).attr("src",$(this).attr("src").replace(/\_on./g,"_off."));
		});

		//이미지에 아래 클래스 추가하여 on/off 효과를 준다.
		$(".ovbtn").mouseover(function(){
			$(this).attr("src",$(this).attr("src").replace(/\_off./g,"_on."));
		});
		$(".ovbtn").mouseout(function(){
			$(this).attr("src",$(this).attr("src").replace(/\_on./g,"_off."));
		});

		// 팝업레이어 div 에 lp 클래스 추가 하여 드래그 이벤트 효과를 추가한다. (jqeury UI 사용)
		$(".lp").draggable({
			containment: "window"
		});

		//팝업창들을 클론하여 생성하기 위해 
		//팝업창 종류별로 각각 변수에 저장한다.
		$pop_setavoid = $(".pop-setavoid").clone();	//회피지점 설정 팝업
		$pop_delavoid = $(".pop-delavoid").clone();	//회피지점 삭제 팝업
		$pop_clearavoid = $(".pop-clearavoid").clone();	//회피지점 초기화 팝업
		$pop_cctv = $(".pop-cctv").clone();	//cctv 팝업
		$pop_disdetail = $(".pop-disdetail").clone();	//재난정보 상세 팝업
		$pop_obstmanage = $(".pop-obstmanage").clone(); //장애관리 팝업
		$pop_obstcardetail = $(".pop-obstcardetail").clone(); //장애차량 상세정보 팝업
		$pop_mosaic = $(".pop-mosaic").clone(); //모자이크 처리 팝업
		$pop_sendcourse = $(".pop-sendcourse").clone(); //경로전송 팝업
		$pop_msg = $(".pop-msg").clone(); //경로전송 팝업
		//add by ktjoon 2016-01
		$pop_sms = $(".pop-sms").clone(); //SMS 수신 팝업
	});

//팝업레이어를 맵 가운데에 생성한다. ( 예 - createPop($pop_cctv) )
function createPop_OLD($pop) {
	$newpop = $pop.clone();
	$newpop.css("position", "absolute")
			.css("left", ($(window).width()-245-$newpop.width()) / 2 )
			.css("top", ($(window).height()-70-$newpop.height()) / 2 );
	$newpop.show();
	$("body").append($newpop);
	$(".lp").draggable({
		containment: "window"
	});
	return $newpop;
}
function createPop($pop) {
	$newpop = $pop.clone();
	$("body").append($newpop);
	$newpop.fadeIn();
	$newpop.css("margin-left", -$newpop.outerWidth() / 2 )
	       .css("margin-top", -$newpop.outerHeight() / 2 );

	var x1 = 0;
	var y1 = -700;
	$(".lp").draggable({
		containment:[x1,y1,$(window).width(),,$(window).height()]
		
	});
	//containment: "window"
	return $newpop;
}
function createModalPop(name) {
	var $popup = $("."+name).clone();
	var $overlay = $(".overlay");

	$popup.appendTo("body");
	$overlay.height($(document).height()).show();
	$popup.fadeIn();
	// 화면의 중앙에 레이어를 띄운다.
	$popup.css('margin-top', '-'+($popup.outerHeight()/2)+'px');
	$popup.css('margin-left', '-'+($popup.outerWidth()/2)+'px');

	$(document).keyup(function(e){
		if(e.keyCode == 27) {
			$popup.fadeOut();
			e.preventDefault();
			$overlay.fadeOut();
			$popup.remove();
		}
	});
	return $popup;
}
function createModalPop2(name) {
	var $popup = $("."+name).clone();
	var $overlay = $('<div class="overlay blk_' + name + '" style="display: none; height: 2714px;"></div>');

	$popup.appendTo("body");
	$overlay.appendTo("body");

	$overlay.height($(document).height()).show();
	$popup.fadeIn();
	// 화면의 중앙에 레이어를 띄운다.
	$popup.css('margin-top', '-'+($popup.outerHeight()/2)+'px');
	$popup.css('margin-left', '-'+($popup.outerWidth()/2)+'px');

	$(document).keyup(function(e){
		if(e.keyCode == 27) {
			$popup.fadeOut();
			e.preventDefault();
			$overlay.fadeOut();
			$popup.remove();
		}
	});
	return $popup;
}

//클래스 명이 popName인 팝업레이어를 제거한다. ( 예 - removePop($(this)) )
function removePop(pop) {
	$pop = $(pop);
	$pop.fadeOut();
	$(".overlay").fadeOut();
	$pop.remove();
}
function removePop2(pop) {
	$pop = $(pop);
	$pop.fadeOut();
	var	cls_name = '.blk_' + $pop.attr('class');
	$(cls_name).fadeOut();
	$(cls_name).remove();
	$pop.remove();
}

// CCTV 팝업 오픈
// 1: 연수구청, 2: 화재감시, 3: 차량캠
function createPopCCTV(ukey, title, url, ctype, ipaddr) {
	if (g_vlcVersion == null) {
		showPopInstall();
	}
	$pop = createPop($pop_cctv);
	
	var	cam_addr = $.trim(ipaddr);
	
	//
	cmLog('createPopCCTV(ukey:' + ukey + ', url:' + url);
	
	var	player 	= new VmxPlayer(ukey, url);

	// makeEmbedPlayer(sId, sURL)
	$pop.find('#car_no').val(title);;
	$pop.find('#cctv_key').val(ukey);
	$pop.find('#cctv_title').text(title);
	$pop.find('#cctv_title').attr('title', title);
	//$pop.find('#video_cctv').html(makeEmbedPlayer(ukey, url));
	$pop.find('#video_cctv').html(player.makeHtml(false));
	

	//2x button functions....
	$pop.find(".inl-btn-close").css("width","45px");
	$pop.find(".inl-btn-window-x2").click(function() {
		var width = $pop.width();
		var w = 349*1;
		var h = 356*1;
		var ch = 315*1;
		var ccw = 320*1;
		var cch = 240*1;
		var tw = 285*1;

		if (width > 360) {
			

			$pop.css("width",w+"px");
			$pop.css("height",h+"px");
			$pop.find(".pop-content").css("height",ch+"px");
			$pop.find(".cctv").css("width",ccw+"px");
			$pop.find(".cctv").css("height",cch+"px");
			$pop.find(".title-wrap").css("width",tw+"px");

			$(this).find("img").attr("src","./images/btn_window_x2.png");
		}else {
			w*=2;
			h=590;
			ch=550;
			ccw*=2;
			ccw += 30;
			cch*=2;
			tw*=2;

			$pop.css("width",w+"px");
			$pop.css("height",h+"px");
			$pop.find(".pop-content").css("height",ch+"px");
			$pop.find(".cctv").css("width",ccw+"px");
			$pop.find(".cctv").css("height",cch+"px");
			$pop.find(".title-wrap").css("width",tw+"px");

			$(this).find("img").attr("src","./images/btn_window_x2_on.png");
		}
	});

	$pop.find(".inl-btn-close2").hover(function() {
		$(this).find("img").attr("src","./images/btn_close2_on.png");

	},function() {
		$(this).find("img").attr("src","./images/btn_close2.png");
	});
	
	


	if (ctype == '1') {
		// 구청CCTV
		$pop.find('#cctv_recog').show();
		$pop.find('#cctv_spread').show();
		disableCctvCtrl($pop);
		hideCctvZoomCtrl($pop);
	}
	else if (ctype == '3') {
		// 차량캠
		$pop.find('#cctv_record').show();
		$pop.find('#cctv_recog').show();
		$pop.find('#cctv_spread').show();
		$pop.find('#cctv_addr').val(cam_addr);
	}
	else if (ctype == '2') {
		// 화재감시
		$pop.find('#cctv_recog').hide();
		$pop.find('#cctv_spread').hide();
		hideCctvCtrl($pop);
		hideCctvZoomCtrl($pop);
	}
	
	player.playWait(1000);
}

function closePopCCTV(pop) {
	$pop = $(pop);
	var	ukey = $pop.find('#cctv_key').val();

	vmxStop(ukey);
	$pop.find('#video_cctv').html('');
	removePop(pop);
}

function showPopCCTVMatrix() {
	window.open("./pop_matrix.do", "pop-matrix", "width=600, height=576");
}
function showPopInstall() {
	window.open("./install/actx_install.do", "pop-install", "width=520, height=200");
}

function makeCctvURL(item) {
	var	url = '';
 	if (item.cctv_type == '1')
 		url = $.trim(item.cam_url);
	else if (item.cctv_type == '2')
 		url = $.trim(item.cam_url);

 	if (url == '')
 		url = 'http://' + window.location.host + '/capture/noaddr.jpg';
 	
 	// 'rtsp://root:pass@' + item.cctv_hostport + '/mpeg4/media.amp';

	return url;
}
function makeCarCctvURL(item) {
	var	url 		= $.trim(item.cam_url);
	var	cam_yn		= $.trim(item.cam_yn);

 	if (cam_yn == 'Y') {
 	 	if (url == '') {
 	 		url = 'http://' + window.location.host + '/capture/noaddr.jpg';
 	 	}
 	}

	//var	rtsp_url = 'rtsp://192.168.25.201/StdCh2';
	//if (winfo.url.indexOf('rtsp://') == 0) {
	//	return rtsp_url;
	//}
	return url;
}

function getCurrAcctSeq() {
	return smap.getAcctCurSel();
}

/**
 * WcarInfo : 출동차량 정보
 */
//
function WcarInfo(ukey, item) {
	this.mkey			= ukey;							// 마커 유일키(DB-KEY) - item.car_id
	this.mtitle			= item.car_no;					// 타이틀(차량번호)
	this.mdate			= $.trim(item.input_datetime);	// 출동시각
	this.car_id			= item.car_id;					// 차량아이
	this.car_no			= item.car_no;					// 차량번호
	this.car_type_name	= item.car_type_name;			// 차종
	this.car_stat_name	= item.car_stat_name;			// 상태명
	this.car_stat_cd	= $.trim(item.car_stat_cd);		// 상태코드(출동/귀소) : 0930005 - 귀소차량
	this.cam_yn			= $.trim(item.cam_yn);			// 차량카메라 존재여부(Y/N)
	this.cam_hostport	= $.trim(item.cam_hostport);	// 차량카메라 주소및포트
	this.url			= makeCarCctvURL(item);			// 접속주소
}


// CCTV 리스트 마커 표시
function showListAllMcctv() {
	
	function onAjaxResult(data) {
		if (data.retCode == const_ret_ok) {
			smap.clearMcctv();
			for (var i = 0; i < data.items.length; i ++) {
				var	item = data.items[i];
				var	cctv_url = makeCctvURL(item);
				var	dataobj = new MarkerInfo(item.cctv_guid, item.cctv_name, item.reg_dt, item.cctv_type, '', cctv_url, '');
				smap.makeMcctv(item.cctv_lat, item.cctv_lng, dataobj);
			}
		} else {
			alert(data.retMsg);
		}
	}

	var url = '/ons/cctv/list_ajax.do';
	var params = {  };

	net_ajax(url, params, onAjaxResult, onNetCallbackEmptyError);
}
// 재난지역 주변 CCTV 표출
function showListRectMcctv(x1, y1, x2, y2) {
	
	function onAjaxResult(data) {
		if (data.retCode == const_ret_ok) {
			smap.clearMcctv();
			for (var i = 0; i < data.items.length; i ++) {
				var	item = data.items[i];
				var	cctv_url = makeCctvURL(item);
				var	dataobj = new MarkerInfo(item.cctv_guid, item.cctv_name, item.reg_dt, item.cctv_type, '', cctv_url, '');
				smap.makeMcctv(item.cctv_lat, item.cctv_lng, dataobj);
			}
		}
		/* else {
			alert('주변CCTV ' + data.retMsg);
		}*/
	}

	var url = '/ons/cctv/rectlist_ajax.do';
	var params = {
		x1: x1,
		y1: y1,
		x2: x2,
		y2: y2
	};

	net_ajax(url, params, onAjaxResult, onNetCallbackEmptyError);
}

// 재난목록 조회 및 화면출력
function showListAccident() {

	function onAjaxResult(data) {
		var	curr_seq = smap.getAcctCurSel();
		
		if (data.retCode == const_ret_ok) {
			cmLog('current_date: ' + data.curr_date);
			acctList.setOLDItems();
			for (var i = 0; i < data.items.length; i ++) {
				var	item = data.items[i];
				acctList.addItem(item);
			}
			acctList.removeFinishItems(data.curr_date, curr_seq);
			acctList.sort();
		}
		dispListAccident(curr_seq);
	}

	var url = '/ons/accident/list_ajax.do';
	var params = {	};

	net_ajax(url, params, onAjaxResult, onNetCallbackEmptyError);
}

function dispListAccident(cur_seq) {
	var	aryList = acctList.getList();
	
	// 재난선택버튼 disable
	$('.disaster-info *:not(img)').unbind('click');
	$('.article-list').html('');
	for (var i = 0; i < aryList.length; i ++) {
		var	item = aryList[i];
		var	sHtml = '';
		var	click_name = 'cid_' + item.dsr_seq;
		
		sHtml += '<div class="article" id="' + click_name + '">';
		sHtml += '	<div class="article-class">';
		sHtml += item.dsr_knd_name;
		sHtml += '	</div>';
		sHtml += '	<div class="article-location">';
		sHtml += item.point_accr_name;
		sHtml += '	</div>';
		sHtml += '	<div class="article-address">';
		sHtml += $.trim(item.dsr_addr);
		sHtml += '	</div>';
		sHtml += '	<img class="ovbtn" src="./images/icon_check_off.png" style="float:right;" ';
		sHtml += '		onclick="showAccidentDetail(\'' + item.dsr_seq + '\')" />';
		sHtml += '	<input id="select_dsr_seq" type="hidden" value="' + item.dsr_seq + '">';
		if (item.acc_stat == '1') {
			sHtml += '	<div class="over">상황종료</div>';
		}
		sHtml += '</div>';

		$('.article-list').append(sHtml);

		// 재난진행중 이거나 선택된 항목과 같으면 선택가능하도록 이벤트 등록
		if (item.acc_stat == '0' || cur_seq == item.dsr_seq) {
			$('.disaster-info #' + click_name + ' *:not(img)').click(function() {
				$(".disaster-info .article").removeClass("select");
				$(this).parent().addClass("select");
				var	dsr_seq = $.trim($(this).parent().find('#select_dsr_seq').val());
				if (dsr_seq.length > 0) {
					selectAccidentItem(dsr_seq);
				}
			});
			if (cur_seq == item.dsr_seq) {
				$('.disaster-info #' + click_name).addClass("select");
			}
		}
	}
}
//
function routeSearch(msg_yn) {
	var d_seq = smap.getAcctCurSel();
	 
	if (d_seq == '' && msg_yn == true) {
		alert('선택된 재난정보가 없습니다.');
		return;
	}
	
	selectAccidentItem(null);
}

//재난목록에서 재난항목 해제
function clearAccidentItem() {
	cmLog('clearAccidentItem()');

	stopMovingTimer();
	smap.showAcctMarker(null);
	smap.clearWard();
	smap.clearWcar();
	clearRouteData();
	smap.clearMcctv();

	$(".disaster-info .article").removeClass("select");
}
// 재난목록에서 재난항목 선택
function selectAccidentItem(d_seq) {
	cmLog('selectAccidentItem(d_seq:' + d_seq + ')');

	if (g_lastShowType == TYPE_WCAR)
		showMarkerDetail('');
	
	if (d_seq != null) {
		var	cur_seq = smap.getAcctCurSel();

		if (cur_seq != '' && cur_seq == d_seq) {
			if (confirm('선택한 재난지역을 해제하시겠습니까?')) {
				clearAccidentItem();
			}
			return;
		}
	}

	stopMovingTimer();
	smap.clearWard();
	smap.clearWcar();
	clearRouteData();
	smap.clearMcctv();

	var	item;

	if (d_seq == null) {
		// 재탐색인 경우
		d_seq = smap.getAcctCurSel();
		item = acctList.getItem(d_seq);

		showMove_WardsWcars(d_seq, item);
		return;
	}

	item = acctList.getItem(d_seq);

	if (item != null) {
		// 1. 재난위치 POI 표출
		// 2. 출동서소 및 출동차량 조회 및 표출
		// 3. 최적경로 탐색
		// 4. 재난위치 및 출동차량 영역 추출
		// 5. 출동주변 CCTV 조회 및 표출
		if (smap.showAcctMarker(item)) {
			showMove_WardsWcars(d_seq, item);
		}
	}
}
// 출동서소 및 출동차량 조회 및 표출
function showMove_WardsWcars(d_seq, dsr_info) {
	if (dsr_info == null)
		return;

	function onAjaxResult(data) {
		if (data.retCode == const_ret_ok) {
			cmLog('showMove_WardsWcars(dsr_seq: ' + d_seq + ', wards: ' + data.wards.length + ', wcars: ' + data.wcars.length + ')');
			
			var	bounds = new RectCoord();
			
			bounds.startWGS84(dsr_info.gis_y, dsr_info.gis_x);
			bounds.adjustWGS84(50);
			
			//출동서소 표출
			smap.clearWard();
			for (var i = 0; i < data.wards.length; i ++) {
				var	item = data.wards[i];
				var	dataobj = new MarkerInfo(item.ward_id, item.ward_name, '');
				smap.makeWard(item.gis_y, item.gis_x, dataobj);
				bounds.recalcWGS84(item.gis_y, item.gis_x);
			}
			
			clearRouteData();
			//출동차량 표출
			smap.clearWcar();
			for (var i = 0; i < data.wcars.length; i ++) {
				var	item = data.wcars[i];
				var	dataobj = new WcarInfo(item.car_id, item);
				var	gis_x = toDouble(item.gis_x);
				var	gis_y = toDouble(item.gis_y);
				smap.makeWcar(gis_y, gis_x, dataobj);
				
				if (gis_x == 0 || gis_y == 0) {
					continue;
				}

				bounds.recalcWGS84(gis_y, gis_x);

				var	r_info = {
						dsr_seq: d_seq, 
						car_id: item.car_id,
						car_no: item.car_no,
						route_no: '', 
						origin: new olleh.maps.UTMK.valueOf(new olleh.maps.LatLng(gis_y, gis_x)),
						dest: new olleh.maps.UTMK.valueOf(new olleh.maps.LatLng(dsr_info.gis_y, dsr_info.gis_x)),
						result: null, 
						reg_action: false
					};

				addRouteData(r_info);
			}

			searchRouteData(d_seq, g_userCfg.sch_type);
			if (bounds.getCount() > 0) {
				showListRectMcctv(bounds.getStartX(), bounds.getStartY(), bounds.getEndX(), bounds.getEndY());
			}
			if (data.wcars.length > 0) {
				startMovingTimer();
			}
		} else {
			alert(data.retMsg);
		}
	}

	var url = '/ons/accident/wardscars_ajax.do';
	var params = {
		dsr_seq: d_seq
	};

	net_ajax(url, params, onAjaxResult, onNetCallbackEmptyError);
}

function startMovingTimer() {
	g_tmMovingWcar = setTimeout('actionMovingTimer()', 5000);
}
function actionMovingTimer() {
	var	d_seq = smap.getAcctCurSel();
	if (d_seq != '') {
		showMovingWcar(d_seq);
	}
	g_tmMovingWcar = setTimeout('actionMovingTimer()', 5000);
}
function stopMovingTimer() {
	if (g_tmMovingWcar != null) {
		clearTimeout(g_tmMovingWcar);
		g_tmMovingWcar = null;
	}
}

function showMoveWard(d_seq) {

	function onAjaxResult(data) {
		if (data.retCode == const_ret_ok) {
			smap.clearWard();
			for (var i = 0; i < data.items.length; i ++) {
				var	item = data.items[i];
				var	dataobj = new MarkerInfo(item.ward_id, item.ward_name, '');
				smap.makeWard(item.gis_y, item.gis_x, dataobj);
			}
		} else {
			alert(data.retMsg);
		}
	}

	var url = '/ons/accident/wardlist_ajax.do';
	var params = {
		dsr_seq: d_seq
	};

	net_ajax(url, params, onAjaxResult, onNetCallbackEmptyError);
}
function showMovingWcar(d_seq) {

	function onAjaxResult(data) {
		if (data.retCode == const_ret_ok) {
			for (var i = 0; i < data.items.length; i ++) {
				var	item = data.items[i];
				var	gis_x = toDouble(item.gis_x);
				var	gis_y = toDouble(item.gis_y);

				if (gis_x == 0 || gis_y == 0) {
					continue;
				}
				smap.setWcarMoveData(item.car_id, item.gis_y, item.gis_x, $.trim(item.car_stat_cd), item.car_stat_name);
			}
		} else {
			alert(data.retMsg);
		}
	}

	var url = '/ons/accident/wcarlist_ajax.do';
	var params = {
		dsr_seq: d_seq
	};

	net_ajax(url, params, onAjaxResult, onNetCallbackEmptyError);
}
// 재난목록에서 재난상세
function showAccidentDetail(d_seq) {
	//$pop = createPop($pop_disdetail);
	$pop = createModalPop('pop-disdetail');

	var	item = acctList.getItem(d_seq);
	
	$pop.find('#dsr_seq').text(d_seq);
	$pop.find('#reg_dtime').text(makeDateTimeHM($.trim(item.reg_dtime)));	// 접수일시
	$pop.find('#dsr_knd_name').text($.trim(item.dsr_knd_name));				// 재난유형
	$pop.find('#reg_user_name').text($.trim(item.reg_user_name));			// 접수자명
	$pop.find('#dsr_cls_name').text(subStr(item.dsr_cls_name, 10));			// 재난분류
	$pop.find('#dsr_cls_name').attr('title', $.trim(item.dsr_cls_name));	// 재난분류
	$pop.find('#call_tel').text($.trim(item.call_tel));					// 신고자연락처
	$pop.find('#point_accr_name').text($.trim(item.point_accr_name));	// 재난위치유형
	$pop.find('#radio_name').text($.trim(item.radio_name));				// 무선기지국
	$pop.find('#dsr_addr').text(subStr($.trim(item.dsr_addr), 27));		// 재난주소
	$pop.find('#dsr_addr').attr('title', $.trim(item.dsr_addr));		// 재난주소
	$pop.find('#call_content').text(subStr(item.call_content, 27));		// 신고내용
	$pop.find('#call_content').attr('title', $.trim(item.call_content));	// 신고내용
}

function onClick_obstmanageDetail(car_no) {
	$pop = createModalPop('pop-obstcardetail');
	
	$pop.find('#recog_data_searching').show();
	//
	$pop.find('#btn_recog_search').hide();
	$pop.find('#btn_recog_save').hide();
	$pop.find('#btn_recog_obstcar').hide();
	$pop.find('#btn_recog_sendmsg').hide();
	
	$pop.find('#cam_main').hide();
	
//	$pop.find('#btn_mgr_obstcar').show();
//	$pop.find('#btn_mgr_sendmsg').show();
	
	$pop.find('#recog_own_car_no').prop('readonly', true);
	$pop.find('#recog_own_car_no').val(car_no);
	$pop.find('#recog_car_no').val(car_no);

	var dsr_seq	= smap.getAcctCurSel();
	var url = '/ons/recog/list_ajax.do';
	var params = {
		car_no: car_no,
		dsr_seq: dsr_seq,
		curr_dt: '2099-12-23 11:11:11'
	};

	net_ajax(url, params, function (data) {

		if (data.retCode == const_ret_ok || data.retCode == const_err_car_not_found) {
			var sHtml	= '';

			$pop.find('#recog_own_name').text(data.own_name);
			$pop.find('#recog_telno').text(data.tel_no);
			$pop.find('#recog_sch_dt').text(data.sch_dt);
			
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
				sHtml += '<div title="' + cctv_name + '"><b>카메라 : ' + subStr(cctv_name, 20) + '</b></div><br />';
				sHtml += '<div title="' + cctv_addr + '">위치 : ' + subStr(cctv_addr) +'</div><br />';
				sHtml += '촬영시각 :' + makeDateTimeHM(recog_time);
				sHtml += '</div>';
				sHtml += '<div class="mos">';
				sHtml += '<div class="carimg"><img src="' + url_data2 +'" width="105px" height="62px" /></div>';
			//	sHtml += '<div class="bg06btn">모자이크</div>';
				sHtml += '</div>';
				sHtml += '</div>';
			}
			
			$pop.find('.sublist').html(sHtml);
			$pop.find('#recog_data_searching').hide();
		}
	}, function () {
		$pop.find('#recog_data_searching').hide();
		alert('오류가 발생하였습니다.\n잠시후에 이용하여 주시기 바랍니다.');
	});
}

// 차량메시지전송 팝업 
function showPopMessage(d_seq, car_id, car_no) {
	var url = '/ons/carmsg/list_ajax.do';
	var params = {
		dsr_seq: d_seq, 
		car_no: car_no,
	};
	
	cmLog('car_no: ' + car_no);
	net_ajax(url, params, function (data) {
		if (data.retCode == const_ret_ok) {
			$pop = createPop($pop_msg);

			$pop.find('#span_carno').text(car_no);
			$pop.find('#msg_carid').val(car_id);
			$pop.find('#msg_carno').val(car_no);
			$pop.find('#msg_dsr_seq').val(d_seq);
			
			var items	= data.items;
			var sHtml	= '';
			
			if (items.length > 0) {
				for (var i = 0; i < items.length; i ++) {
					sHtml += items[i].msg_contents;
					sHtml += '<br />' + items[i].reg_dt + '';
					if (items[i].snd_stat == '0')
						sHtml += ' (*)';
					sHtml += '<br /><br />';
				}
			}
			
			$pop.find('.you').html(sHtml);
		}
	}, onNetCallbackDefaultError);
}

//차량으로 메시지전송
function sendCarMsg(pop) {
	var	carid = $(pop).find('#msg_carid').val();
	var	carno = $(pop).find('#msg_carno').val();
	var	contents = $(pop).find('#msg_contents').val();
	var	d_seq = $(pop).find('#msg_dsr_seq').val();

	if (carno == '') {
		alert('전송할 차량번호가 없습니다.');
		return;
	}
	if (contents == '') {
		alert('전송할 메시지를 입력하십시오.');
		return;
	}
	function onAjaxResult(data) {
		if (data.retCode == const_ret_ok) {
			removePop(pop);
			alert("메시지가 전송 되었습니다.");
		} else {
			alert(data.retMsg);
		}
	}

	var url = '/ons/carmsg/reg_action.do';
	var params = {
		msg_extra: d_seq, 
		car_id: carid,
		car_no: carno,
		msg_contents: contents,
		msg_type: '1'
	};
	
	cmLog('car_no: ' + carno + ', contents: ' + contents);
	net_ajax(url, params, onAjaxResult, onNetCallbackDefaultError);
}

function sendCarCctvMsg(thiz) {
	var car_id		= null;
	var car_no		= thiz.find('.pop-content .carlist #carnum').attr('title');
	var cctv_key	= thiz.find('#cctv_key').val();
	var dsr_seq		= smap.getAcctCurSel();
	
	var marker				= smap.getCctvMarker(cctv_key);
	var cctv_type			= null;
	var msg_contents		= null;
	var ukey				= null;
	
	if (thiz.find('.pop-content .carlist #carnum').attr('title') == '') {
		alert('차량을 선택하세요.');
		return;
	}
	
	if (car_no == '') {
		alert('선택한 차량의 차량번호가 존재하지 않습니다.');
		return;
	}
	
	var aryWcar		= smap.getWcarList();
	
	for (var i = 0; i < aryWcar.length; i ++) {
		if (aryWcar[i].obj.car_no == car_no) {
			car_id	= aryWcar[i].obj.car_id;
		}
	}
	
	if (marker == null) {
		ukey			= car_no;
		cctv_type		= 3;
		msg_contents	= '선착대 제공 현장 영상정보 전송';
	} else {
		ukey			= cctv_key;
		cctv_type		= 2;
		msg_contents	= '상황실 선택 현장 CCTV 영상정보 전송';
	}
	
	var url = '/ons/carmsg/reg_action.do';
	var params = {
		car_id: car_id,
		car_no: car_no,
		msg_type: '2',
		msg_contents: msg_contents,
		msg_extra: dsr_seq,
		cctv_type: cctv_type,
		cctv_dbkey: ukey
	};
	
	net_ajax(url, params, function (data) {
		if (data.retCode == const_ret_ok) {
			removePop($pop);
			alert('전송되었습니다.');
			
		} else {
			alert(data.retMsg);
		}
	}, onNetCallbackDefaultError);
}


function findCourse(car_no) {
	$('.pop-course').show();
	showRouteDetailEx(car_no);
}

function recordVideo(div_thiz) {
	var	carMarker = smap.getWcarMarker(div_thiz.find('#cctv_key').val());

	if (carMarker == null) {
		alert('차량을 다시 선택하여 주시기 바랍니다.');
		return;
	}
	var	coord = carMarker.getPosition();
	var	car_id = carMarker.obj.mkey;
	var	car_no = carMarker.obj.car_no;
	var	rtsp_url = carMarker.obj.url;

	cmLog('rtsp_url: ' + rtsp_url);
	if (rtsp_url == '') {
		alert('차량정보(카메라 접속정보)를 확인하여 주시기 바랍니다.');
		return;
	}

	if (confirm('차량 카메라 영상을 저장하시겠습니까?') == false) {
		return;
	}

	var url = '/ons/recog/record.do';
	var params = {
		car_id: car_id,
		car_no: car_no,
		cam_type: '3',
		poi_x: coord.x,
		poi_y: coord.y,
		cap_url: rtsp_url,
		save_sec: g_userCfg.save_sec
	};
	
	net_ajax(url, params, function (data) {
		if (data.retCode == const_ret_ok) {
			alert('저장 요청이 처리되었습니다.');
		} else {
			alert(data.retMsg);
		}
	}, onNetCallbackDefaultError);
}

// CCTV캡쳐후 차량번호인식 및 장애차량 단속/메시지 처리
function captureAndRecog(thiz) {
	var	recogMarker = null;
	var	cctvMarker = smap.getCctvMarker(thiz.find('#cctv_key').val());
	
	if (cctvMarker != null) {
		recogMarker = new RecogMarker(cctvMarker, cctvMarker.obj.etype);
	}
	else {
		var	wcarMarker = smap.getWcarMarker(thiz.find('#cctv_key').val());
		if (wcarMarker == null) {
			cmLog('wcarMarker is null...');
			alert('선택된 차량CCTV 정보가 존재하지 않습니다.');
			return;
		}
		recogMarker = new RecogMarker(wcarMarker, '3');
	}
	if (smap.getAcctCurSel() == '') {
		alert('선택된 재난정보가 없습니다.');
		return;
	}

	/*
	var	$pop = createModalPop('pop-mosaic');
	
	initCanvas($pop);
	*/
	initRecogCar(smap.getAcctCurSel(), recogMarker);

	var	wpop = window.open('pop_cardetail.do', 'wpop_recogcar', 'resizable=no, scrollbars=no, left=100, top=100, width=1080, height=440');
	wpop.focus();
	
	cctvCoordToAddr(true);
}
function recogStart() {
	if (getRecogWidth() <= 0 && getRecogHeight() <= 0) {
		copyToDest();
	}
	doAction_carnoRecognize(false);
}

function spreadVideo(thiz) {

	var cctv_key	= thiz.find('#cctv_key').val();

	/*
	var	$pop = createPop($pop_sendcourse);

	$pop.find('#cctv_key').val(cctv_key);
	
	var aryWcar		= smap.getWcarList();
	var sHtml	= '';
	
	for (var i = 0; i < aryWcar.length; i ++) {
		if (aryWcar[i].obj.mkey == cctv_key) {
			continue;
		}
		
		var sText	= '';
		if (aryWcar[i].obj.cam_yn == 'Y') {
			sText	= aryWcar[i].obj.car_no + ' - ' + aryWcar[i].obj.car_type_name;
			sHtml	+= '<li title="' + aryWcar[i].obj.car_no +'">' + sText + '</li>';
		}
	}
	
	$pop.find('.pop-content .carlist ul').html(sHtml);
	$pop.find('.pop-content .carlist ul li').mousedown(function() {
		$(this).parent().parent().find('#carnum').text($(this).text());
		$(this).parent().parent().find('#carnum').attr('title', $(this).attr('title'));
	});
	*/

	var	params = '?cctv_key=' + encodeURIComponent(cctv_key);
	var wpop = window.open('pop_sendcourse.do' + params, 'wpop_sendcourse', 'left=300, top=300, width=260, height=130, resizable=no');
	wpop.focus();
}

function showCctvCastRect(rc) {
	var	cctvList = smap.makeCctvListInRect(rc.getBounds());
	if (cctvList.length <= 0) {
		alert('선택된 영역내에는 CCTV가 존재하지 않습니다.');
		return;
	}
	showPopCCTVMatrix();
}
function getCctvCastRect() {
	return smap.getCctvListInRect();
}

function showMarkerDetail(shtml) {
	$('#marker_detail').html(shtml);
	if (shtml == '')
		g_lastShowType = 0;
}

//
// map click & mouse move & marker click event
//
function onClick_mapEvent(omap, evt) {
	g_lastClickCoord = evt.getCoord();

	closeSeachResultList();
	closeRouteDetail();

	if (g_isAvoid) {
		onMapClick_avoidPoint(evt.getCoord());
		return;
	}
	
	// CCTV 투망감시
	if (g_isRectMode) {
		if (g_isFirstClick == false) {
			g_isFirstClick = true;
			mrect.setFirstCoord(evt.getCoord());
		}
		else {
			g_isFirstClick = false;
			mrect.setLastCoord(evt.getCoord());
			mrect.clear();

			g_isRectMode = false;
			$("#layer_container").css("cursor", "default");
			showCctvCastRect(mrect);
		}
		return;
	}

	// 반경표시 모드 활성화
	if (g_isRadiusMode) {
		if (g_isFirstClick == false) {
			g_isFirstClick = true;
			oline.setFirstCoord(evt.getCoord());
		}
		else {
			g_isFirstClick = false;
			oline.setLastCoord(evt.getCoord());
			oline.clear();
			
			mcircle.clear();
			mcircle.setFirstCoord(oline.getFirstCoord());
			mcircle.setLastCoord(oline.getLastCoord());
			mcircle.makeCircle();
		}
	}
}
function onMousemove_mapEvent(omap, evt) {

	if (g_isRadiusMode && g_isFirstClick) {
		oline.setLastCoord(evt.getCoord());
		oline.makeLine();
	}
	else if (g_isRectMode && g_isFirstClick) {
		mrect.setLastCoord(evt.getCoord());
		mrect.makeRect();
	} 
}

function onClick_markerEvent(marker, evt) {
	g_lastClickMarker = marker;

	if (marker.mtype == TYPE_AVOID) {
		var	sHtml = '';
		sHtml += '<span class="traffic-list">관제설정 회피지점</span>';
		sHtml += '<br />';
		sHtml += '<br />';
		sHtml += '<span class="traffic-list">통제정도 : ' + getDegreeName(marker.obj.etype) + '</span>';
		sHtml += '<br />';
		sHtml += '<span class="traffic-list">등록일 : ' + subStr(marker.obj.mdate, 16) + '</span>';
		sHtml += '<br />';
		sHtml += '<br />';
		sHtml += '<div class="ti_btns btns">';
		sHtml += '	<a href="javascript:showPopupDelAvoid(\'' + marker.obj.mkey + '\')"><img src="./images/btn_right04_off.png" alt="회피지점삭제" title="회피지점삭제" /></a>';
		sHtml += '</div>';
		showMarkerDetail(sHtml);
		g_lastShowType = TYPE_AVOID;
	}
	else if (marker.mtype == TYPE_CCTV) {
		
		createPopCCTV(marker.obj.mkey, marker.obj.mtitle, marker.obj.url, marker.obj.etype, marker.obj.ipaddr);
	}
	else if (marker.mtype == TYPE_ACCT) {
		// item.gis_y, item.gis_x;
		// item.dsr_seq
		// item.dsr_knd_name
		cmLog('dsr_seq:' + marker.obj.dsr_seq + ', title:' + marker.obj.mtitle);
	}
	else if (marker.mtype == TYPE_WARD) {
		cmLog('출동소방서명:' + marker.obj.mtitle);
	}
	else if (marker.mtype == TYPE_WCAR) {
		var	d_seq = smap.getAcctCurSel();
		var	sHtml = '';
		sHtml += '<span class="traffic-list">' + marker.obj.car_no + '</span>';
		sHtml += '<br />';
		sHtml += '<span class="traffic-list">차종 : ' + $.trim(marker.obj.car_type_name) + '</span>';
		sHtml += '<br />';
		sHtml += '<span class="traffic-list">출동시각 : ' + subStr(marker.obj.mdate, 16) + '</span>';
		sHtml += '<br />';
		sHtml += '<span class="traffic-list">상태 : ' + marker.obj.car_stat_name + '</span>';
		sHtml += '<br />';
		sHtml += '<div class="ti_btns btns">';
		sHtml += '	<a href="javascript:findCourse(\'' + marker.obj.car_no + '\')"><img src="./images/btn_right01_off.png" alt="경로정보조회" title="경로정보조회" /></a>';
		if (marker.obj.cam_yn == 'Y') {
			var	cctv_params = '';
			var	ipaddr = marker.obj.cam_hostport;
			cctv_params += '\'' + marker.obj.mkey   + '\', ';
			cctv_params += '\'' + marker.obj.mtitle + '\', ';
			cctv_params += '\'' + marker.obj.url  + '\', ';
			cctv_params += '\'3\', ';
			cctv_params += '\'' + ipaddr  + '\'';
			sHtml += '	<a href="javascript:createPopCCTV(' + cctv_params + ')"><img src="./images/btn_right02_off.png" title="차량 CCTV조회" /></a>';
		}
		sHtml += '	<a href="javascript:showPopMessage(\'' + d_seq + '\', \'' + marker.obj.mkey + '\', \'' + marker.obj.car_no + '\')"><img src="./images/btn_right03_off.png" title="차량 메세지 전송" /></a>';
		sHtml += '</div>';
		showMarkerDetail(sHtml);
		g_lastShowType = TYPE_WCAR;

		//
		showRouteData(smap, marker.obj.car_no);
	}
}


function onClick_logout() {
	if (confirm('로그아웃을 하시겠습니까?')) {
		stopAcctListTimer();
		stopMovingTimer();
		location.href = '/ons/logout.do';
	}
}

$(document).ready(function() {
	smap.init('map_div');
	
	//
	if (g_userCfg.sch_type == '')
		g_userCfg.sch_type = '9';
	var	con_text = $('#route_search_type ul li[value='+ g_userCfg.sch_type +']').text();
	$("#route_search_type span").text(con_text);

	//
	showListAvoid();
	
	initRouteData();
	//
	oline = new MapLine();
	mcircle = new MapCircle();
	mrect = new MapRect();

	showListAccident();
	startAcctListTimer();
	
	g_vlcVersion = PluginDetect.getVersion('vlc');
	if (g_vlcVersion == null) {
		showPopInstall();
	}
	
	/* add by ktjoon 2016-01 */
	showSMS();
	
});
/* add by ktjoon 2016-01 */
//차량메시지 수신 팝업 호출
function showSMS() {
	
	var url = '/ons/carmsg/smsList_ajax.do';
	var params = {
	};
	
	net_ajax(url, params, function (data) {
		//alert(data.items);
		if (data.retCode == const_ret_ok && data.items != '') {
			
			
			//창이 떠있으면 새로 열리지 않게 한다.
			if(!isSmsOpen) {
				$pop = createPop($pop_sms);
			}
			isSmsOpen = true;
			
			//alert($pop.css("display"));
			
			if($pop.css("display") == 'none') {
				$pop = createPop($pop_sms);
			}
			
			//$pop.find('#span_carno').text(car_no);
			var items	= data.items;
			var sHtml	= '';
			var msg_seq = '';
			
			if (items.length > 0) {
				for (var i = 0; i < items.length; i ++) {
					sHtml += items[i].reg_dt;
					sHtml += '<br />' + items[i].msg_contents;
					if(items[i].img_url != null) {
						sHtml += '<br /><a href="' + items[i].img_url + '" target="_blank"><img src="' + items[i].img_url + '" width="250" height="200" /></a>';
					}
					sHtml += '<br /><br />';
					msg_seq += items[i].msg_seq + ',';
				}
			}
			$pop.find('.sms').html(sHtml);
	
			msg_seq = msg_seq.substring(0, msg_seq.length-1);
			$pop.find('#msg_seq').val(msg_seq);
		}
	}, onNetCallbackDefaultError);
		
	setTimeout('showSMS()', 5000);
}

/* add by ktjoon 2016-01 */
//차량메시지 닫기
function closeSMS(pop) {
	
	var	msg_seq = pop.find('#msg_seq').val();
	//alert("msg_seq="+msg_seq);
	
	var arr_msg_seq = msg_seq.split(',');
	//alert("arr_msg_seq = " + arr_msg_seq);
	
	$.ajax({
		type : "POST",
		url : "/ons/carmsg/updateMessageList.do",
		data : {"arr_msg_seq" : arr_msg_seq},
		async : true,
		dataType : "json",
		timeout : 10*60*1000,
		success : removePop(pop),
        error : function(request,status,error){
	        //alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        	alert('오류가 발생하였습니다.\n잠시후에 이용하여 주시기 바랍니다.');
	       }
	}); 
}


</script>
</head>
<body>
	<div class="overlay" style="display: none; height: 2714px;"></div>

	<div id="top">
		<div class="logo"></div>
		<div class="title">
			<img class="title-img" src="images/logo_text.png" />
			<!-- 
olleh.maps.DirectionsDrivePriority.PRIORITY_0 : 최단 거리 우선
olleh.maps.DirectionsDrivePriority.PRIORITY_1 : 고속도로 우선
olleh.maps.DirectionsDrivePriority.PRIORITY_2 : 무료 도로 우선
olleh.maps.DirectionsDrivePriority.PRIORITY_3 : 최적 경로
olleh.maps.DirectionsDrivePriority.PRIORITY_4 : 실시간 도로정보 우선
			-->
			<div class="condition" id="route_search_type">
				<span>소방전용탐색</span>
				<ul>
					<li value="9">소방전용탐색</li><!-- 9 -->
					<li value="0">최단거리우선</li><!-- 0 -->
					<li value="1">고속도로우선</li><!-- 1 -->
					<li value="2">무료도로우선</li><!-- 2 -->
					<li value="3">최적경로탐색</li><!-- 3 -->
					<li value="4">실시간정보우선</li><!-- 4 -->
				</ul>
			</div>
			<div class="bg07btn" style="float:left; margin:16px 0 0 0;" onclick="routeSearch(true);">재탐색</div>
			<div class="logout-img" title="로그아웃" onclick="onClick_logout();"></div>
			<div class="guide-img" title="도움말" onclick="window.open('manual.do', '_blank', 'width=990, height=750')"></div>
		</div>
	</div>
	<div id="content">
		<div id="map_div">
		</div>
		<div class="search-div">
			<input id="search-box" type="text" class="search-box" name="sword" />
			<label for="search-box"><span class="search-icon"></span></label>
			<input type="submit" id="search-submit" />
			<ul>
				<!-- <li style='padding: 0 0 10px 0; margin: 0 10px; border-bottom: 1px solid black; cursor:pointer;'><span class="name" style='font-size:20px; font-weight:bold; line-height:40px;'>해강한의원</span><br /><span style=''>인천광역시 연수구 동춘동 234-34</span></li>
				<li style='padding: 0 0 10px 0; margin: 0 10px; border-bottom: 1px solid black; cursor:pointer;'><span class="name" style='font-size:20px; font-weight:bold; line-height:40px;'>해강한의원2</span><br /><span style=''>인천광역시 연수구 동춘동 234-34</span></li>
				<li style='padding: 0 0 10px 0; margin: 0 10px; border-bottom: 1px solid black; cursor:pointer;'><span class="name" style='font-size:20px; font-weight:bold; line-height:40px;'>해강한의원3</span><br /><span style=''>인천광역시 연수구 동춘동 234-34</span></li> -->
			</ul>
		</div>
		<div class="traffic-info-btn" style="display: none;">
			<img src="images/icon_signal.png" />
			<br />
			<span>교통정보</span>
		</div>
		<div class="btns">
			<div id="avoidBtn">
				<p class="text">
					회피지점 설정
				</p>
				<p class="img img-pen"></p>
			</div>
			<div id="clearBtn">
				<p class="text">
					초기화
				</p>
				<p class="img img-clear"></p>
			</div>
			<div id="castBtn">
				<p class="text">
					투망감시
				</p>
				<p class="img img-watch"></p>
			</div>
			<div id="terminateBtn">
				<p class="text">
					장애제거
				</p>
				<p class="img img-warning"></p>
			</div>
		</div>
		<img id="bearing" src="./images/btn_bearing.png" style="top:310px; right:250px; position:absolute; cursor:pointer;" title="반경재기" />
		<div id="detail-content">
			<div class="detail-box">
				<!-- 이 안에 오른쪽 영역 컨텐츠 들어가면 됩니다. -->
				<div class="disaster-info">
					<div class="disaster-title">
						재난 정보 <input style="display: none;" type="button" value="R" onclick="showListAccident()" />
					</div>
					<div class="article-list">
						<!-- 
						<div class="article">
							<div class="article-class">
								화재
							</div>
							<div class="article-location">
								연수동부
							</div>
							<div class="article-address">
								인천 연수구 연수동 123-1 가나다라마 바사아자차카타 파하
							</div>
								<img class="ovbtn" src="./images/icon_check_off.png" style="float:right;" />
						</div>
						<div class="article">
							<div class="article-class">
								응급
							</div>
							<div class="article-location">
								연수동부
							</div>
							<div class="article-address">
								인천 연수구 연수동 123-1 가나다라마 바사아자차카타 파하 테스트테스트테스트테스트 테스트테스트
							</div>
								<img class="ovbtn" src="./images/icon_check_off.png" style="float:right;" />
						</div>
						<div class="article">
							<div class="article-class">
								화재
							</div>
							<div class="article-location">
								연수동부
							</div>
							<div class="article-address">
								인천 연수구 연수동 123-1 가나다라마 바사아자차카타 파하
							</div>
								<img class="ovbtn" src="./images/icon_check_off.png" style="float:right;" />
						</div>
						<div class="article">
							<div class="article-class">
								화재
							</div>
							<div class="article-location">
								연수동부
							</div>
							<div class="article-address">
								인천 연수구 연수동 123-1 가나다라마 바사아자차카타 파하
							</div>
								<img class="ovbtn" src="./images/icon_check_off.png" style="float:right;" />
						</div>
						-->
					</div>
				</div>
				<div class="traffic-info">
					<div class="traffic-title">
						출동차량 및 회피지점 정보
					</div>
					<div class="article">
						<div id="marker_detail">
						</div>
						<!-- 
						<span class="traffic-list">칠곡석적 991</span>
						<br />
						<span class="traffic-list">차종 : 소방차</span>
						<br />
						<span class="traffic-list">출동시각 : 2014-01-01 10:00</span>
						<br />
						<span class="traffic-list">상태 : 현장출</span>
						<br />
						<div class="ti_btns btns">
							<a href="javascript:findCourse()"><img src="./images/btn_right01_off.png" alt="경로정보조회" /></a>
							<a href="javascript:createPop($pop_cctv)"><img src="./images/btn_right02_off.png" /></a>
							<a href="javascript:createPop($pop_msg)"><img src="./images/btn_right03_off.png" /></a>
						</div>
						-->
					</div>
				</div>
			</div>
		</div>
	</div>

	
	<div class="installVLC">
		<div class="pop-top">
			<div class="title">VLC 플러그인 설치가 필요합니다.</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>
		<div class="pop-content">
			<div class="mid">
				<div class="qu">VLC 플러그인 설치 페이지로 이동하시겠습니까?</div>
				<a href="javascript:;" onclick="window.open('http://www.videolan.org/vlc/', '_blank');"><div class="btn">이동</div></a>
                   <a href="javascript:;" onclick="removePop($(this).parent().parent().parent())"><div class="cancel btn">취소</div></a>
			</div>
		</div>
	</div>
	
	<!-- alert 팝업 -->
	<div class="pop-alert">
		<div class="pop-top">
			<div class="title">메시지 팝업.</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>

		<div class="pop-content">
			<div class="mid">
				<div class="qu">알럿 메시지가 들어갑니다.</div>
				<a href="javascript:;" onclick="removePop($(this).parent().parent().parent())"><div class="btn">확인</div></a>
			</div>
		</div>
	</div>

	<!-- confirm 팝업 -->
	<div class="pop-confirm">
		<div class="pop-top">
			<div class="title">메시지 확인 팝업.</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>

		<div class="pop-content">
			<div class="mid">
				<div class="qu">메시지 확인 텍스트가 들어갑니다.</div>
				<a href="javascript:;" onclick=""><div class="btn">확인</div></a>
				<a href="javascript:;" onclick="removePop($(this).parent().parent().parent())"><div class="cancel btn">취소</div></a>
			</div>
		</div>
	</div>

	<!-- 재난상세정보 팝업 클래스 : pop-disdetail -->
	<div class="pop-disdetail lp">
		<div class="pop-top">
			<div class="title">재난 상세 정보</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>
		<div class="pop-content">
			<table>
				<colgroup>
					<col width="90px" />
					<col width="110px" />
					<col width="90px" />
					<col width="110px" />
				</colgroup>
				<tr>
					<th>재난번호</th>
					<td id="dsr_seq">A1234567</td>
					<th>접수일시</th>
					<td id="reg_dtime">2014-12-10 12:14</td>
				</tr>
				<tr>
					<th>재난유형</th>
					<td id="dsr_knd_name">화재</td>
					<th>접수자명</th>
					<td id="reg_user_name">홍길동</td>
				</tr>
				<tr>
					<th>재난분류</th>
					<td id="dsr_cls_name">차량</td>
					<th>신고자연락처</th>
					<td id="call_tel">010-1234-1234</td>
				</tr>
				<tr>
					<th>재난위치유형</th>
					<td id="point_accr_name">연수구지점</td>
					<th>무선기지국</th>
					<td id="radio_name"></td>
				</tr>
				<tr>
					<th>재난주소</th>
					<td id="dsr_addr" colspan="3">인천관역시 연수구</td>
				</tr>
				<tr>
					<th>신고내용</th>
					<td id="call_content" colspan="3"></td>
				</tr>
			</table>
		</div>
	</div>

	<!-- 회피지점 설정 팝업 클래스 : set-aovid -->
	<div class="pop-setavoid lp">
		<div class="pop-top">
			<div class="title">회피지점 설정</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>
		<div class="pop-content">
			<div class="mid">
				<div class="qu">선택하신 도로를 회피지점으로 설정하시겠습니까?</div>
				<div class="condition">
					<span id="avoid-reason">전면통제</span>
					<ul>
						<li value="1">전면통제</li>
						<li value="2">부분통제</li>
					</ul>
					<input id="avoid_type" type="hidden" value="1" />
					<input id="avoid_addr" type="hidden" value="" />
				</div>
				<a href="javascript:;" onclick="setAvoid($(this).parent().parent().parent())"><div class="btn">설정</div></a>
				<a href="javascript:;" onclick="removePop($(this).parent().parent().parent())"><div class="cancel btn">취소</div></a>
			</div>
		</div>
	</div>

	<!-- 회피지점 취소 팝업 클래스 : del-aovid -->
	<div class="avoid pop-delavoid lp">
		<div class="pop-top">
			<div class="title">회피지점 삭제</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>
		<div class="pop-content">
			<div class="mid">
				<div class="qu">입력하신 회피지점를 삭제하시겠습니까?</div>
				<a href="javascript:;" onclick="delAvoid($(this).parent().parent().parent())"><div class="btn">삭제</div></a>
                   <a href="javascript:;" onclick="removePop($(this).parent().parent().parent())"><div class="cancel btn">취소</div></a>
                <input id="del_avoid_key" type="hidden" value="" />
			</div>
		</div>
	</div>

	<!-- 회피지점 초기화 팝업 클래스 : clear-aovid -->
	<div class="avoid pop-clearavoid lp">
		<div class="pop-top">
			<div class="title">회피지점 초기화</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>
		<div class="pop-content">
			<div class="mid">
				<div class="qu">입력하신 회피지점를 초기화하시겠습니까?</div>
				<a href="javascript:;" onclick="clearAvoid($(this).parent().parent().parent())"><div class="btn">초기화</div></a>
                   <a href="javascript:;" onclick="removePop($(this).parent().parent().parent())"><div class="cancel btn">취소</div></a>
			</div>
		</div>
	</div>

	<!-- 경로 전송 팝업 클래스-->
	<div class="pop-sendcourse lp">
		<div class="pop-top">
			<div class="title">CCTV 정보전송</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>
		<div class="pop-content">
			<div class="carlist">
				<span id="carnum" title="">차량 선택</span>
				<ul>
					<li>서울12가 1234 - 소방차</li>
					<li>서울12가 3235 - 구급차</li>
					<li>서울12가 1335 - 승용차</li>
				</ul>
				<input id="cctv_key" type="hidden" value="" />
			</div>
			<div class="bg02btn" onclick="sendCarCctvMsg($(this).parent().parent());">CCTV정보 전송</div>
		</div>
	</div>

	<!-- cctv 팝업 클래스 : pop-cctv -->
	<div class="pop-cctv lp">
		<div class="pop-top">
			<div class="title-wrap" style="width: 285px; height: 41px; float: left; overflow: hidden;">
				<div class="title" id="cctv_title" title="">cctv</div>
			</div>
			<div class="btn-close btns">
				<a href="javascript:;" class="inl-btn-window-x2"><img  src="./images/btn_window_x2.png" /></a>
				<a href="javascript:;" class="inl-btn-close2"><img  src="./images/btn_close2.png" onclick="
				closePopCCTV($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>
		<div class="pop-content">
			<div id="video_cctv" class="cctv">
			</div>
			<div class="btns">
				<img id="cctv_record"  src="./images/btn_camera_off.png" alt="현장 카메라 영상 저장" title="현장 카메라 영상 저장" onclick="recordVideo($(this).parent())" style="display: none;" />
				<img id="cctv_recog"  src="./images/btn_number_off.png" alt="속 처리 및 번호 인식" title="단속 처리 및 번호 인식" onclick="captureAndRecog($(this).parent())" />
				<img id="cctv_spread" src="./images/btn_spread_off.png" alt="영상 정보 전송" title="영상 정보 전송" onclick="spreadVideo($(this).parent())" />
				<input id="car_no" type="hidden" value="" />
				<input id="cctv_key" type="hidden" value="" />
			</div>
			<div class="rc-area">
				<img src="./images/btn_up.png" alt="up" title="up" class="cctv-rc" style="top:0px; right:13px;" onclick="moveUp($(this).parent().parent());"/>
				<img src="./images/btn_left.png" alt="left" title="left" class="cctv-rc" style="top:13px; right:26px;"  onclick="moveLeft($(this).parent().parent());" />
				<img src="./images/btn_right.png" alt="right" title="right" class="cctv-rc" style="top:13px; right:0px;"  onclick="moveRight($(this).parent().parent());" />
				<img src="./images/btn_down.png" alt="down" title="down" class="cctv-rc" style="top:26px; right:13px;"  onclick="moveDown($(this).parent().parent());" />
			</div>
			<div class="zoom">
				<img src="./images/btn_plus.png" alt="plus" style="top:0px; left:0px;" onclick="camZoomIn($(this).parent().parent());" />
				<img src="./images/btn_minus.png" alt="minus" style="top:26px; left:0px;" onclick="camZoomOut($(this).parent().parent());" />
			</div>
			<input id="cctv_addr" type="hidden" value="" />
		</div>
	</div>

	<!-- 모자이크 처리 팝업 클래스-->
	<div class="pop-mosaic lp">
		<div class="pop-top">
			<div class="title">차량번호 영역지정</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>
		<div class="pop-content">
			<div class="mosimg">
				<canvas id="mst_canvas" width="480" height="360"></canvas>
				<canvas id="dst_canvas" style="display: none;"></canvas>
				<canvas id="moc_canvas" style="display: none;"></canvas>
			</div>
			<div class="center">
				<div class="bg01btn" style="display:inline-block;" onclick="recogStart()">차량번호인식</div>
				<div class="bg01btn" style="display:inline-block;" onclick="removePop($(this).parent().parent().parent())" >취소</div>
				<input id="cctv_key" type="hidden" value="" />
			</div>
		</div>
	</div>

	<!-- 메시지 팝업 클래스 -->
	<div class="pop-msg lp">
		<div class="pop-top">
			<div class="title">메시지 : <span id="span_carno">CAR_NO</span>
				<input id="msg_dsr_seq" type="hidden" value="" />
				<input id="msg_carid" type="hidden" value="" />
				<input id="msg_carno" type="hidden" value="" />
			</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>
		<div class="pop-content">
			<div class="msg-area">
				<div class="you">
					<!-- 해강한의원 앞 상황 안좋으니 원인재로 쪽으로 우회바람<br />관제센터 14:34 -->
				</div>
				<div class="me"></div>
			</div>
			<input id="msg_contents" class="message" placeholder="전송하실 메시지를 입력해주세요" style="padding: 0 0 0 10px;"></input>
				<div class="bg03btn" onclick="sendCarMsg($(this).parent().parent())">전송</div>
		</div>
	</div>
	
	<!-- 장애관리 팝업 클래스 : pop-obstmanage -->
	<div class="pop-obstmanage lp" style="display: none;">
		<div class="pop-top">
			<div class="title">장애관리</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>
		<div class="pop-content">
			<div class="table">
				<table>
					<colgroup>
						<col width="48px" />
						<col width="119px" />
						<col width="119px" />
						<col width="119px" />
						<col width="119px" />
						<col width="119px" />
					</colgroup>
					<tr id ="obstmanage_title">
						<th>순서</th>
						<th>차량번호</th>
						<th>최초촬영</th>
						<th>촬영CCTV</th>
						<th>단속여부</th>
						<th class="last">상세</th>
					</tr>
					<!-- <tr>
						<td><input type="checkbox" id="obstcheck" value="" /></td>
						<td>86나1234</td>
						<td>2014.12.12. 17:30:21</td>
						<td>너네사거리CCTV</td>
						<td>X</td>
						<td class="last"><span class="detail" style="border: 1px solid #9D9EA0; padding: 3px 12px; cursor: pointer; border-radius: 5px;">상세</span></td>
					</tr> -->
				</table>
			</div>
		</div>
	</div>

	<div class="pop-obstcardetail lp" style="display:none;">
		<div class="pop-top">
			<div class="title">장애차량 상세정보</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>
		<div class="pop-content">
			<div class="car-detail">
				<div class="car-info">
						<b>차량번호 : <input id="recog_own_car_no" type="text" style="width:100px; text-align:center; border:none; border-radius:5px;"></b><br />
						소유주 : <span id="recog_own_name"></span><br />
						연락처 : <span id="recog_telno"></span>
						<input id="recog_car_no" type="hidden" value="" />
						<input id="recog_img_rect" type="hidden" value="" />
						<input id="recog_curr_dt" type="hidden" value="" />
						<input id="recog_sch_dt" type="hidden" value="" />
						<input id="crack_stat" type="hidden" value="" />
						<input id="enforce_timeover_stat" type="hidden" value="" />
						<input id="enforce_stat" type="hidden" value="" />
						<input id="msg_auto_send" type="hidden" value="" />
						
						<br />
						<div id="recog_data_searching" style="display: none;"><font color="yellow">데이터를 검색하고 있습니다.</font> 잠시만 기다려 주십시오...</div>
                   </div>
                   <div class="car-btns">
						<div class="bg05btn" id="btn_recog_search" onclick="schRecogCarOwnInfo($(this).parent().parent().parent());">검색</div>
						<div class="bg05btn" id="btn_recog_save"onclick="onClick_setApplyData($(this).parent().parent().parent(), '1')">적용</div>
						<div class="bg05btn" id="btn_recog_obstcar" onclick="onClick_setRecogUpdate($(this).parent().parent().parent(), '2')">단속 처리</div>
						<div class="bg05btn sendmsg" id="btn_recog_sendmsg" onclick="onClick_setRecogUpdate($(this).parent().parent().parent(), '3')">메시지 전송</div>

						<div class="bg05btn" 		 style="display: none;"	id="btn_mgr_obstcar" onclick="onClick_setRecogData($(this).parent().parent().parent(), '2')">단속 처리</div>
						<div class="bg05btn sendmsg" style="display: none;"	id="btn_mgr_sendmsg" onclick="onClick_setRecogData($(this).parent().parent().parent(), '3')">메시지 전송</div>
                   </div>
			</div>
			<div class="camera-list">
				<div id="cam_main" class="cam main">
					<div class="camera"><img id="recog_img_data1" src="" width="125px" height="97px" /></div>
					<div class="camera-info">
						<div id="recog_cctv_name"><b>카메라 : 동춘사거리 CCTV</b></div><br />
						<div id="recog_cctv_addr">위치 : 카메라위치 (현장카메라의 경우 촬영 위치)</div><br />
						<div id="recog_cctv_currdate">촬영시각 : 2014-12-12 11:44</div>
					</div>
					<div class="mos">
						<div class="carimg">
							<img id="recog_img_data2" src="" width="105px" height="62px" />
							<img id="recog_img_data3" src="" width="105px" height="62px" style="display: none;"/>
						</div>
						<div class="bg06btn" onclick="showRecogMosaic($(this).parent());">모자이크</div>
					</div>
				</div>
				<div class="sublist">
					<!-- <div class="cam">
						<div class="camera">카메라 영상이 들어갑니다.</div>
						<div class="camera-info">
							<b>카메라 : 동춘사거리 CCTV</b> <span class="move">이동</span><br />
							위치 : 카메라위치 (현장카메라의 경우 촬영 위치)<br />
							촬영시각 : 2014.12.08 12:12:12
						</div>
						<div class="mos">
							<div class="carimg">차량이미지가 들어갑니다.</div>
							<div class="bg06btn">모자이크</div>
						</div>
					</div> -->
				</div>
			</div>
		</div>
	</div>

	<!-- 길안내 고정 레이어 클래스 -->
	<div class="pop-course">
		<div class="pop-top">
			<div class="title" id="course_car_no">차량 이름</div>
			<div class="btn-close btns">
				<div class="bg03btn" style="margin-top: 4px; float: right;" onclick="closeRouteDetail();">닫기</div>
			</div>
		</div>
		<div class="pop-content">
			<div class="course-list">
				<div class="course">
					<div class="course-icon">
						<img src="./images/road/icon_road01.png" />
					</div>
					<div class="course-text">
						동춘 119 안전센터
					</div>
					<div class="speed red">
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- add by ktjoon 2016-01 메시지 수신 팝업 클래스 -->
	<div class="pop-sms lp">
		<div class="pop-top">
			
			<div class="title">메시지 수신
				<input id="msg_seq" name="msg_seq" type="hidden" value="" />
			</div>
			<div class="btn-close btns">
				<a href="javascript:;"><img src="./images/btn_close_off.png" onclick="removePop($(this).parent().parent().parent().parent())" /></a>
			</div>
		</div>
		<div class="pop-content">
			<div class="msg-area">
				<div class="sms">
				</div>
			</div>
			<div class="bg03btn" onclick="closeSMS($(this).parent().parent())">다시 안보기</div>
		</div>
	</div>

	<iframe id="iframetest" src="" width="0" height="0"></iframe>
	<div id="cmd_ex"></div>
</body>
</html>
