<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
	$(function(){
		const ctx = document.getElementById('myChart');
		new Chart(ctx, {
			type : 'bar',
			data : {
				labels : [ 'Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange' ],
				datasets : [ {
					label : '# of Votes',
					data : [ 12, 19, 3, 5, 2, 3 ],
					borderWidth : 1
				} ]
			},
			options : {
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
	<div>
		<canvas id="myChart"></canvas>
	</div>
</body>
</html>