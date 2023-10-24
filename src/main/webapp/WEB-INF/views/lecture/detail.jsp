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
	
<script>
	function addLike() {
		console.log("addLike() 호출");
		const lectureId = $("#lectureId").val();
		const memberId = $("#memberId").val();
		$.ajax({
			type : "POST",
			url : `like/${lectureId}/${memberId}`,
			headers : {
				'Content-Type' : 'application/json'
			},
			success : function(result) {
				$("#btn-like i").removeClass("bi-suit-heart").addClass("bi-suit-heart-fill");
				$("#btn-like i").text(result);
				$("#btn-like").off("click");
				$("#btn-like").click(removeLike);
			}
		}); // end ajax
	}
	function removeLike() {
		console.log("removeLike() 호출");
		const lectureId = $("#lectureId").val();
		const memberId = $("#memberId").val();
		$.ajax({
			type : "DELETE",
			url : `like/${lectureId}/${memberId}`,
			headers : {
				'Content-Type' : 'application/json'
			},
			success : function(result) {
				$("#btn-like i").removeClass("bi-suit-heart-fill").addClass("bi-suit-heart");
				$("#btn-like i").text(result);
				$("#btn-like").off("click");
				$("#btn-like").click(addLike);
			},
		}); // end ajax
	}
	$(function(){
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
	<input id="memberId" type="hidden" value="${memberId}" />

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

						<button class="btn btn-outline-dark flex-shrink-0" type="button">
							<!-- 장바구니추가, 장바구니 삭제, 강의 수강 -->
							<c:choose>
								<c:when test="${purchase }">
									<a class="text-reset link-underline link-underline-opacity-0"
										href="/blooming/lesson/${lectureId }">강의 들으러 가기</a>
								</c:when>
								<c:when test="${not purchase and cart}">
									<i class="bi-cart-fill me-1"></i> 장바구니 바로 가기 
								</c:when>
								<c:when test="${not purchase and not cart}">
									<i class="bi-cart-fill me-1"></i> Add to cart
								</c:when>
							</c:choose>
						</button>
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
		<c:forEach var="reply" items="${replies}">
			<div class="card my-3">
				<div class="card-header">
					<div class="star-rating">
						<c:set var="starRating" value="${reply.lectureReplyScore }" />
						<c:forEach var="i" begin="1" end="5">
							<c:choose>
								<c:when test="${i <= starRating}">
									<i class="bi bi-star-fill"></i>
								</c:when>
								<c:when test="${i - 0.5 <= starRating && starRating < i}">
									<i class="bi bi-star-half"></i>
								</c:when>
								<c:otherwise>
									<i class="bi bi-star"></i>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
					<span class="username">${ reply.authorName}</span>
				</div> <!-- end card header -->
				<div class="card-body">
					<div class="">
						<span class="username">${ reply.lectureReplyContent}</span>
					</div>
				</div>
			</div>
		</c:forEach>
		<div class="input-group">
			<input type="text" class="form-control" placeholder="Type your reply">
			<div class="input-group-append">
				<button class="btn btn-primary" type="button">Send</button>
			</div>
		</div>
	</div>


	<!-- footer -->
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
</body>
</html>