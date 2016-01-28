<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set value="차량정보 수정" var="sHtmlTitle"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<meta name="robots" content="noindex">
	<title><c:out value="${sHtmlTitle }"></c:out></title>
    
    <c:import url="../include/common_js.jsp"></c:import>
    
    <script>
    
    $(document).ready(function() {

        $("#munufact_dt").datepicker();

        $(".datepicker").each(function() {
            if ($(this).val() != '') {
                var d = new Date($(this).val());
                $(this).datepicker('setDate', d);
            }
            $(this).datepicker("option", "dateFormat", "yy-mm-dd");
        });
    });
    
    function doEditAction() {
    	if($("#car_no").val() == ""){
			alert("차량번호를 입력 해주세요.");
			$("#car_no").focus();
			return;
		}
    	if($("#car_type").val() == ""){
			alert("차량번호 입력후 차량ID조회 버튼을 눌러주세요.");
			$("#car_type").focus();
			return; 
		}
		/* if($("#car_id").val() == ""){
			alert("차량번호 입력후 차량ID조회 버튼을 눌러주세요.");
			$("#car_id").focus();
			return; 
		} */
    	
    	var url = '/cms/firecar/edit_action.do';
		var params = $('#resultDataVO').serialize();
		net_ajax(url, params, function(data) {
			if (data.retCode == const_ret_ok) {
				fn_go_page();
			} else {
				alert(data.retMsg);
			}
		}, onNetCallbackDefaultError);
    }
    
	function doDeleteAction() {
    	if(confirm('삭제하시겠습니까?') == false){
			return;
		}
		
    	var url = "/cms/firecar/delete_action.do";
    	var params = $('#resultDataVO').serialize();
    	net_ajax(url, params, function(data) {
			if (data.retCode == const_del_ok) {
				document.regForm.searchCondition.value = '';
				document.regForm.searchKeyword.value = '';
				document.regForm.pageIndex.value = '1';
				fn_go_page();
			} else {
				alert(data.retMsg);
			}
		}, onNetCallbackDefaultError);
    }
	
    function doCanCel() {
    	location.href	= '/cms/firecar/list.do';
    }
    
    function doSelectIdAction() {
    	var car_no = document.regForm.car_no;
    	
    	if(car_no.value =='') {
    		alert("차량번호를 입력하세요.");
    		car_no.focus();
    		return;
    	}
    	
    	var url = '/cms/firecar/id_action.do'
    	var params = "car_no=" + car_no;
    	
    	net_ajax(url, params, function(data) {
			if (data.retCode == 100) {
				document.regForm.car_id.value = data.car_id;
				document.regForm.car_type.value = data.car_type;
			} 
			if(data.retCode == 101){
				alert("차량번호를 확인하세요.");
			}
		}, onNetCallbackDefaultError);
    }
    
    function fn_go_page() {
    	document.regForm.action = '/cms/firecar/list.do';
		document.regForm.submit();
    }
    </script>
</head>

