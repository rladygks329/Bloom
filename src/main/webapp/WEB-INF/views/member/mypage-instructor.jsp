<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		new Chart(barCtx, {
			type : 'bar',
			data : {
				labels : [ '1', '2', '3', '4', '5', '6', "7", "8", "9", "10", "11", "12" ],
				datasets : [ {
					label : '# of Votes',
					data : [ 12, 19, 3, 5, 2, 3, 1, 2, 3, 4, 11, 12 ],
					borderWidth : 1
				} ]
			},
			options : {
				responsive : false,
				scales : {
					y : {
						beginAtZero : true
					}
				}
			}
		});
		new Chart(lineCtx, {
			type : 'line',
			data : {
				labels : [ '1', '2', '3', '4', '5', '6', "7", "8", "9", "10", "11", "12" ],
				datasets : [ {
					label : '# of Votes',
					data : [ 12, 19, 3, 5, 2, 3, 1, 2, 3, 4, 11, 12 ],
					borderWidth : 1
				} ]
			},
			options : {
				responsive : false,
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
				<div class="chartjs-size-monitor">
					<div class="chartjs-size-monitor-expand">
						<div class=""></div>
					</div>
					<div class="chartjs-size-monitor-shrink">
						<div class=""></div>
					</div>
				</div>
				<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">강좌별 판매량</h1>
				</div>
				<canvas class="my-4 w-100 chartjs-render-monitor my-3" id="myChart" style="display: block; width: 842px; height: 355px;"></canvas>
				<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">월 수익</h1>
				</div>
				<canvas class="my-4 w-100 chartjs-render-monitor mb-3" id="myChart2" style="display: block; width: 842px; height: 355px;"></canvas>
				<h2>수강평</h2>
				<div class="table-responsive mb-3">
					<table class="table table-striped table-sm">
						<thead>
							<tr>
								<th scope="col">강좌 이름</th>
								<th scope="col">작성자 닉네임</th>
								<th scope="col">강의 평</th>
								<th scope="col">평점</th>
								<th scope="col">작성일</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1,011</td>
								<td>information</td>
								<td>placeholder</td>
								<td>illustrative</td>
								<td>data</td>
							</tr>
							<tr>
								<td>1,012</td>
								<td>text</td>
								<td>placeholder</td>
								<td>layout</td>
								<td>dashboard</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<h2>나의 강의 상태</h2>
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
							<tr>
								<td>1,011</td>
								<td class="text-center align-middle"><span class="fs-5 badge bg-success"> 이상 없음 </span></td>
								<td class="text-center">
									<button class="btn btn-primary">수정</button>
									<button class="btn btn-primary">보기</button>
								</td>
							</tr>
							<tr>
								<td>1,012</td>
								<td class="text-center align-middle"><span class="fs-5 badge bg-warning text-dark">영상 처리 중 4/5</span></td>
								<td class="text-center"><button class="btn btn-primary disabled">수정</button>
									<button class="btn btn-primary disabled">보기</button>
								</td>
							</tr>
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