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
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<title>홈</title>
</head>
<body>
  <div class="allcontainer">
	<%@ include file="/WEB-INF/views/main_nav.jsp" %>

    <section class="content">
    	<div class="pg-header">
      		<a href="#" onClick="history.back()" style="color: #9E6BD1"><i class="fas fa-chevron-left "></i></a> 
			<span class="pg-header-center">검색</span>
			<span class="pg-header-right"><a href="${pageContext.request.contextPath}/write"><i class="fas fa-pen"></i></a></span>
		</div>
      	<div class="search-box">
        	<form method="get" action="${pageContext.request.contextPath}/search" autocomplete="off">
        	<input type="text" name="kwd"  placeholder="검색어 입력"> <!--검색창-->
        	<button type="submit">검색</button>
        	</form>
      	</div>
      <article class="timeline">
			.
      </article>
  </section>
 </div>
</body>
</html>
