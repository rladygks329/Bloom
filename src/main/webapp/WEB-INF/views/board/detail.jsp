<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />

<title>게시글 상세보기</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	<hr>

	<br>
	<br>
	<input type="hidden" id="authorId" value="${vo.memberId}">
	<div class="container" style="border: 1px solid #ddd; padding: 20px;">
		<div class="container" >
			<h1 style="font-size: 36px;">${vo.boardTitle }</h1>
			<br>
			<div>
				<p style="font-size: 14px;">작성자: ${vo.authorNickname } </p>
				<fmt:formatDate value="${vo.boardDateCreated }" pattern="yyyy-MM-dd HH:mm:ss" var="boardDateCreated"/>
				<p style="font-size: 14px;">
    				${boardDateCreated }에 작성 · 조회수 ${vo.boardViewCount } · 좋아요 
    				<span id="boardLikeCount">${vo.boardLikeCount}</span>
				</p>	
			</div>
			<hr>
		</div>
		
		<div class="container">
			<div style="font-size: 18px;">
				${vo.boardContent }
			</div>
			<br>
			<hr>
		</div>
		
		<div class="container">
			<c:if test="${loginVo != null and loginVo.memberId eq vo.memberId}">
				<a href="update?boardId=${vo.boardId }&page=${page }"><input type="button" class="btn btn-light" value="글 수정"></a>
				
				<form action="deleteOrUpdate" method="post" style="display: inline-block; vertical-align: middle;">
				    <input type="hidden" name="boardId" value="${vo.boardId}">
				    <input type="hidden" name="memberId" value="${vo.memberId}">
				    <input type="hidden" name="boardTitle" value="${vo.boardTitle}">
				    <input type="hidden" name="boardContent"> <!-- value="${vo.boardContent}"를 뺐음 1124 -->
				    <input type="hidden" id="boardReplyCount" name="boardReplyCount" value="${vo.boardReplyCount}">
					<input type="submit" class="btn btn-light" value="글 삭제">	    
				</form>
			</c:if>
			
			<c:if test="${loginVo.memberId ne vo.memberId}">
    			<input type="button" id="boardLike" class="btn btn-light" value="${isLike ? '좋아요 취소' : '좋아요'}">		
			</c:if>
			<a href="list?&page=${page }&option=${option}&keyword=${keyword}"><input type="button" class="btn btn-light" value="글 목록"></a>
		</div>

		<br>
		<br>
		<br>
	
		<div class="container" style="border: 1px solid #ddd; padding: 20px;">
			<div class="container">
				<p style="font-size: 18px;">댓글 ${vo.boardReplyCount }</p>		
				<div class="d-flex">
					<c:if test="${vo.memberId != 999999}">
					<p class="me-2">
						<button class="btn btn-outline-secondary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample01" aria-expanded="false" aria-controls="collapseExample">
							댓글을 입력해 주세요
						</button>
					</p>
					</c:if>
					<p>
						<button class="btn btn-outline-secondary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample02" aria-expanded="false" aria-controls="collapseExample">
							댓글 보이기/숨기기
						</button>
					</p>
				</div>

				<div class="collapse" id="collapseExample01">
					<div class="card card-body">
						<input type="hidden" id="boardId" name="boardId" value="${vo.boardId }">
						<input type="hidden" id="memberId" name="memberId" value="${sessionScope.loginVo.memberId}">
						<textarea class="form-control" rows="3" cols="100" id="boardReplyContent" name="boardReplyContent" required></textarea>
						<input type="button" id="btnAddReply" value="등록">
					</div>
				</div>
				<hr>
				<br>
			</div>
			<div class="container">
				<div class="collapse show" id="collapseExample02">
				<div id="replies"></div>
				</div>
			</div>	
		</div>
	</div>
	
	<!-- 댓글 수정 Modal -->
	<div class="modal fade" id="replyModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="replyModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="replyModalLabel">댓글 수정</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <textarea class="form-control" rows="3" id="modalBoardReplyContent"></textarea>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	        <button type="button" class="btn btn-primary" id="btnModalUpdate">수정</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 대댓글 수정 Modal -->
	<div class="modal fade" id="commentModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="commentModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="commentModalLabel">대댓글 수정</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <textarea class="form-control" rows="3" id="modalBoardCommentContent"></textarea>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	        <button type="button" class="btn btn-primary" id="btnModalUpdate">수정</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
	
	<script>
		function goBack() {
		    window.history.back();
		}
	
	$(document).ready(function(){	
		getAllReplies();
	  	var boardId = $('#boardId').val(); 
	  	var memberId = $('#memberId').val(); 
		
		// 좋아요 onclieck 기능
	    $('#boardLike').click(function() {    	
	        var likeStatus = $('#boardLike').val() === '좋아요'; 
	        // 버튼 텍스트를 확인하여 좋아요 또는 좋아요 취소 요청 구분
	        
	        if(memberId ===''){
				alert("로그인을 하셔야 이용하실 수 있습니다."); 
				return;
			}
	        
	        // Ajax 요청을 통해 서버로 좋아요 또는 좋아요 취소 요청 전송
	        $.ajax({
	            type: likeStatus ? 'POST' : 'DELETE', // 좋아요인 경우 POST, 좋아요 취소인 경우 DELETE 요청
	            url: '/blooming/board/like/' + boardId + '/' + memberId,
	            success: function(data) {
	                // 좋아요 또는 좋아요 취소가 성공적으로 처리된 경우
	                if (likeStatus) {
	                    $('#boardLike').val('좋아요 취소');
	                } else {
	                    $('#boardLike').val('좋아요');
	                }
	                $('#boardLikeCount').text(data);
	            },
	            error: function(xhr, status, error) {
	                // 처리 실패 시 예외 처리
	                console.error(error);
	            }
	        });
	    });
	
		// 게시판 댓글 onclick 기능
		$('#btnAddReply').click(function(){
			console.log("댓글입력");
			var boardId = $('#boardId').val(); 
			var memberId = $('#memberId').val();
			var authorId = $("#authorId").val();
			
			if(authorId == "999999"){
				alert("삭제된 게시글에는 댓글을 달 수 없습니다.");
			}
			
			if (memberId === "") {	    
		        alert("댓글을 작성하려면 로그인이 필요합니다.");
		        return;
		    }
			
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
						location.reload(true);
						
					}
				}
			}); // end ajax()
		}); // end btnReplyAdd.click()
		
		// 게시판 댓글 전체 가져오기
		function getAllReplies() {
			var boardId = $('#boardId').val();
	
			console.log(boardId);
			var boardReplyCount = $('#boardReplyCount').val();
			console.log(boardReplyCount);
			
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
						var updateBtn = "";
						var deleteBtn = "";
						
						var disabled = 'disabled';
	
						console.log(memberId);
						console.log(this.memberId);
						
						if(memberId == this.memberId) {
							updateBtn= '<button id="btn_update" class="btn btn-outline-secondary btn-sm" style="margin-right: 3px;">수정</button>'
							deleteBtn= '<button id="btn_delete" class="btn btn-outline-secondary btn-sm" style="margin-right: 3px;">삭제</button>'
						}
						
		                // 포맷팅된 날짜 문자열 생성
		                var formattedDate = boardReplyDateCreated.getFullYear() + '-' +
		                                    ('0' + (boardReplyDateCreated.getMonth() + 1)).slice(-2) + '-' +
		                                    ('0' + boardReplyDateCreated.getDate()).slice(-2) + ' ' +
		                                    ('0' + boardReplyDateCreated.getHours()).slice(-2) + ':' +
		                                    ('0' + boardReplyDateCreated.getMinutes()).slice(-2) + ':' +
		                                    ('0' + boardReplyDateCreated.getSeconds()).slice(-2);
						
						list += '<div class="reply_item">'
							+ '<pre>'
							+ '<input type="hidden" id="replyId" value="' + this.boardReplyId +'">'
							+ '<input type="hidden" id="memberId" value="' + this.memberId +'">'
							+ '<input type="hidden" id="authorNickname" value="' + this.authorNickname +'">'
							+ '<div class="author-nickname">' + this.authorNickname + '님이 작성 · ' + formattedDate + '</div>'
							+ '<div class="form-control py-3" rows="3" id="boardReplyContent" margin-bottom: 5px;">' + this.boardReplyContent + '</div>'
						    + '<div style="text-align: right;">'
							+ updateBtn
							+ deleteBtn
							+ '<button id="btnComment" class="btn btn-outline-secondary btn-sm">답글</button>'	
							+ '</div>'
							+ '<div class="comments"></div>'
							+ '</pre>'
							+ '</div>';
					}); // end each()
					$('#replies').html(list);
				}
			); // end getJSON()
		} // end getAllReplies()
		
		// 댓글 수정시 모달창
		$('#replies').on('click', '.reply_item #btn_update', function(){
		    // 선택된 댓글의 replyId, replyContent 값을 저장
		    var replyId = $(this).parent().prevAll('#replyId').val();
		    var boardReplyContent = $(this).parent().prevAll('#boardReplyContent').val();
		    console.log("선택된 댓글 번호 : " + replyId + ", 댓글 내용 : " + boardReplyContent);
		    
		    $('#replyModal').data('replyId', replyId);
		    $('#replyModal').modal('show');
		});
		
		// 모달 내의 수정 버튼 클릭 이벤트
		$('#replyModal').on('click', '#btnModalUpdate', function(){
		    var replyId = $('#replyModal').data('replyId');
		    var boardReplyContent = $('#modalBoardReplyContent').val();
		    console.log("replyId = " + replyId);
		    console.log("boardReplyContent = " + boardReplyContent);
			
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
						$('#replyModal').modal('hide');
						alert('댓글 수정 성공!');
						getAllReplies();
					}
				}
			}); // end ajax()
		}); // end replies.on()
		
		// 삭제 버튼을 클릭하면 선택된 댓글 삭제 혹은 업데이트
		$('#replies').on('click', '.reply_item #btn_delete', function(){
			console.log(this);
			
			var boardId = $('#boardId').val();
			var replyId = $(this).parent().prevAll('#replyId').val();
			console.log("선택된 댓글 번호 : " + replyId);
				
			// ajax 요청
			$.ajax({
				type : 'DELETE', 
				url : 'replies/' + replyId, 
				headers : {
					'Content-Type' : 'application/json'
				},
				data : boardId,
				success : function(result) {
					console.log(result);
					if(result == 1) {
						alert('댓글 삭제 성공!');
						getAllReplies();
					}
				}
			}); // end ajax()
	
		}); // end replies.on()
		
	    // 답글 불러오기
		$('#replies').on('click','.reply_item #btnComment', function(){ 		
			var boardReplyId = $(this).closest('.reply_item').find('#replyId').val();
			console.log("답글버튼클릭 boardReplyId = " + boardReplyId);
			getAllComments(boardReplyId, this);
			
	    }) // end btnComment.click()     
	     
		// 게시판 답글 전체 가져오기
		function getAllComments(boardReplyId, context) {
			var commentContainer = $(context).closest('.reply_item').find('.comments');
			commentContainer.html('');
	        console.log("getAllComments() 호출: boardReplyId = " + boardReplyId);
	        console.log(this);
	        var url = 'comments/' + boardReplyId;
	        var reply_item = $(this).closest('.reply_item');
			
	        $.getJSON(
					url, 
					function(data) {
						console.log(data);
						var memberId = $('#memberId').val();
						var list =''; 
						var disabledWrite = 'disabled';
						
						if(memberId != "") {
							disabledWrite = '';
						}
										
						$(data).each(function(){
							console.log(this);
							
							var boardCommentDateCreated = new Date(this.boardCommentDateCreated);						
							var updateBtn = "";
							var deleteBtn = "";
							if(memberId == this.memberId) {
								updateBtn= '<button id="btn_comment_update" class="btn btn-outline-secondary btn-sm" style="margin-right: 3px;">수정</button>';
								deleteBtn= '<button id="btn_comment_delete" class="btn btn-outline-secondary btn-sm" style="margin-right: 3px;">삭제</button>';
							}						
	
			                // 포맷팅된 날짜 문자열 생성
			                var formattedDate = boardCommentDateCreated.getFullYear() + '-' +
			                                    ('0' + (boardCommentDateCreated.getMonth() + 1)).slice(-2) + '-' +
			                                    ('0' + boardCommentDateCreated.getDate()).slice(-2) + ' ' +
			                                    ('0' + boardCommentDateCreated.getHours()).slice(-2) + ':' +
			                                    ('0' + boardCommentDateCreated.getMinutes()).slice(-2) + ':' +
			                                    ('0' + boardCommentDateCreated.getSeconds()).slice(-2);
							
							list += '<div class="comment_item" style="margin-left: 20px;">'
								+ '<pre>'
								+ '<input type="hidden" id="commentId" value="' + this.boardCommentId +'">'
								+ '<input type="hidden" id="authorNickname" value="' + this.authorNickname +'">'
								+ '<i class="bi bi-arrow-return-right" style="font-size: 1rem"></i>'
								+ '<div class="author-nickname">' + this.authorNickname + '님이 작성 · ' + formattedDate + '</div>'
								+ '<div class="form-control py-3" rows="3" id="boardCommentContent" margin-bottom: 5px;">' + this.boardCommentContent + '</div>'
								+ '<div style="text-align: right;">'
								+ updateBtn
								+ deleteBtn
								+ '</div>'
								+ '</pre>'
								+ '</div>';
						}); // end each()
						console.log(list);
	
						// 답글을 모두 불러온 뒤에 새로운 답글을 작성할 수 있는 input 추가
			            list += '<div class="comment_regist_item" style="margin-left: 20px;">'
			                + '<pre>'
							+ '<input type="hidden" id="authorNickname" value="' + this.authorNickname +'">'
			                + '<textarea class="form-control" rows="3" id="boardCommentContent" margin-bottom: 5px;" placeholder="답글을 입력하세요"></textarea>'
			                + '<div style="text-align: right;">'
			                + '<button id="btnAddComment" class="btn btn-outline-secondary btn-sm" style="margin-right: 3px;" ' + disabledWrite + '>답글 추가</button>'
			                + '</div>'
			                + '</pre>'
			                + '</div>';
			            commentContainer.append(list);
					}
				); // end getJSON()
		} // end getAllComments
		
		// 답글 입력
	    $(document).on('click', '#btnAddComment', function(){    	
	   		var replyItem = $(this).closest('.reply_item');   		
	   		var memberId = $('#memberId').val(); 
			var boardReplyId = $(this).closest('.reply_item').find('#replyId').val();
			var authorReplyId = $(this).closest('.reply_item').find('#memberId').val();
		    var boardCommentContent = $(this).parent().prevAll('#boardCommentContent').val();
			var authorId = $("#authorId").val();
			
			console.log(boardReplyId);
			console.log(boardCommentContent);
			if(authorReplyId == "999999"){
				alert("삭제된 댓글에는 답글을 달 수 없습니다.");
				return;
			}
			
			if(authorId == "999999"){
				alert("삭제된 게시글에는 답글을 달 수 없습니다.");
				return;
			}
		    
		    var obj = {
					'memberId' : memberId,
					'boardReplyId' : boardReplyId, 
					'boardCommentContent' : boardCommentContent
			};
			console.log(obj);		
	
			$.ajax({
				type : 'POST',
				url : 'comments/' + boardReplyId,
				headers : {
					'Content-Type' : 'application/json'
				},
				data : JSON.stringify(obj),
				success : function(result){
					console.log(result);
					if(result == 1){
						alert('답글 입력 성공');
						getAllComments(boardReplyId, replyItem);
					} 
				} // end success
			}) // end ajax
	    }) // end document()
	    
		// 대댓글 수정버튼 클릭 시 모달창
	    $(document).on('click', '#btn_comment_update', function(){   	 
	   		var replyItem = $(this).closest('.reply_item');
	   		var boardReplyId = $(this).closest('.reply_item').find('#replyId').val();
			var boardCommentId = $(this).parent().prevAll('#commentId').val();
		    var boardCommentContent = $(this).parent().prevAll('#boardCommentContent').val(); 	  
		    console.log("boardCommentId = " + boardCommentId + " boardCommentContent = " + boardCommentContent);
		    
		    $('#commentModal').data('commentId', boardCommentId);
		    $('#commentModal').data('boardReplyId', boardReplyId);
		    $('#commentModal').data('replyItem', replyItem);
		    $('#commentModal').modal('show');
		});
		
		// 대댓글 모달창 수정버튼 클릭
		$('#commentModal').on('click', '#btnModalUpdate', function(){
			var boardCommentId = $('#commentModal').data('commentId');
			var boardReplyId = $('#commentModal').data('boardReplyId');
			var replyItem = $('#commentModal').data('replyItem');
			var boardCommentContent = $('#modalBoardCommentContent').val();
			console.log("코멘트아이디 = " + boardCommentId);
			console.log("코멘트내용 = " + boardCommentContent);
	   	 
	   		$.ajax({
	   			type : 'PUT',
	   		 	url : 'comments/' + boardCommentId,
		   		headers : {
		   			'Content-Type' : 'application/json'
		   		},
		   		data : boardCommentContent,
		   		success : function(result){
		   			console.log(result);
		   			if(result == 1){
		   				$('#commentModal').modal('hide');
		   				alert('답글 수정 성공!');
		   				getAllComments(boardReplyId, replyItem);
		   			}
		   		} // end success
		   	}) // end ajax			
		});

	    
	    $(document).on('click', '#btn_comment_delete', function(){
	   	 console.log(this);
			var replyItem = $(this).closest('.reply_item');
	   		var boardReplyId = $(this).closest('.reply_item').find('#replyId').val();
			var boardCommentId = $(this).parent().prevAll('#commentId').val();
			console.log("boardReplyId = " + boardReplyId + " boardCommentId = " + boardCommentId);
			 
			$.ajax({
				type : 'DELETE',
				url : 'comments/' + boardCommentId,
				headers : {
					'Content-Type' : 'application/json'
				},
				data : boardReplyId,
				success : function(result) {
					console.log(result);
					if(result == 1){
						alert('답글 삭제 성공!');
						getAllComments(boardReplyId, replyItem);
					}
				} // end success
			}) // end ajax
	    }); // end document()
	
	
	}) // end document
	
	</script>

</body>

</html>























