// JavaScript Document
$(function(){
	 umengSelectIitate();//select_imitate
	 umengRadioImitate();//radio_imitate
	 umengInputText();
	 //umengCheckImitate();
	 umengADsystemTabCommon();
	 chooseTime();//����ʱ��ѡ���
	 newsScrollShowOrHide();//�ж��Ƿ���ʾ������Ϣ
})

//==select_imitate start==
function umengSelectIitate(){
	$('.select_imitate').click(function(){
		//ie6 for overflow auto
		if($.browser.msie&&($.browser.version < "7.0")){
			if($(this).children('.pop_menu').height()>150){
				$(this).children('.pop_menu').css('height','144px');
			}
		}
		//click slide
		$(this).children('.pop_menu').slideToggle(300);
	})
	$('.select_imitate .pop_menu ul li a').click(function(){
		$(this).parent().parent().parent().prev('.text').html($(this).html());
	})
	$(document).click(function(){
		$('.select_imitate .pop_menu').hide();
	})
	$('.select_imitate').click(function(e){
		if (e.stopPropagation) e.stopPropagation();
		else e.cancelBubble = true;
	})
}
//==select_imitate start==
//==radio_imitate start==
function umengRadioImitate(){
	$('.radio_imitate').click(function(){
		
		//if($(this).hasClass('radio_imitate_checked')){
			//$('.radio_imitate').removeClass('radio_imitate_checked');
			//$(this).removeClass('radio_imitate_checked');
		//}
		//else{
			$('.radio_imitate').removeClass('radio_imitate_checked');
			$(this).addClass('radio_imitate_checked');
		//}
		
	})
}
//==radio_imitate end==

//==input_text start==
function umengInputText(){//for ie6 mouse effect
	if($.browser.msie&&($.browser.version < "8.0")){
		
		//input_text
		$('.input_text').focus(function(){
			$(this).addClass('input_text_focus');
		})
		$('.input_text').blur(function(){
			$(this).removeClass('input_text_focus');
		})
		
		//input_text_inner
		$('.input_text_combo_rmb .input_text_inner').focus(function(){
			$(this).addClass('input_text_inner_focus');
		})
		$('.input_text_combo_rmb .input_text_inner').blur(function(){
			$(this).removeClass('input_text_inner_focus');
		})
		
		//textarea
		$('.input_textarea').focus(function(){
			$(this).addClass('input_text_focus');
		})
		$('.input_textarea').blur(function(){
			$(this).removeClass('input_text_focus');
		})
	}
}
//==input_text end==


//umengADsystem_tab_common start
function umengADsystemTabCommon(){
	$('.umengADsystem_tab_common li').click(function(){
		$('.umengADsystem_tab_common li').removeClass('on');
		$('.umengADsystem_tab_common .box').removeClass('box_on');
		var tempNum =$(this).parent().find("li").index($(this)[0]);
		$(this).addClass('on');
		$('.umengADsystem_tab_common .box').eq(tempNum).addClass('box_on');
	})
}
//umengADsystem_tab_common start
//choose_time start
function chooseTime(){
	$('.choose_time').click(function(e){
		if (e.stopPropagation) e.stopPropagation();
		else e.cancelBubble = true;
		$(this).children('.pop_contont').toggle(0);
	})
	$('.choose_time .pop_contont').click(function(e){
		if (e.stopPropagation) e.stopPropagation();
		else e.cancelBubble = true;
	})
	$(document).click(function(){
		$('.choose_time .pop_contont').hide(0);
	})
	$('.choose_time .pop_contont .btn_cancel').click(function(){
		$('.choose_time .pop_contont').hide(0);
	})
}
//choose_time end

//���Ź���start
function newsScrollShowOrHide(){
	if($('.news_wrap .content_news li').length==0){
		$('.news_wrap').remove();
	}
	if($('.news_wrap .content_news li').length>1){
		newsScroll();
	}
}
function newsScroll(){
var _wrap=$('#news_tick');//�����������
	var _interval=2000;//���������϶ʱ��
	var _moving;//��Ҫ����Ķ���
	_wrap.hover(function(){
		clearInterval(_moving);//������ڹ���������ʱ,ֹͣ����
	},function(){
		_moving=setInterval(function(){
			var _field=_wrap.find('li:first');//�˱������ɷ����ں�����ʼ��,li:firstȡֵ�Ǳ仯��
			var _h=_field.height();//ȡ��ÿ�ι����߶�(���й��������,�˱����������ڿ�ʼ��,������м��ʱ����ʱ)
			_field.animate({marginTop:-_h+'px'},600,function(){//ͨ��ȡ��marginֵ,���ص�һ��
				_field.css('marginTop',0).appendTo(_wrap);//���غ�,�����е�marginֵ����,�����뵽���,ʵ���޷����
			})
		},_interval)//�������ʱ��ȡ����_interval
	}).trigger('mouseleave');//��������ʱ,ģ��ִ��mouseleave,���Զ�����
}
//����end
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
//umengADsystem_tab_common start
function closeNotice(link){
	$(link).parent().remove();
}