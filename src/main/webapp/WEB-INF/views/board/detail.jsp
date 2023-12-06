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
<style>
        body {
            margin-top: 20px;
            background: #eee;
        }


        .content-item {
            padding: 30px 0;
            background-color: #FFFFFF;
        }

        .content-item.grey {
            background-color: #F0F0F0;
            padding: 50px 0;
            height: 100%;
        }

        .content-item h2 {
            font-weight: 700;
            font-size: 25px;
            line-height: 45px;
            text-transform: uppercase;
            margin: 20px 0;
        }

        .content-item h3 {
            font-weight: 400;
            font-size: 20px;
            color: #555555;
            margin: 10px 0 15px;
            padding: 0;
        }

        .content-headline {
            height: 1px;
            text-align: center;
            margin: 20px 0 70px;
        }

        .content-headline h2 {
            background-color: #FFFFFF;
            display: inline-block;
            margin: -20px auto 0;
            padding: 0 20px;
        }

        .grey .content-headline h2 {
            background-color: #F0F0F0;
        }

        .content-headline h3 {
            font-size: 14px;
            color: #AAAAAA;
            display: block;
        }

        /* BOXES */

        .box {
            background-color: #FFFFFF;
            padding: 10px 20px;
            box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        .box h3 {
            margin: 30px 0 5px;
            font-weight: bold;
        }

        .box ul {
            margin: 0;
        }

        .box ul li {
            font-size: 13px;
            border-bottom: 1px dashed #DDDDDD;
            padding: 10px 0;
            font-weight: 600;
        }

        .box ul li:last-child {
            border-bottom: 0;
        }

        .box ul li i {
            font-size: 18px;
            margin-right: 20px;
        }

        .box.categories ul li i {
            color: #BBBBBB;
            position: relative;
            top: 2px;
            width: 20px;
        }

        .posts ul li a {
            font-size: 11px;
            line-height: 23px;
        }

        .posts ul li a:hover {
            color: #333333;
        }

        .posts ul li div {
            font-size: 13px;
            color: #999999;
            font-weight: bold;
            text-align: right;
            margin-top: 5px;
        }

        .box.posts ul li i {
            color: #333333;
            font-size: 14px;
            margin-right: 10px;
        }

        .box.tags ul.blog-tags li {
            border: 0;
        }

        ul.blog-tags li {
            padding: 7px 0;
        }

        div ul.blog-tags li i,
        div .box.tags ul li i {
            color: #FFFFFF;
            position: relative;
            top: 1px;
            font-size: 14px;
        }

        .blog p img.pull-left {
            margin-right: 15px;
        }

        .blog p img {
            max-width: 300px;
            border: 3px solid #BBBBBB;
            padding: 5px;
        }


        hr {
            margin-top: 20px;
            margin-bottom: 20px;
            border: 0;
            border-top: 1px solid #FFFFFF;
        }

        a {
            color: #82b440;
            text-decoration: none;
        }

        .blog-comment::before,
        .blog-comment::after,
        .blog-comment-form::before,
        .blog-comment-form::after {
            content: "";
            display: table;
            clear: both;
        }

        .blog-comment ul {
            list-style-type: none;
            padding: 0;
        }

        .blog-comment img {
            opacity: 1;
            filter: Alpha(opacity=100);
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            -o-border-radius: 4px;
            border-radius: 4px;
        }

        .blog-comment img.avatar {
            position: relative;
            float: left;
            margin-left: 0;
            margin-top: 0;
            width: 65px;
            height: 65px;
        }

        .blog-comment .post-comments {
            border: 1px solid #eee;
            margin-bottom: 20px;
            margin-right: 0px;
            padding: 10px 20px;
            position: relative;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            -o-border-radius: 4px;
            border-radius: 4px;
            background: #fff;
            color: #6b6e80;
            position: relative;
        }

        .blog-comment .meta {
            font-size: 13px;
            color: #aaaaaa;
            padding-bottom: 8px;
            margin-bottom: 10px !important;
            border-bottom: 1px solid #eee;
        }

        .blog-comment ul.comments ul {
            list-style-type: none;
            padding: 0;
            margin-left: 85px;
        }

        .blog-comment-form {
            padding-left: 15%;
            padding-right: 15%;
            padding-top: 40px;
        }

        .blog-comment h3,
        .blog-comment-form h3 {
            margin-bottom: 40px;
            font-size: 26px;
            line-height: 30px;
            font-weight: 800;
        }
    </style>
<title>게시글 상세보기</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>

	<!-- <input type="hidden" id="loginMemberId" name="loginMemberId" value="${sessionScope.loginVo.memberId}"> -->
	
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
		<p>댓글수: ${vo.boardReplyCount }</p>
		<p>좋아요: ${vo.boardLikeCount }</p>
	</div>
	<div class="boardContent" style="border: 1px solid black; padding: 10px;">
		${vo.boardContent }
	</div>
	<a href="list?&page=${page }">
	<input type="button" value="글 목록">
	</a>
	<c:if test="${loginVo != null and loginVo.memberId eq vo.memberId}">
		<a href="update?boardId=${vo.boardId }&page=${page }"><input type="button" value="글 수정"></a>
		
		<form action="deleteOrUpdate" method="post">
		    <input type="hidden" name="boardId" value="${vo.boardId}">
		    <input type="hidden" name="memberId" value="${vo.memberId}">
		    <input type="hidden" name="boardTitle" value="${vo.boardTitle}">
		    <input type="hidden" name="boardContent"> <!-- value="${vo.boardContent}"를 뺐음 1124 -->
		    <input type="hidden" id="boardReplyCount" name="boardReplyCount" value="${vo.boardReplyCount}">
			<input type="submit" value="글 삭제">	    
		</form>
	</c:if>
	
	<c:if test="${loginVo.memberId ne vo.memberId}">
		<input type="button" id="boardLike" value="좋아요">		
	</c:if>
	
	<br>
	<br>
	<br>
			
	<div>
		<p>댓글을 작성해 주세요</p>
		<input type="hidden" id="boardId" name="boardId" value="${vo.boardId }">
		<input type="hidden" id="memberId" name="memberId" value="${sessionScope.loginVo.memberId}">
		<textarea rows="10" cols="120" id="boardReplyContent" name="boardReplyContent" placeholder="내용 입력"></textarea>
		<input type="button" id="btnAddReply" value="댓글등록" >
	</div>
	<div>
		<div id="replies"></div>
	</div>	
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
	
	
	
	<section class="content-item grey" id="blog-item">
        <div class="container">
            <div class="row">
                <!-- BLOG POST - START -->
                <div class="col blog">
                    <h2>${vo.boardTitle }</h2>
                    <ul class="blog-detail list-unstyled list-inline">
                        <li><i class="fa fa-calendar"></i> ${boardDateCreated }</li>
                        <li><i class="fa fa-user"></i> ${vo.authorNickname }</li>
                        <li><i class="fa fa-comments"></i> ${vo.boardReplyCount }</li>
                    </ul>
                    <div class="boardContent" style="border: 1px solid black; padding: 10px;">
						${vo.boardContent } Etiam consequat odio sollicitudin metus condimentum, 
					</div>
                </div>
                <!-- BLOG POST - END -->

                <div>
                    <div class="col">
                        <div class="row">
                            <div class="col">
                                <div class="blog-comment">
                                    <h3 class="text-success">Comments</h3>
                                    <hr />
                                    <ul class="comments">
                                        <li class="clearfix">
                                            <div class="post-comments border border-secondary">
                                                <p class="meta">Dec 18, 2014 
                                                	JohnDoe says : <i class="pull-right">
                                                	<a href="#"><small>Reply</small></a></i></p>
                                                <p>
                                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                                    Etiam a sapien odio, sit amet
                                                </p>
                                            </div>
                                        </li>
                                        <li class="clearfix">
                                            <div class="post-comments border border-secondary">
                                                <p class="meta">Dec 19, 2014 <a href="#">JohnDoe</a> says : <i
                                                        class="pull-right"><a href="#"><small>Reply</small></a></i></p>
                                                <p>
                                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                                    Etiam a sapien odio, sit amet
                                                </p>
                                            </div>

                                            <ul class="comments border border-secondary">
                                                <li class="clearfix">
                                                    <div class="post-comments">
                                                        <p class="meta">Dec 20, 2014 <a href="#">JohnDoe</a> says : <i
                                                                class="pull-right"><a
                                                                    href="#"><small>Reply</small></a></i></p>
                                                        <p>
                                                            Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                                            Etiam a sapien odio, sit amet
                                                        </p>
                                                    </div>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </section>
	
	
	
	
	
	
	
	
	
	
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
						
						var disabled = 'disabled';
	
						console.log(memberId);
						console.log(this.memberId);
						
						if(memberId == this.memberId) {
							disabled = '';
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
							+ this.authorNickname
							+ '&nbsp;&nbsp;' // 공백
							+ '<textarea rows="5" cols="50" id="boardReplyContent">' + this.boardReplyContent + '</textarea>'
							+ '&nbsp;&nbsp;' // 공백
							+ formattedDate
							+ '&nbsp;&nbsp;' // 공백
							+ '<button class="btn_update" ' + disabled + '>수정</button>'
							+ '<button class="btn_delete" ' + disabled + '>삭제</button>'
							+ '<button class="btnComment">답글</button>'		
							+ '<div class="comments"></div>'
							+ '</pre>'
							+ '</div>';
					}); // end each()
					$('#replies').html(list);
				}
			); // end getJSON()
		} // end getAllReplies()
		
		
		/*
		                                        <li class="clearfix">
                                            <div class="post-comments border border-secondary">
                                                <p class="meta">Dec 18, 2014 
                                                	JohnDoe says : </p>
                                                <p>
                                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                                    Etiam a sapien odio, sit amet
                                                </p>
                                            </div>
                                        </li>
		
		*/
		
		function makeReplyHtml(reply){

			const li = $("<li>").addClass(clearfix);
			const div = $("<div>").addClass("post-comments").addClass("border").addClass("border-secondary");
			const p = $("<p>").addClass(meta).text(reply.boardReplyDateCreated + ' ' + reply.authorNickname + '님이 입력');
			const q = $("<p>").text(reply.boardReplyContent);
			
			return li;
		}
		
		
		
			
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
		
		// 삭제 버튼을 클릭하면 선택된 댓글 삭제 혹은 업데이트
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
		
	    // 답글 불러오기
		$('#replies').on('click','.reply_item .btnComment', function(){ 		
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
							var disabled = 'disabled';						
							
							if(memberId == this.memberId) {
								disabled = '';
							}						
	
			                // 포맷팅된 날짜 문자열 생성
			                var formattedDate = boardCommentDateCreated.getFullYear() + '-' +
			                                    ('0' + (boardCommentDateCreated.getMonth() + 1)).slice(-2) + '-' +
			                                    ('0' + boardCommentDateCreated.getDate()).slice(-2) + ' ' +
			                                    ('0' + boardCommentDateCreated.getHours()).slice(-2) + ':' +
			                                    ('0' + boardCommentDateCreated.getMinutes()).slice(-2) + ':' +
			                                    ('0' + boardCommentDateCreated.getSeconds()).slice(-2);
							
							list += '<div class="comment_item">'
								+ '<pre>'
								+ '<input type="hidden" id="commentId" value="' + this.boardCommentId +'">'
								+ '<input type="hidden" id="authorNickname" value="' + this.authorNickname +'">'
								+ this.authorNickname
								+ '&nbsp;&nbsp;' // 공백
								+ '<textarea rows="2" cols="50" id="boardCommentContent">' + this.boardCommentContent + '</textarea>'
								+ '&nbsp;&nbsp;' // 공백
								+ formattedDate
								+ '&nbsp;&nbsp;' // 공백
								+ '<button class="btnUpdateComment" ' + disabled + '>수정</button>'
								+ '<button class="btnDeleteComment" ' + disabled + '>삭제</button>'
								+ '</pre>'
								+ '</div>';
						}); // end each()
	
						// 답글을 모두 불러온 뒤에 새로운 답글을 작성할 수 있는 input 추가
			            list += '<div class="comment_regist_item">'
			                + '<pre>'
							+ '<input type="hidden" id="authorNickname" value="' + this.authorNickname +'">'
			                + '&nbsp;&nbsp;' // 공백
			                + '<textarea rows="2" cols="50" id="boardCommentContent" placeholder="답글을 입력하세요"></textarea>'
			                + '&nbsp;&nbsp;' // 공백
			                + '<button class="btnAddComment" ' + disabledWrite + '>답글 추가</button>'
			                + '</pre>'
			                + '</div>';
			            commentContainer.append(list);
					}
				); // end getJSON()
		} // end getAllComments
		

		
		
		// 답글 입력
	    $(document).on('click', '.btnAddComment', function(){    	
	   		var replyItem = $(this).closest('.reply_item');   		
	   		var memberId = $('#memberId').val(); 
			var boardReplyId = $(this).closest('.reply_item').find('#replyId').val();
		    var boardCommentContent = $(this).prevAll('#boardCommentContent').val(); 	    
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
	    
		// 대댓글 수정
	    $(document).on('click', '.btnUpdateComment', function(){   	 
	   		var replyItem = $(this).closest('.reply_item');
	   		var boardReplyId = $(this).closest('.reply_item').find('#replyId').val();
			var boardCommentId = $(this).prevAll('#commentId').val();
		    var boardCommentContent = $(this).prevAll('#boardCommentContent').val(); 	  
		    console.log("boardCommentId = " + boardCommentId + " boardCommentContent = " + boardCommentContent);
	   	 
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
	   				 alert('답글 수정 성공!');
	   				 getAllComments(boardReplyId, replyItem);
	   				 console.log("수정 ajax 에서 this = " + this);
	   			 }
	   		 } // end success
	   	 }) // end ajax
	    }) // end document(*)
	    
	    $(document).on('click', '.btnDeleteComment', function(){
	   	 console.log(this);
			var replyItem = $(this).closest('.reply_item');
	   		var boardReplyId = $(this).closest('.reply_item').find('#replyId').val();
			var boardCommentId = $(this).prevAll('#commentId').val();
			 
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























