<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set value="CCTV 등록" var="sHtmlTitle"></c:set>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">        <meta name="robots" content="noindex">
    <title><c:out value="${sHtmlTitle }"></c:out></title>
    
    <c:import url="../include/common_js.jsp"></c:import>
    
    <script>
    function doCctvRegAction() {
    	if($("#cctv_name").val() == ""){
			alert("CCTV이름을 입력 해주세요.");
			$("#cctv_name").focus();
			return;
		}else if($("#cctv_lat").val() == ""){
			alert("위도를 입력해주세요.");
			$("#cctv_lat").focus();
			return; 
		}else if($("#cctv_lng").val() == ""){
			alert("경도을 입력해주세요.");
			$("#cctv_lng").focus();
			return; 
		}else if($("#cctv_hostport").val() == ""){
			alert("IP를 입력해주세요.");
			$("#cctv_hostport").focus();
			return; 
		}
		
    	var url = '/cms/cctv/name_action.do';
    	var params = "cctv_name=" + $("#cctv_name").val();

    	net_ajax(url, params, function(data) {
			if (data.retCode == const_ret_ok) {
				if (data.dataCount > 0) {
					alert('동일한 CCTV이름이 존재합니다.');
					return;
					/* if(confirm('같은 CCTV이름이 존재합니다. \n 진행하시겠습니까?') == false){
						return;
					} */
				}
				url = '/cms/cctv/reg_action.do';
			    params = $('#pageVO').serialize();
			    net_ajax(url, params, function(data) {
			    	if (data.retCode == const_ret_ok) {
			    		fn_go_page();
			    	} else {
			    		alert(data.retMsg);
			   		}
			   	}, onNetCallbackDefaultError);
			} 
		}, onNetCallbackDefaultError);
    	
    }
    
    function doCanCel() {
    	location.href	= '/cms/cctv/list.do';
    }
    
    function fn_go_page() {
    	document.regForm.action = '/cms/cctv/list.do';
		document.regForm.submit();
    }
    
    function openExcelPop() {
    	$("#excelUp").show();
    }
    
    function uploadExcel() {
    	if (jQuery.trim(jQuery("#excel").val())=="") {
			alert("업로드할 엑셀을 선택하여주세요.");
		    return;
		};
		if (jQuery("#excel").val().match(/\.(xlsx|xls)$/i)){ 
			var form = $("#pageVO");
			form.attr({action:"/cms/cctv/excel_upload.do",method:"post"});
			form.submit(); 
		}else{
			alert("엑섹 파일을 올려주세요.");
			return;
		};
    }
    
    </script>
    
    <c:if test = "${uploadYN eq 'Y'}">
		<script>
			location.href = '/cms/cctv/list.do';
		</script>
	</c:if>
	
</head>

<body>
<div class="pop_back"></div>
<form:form commandName="pageVO" name="regForm" method="post" enctype="multipart/form-data">
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
                <td class="active text-center" style="vertical-align: middle;">* CCTV 이름</td>
                <td >
                	<label for="cctv_name"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" maxlength="200" id="cctv_name" name="cctv_name" value="" />        
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">* CCTV 설치 좌표</td>
                <td style="vertical-align: middle;">
                    &nbsp;위도(Lat)&nbsp;
                    <label for="cctv_lat"></label>
					<input class="form-control" style="display:inline-block;width:140px;" type="text" maxlength="50" id="cctv_lat" name="cctv_lat"/>
					&nbsp;경도(Lon)&nbsp;
					<label for="cctv_lng"></label>
					<input class="form-control" style="display:inline-block;width:140px;" type="text" maxlength="50" id="cctv_lng" name="cctv_lng"/>
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">* 카메라 IP주소</td>
                <td style="vertical-align: middle;">
                	<label for="cctv_hostport"></label>
                	<input class="form-control" style="display:inline-block;width:240px;" type="text" id="cctv_hostport" name="cctv_hostport" maxlength="90" />
                	&nbsp;&nbsp;입력예) 192.168.0.100:7001
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">CCTV 일련번호</td>
                <td style="vertical-align: middle;">
                	<label for="cctv_seqno"></label>
                	<input class="form-control" style="display:inline-block;width:240px;" type="text" id="cctv_seqno" name="cctv_seqno" maxlength="20" />
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">카메라인증정보</td>
                <td style="vertical-align: middle;">
                	<label for="cam_auth_data"></label>
                	<input class="form-control" style="display:inline-block;width:240px;" type="text" id="cam_auth_data" name="cam_auth_data" maxlength="50" />
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">카메라접속정보</td>
                <td style="vertical-align: middle;">
                	<label for="cam_url"></label>
                	<input class="form-control" style="display:inline-block;width:240px;" type="text" id="cam_url" name="cam_url" maxlength="250" />
                	&nbsp;&nbsp;입력예) rtsp://192.168.0.100/StdCh3
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">감시타입</td>
                <td >
                	<label for="cctv_kind"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" maxlength="30" id="cctv_kind" name="cctv_kind" value="" />        
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">CCTV 모델명</td>
                <td >
                	<label for="cctv_model"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" maxlength="200" id="cctv_model" name="cctv_model" value="" />        
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">전화번호</td>
                <td >
                	<label for="tel_no"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" maxlength="200" id="tel_no" name="tel_no" value="" />        
                </td>
            </tr>
            <tr id="excelUp" style="display: none;">
                <td class="active text-center" style="vertical-align: middle;">엑셀업로드</td>
                <td >
                	<label for="excel"></label>
					<input class="form-control" id="excel" name="excel" style="display:inline-block;width:200px;  float:left;" type="file" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-default" style="margin-right:10px;" onclick="uploadExcel();">Excel전송</a>
                </td>
            </tr>
        </table>
        <div style="text-align: center;">
        	<a href="javascript:" class="btn btn-default" style="margin-right:10px;" id="excelOpen" onclick="openExcelPop();">Excel등록</a>
            <a href="javascript:" class="btn btn-default" style="margin-right:10px;" id="cctvadd" onclick="doCctvRegAction();">등록</a>
            <a href="javascript:" class="btn btn-default" style="margin-right:10px;" id="addCancel" onclick="doCanCel()">취소</a>
        </div>
            <!-- /.container-fluid -->
            <br><br><br>
        </div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
    <!-- 검색조건 유지 -->
    </form:form>
</body>
</html>
    