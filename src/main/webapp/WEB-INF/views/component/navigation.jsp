<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.edu.blooming.domain.MemberVO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
MemberVO loginInfo = (MemberVO) session.getAttribute("loginVo"); // 세션에서 로그인 정보를 가져옴
boolean isInstructor = false;
if (loginInfo != null) {
  isInstructor = loginInfo.getMemberLevel().equals("instructor");
}
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container px-4 px-lg-5">
		<a class="navbar-brand" href="/blooming/main">Bloom</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
				<li class="nav-item"><a class="nav-link" href="/blooming/lecture/list">강의 목록</a></li>
				<li class="nav-item"><a class="nav-link" href="/blooming/board/list">질문게시판</a></li>
			</ul>
			<ul class="navbar-nav mb-2 mb-lg-0 ms-lg-4">
				<c:choose>
					<c:when test="<%=loginInfo != null%>">
						<%-- 로그인 상태인 경우: 로그아웃 버튼 표시 --%>
						<li class="nav-item">
							<a class="nav-link" href="/blooming/cart"> 
								<i class="bi-cart-fill me-1"></i> 장바구니
							</a>
						</li>
						
						<c:if test="<%=isInstructor%>">
							<li class="nav-item">
								<a class="nav-link" href="/blooming/member/instructor-page"> 
									<i class="bi-person-fill me-1"></i> 강사 정보
								</a>
							</li>
						</c:if>
						
						<li class="nav-item">
							<a class="nav-link" href="/blooming/member/mypage"> 
								<i class="bi-person-fill me-1"></i> 마이페이지
							</a>
						</li>
            
						<li class="nav-item">
							<a class="nav-link" href="/blooming/member/logout">
								로그아웃
							</a>
						</li>
					</c:when>
					<c:otherwise>
						<%-- 로그인 상태가 아닌 경우: 회원가입 및 로그인 버튼 표시 --%>
						<li class="nav-item"><a class="nav-link" href="/blooming/member/register-type">회원가입</a></li>
						<li class="nav-item"><a class="nav-link" href="/blooming/member/login">로그인</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</nav>
