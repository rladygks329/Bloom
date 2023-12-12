<%@page import="com.edu.blooming.domain.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<style>
	table, th, td {
		board-style : solid;
		board-width : 1px;
		text-align : center;
	}

    .nav-tabs .nav-link {
        font-size: 20px; 
        color: #000000;  
    }

	.form_instructor {display: none;}

</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/component/navigation.jsp"%> 
    <br>
    <div class="container">
		<ul class="nav nav-tabs">
	  		<li class="nav-item">
	   			<a class="nav-link" href="/blooming/member/mypage">내 활동</a>
	 		</li>
			<li class="nav-item">
				<a class="nav-link" href="/blooming/member/mypage-identify">내 정보 수정</a>
			</li>
		</ul>
		<br>
		<br>
	</div>
	<div class="container" style="border: 1px solid #ddd; padding: 20px;">	
		<h5>작성한 글</h5>
		<hr>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">제목</th>
					<th scope="col">조회수</th>
					<th scope="col">댓글수</th>
					<th scope="col">좋아요</th>
					<th scope="col">작성일</th>
				</tr>
			</thead>
			
			<tbody>
				<c:if test="${ empty listByMemberId }">
					<tr>
						<td></td>
						<td>작성한 글이 없습니다.</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:if>
				<c:if test="${not empty listByMemberId }">
					<c:forEach var="vo" items="${listByMemberId }">
						<tr>
							<td>${vo.boardId }</td>
							<td><a href="/blooming/board/detail?boardId=${vo.boardId }" style="color: black; text-decoration: none;">${vo.boardTitle }</a></td>
							<td>${vo.boardViewCount }</td>
							<td>${vo.boardReplyCount }</td>
							<td>${vo.boardLikeCount }</td>
							<fmt:formatDate value="${vo.boardDateCreated }"
							pattern="yyyy-MM-dd HH:mm:ss" var="boardDateCreated"/>
							<td>${boardDateCreated }</td>
						</tr>			
					</c:forEach>
				</c:if>
			</tbody> 
		</table>
		<br>
	</div>
	
	<div class="container" style="border: 1px solid #ddd; padding: 20px;">
		<h5>좋아요 누른 글</h5>
		<hr>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">제목</th>
					<th scope="col">조회수</th>
					<th scope="col">댓글수</th>
					<th scope="col">좋아요</th>
					<th scope="col">작성일</th>
				</tr>
			</thead>
			
			<tbody>
				<c:if test="${ empty listByLike }">
					<tr>
						<td></td>
						<td>좋아요 누른 글이 없습니다.</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:if>
				
				<c:if test="${ not empty listByLike }">
					<c:forEach var="vo" items="${listByLike }">
						<tr>
							<td>${vo.boardId }</td>
							<td><a href="/blooming/board/detail?boardId=${vo.boardId }" style="color: black; text-decoration: none;">${vo.boardTitle }</a></td>
							<td>${vo.boardViewCount }</td>
							<td>${vo.boardReplyCount }</td>
							<td>${vo.boardLikeCount }</td>
							<fmt:formatDate value="${vo.boardDateCreated }"
							pattern="yyyy-MM-dd HH:mm:ss" var="boardDateCreated"/>
							<td>${boardDateCreated }</td>
						</tr>			
					</c:forEach>
				</c:if>
			</tbody> 
		</table>
		<br>	
	</div>
	
	<div class="container" style="border: 1px solid #ddd; padding: 20px;">
		<h5>작성한 댓글</h5>
		<hr>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">댓글내용</th>
					<th scope="col">작성일</th>
				</tr>
			</thead>
			
			<tbody>
				<c:if test="${ empty replyListByMemberId }">
					<tr>
						<td></td>
						<td>작성한 댓글이 없습니다.</td>
						<td></td>
					</tr>
				</c:if>
				<c:if test="${ not empty replyListByMemberId }">
					<c:forEach var="vo" items="${replyListByMemberId }">
						<tr>
							<td>${vo.boardReplyId }</td>
							<td><a href="/blooming/board/detail?boardId=${vo.boardId }" style="color: black; text-decoration: none;">${vo.boardReplyContent }</a></td>
							<fmt:formatDate value="${vo.boardReplyDateCreated }"
							pattern="yyyy-MM-dd HH:mm:ss" var="boardReplyDateCreated"/>
							<td>${boardReplyDateCreated }</td>
						</tr>			
					</c:forEach>
				</c:if>
			</tbody> 
		</table>
		<br>
	</div>
	
	<div class="container" style="border: 1px solid #ddd; padding: 20px;">
		<h5>내가 구매한 강의</h5>

		<hr>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">강의 이름</th>
				</tr>
			</thead>
			<tbody>
			<c:if test="${ empty purchasedList }">
				<tr>
					<td></td>
					<td>구매하신 강의가 없습니다.</td>
				</tr>
			</c:if>
			<c:if test="${not empty purchasedList }">
				<c:forEach var="lecture" items="${purchasedList }">
					<tr>
						<td>${lecture.lectureId }</td>
						<td><a href="/blooming/lecture/${lecture.lectureId }/course" style="color: black; text-decoration: none;">${lecture.lectureTitle}</a></td>
					</tr>			
				</c:forEach>
			</c:if>
			</tbody> 
		</table>
		<br>
	</div>
	
	<div class="container" style="border: 1px solid #ddd; padding: 20px;">
		<h5>좋아요 누른 강의 </h5>
		<hr>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">강의 이름</th>
				</tr>
			</thead>
			
			<tbody>
			<c:if test="${ empty likedList }">
				<tr>
					<td></td>
					<td>좋아요 누른 강의가 없습니다.</td>
				</tr>
			</c:if>
			<c:if test="${not empty likedList }">
				<c:forEach var="lecture" items="${likedList }">
					<tr>
						<td>${vo.lectureId }</td>
						<td><a href="/blooming/lecture/detail?lectureId=${lecture.lectureId }" style="color: black; text-decoration: none;">${lecture.lectureTitle}</a></td>
					</tr>			
				</c:forEach>
			</c:if>
			</tbody> 
		</table>
		<br>
	</div>
	
	<br>
	<hr>
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
</body>
</html>