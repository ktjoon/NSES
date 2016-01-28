<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set value="화재감시 CCTV 수정" var="sHtmlTitle"></c:set>
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
    function doEditAction() {
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
    	
    	if($("#prev_name").val() == $("#cctv_name").val()) {
    		var url = '/cms/firecctv/edit_action.do';
		    var params = $('#pageVO').serialize();
		    net_ajax(url, params, function(data) {
		    	if (data.retCode == const_ret_ok) {
		    		fn_go_page();
		    	} else {
		    		alert(data.retMsg);
		   		}
		   	}, onNetCallbackDefaultError);
    	} else {
    		var url = '/cms/firecctv/name_action.do';
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
   					url = '/cms/firecctv/edit_action.do';
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
    }
    
	function doDeleteAction() {
		if(confirm('삭제하시겠습니까?') == false){
			return;
		}
    	var url = "/cms/firecctv/delete_action.do";
    	var params = $('#pageVO').serialize();
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
    	location.href	= '/cms/firecctv/list.do';
    }
    
    function fn_go_page() {
    	document.regForm.action = '/cms/firecctv/list.do';
		document.regForm.submit();
    }
    </script>
</head>

<body>
<form:form commandName="pageVO" name="regForm" method="post">
<input type="hidden" name="cctv_guid" value="<c:out value="${resultDataVO.cctv_guid }"></c:out>" />
<input type="hidden" id="prev_name" name="prev_name" value="<c:out value="${resultDataVO.cctv_name }"></c:out>" />
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
                <td class="active text-center" style="vertical-align: middle;">* CCTV 이름</td>
                <td >
                	<label for="cctv_name"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" id="cctv_name" name="cctv_name" maxlength="200"  value="<c:out value="${resultDataVO.cctv_name }"></c:out>" />        
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">* CCTV 설치 좌표</td>
                <td style="vertical-align: middle;">
                    &nbsp;위도(Lat)&nbsp;
                    <label for="cctv_lat"></label>
					<input class="form-control" style="display:inline-block;width:140px;" type="text" id="cctv_lat" maxlength="50" name="cctv_lat" value="<c:out value="${resultDataVO.cctv_lat }"></c:out>"/>
					&nbsp;경도(Lon)&nbsp;
					<label for="cctv_lng"></label>
					<input class="form-control" style="display:inline-block;width:140px;" type="text" id="cctv_lng" maxlength="50" name="cctv_lng" value="<c:out value="${resultDataVO.cctv_lng }"></c:out>"/>
                </td>
            </tr>
            <tr>
				<td class="active text-center" style="vertical-align: middle;">* 카메라 IP주소</td>
                <td style="vertical-align: middle;">
                	<label for="cctv_hostport"></label>
                	<input class="form-control" style="display:inline-block;width:240px;" type="text" id="cctv_hostport" name="cctv_hostport" maxlength="90" value='<c:out value="${resultDataVO.cctv_hostport }"></c:out>' />
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
                	<input class="form-control" style="display:inline-block;width:350px;" type="text" id="cam_url" name="cam_url" maxlength="250" value="<c:out value="${resultDataVO.cam_url }"></c:out>" />
                	&nbsp;&nbsp;입력예) rtsp://192.168.0.100/StdCh3
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">감시타입</td>
                <td >
                	<label for="cctv_kind"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" id="cctv_kind" name="cctv_kind" maxlength="30" value="<c:out value="${resultDataVO.cctv_kind }"></c:out>" />        
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">CCTV 모델명</td>
                <td >
                	<label for="cctv_model"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" id="cctv_model" name="cctv_model" maxlength="200" value="<c:out value="${resultDataVO.cctv_model }"></c:out>" />        
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">전화번호</td>
                <td >
                	<label for="tel_no"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" id="tel_no" name="tel_no" maxlength="200" value="<c:out value="${resultDataVO.tel_no }"></c:out>" />        
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
    