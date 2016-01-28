<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>장애차량제거</title>
<meta charset="utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

<style type="text/css">

.body {
    overflow: hidden;
}

.pop-obstcardetail {
    width: 534px;
    height: 422px;
    float: left;
    border: 1px solid #6F6F77;
}

.pop-obstcardetail .pop-content {
    width: 100%;
    height: 408px;
    text-align: center;
    vertical-align: middle;
    background-color: #50536A;
    color: white;
    margin: 0 auto;
}

.pop-obstcardetail .pop-content .car-detail {
    margin: 10px 0;
    padding: 0 14px;
    height: 88px;
    border-bottom: 1px solid #6F6F77;
}

.pop-obstcardetail .pop-content .car-detail .car-info {
    float: left;
    text-align: left;
    font-size: 11px;
    line-height: 20px;
    padding: 10px 0 0 0;
}

.pop-obstcardetail .pop-content .car-detail .car-btns {
    float: right;
    width: 260px;
    margin: 5px 0 0 0;
}

.pop-obstcardetail .pop-content .car-detail .car-btns div {
    margin: 0 0 3px 0;
    float: left;
}

.pop-obstcardetail .pop-content .camera-list {
    width: 534px;
    height: 310px;
    overflow-y: scroll;
    overflow-x: hidden;
}

.pop-obstcardetail .pop-content .camera-list .cam {
    width: 490px;
    height: 100px;
    padding: 14px;
    opacity: 0.8;
}

.pop-obstcardetail .pop-content .camera-list .main {
    border-bottom: 1px solid #6F6F77;
    opacity: 1;
}

.pop-obstcardetail .pop-content .camera-list .cam .camera {
    width: 125px;
    height: 97px;
    background: black;
    float: left;
}
.pop-obstcardetail .pop-content .camera-list .cam .camera-info {
    width: 230px;
    margin: 10px 15px;
    font-size: 11px;
    line-height: 14px;
    text-align: left;
    vertical-align: middle;
    float: left;
}

.pop-obstcardetail .pop-content .camera-list .cam .camera-info span.move{
    margin: 0 0 0 5px;
    padding: 3px 8px;
    border: 1px solid #ABABAB;
    border-radius: 5px;
    cursor: pointer;
    float: right;
}

.pop-obstcardetail .pop-content .camera-list .cam .mos {
    width: 105px;
    height: 100px;
    float:right;
}

.pop-obstcardetail .pop-content .camera-list .cam .mos .carimg {
    width:105px;
    height: 62px;
    background: black;
    margin: 0 0 5px 0;
}

.pop-obstcardetail .pop-content .camera-list .cam .mos .carimg {
    width:105px;
    height: 62px;
    background: black;
    margin: 0 0 5px 0;
}

.pop-obstcardetail .pop-content .camera-list .cam .mos .bg06btn {
}

.pop-mosaic {
    width: 506px;
    height: 422px;
    float: left;
    border: 1px solid #6F6F77;
}

.pop-mosaic .pop-content {
    width: 100%;
    height: 408px;
    text-align: center;
    vertical-align: middle;
    background-color: #50536A;
    color: white;
    margin: 0 auto;
}

.pop-mosaic .pop-content .mosimg {
    margin: 14px 14px 5px 14px;
    width: 480px;
    height: 360px;
    border: 1px solid white;
}

.pop-mosaic .pop-content > .bg01btn {
    float: left;
}


.bg01btn {
    width: 105px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    vertical-align: middle;
    font-size: 11px;
    font-weight: bold;
    color: black;
    cursor: pointer;
    background: url("./images/btn_bg01.png");
}

.bg01btn:active {
    background: url("./images/btn_bg01_on.png");
}

