<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="sidebar-nav navbar-collapse">
	<ul class="nav" id="side-menu">
		
		<!-- 사용자 관리 -->
		<li class="active"><a href="#">
			<i class="fa fa-dashboard fa-fw"></i>사용자 관리<span class="fa arrow"></span></a>
			<ul class="nav nav-second-level">
				<li><a href="/cms/user/list.do" class="">사용자 정보</a></li>
			</ul>
			<!-- /.nav-second-level -->
		</li>
		
		<!-- 등록 관리 -->
		<li class="active"><a href="#">
			<i class="fa fa-dashboard fa-fw"></i>등록관리<span class="fa arrow"></span></a>
			<ul class="nav nav-second-level">
				<li><a href="/cms/cctv/list.do" class="">CCTV 정보관리</a></li>
				<li><a href="/cms/firecctv/list.do" class="">화재감시 CCTV 정보관리</a></li>
				<li><a href="/cms/firecar/list.do"  class="">차량 정보관리</a></li>
				<li><a href="/cms/carown/list.do"  class="">차주 정보관리</a></li>
			</ul>
			<!-- /.nav-second-level -->
		</li>
		
		<!-- 단속 관리 -->
		<li class="active"><a href="#">
			<i class="fa fa-dashboard fa-fw"></i>단속관리<span class="fa arrow"></span></a>
			<ul class="nav nav-second-level">
				<li><a href="/cms/recog/list.do" class="">단속정보 조회</a></li>
				<!-- 
				<li><a href="/cms/recog/sendermsg_list.do" class="">단속메세지 조회</a></li>
				-->
				<li><a href="/cms/recogset/edit.do" class="">단속 설정</a></li>
			</ul>
			<!-- /.nav-second-level -->
		</li>
		
		<!-- 저장 영상 -->
		<li class="active"><a href="#">
			<i class="fa fa-dashboard fa-fw"></i>저장 영상<span class="fa arrow"></span></a>
			<ul class="nav nav-second-level">
				<li><a href="/cms/saved/list.do" class="">영상 정보</a></li>
			</ul>
			<!-- /.nav-second-level -->
		</li>
		
		<!-- 통계 조회 -->
		<li class="active"><a href="#">
			<i class="fa fa-dashboard fa-fw"></i>통계 조회<span class="fa arrow"></span></a>
			<ul class="nav nav-second-level">
				<li><a href="/cms/stats/list.do" class="">차량 단속 통계</a></li>
			</ul>
			<!-- /.nav-second-level -->
		</li>
		
	</ul>
</div>