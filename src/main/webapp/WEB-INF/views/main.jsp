<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<!-- Jquery -->
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<!-- Bootstrap css -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
<!-- Bootstrap icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<meta charset="UTF-8">
<title>Bloom</title>
</head>
<style>
.carousel-inner {
	height: 450px;
}
@media ( max-width : 767px) {
	.carousel-inner {
		height: auto;
	}
	.carousel-inner .carousel-item>div {
		display: none;
	}
	.carousel-inner .carousel-item>div:first-child {
		display: block;
	}
}

.carousel-inner .carousel-item.active, .carousel-inner .carousel-item-next,
	.carousel-inner .carousel-item-prev {
	display: flex;
	gap: 1em;
}

/* medium and up screens */
@media ( min-width : 768px) {
	.carousel-inner .carousel-item-end.active, .carousel-inner .carousel-item-next{
		transform: translateX(20%);
	}
	.carousel-inner .carousel-item-start.active, .carousel-inner .carousel-item-prev{
		transform: translateX(-20%);
	}
}

.carousel-inner .carousel-item-end, .carousel-inner .carousel-item-start{
	transform: translateX(0);
}
.card-img-top{
	height: 10rem;
	object-fit: cover;
}
</style>
<script>
$(function(){
	const minPerSlide = 5 // if maxPerSlide = 3 / the transform is 100/3 => 33
	
	$("#HotLikesCarousel .carousel-item:first").addClass("active");
	$("#HotSalesCarousel .carousel-item:first").addClass("active");
	
	$(".slide").each(function(index, slide){
		let items = $(slide).find(".carousel-item");
		items.each(function(index, item){
		    let next = item.nextElementSibling
		    for (var i = 1; i < minPerSlide; i++) {
		        if (!next) {
		        	next = items[0] 
		        } // wrap carousel by using first child
		        const cloneChild = next.cloneNode(true)
		        item.appendChild(cloneChild.children[0])
		        next = next.nextElementSibling
		    }
		});
	})
})

