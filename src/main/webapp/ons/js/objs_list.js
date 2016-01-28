/**
 * 대상물검색 목록
 */

function searchObjectData(schText) {
	var url = '/ons/search/datalist_ajax.do';
	var params = { sch_text : schText };

	net_ajax(url, params, function (data) {
		if (data.retCode == const_ret_ok) {
			$('#content .search-div ul li').remove();
			var txt = '';
			for (var i = 0; i < data.items.length; i ++) {
				txt += '<li style="padding: 0 0 10px 0; margin: 0 10px; border-bottom: 1px solid black; cursor:pointer;"';
				txt += '	onclick="onClickSearchListItem($(this));"><span class="name" ';
				txt += '	style="font-size:20px; font-weight:bold; line-height:40px;">' + data.items[i].constName + '</span>';
				txt += '	<br /><span class="address">' + data.items[i].address + '</span>';
				txt += '</li>';
			}
			$('#content .search-div ul').html(txt);
		} else {
			alert(data.retMsg);
		}
	}, onNetCallbackEmptyError); 
}

function closeSeachResultList() {
	$("#content .search-div ul").css("height", "0")
								.css("opacity", "0")
								.css("padding", "0");
	$("#content .search-div ul li").css("opacity", "0")
								   .css("height", "0");
}

var g_searchGeocoder = null;
var	g_searchLastText = '';

function onClickSearchListItem(thiz) {
	if (g_searchGeocoder == null)
		g_searchGeocoder = new olleh.maps.Geocoder(const_map_api_key);

	g_searchLastText = thiz.children("span.name").text();
	$("#search-box").val(g_searchLastText);

	g_searchGeocoder.geocode({ 
			type : 0, 
			addr : thiz.children("span.address").text()
		}, 
		"searchResult_geocode_callback"
	);
}

function searchResult_geocode_callback(data) {
	var geoResult = g_searchGeocoder.parseGeocode(data);
	if (geoResult["count"] != "0") {
		var infoArr = geoResult["infoarr"]; 

		if (infoArr.length > 0) {
			smap.showObjectMarker({
				key: '1', 
				x: infoArr[0].x, 
				y: infoArr[0].y,
				title: g_searchLastText,
				addr: infoArr[0].address
			});
		}
	}
}
