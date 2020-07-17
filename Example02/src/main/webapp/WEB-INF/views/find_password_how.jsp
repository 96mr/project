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

<title>홈</title>
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
				<h1>비밀번호 찾기</h1>
			</div>
			<div>
				<div>
					<div>임시 비밀번호를 받을 방법을 선택해주세요</div>
					<form method="post" action="/find/password/how">
						<label>계정 :</label><input type="text" name="user_id" value="${user_id}" style="border:none;" readonly/><br>
						<c:if test="${not empty to_email}">
							<input type="radio" name="how" value="email" checked><input type="text" name="where" value="${to_email}" style="border:none;" readonly/>
						</c:if>
						<c:if test="${not empty to_phone}">
							<input type="radio" name="how" value="phone" checked><input type="text" name="where" value="${to_phone}" style="border:none;" readonly/>
						</c:if>
						<br>
						<button type="submit">전송</button>
					</form>
				</div>
			</div>
		</section>
	</div>
	<script>
	</script>
</body>
</html>
