<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="true" %>
<html>
<head>
<link rel="stylesheet" href="/resources/css/mycustom.css" />
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
<script src="//code.jquery.com/jquery-3.2.1.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>


<title>홈</title>
</head>
<body>
<div class="allcontainer">
    <%@ include file="/WEB-INF/views/main_nav.jsp" %>

		<section class="content">
			<div class="pg-header">
				<a href="#" onClick="history.back()" style="color:#9E6BD1"><i class="fas fa-chevron-left "></i></a>
				<span class="pg-header-center">게시글</span>
				<span></span>
			</div>
			<article class="timeline">
				<div class="board" data-bno=${board.bno }>
					<!--글 상세-->
					<div>
						<a href="${pageContext.request.contextPath}/${board.member.id}/profile">
							<span class="board-member-image"><img src="${pageContext.request.contextPath }/resources/images/${board.member.profile.image_file.save_name}" /></span> 
							<span class="board-member-name">${board.member.profile.name }</span> 
							<span class="board-member-id">@ ${board.member.id }</span>
						</a>
						<c:if test="${sessionID eq board.member.id }">
							<div class="board-dropdown">
								<i class="fas fa-angle-down fa-2x"></i>
								<ul>
									<li><a><i class="fas fa-trash-alt"></i>삭제하기</a></li>
									<li><a>취소</a></li>
								</ul>
							</div>
						</c:if>
						<c:if test="${not empty board.files }">
							<c:forEach var="files" items="${board.files }">
								<div class="board-image-list">
									<span>
										<img src="${pageContext.request.contextPath }/resources/images/${files.save_name }"/>
									</span>
								</div>
							</c:forEach>
						</c:if>
						<div class="board-txt">${board.content }</div>
					</div>
          			<div class="board-bottom">
						<div class="board-icon"> 
							<span onclick="likeButton(${board.bno})" style="color:red;">
								<span class="unlike <c:if test='${board.islike == 0 }'>active</c:if>"><i class='far fa-heart'></i></span> 
								<span class="like <c:if test='${board.islike == 1 }'>active</c:if>"><i class='fas fa-heart'></i></span>
							</span>
							<span class="like-cnt" onclick="liker_list(${board.bno})">${fn:length(board.liker_list)}</span>
						</div> 
						<div class="board-icon">
							<a href="${pageContext.request.contextPath}/${board.member.id}/${board.bno}">
								<span><i class="far fa-comment-alt"></i></span>
								<span class="comment-cnt">${fn:length(board.reply)}</span>
							</a>
						</div>
						<span class='board-regdate'>${board.regdate}</span>
					</div>
				</div>
	
			</article>
			<div class="board_comment">
				<span>댓글</span>
				<div class="add-comment">
					<c:choose>
						<c:when test="${sessionID == null }">
							<span><a href="${pageContext.request.contextPath}/login">로그인 후 이용하실 수 있는 서비스 입니다.</a></span>
						</c:when>
						<c:otherwise>
							<form id="comment-form" method="POST">
								<input name="bno" value="${board.bno}" type="hidden"></input>
								<textarea name="content" rows="3" cols="50" placeholder="500자 이내 작성하시오"></textarea>
								<button type="button" onclick="addComment('comment-form')" >작성</button>
							</form>
						</c:otherwise>
					</c:choose>
				</div>
				
				<div class="comment">
				</div>
			</div>
		</section>
		
		<div id="modal-like" class="modal">
			<div class="modal-context">
				<div class="modal-header">
					<span>좋아요</span>
					<span class="close"><i class="fas fa-times"></i></span>
				</div>
				<div class="modal-list liker">
		
				</div>
			</div>
		</div>
		
		
	</div>
	
	<script type="text/javascript" charset="utf-8">
        var contextPath = "<c:out value='${pageContext.request.contextPath}'/>";
		var loginID = "<c:out value='${sessionID}'/>";
		var userID = "<c:out value='${user.id}'/>"
	</script>	
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/like.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/follow.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/mycustom.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/websocket.js"></script>
	<script>
	$(document).ready(function(){
		commentList("<c:out value='${board.bno}'/>");
  	});
  	
	function addComment(formName){
		var board = "<c:out value='${board.bno}'/>";
		console.log(formName);
		$.ajax({
			url:"${pageContext.request.contextPath}/comment/add",
			type:'post',
			cache : false,
			data: $('#'+formName).serialize(),
			success: function(data){
				$('form').each(function() { this.reset(); });
				commentList(board);
			},
			error:function(request,status,error){
				   alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}

	function deleteComment(rep_no){
		var board = "<c:out value='${board.bno}' />"
		console.log("삭제 번호"+rep_no);
		$.ajax({
			url:"${pageContext.request.contextPath}/comment/delete",
			type:'post',
			data: {rep_no : rep_no},
			success: function(data){
				commentList(board);
			},
			error:function(request,status,error){
				   alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}

	function commentList(board){
		var cmt = "";
		var recmt = "";
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
							str = "<span class='member-profile'><a href='"+contextPath+"/"+ value.member.id +"/profile'><span class='member-image'>"
									+"<img src='"+contextPath+"/resources/images/"+ value.member.profile.image_file.save_name +"' /></span>"
									+"<span class='member-name'>"+value.member.profile.name+"<br>"
									+"<span class='member-id'>@"+ value.member.id +"</span></span></a></span>"
									+"<span class='comment-txt'>";
									if(value.del_chk === 'N'){
										str += value.content; 
										comment_cnt++;
									}else if(value.del_chk === 'Y'){
										str += "삭제된 댓글입니다";
									}
									
							str	+= "</span><span class='comment-date'>"+value.regdate+"</span>";
									if(loginID === value.member.id && value.del_chk === 'N')
										str +="<div><span class='comment-delete' onclick='javascript:deleteComment('value.delete_repno');'>삭제</span></div>";
									
							if(value.parent_id == 0){
								cmt += "<div class='comment-list'>"+str+"</div>";
								if(value.del_chk === 'N')
									cmt +="<span class='re-comment'>답글달기</span><div class='re-comment-form'>"
										+"<form id='recomment-form-"+value.repno+"' method='POST'>"
										+"<input name='bno' value='"+value.bno+"' type='hidden' />"
										+"<input name='parent_id' value='"+value.repno+"' type='hidden'/>"
										+"<textarea name='content' rows='1' cols='50' placeholder='답글달기'></textarea>"
										+"<button type='button' onclick='javascript:addComment('value.repno');' >작성</button></form></div>";
							}
							else{
								cmt += "<div class='comment-re'><span>└</span>" + str +"</div>";								
							}
							cmt = cmt.replace(/'value.delete_repno'/gi, value.repno);
							cmt = cmt.replace(/'value.repno'/gi, '"recomment-form-' + value.repno + '"');
						});
						$('.comment').html(cmt);
						$('.comment-cnt').text(comment_cnt);
					}
				},
				error:function(request,status,error){
					   alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
		}

 	$(document).on('click','.re-comment', function(){
 	 	$('.re-comment-form').css('display','none');
 		$(this).next().css('display','block');
 	 });

	</script>
</body>
</html>