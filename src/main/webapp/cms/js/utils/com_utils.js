/**
 * 공통으로 사용하는 함수
 *
 */

function strToInt(s) {
	var	v = 0;
	try {
		if (s != null && s != '') {
			v = parseInt(s.toString());
			if (isNaN(v))
				v = 0;
		}
	} catch(e) { }
	return v;
}

function toStr(n) {
	var	v = '';
	try {
		if (n != null) {
			v = n.toString();
		}
	} catch(e) { }
	return v;
}



//
/**
 * 아이디 체크 | 특수문자.
 */
function checkId(sValue)
{
    var oIDMatch = new RegExp(/^[A-Z0-9]{8,20}$/i);

    return oIDMatch.test(sValue);
}

function checkPass(sValue)
{
    var oIDMatch = new RegExp(/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,10}$/);

    return oIDMatch.test(sValue);
}


function checkNumber(num)
{
	if(isNaN(num)) { alert("숫자만 입력할 수 있습니다."); return ''; }		    
	return num;
}

function checkEmail(str) 
{
	if(/^[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+)*@[0-9a-zA-Z-]+(\.)+([0-9a-zA-Z-]+)([\.0-9a-zA-Z-])*$/.test(str) == false) 
	{
		return false;
	}
	return true;
}
