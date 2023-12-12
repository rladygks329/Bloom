<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<!-- Jquery -->
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<!-- Bootstrap css -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous" />
<!-- Bootstrap icons -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
	rel="stylesheet" />
<!-- Bootstrap core JS-->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<style>
.star-rating {
	line-height: 32px;
	font-size: 1.25em;
}

.review-rating .bi-star-fill {
	color: #F4CE14;
}

.star-rating .bi-star-fill {
	color: #F4CE14;
}
</style>
<script>
	function convertLikeBtn(result) {
		$("#btn-like i").removeClass("bi-suit-heart").addClass("bi-suit-heart-fill");
		$("#btn-like i").text(" " + result);
		$("#btn-like").off("click");
		$("#btn-like").click(removeLike);
	}
	
	function convertCartBtn() {
		const addBtn = $("#addCartBtn").empty().off("click");
		const link = 
			$("<a class='text-reset link-underline link-underline-opacity-0'>").text(" 장바구니 바로 가기").attr("href", "/blooming/cart");
		addBtn.append(link);
	}
	
	function addLike() {
		const lectureId = $("#lectureId").val();
		const memberId = $("#memberId").val();
		
		if(memberId ===''){
			alert("로그인을 하셔야 이용하실 수 있습니다.");
			return;
		}
		
		$.ajax({
			type : "POST",
			url : `like/${lectureId}`,
			headers : {
				'Content-Type' : 'application/json'
			},
			statusCode: {
				200: convertLikeBtn,
				409: function(response) {
					const count = Number($("#btn-like i").text());
					convertLikeBtn(count + 1);
					alert(response.responseText);
				}
			}
		});
	}
	function removeLike() {
		const lectureId = $("#lectureId").val();
		const memberId = $("#memberId").val();
		
		if(memberId ===''){
			alert("로그인을 하셔야 이용하실 수 있습니다.");
			return;
		}
		
		$.ajax({
			type : "DELETE",
			url : `like/${lectureId}`,
			headers : {
				'Content-Type' : 'application/json'
			},
			success : function(result) {
				$("#btn-like i").removeClass("bi-suit-heart-fill").addClass(
						"bi-suit-heart");
				$("#btn-like i").text(" " + result);
				$("#btn-like").off("click");
				$("#btn-like").click(addLike);
			},
		});
	}
	
	function addCart() {
		const lectureId = $("#lectureId").val();
		const memberId = $("#memberId").val();
		
		if(memberId ===''){
			alert("로그인을 하셔야 이용하실 수 있습니다.");
			return;
		}
		
		$.ajax({
			type : "POST",
			url : "/blooming/cart/item/" + lectureId,
			headers : {
				'Content-Type' : 'application/json'
			},
			statusCode: {
				201: convertCartBtn,
				409: function(response) {
					alert(response.responseText);
					convertCartBtn();
				}
			}	
		});
	}
	
	// window.onload
	$(function() {
		let like = $("#like").val();
		$("#btn-like").click((like === 'false') ? addLike : removeLike);
		$("#addCartBtn").click(addCart);
	    
	    $('#exampleModal').on('show.bs.modal', function (event) {
	        const button = $(event.relatedTarget);
	        const replyId = button.data('replyid');
	        $('#lecture-reply-id').val(replyId);
	    })

	    $("#modal-edit").click(function(event){
	        const replyId = $('#lecture-reply-id').val();
	        updateReply(replyId);
	    })
	    
	    $(".more-reply-btn").click(fetchNext);
        fetchNext();
	})
