<%@page import="com.edu.blooming.domain.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/component/navigation.jsp"%> 
	<ul class="nav justify-content-center underline">
  		<li class="nav-item">
   			<a class="nav-link" href="/blooming/member/mypage">내 활동</a>
 		</li>
		<li class="nav-item">
			<a class="nav-link" href="/blooming/member/mypage-identify">내 정보 수정</a>
		</li>
	</ul>
	<hr>
	<br>
	<br>
	
	<div class="container my-5">
		<br>
		<div class="card bg-light">
			<article class="card-body mx-auto">
				<p class="text-center">개인정보 보호를 위해 비밀번호를 입력해주시기 바랍니다</p>
				<br>

				<form action="identify" method="post">
					<input type="hidden" name="memberEmail" value="${loginVo.memberEmail }"> 
					
					<input type="hidden" name="targetURL" value="${param.targetURL}">

					<!-- password -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-lock"></i>
						</span> <input class="form-control" name="memberPassword" placeholder="비밀번호" type="password">
						<div class="invalid-feedback"></div>
					</div>
					
					<br>
					<br>
					<!-- form-group end.// -->
					<div class="d-flex justify-content-center">
						<button class="btn btn-primary">비밀번호 확인</button>
					</div>
				</form>
			</article>
		</div>
		<!-- card.// -->
	</div>
	<!--container end.//-->
	
	
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
	
</body>
</html>