<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<!-- Bootstrap css -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" 
		rel="stylesheet" 
		integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" 
		crossorigin="anonymous"/>
	<!-- Bootstrap icons -->
	<link
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
      rel="stylesheet"/>
	 <!-- Bootstrap core JS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<meta charset="UTF-8">
	<title>Bloom</title>
</head>
<body>

    <h1>메인페이지</h1>
    
    <%-- 세션에서 로그인 정보 가져오기 --%>
    <%
        Object loginInfo = session.getAttribute("loginVo"); // 세션에서 로그인 정보를 가져옴
    %>
    
	<!-- 
	<h1>메인페이지</h1>
    <a href="/blooming/member/register-type">회원가입</a>
    <a href="/blooming/member/login">로그인</a>
     -->
    <c:choose>
        <c:when test="<%= loginInfo != null %>">
            <%-- 로그인 상태인 경우: 로그아웃 버튼 표시 --%>
            <a href="/blooming/member/logout">로그아웃</a>
            <a href="/blooming/member/introductor-profile">강사정보</a>
        </c:when>
        <c:otherwise>
            <%-- 로그인 상태가 아닌 경우: 회원가입 및 로그인 버튼 표시 --%>
            <a href="/blooming/member/register-type">회원가입</a>
            <a href="/blooming/member/login">로그인</a>
        </c:otherwise>
    </c:choose>
    <a href="/blooming/board/list">질문 & 답변</a>
    <%@ include file="/WEB-INF/views/component/navigation.jsp" %>
    
	<header class="bg-dark py-5">
      <div class="container px-4 px-lg-5 my-5">
        <div class="text-center text-white">
          <h1 class="display-4 fw-bolder">Bloom</h1>
          <p class="lead fw-normal text-white-50 mb-0">bloom your talent</p>
        </div>
      </div>
    </header>
    
    <section class="py-5">
      <div class="container px-4 px-lg-5 mt-5">
        <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
        	<c:forEach var="lecture" items="${lectureList }">
        		<div class="col mb-5">
		            <div class="card h-100">
		              <!-- Product image-->
		              <img
		                class="card-img-top"
		                src="${lecture.lectureThumbnailUrl }"
		                alt="..."
		              />
		              <!-- Product details-->
		              <div class="card-body p-4">
		                <div class="text-center">
		                  <!-- Product name-->
		                  <h5 class="fw-bolder">${lecture.lectureTitle }</h5>
		                  <h6 class="fw-bolder">${lecture.authorName }</h5>
		                  
		                  <!-- Product price-->
		                  <fmt:formatNumber type="number" maxFractionDigits="3" value="${lecture.lecturePrice }" /> 원
		                  
		                  <!-- Product reviews-->
		                  <div class="d-flex justify-content-center small text-warning mb-2">
		                  	<c:if test="${lecture.lectureSalesCount == 0}">
		                  		<i class="bi bi-star"></i>	
		                  		<i class="bi bi-star"></i>
		                  		<i class="bi bi-star"></i>
			        			<i class="bi bi-star"></i>
			        			<i class="bi bi-star"></i>
			        			<p class="fs-6">(0)</p>
			        		</c:if>
			        		<c:if test="${lecture.lectureSalesCount != 0}">
								<c:set var="starRating" value="${lecture.lectureTotalScore / lecture.lectureSalesCount }" />
			                	<c:forEach var="i" begin="1" end="5">
			                    	<c:choose>
			                        	<c:when test="${i <= starRating}">
			                            	<i class="bi bi-star-fill"></i>
			                        	</c:when>
			                        	<c:when test="${i - 0.5 <= starRating && starRating < i}">
			                            	<i class="bi bi-star-half"></i>
			                        	</c:when>
			                        	<c:otherwise>
			                            	<i class="bi bi-star"></i>
			                        	</c:otherwise>
			                    	</c:choose>
			                	</c:forEach>
			                	<p class="fs-6">(${lecture.lectureSalesCount})</p>
			        		</c:if>
		                  </div>
		              	</div>
		              </div>
		              
		              <!-- Product actions-->
		              <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
		                <div class="text-center">
		                  <a class="btn btn-outline-dark mt-auto" href="#">강좌 담기</a>
		                </div>
		              </div>
		              <!-- end product actions -->
		            </div>
		          </div> 
        	</c:forEach>
        </div>
      </div>
    </section>
    
    <%@ include file="/WEB-INF/views/component/footer.jsp" %>
</body>
</html>