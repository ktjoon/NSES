<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set value="단속 수정" var="sHtmlTitle"></c:set>
<c:set value="${fn:split(recogsetVO.enforce_start_time, ':') }" var="aryEnforceStartTime"></c:set>
<c:set value="${fn:split(recogsetVO.enforce_end_time, ':') }" var="aryEnforceEndTime"></c:set>
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

        $("#sdate").datepicker();
        $("#edate").datepicker();
        $("#rdate").datepicker();

        $(".datepicker").each(function() {
            if ($(this).val() != '') {
                var d = new Date($(this).val());
                $(this).datepicker('setDate', d);
            }
            $(this).datepicker("option", "dateFormat", "yy-mm-dd");
        });
    });
    
    function doEditAction() {
    	if($("#enforce_time").val() == ""){
			alert("단속설정 시간을 입력해주세요.");
			$("#enforce_time").focus();
			return;
		}
		if($("#org_user_name").val() == ""){
			alert("담당자를 입력해주세요.");
			$("#org_user_name").focus();
			return; 
		}
		if (!checkNumber($("#enforce_time").val())) {
			$("#enforce_time").focus();
    		return;
    	}
    	
    	var url = '/cms/recogset/edit_action.do';
		var params = $('#recogsetVO').serialize();
		net_ajax(url, params, function(data) {
			if (data.retCode == const_ret_ok) {
				alert("수정되었습니다.");
			} else {
				alert(data.retMsg);
			}
		}, onNetCallbackDefaultError);
    }
    
    
    function doBack() {
    	location.href = "/cms/recogset/edit.do";
    }
    
    </script>
</head>

