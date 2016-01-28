<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>로그인</title>

<link rel="stylesheet" href="./css/login.css" />
<script src="./js/libs/jquery.1.10.js"></script>
<script src="./js/utils/com_codes.js"></script>
<script src="./js/utils/com_network.js"></script>

<script type="text/javascript">
$(function() {
	$(".pad").css("height", $(window).height()/2-70);
	$(window).resize(function() {
		$(".pad").css("height", $(window).height()/2-70);
	});

	//btns 클래스 내부의 img 태그에 on, off 효과를 준다.
	$("form .img").mousedown(function() {
		$(this).attr("src",$(this).attr("src").replace(/\_off./g,"_on."));
	});
	$("form .img").mouseup(function() {
		$(this).attr("src",$(this).attr("src").replace(/\_on./g,"_off."));
	});
	$("form .img").mouseout(function() {
		$(this).attr("src",$(this).attr("src").replace(/\_on./g,"_off."));
	});
});

$(document).ready(function() {
	$( "#frmLogin" ).submit(function( event ) {
		var	f = document.getElementById('frmLogin');

		if (f.user_id.value == '') {
			alert('아이디를 입력해주십시오.');
			return;
		}
		if (f.user_pwd.value == '') {
			alert('비밀번호를 입력해주십시오.');
			return;
		}
		
		function onLoginResult(data) {
			if (data.retCode == const_ret_ok) {
				location.href = '/ons/main.do';
			} else {
				alert(data.retMsg);
			}
		}
		function onLoginError() {
			alert('로그인 수행 도중 오류가 발생하였습니다.\n잠시후에 이용하여 주시기 바랍니다.');
		}

		var url = '/ons/login_action.do';
		var params = {
			user_id: f.user_id.value,
			user_pwd: f.user_pwd.value
		};

		net_ajax(url, params, onLoginResult, onLoginError);
	});
});

</script>
</head>
<body>
    <div class="pad"></div>
    <div class="back">
        <div class="logo">
            <img src="./images/login/login_logo.png" alt="logo" />
        </div>
        <form id="frmLogin" action="#" onsubmit="return false;">
            <input id="user_id" class="text" type="text" placeholder="아이디를 입력해주세요" value="" ><br />
            <input id="user_pwd" class="text" type="password" placeholder="패스워드를 입력해주세요" value=""><br />
            <input class="img" type="image" src="./images/login/btn_login_off.png">
        </form>

    </div>
</body>
</html>
