var boardNO = getBoardNO();
function getBoardNO(){
	return boardNO;
}
$(document).ready(function(){
	commentList(boardNO);
});
function addComment(formName){
	$.ajax({
		url:contextPath+"/comment/add",
		type:'post',
		cache : false,
		data: $('#'+formName).serialize(),
		success: function(data){
			$('form').each(function() { this.reset(); });
			commentList(boardNO);
			send_alarms(data);
		},
		error:function(request,status,error){
			   alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

	function deleteComment(rep_no){
		$.ajax({
			url:contextPath+"/comment/delete",
			type:'post',
			data: {rep_no : rep_no},
			success: function(data){
				commentList(boardNO);
			},
			error:function(request,status,error){
				   alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}

	function commentList(board){
		var cmt = "";
			$.ajax({
				url: contextPath+"/comment/list",
				type:'post',
				cache : false,
				data: {bno:board},
				success: function(data){
					if(data){	
						var str = "";	
						var comment_cnt = 0;	
						$.each(data, function(index, value){
							str =  `<span class='member-profile'>
										<a href='${contextPath}/${value.member.id}/profile'>
											<span class='member-image'>
												<img src="${contextPath}/resources/images/${value.member.profile.image_file.save_name}"/>
											</span>
											<span class='member-name'>
												${value.member.profile.name}<br>
												<span class='member-id'>@${value.member.id}</span>
											</span>
										</a>
									</span>
									<span class='comment-txt'>`;
									if(value.del_chk === 'N'){
										str += `${value.content}`; 
										comment_cnt++;
									}else if(value.del_chk === 'Y'){
										str += `삭제된 댓글입니다`;
									}
									
							str	+= `</span><span class='comment-date'>${value.regdate}</span>`;
								if(loginID === value.member.id && value.del_chk === 'N'){
									str += `<div>
											<span class='comment-delete' onclick="javascript:deleteComment(${value.repno});">
												삭제
											</span>
											</div>`;
								}
											
							if(value.parent_id == 0){
								cmt += `<div class='comment-list'>${str}</div>`;
								if(value.del_chk === 'N')
									cmt += `<span class='re-comment'>답글달기</span>
											<div class='re-comment-form'>
												<form id='recomment-form-${value.repno}' method='POST'>
													<input name='bno' value='${value.bno}' type='hidden' />
													<input name='parent_id' value='${value.repno}' type='hidden'/>
													<textarea name='content' rows='1' cols='50' placeholder='답글달기'></textarea>
													<button type='button' onclick="javascript:addComment('recomment-form-${value.repno}');" >작성</button>
												</form>
											</div>`;
							}
							else{
								cmt += `<div class='comment-re'><span>└</span>${str}</div>`;								
							}
						});
						$('.comment').html(cmt);
						$('.comment-cnt').text(comment_cnt);
					}
				}
			});
		}

 	$(document).on('click','.re-comment', function(){
 	 	$('.re-comment-form').css('display','none');
 		$(this).next().css('display','block');
 	 });