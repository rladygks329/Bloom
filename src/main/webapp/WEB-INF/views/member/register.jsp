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
    <h2>회원 가입하기</h2>

    <c:choose>
        <c:when test="${param.type eq 'student'}">
            <jsp:include page="register-student.jsp" />
        </c:when>
        <c:when test="${param.type eq 'teacher'}">
            <jsp:include page="register-teacher.jsp" />
        </c:when>
        <c:otherwise>
            <!-- 기본적으로 노출할 내용 또는 에러 처리 -->
        </c:otherwise>
    </c:choose>
  	
</body>

</html>










