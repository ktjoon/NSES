/**
 * NSES MAP - map control
 */

var		const_icon_size		= new olleh.maps.Size(42, 42);
var		const_icon_anchor	= new olleh.maps.Point(21, 42);

var		TYPE_ACCT			= 1;	// 재난 발생 위치
var		TYPE_EMGCAR			= 2;	// 응급차량 아이콘
var		TYPE_POLCAR			= 3;	// 경찰차량 아이콘
var		TYPE_WCAR			= 4;	// 소방차량 아이콘
var		TYPE_WARD			= 5;	// 소방서소 아이콘
var		TYPE_CCTV			= 6;	// CCTV 아이콘
var		TYPE_AVOID			= 7;	// 회피지점 아이콘
var		TYPE_SEARCH			= 11;	// 대상물검색 아이콘

var		ICON_ACCT			= '/ons/images/icon/icon01.png';	// 재난 발생 위치
var		ICON_ACCT_1			= '/ons/images/icon/icon01_1.png';	// 재난구분(화재) 아이콘
var		ICON_ACCT_2			= '/ons/images/icon/icon01_2.png';	// 재난구분(구조) 아이콘
var		ICON_ACCT_3			= '/ons/images/icon/icon01_3.png';	// 재난구분(구급) 아이콘
var		ICON_ACCT_4			= '/ons/images/icon/icon01_4.png';	// 재난구분(기타) 아이콘
var		ICON_EMGCAR			= '/ons/images/icon/icon02.png';	// 응급차량 아이콘
var		ICON_POLCAR			= '/ons/images/icon/icon03.png';	// 경찰차량 아이콘
var		ICON_WCAR_I			= '/ons/images/icon/icon04.png';	// 소방차량 아이콘
var		ICON_WCAR_R			= '/ons/images/icon/icon04_on.png';	// 소방차량 아이콘(귀소차량)
var		ICON_WARD			= '/ons/images/icon/icon05.png';	// 소방서소 아이콘
var		ICON_CCTV_NORMAL	= '/ons/images/icon/icon06.png';	// 연수구청 CCTV 아이콘
var		ICON_CCTV_FIRE		= '/ons/images/icon/icon06_on.png';	// 화재감시 CCTV 아이콘
var		ICON_CCTV_ACCT		= '/ons/images/icon/icon06_on.png';	// 재난주변 CCTV 아이콘
var		ICON_AVOID			= '/ons/images/icon/icon07.png';	// 회피지점 아이콘
var		ICON_SEARCH			= '/ons/images/icon/icon11.png';	// 대상물검색 아이콘

//
function MarkerInfo(ukey, utitle, udate, utype, uaddr, camurl, camip) {
	this.mkey		= ukey;		// 마커 유일키(DB-KEY)
	this.mtitle		= utitle;	// 타이틀
	this.mdate		= udate;	// 등록일
	this.etype		= utype;	// 추가1 타입정보 (CCTV)
	this.eaddr		= uaddr;	// 추가2 - 주소(대상물, 장애차량에서 사용)
	this.url		= camurl;	// 추가3 - CCTV URL
	this.ipaddr		= camip;	// 추가4 - CAM IPADDRESS
}

//
NsesMap = function () {
	this.omap 		= null;
	this.index		= 0;
	this.aryAVOID	= new Array();	// 회피지점
	//
	this.c_dsr_seq		= '';			// 재난선택항목
	this.acctMarker		= null;			// 재난마커
	this.objMarker		= null;			// 대상물마커
	this.aryWARD		= new Array();	// 출동소방서소
	this.aryWCAR		= new Array();	// 출동차량
	this.aryCCTV		= new Array();	// 재난주변CCTV
	this.actCctvList	= new Array();	// 주변CCTV(사용안함)
	this.monCctvList	= new Array();	// 투망감시CCTV
	this.markerList		= new Array();
};

