var contextPath = getContextPath();
var loginID = getLoginID();
var userID = getUserID();
function getContextPath(){
	return contextPath;
}
function getLoginID(){
	return loginID;
}
function getUserID(){
	return userID;
}
$(document).ready(function(){
	swiperLoad();
});

/*게시글 드롭다운*/
$(document).on("click",'.board-dropdown',function(){
	$(this).find('ul').css('display','block');
	event.stopPropagation();
});
/*modal창 닫기*/
$('.close').click(function(){
    $(this).parents('.modal').css('display','none');
});

$(".board-image-list").on("click",function(){
	var select_bno = $(this).parents('.board').attr('data-bno');
	var select_memberid = $(this).parents('.board').find('.board-member-id').text();
	$('#full-image').css('display','block');
	$.ajax({
		url:contextPath+"/"+select_memberid+"/"+select_bno+"/photo",
		method:'post',
		data:{id: select_memberid, board_no : select_bno},
		async:false,
		success:function(data){
			$('.full-image-list').html(data);
			swiperLoad();
			
		}
	});
	event.stopPropagation();
});


function swiperLoad(){
	var swiper = new Swiper('.swiper-container', {	    
		// 현재 페이지를 나타내는 점이 생깁니다. 클릭하면 이동합니다.
		 pagination: {
		    el: '.swiper-pagination',
		    clickable: true,
		 },  
		navigation : { // 네비게이션
	        nextEl : '.swiper-button-next', // 오른쪽(다음) 화살표
	        prevEl : '.swiper-button-prev', // 왼쪽(이전) 화살표
	    },
	});
}

