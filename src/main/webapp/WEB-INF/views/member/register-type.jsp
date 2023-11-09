<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입하기</title>
<!-- Bootstrap css -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
<!-- Bootstrap icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<style>
@media ( min-width : 768px) {
	.h-md-100 {
		height: 100vh;
	}
}

.bg-a {
	background-color: #272829;
}

.bg-b {
	background-color: #61677A;
}

.zoom:hover {
	transform: scale(1.05);
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	<div class="d-md-flex h-md-100 align-items-center py-3">
		<!-- First Half -->
		<div class="col-md-6 p-0 bg-a h-100 ">
			<a class="text-decoration-none" href="/blooming/member/register?type=student">
				<div class="text-white d-md-flex align-items-center h-100 text-center justify-content-center zoom">
					<div class="pt-5 pb-5 display-3 fw-bold">
						<i class="bi bi-backpack"></i>
						일반 회원
					</div>
				</div>
			</a>
		</div>

		<!-- Second Half -->
		<div class="col-md-6 p-0 bg-b h-100">
			<a class="text-decoration-none" href="/blooming/member/register?type=instructor">
				<div class="text-white d-md-flex align-items-center h-100 text-center justify-content-center zoom">
					<div class="logoarea pt-5 pb-5 display-3 fw-bold">
						<i class="bi bi-clipboard"></i>
						강사 회원
					</div>
				</div>
			</a>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
</body>
</html>