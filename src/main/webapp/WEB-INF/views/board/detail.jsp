<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js" 
integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous">
</script>
	
<script>

	

</script>

<meta charset="UTF-8">
<title>게시글번호나오게</title>
</head>
<body>
	<c:forEach var="vo" items="${list }">
		<c:if test="${vo.boardParentId == 0 }">
			<h2>글내용 보기</h2>
			<div>
				<p>글 번호: ${vo.boardId }</p>
			</div>
			<div>
				<p>제목: ${vo.boardTitle }</p>
			</div>
			<div>
				<p>작성자: ${vo.authorName } </p>
				<fmt:formatDate value="${vo.boardDateCreated }"
					pattern="yyyy-MM-dd HH:mm:ss" var="boardDateCreated"/>
				<p>작성일: ${boardDateCreated }</p>
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
			
			<div>
				<p>답글을 작성해 주세요</p>
				<textarea rows="10" cols="120" name="boardContent" placeholder="내용 입력"></textarea>
				<input type="button" value="저장" id="saveAnswer">
			</div>
			
		</c:if>
	</c:forEach>

	<c:forEach var="vo" items="${list }">
		<c:if test="${param.boardId == vo.boardParentId }">
			<div>
				<p>제목: ${vo.boardTitle }</p>
				<p>작성자: ${vo.authorName } </p>
				<p>작성일: ${boardDateCreated }</p>		
				<p>좋아요: ${vo.boardLikeCount }</p>
				<p>게시글번호: ${vo.boardId }</p>
				<p>게시글부모번호: ${vo.boardParentId }</p>
			</div>	
			<div>
				<textarea rows="20" cols="120" readonly>${vo.boardContent }</textarea>
			</div>
			<a href="update?boardId=${vo.boardId }&page=${page }"><input type="button" value="글 수정"></a>
			<div>
				<p>댓글을 작성해 주세요</p>
				<textarea rows="5" cols="60" name="ReplyContent" placeholder="내용 입력"></textarea>
				<input type="button" value="저장" id="btnAddReply">
			</div>
			
		</c:if>
	</c:forEach>
	
</body>
</html>