.bg02btn {
    width: 219px;
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

.bg03btn {
    width: 69px;
    height: 34px;
    line-height: 34px;
    text-align: center;
    vertical-align: middle;
    font-size: 11px;
    font-weight: bold;
    color: black;
    cursor: pointer;
    background: url("./images/btn_bg03.png");
}

.bg03btn:active {
    background: url("./images/btn_bg03_on.png");
}

.bg05btn {
    width: 130px;
    height: 32px;
    line-height: 32px;
    text-align: center;
    vertical-align: middle;
    font-size: 11px;
    font-weight: bold;
    color: black;
    cursor: pointer;
    background: url("./images/btn_bg05.png");
}

.bg05btn:active {
    background: url("./images/btn_bg05_on.png");
}

.bg06btn {
    width: 105px;
    height: 35px;
    line-height: 35px;
    text-align: center;
    vertical-align: middle;
    font-size: 11px;
    font-weight: bold;
    color: black;
    cursor: pointer;
    background: url("./images/btn_bg06.png");
}

.bg06btn:active {
    background: url("./images/btn_bg06_on.png");
}

.bg07btn {
    width: 85px;
    height: 43px;
    line-height: 43px;
    text-align: center;
    vertical-align: middle;
    font-size: 11px;
    font-weight: bold;
    color: black;
    cursor: pointer;
    background: url("./images/btn_bg07.png");
}

.bg07btn:active {
    background: url("./images/btn_bg07_on.png");
}

</style>

<script src="./js/libs/jquery.1.10.js"></script>
<script src="./js/libs/jquery-ui-1.11.2.js"></script>
<script src="./js/utils/com_codes.js"></script>
<script src="./js/utils/com_network.js"></script>
<script src="./js/utils/com_utils.js"></script>

<script src="./js/recog_car.js"></script>

<script type="text/javascript">

function recogStart() {
	if (getRecogWidth() <= 0 && getRecogHeight() <= 0) {
		//copyToDest();
		alert('차량인식할 영역을 선택하여 주십시오.');
		return;
	}
	doAction_carnoRecognize(true);
	
	$('.pop-obstcardetail').find('#cam_recog_msg').hide();
}
function closeWindow() {
	window.close();
}

$(document).ready(function() {
	var	$pop = $('.pop-mosaic');
	
	initCanvas($pop);
	
	//initRecogCar(smap.getAcctCurSel(), recogMarker);
	g_currRecogMarker	= opener.g_currRecogMarker;
	g_currAcctSeq		= opener.getCurrAcctSeq();
	
	doAction_cctvCapture();

	//
	$pop = $('.pop-obstcardetail');
	
	disableOwnerCarnoEdit($pop);

	disableSearchBtn($pop);
	disableSaveBtn($pop);
	disableObstCar($pop);
	disableSendMsg($pop);
	
	$pop.find('#cam_recog_msg').show();
	$pop.find('#cam_main').hide();
	$pop.find('.sublist').html('');
});

</script>

</head>
<body style="background: #50536A">
    <!-- 모자이크 처리 팝업 클래스-->
    <div class="pop-mosaic">
        <div class="pop-content">
            <div class="mosimg">
                <canvas id="mst_canvas" width="480" height="360"></canvas>
                <canvas id="dst_canvas" style="display: none;"></canvas>
                <canvas id="moc_canvas" style="display: none;"></canvas>
            </div>
            <div class="center">
                <div class="bg01btn" style="display:inline-block;" onclick="recogStart()">차량번호인식</div>
                <div class="bg01btn" style="display:inline-block;" onclick="closeWindow()" >취소</div>
                <input id="cctv_key" type="hidden" value="" />
            </div>
        </div>
    </div>

    <div class="pop-obstcardetail">
        <div class="pop-content">
            <div class="car-detail">
                <div class="car-info">
                        <b>차량번호 : <input id="recog_own_car_no" type="text" style="width:100px; text-align:center; border:none; border-radius:5px;" maxlength="20"></b><br />
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

                        <div class="bg05btn"         style="display: none;" id="btn_mgr_obstcar" onclick="onClick_setRecogData($(this).parent().parent().parent(), '2')">단속 처리</div>
                        <div class="bg05btn sendmsg" style="display: none;" id="btn_mgr_sendmsg" onclick="onClick_setRecogData($(this).parent().parent().parent(), '3')">메시지 전송</div>
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
                <div id="cam_recog_msg" class="cam main" style="display: none;">
                	<div>장애차량 제거 업무 순서</div>
                	<div>&nbsp;</div>
                	<div>1. 차량번호 인식</div>
                	<div>2. 차량번호 검색</div>
                	<div>3. 차량번호 적용</div>
                </div>
                <div class="sublist">
                    <div class="cam">
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
                        </div>
                    </div>
                    <div class="cam">
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
                        </div>
                    </div>
                    <div class="cam">
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
                        </div>
                    </div>
                    <div class="cam">
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
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
