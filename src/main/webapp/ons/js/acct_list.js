/**
 * 재난목록 관리
 */

AcctList = function () {
	//
	this.aryList	= new Array();	// 재난목록
};

AcctList.prototype = {
	addItem: function (item) {
		var	inx = this.getIndex(item.dsr_seq);
		
		item.expire_data = 0;
		if (inx >= 0)
			this.aryList[inx] = item;
		else
			this.aryList.push(item);
		
		return inx;
	},
	getItem: function (dseq) {
		var	item = null;
		for (var i = 0; i < this.aryList.length; i ++) {
			if (this.aryList[i].dsr_seq == dseq) {
				item = this.aryList[i];
				break;
			}
		}
		return item;
	},
	getIndex: function (dseq) {
		var	index = -1;
		for (var i = 0; i < this.aryList.length; i ++) {
			if (this.aryList[i].dsr_seq == dseq) {
				index = i;
				break;
			}
		}
		return index;
	},
	setOLDItems: function () {
		for (var i = 0; i < this.aryList.length; i ++) {
			this.aryList[i].expire_data = 1;
		}
	},
	removeFinishItems: function (del_date, curr_seq) {
		var	count = 0;
		for (var i = this.aryList.length-1; i >= 0; i --) {
			if (this.aryList[i].dsr_seq == curr_seq) {
				if (this.aryList[i].expire_data == 1) {
					this.aryList[i].acc_stat = '1';
				}
				continue;
			}

			if (this.aryList[i].expire_data == 1 || (this.aryList[i].acc_stat == '1' && this.aryList[i].reg_dtime < del_date)) {
				this.aryList.splice(i, 1);
				count ++;
			}
		}
		return count;
	},
	sort: function () {
		this.aryList.sort(function(a, b){
			if (a.reg_dtime > b.reg_dtime)
				return -1;
			return (a.reg_dtime < b.reg_dtime) ? 1 : 0;
		});
	},
	getList: function () {
		return this.aryList;
	},
	clear: function () {
		this.aryList = [];
	}
};

var	g_tmAcctList = null;

function startAcctListTimer() {
	g_tmAcctList = setTimeout('actionAcctListTimer()', 15000);
}
function actionAcctListTimer() {
	showListAccident();
	g_tmAcctList = setTimeout('actionAcctListTimer()', 15000);
}
function stopAcctListTimer() {
	if (g_tmAcctList != null) {
		clearTimeout(g_tmAcctList);
		g_tmAcctList = null;
	}
}
