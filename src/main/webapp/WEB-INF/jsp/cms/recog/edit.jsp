<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set value="단속정보 조회" var="sHtmlTitle"></c:set>
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
    	var f	= document.listForm;
    	document.editForm.action = '/cms/recog/list.do';
		document.editForm.submit();
    }
    
 // 단속처리/메시지전송
    function onClick_setRecogUpdate() {
    	var own_car_no			= $('#own_car_no').val();
    	var dsr_seq			= $('#dsr_seq').val();
    	
    	// 2: 단속처리, 3: 메세지전송 
    	
    	var url = '/ons/recog/update_action.do';
    	var params = {
    		own_car_no: own_car_no,
    		dsr_seq: dsr_seq,
    		recog_flag: "2"
    	};
    	
    	net_ajax(url, params, function (data) {
    		if (data.retCode == const_ret_ok) {
    			if (flag == 2) {
    				disableObstCar(pop_thiz);
    				alert('단속처리 되었습니다.');
    				
    			} else if (flag == 3) {
    				alert('메세지가 전송되었습니다.');
    			}
    		} else {
    			alert(data.retMsg);
    		}
    	}, onNetCallbackDefaultError);
    }
    
    </script>
</head>

<body>
<div class="pop_back"></div>
<form:form commandName="pageVO" name="editForm" method="post">
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
							<h1 class="page-header"> 단속관리 </h1>
							<ol class="breadcrumb">
								<li>
									<i class="fa fa-table"></i>
									단속관리
								</li>
								<li class="active">
									<i class="fa fa-desktop"></i> 단속정보 상세조회
								</li>
							</ol>
						</div>
					</div>
                <!-- /.row -->
        <table class="table table-bordered">
        		<tr>
					<td class="active text-center" style="font-weight: bold"> 차량번호 </td>
					<td style="font-weight: bold ">
						<c:if test="${car_no != '' }">
							${car_no}
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="active text-center"> 소유주 </td>
					<td>
						<c:if test="${own_name != '' }">
							${own_name}
						</c:if>	
					</td>
				</tr>
				<tr>
					<td class="active text-center"> 연락처 </td>
					<td>
						<c:if test="${tel_no != '' }">
							${tel_no}
						</c:if>	
					</td>
				</tr>
        </table>
        
        <table class="table table-bordered table-hover  ">
							<colgroup>
								<col style="width:300px;">
								<col style="width:*;">
								<col style="width:150px;">
							</colgroup>
							<tbody>
								<c:forEach var="result" items="${resultList}" varStatus="status">	
									<tr style="">
										<td class=" text-center">
											<div style="width:300px; height:230px;">
												<img src="/ons/recog/image.do?dsr_seq=${result.dsrSeq }&car_no=${uenc_car_no}&recog_time=${result.recogTime}&img_data=1" width="300px" height="230px" />
											</div>
										</td>
	
										<td class="active text-left">
											<div>
												<ul style="list-style:none; line-height: 70px; font-size: 20px;">
													<li style="font-weight:bold;">카메라 : <c:out value="${result.cctvName }"></c:out><li>
													<li>위치 : <c:out value="${result.poiAddr}"></c:out> </li>
													<li>촬영시각 :  <c:out value="${result.recogConvertTime}"></c:out></li>
												</ul>
											</div>
										</td>
										<td class=" text-center">
											<img src="/ons/recog/image.do?dsr_seq=${result.dsrSeq }&car_no=${uenc_car_no}&recog_time=${result.recogTime}&img_data=2" style="width:150px; height: 190px;" alt="이미지" />
											<!-- <div><a class="btn btn-default" style="margin:10px 0 0 0;" id="useradd" onclick="">모자이크</a></div> -->
										</td>
									</tr>
								</c:forEach> 
							</tbody>
						</table>
        
        
        <div style="text-align: center;">
            <a class="btn btn-default" style="margin-right:10px;" onclick="fn_go_page();">목록</a>
			<!-- <a class="btn btn-default" style="margin-right:10px;" onclick="onClick_setRecogUpdate();">단속처리</a> -->
        </div>
            <!-- /.container-fluid -->
            <br><br><br>
        </div>
        <!-- /#page-wrapper -->
    </div>
    </div>
    <!-- /#wrapper -->
    <!-- 검색조건 유지 -->
    <input type="hidden" id="dsr_seq" name="dsr_seq" value="<c:out value="${dsr_seq}"></c:out>">
    <input type="hidden" id="own_car_no" name="own_car_no" value="<c:out value="${car_no}"></c:out>">
	<input type="hidden" name="searchCondition" value="<c:out value='${pageVO.searchCondition}'/>"/>
	<input type="hidden" name="searchKeyword" value="<c:out value='${pageVO.searchKeyword}'/>"/>
	<input type="hidden" name="pageIndex" value="<c:out value='${pageVO.pageIndex}'/>"/>
    </form:form>
</body>
</html>
    