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
	function addLike() {
		console.log("addLike() 호출");
		const lectureId = $("#lectureId").val();
		const memberId = $("#memberId").val();
		
		if(memberId ===''){
			alert("로그인을 하셔야 이용하실 수 있습니다.");
			return;
		}
		
		$.ajax({
			type : "POST",
			url : `like/${lectureId}/${memberId}`,
			headers : {
				'Content-Type' : 'application/json'
			},
			success : function(result) {
				$("#btn-like i").removeClass("bi-suit-heart").addClass(
						"bi-suit-heart-fill");
				$("#btn-like i").text(" " + result);
				$("#btn-like").off("click");
				$("#btn-like").click(removeLike);
			}
		}); // end ajax
	}
	function removeLike() {
		console.log("removeLike() 호출");
		const lectureId = $("#lectureId").val();
		const memberId = $("#memberId").val();
		
		if(memberId ===''){
			alert("로그인을 하셔야 이용하실 수 있습니다.");
			return;
		}
		$.ajax({
			type : "DELETE",
			url : `like/${lectureId}/${memberId}`,
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
		}); // end ajax
	}
	function addCart(){
		const lectureId = $("#lectureId").val();
		const memberId = $("#memberId").val();
		$.ajax({
			type : "POST",
			url : `/blooming/cart/item/${memberId}/${lectureId}`,
			headers : {
				'Content-Type' : 'application/json'
			},
			success : function(response) {
				console.log("addCart() 성공");
				const addBtn = $("#addCartBtn").html("");
				const link = 
					$("<a>").addClass("text-reset").addClass("link-underline").addClass("link-underline-opacity-0")
					.text(" 장바구니 바로 가기")
					.attr("href", "/blooming/cart");

				addBtn.removeAttr("onclick");
				addBtn.append(link);
			},
		});
	}
	
	// window.onload
	$(function() {
		let like = $("#like").val();
		$("#btn-like").click((like === 'false') ? addLike : removeLike);
	})
</script>
<meta charset="UTF-8">
<title>Bloom - 전체 강의</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	<hr>
	<!-- Product section-->
	<input id="like" type="hidden" value="${like}" />
	<input id="lectureId" type="hidden" value="${lectureId}" />
	<input id="authorId" type="hidden" value="${lecture.memberId }">
	<input id="memberId" type="hidden" value="${memberId}" />
	<input id="purchase" type="hidden" value="${purchase}" />
	<input id="cart" type="hidden" value="${cart}" />

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
					<div class="fs-5 mb-5">
						<fmt:formatNumber type="number" maxFractionDigits="3"
							value="${lecture.lecturePrice }" />
						원
					</div>
					<div class="fs-5 mb-5"></div>
					<p class="lead">${lecture.lectureDescription }</p>
					<div class="d-flex">
						<button id="btn-like"
							class="btn btn-outline-dark flex-shrink-0 me-1" type="button">
							<i class="bi bi-suit-heart${like ? "-fill" : ""} p-1" >
								${lecture.lectureLikeCount } </i>
						</button>


						<!-- 차례로 원작자, 구매자, 장바구니에 있는 사람, 없는 사람 -->
						<c:choose>
							<c:when test="${memberId eq lecture.memberId}">
								<button class="btn btn-outline-dark flex-shrink-0" type="button">
									<a class="text-reset link-underline link-underline-opacity-0"
										href=""> 강의 수정하기 </a>
								</button>
							</c:when>
							<c:when test="${purchase }">
								<button class="btn btn-outline-dark flex-shrink-0" type="button">
									<a class="text-reset link-underline link-underline-opacity-0"
										href="/blooming/lecture/${lectureId }/course">강의 들으러 가기</a>
								</button>
							</c:when>
							<c:when test="${not purchase and cart}">
								<button class="btn btn-outline-dark flex-shrink-0" type="button">
									<a class="text-reset link-underline link-underline-opacity-0"
										href="/blooming/cart"> 장바구니 바로 가기 </a>
								</button>
							</c:when>
							<c:when test="${not purchase and not cart}">
								<button id="addCartBtn" class="btn btn-outline-dark flex-shrink-0" onclick="addCart()" type="button">
									<i class="bi-cart-fill me-1"></i> Add to cart
								</button>
							</c:when>
						</c:choose>

					</div>
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
		<h3 class="display-6 fw-bolder">수강평</h3>
		<div class="lecture-comment-container"></div>

		<!-- 댓글 입력 창 -->
		<c:if test="${not empty memberId }">
			<hr>
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
											id="review-score" type="hidden" name="whatever1"
											class="rating-value" value="5">
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
		var $star_rating = $('.review-rating .bi');
		var SetRatingStar = function(starRating) {
			return $star_rating
					.each(function() {
						if (parseInt($(this).siblings('input.rating-value')
								.val()) >= parseInt($(this).data('rating'))) {
							return $(this).removeClass('bi-star').addClass(
									'bi-star-fill');
						} else {
							return $(this).removeClass('bi-star-fill')
									.addClass('bi-star');
						}
					});
		};

		$star_rating.on('click', function() {
			$(this).siblings('input.rating-value').val($(this).data('rating'));
			return SetRatingStar(this);
		});
		SetRatingStar();
	</script>
	<script src="<%=request.getContextPath()%>/resources/lecture/detail.js"></script>
	<!-- footer -->
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
</body>
</html>