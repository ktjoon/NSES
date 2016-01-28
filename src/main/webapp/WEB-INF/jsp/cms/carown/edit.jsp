<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set value="차주정보 수정" var="sHtmlTitle"></c:set>
<c:set value="${fn:split(carOwnerVO.tel_no, '-') }" var="aryOwnTelno"></c:set>

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
    function fn_go_page() {
    	document.editForm.action = '/cms/carown/list.do';
		document.editForm.submit();
    }
    
    function doEditAction() {
    	if($('#own_car_no').val() == ''){
			alert('차량번호를 입력 해주세요.');
			$('#own_car_no').focus();
			return;
		}else if($('#own_name').val() == ''){
			alert('소유주를 입력해주세요.');
			$('#own_name').focus();
			return; 
		}

    	if ($('#own_tel02').val().length > 0 || $('#own_tel03').val() > 0) {
    		if ($('#own_tel01').val() == '') {
    			alert('첫번째 전화번호를 선택하세요.');
    			$('#own_tel01').focus();
    			return; 
    		}
    		
   			if (!checkNumber($("#own_tel02").val())) {
   				$("#own_tel02").focus();
   	    		return;
   			} else if (!checkNumber($("#own_tel03").val())) {
   				$("#own_tel03").focus();
   				return;
    		}
    	}
    	
    	
    	var url = '/cms/carown/edit_action.do';
		var params = $('#carOwnerVO').serialize();
		net_ajax(url, params, function(data) {
			if (data.retCode == const_ret_ok) {
				fn_go_page();
			} else {
				alert(data.retMsg);
			}
		}, onNetCallbackDefaultError);
    }
    
    function doUserDelAction() {
    	if (confirm('삭제하시겠습니까?') == false) {
    		return;
    	}
    	
   		var ukey	= $('#own_car_no').val();
   		
   		var url = '/cms/carown/del_action.do';
   		var params = 'own_car_no=' + ukey;
   		net_ajax(url, params, function(data) {
   			if (data.retCode == const_ret_ok) {
   				location.href	= '/cms/carown/list.do';
   			} else {
   				alert(data.retMsg);
   			}
   		}, onNetCallbackDefaultError);
    }
    </script>
</head>

<body>
<div class="pop_back"></div>
<form:form commandName="carOwnerVO" name="editForm" method="post">
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
                <td class="active text-center" style="vertical-align: middle;">* 차량 번호</td>
                <td >
                	<label for="own_car_no"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" id="own_car_no" name="own_car_no" value="<c:out value="${carOwnerVO.own_car_no }"></c:out>" readonly />        
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">* 소유주</td>
                <td >
                	<label for="own_name"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" id="own_name" name="own_name" value="<c:out value="${carOwnerVO.own_name }"></c:out>" maxlength="20" />        
                </td>
            </tr>
			<tr>
				<td class="active text-center" style="vertical-align: middle;">연락처</td>
                <td style="vertical-align: middle;">
                	<label for="own_tel01"></label>
                	<select id="own_tel01" name="own_tel01" class="form-control" style="display:inline-block;width:80px;" >
                            <option value="" selected='selected'>전체</option>
                            <option value="010" ${aryOwnTelno[0] == '010' ? 'selected' : '' }>010</option>
                            <option value="011" ${aryOwnTelno[0] == '011' ? 'selected' : '' }>011</option>
                            <option value="019" ${aryOwnTelno[0] == '019' ? 'selected' : '' }>019</option>
                    </select>
                    &nbsp;-&nbsp;
                    <label for="own_tel02"></label>
					<input class="form-control" style="display:inline-block;width:80px;" type="text" id="own_tel02" name="own_tel02" maxlength="4" value='<c:out value="${aryOwnTelno[1] }"></c:out>' />
					&nbsp;-&nbsp;
					<label for="own_tel03"></label>
					<input class="form-control" style="display:inline-block;width:80px;" type="text" id="own_tel03" name="own_tel03" maxlength="4" value='<c:out value="${aryOwnTelno[2] }"></c:out>' />
                </td>
            </tr>
        </table>
        <div style="text-align: center;">
            <a href="javascript:;" class="btn btn-default" style="margin-right:10px;" onclick="doEditAction();">수정</a>
            <a href="javascript:;" class="btn btn-default" style="margin-right:10px;" onclick="fn_go_page();">취소</a>
            <a href="javascript:;" class="btn btn-success" style="margin-right:10px;" onclick="doUserDelAction();">삭제</a>
			
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
    