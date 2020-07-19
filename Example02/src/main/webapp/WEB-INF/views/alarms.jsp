<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/resources/css/mycustom.css" />
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<script src="//code.jquery.com/jquery-3.2.1.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<title>알림</title>
</head>
<body>
	<div class="allcontainer">
		<%@ include file="/WEB-INF/views/main_nav.jsp"%>

		<section class="content">
			<div class="pg-header">
				<span class="pg-header-center">알림</span>
				<span class="pg-header-right"></span>
			</div>
			<article class="alarmlist">
				<c:if test="${empty alarm_list}">
					<div class="board-empty">
						<div>새로운 알림이 없습니다</div>
					</div>
				</c:if>
				<c:forEach var="list" items="${alarm_list}" varStatus = "cnt"> <!-- 카운트를 세서 새로운 알림이랑 비교해야지  -->
					<c:choose>
					<c:when test="${new_notice_cnt == 0}">
						<c:if test="${cnt.index == 0 }">
							<div class="alarm_chk_text">읽은 알림</div>
						</c:if>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${cnt.index == 0}">
								<div class="alarm_chk_text">읽지 않은 알림</div>
							</c:when>
							<c:when test="${cnt.index == new_notice_cnt}">
								<div class="alarm_chk_text">읽은 알림</div>
							</c:when>
						</c:choose>	
					</c:otherwise>
					</c:choose>
					
					<div class="board">
						<div>
							<!--글 하나씩-->
							<div>
								<a href="${pageContext.request.contextPath}/${list.member.id}/profile">
									<span class="board-member-image"><img src="${pageContext.request.contextPath }/resources/images/${list.member.profile.image_file.save_name}" /></span><br>
									<span class="board-member-name">${list.member.profile.name }</span>
								</a>
								<c:choose>
									<c:when test="${list.notetype eq 'follow' }">
										<span class="alarm-content alarm-follow">님이 나를 팔로우 합니다</span>
										<button type="button" id="addFollow_btn" class="follow-btn" data-name="${list.member.id}" onclick ="javascript:follow_btn(this,'${list.member.id}')" >팔로우</button>
									</c:when>
									<c:when test="${list.notetype eq 'like' }">
										<span class="alarm-content">님이 <a href="${pageContext.request.contextPath}/${sessionID}/${list.bno}"><b>내 글</b></a>을 좋아합니다</span>
									</c:when>
									<c:otherwise>
										<span class="alarm-content">님이 댓글을 남겼습니다<br></span>
										<div class="alarm-content-reply">
											<a href="${pageContext.request.contextPath}/${sessionID}/${list.bno}">
												${list.content }								
											</a>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</c:forEach>
			</article>
		</section>
		
		
	</div>

	<script type="text/javascript" charset="utf-8">
        var contextPath = "<c:out value='${pageContext.request.contextPath}'/>";
		var loginID = "<c:out value='${sessionID}'/>";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/like.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/follow.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/mycustom.js"></script>
	<script>
	$(document).ready(function() {
		$('.board button').each(function(){
			var isF = isFollow($(this).attr('data-name'));
			if(isF == 1)
				$(this).addClass('active');
		});
	});
    </script>
</body>
</html>
