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
		
		<!-- Tus protocol -->
		<script src="https://cdn.jsdelivr.net/npm/tus-js-client@latest/dist/tus.min.js"></script>
		
		<meta charset="UTF-8">
		<title>강좌 변경</title>
		<script>
			function validateInputs(event) {
				const title = $('#lectureTitle').val();
				const description = $('#lectureDescription').val();
				const price = $('#lecturePrice').val();
				const thumbnail = $('#lectureThumbnailUrl').val();
				const lectureVideos = $('.uploaded-div');
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
					if (proccessRate != "100.00%") {
						alert("모든 강의 영상을 업로드 완료 해주세요");
						return false;
					}
				});
				
				// diabled한 input은 넘어가지 않으므로 풀어준다.
				$("input[name='lessonName']").each(function(i) {
			        $(this).removeAttr('disabled');
				});
				return true;
			} //end validateInputs()

			function renderFile(file) {
				const fileName = file.name;
				const name = fileName.substring(0, fileName.lastIndexOf("."));
				const extension = fileName.substring(fileName.lastIndexOf(".") + 1);
				const index = Number($('input[name="lessonIndex"]').last().val());
				
				if(extension != "mp4"){
					alert("mp4 파일을 올려주세요");
					return;
				}
				const wrapper = $('<div class="uploaded-div small text-muted mt-2">');
				
				const inputGroup = $('<div class="input-group my-1 py-1">');
				const moveIcon = $('<span class="input-group-text">').append('<i class="bi bi-arrows-move">');
				const inputName = $('<input class="form-control" name="lessonName" value="'+ name +'" disabled="disabled" required />');
				const inputURL = $('<input name="lesssonUrl" type="hidden">');
				const inputId = $('<input name="lessonId" type="hidden">').val(0);
				const inputIndex = $('<input name="lessonIndex" type="hidden">').val(index + 1024);
				const editBtn = $('<button type="button" class="btn btn-primary btn-edit">').text("수정");
				const deleteBtn = $('<button type="button" class="btn btn-danger btn-delete">').text("삭제");
				
				const progressBarWrapper = $(`<div class="progress">`);
				const progressBar = $(`<div class="progress-bar progress-bar-striped" role="progressbar" style="width: 10%" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">`).text("0%");
				const button = $(`<button type="button" class="btn btn-primary my-3">전송 시작</button>`);
				
				inputGroup.append(moveIcon)
					.append(inputName)
					.append(inputURL)
					.append(inputId)
					.append(inputIndex)
					.append(editBtn)
					.append(deleteBtn);
				
				progressBarWrapper
					.append(progressBar);
					
				wrapper
					.append(inputGroup)
					.append(progressBarWrapper)
					.append(button)
					

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
						onSuccess: () => {
							console.log("onsuccess")
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
				return wrapper;
			}

			function draggable(){
				$('.uploaded-video').sortable({
					items: $('.uploaded-div'),
					update: function( event, ui ) {
						const item = ui.item;
						const index = $(item).index();
						const length = $(item).parent().children().length;
						const input = $(item).find('input[name="lessonIndex"]');
						
						let value = 0;
						if(index == 0){
							value = Number($(item).siblings('.uploaded-div:first').find('input[name=lessonIndex]').val())/2;
						}else if(index == length - 1){
							value = Number($(item).siblings('.uploaded-div:last').find('input[name=lessonIndex]').val()) + 1024;
						}else{
							const prev = Number($(item).prev().find('input[name=lessonIndex]').val())
							const next = Number($(item).next().find('input[name=lessonIndex]').val())
							value = (prev + next)/2
						}
						input.val(value);
					}
				});
			} //end draggable
			
			function handleFileChange(event) {
				const container = $(".uploaded-video");
				// 파일들을 순회하면서 뷰 추가하기
				[...event.target.files].forEach(file => container.append(renderFile(file)));
				draggable();
			}
			
			function handleEdit(btnEdit){
				const input = $(btnEdit).siblings("input");
				const condition = input.prop("disabled");
				$(btnEdit).text(condition ? "완료" : "수정");
				input.prop("disabled", !condition);
			}
			
			function handleDelete(btnDelete){
				const row = $(btnDelete).closest(".uploaded-div").remove();
			}
			
			$(function () {
				draggable();	
				$(".uploaded-video").click(function(event){
					const target = event.target;
					if($(target).hasClass("btn-edit")){
						handleEdit(target);
					}else if($(target).hasClass("btn-delete")){
						handleDelete(target);
					}
				})
				
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
						<form action="/blooming/lecture/modify" method="post" onsubmit="return validateInputs(event)">
							<div class="col">
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
													class="form-control form-control-lg" placeholder="제목을 입력해주세요 " value="${lecture.lectureTitle }" autofocus />
											</div>
										</div>

										<!-- Lecture Description -->
										<div class="row align-items-center pt-4 pb-3">
											<div class="col-md-3 ps-5">
												<h6 class="mb-0">강좌 설명</h6>
											</div>
											<div class="col-md-9 pe-5">
												<textarea id="lectureDescription" name="lectureDescription" type="text" class="form-control" 
												placeholder="강좌 설명을 입력해주세요" cols="20" rows="3">${lecture.lectureDescription }</textarea>
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
													placeholder="10000" value="${lecture.lecturePrice }"/>
											</div>
										</div>

										<hr class="mx-n3">

										<!-- Lecture Thumbnail -->
										<div class="row align-items-center py-3">
											<div class="col-md-3 ps-5">
												<h6 class="mb-0">미리보기 이미지</h6>
											</div>
											<div class="col-md-9 pe-5">
												<img id="lectureThumbnailImg" src="${lecture.lectureThumbnailUrl }"
													class="img-fluid file-drop" alt="Responsive image"> 
													<input id="lectureThumbnailUrl" name="lectureThumbnailUrl" type="hidden" value="${lecture.lectureThumbnailUrl }" required>
												<div class="small text-muted mt-2">파일을 끌어다가 올려주세요</div>
											</div>
										</div>
									</div>

									<hr class="mx-n3">

									<!-- Lessons -->
									<div class="row align-items-center py-3">
										<div class="col-md-3 ps-5">
											<h6 class="mb-0">강의 추가</h6>
										</div>
										<div class="col-md-9 pe-5">
											<input class="form-control form-control-lg" type="file" placeholder="파일 추가하기"
												onchange="handleFileChange(event)" multiple="multiple" />
											<hr>
											<div class="uploaded-video">
												<c:forEach var="lesson" items="${lessons }">
													<div class="uploaded-div small text-muted mt-2">
														<div class="input-group my-1 p-1">
															<span class="input-group-text"> <i class="bi bi-arrows-move"></i></span>
															<input class="form-control" name="lessonName" value="${lesson.lessonName }" disabled="disabled" required/>
															<input name="lessonUrl" type="hidden" value="${lesson.lessonUrl}"/>
															<input name="lessonId" type="hidden" value="${lesson.lessonId}">
															<input name="lessonIndex" type="hidden" value="${lesson.lessonIndex}">
															<button type="button" class="btn btn-primary btn-edit"> 수정 </button>
															<button type="button" class="btn btn-danger btn-delete"> 삭제 </button>
														</div>
														<div class="progress">
															<div class="progress-bar progress-bar-striped" role="progressbar" style="width: 100%" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">100.00%</div>
														</div>
													</div>
												</c:forEach>
											</div>
											<div class="small text-muted mt-2">강의에 필요한 영상을 올려주세요</div>
											<div class="small text-muted mt-2 ">드래그하여 순서를 변경하세요</div>
											<div class="small text-danger mt-2 ">동영상 이름이 소제목으로 설정되오니 주의해주세요</div>
										</div>
									</div>
									<hr class="mx-n3">
									<div class="px-5 py-4">
										<button type="submit" class="btn btn-primary btn-lg">강좌 수정하기</button>
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