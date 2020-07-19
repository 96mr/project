<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
				<span class="pg-header-center">설정</span>
				<span class="pg-header-right"></span>
			</div>
			<div>
				<div style="width:100%;">
					<div style="padding:2%; font-size:150%;border-bottom:1px solid black"><b>회원정보</b></div>
					<div style="padding:2% 5%; font-size:110%;border-bottom:1px solid black"><a href="${pageContext.request.contextPath}/settings/account">
						<span style="padding-right:80%;"><span>회원정보 변경</span></span><i class="fa fa-angle-right" aria-hidden="true"></i></a>
					</div>
					<div style="padding:2% 5%; font-size:110%;border-bottom:1px solid black"><a href="${pageContext.request.contextPath}/settings/password">
						<span style="padding-right:80%;"><span>비밀번호 변경</span></span><i class="fa fa-angle-right" aria-hidden="true"></i></a>
					</div>
					<%-- <div style="padding:2% 5%; font-size:110%;border-bottom:1px solid black"><a href="${pageContext.request.contextPath}/">
						<span style="padding-right:80%;"><span>공개범위</span></span><i class="fa fa-angle-right" aria-hidden="true"></i></a>
					</div> --%>
				</div>
			</div>

		</section>
	</div>
</body>
</html>