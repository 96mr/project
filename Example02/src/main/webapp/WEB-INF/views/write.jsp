<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mycustom.css" />
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
<script src="//code.jquery.com/jquery-3.2.1.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<title>글 작성하기</title>
</head>
<body>
	<div class="allcontainer">
		<%@ include file="/WEB-INF/views/main_nav.jsp"%>

		<section class="content">
			<div class="pg-header">
				<a href="#" onClick="history.back();" style="color: #9E6BD1"><i class="fas fa-chevron-left "></i></a> 
				<span class="pg-header-center">글작성</span>
				<span></span>
			</div>
			
			<article class="boardwrite">
				<form id='board-form' method="post" enctype="multipart/form-data">
					<textarea id="board-content" name="content" cols="50" rows="10" placeholder="300자 이내"></textarea>
					<div id="board-content-count"><span id="content-count">0</span>/<span id="content-max-count" >300</span></div>
					<div id="file_list">
						<div>-------- 업로드 이미지 --------</div>
					</div>
					<hr>
					<div class="boardwrite-btn">
						<label for="file-button"><i class="fas fa-image fa-2x"></i></label>
						<input id="file-button" type="file" multiple>
						<button type="button" onclick="addBoard('board-form');"><i class="fas fa-pen"></i></button>
					</div>
				</form>
			</article>
		</section>
	</div>
	<script type="text/javascript" charset="utf-8">
        var contextPath = "<c:out value='${pageContext.request.contextPath}'/>";
		var loginID = "<c:out value='${sessionID}'/>";
		var userID = "<c:out value='${user.id}'/>"
	</script>	
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/mycustom.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/websocket.js"></script>
	<script>
	var formData = new FormData();
	
	function addBoard(formName){
		formData.append('content',$("#board-content").val());
		$.ajax({
			url:contextPath+'/write',
			method:'post',
			data:formData,
			processData: false, 
			contentType: false,
			cache:false,
			success:function(data){
				window.location.href = contextPath+data;
			}
		});
	}
	
	
	function validationFile(filename){ //파일 확장자 체크 (jpg, png, gif)
		var ExtensionIndex = filename.lastIndexOf('.')+1;
		var Extension = filename.toLowerCase().substring(ExtensionIndex, filename.lengh); //확장자
		if(!((Extension === "jpg") || (Extension === "png") || (Extension === "gif"))){
			alert('jpg, png, gif 확장자 파일만 업로드 가능합니다');
			return false;
		}
		return true;
	}
	
  	function addFile(file){
  	  	formData.append("file", file);
		var str = "<div class='file-thumbnail'><span class='thumbnail-img'><input type='hidden' name='file-name' value='"+file.name+"'><img src='"+URL.createObjectURL(file)+"'/></span>"
				+ "<a href='#this' name='file-delete'>삭제</a></div>";
		$('#file_list').append(str);
		$("a[name='file-delete']").on("click", function(e) {
           	e.preventDefault();
           	deleteFile($(this));
        });
	} 


 	$("input[type=file]").change(function () {
        var fileInput = document.getElementById("file-button");     
        var files = fileInput.files;
        var file;   
        for (var i = 0; i < files.length; i++) {      
            file = files[i];
            if(validationFile(file.name)){ // 업로드 확장자 체크
            	addFile(file);
            }
            else{
                alert("업로드 실패 : 사용 불가능한 확장자");
            }
        }
        
    }); 
 
	
	function deleteFile(obj) {
		var all_obj = formData.getAll('file');
		var del_obj = obj.prev('span').find("input[name='file-name']").val();
       	obj.parent().remove();
  		formData.delete('file');
       	for (var value of all_obj) {
           	if(del_obj !== value.name){
               	formData.append('file', value);
            }           	
    	}
    }

	$('#board-content').keyup(function (e){
	    var content = $(this).val();
	    $('#content-count').html(content.length);    //글자수 실시간 카운팅

	    if (content.length > 300){
	        alert("최대 300자까지 입력 가능합니다.");
	        $(this).val(content.substring(0, 300));
	        $('#content-count').html("300");
	        $('.boardwrite-btn button').attr('disabled',true);
	    }
	});
    
	</script>
</body>
</html>
