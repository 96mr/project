<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mycustom.css" />
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<script src="//code.jquery.com/jquery-3.2.1.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<title>문제 발생</title>
</head>
<body>
	<c:if test="${not empty msg}">
		<script>
			alert("${msg}");
		</script>
	</c:if>
	<div class="allcontainer">
		<%@ include file="/WEB-INF/views/main_nav.jsp"%>
		<section class="content">
			<div class="pg-header">
				<span class="pg-header-center"></span>
				<span class="pg-header-right"></span>
			</div>
			<article class="timeline">
			<div style="text-align:center">
				<b>
					<c:if test="${requestScope['javax.servlet.error.status_code'] == 400}">
						잘못된 요청입니다
					</c:if>
					<c:if test="${requestScope['javax.servlet.error.status_code'] == 404}">
						요청하신 페이지를 찾을 수 없습니다
					</c:if>
					<c:if test="${requestScope['javax.servlet.error.status_code'] == 500}">
						문제가 발생하였습니다
					</c:if>
				</b><br>
				<a href="${pageContext.request.contextPath}/home">홈으로 돌아가기</a>
			</div>
			</article>
		</section>
	</div>

	<script type="text/javascript" charset="utf-8">
        var contextPath = "<c:out value='${pageContext.request.contextPath}'/>";
		var loginID = "<c:out value='${sessionID}'/>";
		var userID = "<c:out value='${user.id}'/>";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/like.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/follow.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/mycustom.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/infiniteboard.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/websocket.js"></script>
</body>
</html>
