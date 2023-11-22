<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<!-- Bootstrap css -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
<!-- Bootstrap icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
	$(function() {
		const barCtx = document.getElementById('myChart');
		const lineCtx = document.getElementById('myChart2');
		
		const barLabel = new Array();
		const barData = new Array();
		
		const lineLabel = new Array();
		const lineData = new Array();
		
		<c:forEach items="${month_sales}" var="sales">
			lineLabel.push("${sales.MONTH}월");
			lineData.push("${sales.TOTAL_INCOME}");
		</c:forEach>
		
		<c:forEach items="${lecture_sales}" var="lecture">
			barLabel.push("${lecture.LECTURE_TITLE}");
			barData.push("${lecture.SALES}");
		</c:forEach>
		
		const barChart = new Chart(barCtx, {
			type : 'bar',
			data : {
				labels : barLabel,
				datasets : [ {
					label : '판매량',
					data : barData,
					borderWidth : 1
				} ]
			},
			options : {
				animation: false,
				scales : {
					x: {
						ticks: {
							callback: function(index){
								const title = barLabel[index];
								if(title.length > 5) {
									return title.substr(0, 5) + "..";
								}
								return title;
							}
						}
					},
					y : {
						beginAtZero : true
					}
				}
			}
		});
		
		const lineChart = new Chart(lineCtx, {
			type : 'line',
			data : {
				labels : lineLabel,
				datasets : [ {
					label : '수익',
					data : lineData,
					borderWidth : 1
				} ]
			},
			options : {
				animation: false,
				scales : {
					y : {
						beginAtZero : true
					}
				}
			}
		});
	})
	
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	<div class="container-fluid">
		<div class="row d-flex justify-content-center">
			<main class="col-md-9 col-lg-10 px-md-4">
				<div class="d-flex flex-row">
				  	<div class="chart-container flex-fill" style="position: relative; width: 8em;">
						<h1 class="h2">강좌별 판매량</h1>
						<canvas id="myChart"></canvas>
					</div>
					<div class="chart-container flex-fill" style="position: relative; width: 8em;">
						<h1 class="h2">월 수익</h1>
						<canvas id="myChart2"></canvas>
  					</div>
				</div>
				
				<h2>수강평</h2>
				<div class="table-responsive mb-3">
					<table class="table table-striped table-sm">
						<thead>
							<tr>
								<th scope="col">강좌 번호</th>
								<th scope="col">작성자 닉네임</th>
								<th scope="col">강의 평</th>
								<th scope="col">평점</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="reply" items="${replies }">
								<tr>
									<td>${reply.lectureId }</td>
									<td>${reply.authorNickName }</td>
									<td>${reply.lectureReplyContent }</td>
									<td>${reply.lectureReplyScore }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
				<h2>나의 강의 상태</h2>s
				<div class="table-responsive mb-3">
					<table class="table table-striped table-sm">
						<thead>
							<tr>
								<th scope="col">강의 명</th>
								<th class="text-center" scope="col">현재 상태 </th>
								<th class="text-center" scope="col">비고</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="row" items="${status }">
								<tr>
									<td>${row.LECTURE_TITLE }</td>
									<c:choose>
										<c:when test="${row.PROCCESS_RATE == 1}">
											<td class="text-center align-middle"><span class="fs-5 badge bg-success"> 이상 없음 </span></td>
											<td class="text-center">
												<a class="text-reset link-underline link-underline-opacity-0" href="/blooming/lecture/modify?target=${row.LECTURE_ID }">
													<button class="btn btn-primary m-1">수정</button>
												</a>
												<a class="text-reset link-underline link-underline-opacity-0" href="/blooming/lecture/${row.LECTURE_ID }/course">
													<button class="btn btn-primary m-1">보기</button>
												</a>
											</td>
										</c:when>
										<c:otherwise>
											<td class="text-center align-middle"><span class="fs-5 badge bg-warning text-dark">영상 처리 중 ${row.PROCCESS_RATE * 100} % </span></td>
											<td class="text-center">
												<button class="btn btn-primary disabled m-1">수정</button>
												<button class="btn btn-primary disabled m-1">보기</button>
											</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="row mx-2 mt-4 text-end">
					<a href="/blooming/lecture/upload">
						<button class="btn btn-primary mb-4 btn-lg pl-5 pr-5">강의 올리기</button>
					</a>
				</div>
			</main>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
</body>
</html>