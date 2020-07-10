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
				<span class="pg-header-center">비밀번호변경</span>
				<span class="pg-header-right"></span>
			</div>
			<div class="setting-form">
				<form method="post" autocomplete="off">
					<h5>현재 비밀번호</h5>
					<input  type="password" name="pw" id="user_CurrentPw" placeholder="현재 비밀번호를 입력하세요"/><br />
					<h5>비밀번호 변경</h5>
					<input  type="password" name="new_pw" id="userPw" placeholder="변경할 비밀번호를 입력하세요"/><br />
					<small id="pwNotice"></small><br>
					<h5>비밀번호 확인</h5>
					<input  type="password" name="new_pwChk" id="pwChk" placeholder="변경할 비밀번호 확인"/><br />
					<small id="pwChkNotice"></small><br>
					<button type="submit" name="button">수정하기</button>
			</form>
			</div>

		</section>
	</div>
	<script>
	$("#userPw").keyup(function(){
		var pwNotice = $('#pwNotice');
		$.ajax({
			url:'${pageContext.request.contextPath}/pwCheck',
			type:'post',
			data:{"pw":$("#userPw").val()},
			success:function(data){
					if($.trim($("#userPw").val())==null||data == 0){
						pwNotice.text('비밀번호는 8~30글자로 숫자, 영문자를 조합해주세요.');
	   	   	   	   	   	pwNotice.css('color','red');
					}else{
						pwNotice.text('');
	   	   	   	   	 	pwNotice.css('color','blue');
					}
						
				}
		});
	});
	
	$(function(){	//비밂번호 체크
		$('#pwChk').keyup(function(){
			$('#pwChkNotice').text('');
		});

		$('#pwChk').keyup(function(){
			if($('#userPw').val() != $('#pwChk').val()){
				$('#pwChkNotice').text('비밀번호 일치하지 않음');
				$('#pwChkNotice').css("color", "red");
				return false;
			} else{
				$('#pwChkNotice').text('비밀번호 일치함');
			    $('#pwChkNotice').css("color", "blue");
			}
		});

	});
	</script>
</body>
</html>