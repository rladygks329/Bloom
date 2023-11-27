<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
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
<title>장바구니 내역</title>
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
				<div class="row w-100">
					<div class="col-lg-12 col-md-12 col-12">
						<table id="shoppingCart" class="table table-condensed table-responsive">
							<thead>
								<tr>
									<th style="width: 60%">상품</th>
									<th style="width: 12%">가격</th>
									<th style="width: 16%"></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<div class="float-right text-right">
							<h4>총합:</h4>
							<h1 id="total">0 원</h1>
						</div>
					</div>
				</div>
				<div class="row mt-4 d-flex align-items-center">

					<div class="text-center">
						<form id="purchase" method="post">
							<button type="submit" class="btn btn-outline-warning mb-4 btn-lg pl-5 pr-5">
								<img src="${path}/resources/assets/payment_icon_yellow_medium.png" alt="카카오 결제">
							결제하기</button>
						</form>
					</div>
				</div>
			</div>
		</section>
	</div>
	<!-- footer -->
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
	<script type="text/javascript">
		function updateView(lectureList) {
			console.log("updateView 실행")
			console.log(lectureList);
			let total = 0;
			
			$("table tbody").html("");
			$.each(lectureList, function(index, lecture) {
				console.log(lecture);
				total += lecture.lecturePrice;
				
				let newRow = $("<tr>");
				
				// productCell
				let productCell = $("<td>").attr("data-th","Product");
				let imageCol = $("<div>").addClass("col-md-3 text-left");
				let img = $("<img>")
					.attr("src", lecture.lectureThumbnailUrl)
					.attr("alt", "")
					.addClass("img-fluid d-none d-md-block rounded mb-2 shadow");
				imageCol.append(img);
				let textCol = $("<div>").addClass("col-md-9 text-left mt-sm-2");
				let productName = $("<h4>").text(lecture.lectureTitle);
				let brandName = $("<p>").addClass("font-weight-light").text("blooming");
				textCol.append(productName, brandName);
				productCell.append(imageCol, textCol);
				
				// priceCell
				let priceCell = $("<td>")
					.addClass("align-middle")
					.attr("data-th", "Price")
					.text(lecture.lecturePrice.toLocaleString('ko-KR'));
				
				// actionCell
				let actionsCell = $("<td>").addClass("actions align-middle").attr("data-th","");
				let buttonDiv = $("<div>").addClass("text-center");
				let deleteButton = $("<button>").addClass("btn btn-lg btn-outline-danger");
				let trashIcon = $("<i>").css("font-size", "1.5em").addClass("bi bi-trash-fill");
				
				deleteButton.click(function() {	
					const lectureId = lecture.lectureId;
					removeCartItem(lectureId);
				})
				deleteButton.append(trashIcon);
				buttonDiv.append(deleteButton);
				actionsCell.append(buttonDiv);
				
				newRow.append(productCell, priceCell, actionsCell);
				$("table tbody").append(newRow);
			})
			$("#total").text(total.toLocaleString('ko-KR') + " 원");
		}

		function getCartList() {
			$.ajax({
				type : "GET",
				url : "/blooming/cart/item",
				headers : {
					'Content-Type' : 'application/json'
				},
				success : function(response) {
					console.log("getCartList 성공");
					console.log(response);
					updateView(response)
				},
			});
		}

		function removeCartItem(lectureId) {
			$.ajax({
				type : "DELETE",
				url : "/blooming/cart/item/" + lectureId,
				headers : {
					'Content-Type' : 'application/json'
				},
				success : function(response) {
					console.log("removeCartItem 성공");
					console.log(response);
					getCartList();
				},
			});
		}

		$(function() {
			getCartList();
			
			$("#purchase").submit(function(e){
				e.preventDefault();
				$.ajax({
					type : "POST",
					url : "/blooming/purchase/kakao/ready",
					headers : {
						'Content-Type' : 'application/json'
					},
					success : function(response) {
						window.location.href = response.next_redirect_pc_url; 
					},
				})
			});
		})
	</script>
</body>
</html>