/**
 * 콤보 컨트롤 함수
 *
 */

	function NmValue(p_key, p_text) {
		this.key	= p_key;
		this.text	= p_text;
	}
	function compare_NmVal(obj1, obj2) {
		if (obj1.name > obj2.name)
			return 1;
		if (obj1.name < obj2.name)
			return -1;
		return 0;
	}

    // combo object 추가
    function combo_addItem(cbo, sText, sValue, bSelected) {
        var oOption = new Option(sText, sValue, bSelected);
        cbo.add(oOption);
    }
    // get current Text
    function combo_getText(cbo) {
    	if (cbo.selectedIndex < 0)
    		return '';
    	return cbo.options[cbo.selectedIndex].text;
    }
    // first item 은 삭제하지 않고 clear
	function combo_clear(obj_name) {
		var cbo_obj = document.getElementById(obj_name);
		while(cbo_obj.options.length > 1) {
			cbo_obj.options.remove(cbo_obj.options.length - 1);
		}
	}
	function combo_clear_all(obj_name) {
		var cbo_obj = document.getElementById(obj_name);
		while(cbo_obj.options.length > 0) {
			cbo_obj.options.remove(cbo_obj.options.length - 1);
		}
	}
	function combo_obj_clear(cbo_obj) {
		while(cbo_obj.options.length > 1) {
			cbo_obj.options.remove(cbo_obj.options.length - 1);
		}
	}

	// 소트하고 콤보에 넣는 함수
	function combo_fill_sort(obj_name, jarry) {
    	var	aryData = new Array();
		for (var i = 0; i < jarry.length; i ++) {
			aryData.push(new NmValue(jarry[i].codeKey,jarry[i].codeText));
		}
		aryData.sort(compare_NmVal);

		var cbo_obj = document.getElementById(obj_name);
		combo_obj_clear(cbo_obj);
		for (var i = 0; i < aryData.length; i ++) {
			combo_addItem(cbo_obj, aryData[i].text, aryData[i].key, false);
		}
	}
	function combo_fill(obj_name, items) {
		var cbo_obj = document.getElementById(obj_name);
		combo_obj_clear(cbo_obj);
		for (var i = 0; i < items.length; i ++) {
			combo_addItem(cbo_obj, items[i].codeText, items[i].codeKey, false);
		}
	}
