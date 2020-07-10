<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="true"%>

	<div class="pg-header">
		<a href="#" onClick="location.href = document.referrer;" style="color:#9E6BD1"><i class="fas fa-chevron-left "></i></a>
			<span class="pg-header-center">${user.profile.name }</span>
			<span class="pg-header-right">
			<c:choose>
				<c:when test="${sessionID ne user.id}">
					<button type="button" id="addFollow_btn" class="follow-btn" onclick ="javascript:follow_btn(this,'${user.id }')" >팔로우</button>
				</c:when>
				<c:otherwise>
					<a href="${pageContext.request.contextPath}/edit/profile"><i class="fas fa-user-cog"></i></a>
				</c:otherwise>
			</c:choose>			
			</span>
	</div>
			
	<div id="modal-follow" class="modal"> <!-- 팔로우/팔로워 리스트 -->
		<div class="modal-context">
			<div class="modal-header">
				<span class="following-list">팔로잉</span><span class="follower-list">팔로워</span>
				<span class="close"><i class="fas fa-times"></i></span>
			</div>
			<div class="modal-list following">
			</div>
			<div class="modal-list follower">			
			</div>
		</div>
	</div>
			
	<div class="profile">
		<div class="profile-member-image">
			<img src="${pageContext.request.contextPath }/resources/images/${user.profile.image_file.save_name}" />
		</div>
		<div class="profile-member-name">
			<b>${user.profile.name }</b><br>@ ${user.id }<br>
		</div>
		<div class="profile-member-notice">
			<div>
				<b>${board_cnt}</b><br><span>게시글</span>
			</div>
			<div class="following-list">
				<b></b><br><span>팔로잉</span>
			</div>
			<div class="follower-list">
				<b></b><br><span>팔로워</span>
			</div>
			</div>
			<div class="profile-member-introduce">
				${user.profile.introduce }
			</div>
		</div>
	