<body>
<div class="pop_back"></div>
<form:form commandName="recogsetVO" name="editForm" method="post">
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
            <tbody>
                            <tr>
                                <td class="active">단속 시간 설정</td>
                                <td>
                                	<label for="enforce_time"></label>
                                	<input type="text" maxlength="20" class="form-control" style="width:20%; display: inline-block;" id="enforce_time" name="enforce_time" value="<c:out value="${recogsetVO.enforce_time }"></c:out>"/>
                                    <div style="display: inline-block;">초</div>
                                    <div style="">* 단속 시간으로 설정해놓은 시간이 지나면 차량 화면에 표시가 됩니다.</div>
                                </td>
                            </tr>
                            <tr>
                                <td class="active">담당자명</td>
                                <td>
                                	<label for="org_user_name"></label>
                                	<input type="text" maxlength="30" class="form-control" style="width:20%; display: inline-block;" id="org_user_name" name="org_user_name" value="<c:out value="${recogsetVO.org_user_name }"></c:out>"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="active">전화번호</td>
                                <td>
                                	<label for="sender_tel"></label>
                                	<input type="text" maxlength="20" class="form-control" style="width:20%; display: inline-block;" id="sender_tel" name="sender_tel" value="<c:out value="${recogsetVO.sender_tel }"></c:out>"/>
                                	<div style="">'-'을 제외한 숫자만 입력해주세요.</div>
                                </td>
                            </tr>
                            <tr>
                                <td class="active">담당자전화번호</td>
                                <td>
                                	<label for="org_user_tel"></label>
                                	<input type="text" maxlength="20" class="form-control" style="width:20%; display: inline-block;" id="org_user_tel" name="org_user_tel" value="<c:out value="${recogsetVO.org_user_tel }"></c:out>"/>
                                	<div style="">'-'을 제외한 숫자만 입력해주세요.</div>
                                </td>
                            </tr>
                            <tr>
                                <td class="active">단속 시간대 설정</td>
                                <td>
                                	<label for="enforce_start_hh"></label>
                                    <select class="form-control" id="enforce_start_hh" name="enforce_start_hh" style="width:10%; display:inline-block">
                                       <option value="00" selected="selected">00</option>
								       <c:forEach var="item" varStatus="i" begin="1" end="24" step="1">
									       <option value="<c:if test='${item < 10 }'>0</c:if>${item}"  <c:if test="${item == aryEnforceStartTime[0]}">selected="selected"</c:if>>
									        <c:if test="${item < 10}">0</c:if><c:out value="${item}" />
									       </option>
								       </c:forEach>
                                    </select>
                                    <div style="display: inline-block;">:</div>
                                    <label for="enforce_start_mm"></label>
                                    <select class="form-control" id="enforce_start_mm" name="enforce_start_mm" style="width:10%; display:inline-block">
                                        <option value="00" selected="selected">00</option>
                                    	<c:forEach var="item" varStatus="i" begin="1" end="59" step="1">
									       <option <c:if test="${item < 10 }"> var</c:if> <c:if test="${item == aryEnforceStartTime[1]}">selected="selected"</c:if>>
									        <c:if test="${item < 10}">0</c:if><c:out value="${item}" />
									       </option>
								       </c:forEach>
                                    </select>
                                    <div style="display: inline-block;">~</div>
                                    <label for="enforce_end_hh"></label>
                                    <select class="form-control" id="enforce_end_hh" name="enforce_end_hh" style="width:10%; display:inline-block">
                                        <option value="00" selected="selected">00</option>
								       <c:forEach var="item" varStatus="i" begin="1" end="24" step="1">
									       <option value="<c:if test='${item < 10 }'>0</c:if>${item}"  <c:if test="${item == aryEnforceEndTime[0]}">selected="selected"</c:if>>
									        <c:if test="${item < 10}">0</c:if><c:out value="${item}" />
									       </option>
								       </c:forEach>
                                    </select>
                                    <div style="display: inline-block;">:</div>
                                    <label for="enforce_end_mm"></label>
                                    <select class="form-control" id="enforce_end_mm" name="enforce_end_mm" style="width:10%; display:inline-block">
                                        <option value="00" selected="selected">00</option>
                                    	<c:forEach var="item" varStatus="i" begin="1" end="59" step="1">
									       <option <c:if test="${item < 10 }"> var</c:if> <c:if test="${item == aryEnforceEndTime[1]}">selected="selected"</c:if>>
									        <c:if test="${item < 10}">0</c:if><c:out value="${item}" />
									       </option>
								       </c:forEach>
                                    </select>
                                    <div style="">* 단속 시간대 시작과 종료시점을 설정합니다.</div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="active">이동권고 메시지 설정</td>
                            </tr>
                            <tr>
                                <td class="active">자동발송</td>
                                <td>
                                    <figure>
                                    		자동 
                                    		<label for="c"></label>
                                            <input id="c" name="msg_auto_send" type="checkbox" <c:if test="${recogsetVO.msg_auto_send eq '1' }">checked="checked"</c:if>   />
                                            
                                            <!-- <label for="c">
                                                <div class="card slide"></div>
                                            </label> -->
                                    </figure>

                                    <div style="">* 번호인식 후 설정된 메시지를 번호로 자동으로 전송합니다.</div>
                                </td>
                            </tr>
                            <tr>
                                <td class="active">메시지 내용</td>
                                <td>
                                	<label for="msg_contents"></label>
                                    <textarea style="height:100px; width:100%;" id="msg_contents" name="msg_contents"><c:out value="${recogsetVO.msg_contents }"></c:out></textarea>
                                </td>
                            </tr>
                            <c:if test="${show_save_sec == 1}">
                            <tr>
                                <td colspan="2" class="active">영상정보 저장시간 설정</td>
                            </tr>
                            <tr>
                                <td class="active">시간설정(초)</td>
                                <td>
                                	<label for="rec_save_sec"></label>
                                	<input type="text" maxlength="15" class="form-control" style="width:20%; display: inline-block;" id="rec_save_sec" name="rec_save_sec" value="<c:out value="${recogsetVO.rec_save_sec }"></c:out>"/>
                                </td>
                            </tr>
                            </c:if>
                            <c:if test="${show_save_sec != 1}">
                               	<input type="hidden" id="rec_save_sec" name="rec_save_sec" value="<c:out value="${recogsetVO.rec_save_sec }"></c:out>"/>
                            </c:if>
                        </tbody>
        </table>
        <div style="text-align: center;">
            <a href="javascript:;" class="btn btn-default" style="margin-right:10px;" onclick="doEditAction();">변경사항 저장</a>
            <a href="javascript:;" class="btn btn-default" style="margin-right:10px;" onclick="doBack();">되돌리기</a>
        </div>
            <!-- /.container-fluid -->
            <br><br><br>
        </div>
        <!-- /#page-wrapper -->
    </div>
    </div>
    <!-- /#wrapper -->
    </form:form>
</body>
</html>
    