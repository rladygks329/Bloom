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

    <h1>메인페이지</h1>
    
    <%-- 세션에서 로그인 정보 가져오기 --%>
    <%
        Object loginInfo = session.getAttribute("loginVo"); // 세션에서 로그인 정보를 가져옴
    %>
    
    <c:choose>
        <c:when test="<%= loginInfo != null %>">
            <%-- 로그인 상태인 경우: 로그아웃 버튼 표시 --%>
            <a href="/blooming/member/logout">로그아웃</a>
        </c:when>
        <c:otherwise>
            <%-- 로그인 상태가 아닌 경우: 회원가입 및 로그인 버튼 표시 --%>
            <a href="/blooming/member/register-type">회원가입</a>
            <a href="/blooming/member/login">로그인</a>
        </c:otherwise>
    </c:choose>


	
</body>
</html>