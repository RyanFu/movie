// JavaScript Document
$(function(){
   adjustHeight();
})
//�����Ʒ���ܺͰ�����Ŀ�߶�start
function adjustHeight(){
	$('.umengADportal_product .col_2 .col_l').removeAttr('style');
	var tempHeightleft= $('.umengADportal_product .col_2 .col_l').height();
	var tempHeightRight = $('.umengADportal_product .col_2 .col_r').height();
	if(tempHeightleft<=tempHeightRight){
		$('.umengADportal_product .col_2 .col_l').height(tempHeightRight);
	}
}
//�����Ʒ���ܺͰ�����Ŀ�߶�end


//����������ʾstart
function iconHelpPop(helpObj , popContent){
	var tempContent  =popContent;
	var tempHtml = '<span class="pop_content">'+tempContent+'</span>';
	
	$(helpObj).append(tempHtml);
	$(helpObj).hover(function(){
		$(this).children('.pop_content').slideDown(100);
	},function(){
		$(this).children('.pop_content').slideUp(100);
	})
}
//����������ʾend