<%@page import="nses.common.utils.ComStr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String	sCctvKey = ComStr.toStr(request.getParameter("cctv_key"));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>CCTV 정보전송</title>
<meta charset="utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

<style type="text/css">

.pop-sendcourse {
    width: 245px;
    height: 110px;
}

.pop-sendcourse .pop-content {
    width: 100%;
    height: 110px;
    text-align: center;
    vertical-align: middle;
    background-color: #50536A;
    color: white;
    margin: 0 auto;
}

.pop-sendcourse .pop-content .desc {
    font-size: 13px;
    line-height: 20px;
}

.pop-sendcourse .pop-content .carlist {
    width: 217px;
    height: 39px;
    line-height: 40px;
    border-radius: 5px;
    position: relative;
    color: black;
    margin: 5px 16px;
    font-size: 12px;
}

.pop-sendcourse .pop-content > .bg02btn {
    margin: 0 0 0 14px;
}

.bg02btn {
    width: 217px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    vertical-align: middle;
    font-size: 11px;
    font-weight: bold;
    color: black;
    cursor: pointer;
    background: url("./images/btn_bg02.png");
}

.bg02btn:active {
    background: url("./images/btn_bg02_on.png");
}

</style>

<script src="./js/libs/jquery.1.10.js"></script>
<script src="./js/libs/jquery-ui-1.11.2.js"></script>
<script src="./js/utils/com_codes.js"></script>
<script src="./js/utils/com_network.js"></script>
<script src="./js/utils/com_utils.js"></script>

<script type="text/javascript">

var	g_cctv_key = '<%=sCctvKey %>';

function closeWindow() {
	window.close();
}
function sendCarCctvMsg() {
	var car_id		= $('#cbo_carlist').val();
	var car_no		= '';
	
	
	if (car_id == '') {
		alert('전송할 차량을 선택하세요.');
		return;
	}

	// 출동차량에서 차량번호 추출
	var wcar_marker	= opener.smap.getWcarMarker(car_id);
	
	if (wcar_marker != null) {
		car_no	= wcar_marker.obj.car_no;
	}
	
	if (car_no == '') {
		alert('선택한 차량의 차량번호가 존재하지 않습니다.');
		return;
	}
	
	var cctv_key		= g_cctv_key;
	var dsr_seq			= opener.getCurrAcctSeq();
	var marker			= opener.smap.getCctvMarker(cctv_key);
	var cctv_type		= null;
	var msg_contents	= null;
	
	if (marker == null) {
		cctv_type		= '3';
		msg_contents	= '선착대 제공 현장 영상정보 전송';
	} else {
		cctv_type		= marker.obj.etype;
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
		cctv_dbkey: cctv_key
	};
	
	net_ajax(url, params, function (data) {
		if (data.retCode == const_ret_ok) {
			alert('전송되었습니다.');
			closeWindow();
		} else {
			alert(data.retMsg);
		}
	}, onNetCallbackDefaultError);
}

$(document).ready(function() {
	
	var aryWcar	= opener.smap.getWcarList();
	var sHtml	= '<option value="">차량선택</option>';
	var marker	= opener.smap.getCctvMarker(g_cctv_key);

	for (var i = 0; i < aryWcar.length; i ++) {
		if (marker == null) {
			// 차량용 카메라를 선택한 경우
			if (aryWcar[i].obj.car_id == g_cctv_key) {
				continue;
			}
		}
		
		var sText	= '';
		if (aryWcar[i].obj.cam_yn == 'Y') {
			sText	= aryWcar[i].obj.car_no + ' - ' + aryWcar[i].obj.car_type_name;
			sHtml	+= '<option value="' + aryWcar[i].obj.car_id +'">' + sText + '</option>';
		}
	}

	$('#cbo_carlist').html(sHtml);
});

</script>
</head>

<body style="background: #50536A">
    <!-- cctv 팝업 클래스 : pop-cctv -->
    <div class="pop-sendcourse lp">
        <div class="pop-content">
            <div class="desc">
                차량을 선택 후 <span style="color:orange">전송</span>하여 주세요.
            </div>
            <div class="carlist">
                <select name="cbo_carlist" id="cbo_carlist" style="width:215px; font-size:16px;" tabindex="1" >
                    <option value="">차량선택</option>
                </select>
                <input id="cctv_key" type="hidden" value="" />
            </div>
            <div tabindex="2" class="bg02btn" onclick="sendCarCctvMsg();">CCTV정보 전송</div>
        </div>
    </div>
</body>
</html>
