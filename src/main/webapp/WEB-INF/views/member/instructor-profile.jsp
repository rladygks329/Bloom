<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>강사소개</h2>
	
	<h2>프로필 이미지 출력 부분</h2>
	<img src="<%= session.getAttribute("memberProfileUrl") %>" alt="프로필 이미지" />

</body>
</html>