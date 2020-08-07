<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mycustom.css" />
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
<script src="//code.jquery.com/jquery-3.2.1.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>


<title>프로필 수정</title>
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
				<span class="pg-header-center">프로필 수정</span>
				<span class="pg-header-right"><a href=""><i class="fas fa-user-cog"></i></a></span>
			</div>
			<div id="editProfile">
				<form method="post" enctype="multipart/form-data" autocomplete="off">
					<div class="edit-image">
						<span class="edit-image-thumbnail">
						<img src="${pageContext.request.contextPath }/resources/images/${profile.image_file.save_name}" />
						</span>
						<br>
						<label for="file-button" class="board-icon">
							<h6>이미지 변경</h6>
						</label> 
						<input type="file" id="file-button" name="file" />
					</div>
					<div class="edit-name"> 
						<span>이름</span> <input type="text" id="edit_name" name="name" value="${profile.name }"/>
					</div>
					<div class="edit-introduce">
						<span>소개</span> <textarea name="introduce" id="edit_introduce" rows="2" cols="20">${profile.introduce }</textarea>
					</div>
					<button type="submit">수정</button>
				</form>
			</div>

		</section>
	</div>
	<script>
	function validationFile(filename){ //파일 확장자 체크 (jpg, png, gif)
		var ExtensionIndex = filename.lastIndexOf('.')+1;
		var Extension = filename.toLowerCase().substring(ExtensionIndex, filename.lengh); //확장자
		if(!((Extension === "jpg") || (Extension === "png") || (Extension === "gif"))){
			alert('jpg, png, gif 확장자 파일만 업로드 가능합니다');
			return false;
		}
		return true;
	}

	
 	$("input[type=file]").change(function () {
        var file = document.getElementById("file-button").files[0];     
       	if(validationFile(file.name)){ // 업로드 확장자 체크
			$('.edit-image-thumbnail').find("img").attr('src',URL.createObjectURL(file));
        }
        else{
            alert("업로드 실패 : 사용 불가능한 확장자");
        }     
    }); 

 	$('#edit_name').keyup(function (e){
	    var content = $(this).val();
	    if (content.length > 10){
	        alert("이름은 최대 10자까지 입력 가능합니다.");
	        $(this).val(content.substring(0, 10));
	    }
	});
 	$('#edit_introduce').keyup(function (e){
	    var content = $(this).val();
	    if (content.length > 30){
	        alert("자기소개는 최대 30자까지 입력 가능합니다.");
	        $(this).val(content.substring(0, 30));
	    }
	});
	</script>
</body>
</html>