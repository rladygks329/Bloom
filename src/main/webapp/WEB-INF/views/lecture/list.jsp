<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
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
<meta charset="UTF-8">
<title>Bloom - 전체 강의</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	<hr>

	<div class="container px-4 px-lg-5">
		<header class="py-2">
			<div class="d-flex justify-content-between">
				<!-- title -->
				<div class="col-md-8 col-sm-8 col-xs-12">
					<c:if test="${not empty keyword }">
						<h4>'${keyword }'의 검색 결과</h4>
					</c:if>
					<c:if test="${ empty keyword }">
						<h4>전체 강의</h4>
					</c:if>

				</div>
				<!-- search bar -->
				<form class="input-group">
					<input name="keyword" type="search" class="form-control rounded"
						placeholder="Search" aria-label="Search"
						aria-describedby="search-addon" value="${keyword}" />
					<button type="submit" class="btn btn-outline-primary">search</button>
				</form>
			</div>
		</header>

		<section>
			<div class="container px-4 px-lg-5 mt-2">
				<div
					class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
					<c:forEach var="lecture" items="${lectureList }">
						<div class="col mb-5">
							<a class="text-reset link-underline link-underline-opacity-0"
								href="detail?lectureId=${lecture.lectureId}">
								<div class="card h-100">
									<!-- Product image-->

									<img class="card-img-top"
										src="${lecture.lectureThumbnailUrl }" 
										onerror="this.src='https://dummyimage.com/450x300/dee2e6/6c757d.jpg';"
										alt="..." />
									<!-- Product details-->
									<div class="card-body p-4">
										<div class="text-left">
											<!-- Product name-->
											<h5 class="fw-bolder">${lecture.lectureTitle }</h5>
											<h6 class="fw-bolder">${lecture.authorName }</h6>

											<!-- Product price-->
											<fmt:formatNumber type="number" maxFractionDigits="3"
												value="${lecture.lecturePrice }" />
											원

											<!-- Product reviews-->
											<div
												class="d-flex justify-content-left small text-warning mb-2">
												<c:if test="${lecture.lectureReplyCount == 0}">
													<i class="bi bi-star"></i>
													<i class="bi bi-star"></i>
													<i class="bi bi-star"></i>
													<i class="bi bi-star"></i>
													<i class="bi bi-star"></i>
													<p class="fs-6">(0)</p>
												</c:if>
												<c:if test="${lecture.lectureReplyCount != 0}">
													<c:set var="starRating"
														value="${lecture.lectureTotalScore / lecture.lectureReplyCount }" />
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
					</c:forEach>
				</div>
			</div>
		</section>

		<!-- pageNation -->
		<nav aria-label="페이지 이동">
			<ul class="pagination justify-content-center">
				<c:if test="${not empty keyword}">
					<li class="page-item  ${pageMaker.hasPrev ? "" : "disabled"}">
						<a class="page-link"
						href="list?page=${pageMaker.startPageNo - 1 }&keyword=${keyword}"
						tabindex="-1">Previous</a>
					</li>
					<c:forEach var="i" begin="${pageMaker.startPageNo}"
						end="${pageMaker.endPageNo}">
						<li class="page-item ${pageMaker.criteria.page == i ? "active" : " "}">
							<a class="page-link" href="list?page=${i }&keyword=${keyword}">${i }</a>
						</li>
					</c:forEach>
					<li class="page-item ${pageMaker.hasNext ? "" : "disabled"}">
						<a class="page-link"
						href="list?page=${pageMaker.endPageNo + 1 }&keyword=${keyword}"
						tabindex="-1">Next</a>
					</li>
				</c:if>
				<c:if test="${empty keyword}">
					<li class="page-item  ${pageMaker.hasPrev ? "" : "disabled"}">
						<a class="page-link"
						href="list?page=${pageMaker.startPageNo - 1 }" tabindex="-1">Previous</a>
					</li>
					<c:forEach var="i" begin="${pageMaker.startPageNo}"
						end="${pageMaker.endPageNo}">
						<li class="page-item ${pageMaker.criteria.page == i ? "active" : " "}">
							<a class="page-link" href="list?page=${i }">${i }</a>
						</li>
					</c:forEach>
					<li class="page-item ${pageMaker.hasNext ? "" : "disabled"}">
						<a class="page-link" href="list?page=${pageMaker.endPageNo + 1 }"
						tabindex="-1">Next</a>
					</li>
				</c:if>
			</ul>
		</nav>
	</div>

	<!-- footer -->
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
</body>
</html>