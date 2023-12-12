<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous">	
</script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<meta charset="UTF-8">
<title>로그인 하기</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>

	<div class="container">
		<br>
		<div class="card bg-light mb-5 py-5">
			<article class="card-body mx-auto">
				<h4 class="card-title mt-3 text-center">로그인</h4>
				<p class="text-center">Bloom</p>
				<br>
				<br>
				<form action="login" method="post">
					<input type="hidden" name="targetURL" value="${param.targetURL }"> 
					<!-- email -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-envelope"></i>
						</span> <input name="memberEmail" class="form-control" placeholder="Email address" type="email">
					</div>

					<!-- password -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-lock"></i>
						</span> <input class="form-control" name="memberPassword" placeholder="비밀번호" type="password">
						<div class="invalid-feedback">아이디나 비밀번호를 확인해주세요</div>
					</div>
					<c:if test="${not empty errorMessage}">
	 					<div class="alert alert-danger text-center" role="alert">
	    		    		${errorMessage}
				    	</div>
					</c:if>
					<br>
					<!-- form-group end.// -->
					<p class="text-center">
						계정이 없으십니까? <a href="/blooming/member/register-type">회원가입하기</a>
					</p>
					<div class="d-flex justify-content-center">
						<button class="btn btn-primary">로그인 하기</button>
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