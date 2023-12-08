<%@page import="org.apache.ibatis.javassist.compiler.ast.Keyword"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<!-- Bootstrap css -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
<!-- Bootstrap icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Jquery -->
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<meta charset="UTF-8">
<title>Bloom - 전체 강의</title>
<style>
.card-img-top{
	height: 10rem;
	object-fit: cover;
}
</style>
<script>
	$(function(){
		const orderType = $(".orderType");
		const {origin, pathname} = window.location;
		const url = (new URL(document.location)).searchParams;
		
		orderType.change(function(event){
			const newOrderType = event.target.value;
			url.set("order", newOrderType);
			location.href = origin + pathname + "?" + url.toString();
		})
		
		$(".search").submit(function(e){
			e.preventDefault();
			const keyword = $(".searchType").val() + '.' + $('input[name="keyword"]').val();
			url.set("keyword", keyword);
			url.set("page", "1");
			location.href = origin + pathname + "?" + url.toString();
		})
	})
</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	<hr>

	<div class="container px-4 px-lg-5">
		<header class="py-2">
			<!-- title -->
			<div class="col-md-8 col-sm-8 col-xs-12">
				<c:if test="${not empty keyword }">
					<h4>'${keyword.startsWith("content.") || keyword.startsWith("writer.") ? keyword.substring(keyword.indexOf('.') + 1) : keyword}'의 검색 결과</h4>
				</c:if>
				<c:if test="${ empty keyword }">
					<h4>전체 강의</h4>
				</c:if>
			</div>
			<div class="d-flex justify-content-between my-3">
				<div>
					<select class="form-select orderType">
						<option value="seq" ${((order == "seq") or empty order) ? "selected" : "" }>최신 순</option>
						<option value="price-desc" ${order == "price-desc"? "selected" : "" }>가격 높은 순</option>
						<option value="price-asc" ${order == "price-asc"? "selected" : "" }>가격 낮은 순</option>
						<option value="famous" ${order == "famous"? "selected" : "" }>좋아요 많은 순</option>
						<option value="sales" ${order == "sales"? "selected" : "" }>판매량 많은 순</option>
						<option value="rating" ${order == "rating"? "selected" : "" }>평점 순</option>
					</select>
				</div>
				
				<!-- search bar -->
				<div>
				<form class="input-group search">
					<select class="form-select searchType">
						<option value="content" ${empty keyword or keyword.startsWith("content.") ? "selected" : "" }>제목 + 내용</option>
						<option value="writer" ${not empty keyword and keyword.startsWith("writer.") ? "selected" : "" }>작성자</option>
					</select>
					<input name="keyword" type="search" class="form-control rounded flex-grow-1" placeholder="Search" aria-label="Search" aria-describedby="search-addon" value="${keyword.startsWith("content.") || keyword.startsWith("writer.") ? keyword.substring(keyword.indexOf('.') + 1) : keyword}" />
					<button type="submit" class="btn btn-outline-primary">search</button>
				</form>
				</div>
			</div>

		</header>

		<section>
			<div class="container px-4 px-lg-5 mt-2">
				<div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
					<c:forEach var="lecture" items="${lectureList }">
						<div class="col mb-5">
							<a class="text-reset link-underline link-underline-opacity-0" href="detail?lectureId=${lecture.lectureId}">
								<div class="card h-100">
									<!-- Product image-->

									<img class="card-img-top" src="${lecture.lectureThumbnailUrl }" onerror="this.src='https://dummyimage.com/450x300/dee2e6/6c757d.jpg';" alt="..." />
									<!-- Product details-->
									<div class="card-body p-4">
										<div class="text-left">
											<!-- Product name-->
											<h5 class="fw-bolder">${lecture.lectureTitle }</h5>
											<h6 class="fw-bolder">${lecture.authorName }</h6>

											<!-- Product price-->
											<fmt:formatNumber type="number" maxFractionDigits="3" value="${lecture.lecturePrice }" />
											원

											<!-- Product reviews-->
											<div class="d-flex justify-content-left small text-warning mb-2">
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
					</c:forEach>
				</div>
			</div>
		</section>
		
		<!-- 검색어와 정렬방법을 쿼리 스트링에 추가한다. -->
		<c:set scope="page" var="queryString" value="" />
		<c:if test="${not empty keyword }">
			<c:set scope ="page" var="queryString" value="${queryString }&keyword=${keyword}"/>
		</c:if>
		<c:if test="${not empty order }">
			<c:set scope ="page" var="queryString" value="${queryString }&order=${order}"/>
		</c:if>
		<!-- pageNation -->
		<nav aria-label="페이지 이동">
			<ul class="pagination justify-content-center">
				<li class="page-item  ${pageMaker.hasPrev ? "" : "disabled"}">
					<a class="page-link" href="list?page=${pageMaker.startPageNo - 1 }${queryString}" tabindex="-1">Previous</a>
				</li>
				<c:forEach var="i" begin="${pageMaker.startPageNo}" end="${pageMaker.endPageNo}">
					<li class="page-item ${pageMaker.criteria.page == i ? "active" : " "}">
						<a class="page-link" href="list?page=${i }${queryString}">${i }</a>
					</li>
				</c:forEach>
				<li class="page-item ${pageMaker.hasNext ? "" : "disabled"}">
					<a class="page-link" href="list?page=${pageMaker.endPageNo + 1 }${queryString}" tabindex="-1">Next</a>
				</li>
			</ul>
		</nav>
	</div>

	<!-- footer -->
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
</body>
</html>