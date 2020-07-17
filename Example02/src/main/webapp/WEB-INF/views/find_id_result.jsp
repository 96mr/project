<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
<link rel="stylesheet" href="/resources/css/mycustom.css" />
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
<script src="//code.jquery.com/jquery-3.2.1.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>

<title>아이디 찾기 결과/title>
</head>
<body>
<c:if test ="${not empty msg}">
	<script>
		alert("${msg}");
	</script>
</c:if>
	<div class="allcontainer">

		<section class="user-find">
			<div class="user-find-header">
				<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath }/resources/images/logo.png" width="50px" alt="logo" /></a>
				<h1>아이디 찾기</h1>
			</div>
			<div>
				<div>${result_count}개의 아이디를 찾았습니다</div>
				<c:forEach var="list" items="${result}">
					<div>
						<input type="radio" name="user_id" readonly>${list }
					</div>
				</c:forEach>
			</div>
			<div>
				<a href="${pageContext.request.contextPath}/login" type="button">로그인 하러 가기</a>
			</div>
		</section>
	</div>
	<script>
	</script>
</body>
</html>
