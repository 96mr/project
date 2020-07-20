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


<title>${board.member.profile.name }님의 글</title>
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
			<c:choose>
			<c:when test="${empty board }">
				<article class="timeline">
					<div class="board">
						<!--글 상세-->
						<div>
							<div class="board-txt" style="text-align:center"><b>존재하지 않는 페이지입니다.</b></div>
						</div>
					</div>
				</article>
			</c:when>
			<c:otherwise>
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
			</c:otherwise>
			</c:choose>
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
		
		<div id="board-modal">
			<ul>
				<li></li>
				<li><i class="fas fa-trash-alt"></i><span id="board-delete">삭제</span></li>
				<li><span>기능</span></li>
			</ul>
		</div>

		<div id ="board-popup" class="modal">
			<div class="modal-context">
			</div>
		</div>
	</div>
	
	<script type="text/javascript" charset="utf-8">
        var contextPath = "<c:out value='${pageContext.request.contextPath}'/>";
		var loginID = "<c:out value='${sessionID}'/>";
		var userID = "<c:out value='${board.member.id}'/>";
		var boardNO = "<c:out value='${board.bno}'/>"
	</script>	
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/like.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/follow.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/mycustom.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/websocket.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/comment.js"></script>
</body>
</html>
