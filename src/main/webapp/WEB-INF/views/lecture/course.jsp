<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강좌 보기</title>
<!-- Jquery -->
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
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
<!-- Video.js -->
<link href="https://vjs.zencdn.net/7.14.3/video-js.css" rel="stylesheet" />
<script src="https://vjs.zencdn.net/7.14.3/video.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/silvermine-videojs-quality-selector@1.1.2/dist/js/silvermine-videojs-quality-selector.min.js"></script>
<link 	href="https://cdn.jsdelivr.net/npm/silvermine-videojs-quality-selector@1.1.2/dist/css/quality-selector.min.css" rel="stylesheet" />

<script type="text/javascript">
	$(function() {
		$("#submenu li a:first").addClass("active");
		const headURL = $('input[name="head"]').val();
		
		function addQualitySelector() {
			const player = this;
			player.controlBar.addChild('QualitySelector');
		}
		
		const vPlayer = videojs('my-video', {
			techOrder : [ "html5", "youtube" ],
			fill : true,
			autoplay : true,
			sources : [{
				type : "application/x-mpegURL",
				src : '/blooming/hls/' + headURL + '/480/playlist.m3u8',
				label: '480p'
			},{
				type : "application/x-mpegURL",
				src : '/blooming/hls/' + headURL + '/720/playlist.m3u8',
				label: '720p'
			},{
				type : "application/x-mpegURL",
				src : '/blooming/hls/' + headURL + '/1080/playlist.m3u8',
				label: '1080p'
			},{
				type : "application/x-mpegURL",
				src : '/blooming/hls/' + headURL + '/master.m3u8',
				label: 'auto',
				selected: true
			}]
		}, addQualitySelector);
		vPlayer.play();
		
		const controlBar = $(".vjs-control-bar");
		vPlayer.on('mouseout', function() {
			vPlayer.controlBar.hide();
		});

		vPlayer.on('mouseover', function() {
			vPlayer.controlBar.show();
		});

		$("#submenu li").click(function() {
			const actived = $("#submenu li").find(".active").removeClass("active");
			$(this).find("a").addClass("active");
			const url = $(this).find("input").val();
			console.log("url : " + url);
			const filename = url.split(".")[0];
			const extention = url.split(".")[1];

			const newSrc = [{
				type : "application/x-mpegURL",
				src : '/blooming/hls/' + filename + '/480/playlist.m3u8',
				label: '480p'
			},{
				type : "application/x-mpegURL",
				src : '/blooming/hls/' + filename + '/720/playlist.m3u8',
				label: '720p'
			},{
				type : "application/x-mpegURL",
				src : '/blooming/hls/' + filename + '/1080/playlist.m3u8',
				label: '1080p'
			},{
				type : "application/x-mpegURL",
				src : '/blooming/hls/' + filename + '/master.m3u8',
				label: 'auto',
				selected: true
			}]
			vPlayer.src(newSrc);
			vPlayer.play();
		});
	})
</script>
<link href="${path}/resources/lecture/course.css" rel="stylesheet" />
</head>
<body>
	<input type="hidden" name="head" value="${headURL }">
	<div class="container-fluid">
		<div class="row flex-nowrap">
			<div id="trailer"
				class="col d-flex flex-column justify-content-center embed-responsive embed-responsive-16by9 p-0 ">
				<nav class="text-white bg-black" aria-label="breadcrumb">
					<ol class="breadcrumb breadcrumb-custom m-0">
						<li class="breadcrumb-item"><a class="h4" href="#" data-abc="true">강의
								대쉬 보드</a></li>
						<li class="breadcrumb-item active" aria-current="page"><span class="h4">${head.lessonName }</span></li>
					</ol>
				</nav>
				<video id="my-video" class="video-js vjs-fill vjs-big-play-centered" controls>
				</video>
			</div>

			<div id="wrapper"
				class="col-auto col-md-3 col-xl-2 px-sm-2 px-0 bg-dark toggled">
				<div
					class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">
					<a href="#" class="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-white text-decoration-none">
						<span class="fs-5 d-none d-sm-inline">Menu</span>
					</a>
					<ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start text-white w-100" id="menu">
						<li class="nav-item ">
							<a href="/blooming/main" class="nav-link align-middle px-0 text-white text-decoration-none">
									<i class="fs-4 bi-house"></i> <span
									class="ms-1 d-none d-sm-inline">홈</span>
							</a>
						</li>
						<li class="nav-item w-100">
							<a href="#submenu1" data-bs-toggle="collapse" class="nav-link px-0 align-middle text-white text-decoration-none">
								<i class="fs-4 bi-speedometer2"></i> 
								<span class="ms-1 d-none d-sm-inline">강의 목록</span>
							</a>
							<ul class="nav flex-column ms-1" id="submenu" data-bs-parent="#menu">
								<c:forEach var="lesson" items="${lessons}">
									<li>
										<a class="nav-link my-1">
											<i class="bi bi-dot px-0 d-none d-sm-inline">${lesson.lessonName }</i>
											<input type="hidden" value="${lesson.lessonUrl }">
										</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>