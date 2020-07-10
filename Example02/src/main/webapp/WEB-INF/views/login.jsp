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
		<nav>
			<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath }/resources/images/logo.png" width="50px" alt="logo" /></a>
		</nav>
		<section class="content-login">
			<h1>로그인</h1>
			<form method="post">
				<input type="text" name="id" autocomplete="off" placeholder="아이디를 입력하세요"><br /> 
				<input type="password" name="password" placeholder="비밀번호를 입력하세요"><br />
				<button type="submit" >로그인</button>
			</form>
			<p>
				<a href="">아이디찾기</a>|<a href="">비밀번호찾기</a> <a href="${pageContext.request.contextPath}/join">회원가입</a>
			</p>
		</section>
	</div>
	<script>

	</script>
</body>
</html>
