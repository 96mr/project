<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
			<a href="${pageContext.request.contextPath}/"><img src="logo.png" width="50px" alt="logo" /></a>
		</nav>

		<section class="content-login">
			<h1>회원가입</h1>
			<form:form method="post" commandName="vo" autocomplete="off">
				<h3>필수항목</h3>
				<h5 style="width:17%;margin:0.1em; display:inline-block">아이디</h5>
				<form:input path="id" type="text" name="id" id="userId" placeholder="아이디를 입력하세요"/><br />
				<small id="idNotice"></small><br>
				<form:errors path="id" element="small"/><br>	
				<h5>비밀번호</h5>
				<form:input path="password" type="password" name="pw" id="userPw" placeholder="비밀번호를 입력하세요"/><br />
				<small id="pwNotice"></small><br>
				<form:errors path="password" element="small"/><br>
				<h5>비밀번호 확인</h5>
				<input type="password" name="pwChk" id="pwChk" placeholder="비밀번호를 입력하세요"/><br />
				<small id="pwChkNotice"></small><br>
				<h3>선택항목</h3>
				<h5>생년월일</h5>
				<input class="input-birth" type="text" name="birth" id="birth" placeholder="ex.19990101"/><br>
				<small id = "birthNotice"></small><br>
				<h5>이메일</h5>
				<input type="email" name="email" placeholder="선택항목 : 이메일을 입력하세요(ex.abcd@aaa.com)"/><br />
				<h5>폰번호</h5>
				<input type="text" name="phone" placeholder="선택항목 : -를 제외한 번호만 입력해주세요(ex.01012345678)"/><br />
				<button type="submit" name="button">가입하기</button>
			</form:form>
		</section>
	</div>
	<script>
   //유효성 검사 ajax (이미 존재하는 아이디, 패스워드)
   	$('#userId').keyup(function(){
   	   	var idNotice = $('#idNotice');
   	   	$.ajax({
   	   	   	url:'${pageContext.request.contextPath}/idCheck',
   	   	   	type:'post',
   	   	   	data:{"id":$("#userId").val()},
   	   	   	success:function(data){
   	   	   	   	console.log(data);
   	   	   	   	if(data == -1){ // 존재하는 아이디
   	   	   	   		idNotice.text('이미 존재하는 아이디입니다.');
	   	   	   		idNotice.css('color','red');
   	   	   	   	}else if($.trim($("#userId").val())==null || data == 0){
   	   	   	   	   	idNotice.text('아이디는 4~20글자로 숫자, 영문자를 조합해주세요.');
   	   	   	   	   	idNotice.css('color','red');
   	   	   	   	}else{
   	   	   	   	   	idNotice.text('사용가능한 아이디입니다.');
   	   	   	   	   	idNotice.css('color','blue');
   	   	   	   	}
   	   	   	}
   	   	 });
   	});
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
						pwNotice.text('사용가능한 비밀번호입니다');
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

		$('#birth').blur(function(){
			$('#birthNotice').text('');
		});
		$('#birth').blur(function(){
			var birth = $(this).val();
			var year = Number(birth.substr(0,4));	//년
			var month = Number(birth.substr(4,2));	//월
			var day = Number(birth.substr(6,2));	//일
			
			if (birth.length == 8) {
				if (day >= 1 && (month < 13 && month >= 1)){
						if (month == 2) {
							var leap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));//윤년
							if (day > 29 || (day == 29 && !leap)) {
									$('#birthNotice').text('생년월일을 확인해주세요');
									$('#birthNotice').css('color','red');
									return false;
							}
							else{
									$('#birthNotice').text('');
							}
						}
						else if (month == 4 || month == 6 || month == 9 || month == 11) { //30일까지 있는 달
							if (day > 30) {
									$('#birthNotice').text('생년월일을 확인해주세요');
									$('#birthNotice').css('color','red');
									return false;
							}
							else{
									$('#birthNotice').text('');
							}
						}
						else {
							if (day > 31) {
									$('#birthNotice').text('생년월일을 확인해주세요');
									$('#birthNotice').css('color','red');
									return false;
							}
							else {
									$('#birthNotice').text('');
							}
						}
				}
				else {
					$('#birthNotice').text('생년월일을 확인해주세요');
					$('#birthNotice').css('color','red');
					return false;
				}
			}else{
				$('#birthNotice').text('생년월일을 확인해주세요');
				$('#birthNotice').css('color','red');
				return false;
			}
			
		});
	});
	</script>
</body>
</html>
