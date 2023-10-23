<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

    <h2>회원 가입하기</h2>
	<form method="post">
	    <input type="radio" name="memberLevel" value="student"> 일반회원
	    <input type="radio" name="memberLevel" value="instructor"> 강사회원
    <button type="submit">선택</button>
	</form>
	
	<!-- 
	
    <a href="/blooming/member/register-student.jsp?type=student">일반회원</a>
    <a href="/blooming/member/register-teacher.jsp?type=teacher">강사회원</a> 

	    <c:choose>
        <c:when test="${param.type eq 'student'}">
            <jsp:include page="register-student.jsp" />
        </c:when>
        <c:when test="${param.type eq 'teacher'}">
            <jsp:include page="register-teacher.jsp" />
        </c:when>
        <c:otherwise>
            
        </c:otherwise>
    </c:choose>


    -->
	
	
 


</body>
</html>