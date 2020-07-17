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

<title>아이디 찾기</title>
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
				<div>
					<input type="radio" id="user_email" name="how_find_id" value="user_email"><label for="user_email">가입한 이메일로 찾기</label>
					<div id="find_by_email" class="how_find">
						<form method ="post">
							<input type="email" name="email" autocomplete="off" placeholder="이메일 주소 전체를 입력해주세요."/>
							<button type="submit" name="button">확인</button>
						</form>
					</div>
				</div>
				<div style="color:gray">
					<input type="radio" id="user_email" name="how_find_id" value="user_phone" disabled><label for="user_phone">가입한 핸드폰 번호로 찾기</label>
				</div>
			</div>
		</section>
	</div>
	<script>
	 $("input:radio[name=how_find_id]").click(function(){ 
	        if($("input[name=how_find_id]:checked").val() == "user_email"){
	            $("#find_by_email").css("display","block");
	        }else if($("input[name=how_find_id]:checked").val() == "user_phone"){
	        	$("#find_by_email").css("display","none");
	        }
	    });
	</script>
</body>
</html>
