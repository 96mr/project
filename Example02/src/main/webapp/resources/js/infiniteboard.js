var cur_pg = 1;
$(window).scroll(function() {// 스크롤 이벤트가 발생할 때마다 인식
	
	if($(this).scrollTop() >= ($(document).height() - $(this).height() - 1)  ) {// 스크롤이 끝에 닿는걸 인식
		var url = document.location.href.substring(document.location.href.lastIndexOf("/")+1);
		var last_bno = $(".board").attr("data-bno");
		infiniteBoard(last_bno, url);
		var height = $(document).scrollTop();
		$('html, body').stop().animate({scrollTop : height+400},600);
	}
});

function infiniteBoard(last_bno, url){
	var send_data = null;
	if(url == ""){
		send_data = {"page" : cur_pg};
	}else if(url.startsWith("search")){
		var params = url.substring(url.indexOf("?")+1);
		params = params.split("&");
	    for (var i = 0, result={}; i < params.length; i++) {
	        temp = params[i].split("=");
	        result[i] = temp[1];
	    }
		send_data = {"page" : cur_pg, "kwd": result[0], "option": result[1]};
		url = "/search";
		console.log("search");
	}else{
		if(url === "profile") tab = 0;
		else if(url === "writing") tab = 1;
		else if(url === "media") tab = 2;
		else if(url === "likes") tab = 3;
		send_data = {"page":cur_pg, "user":userID, "tab":tab};
	    url = "/profile";
	}
	
	$.ajax({
		url: contextPath+"/infiniteBoard"+url,
		type:"post",
		data:send_data,
		async:false,
		success: function(data){
			var str = "";
			if(data != null){
				$.each(data, function(index, value){
					str += "<div class='board' data-bno='"+value.bno+"'>"
					+"<div onclick='location.href =\""+contextPath+"/"+value.member.id+"/"+value.bno+"\"'>";
					if(send_data.tab == 2){
						str +="<div class='board-image-list'>"
							+"<span><img src='"+contextPath+"/resources/images/"+value.files[0].save_name+"'/></span></div>";
					}
					else{
						str +="<div><a href='"+contextPath+"/"+value.member.id+"/profile'>"
							+"<span class='board-member-image'>"
							+"<img src='"+contextPath+"/resources/images/"+value.member.profile.image_file.save_name+"'/></span>"
							+"<span class='board-member-name'>"+value.member.profile.name+"</span>"
							+"<span class='board-member-id'>@"+value.member.id+"</span></a>";
						
						if(value.files){
							str +="<div class='swiper-container'><div class='swiper-wrapper'>";
							value.files.forEach(function(files){
								str += "<div class='swiper-slide'><div class='board-image-list'>"
									+"<span><img src='"+contextPath+"/resources/images/"+files.save_name+"'/></span></div></div>";
							});
							str+="</div><div class='swiper-pagination'></div></div>";
						}		
						str +="<div class='board-txt'>"+value.content+"</div></div></div>";
					}
						str	+="<div class='board-bottom'><div class='board-icon'>"
							+"<span onclick='likeButton('value.bno')'style='color: red;'>";
							if(value.islike == 0){
								 str +="<span class='unlike active'><i class='far fa-heart'></i></span>"
									 +"<span class='like'><i class='fas fa-heart'></i></span></span>";
							}else if(value.islike == 1){
								 str +="<span class='unlike'><i class='far fa-heart'></i></span>"
									 +"<span class='like active'><i class='fas fa-heart'></i></span></span>";
						 	}
						str += "<span class='like-cnt' onclick='liker_list('value.bno')'>"+(value.liker_list).length+"</span></div>"
						    + "<div class='board-icon'><a href='"+contextPath+"/"+value.member.id+"/"+value.bno+"'>"
							+ "<span><i class='far fa-comment-alt'></i></span>"
							+ "<span class='comment-cnt'>"+value.reply.length+"</span></a></div><span class='board-regdate'>"+value.regdate+"</span>";
						
						if(loginID === value.member.id){
							str += "<div class='board-dropdown'><i class='fas fa-angle-down fa-2x'></i>"
								+"<ul><li><a><i class='fas fa-trash-alt'></i>삭제</a></li><li><a>취소</a></li></ul></div>";
						}
					str +="</div></div>";
					
					str = str.replace(/'value.bno'/gi, value.bno);
				});
				$(".board:last").after(str);
				cur_pg++;
				swiperLoad();
			}
		},error:function(request,status,error){
			   alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});	
}


