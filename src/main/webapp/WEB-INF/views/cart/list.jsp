<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Insert title here</title>
<style type="text/css">
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	<hr>
	<div class="container px-4 px-lg-5">
		<header class="py-2">
			<div class="d-flex justify-content-between">
				<!-- title -->
				<h4>수강바구니</h4>
			</div>
		</header>
		<section class="pb-5">
			<div class="container">
				<div class="d-flex justify-content-between">
					<!-- title -->

				</div>
				<div class="row w-100">
					<div class="col-lg-12 col-md-12 col-12">
						<table id="shoppingCart"
							class="table table-condensed table-responsive">
							<thead>
								<tr>
									<th style="width: 60%">상품</th>
									<th style="width: 12%">가격</th>
									<th style="width: 16%"></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td data-th="Product">
										<div class="row">
											<div class="col-md-3 text-left">
												<img src="https://via.placeholder.com/250x250/5fa9f8/ffffff"
													alt=""
													class="img-fluid d-none d-md-block rounded mb-2 shadow ">
											</div>
											<div class="col-md-9 text-left mt-sm-2">
												<h4>Product Name</h4>
												<p class="font-weight-light">Brand &amp; Name</p>
											</div>
										</div>
									</td>
									<td class="align-middle" data-th="Price">$49.00</td>
									<td class="actions align-middle" data-th="">
										<div class="text-center">
											<button class="btn btn-lg btn-outline-danger">
												<i style="font-size: 1.5em;" class="bi bi-trash-fill"></i>
											</button>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="float-right text-right">
							<h4>총합:</h4>
							<h1>$99.00</h1>
						</div>
					</div>
				</div>
				<div class="row mt-4 d-flex align-items-center">

					<div class="text-center">
						<a href="" class="btn btn-primary mb-4 btn-lg pl-5 pr-5">결제하기</a>
					</div>
				</div>
			</div>
		</section>
	</div>
	<!-- footer -->
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
</body>
</html>