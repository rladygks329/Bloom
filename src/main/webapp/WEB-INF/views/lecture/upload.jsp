<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>
	<head>
		
		<!-- Jquery -->
		<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
		<!-- Jquery UI -->
		<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js" integrity="sha256-lSjKY0/srUM9BE3dPm+c4fBo1dky2v27Gdjm2uoZaL0=" crossorigin="anonymous"></script>
		
		<!-- Bootstrap css -->
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
			integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
			crossorigin="anonymous" />
		<!-- Bootstrap icons -->
		<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
		<!-- Bootstrap core JS-->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
		<meta charset="UTF-8">
		<title>레슨 : 등록</title>

		<script src="https://cdn.jsdelivr.net/npm/tus-js-client@latest/dist/tus.min.js"></script>
		<script type="text/javascript">
		</script>
		<script>
			function validateInputs(event) {
				const title = $('#lectureTitle').val();
				const description = $('#lectureDescription').val();
				const price = $('#lecturePrice').val();
				const thumbnail = $('#lectureThumbnailUrl').val();
				const lectureVideos = $('input[name=lectureVideosURL]');
				const uploadRate = $('.progress-bar');

				if (!title) {
					alert("강좌 제목을 입력해주세요");
					return false;
				}

				if (!description) {
					alert("강좌 설명을 입력해주세요");
					return false;
				}

				if (!price) {
					alert("가격을 입력해주세요");
					return false;
				}

				if (Number(price) < 0) {
					alert("0 이상의 가격을 입력해주세요");
					return false;
				}

				if (!thumbnail) {
					alert("미리보기 이미지를 넣어주세요");
					return false;
				}

				if (lectureVideos.length === 0) {
					alert("강의 영상을 하나라도 업로드 해주세요");
					return false;
				}


				let result = true;
				$.each(uploadRate, function (index, el) {
					const proccessRate = $(this).text();
					if (proccessRate != "100%") {
						alert("모든 강의 영상을 업로드 완료 해주세요");
						return false;
					}
				});

				return true;
			} //end validateInputs()

			function renderFile(file) {
				const fileName = file.name;
				const name = fileName.substring(0, fileName.lastIndexOf("."))
				const extension = fileName.substring(fileName.lastIndexOf(".") + 1)

				const progressBarContainer = $('<div class="uploaded-div small text-muted mt-2">').text(name);
				const progressBarWrapper = $(`<div class="progress">`);
				const progressBar = $(`<div class="progress-bar progress-bar-striped" role="progressbar" style="width: 10%" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">`).text("0%");
				const button = $(`<button type="button" class="btn btn-primary my-3">전송 시작</button>`);
				const inputURL = $(`<input name="lectureVideosURL" type="hidden" required>`);
				const inputTitle = $(`<input name="lectureVideosTitle" type="hidden">`).val(name);

				progressBarWrapper.append(progressBar)
				progressBarContainer.append(progressBarWrapper).append(button).append(inputURL).append(inputTitle);

				button.click(function () {
					const chunkSize = 1024 * 1024 * 5; // 5MB
					let response;

					button.attr("disabled", true);

					// 업로드 객체 생성
					const upload = new tus.Upload(file, {
						endpoint: "/blooming/tus/upload",
						chunkSize,
						retryDelays: [0, 1000, 3000, 5000],
						metadata: {
							filename: file.name,
							filetype: file.type
						},

						// 에러 발생시 콜백
						onError: (error) => {
							console.log("Failed because: " + error);
						},

						// 업로드 진행중 콜백
						onProgress: (bytesUploaded, bytesTotal) => {
							const percentage = ((bytesUploaded / bytesTotal) * 100).toFixed(2);
							progressBar.text(percentage + "%");
							progressBar.css("width", percentage + "%");
						},

						// 업로드 완료 후 콜백
						onSuccess: (response) => {
							console.log("onsuccess")
							console.log(response);
							button.remove();

						},

						// 업로드 중 응답 콜백
						onAfterResponse: (req, res) => {
							console.log("onAfterResponse")
							response = res.getBody();
							if (response) {
								console.log(response);
								inputURL.val(response);
							}
						}
					});
					// 중단된 업로드 조회
					upload.findPreviousUploads().then((previousUploads) => {
						// 중단 된 업로드가 있으면 재개
						if (previousUploads.length) {
							upload.resumeFromPreviousUpload(previousUploads[0]);
						}
						// 업로드 시작
						upload.start();

					}) //end click();
				});
				// 업로드 일시 중지
				// upload.abort();
				return progressBarContainer;
			}

			function handleFileChange(event) {
				const files = event.target.files;
				const length = files.length;

				let result = "";
				let i;

				// 초기화
				const uploadedVideos = $(".uploaded-video");
				uploadedVideos.empty();

				for (i = 0; i < length; i++) {
					const view = renderFile(files[i]);
					uploadedVideos.append(view);
				}
				$('.uploaded-video').sortable({
					items: $('.uploaded-div')
				});
			}

			$(function () {
				// 가격 에러 메세지
				$('#lecturePrice').blur(function (event) {
					const label = $("#lecturePriceLabel");
					const value = event.target.value;
					if (value < 0) {
						label.css("color", "red");
						label.text("0 이상의 값을 입력해주세요");
						label.removeAttr("hidden");
					} else {
						label.attr("hidden", true);
					}
				});

				//파일을 끌어다 놓을 때 (drag&drop)
				//브라우저가 파일을 자동으로 열어주는 기능을 막음
				$('.file-drop').on('dragenter dragover', function (event) {
					event.preventDefault();
					console.log('drag 테스트');
				});

				$('.file-drop').on('drop', function (event) {
					event.preventDefault();
					console.log('drop 테스트');

					// Ajax를 이용하여 서버로 파일을 업로드
					// multipart/form-data 타입으로 파일을 업로드하는 객체
					var formData = new FormData();

					// 드래그한 파일 정보를 갖고 있는 객체
					var files = event.originalEvent.dataTransfer.files;

					var i = 0;
					for (i = 0; i < files.length; i++) {
						console.log(files[i]);
						formData.append("files", files[i]);
					}

					// 수정해야할 부분 : url, attr 부분
					$.ajax({
						type: 'POST',
						url: '/ex05/upload-ajax',
						data: formData,
						contentType: false,
						processData: false,
						success: function (data) {
							console.log("성공 data: " + data);
							$("#lectureThumbnailImg").attr("src", "display?fileName=" + data);
							$("#lectureThumbnailUrl").val("display?fileName=" + data);
						},
					}); //end ajax
				}); //end file drop
			}); //end on-load
		</script>
	</head>

	<body>
		<%@ include file="/WEB-INF/views/component/navigation.jsp" %>
			<section class="py-2 bg-secondary">
				<div class="container">
					<div class="row d-flex justify-content-center align-items-center h-100">
						<form action="/blooming/lecture/upload" method="post" onsubmit="return validateInputs(event)">
							<input type="hidden" id="memberId" name="memberId" value="${memberId}" />
							<div class="col-xl-9">

								<h1 class="text-white my-2">강좌 등록</h1>

								<div class="card" style="border-radius: 15px;">
									<div class="card-body">

										<!-- Lecture Title -->
										<div class="row align-items-center pt-4 pb-3">
											<div class="col-md-3 ps-5">
												<h6 class="mb-0">강좌 제목</h6>
											</div>
											<div class="col-md-9 pe-5">
												<input id="lectureTitle" name="lectureTitle" type="text"
													class="form-control form-control-lg" placeholder="제목을 입력해주세요 "
													autofocus />
											</div>
										</div>

										<!-- Lecture Description -->
										<div class="row align-items-center pt-4 pb-3">
											<div class="col-md-3 ps-5">
												<h6 class="mb-0">강좌 설명</h6>
											</div>
											<div class="col-md-9 pe-5">
												<textarea id="lectureDescription" name="lectureDescription" type="text"
													class="form-control" placeholder="강좌 설명을 입력해주세요" cols="20"
													rows="3"></textarea>
											</div>
										</div>

										<hr class="mx-n3">

										<!-- Lecture Price -->
										<div class="row align-items-center py-3">
											<div class="col-md-3 ps-5">

												<h6 class="mb-0">가격</h6>
											</div>
											<div class="col-md-9 pe-5">
												<div id="lecturePriceLabel" class="small text-muted mt-2" hidden></div>
												<input id="lecturePrice" name="lecturePrice" type="number" min="0"
													step="1000" class="form-control form-control-lg"
													placeholder="10000" />
											</div>
										</div>

										<hr class="mx-n3">

										<!-- Lecture Thumbnail -->
										<div class="row align-items-center py-3">
											<div class="col-md-3 ps-5">
												<h6 class="mb-0">미리보기 이미지</h6>
											</div>
											<div class="col-md-9 pe-5">
												<img id="lectureThumbnailImg" src="https://placehold.co/600x200"
													class="img-fluid file-drop" alt="Responsive image"> <input
													id="lectureThumbnailUrl" name="lectureThumbnailUrl" type="hidden"
													required>
												<div class="small text-muted mt-2">파일을 끌어다가 올려주세요</div>
											</div>
										</div>
									</div>

									<hr class="mx-n3">

									<!-- Lessons -->
									<div class="row align-items-center py-3">
										<div class="col-md-3 ps-5">
											<h6 class="mb-0">강의 업로드</h6>

										</div>
										<div class="col-md-9 pe-5">
											<input class="form-control form-control-lg" type="file"
												onchange="handleFileChange(event)" multiple />
											<div class="uploaded-video"></div>
											<div class="small text-muted mt-2">강의에 필요한 영상을 올려주세요</div>
											<div class="small text-muted mt-2 ">드래그하여 순서를 변경하세요</div>
											<div class="small text-danger mt-2 ">동영상 이름이 소제목으로 설정되오니
												주의해주세요</div>
										</div>
									</div>
									<hr class="mx-n3">
									<div class="px-5 py-4">
										<button type="submit" class="btn btn-primary btn-lg">강좌
											만들기</button>
									</div>

								</div>
								<!-- end card -->
							</div>
					</div>
					</form>
				</div>
				</div>
				<!-- end container -->
			</section>
			<%@ include file="/WEB-INF/views/component/footer.jsp" %>
	</body>
</html>