</script>
<meta charset="UTF-8">
<title>Bloom - ${lecture.lectureTitle }</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	<hr>
	<!-- Product section-->
	<input id="like" type="hidden" value="${likeStatus}" />
	<input id="lectureId" type="hidden" value="${lectureId}" />
	<input id="authorId" type="hidden" value="${lecture.memberId }">
	<input id="memberId" type="hidden" value="${memberId}" />
	<input id="purchase" type="hidden" value="${purchaseStatus}" />
	<input id="cart" type="hidden" value="${cartStatus}" />
	<input id="purchase" type="hidden" value="${purchaseStatus}" />
	<input id="authorNickname" type="hidden" value="${loginVo.memberNickname}" />
	
	<section class="py-5">
		<div class="container px-4 px-lg-5 my-5">
			<div class="row gx-4 gx-lg-5 align-items-center">
				<div class="col-md-6">
					<img class="card-img-top mb-5 mb-md-0"
						src="${lecture.lectureThumbnailUrl }" alt="..."
						onerror="this.src='	https://dummyimage.com/600x700/dee2e6/6c757d.jpg';" />
				</div>
				<div class="col-md-6">
					<div class="small mb-1"></div>
					<h1 class="display-5 fw-bolder">${lecture.lectureTitle }</h1>
					<div class="fs-5">
						<a href="/blooming/lecture/list?keyword=writer.${author.memberName }&page=1" class="text-reset link-underline-secondary">${author.memberName}</a>
					</div>
					<div class="fs-5 mb-5">
						<fmt:formatNumber type="number" maxFractionDigits="3" value="${lecture.lecturePrice }" /> 원
						<br>
						
					</div>
					<div class="fs-5 mb-5"></div>
					<p class="lead">${lecture.lectureDescription }</p>
					<div class="d-flex">
						<button id="btn-like" class="btn btn-outline-dark flex-shrink-0 me-1" type="button">
							<i class="bi bi-suit-heart${likeStatus ? "-fill" : ""} p-1" > ${lecture.lectureLikeCount } </i>
						</button>

						<!-- 차례로 원작자, 구매자, 장바구니에 있는 사람, 없는 사람 -->
						<c:choose>
							<c:when test="${memberId eq lecture.memberId}">
								<button class="btn btn-outline-dark flex-shrink-0" type="button">
									<a class="text-reset link-underline link-underline-opacity-0"
										href="/blooming/lecture/${lectureId }/course"> 강의 들으러 가기 </a>
								</button>
							</c:when>
							<c:when test="${purchaseStatus }">
								<button class="btn btn-outline-dark flex-shrink-0" type="button">
									<a class="text-reset link-underline link-underline-opacity-0"
										href="/blooming/lecture/${lectureId }/course">강의 들으러 가기</a>
								</button>
							</c:when>
							<c:when test="${not purchaseStatus and cartStatus}">
								<button class="btn btn-outline-dark flex-shrink-0" type="button">
									<a class="text-reset link-underline link-underline-opacity-0"
										href="/blooming/cart"> 장바구니 바로 가기 </a>
								</button>
							</c:when>
							<c:when test="${not purchaseStatus and not cartStatus}">
								<button id="addCartBtn" class="btn btn-outline-dark flex-shrink-0" type="button">
									<i class="bi-cart-fill me-1"></i> 장바구니 담기
								</button>
							</c:when>
						</c:choose>

					</div>
				</div>
				
				<div class="collapse my-3" id="collapseExample">
					<div class="">
						<div class="d-flex">
							<div class="col-md-3 me-3">
								<img class="img-fluid mb-1" src="/blooming/image/display?fileName=${author.memberProfileUrl }" onerror="this.src='https://dummyimage.com/450x300/dee2e6/6c757d.jpg';" alt="..." loading="lazy"/>
							</div>
							${author.memberName }
							<br/>
							${author.memberIntroduce }
							</div>
					</div>
				</div>
				<div class="d-flex flex-row-reverse my-3">
					<a class="btn btn-primary " data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
						강사 소개 보기
					</a>
				</div>
				
			</div>
		</div>
	</section>
	<hr>

	<div class="container px-4 px-lg-5 my-5">
		<!-- 영상 리스트 -->
		<h3 class="display-6 fw-bolder">커리 큘럼</h3>
		<ul class="list-group">
			<c:forEach var="lesson" items="${lessons}">
				<li class="list-group-item">${lesson.lessonName }</li>
			</c:forEach>
		</ul>
		<hr>
		<div class="d-flex flex-column">
			<h3 class="display-6 fw-bolder">수강평</h3>
			<div class="my-comment-container"></div>
			<div class="lecture-comment-container"></div>
			<button class="btn btn-secondary m-auto more-reply-btn">더 보기</button>
			<div class="d-flex justify-content-center mt-3" >
  				<div class="spinner-border" role="status" hidden>
    				<span class="visually-hidden">Loading...</span>
  				</div>
			</div>
		</div>

		<!-- 댓글 입력 창 -->
		<hr>
		<c:if test="${ empty memberId }">
			<p class="text-secondary">강의를 구매하셔야 수강평을 남길 수 있습니다.</p>
		</c:if> 
		<c:if test="${(not empty memberId) and purchaseStatus}">
			<div
				class="lecture-comment-prompt input-group border border-dark p-1">
				<div class="container">
					<div class="row justify-content-end">
						<div class="col-lg-12">
							<div class="review-rating" class="star-rating">
								평점 : <i class="bi bi-star-fill" data-rating="1"></i> <i
									class="bi bi-star-fill" data-rating="2"></i> <i
									class="bi bi-star-fill" data-rating="3"></i> <i
									class="bi bi-star-fill" data-rating="4"></i> <i
									class="bi bi-star-fill" data-rating="5"></i> <input
									id="review-score" type="hidden" name="whatever1"
									class="rating-value" value="5">
							</div>
						</div>
					</div>
				</div>
				<input id="review-content" type="text" class="form-control"
					placeholder="Type your reply">
				<div class="input-group-append">
					<button class="btn btn-primary" type="button" onclick="addReply()">Send</button>
				</div>
			</div>
		</c:if>
	</div>
	<!-- modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<input id="lecture-reply-id" type="hidden">
					<div class="input-group p-1">
						<div class="container">
							<div class="row justify-content-end">
								<div class="col-lg-12">
									<div class="review-rating">
										평점 : <i class="bi bi-star-fill" data-rating="1"></i> <i
											class="bi bi-star-fill" data-rating="2"></i> <i
											class="bi bi-star-fill" data-rating="3"></i> <i
											class="bi bi-star-fill" data-rating="4"></i> <i
											class="bi bi-star-fill" data-rating="5"></i> <input
											id="review-score" type="hidden"class="rating-value" value="5">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-body">
					<form>
						<div class="mb-3">
							<label for="message-text" class="col-form-label">수강평:</label>
							<textarea class="form-control" id="message-text"></textarea>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="modal-close" class="btn btn-secondary"
						data-bs-dismiss="modal">닫기</button>
					<button type="button" id="modal-edit" class="btn btn-primary">수정하기</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		const $star_rating = $('.review-rating .bi');
		const SetRatingStar = function(starRating) {
			return $star_rating.each(function() {
						if (parseInt($(this).siblings('input.rating-value').val()) >= parseInt($(this).data('rating'))) 
						{
							return $(this).removeClass('bi-star').addClass('bi-star-fill');
						} else {
							return $(this).removeClass('bi-star-fill').addClass('bi-star');
						}
					});
		};

		$star_rating.on('click', function() {
			$(this).siblings('input.rating-value').val($(this).data('rating'));
			return SetRatingStar(this);
		});
		SetRatingStar();
		
		function makeStarRatingDiv(rating){
			const starRatingDiv = $("<div>").addClass("star-rating");
			for (let i = 1; i <= 5; i++) {
				const starIcon = $("<i>").addClass("bi");
				if (i <= rating) {
					starIcon.addClass("bi-star-fill");
				} else if (i - 0.5 <= rating && rating < i) {
					starIcon.addClass("bi-star-half");
				} else {
					starIcon.addClass("bi-star");
		        }
				starRatingDiv.append(starIcon);
		    }
			return starRatingDiv;
		}
		
		function makeReplyBody(reply){
	        const replyCard = $("<div>").addClass("card my-3");
	        const cardHeader = $("<div>").addClass("card-header");
	        const cardBody = $("<div>").addClass("card-body");
	        const starRatingDiv = makeStarRatingDiv(reply.lectureReplyScore);
	        cardHeader.append(starRatingDiv);
	        cardHeader.append($("<span>").text(reply.authorNickName));
	        cardBody.append($("<div>").text(reply.lectureReplyContent));
	        
	        replyCard.append(cardHeader);
	        replyCard.append(cardBody);
			return replyCard;	
		}
		
		function makeReplyFooter(reply){
			const cardFooter = $('<div class="d-flex flex-row-reverse">').addClass("card-footer");
	        cardFooter.append($(`<input type="hidden" value=${reply.lectureReplyId}>`))
	        const editBtn = $("<button class='me-1 btn btn-primary btn-edit' data-bs-toggle='modal' data-bs-target='#exampleModal' data-replyId='" + reply.lectureReplyId + "'>").text("수정");
	        const deleteBtn = $("<button>").addClass("btn").addClass("btn-danger").addClass("btn-delete").data("replyid", reply.lectureReplyId).text("삭제");
	        deleteBtn.click(function(){
	        	deleteReply(reply.lectureReplyId);
	        })
	        cardFooter.append(deleteBtn).append(editBtn);
	        return cardFooter;
		}
		
		function makeMyComment(reply){
			if(reply == null){
				return;
			}
			const cardBody = makeReplyBody(reply);
			const cardFooter = makeReplyFooter(reply);
			$(cardBody).append(cardFooter);
			$(".my-comment-container").empty();
	        $(".my-comment-container").append(cardBody);
		}
		
		function addReply(){
		    const memberId = $("#memberId").val();
		    const lectureId = $("#lectureId").val();
		    const lectureReplyScore = $("#review-score").val();
		    const lectureReplyContent = $("#review-content").val();
		    const authorNickName = $("#authorNickname").val();
	
		    if(memberId === ''){
		        alert("로그인한 유저만 이용가능합니다.");
		        return;
		    }
	
		    if(lectureReplyContent === ''){
		        alert("내용을 입력해주세요");
		        return;
		    }
		    
		    const data = {
		        memberId: memberId,
		        lectureId: lectureId,
		        lectureReplyScore: lectureReplyScore,
		        lectureReplyContent: lectureReplyContent
		    };
		    
		    $.ajax({
		        type : "POST",
		        url : `/blooming/lecture/${lectureId}/replies`,
		        headers : {
		            'Content-Type' : 'application/json'
		        },
		        data : JSON.stringify(data),
		        statusCode : {
		        	201: function(result) {
			            console.log("addReplies 성공 result : " + result);
			            const newReply = {...data, lectureReplyId : result, authorNickName: authorNickName}; 
			            makeMyComment(newReply);
			         	// 기존 값 지우기
			            $("#review-content").val("");
			            $("#review-score").val(5);
			            SetRatingStar();
			            $('.lecture-comment-prompt').hide();
			        },
					409: function(response) {
						alert(response.responseText);
					}
		       }
		    }); // end ajax
		}
	
		function updateReply(replyId){
			console.log("updateReply() 호출 : replyId: " + replyId); 
		    const lectureReplyId = $('#lecture-reply-id').val();
		    const memberId = $("#memberId").val();
		    const lectureId = $("#lectureId").val();
		    const lectureReplyScore = $(".modal .review-rating input").val();
		    const lectureReplyContent = $(".modal-body textarea").val();
	
		    if(memberId === ''){
		        alert("로그인한 유저만 이용가능합니다.");
		        return;
		    }
	
		    if(lectureReplyContent === ''){
		        alert("내용을 입력해주세요");
		        return;
		    }
		    
		    const data = {
		        lectureReplyId: replyId,
		        memberId: memberId,
		        lectureId: lectureId,
		        lectureReplyScore: lectureReplyScore,
		        lectureReplyContent: lectureReplyContent
		    };
		    
		    $.ajax({
		        type : "PUT",
		        url : "/blooming/lecture/" + lectureId + "/replies/" + lectureReplyId,
		        headers : {
		            'Content-Type' : 'application/json'
		        },
		        data : JSON.stringify(data),
		        success : function(result) {
		        	const newContent = $(".modal-body textarea").val()
		            const newRating = $(".modal .review-rating input").val();
		        	const starRatingDiv = makeStarRatingDiv(newRating);
		        	
		        	$(".my-comment-container .card-body > div").text(newContent);
		        	$(".my-comment-container .card-header i").remove();
		        	$(".my-comment-container .card-header").prepend(starRatingDiv);
		        	
		            // 기존 값 지우기
		            $(".modal-body textarea").val("")
		            $(".modal .review-rating input").val(5);
		            SetRatingStar();
	
		            $('.modal').modal('hide');
		            console.log("updateReplies 성공 result : " + result);
		        },
		    }); // end ajax
		}
	
		function deleteReply(replyId){
			console.log("deleteReply() 호출 replyId: " + replyId);
		    const memberId = $("#memberId").val();
		    const lectureId = $("#lectureId").val();
	
		    if(memberId === ''){
		        alert("로그인한 유저만 이용가능합니다.");
		        return;
		    }
		    
		    $.ajax({
		        type : "DELETE",
		        url : "/blooming/lecture/" + lectureId + "/replies/" + replyId,
		        headers : {
		            'Content-Type' : 'application/json'
		        },
		        success : function(result) {
		            console.log("deleteReplies 성공 result : " + result);
		            $(".my-comment-container").empty();
		            $('.lecture-comment-prompt').show();
		        },
		        error: function(){
		        }
		    }); // end ajax
		}
		
        
        const pageSize = 1;
        let maxReplyId = 0;
        function fetchNext(){
        	const lectureId = $("#lectureId").val();
        	$(".spinner-border").show();
        	$.ajax({
    		    type : "GET",
    		    url : "/blooming/lecture/${lectureId}/replies?lastReplyId=" + maxReplyId + "&pageSize=" + pageSize,
    		    headers : {
    		    	'Content-Type' : 'application/json'
    		   	},
    		   	success : function(result) {
    		   		console.log(result);
    		   		$(".spinner-border").hide();
    		   		$('.lecture-comment-prompt').toggle(!result.hasPrevComment);
    		   		
    		   		// 최댓값 갱신
    		   		let resultArray = result.list.map(reply => reply.lectureReplyId);
    		   		if(resultArray.length != 0){
        		   		maxReplyId = Math.max(...resultArray);
    		   		}else if(resultArray.length == 0){
    		   		 	$(".more-reply-btn").remove();
    		   		}
    		   		
    		   		// 다른 사람들의 코멘트 만들기
    		   		result.list.map(reply =>  $(".lecture-comment-container").append(makeReplyBody(reply)));
    		   		makeMyComment(result.myComment);
    		   		
    		   		const replyLength = $(".lecture-comment-container").children().length + $(".my-comment-container").children().length ;
    			    if(replyLength == 0){
    				    const info = $('<p>').addClass("text-secondary").text("수강평이 없습니다.");
    			    	$(".lecture-comment-container").append(info);
    			    }
    		   	},
    		}); // end ajax
        	
        }
	</script>
	
<!-- footer -->
<%@ include file="/WEB-INF/views/component/footer.jsp"%>
</body>
</html>