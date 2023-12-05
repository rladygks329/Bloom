<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head type="text/css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js" 
integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous">
</script>
<style>
	table, th, td {
		board-style : solid;
		board-width : 1px;
		text-align : center;
	}
	#register,
	a {
		display: inline-block;
		margin-right: 2px; /* 버튼 사이의 간격을 조절  */
	}
	form {
	float: right;
	}
</style>
<title>질문답변 게시판</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	<input type="hidden" id="memberId" name="memberId" value="${loginVo.memberId}" />
	<br>
	
	<h1>묻고 답해요</h1>
	<br>
	
	<div id="register">
		<a href="register"><button type="button" class="btn btn-light">새 글 작성</button></a>
	</div>
	<a href="list"><button type="button" class="btn btn-light">목록으로</button></a>
	
	<form action="list" method="GET">
	    <select id="option" name="option">
		    <option value="searchNickname">작성자</option>		    
		    <option value="searchTitleOrContent">제목&내용</option>
	    </select>      
	    <input type="text" id="keyword" name="keyword" value="${keyword}" placeholder="검색어를 입력하세요">
    	<input type="submit" value="검색">
    </form>
	
	<hr>
	<table class="table">
	  <thead>
	    <tr>
	      <th scope="col" style="width : 60px">#</th>
	      <th scope="col" style="width : 700px">제목</th>
	      <th scope="col" style="width : 120px">작성자</th>
	      <th scope="col" style="width : 60px">조회수</th>
	      <th scope="col" style="width : 60px">댓글수</th>
	      <th scope="col" style="width : 60px">좋아요</th>
	      <th scope="col" style="width : 300px">작성일</th>
	    </tr>
	  </thead>
	  	<tbody>
			<c:forEach var="vo" items="${list }">
				<tr>
					<td>${vo.boardId }</td>
					<td><a href="detail?boardId=${vo.boardId }&page=${pageMaker.criteria.page}">${vo.boardTitle }</a></td>
					<td>${vo.authorNickname }</td>
					<td>${vo.boardViewCount }</td>
					<td>${vo.boardReplyCount }</td>
					<td>${vo.boardLikeCount }</td>
					<fmt:formatDate value="${vo.boardDateCreated }"
					pattern="yyyy-MM-dd HH:mm:ss" var="boardDateCreated"/>
					<td>${boardDateCreated }</td>
				</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 검색어와 정렬방법을 쿼리 스트링에 추가한다. -->
	<c:set scope="page" var="queryString" value="" />
	<c:if test="${not empty option }">
		<c:set scope ="page" var="queryString" value="${queryString }&option=${option}"/>
	</c:if>
	<c:if test="${not empty keyword }">
		<c:set scope ="page" var="queryString" value="${queryString }&keyword=${keyword}"/>
	</c:if>
			
	<!-- pageNation -->
	<nav aria-label="페이지 이동">
		<ul class="pagination justify-content-center">
			<li class="page-item  ${pageMaker.hasPrev ? "" : "disabled"}">
				<a class="page-link" href="list?page=${pageMaker.startPageNo - 1 }${queryString}" tabindex="-1">Previous</a>
			</li>
			<c:forEach var="i" begin="${pageMaker.startPageNo}" end="${pageMaker.endPageNo}">
				<li class="page-item ${pageMaker.criteria.page == i ? "active" : " "}">
					<a class="page-link" href="list?page=${i }${queryString}">${i }</a>
				</li>
			</c:forEach>
			<li class="page-item ${pageMaker.hasNext ? "" : "disabled"}">
				<a class="page-link" href="list?page=${pageMaker.endPageNo + 1 }${queryString}" tabindex="-1">Next</a>
			</li>
		</ul>
	</nav>
	
	<script type="text/javascript">
    $(document).ready(function() {
        if ($('#memberId').val() == "") {
        	$("#register a").attr("href", "/blooming/member/login?targetURL=/board/register");
        }
    });
	</script>
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
	
	
	
	
	
	
	
	
	
</body>
</html>











