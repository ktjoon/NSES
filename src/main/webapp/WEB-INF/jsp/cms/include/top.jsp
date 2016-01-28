<%@page import="nses.common.session.BaseSessInfo"%>
<%@page import="nses.common.session.SessionUtility"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set value="${sessionScope.sess_LoginAdmin}" var="sessInfo"/>
<script>

	function doLogout() {
		if (confirm('로그아웃을 하시겠습니까?')) {
			location.href	= '${sessInfo.logoutUri}';
		}
		
	}
	function goBaseUri() {
		location.href	= '${sessInfo.baseUri}';
	}
</script>
<div class="navbar-header">
	<button type="button" class="navbar-toggle" data-toggle="collapse"
		data-target=".navbar-collapse">
		<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
		<span class="icon-bar"></span> <span class="icon-bar"></span>
	</button>
	<a class="navbar-brand" href="javascript:goBaseUri();">119 길안내 시스템 </a>
</div>
<!-- /.navbar-header -->

<ul class="nav navbar-top-links navbar-right">

	<li><i class="fa fa-envelope fa-fw"></i><c:out value="${sessInfo.userEmail }"></c:out></li>
	<li><i class="fa fa-user fa-fw"></i> 관리자</li>
	<!-- /.dropdown -->
	<li>
		<a href="#" onclick="doLogout();">
		<i class="fa fa-sign-out fa-fw"></i>
			Logout
		</a>
	</li>
	<!--/.dropdown -->
</ul>