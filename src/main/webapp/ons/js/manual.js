var div_height = new Array();
var isChange = true;

$(document).ready(function() {
	var basic_height = 0;
	div_height.push(0);
	$(".content div").each(function(){
		basic_height += $(this).outerHeight();
		div_height.push(basic_height-250);
	});
	div_height[8] -= 50;

	$('.menu>div').click(function() {
		var no = $(this).index() + 1;

		menu_div(no);
		go_section(no);
		isChange = false;

	});
});

function menu_div(no){
	$(".menu div").removeClass('click');
	$(".menu").find(".no"+no.toString()).addClass('click');
}

function go_section(no) {
	$section = $(".content").find('.no' + no.toString());
	var pos = $section.offset().top;
	$("body,html").animate({
		scrollTop : pos
	}, "slow",function(){
		setTimeout(function(){
			isChange = true;
		},50);
	});

}

$(window).scroll(function(event){
	if(isChange){
		var scroll_height = $(document).scrollTop();
	
		for(var i=0; i<div_height.length; i++){
			if(div_height[i] < scroll_height &&  scroll_height < div_height[i+1]){			
				menu_div(i+1);
				break;
			}
		}
	}
	
	
});
 