</script>
<body>
	<!-- nav -->
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	<header class="bg-dark py-5">
		<div class="container px-4 px-lg-5 my-5">
			<div class="text-center text-white">
				<h1 class="display-4 fw-bolder">Bloom</h1>
				<p class="lead fw-normal text-white-50 mb-0">bloom your talent</p>
			</div>
		</div>
	</header>
	
	<!-- section -->
	<section>
		<div class="container-fluid px-4 mt-2 text-center">
			<h2 class="font-weight-light text-start">Hot Likes</h2>
			<div class="mx-auto my-auto justify-content-center">
				<div id="HotLikesCarousel" class="carousel slide" data-bs-ride="carousel">
					<div class="carousel-inner" role="listbox">
						<c:forEach var="lecture" items="${list_hot_like }">
							<div class="carousel-item">
								<div class="col mb-5">
									<a class="text-reset link-underline link-underline-opacity-0" href="/blooming/lecture/detail?lectureId=${lecture.lectureId}">
										<div class="card h-100">
											<!-- Product image-->
											<img class="card-img-top d-block w-100" src="${lecture.lectureThumbnailUrl }" onerror="this.src='https://dummyimage.com/450x300/dee2e6/6c757d.jpg';" alt="..." />
											<!-- Product details-->
											<div class="card-body pt-4 px-4 pb-2">
												<div class="text-left">
													<!-- Product name-->
													<h5 class="fw-bolder">${lecture.lectureTitle }</h5>
													<h6 class="fw-bolder">${lecture.authorName }</h6>

													<!-- Product price-->
													<fmt:formatNumber type="number" maxFractionDigits="3" value="${lecture.lecturePrice }" />
													원

													<!-- Product reviews-->
													<div class="d-flex justify-content-center small text-warning mb-2">
														<c:if test="${lecture.lectureReplyCount == 0}">
															<i class="bi bi-star"></i>
															<i class="bi bi-star"></i>
															<i class="bi bi-star"></i>
															<i class="bi bi-star"></i>
															<i class="bi bi-star"></i>
															<p class="fs-6">(0)</p>
														</c:if>
														<c:if test="${lecture.lectureReplyCount != 0}">
															<c:set var="starRating" value="${lecture.lectureTotalScore / lecture.lectureReplyCount }" />
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
															<p class="fs-6">(${lecture.lectureReplyCount})</p>
														</c:if>
													</div>
												</div>
											</div>
										</div>
									</a>
								</div>
							</div>
						</c:forEach>
					</div>
					<a class="carousel-control-prev bg-transparent w-aut" href="#HotLikesCarousel" role="button" data-bs-slide="prev"> 
						<span class="carousel-control-prev-icon" aria-hidden="true"></span>
					</a> 
					<a class="carousel-control-next bg-transparent w-aut" href="#HotLikesCarousel" role="button" data-bs-slide="next"> 
						<span class="carousel-control-next-icon" aria-hidden="true"></span>
					</a>
				</div>
			</div>
			<h2 class="font-weight-light text-start">Hot sales</h2>
			<div class="mx-auto my-auto justify-content-center">
				<div id="HotSalesCarousel" class="carousel slide" data-bs-ride="carousel">
					<div class="carousel-inner" role="listbox">
						<c:forEach var="lecture" items="${list_hot_sale }">
							<div class="carousel-item">
								<div class="col mb-5">
									<a class="text-reset link-underline link-underline-opacity-0" href="/blooming/lecture/detail?lectureId=${lecture.lectureId}">
										<div class="card h-100">
											<!-- Product image-->
											<img class="card-img-top d-block w-100" src="${lecture.lectureThumbnailUrl }" onerror="this.src='https://dummyimage.com/450x300/dee2e6/6c757d.jpg';" alt="..." />
											<!-- Product details-->
											<div class="card-body pt-4 px-4 pb-2">
												<div class="text-left">
													<!-- Product name-->
													<h5 class="fw-bolder">${lecture.lectureTitle }</h5>
													<h6 class="fw-bolder">${lecture.authorName }</h6>

													<!-- Product price-->
													<fmt:formatNumber type="number" maxFractionDigits="3" value="${lecture.lecturePrice }" />
													원

													<!-- Product reviews-->
													<div class="d-flex justify-content-center small text-warning mb-2">
														<c:if test="${lecture.lectureReplyCount == 0}">
															<i class="bi bi-star"></i>
															<i class="bi bi-star"></i>
															<i class="bi bi-star"></i>
															<i class="bi bi-star"></i>
															<i class="bi bi-star"></i>
															<p class="fs-6">(0)</p>
														</c:if>
														<c:if test="${lecture.lectureReplyCount != 0}">
															<c:set var="starRating" value="${lecture.lectureTotalScore / lecture.lectureReplyCount }" />
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
															<p class="fs-6">(${lecture.lectureReplyCount})</p>
														</c:if>
													</div>
												</div>
											</div>
										</div>
									</a>
								</div>
							</div>
						</c:forEach>
					</div>
					<a class="carousel-control-prev bg-transparent w-aut" href="#HotSalesCarousel" role="button" data-bs-slide="prev"> 
						<span class="carousel-control-prev-icon" aria-hidden="true"></span>
					</a> 
					<a class="carousel-control-next bg-transparent w-aut" href="#HotSalesCarousel" role="button" data-bs-slide="next"> 
						<span class="carousel-control-next-icon" aria-hidden="true"></span>
					</a>
				</div>
			</div>
		</div>
	</section>
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
	
	<input type="hidden" id="result" value="${result}">
	<input type="hidden" id="authorEmail" value="${authorEmail ? 'true' : 'false'}">
	
	<script>
        window.onload = function() {
            var result = document.getElementById('result').value;
            if (result === '1') {
                alert("회원가입을 축하합니다!");
            }
        };    
    </script>	
	
</body>
</html>