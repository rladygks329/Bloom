<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js" 
integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous">
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>로그인</h1>
	<form action="login" method="post">
		<input type="text" name="memberEmail"><br>
		<input type="password" name="memberPassword"><br>
		<input type="hidden" name="targetURL" value="${param.targetURL }">
		<input type="submit" value="로그인" >	
		<a href="/blooming/member/register-type">회원가입</a>
		
		<c:if test = "${result == 0 }">
                <div class = "login_warn">사용자 ID 또는 비밀번호를 잘못 입력하셨습니다.</div>
        </c:if>
		
	</form>

	
	<!-- 
	<script>
		$(".login_button").click(function(){
			// alert("로그인 버튼 작동");
			
			/* 로그인 메서드 서버 요청 */
	        $("#login").attr("action", "/member/login");
	        $("#login").submit();
  		});
		
	</script>
	 -->
	 
	
</body>
</html>