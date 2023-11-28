<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head type="text/css">
<style>
table, th, td {
	board-style : solid;
	board-width : 1px;
	text-align : center;
}

ul {
	list-style-type : none;
}

li {
	display : inline-block;
}
</style>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js" 
integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous">
</script>
<title>질문답변 게시판</title>
</head>
<body>
	
	<input type="hidden" id="memberId" name="memberId" value="${loginVo.memberId}" />
	
	<h1>게시판 메인</h1>
	<div id="register">
		<a href="register"><input type="button" value="새 글 작성"></a>
	</div>
	<a href="list"><input type="button" value="목록으로"></a>
	
	<form action="list" method="GET">
	    <select id="option" name="option">
		    <option value="searchNickname">작성자</option>		    
		    <option value="searchTitleOrContent">제목&내용</option>
	    </select>      
	    <input type="text" id="keyword" name="keyword" value="${keyword}" placeholder="검색어를 입력하세요">
    	<input type="submit" value="검색">
    </form>
	
	<hr>
	<table>
		<thead>
			<tr>
				<th style="width : 60px">번호</th>
				<th style="width : 700px">제목</th>
				<th style="width : 120px">작성자</th>
				<th style="width : 60px">조회수</th>
				<th style="width : 60px">댓글수</th>
				<th style="width : 60px">좋아요</th>
				<th style="width : 300px">작성일</th>
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
			
	<ul>
		<c:if test="${pageMaker.hasPrev }">
			<li><a href="list?page=${pageMaker.startPageNo -1 }${queryString}">이전</a></li>
		</c:if>
		<c:forEach begin="${pageMaker.startPageNo }" end="${pageMaker.endPageNo }" var="num">
			<li><a href="list?page=${num }${queryString}">${num }</a></li>
		</c:forEach>
		<c:if test="${pageMaker.hasNext }">
			<li><a href="list?page=${pageMaker.endPageNo + 1}${queryString}">다음</a></li>
		</c:if>
		
	</ul>
	
	<script type="text/javascript">
    $(document).ready(function() {
        if ($('#memberId').val() == "") {
        	$("#register a").attr("href", "/blooming/member/login?targetURL=/board/register");
        }
    });
	</script>
	
</body>
</html>











