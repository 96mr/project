<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<nav>
	<ul class="main-nav">
        <li class="navitem">  <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath }/resources/images/logo.png" width="50px" alt="logo"/></a></li>
        <li class="navitem"><a href="${pageContext.request.contextPath}/"><i class="fas fa-home"></i><span class="nav-text">홈</span></a></li>
        <li class="navitem"><a href="${pageContext.request.contextPath}/browse"><i class="fas fa-search"></i><span class="nav-text">검색</span></a></li>
        <c:choose>
    	    <c:when test="${empty sessionID}">
      	  		<li class="navitem"><a href="${pageContext.request.contextPath}/login"><i class="fas fa-sign-in-alt"></i><span class="nav-text">로그인</span></a></li>
      	  		<li class="navitem"><a href="${pageContext.request.contextPath}/join"><span class="nav-text">회원가입</span></a></li>
        	</c:when>
        	<c:otherwise>
        		<li class="navitem"><a href="${pageContext.request.contextPath}/alarms"><i class="fa fa-bell"></i><span class="nav-text">알림</span><span id="alarm-count"></span></a></li>
        		<li class="navitem"><a href="${pageContext.request.contextPath}/${sessionID}/profile"><i class="fas fa-user-circle"></i><span class="nav-text">프로필</span></a></li>
        		<li class="navitem"><a href="${pageContext.request.contextPath}/settings"><i class="fa fa-cog"></i><span class="nav-text">설정</span></a></li>
        		<li class="navitem"><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i><span class="nav-text">로그아웃</span></a></li>
        	</c:otherwise>
        </c:choose>
    </ul>
</nav>