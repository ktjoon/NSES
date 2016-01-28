<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set value="119 길안내 시스템 관리자" var="sHtmlTitle"></c:set>
<c:set value="${paginationInfo.totalRecordCount }" var="nTotalRecordCnt"></c:set>
<c:set value="${paginationInfo.currentPageNo }" var="nCurrPageNo"></c:set>
<c:set value="${paginationInfo.recordCountPerPage }" var="nRecordCntPerPage"></c:set>

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
	/* 검색 function */
	function onCheckSearch() {
		var search_keyword	= document.listForm.searchKeyword;
		var search_type		= document.listForm.searchCondition;
		
		if (search_type.value == '') {
			alert('검색 타입을 선택하세요.');
			search_type.focus();
			return;
		}
		if (search_keyword.value == '') {
			alert('검색어를 입력하세요.');
			search_keyword.focus();
			return;
		}
		
		document.listForm.action = '/cms/carown/list.do';
		document.listForm.submit();
	}
	
    </script>
</head>

<body>
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
        <!-- /#page-wrapper -->
            <div class="container-fluid ">
                <!-- Page Heading -->
                <div class="row">
                    
                    
                </div>
                <!-- /.row -->
   		
            </div>
            <!-- /.container-fluid -->
            <br><br><br>
        </div>
        <!-- /#page-wrapper -->
	</div>
    <!-- /#wrapper -->
</body>
</html>
    