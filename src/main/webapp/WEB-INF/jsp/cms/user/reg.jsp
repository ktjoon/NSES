<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set value="사용자 등록" var="sHtmlTitle"></c:set>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">        <meta name="robots" content="noindex">
    <title><c:out value="${sHtmlTitle }"></c:out></title>
    
    <c:import url="../include/common_js.jsp"></c:import>
    
    <script>
    function fn_go_page() {
    	document.regForm.action = '/cms/user/list.do';
		document.regForm.submit();
    }
    
    function doUserRegAction() {
    	if ($('#user_id').val() == '') {
			alert('아이디를 입력해주세요.');
			$('#user_id').focus();
			return;
		} else if($('#user_pwd').val() == '') {
			alert('비밀번호를 입력해주세요.');
			$('#user_pwd').focus();
			return; 
		} else if ($('#user_repwd').val() == '') {
			alert('비밀번호를 다시 입력하여 주세요.');
			$('#user_repwd').focus();
			return;
		} else if ($('#user_pwd').val() != $('#user_repwd').val()) {
			alert('비밀번호가 일치하지 않습니다.');
			return;
		} else if($('#user_name').val() == '') {
			alert('이름을 입력해주세요.');
			$('#user_name').focus();
			return; 
		}
    	
    	if ($('#user_email').val().length > 0) {
    		if (!checkEmail($("#user_email").val())) {
        		alert('잘못된 이메일 형식입니다.');
        		$("#user_email").focus();
        		return;
    		}
    	}
		
    	if(!checkPass($("#user_pwd").val())) {
			$("#user_pwd").focus();
			alert("비밀번호는 문자, 숫자, 특수문자의 조합으로 8 ~ 10 자리로 입력해주세요.");		
			return;
		}
    	
    	if(!checkId($("#user_id").val())) {
			$("#user_id").focus();
			alert("아이디는 영문, 숫자 조합으로 8 ~ 20 자리로 입력해주세요.");		
			return;
		}
    	
    	if ($('#user_tel02').val().length > 0 || $('#user_tel03').val() > 0) {
    		if ($('#user_tel01').val() == '') {
    			alert('첫번째 전화번호를 선택하세요.');
    			$('#user_tel01').focus();
    			return; 
    		}
    		
   			if (!checkNumber($("#user_tel02").val())) {
   				$("#user_tel02").focus();
   	    		return;
   			} else if (!checkNumber($("#user_tel03").val())) {
   				$("#user_tel03").focus();
   				return;
    		}
   			
    	}
    	
    	var url = '/cms/user/reg_action.do';
		var params = $('#userInfoVO').serialize();
		net_ajax(url, params, function(data) {
			if (data.retCode == const_ret_ok) {
				fn_go_page();
			} else {
				alert(data.retMsg);
			}
		}, onNetCallbackDefaultError);
    }
    </script>
</head>

<body>
<div class="pop_back"></div>
<form:form commandName="userInfoVO" name="regForm" method="post">
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
                <td class="active text-center" style="vertical-align: middle;">* 아이디</td>
                <td >
                	<label for="user_id"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" id="user_id" name="user_id" maxlength="20" value="" />        
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">* 비밀번호</td>
                <td >
                	<label for="user_pwd"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="password" id="user_pwd" name="user_pwd" maxlength="50" value="" />        
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">* 비밀번호 확인</td>
                <td >
                	<label for="user_repwd"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="password" id="user_repwd" name="user_repwd" maxlength="50" value="" />        
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">* 이름</td>
                <td >
                	<label for="user_name"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="text" id="user_name" name="user_name" maxlength="100" value="" />        
                </td>
            </tr>
            <tr>
                <td class="active text-center" style="vertical-align: middle;">이메일</td>
                <td >
                	<label for="user_email"></label>
					<input class="form-control" style="display:inline-block;width:200px;  float:left;" type="email" id="user_email" name="user_email" maxlength="120" value="" />        
                </td>
			</tr>
			<tr>
				<td class="active text-center" style="vertical-align: middle;">전화번호</td>
                <td style="vertical-align: middle;">
                	<label for="user_tel01"></label>
                	<select id="user_tel01" name="user_tel01" class="form-control" style="display:inline-block;width:80px;" >
                            <option value="" selected='selected'>전체</option>
                            <option value="010" >010</option>
                            <option value="011" >011</option>
                            <option value="019" >019</option>
                    </select>
                    &nbsp;-&nbsp;
                    <label for="user_tel02"></label>
					<input class="form-control" style="display:inline-block;width:80px;" type="text" id="user_tel02" name="user_tel02" maxlength="4" />
					&nbsp;-&nbsp;
					<label for="user_tel03"></label>
					<input class="form-control" style="display:inline-block;width:80px;" type="text" id="user_tel03" name="user_tel03" maxlength="4"/>
                </td>
            </tr>
        </table>
        
        <div style="text-align: center;">
            <a href="javascript:;" class="btn btn-default" style="margin-right:10px;" onclick="doUserRegAction();">등록</a>
            <a href="javascript:;" class="btn btn-default" style="margin-right:10px;" onclick="fn_go_page()">취소</a>
        </div>
            <!-- /.container-fluid -->
            <br><br><br>
        </div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
    <!-- 검색조건 유지 -->
	<input type="hidden" name="searchCondition" value="<c:out value='${userInfoVO.searchCondition}'/>"/>
	<input type="hidden" name="searchKeyword" value="<c:out value='${userInfoVO.searchKeyword}'/>"/>
	<input type="hidden" name="pageIndex" value="<c:out value='${userInfoVO.pageIndex}'/>"/>
    </form:form>
</body>
</html>
    