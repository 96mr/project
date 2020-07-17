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
	
	event.stopPropagation();
});
/*modal창 닫기*/
$(document).on("click",'.close',function(){
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

$(document).on("click",function(e){
	if(!($('.board-dropdown').has(e.target).length)){
		$('#board-modal').css('display','none');
	}
});

$(document).on("click",'.board-dropdown',function(e){
	var select_bno = $(this).parents('.board').attr('data-bno');
	$('#board-modal ul li:first').text(select_bno);
	$("#board-modal").css({
		"top":e.pageY-40 , 
		"left":e.pageX+40,
		"position": "absolute"
	}).show();
});

$('#board-delete').on("click",function(e){
	var bno = $(this).parents('ul').find('li:first').text();
	var str= "<div class='modal-content'><div>정말로 삭제하실건가요?</div><ul><li onclick='delete_board("+bno+")'>삭제</li><li class='close'>취소</li></ul></div>";
	console.log(bno);
	console.log(str);
	$('#board-popup .modal-context').html(str);
	$('#board-popup').css("display","block");
});

function delete_board(board){
	$.ajax({
		url:contextPath+"/board/delete",
		type:'post',
		data: {bno:board},
		success: function(data){
			$('#board-popup').css("display","none");
			if(data > 0){
				alert('글이 삭제되었습니다!');
				$('[data-bno = '+data+']').remove();
			}
			console.log(data);
		},
		error:function(request,status,error){
			   alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}
