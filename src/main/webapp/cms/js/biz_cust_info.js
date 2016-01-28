/**
 * 
 */
 
 function onClickTrans(flag, ukey) {
	var		f = document.getElementById('');
	if (flag == 'L') {
		f.action = '/cms/cust/list.do';
	}
	else if (flag == 'R') {
		f.action = '/cms/cust/reg.do';
	}
	else if (flag == 'E') {
		f.action = '/cms/cust/edit.do?cust_key=' + ukey;
	}
	else if (flag == 'V') {
		f.action = '/cms/cust/view.do?cust_key=' + ukey;
	}
	else if (flag == 'goLIST') {
		f.page_no.value 		= '0';
		f.sc_search_type.value 	= '';
		f.sc_search_text.value 	= '';
		f.action 				= '/cms/cust/list.do';
	}
	f.submit();
}