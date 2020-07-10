<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/resources/css/mycustom.css" />
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
<script src="//code.jquery.com/jquery-3.2.1.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>


<title></title>
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
				<a href="#" onClick="history.back()" style="color: #9E6BD1"><i class="fas fa-chevron-left "></i></a> 
				<span class="pg-header-center">회원정보수정</span>
				<span class="pg-header-right"></span>
			</div>
			<div class="setting-form">
				<form:form method="post" commandName="vo" autocomplete="off">
				<h5 style="width:17%;margin:0.1em; display:inline-block">아이디</h5>
				<form:input path="id" type="text" name="id" id="userId" placeholder="아이디를 입력하세요"/><br />
				<small id="idNotice"></small><br>
				<form:errors path="id" element="small"/><br>	
				<h5>생년월일</h5>
				<input class="input-birth" type="text" name="birth" id="birth" placeholder="ex.19990101"/><br>
				<small id = "birthNotice"></small><br>
				<h5>이메일</h5>
				<input type="email" name="email" placeholder="선택항목 : 이메일을 입력하세요(ex.abcd@aaa.com)"/><br />
				<h5>휴대폰</h5>
				<input type="text" name="phone" placeholder="선택항목 : -를 제외한 번호만 입력해주세요(ex.01012345678)"/><br />
				<h5>현재 비밀번호</h5>
				<form:input path="password" type="password" name="pw" id="userPw" placeholder="수정하려면 현재 비밀번호를 입력하세요"/><br />
				<small id="pwNotice"></small><br>
				<form:errors path="password" element="small"/><br>
				<button type="submit" name="button">수정하기</button>
			</form:form>
			</div>

		</section>
	</div>
</body>
</html>