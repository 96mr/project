/*modal창 닫기*/
$('.close').click(function(){
    $(this).parents('.modal').css('display','none');
});

function liker_list(e, board){
	$('#modal-like').css('display','block');
	Likes(e, board);
}
	
function likeButton(board){
	if($('[data-bno = '+board+']').find('.like').hasClass('active') === false){
		$.ajax({
			url: contextPath+'/like/add',
			type:'POST',
			data:{no: board},    
			success:function(data){
				isLike(board);
				Likes(board);
				send_alarms(data);
			},
			error: function(error){
	  	  	  	if(error.status == 500){
	  	  	  		alert('로그인해주세요');
	  	  	  		location.href = contextPath + '/login';
		  	  	}
	  	  	}
		});
	}else{
		$.ajax({
			url: contextPath+'/like/delete',
			type:'POST',
			data:{no: board},
			success:function(data){
				isLike(board);
				Likes(board);
			},
			error:function(request,status,error){
				   alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
}

function isLike(board){
	var result = 0;
	$.ajax({
		url: contextPath+'/islike/'+board ,
		type:'post',
		data:{no: board},
		async : false,
		success:function(data){
			if(data == 1){
				$('[data-bno ='+ board+']').find('.like').addClass('active');
				$('[data-bno ='+board+']').find('.unlike').removeClass('active');
				result = data;
			}else{
				$('[data-bno ='+ board+']').find('.unlike').addClass('active');
				$('[data-bno ='+ board+']').find('.like').removeClass('active');
			}	
		},
		error:function(request,status,error){
			   alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
	});
	return result;
}
	
function Likes(board){
	var str="";
	$.ajax({
		url:contextPath+'/likes/'+board,
		type:'post',
		data:{no: board},
		success:function(data){
			$('[data-bno = ' + board + ']').find('.like-cnt').text(data.length); //좋아요 한 사람 수
			if(data.length != 0){					
				$.each(data, function(index, value){
					var isF = isFollow(value.id);
					str += `<div class='modal-li'>
							<a href='${contextPath}/${value.id}/profile'>
				  			<span class='profile-member-image'>
				  			<img src="${contextPath}/resources/images/${value.profile.image_file.save_name}"/></span>
				  			<span class='profile-member-name'>
				  			<span id='#following-name'>${value.profile.name}</span><br>
				  			<span id='#following-id'>${value.id}</span></a>`;
				 	if(loginID != value.id){
				 		str += `<button type='button' class='follow-btn follow-list 
							${isF == 1 ?'active':''}' onclick="javascript:follow_btn(this,'${value.id}');">팔로우</button>`;
					}
				});
			}else{
				str =`<div>아직 좋아요를 누른 사람이 없습니다.</div>`;
			}	
			$('.modal-list.liker').html(str);
		}
	});
}
