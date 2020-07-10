/*팔로우 버튼*/
function follow_btn(e, str){                       //팔로우, 언팔로우
  	if($(e).hasClass('active')=== false){
		$.ajax({
	  		url: contextPath+"/follow/add", 
	    	data: { id: str },          
	  	  	type : "POST",
	  	  	success: function(data){
	  	  	  	$(e).addClass('active');
	  	  	  	ff_count();
	  	  	},
	  	  	error: function(error){
	  	  	  	if(error.status == 500){
	  	  	  		alert('로그인해주세요');
	  	  	  		location.href = contextPath + '/login';
		  	  	}
	  	  	}
  	  	});
  	}
  	else{
  		$.ajax({
  			url: contextPath+"/follow/delete", 
  	    	data: { id: str },          
  	  	  	type : "POST",
  	  	  	success: function(data){
  	  	  		$(e).removeClass('active');
  	  	  	  	ff_count();
  	  	  	},
	  	  	error: function(error){
	  	  	  	if(error.status == 500){
	  	  	  		alert('로그인해주세요');
	  	  	  		location.href = contextPath + '/login';
		  	  	}
	  	  	}
  	  	}); 
  	}
	}
	
$('.following-list').click(function(){   //팔로잉 리스트
	$('#modal-follow').css('display','block');
	following_list();
		
	$('.modal-header .following-list').addClass('current');
	$('.modal-header .follower-list').removeClass('current');
	$('.modal-list.following').css('display','block');
	$('.modal-list.follower').css('display','none');
});

$('.follower-list').click(function(){    //팔로워 리스트
	$('#modal-follow').css('display','block');
	follower_list();
	
	$('.modal-header .following-list').removeClass('current');
	$('.modal-header .follower-list').addClass('current');
	$('.modal-list.follower').css('display','block');
	$('.modal-list.following').css('display','none');
	
});
	
function ff_count(){ //팔로잉팔로워수
	$.ajax({
	    url:contextPath+"/follow/count",
	  	data: {id:userID},
	  	type : "post",
	  	success: function(data){
	  		$('.following-list b').text(data.follow);
	  	  	$('.follower-list b').text(data.follower);
	  	}
  	});
}

function following_list(){
  	var str="";
	$.ajax({
		url: contextPath+"/following", 
	    data: { id: userID },                
	    type: "POST",                             
	    dataType: "json",                      
		success: function(data){
			$.each(data, function(index, value){
				var isF = isFollow(value.member.id);
				str +="<div class='modal-li'>"
					+"<a href='"+contextPath+"/"+value.member.id+"/profile'>"
			  		+"<span class='profile-member-image'>" 
			  		+"<img src='"+contextPath+"/resources/images/"+value.member.profile.image_file.save_name+"' /></span>"
			  		+"<span class='profile-member-name'>"
			  		+"<span id='#following-name'>"+value.member.profile.name+"</span><br>"
			  		+"<span id='#following-id'>"+value.member.id+"</span></a>";
			  	if(loginID != value.member.id){
					str += "<button type='button' class='follow-btn follow-list ";
					if(isF == 1)
						str+="active";
					str +="' onclick='javascript:follow_btn(this,'value.id');'>팔로우</button></div>";
				}
			  	str = str.replace(/'value.id'/gi, '"' + value.member.id + '"');
			});
			$('.modal-list.following').html(str);
		}
	});
}

function follower_list(){
	var str= "";
	$.ajax({
	    url: contextPath+"/follower", 
	    data: { id: userID },                
	    type: "POST",                             
	    dataType: "json",                      
		success: function(data){
			$.each(data, function(index, value){
				var isF = isFollow(value.member.id);
				str +="<div class='modal-li'>"
					+"<a href='"+contextPath+"/"+value.member.id+"/profile'>"
			  		+"<span class='profile-member-image'><img src='"+contextPath+"/resources/images/"+value.member.profile.image_file.save_name+"' /></span>"
			  		+"<span class='profile-member-name'>"
			  		+"<span id='#following-name'>"+value.member.profile.name+"</span><br>"
			  		+"<span id='#following-id'>"+value.member.id+"</span></a>";
			  	if(loginID != value.member.id){
				  	str += "<button type='button' class='follow-btn follow-list ";
					if(isF == 1)
						str+="active";
						str +="' onclick='javascript:follow_btn(this,'value.id');'>팔로우</button></div>";
			  		}
			  	str = str.replace(/'value.id'/gi, '"' + value.member.id + '"');
		  		
			});
			$('.modal-list.follower').html(str);
		}
	});
}


/*팔로우 중인가?*/
function isFollow(str){
	var result = 0;
	$.ajax({
	    url: contextPath+"/isFollow", 
	    data: { id: str },                
	    type: "POST",
	    async: false,                                      
		success: function(data){
			if(data == 1){			
				result = data;
			}
		}
	});
	return result;
}