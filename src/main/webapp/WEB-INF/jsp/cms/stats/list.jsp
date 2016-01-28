<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set value="차량 단속 통계" var="sHtmlTitle"></c:set>
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
    $(document).ready(function() {
    	var	sdate = '${sdate}';
    	var	edate = '${edate}';

        $("#sdate").datepicker();
        $("#edate").datepicker();

        $("#sdate").val(sdate);
        $("#edate").val(edate);
        
        $(".datepicker").each(function() {
            if ($(this).val() != '') {
                var d = new Date($(this).val());
                $(this).datepicker('setDate', d);
            }
            $(this).datepicker("option", "dateFormat", "yy-mm-dd");
        });
        
        onclickStats('0');
    });
    </script>
    
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script type="text/javascript">
	
	function makeDateYMD(dateStr) {

		if (dateStr === undefined)
			return '';
		
		if (dateStr == null)
			return '';
		
		if (dateStr.length < 8)
			return dateStr;

		var		str = '';
		
		str += dateStr.substring(0, 4) + '-';
		str += dateStr.substring(4, 6) + '-';
		str += dateStr.substring(6, 8);

		return str;
	}
	function makeDateYM(dateStr) {

		if (dateStr === undefined)
			return '';
		
		if (dateStr == null)
			return '';
		
		if (dateStr.length < 6)
			return dateStr;

		var		str = '';
		
		str += dateStr.substring(0, 4) + '-';
		str += dateStr.substring(4, 6);

		return str;
	}

	
	function makeDateStr(s_type, data) {
		if (s_type == '0') {
			return makeDateYMD(data);
		}
		if (s_type == '1') {
			return makeDateYM(data);
		}
		return data;
	}
	
	google.load("visualization", "1", {packages:["corechart"]});
	//google.setOnLoadCallback(drawVisualization);

	function drawVisualization(s_type, gdata, term) {
		var rows = new Array();

		rows.push(['Days','건수']);
		
		for(var i=0; i<term.length; i++) {
			var row = new Array();   
			var flag = false;
			var num = 0;

			for(var j = 0; j<gdata.length; j++) {
				if(term[i] == gdata[j].recogDt) {
					num = gdata[j].cntNum;
					flag = true;
				}
			}

			if(flag) {
				row[0] = makeDateStr(s_type, term[i]);
				row[1] = Number(num);
			} else {
				row[0] = makeDateStr(s_type, term[i]);
				row[1] = 0;
			}

			rows.push(row);
		}

		var data = google.visualization.arrayToDataTable(rows);
		
		if(s_type == '0') {
			var options = {
					vAxis: {title: "단속건수"},
					hAxis: {title: "일자"},
					series: {5: {type: "line"}}
				};	
		} else {
			var options = {
					vAxis: {title: "단속건수"},
					hAxis: {title: "월별"},
					series: {5: {type: "line"}}
				};	
		}
		
		
		

		var chart = new google.visualization.ComboChart(document.getElementById('chart'));
		chart.draw(data, options);
	}
    
    
    
	function onclickStats(dtype) {
						
		var sdt = $('#sdate').val();
		var edt = $('#edate').val();
		
		if (sdt > edt) {
			alert("날짜를 확인해주세요.");
			return;
		}

		var url = '/cms/stats/stats_action.do';
						
		var params = {
			type : dtype,
			start_dt : sdt,
			end_dt : edt
		};
		
		net_ajax(url, params, function(data) {
			if (data.retCode == const_ret_ok) {
				drawVisualization(dtype, data.items, data.days);
			} else {
				alert(data.retMsg);
			}
		}, onNetCallbackDefaultError);

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

            <div class="container-fluid ">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header"> 통계조회 </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-film"></i>
                                통계조회
                            </li>
                            <li class="active">
                                <i class="fa fa-desktop"></i> 차량 단속 통계
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

                <form name="f" >
                    <div style="text-align:right"> 조회하려는 날짜는 선택하십시오. 
                    	<label for="sdate"></label>
                        <input type="text" id="sdate" class="datepicker form-control" style="width:120px; display:inline-block"  />
                        ~
                        <label for="edate"></label>
                        <input type="text" id="edate" class="datepicker form-control" style="width:120px; display:inline-block" />
                        <!-- <input type="button" class="btn" value="조회" style="float:right;" />
                       	carVO.getTel_no() -->
                       	 <input type="button" class="btn" value="일별" style="" onclick="onclickStats('0')"/>
                        <input type="button" class="btn" value="월별" style="margin:0 0 0 5px;" onclick="onclickStats('1')"/>
                        <br />
                    </div>
 <!--                    <div style="text-align:right; margin:10px 0 0 0">
                        <input type="button" class="btn" value="일별" style="" onclick="onclickStats('0')"/>
                        <input type="button" class="btn" value="월별" style="margin:0 0 0 5px;" onclick="onclickStats('1')"/>
                    </div> -->

                    <div id="chart" style="width:100%; height:400px; margin:10px 0 0 0; border:1px solid black">
                       
                    </div>

                </form>

            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
    <br>
</body>
</html>
    