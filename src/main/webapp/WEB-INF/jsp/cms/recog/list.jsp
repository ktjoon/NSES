<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    	f.searchKeyword.value		= '';
		f.searchCondition.value		= '';
		f.action 				= '/cms/recog/list.do';
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
		
		document.listForm.action = '/cms/recog/list.do';
		document.listForm.submit();
	}
	
	/* pagination 페이지 링크 function */
	function fn_egov_link_page(pageNo){
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "<c:url value='/cms/recog/list.do'/>";
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
                            단속정보 목록
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-table"></i>  
                                단속정보 관리
                            </li>
                            <li class="active">
                                <i class="fa fa-desktop"></i> <c:out value="${sHtmlTitle }"></c:out>
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

        <form:form commandName="pageVO" name="listForm" method="post">
        <input type="hidden" name="dsr_seq" />
        <input type="hidden" name="own_car_no" />
        <div style="clear:both">
				<form:select path="searchCondition" cssClass="form-control" style="display:inline-block;width:100px;float:left;">
					<form:option value="" label="선택" />
					<form:option value="own_car_no" label="차량번호" />
					<form:option value="own_name" label="소유주" />
					<form:option value="cctv_name" label="촬영카메라" />
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
			
        	<button class="btn btn-default" style="float:left; margin-bottom:2px" type="submit" onclick="onCheckSearch();">검색</button>
			<button class="btn btn-default" style="float: right; margin-bottom:2px" type="button" onclick="goList();">목록</button>
			<!-- <button style="float:right; margin-right: 10px;" class="btn btn-success" style="margin-bottom:2px" type="button" onclick="onClickTrans('R');">등록</button>
			<button style="float:right; margin-right: 10px;" class="btn btn-success" style="margin-bottom:2px" type="button" onclick="onClickTrans('R');">등록</button> -->
			
			       
        </div>

          <table class="table table-bordered table-hover  ">
                <colgroup>
                    <col style="width:8%;">
                    <col style="width:15%;">
                    <col style="width:10%;;">
                    <col style="width:15%;;">
                    <col style="width:10%;;">
                    <col style="width:10%;;">
                    <col style="width:15%;;">
                    <col style="width:8%;;">
                </colgroup>         
            <thead>
            <tr>
                <th class=" text-center">NO</th>
                <th class=" text-center">촬영카메라</th>
                <th class=" text-center">촬영위치</th>
                <th class=" text-center">단속시간</th>
                <th class=" text-center">차량번호</th>
                <th class=" text-center">소유주</th>
                <th class=" text-center">연락처</th>
                <th class=" text-center">상세보기</th>
            </tr>
            </thead>
            <tbody>
		
        	<c:forEach var="result" items="${resultList}" varStatus="status">
			<c:set value="${(nTotalRecordCnt - ((nCurrPageNo - 1) * nRecordCntPerPage)) - status.index }" var="nIndexCnt"></c:set>
			<tr>
				<td class="text-center"><c:out value="${nIndexCnt}"/></td>
				<td class="text-center"><c:out value="${result.cctv_name}"/></td>
				<td class="text-center"><c:out value="${result.poi_addr}"/></td>
				<td class="text-center"><c:out value="${result.recog_time}"></c:out></td>
				<td class="text-center"><c:out value="${result.own_car_no}"></c:out></td>
				<td class="text-center"><c:out value="${result.own_name }"></c:out></td>
				<td class="text-center"><c:out value="${result.tel_no }"></c:out></td>
				<td class="text-center">
					<button class="btn btn-default" style="float:center; margin-bottom:2px" type="button" onclick="onClickTrans('${result.dsr_seq}', '${result.own_car_no}')">보기</button>
				</td>
			</tr>
			</c:forEach>             
        </tbody>
        </table>
        <div style="text-align: center;">
        <ul class="pagination">
           <!-- <li class="disabled"><span>&laquo;</span></li><br>
            <li class="active"><span>1</span></li><br>
            <li><a href="#">2</a></li><br>
            <li><a href="#" rel="next">&raquo;</a></li> -->
        	<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="fn_egov_link_page" />
        </ul>
			<form:hidden path="pageIndex" />
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
    