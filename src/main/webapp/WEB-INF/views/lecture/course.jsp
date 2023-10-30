<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Jquery -->
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<!-- Bootstrap css -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
<!-- Bootstrap icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
	<div class="container-fluid">
		<div class="row flex-nowrap">
		
			<div id="trailer" class="col d-flex justify-content-center embed-responsive embed-responsive-16by9 p-0">
				<video class="embed-responsive-item" controls autoplay loop muted>
					<source src="https://www.youtube.com/embed/zpOULjyy-n8?rel=0" type="video/mp4">
					Your browser does not support the video tag.
				</video>
			</div>

			<div id="wrapper" class="col-auto col-md-3 col-xl-2 px-sm-2 px-0 bg-dark toggled">
				<div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">
					<a href="/" class="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-white text-decoration-none"> 
						<span class="fs-5 d-none d-sm-inline">Menu</span>
					</a>
					<ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start text-white" id="menu">
						<li class="nav-item ">
							<a href="#" class="nav-link align-middle px-0 text-white text-decoration-none">
							  <i class="fs-4 bi-house"></i> 
							  <span class="ms-1 d-none d-sm-inline">Home</span>
							</a>
						</li>
						<li>
							<a href="#submenu1" data-bs-toggle="collapse" class="nav-link px-0 align-middle text-white text-decoration-none"> 
								<i class="fs-4 bi-speedometer2"></i> 
								<span class="ms-1 d-none d-sm-inline">Dashboard</span>
							</a>
							<ul class="collapse show nav flex-column ms-1" id="submenu1" data-bs-parent="#menu">
								<li class="w-100">
									<a href="#" class="nav-link px-0 text-white text-decoration-none"> 
										<span class="d-none d-sm-inline ">Item</span> 1
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>