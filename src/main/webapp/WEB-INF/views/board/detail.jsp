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
			
			<a><input type="button" onclick="goBack()" value="글 목록"></a>
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
				<input type="hidden" class="boardId" value="${vo.boardId}">
				<input type="hidden" id="memberId" value="${sessionScope.loginVo.memberId}">
				<textarea rows="5" cols="50" id="boardReplyContent"></textarea>
				<button type="button" class="btnAddReply">댓글 달기</button>        		
			</div>
			<div style="text-align: center;">
				<div id="replies"></div>
			</div>
		</c:if>
	</c:forEach>
	
<script>
	function goBack() {
	    window.history.back();
	}


$(document).ready(function(){
	getAllReplies();
	
	$('.btnAddReply').click(function(){
		var boardId = $('.boardId').val(); 
		var memberId = $('#memberId').val(); 
		var boardReplyContent = $('#boardReplyContent').val(); 
		var obj = {
				'boardId' : boardId, 
				'memberId' : memberId,
				'boardReplyContent' : boardReplyContent
		};
		console.log(obj);
		
		// $.ajax로 송수신
		$.ajax({
			type : 'POST', 
			url : 'replies/' + boardId,
			headers : {
				'Content-Type' : 'application/json'
			},
			data : JSON.stringify(obj), // JSON으로 변환
			success : function(result){
				console.log(result);
				if(result == 1) {
					alert('댓글 입력 성공');
					getAllReplies();
				}
			}
		}); // end ajax()
	}); // end btnReplyAdd.click()
	
	// 게시판 댓글 전체 가져오기
	function getAllReplies() {
		var boardId = $('#boardId').val();
		console.log(boardId);
		
		var url = 'replies/' + boardId;
		console.log(url);
		$.getJSON(
			url, 
			function(data) {
				// data : 서버에서 전송받은 list 데이터가 저장되어 있음.
				// getJSON()에서 json 데이터는
				// javascript object로 자동 parsing됨.
				console.log(data);
			
				var memberId = $('#memberId').val();
				var list =''; // 댓글 데이터를 HTML에 표현할 문자열 변수	
								
				// $(컬렉션).each() : 컬렉션 데이터를 반복문으로 꺼내는 함수
				$(data).each(function(){
					// this : 컬렉션의 각 인덱스 데이터를 의미
					console.log(this);
					
					var boardReplyDateCreated = new Date(this.boardReplyDateCreated);
					var disabled = 'disabled';
					var readonly = 'readonly';
					
					if(memberId == this.memberId) {
						disabled = '';
						readonly = '';
					}
					
					list += '<div class="reply_item">'
						+ '<pre>'
						+ '<input type="hidden" id="replyId" value="' + this.boardReplyId +'">'
						+ '<input type="hidden" id="authorName" value="' + this.authorName +'">'
						+ this.authorName
						+ '&nbsp;&nbsp;' // 공백
						+ '<input type="text" id="boardReplyContent" value="' + this.boardReplyContent + '">'
						+ '&nbsp;&nbsp;' // 공백
						+ boardReplyDateCreated
						+ '&nbsp;&nbsp;' // 공백
						+ '<button class="btn_update" >수정</button>'
						+ '<button class="btn_delete" >삭제</button>'
						+ '</pre>'
						+ '</div>';
				}); // end each()
				$('#replies').html(list);
			}
		); // end getJSON()
	} // end getAllReplies()
		
	// 수정 버튼을 클릭하면 선택된 댓글 수정
	$('#replies').on('click', '.reply_item .btn_update', function(){
		console.log(this);
				
		// 선택된 댓글의 replyId, replyContent 값을 저장
		// prevAll() : 선택된 노드 이전에 있는 모든 형제 노드를 접근
		
		var replyId = $(this).prevAll('#replyId').val();		
		var boardReplyContent = $(this).prevAll('#boardReplyContent').val();
		console.log("선택된 댓글 번호 : " + replyId + ", 댓글 내용 : " + boardReplyContent);
		
		// ajax 요청
		$.ajax({
			
			type : 'PUT', 
			url : 'replies/' + replyId, 
			headers : {
				'Content-Type' : 'application/json'
			},
			data : boardReplyContent,
			success : function(result) {
				console.log(result);
				if(result == 1) {
					alert('댓글 수정 성공!');
					getAllReplies();
				}
			}
		}); // end ajax()
	}); // end replies.on()
	
	
	
	
		
	}) // end document


</script>



</body>
</html>