NsesMap.prototype = {
	init: function (mapDiv) {
		var mapOpts = {
			center: new olleh.maps.LatLng(37.448441, 126.669013),
			copyrightControl: false, 	// Copyright 컨트롤 비활성화
			panControl: false, 			// PAN 컨트롤 비활성화
			/*zoomControlOptions: {
				top: 120,
				right: 10
			},
			measureControlOptions: {
				right: 10
			},*/
			zoom: 10
		};

		this.omap = new olleh.maps.Map(document.getElementById(mapDiv), mapOpts);
		
		this.omap.onEvent('click', function (evt) {
			onClick_mapEvent(this, evt);
		});
		this.omap.onEvent('mousemove', function (evt) {
			onMousemove_mapEvent(this, evt);
		});
	},
	getMap: function () {
		return this.omap;
	},
	setCenter: function (coord) {
		this.omap.setCenter(coord);
	},
	showAcctMarker: function (item) {
		//
		if (item == null) {
			if (this.acctMarker != null) {
				this.clearCmMarker(this.acctMarker);
				this.acctMarker = null;
			}
			this.c_dsr_seq = '';
			return false;
		}

		if (this.acctMarker != null) {
			var	accItem = this.acctMarker.obj;

			if (accItem.dsr_seq == item.dsr_seq) {
				var	coord = new olleh.maps.LatLng(item.gis_y, item.gis_x);
				this.setCenter(coord);
				return false;
			}
		}

		//
		this.c_dsr_seq = item.dsr_seq;
		cmLog('showAcctMarker(dsr_seq: ' + item.dsr_seq + ') - x: ' + item.gis_x + ', y: ' + item.gis_y);

		// 재난마커제거
		if (this.acctMarker != null) {
			this.clearCmMarker(this.acctMarker);
			this.acctMarker = null;
		}
		
		var	icon = ICON_ACCT_4;
		
		if (item.dsr_knd_cd == '0040001') {
			icon = ICON_ACCT_1;
		} else if (item.dsr_knd_cd == '0040002') {
			icon = ICON_ACCT_2;
		} else if (item.dsr_knd_cd == '0040003') {
			icon = ICON_ACCT_3;
		}

		var	coord = new olleh.maps.LatLng(item.gis_y, item.gis_x);
		this.acctMarker = this.makeCmMarker(coord, item.dsr_knd_name, item, icon, TYPE_ACCT);
		this.setCenter(coord);

		return true;
	},
	getAcctCurSel: function () {
		return this.c_dsr_seq;
	},
	showObjectMarker: function (item) {
		//
		if (item == null) {
			if (this.objMarker != null) {
				this.clearCmMarker(this.objMarker);
				this.objMarker = null;
			}
			return;
		}

		// 마커제거
		if (this.objMarker != null) {
			this.clearCmMarker(this.objMarker);
			this.objMarker = null;
		}

		var	coord = new olleh.maps.UTMK(item.x, item.y);
		var	itemobj = new MarkerInfo(item.key, item.title, '', '0', item.addr);
		this.objMarker = this.makeCmMarker(coord, item.title, itemobj, ICON_SEARCH, TYPE_SEARCH);
		this.setCenter(coord);
	},
	makePoint: function (lat, lng, stitle) {
		var marker = new olleh.maps.overlay.Marker({
			position: new olleh.maps.LatLng(lat, lng),
			icon: this.getIcon(ICON_CCTV), 		// 아이콘 url만 변경
			map: this.omap,
			draggable: true,
			title: stitle
		});
		this.index ++;
		marker.idx = this.index;

		marker.onEvent('click', function (evt) {
			alert(this.idx);
		});
		
		this.markerList.push(marker);
	},
	makeCmMarker: function (coord, stitle, itemobj, iconUri, uType) {
		var marker = new olleh.maps.overlay.Marker({
			position: coord,
			icon: this.getIcon(iconUri), 		// 아이콘 url만 변경
			map: this.omap,
			title: stitle
		});
		this.index ++;
		marker.idx = this.index;
		marker.mtype = uType;
		marker.obj = itemobj;

		marker.onEvent('click', function (evt) {
			onClick_markerEvent(this, evt);
		});
		return marker;
	},
	makeArrayMarker: function (coord, stitle, itemobj, aryList, iconUri, uType) {
		var	disp_title = null;
		if (stitle !== undefined) {
			var	width = getCharCount(stitle) * 6;
			if (width > 0) {
				width += 14;
				if (width > 200)
					width = 200;
				disp_title = '<div style="width: ' + width + 'px;">' + stitle + '</div>';
			}
		}
		var marker = new olleh.maps.overlay.Marker({
			position: coord,
			icon: this.getIcon(iconUri), 		// 아이콘 url만 변경
			map: this.omap,
			title: disp_title
		});
		this.index ++;
		marker.idx = this.index;
		marker.mtype = uType;
		marker.obj = itemobj;

		marker.onEvent('click', function (evt) {
			onClick_markerEvent(this, evt);
		});
		
		aryList.push(marker);
	},
	makeAvoid: function (x, y, itemobj) {
		var	coord = new olleh.maps.UTMK(x, y);
		this.makeArrayMarker(coord, undefined, itemobj, this.aryAVOID, ICON_AVOID, TYPE_AVOID);
	},
	makeWard: function (lat, lng, itemobj) {
		var	coord = new olleh.maps.LatLng(lat, lng);
		this.makeArrayMarker(coord, itemobj.mtitle, itemobj, this.aryWARD, ICON_WARD, TYPE_WARD);
	},
	makeWcar: function (lat, lng, itemobj) {
		var	coord = new olleh.maps.LatLng(lat, lng);
		this.makeArrayMarker(coord, itemobj.mtitle, itemobj, this.aryWCAR, ICON_WCAR_I, TYPE_WCAR);
	},
	makeMcctv: function (lat, lng, itemobj) {
		var	coord = new olleh.maps.LatLng(lat, lng);
		var	oicon = itemobj.etype == '2' ? ICON_CCTV_FIRE : ICON_CCTV_NORMAL;
		this.makeArrayMarker(coord, itemobj.mtitle, itemobj, this.aryCCTV, oicon, TYPE_CCTV);
	},
	makeCctvListInAcct: function (bounds) {
		this.actCctvList = new Array();

		for (var i = 0; i < this.aryCCTV.length; i ++) {
			var	marker = this.aryCCTV[i];
			if (bounds.contains(marker.getPosition())) {
				marker.setIcon(this.getIcon(ICON_CCTV_ACCT));
				this.actCctvList.push(marker);
			}
		}
		return this.actCctvList;
	},
	makeCctvListInRect: function (bounds) {
		this.monCctvList = new Array();	// 영역내의 CCTV 목록

		for (var i = 0; i < this.aryCCTV.length; i ++) {
			var	marker = this.aryCCTV[i];
			if (bounds.contains(marker.getPosition())) {
				this.monCctvList.push(marker);
			}
		}
		return this.monCctvList;
	},
	getIcon: function (ico_url) {
		var iconObj = {
			url: ico_url,
			size: const_icon_size,
			anchor: const_icon_anchor
		};
		return iconObj;
	},
	getCctvMarker: function (ukey) {
		var	mkr = null;
		for (var i = 0; i < this.aryCCTV.length; i ++) {
			if (this.aryCCTV[i].obj.mkey == ukey) {
				mkr = this.aryCCTV[i];
				break;
			}
		}
		return mkr;
	},
	getWcarMarker: function(ukey) {
		var	mkr = null;
		for (var i = 0; i < this.aryWCAR.length; i ++) {
			if (this.aryWCAR[i].obj.mkey == ukey) {
				mkr = this.aryWCAR[i];
				break;
			}
		}
		return mkr;
	},
	getWcarList: function() {
		return this.aryWCAR;
	},
	getCctvListInRect: function () {
		return this.monCctvList;
	},
	setWcarMoveData: function (car_id, lat, lng, car_stat_cd, car_stat_name) {
		for (var i = 0; i < this.aryWCAR.length; i ++) {
			if (this.aryWCAR[i].obj.mkey == car_id) {
				var	coord = new olleh.maps.LatLng(lat, lng);
				this.aryWCAR[i].setPosition(coord);
				if (this.aryWCAR[i].obj.car_stat_cd != car_stat_cd) {
					this.aryWCAR[i].obj.car_stat_cd = car_stat_cd;
					this.aryWCAR[i].obj.car_stat_name = car_stat_name;
					if (car_stat_cd == '0930005') {
						this.aryWCAR[i].setIcon(this.getIcon(ICON_WCAR_R));
					}
				}
			}
		}
	},
	removeAvoid: function (ukey) {
		for (var i = 0; i < this.aryAVOID.length; i ++) {
			if (this.aryAVOID[i].obj.mkey == ukey) {
				this.aryAVOID[i].setMap(null);
				this.aryAVOID.splice(i, 1);
				break;
			}
		}
	},
	getAvoidCount: function () {
		return this.aryAVOID.length;
	},
	getAvoidParams: function () {
		var	info = { count: 0, ux: '', uy: '', degree: '' };
		
		info.count = this.aryAVOID.length;
		for (var i = 0; i < this.aryAVOID.length; i ++) {
			var	coord = this.aryAVOID[i].getPosition();
			
			if (info.ux.length > 0)
				info.ux += ',';
			if (info.uy.length > 0)
				info.uy += ',';
			if (info.degree.length > 0)
				info.degree += ',';

			info.ux += Math.round(coord.x);
			info.uy += Math.round(coord.y);
			info.degree += this.aryAVOID[i].obj.etype;
		}
		if (info.degree == '')
			info.degree = '0';
		return info;
	},
	clearCmMarker: function (marker) {
		marker.obj = null;
		marker.setMap(null);
	},
	clearAvoid: function () {
		for (var i = 0; i < this.aryAVOID.length; i ++) {
			this.aryAVOID[i].setMap(null);
		}
		this.aryAVOID = [];
	},
	clearWard: function () {
		for (var i = 0; i < this.aryWARD.length; i ++) {
			this.aryWARD[i].setMap(null);
		}
		this.aryWARD = [];
	},
	clearWcar: function () {
		for (var i = 0; i < this.aryWCAR.length; i ++) {
			this.aryWCAR[i].setMap(null);
		}
		this.aryWCAR = [];
	},
	clearMcctv: function () {
		for (var i = 0; i < this.aryCCTV.length; i ++) {
			this.aryCCTV[i].setMap(null);
		}
		this.aryCCTV = [];
	},
	clearCctvListInAcct: function (bounds) {
		for (var i = 0; i < this.actCctvList.length; i ++) {
			this.actCctvList[i].setIcon(this.getIcon(ICON_CCTV_NORMAL));
		}
		this.actCctvList = [];
	},
	clearAll: function () {
		this.clearWard();	// 출동소방서소 마커 삭제
		this.clearWcar();	// 출동차량 마커 삭제
		this.clearMcctv();	// 재난주변CCTV 마커 삭제
	},
	clear: function () {
	}
};

function getCharCount(str) {
	if (str === undefined)
		return 0;
	if (str == null) {
		return 0;
	}

	var		cnt = 0;
	var		ch;
	
	for (var i = 0; i < str.length; i ++) {
		ch = str.charAt(i);
		if(escape(ch).length > 4) {
			cnt += 2;
		} else {
			cnt += 1;
		}
	}
	
	return cnt;
}
