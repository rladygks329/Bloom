<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>새 글 작성 페이지</title>

</head>

<body>
	<h2>글 작성 페이지</h2>
	<form action="register" method="POST">
		<div>
			<p>제목 : </p>
			<input type="text" name="boardTitle" placeholder="제목 입력" required>
		</div>
		<div>
			<p>내용</p>
			<div id="boardContent">
				<textarea rows="20" cols="120" name="boardContent" placeholder="내용 입력"></textarea>
			</div>
			<input type="hidden" name="boardParentId" value="0">
			<input type="hidden" id="memberId" name="memberId" value="${loginVo.memberId}" />
		</div>
		<div>
			<input type="submit" value="등록">
		</div>
		<div>
    		<button type="button" onclick="goBack()">취소</button>
		</div>
	
	</form>
	
	<script>
	    function goBack() {
	        window.history.back();
	    }	    
	</script>

</body>
</html>




