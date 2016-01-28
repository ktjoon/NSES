<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set value="단속정보 목록" var="sHtmlTitle"></c:set>
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
    function onClickTrans(dkey, ukey) {
    	var f	= document.listForm;	
    	
    	f.dsr_seq.value	= dkey;
   		f.own_car_no.value	= ukey;
    	f.action		= '/cms/recog/edit.do';
    	f.submit();
    }
    
    function goList() {
    	var	f = document.getElementById('pageVO');
    	
    	f.searchKeyword.value		= '';
		f.searchCondition.value		= '';
		f.action 				= '/cms/recog/sendermsg_list.do';
		f.submit();
    }
    
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

		document.listForm.action = '/cms/recog/sendermsg_list.do';
		document.listForm.submit();
	}
	
	/* pagination 페이지 링크 function */
	function fn_egov_link_page(pageNo){
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "<c:url value='/cms/recog/sendermsg_list.do'/>";
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
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            단속메세지 목록
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-table"></i>  
                                단속관리
                            </li>
                            <li class="active">
                                <i class="fa fa-desktop"></i> 단속메세지 조회
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

        <form:form commandName="pageVO" name="listForm" method="post" onsubmit="onCheckSearch();return false;">
        <input type="hidden" name="dsr_seq" />
        <input type="hidden" name="own_car_no" />
        <div>
				<form:select path="searchCondition" cssClass="form-control" style="display:inline-block;width:100px;float:left;">
					<form:option value="" label="선택" />
					<form:option value="call_to" label="수신번호" />
					<form:option value="call_from" label="발신번호" />
				</form:select>
			
			<c:choose>
				<c:when test="${pageVO.searchKeyword != null }">
					<label for="searchKeyword"></label>
					<input class="form-control" style="display:inline-block;width:200px; margin-bottom:20px; float:left;" type="text" id="searchKeyword" name="searchKeyword" value="<c:out value="${pageVO.searchKeyword}"></c:out>" />
				</c:when>
				<c:otherwise>
					<label for="searchKeyword"></label>
					<input class="form-control" style="display:inline-block;width:200px; margin-bottom:20px; float:left;" type="text" id="searchKeyword" name="searchKeyword" value="" />
				</c:otherwise>
			</c:choose>
			
        	<button class="btn btn-default" style="float:left; margin-bottom:2px" type="submit">검색</button>
			<button class="btn btn-default" style="float:right; margin-bottom:2px" type="button" onclick="goList();">목록</button>
			<!-- <button style="float:right; margin-right: 10px;" class="btn btn-success" style="margin-bottom:2px" type="button" onclick="onClickTrans('R');">등록</button>
			<button style="float:right; margin-right: 10px;" class="btn btn-success" style="margin-bottom:2px" type="button" onclick="onClickTrans('R');">등록</button> -->
        </div>
          <table class="table table-bordered table-hover  ">
                <colgroup>
                    <col style="width:8%;">
                    <col style="width:10%;">
                    <col style="width:10%;;">
                    <col style="width:10%;;">
                    <col style="width:25%;;">
                    <col style="width:15%;;">
                    <col style="width:8%;;">
                </colgroup>         
            <thead>
            <tr>
                <th class=" text-center">NO</th>
                <th class=" text-center">구분</th>
                <th class=" text-center">수신번호</th>
                <th class=" text-center">발신번호</th>
                <th class=" text-center">내용</th>
                <th class=" text-center">발송요청시간</th>
                <th class=" text-center">상태</th>
                <th class=" text-center">비고</th>
            </tr>
            </thead>
            <tbody>
			<c:choose>
				<c:when test="${fn:length(resultList) <= 0 }">
					<tr>
						<td class="text-center" colspan="8">검색 결과에 대한 데이터가 존재하지 않습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="result" items="${resultList}" varStatus="status">
					<c:set value="${(nTotalRecordCnt - ((nCurrPageNo - 1) * nRecordCntPerPage)) - status.index }" var="nIndexCnt"></c:set>
					<tr>
						<td class="text-center"><c:out value="${nIndexCnt}"/></td>
						<td class="text-center"><c:out value="${result.msg_type }"></c:out></td>
						<td class="text-center"><c:out value="${result.call_to }"/></td>
						<td class="text-center"><c:out value="${result.call_from }"/></td>
						<td class="text-center"><c:out value="${result.sms_txt }"></c:out></td>
						<td class="text-center"><c:out value="${result.req_date }"></c:out></td>
						<td class="text-center"><c:out value="${result.cur_state }"></c:out></td>
						<td class="text-center"></td>
					</tr>
					</c:forEach>   
				</c:otherwise>
			</c:choose>
        	          
        </tbody>
        </table>
        <div style="text-align: center;">
        <ul class="pagination">
           <!-- <li class="disabled"><span>&laquo;</span></li><br>
            <li class="active"><span>1</span></li><br>
            <li><a href="#">2</a></li><br>
            <li><a href="#" rel="next">&raquo;</a></li> -->
        	<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="fn_egov_link_page" />
			<form:hidden path="pageIndex" />
        </ul>
        </div>
        </form:form>
   		
            </div>
            <!-- /.container-fluid -->
            <br><br><br>
        </div>
        <!-- /#page-wrapper -->
	</div>
    <!-- /#wrapper -->
</body>
</html>
    