<body>
<form:form commandName="resultDataVO" name="regForm" method="post">
<div class="pop_back"></div>
    <div id="wrapper">
            <!-- navbar start -->
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <c:import url="../include/top.jsp"></c:import>
            
            <!-- /.navbar-top-links -->
            <div class="navbar-default sidebar" role="navigation">
                <c:import url="../include/left.jsp"></c:import>
            </div>
        </nav>
        <!-- navbar end -->
        
        
        <div id="page-wrapper">
            <div class="container-fluid ">
				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header"><c:out value="${sHtmlTitle }"></c:out></h1>
					</div>
				</div>
                <!-- /.row -->
        <table class="table table-bordered">
            <tr>
                <td class="active text-center" style="vertical-align: middle;">* 차량번호</td>
                <td >
                	<label for="car_no"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" id="car_no" name="car_no" value="<c:out value="${resultDataVO.car_no }"></c:out>" readonly="readonly" />
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">* 차종</td>
                <td style="vertical-align: middle;">
					<%-- <form:select path="car_type" cssClass="form-control" style="display:inline-block;width:140px;float:left;">
						<form:option value="" label="차종" />
						<form:option value="대형" label="소방차(대형)"  />
						<form:option value="중형" label="소방차(중형)" />
						<form:option value="소형" label="소방차(소형)" />
					</form:select> --%>
					<label for="car_type"></label>	
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" id="car_type" name="car_type" value="<c:out value="${resultDataVO.car_type }"></c:out>" readonly="readonly"/>
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">* 차량 ID</td>
                <td style="vertical-align: middle;">
                	<label for="car_id"></label>	
                	<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" id="car_id" name="car_id" value="<c:out value="${resultDataVO.car_id }"></c:out>" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">* 사용상태</td>
                <td >
                	<label for="use_stat1"></label>
                	<form:radiobutton  path="use_stat" value="1"/> &nbsp; 사용 &nbsp; &nbsp; 
                	<label for="use_stat2"></label>
                	<form:radiobutton path="use_stat" value="0"/> &nbsp; 정지 
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">* 현장카메라 보유</td>
                <td >
                	<label for="cam_yn1"></label>	
                	<form:radiobutton path="cam_yn" value="Y"/> &nbsp; 유 &nbsp; &nbsp; 
                	<label for="cam_yn2"></label>
                	<form:radiobutton path="cam_yn" value="N"/> &nbsp; 무 
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">카메라 IP주소</td>
                <td style="vertical-align: middle;">
                	<label for="cam_hostport"></label>	
                	<input class="form-control" style="display:inline-block;width:240px;" type="text" id="cam_hostport" name="cam_hostport" maxlength="90" value='<c:out value="${resultDataVO.cam_hostport }"></c:out>' />
                	&nbsp;&nbsp;입력예) 192.168.0.100:7001
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">CCTV 일련번호</td>
                <td style="vertical-align: middle;">
                	<label for="cctv_seqno"></label>	
                	<input class="form-control" style="display:inline-block;width:240px;" type="text" id="cctv_seqno" name="cctv_seqno" maxlength="20" value='<c:out value="${resultDataVO.cctv_seqno }"></c:out>' />
                </td>
            </tr>
             <tr>
				<td class="active text-center" style="vertical-align: middle;">카메라인증정보</td>
                <td style="vertical-align: middle;">
                	<label for="cam_auth_data"></label>	
                	<input class="form-control" style="display:inline-block;width:240px;" type="text" id="cam_auth_data" name="cam_auth_data" maxlength="50" value="<c:out value="${resultDataVO.cam_auth_data }"></c:out>" />
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">카메라접속정보</td>
                <td style="vertical-align: middle;">
                	<label for="cam_url"></label>	
                	<input class="form-control" style="display:inline-block;width:240px;" type="text" id="cam_url" name="cam_url" maxlength="250" value="<c:out value="${resultDataVO.cam_url }"></c:out>" />
                	&nbsp;&nbsp;입력예) rtsp://192.168.0.100/StdCh2
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">카메라 모델명</td>
                <td style="vertical-align: middle;">
                	<label for="model_name"></label>
                	<input class="form-control" style="display:inline-block;width:240px;  float:left;" maxlength="45" type="text" id="model_name" name="model_name" value="<c:out value="${resultDataVO.model_name }"></c:out>"/>
                </td>
            </tr>
            <tr style="display: none;">
				<td class="active text-center" style="vertical-align: middle;">카메라 모델번호</td>
                <td style="vertical-align: middle;">
                	<label for="model_number"></label>
                	<input class="form-control" style="display:inline-block;width:240px;  float:left;" maxlength="45" type="text" id="model_number" name="model_number" value="<c:out value="${resultDataVO.model_number }"></c:out>"/>
                </td>
            </tr>
            <tr style="display: none;">
				<td class="active text-center" style="vertical-align: middle;">카메라 시리얼번호</td>
                <td style="vertical-align: middle;">
                	<label for="serial_number"></label>
                	<input class="form-control" style="display:inline-block;width:240px;  float:left;" maxlength="45" type="text" id="serial_number" name="serial_number" value="<c:out value="${resultDataVO.serial_number }"></c:out>"/>
                </td>
            </tr>
            <tr style="display: none;">
				<td class="active text-center" style="vertical-align: middle;">카메라 제조일</td>
                <td style="vertical-align: middle;">
                	<label for="munufact_dt"></label>
                	<input class="datepicker form-control" style="width:120px; display:inline-block" type="text" id="munufact_dt" name="munufact_dt" value="<c:out value="${resultDataVO.munufact_dt }"></c:out>"/>
                </td>
            </tr>
        </table>
        <div style="text-align: center;">
            <a href="javascript:" class="btn btn-default" style="margin-right:10px;" onclick="doEditAction();">수정</a>
            <a href="javascript:" class="btn btn-default" style="margin-right:10px;" onclick="doCanCel();">취소</a>
            <a href="javascript:" class="btn btn-success" style="margin-right:10px;" onclick="doDeleteAction();">삭제</a>
        </div>
            <!-- /.container-fluid -->
            <br><br><br>
        </div>
        <!-- /#page-wrapper -->
    </div>
    </div>
    <!-- /#wrapper -->
    <!-- 검색조건 유지 -->
	<input type="hidden" name="searchCondition" value="<c:out value='${pageVO.searchCondition}'/>"/>
	<input type="hidden" name="searchKeyword" value="<c:out value='${pageVO.searchKeyword}'/>"/>
	<input type="hidden" name="pageIndex" value="<c:out value='${pageVO.pageIndex}'/>"/>
</form:form>
</body>
</html>
    