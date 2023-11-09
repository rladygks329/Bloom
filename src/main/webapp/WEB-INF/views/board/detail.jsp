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

	<h2>글내용 보기</h2>
	<div>
		<p>글 번호: ${vo.boardId }</p>
	</div>
	<div>
		<p>제목: ${vo.boardTitle }</p>
	</div>
	<div>
		<p>작성자: ${vo.authorNickname } </p>
		<fmt:formatDate value="${vo.boardDateCreated }"
			pattern="yyyy-MM-dd HH:mm:ss" var="boardDateCreated"/>
		<p>작성일: ${boardDateCreated }</p>
		<p>조회수: ${vo.boardViewCount }</p>
		<p>좋아요: ${vo.boardLikeCount }</p>
	</div>
	<div>
		<textarea rows="20" cols="120" readonly>${vo.boardContent }</textarea>
	</div>
	
	<input type="hidden" id="boardId" name="boardId" value="${vo.boardId }">
	<input type="hidden" id="memberId" value="${sessionScope.loginVo.memberId}">
	<a><input type="button" onclick="goBack()" value="글 목록"></a>
	<a href="update?boardId=${vo.boardId }&page=${page }"><input type="button" value="글 수정"></a>
	<input type="button" value="글 삭제">
					
	<input type="button" id="boardLike" value="좋아요">		

	<br>
	<br>
	<br>
			
	<div>
		<p>답글을 작성해 주세요</p>
		<textarea rows="10" cols="120" name="boardContent" id="answerContent" placeholder="내용 입력"></textarea>
		<input type="button" value="저장" id="saveAnswer">
	</div>
	
<script>
	function goBack() {
	    window.history.back();
	}

$(document).ready(function(){
	getAllReplies();
	
  	var boardId = $('#boardId').val(); 
  	var memberId = $('#memberId').val(); 

	// Ajax 요청을 통해 초기 "좋아요" 상태를 가져옴
  	$.ajax({
        type: 'GET',
        url: '/blooming/board/like/' + boardId + '/' + memberId,
        success: function(data) {
            // "좋아요" 상태를 확인하고 버튼 텍스트를 업데이트
            if (data) {
                $('#boardLike').val('좋아요 취소');
            } else {
                $('#boardLike').val('좋아요');
            }
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
	
	// 좋아요 onclieck 기능
    $('#boardLike').click(function() {
        var likeStatus = false; // 좋아요 상태를 나타내는 변수 (초기값: 좋아요하지 않음) 위에 확인 후 삭제
        // 버튼 텍스트를 확인하여 좋아요 또는 좋아요 취소 요청 구분
        if ($('#boardLike').val() === '좋아요') {
            likeStatus = true; // 좋아요
        }
        
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
            },
            error: function(xhr, status, error) {
                // 처리 실패 시 예외 처리
                console.error(error);
            }
        });
    });

	// 게시판 댓글 onclick 기능
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
						+ '<input type="hidden" id="authorNickname" value="' + this.authorNickname +'">'
						+ this.authorNickname
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
	
	// 삭제 버튼을 클릭하면 선택된 댓글 삭제
	$('#replies').on('click', '.reply_item .btn_delete', function(){
		console.log(this);
	
		var boardId = $('#boardId').val();
		var replyId = $(this).prevAll('#replyId').val();
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
	
}) // end document


</script>



</body>
</html>























