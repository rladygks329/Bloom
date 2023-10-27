<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script>
		function validateForm() {
			// memberLevel 이름을 가진 input 요소 중 check된 요소를 선택하고, 그것을 memberLevel에 할당
			var memberLevel = document.querySelector('input[name="memberLevel"]:checked')
			if(!memberLevel) {
				alert("회원 구분을 선택하세요.");
				return false;	
			}
			return true;
		} // end validateForm()
	</script>
</head>
<body>

    <h2>회원 가입하기</h2>
	<form method="post" onsubmit="return validateForm();">
	    <input type="radio" name="memberLevel" value="student"> 일반회원
	    <input type="radio" name="memberLevel" value="instructor"> 강사회원
    <button type="submit">선택</button>
	</form>

</body>
</html>