<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js" 
integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous">
</script>
<meta charset="UTF-8">
<title>게시글 번호 가져오는방법</title>
</head>
<body>
	<c:forEach var="vo" items="${list }">
		<c:if test="${vo.boardParentId == 0 }">
			<h2>글내용 보기</h2>
			<div>
				<p>글 번호: ${vo.boardId }</p>
			</div>
			<div>
				<p>제목 : </p>
				<p>${vo.boardTitle }</p>
			</div>
			<div>
				<p>작성자: ${vo.authorName } </p>
				<p>작성일: ${vo.boardDateCreated }</p>
				<p>조회수: ${vo.boardViewCount }</p>
				<p>좋아요: ${vo.boardLikeCount }</p>
			</div>
			<div>
				<textarea rows="20" cols="120" readonly>${vo.boardContent }</textarea>
			</div>
			
			<a><input type="button" value="글 목록"></a>
			<a href="update?boardId=${vo.boardId }&page=${page }"><input type="button" value="글 수정"></a>
			<form action="deleteQuestion" method="POST">
				<input type="hidden" id="boardId" name="boardId" value="${vo.boardId }">
				<input type="submit" value="글 삭제"> 
			</form>
	
			<br>
			<br>
			<br>
		
			<div>
				<p>답변 ${vo.boardAnswerCount }개</p>
			</div>
			
		</c:if>
	</c:forEach>

	<c:forEach var="vo" items="${list }">
		<c:if test="${param.boardId == vo.boardParentId }">
			<div>
				<p>작성자: ${vo.authorName } </p>
				<p>작성일: ${vo.boardDateCreated }</p>		
				<p>좋아요: ${vo.boardLikeCount }</p>
			</div>	
			<div>
				<textarea rows="20" cols="120" readonly>${vo.boardContent }</textarea>
			</div>
		</c:if>
	</c:forEach>
	
</body>
</html>























