<%@page import="nses.common.session.BaseSessInfo"%>
<%@page import="nses.common.session.SessionUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.1
Version: 3.3.0
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8"/>
	<title>Login</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta content="" name="description"/>
	<meta content="" name="author"/>
	<link rel="stylesheet" href="./css/login.css" />

<script src="./js/libs/jquery-1.9.1.min.js"></script>
<script src="./js/utils/com_codes.js"></script>
<script src="./js/utils/com_network.js"></script>

<script type="text/javascript">
function onSubmit() {
	var	f = document.getElementById('frmLogin');

	function onLoginResult(data) {
		if (data.retCode == const_ret_ok) {
			//location.href = '/cms/main.do';
			location.href = '/cms/user/list.do';
		} else {
			alert(data.retMsg);
		}
	}

	function onLoginError() {
		alert('로그인 수행 도중 오류가 발생하였습니다.\n잠시후에 이용하여 주시기 바랍니다.');
	}

	var url = '/cms/login_action.do';
	var params = {
		user_id: f.user_id.value,
		user_pwd: f.user_pwd.value
	};

	net_ajax(url, params, onLoginResult, onLoginError);
	return false;
}
</script>

</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="login" style="background: url('./images/login/bg.jpg');">

	<!-- BEGIN LOGO -->
	<div class="logo">
		<img src="./images/login/logo.jpg" alt=""/>
	</div>
	<!-- END LOGO -->
	<!-- BEGIN LOGIN -->
	<div class="content" >
		<!-- BEGIN LOGIN FORM -->
		<form id="frmLogin" name="frmLogin" class="login-form" action="index.html" method="post" onsubmit="return onSubmit();" >
			<h3 class="form-title" style="">Sign In</h3>
			<div class="form-group">
				<label for="user_id"></label>
				<input  type="text" autocomplete="off" placeholder="아이디" id="user_id" name="user_id"/>
			</div>
			<div class="form-group">
				<label for="user_pwd"></label>
				<input  type="password" autocomplete="off" placeholder="비밀번호" id="user_pwd" name="user_pwd"/>
			</div>
			<div class="form-actions" >
				<button type="submit" >
					Login
				</button>
				<!--
				<label class="rememberme check">
				<input type="checkbox" name="remember" value="1"/>Remember </label>
				<a href="javascript:;" id="forget-password" class="forget-password">Forgot Password?</a>
				-->
			</div>
			<!--
			<div class="login-options">
			<h4>Or login with</h4>
			<ul class="social-icons">
			<li>
			<a class="social-icon-color facebook" data-original-title="facebook" href="#"></a>
			</li>
			<li>
			<a class="social-icon-color twitter" data-original-title="Twitter" href="#"></a>
			</li>
			<li>
			<a class="social-icon-color googleplus" data-original-title="Goole Plus" href="#"></a>
			</li>
			<li>
			<a class="social-icon-color linkedin" data-original-title="Linkedin" href="#"></a>
			</li>
			</ul>
			</div>

			<div class="create-account">
			<p>
			<a href="javascript:;" id="register-btn" class="uppercase">Create an account</a>
			</p>
			</div>-->
		</form>
		<!-- END LOGIN FORM -->

	</div>
	<div class="copyright">
		<img src="./images/login/copyright.png"/>
	</div>
	<!-- END LOGIN -->

</body>
<!-- END BODY -->
</html>