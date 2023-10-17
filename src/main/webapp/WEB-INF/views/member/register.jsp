<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>회원 가입하기</h2>
  	<form action="register" method="post">
    	<p>이메일</p>
	    <input type="text" name="memberEmail" placeholder="이메일 입력" required="required">
	    <p>패스워드</p>
	    <input type="password" name="memberPassword" placeholder="비밀번호 입력" required="required">
	    
	    <p>핸드폰</p>
	    <input type="text" name="memberPhone" placeholder="번호 입력">
	    
	    <p>주소</p>
	    <input type="text" name="memberAddress" placeholder="주소 입력" required="required">
	    	 
	    <p>회원등급</p>
	    <input type="text" name="memberLevel" placeholder="회원등급" required="required">
	    	 
	    <p>자기소개</p>
	    <input type="text" name="memberIntroduce" placeholder="자기소개" required="required">	
	     
	    <p>사진url</p>
	    <input type="text" name="memberProfileUrl" placeholder="사진" required="required">	    
	    
	    <br>
	    <input type="submit" value="전송" >
  	</form>
</body>
</html>