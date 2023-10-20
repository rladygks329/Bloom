<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<!-- Jquery -->
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<!-- Bootstrap css -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" 
		rel="stylesheet" 
		integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" 
		crossorigin="anonymous"/>
	<!-- Bootstrap icons -->
	<link
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
      rel="stylesheet"/>
	 <!-- Bootstrap core JS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<meta charset="UTF-8">
	<title>레슨 : 등록</title>
	<script>
		function handleFile(file){
			const name = file.name;
			const progressBarContainer = $('<div class="small text-muted mt-2">').text(name);
			const progressBarWrapper = $(`<div class="progress">`);
			const progressBar = $(`<div class="progress-bar progress-bar-striped" role="progressbar" style="width: 10%" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">`).text("0%");
			const button = $(`<button type="button" class="btn btn-primary my-3">전송 시작</button>`);
			const input = $(`<input name="lectureVideos" type="hidden">`);
			
			progressBarWrapper.append(progressBar)
			progressBarContainer.append(progressBarWrapper).append(button).append(input);
			
			button.click(
				function(){
					sendVideoChunks(file, progressBar, function(videoUrl){
						button.remove();
						input.val(videoUrl);
					});
				}
			);
			return progressBarContainer;
		}

		function handleVideoUpload(event) {
			const files = event.target.files;
			const length = files.length;
			
			let result = "";
			let i;
			
			// 초기화
			const uploadedVideos = $(".uploaded-video");
			uploadedVideos.empty();
			
			for(i = 0; i < length; i++){
				const view = handleFile(files[i]); 
				uploadedVideos.append(view);
			}
		}

		$(function(){
			// 가격 에러 메세지
			$('#lecturePrice').blur(function(event){
				const label = $("#lecturePriceLabel");
				const value = event.target.value;
				if(value < 0){
					label.css("color", "red");
					label.text("0 이상의 값을 입력해주세요");
					label.removeAttr("hidden");
				} else{
					label.attr("hidden", true);
				}
			});

			//파일을 끌어다 놓을 때 (drag&drop)
			//브라우저가 파일을 자동으로 열어주는 기능을 막음
			$('.file-drop').on('dragenter dragover', function(event){
				event.preventDefault();
				console.log('drag 테스트');
			});
			
			$('.file-drop').on('drop', function(event){
				event.preventDefault();
				console.log('drop 테스트');
				
				// Ajax를 이용하여 서버로 파일을 업로드
				// multipart/form-data 타입으로 파일을 업로드하는 객체
				var formData = new FormData();
				
				// 드래그한 파일 정보를 갖고 있는 객체
				var files = event.originalEvent.dataTransfer.files;
				
				var i = 0;
				for(i=0; i<files.length; i++) {
					console.log(files[i]);
					formData.append("files", files[i]);
				}
				
				// 수정해야할 부분 : url, attr 부분
				$.ajax({
					type : 'POST',
					url : '/ex05/upload-ajax',
					data : formData,
					contentType: false,
					processData: false,
					success : function(data) {
						$("#lectureThumbnailImg").attr("src", "display?fileName=" + data);
						$("#lectureThumbnailUrl").val("display?fileName=" + data);
					},
				}); //end ajax
			}); //end file drop
		}); //end on-load

	    const sendVideoChunks = (file, progressBar, onSuccess) => {
	        const chunkSize = 1024 * 1024; // 1MB

	  		// total size 계산
	        const totalChunks = Math.ceil(file.size / chunkSize);
	        let currentChunk = 0;

	  		// chunk file 전송
	        const sendNextChunk = () => {
	  			
	  			// chunk size 만큼 데이터 분할
	            const start = currentChunk * chunkSize;
	            const end = Math.min(start + chunkSize, file.size);

	            const chunk = file.slice(start, end);
				
	  			// form data 형식으로 전송
	            const formData = new FormData();
	            formData.append("chunk", chunk, file.name);
	            formData.append("chunkNumber", currentChunk);
	            formData.append("totalChunks", totalChunks);
	            
				$.ajax({
					type : 'POST',
					url : '/blooming/video',
					data : formData,
					contentType: false,
					processData: false,
					statusCode: {
						206 : function() {
							const progressRate = Math.round(currentChunk / totalChunks * 100) + "%";
							progressBar.text(progressRate);
							progressBar.css("width", progressRate);
		                    currentChunk++;
		                    if (currentChunk < totalChunks) {
		                        sendNextChunk();
		                    }
						},
						200 : function(msg) {
							progressBar.text("100%");
							progressBar.css("width", "100%");
							onSuccess(msg);
						}
					},
				});
	        };
	        sendNextChunk();
	    }
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp" %>
	<section class="py-2 bg-secondary">
	  <div class="container">
	    <div class="row d-flex justify-content-center align-items-center h-100">
	    <form action="/blooming/lesson" method="post">
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
	                <input id="lectureTitle" name="lectureTitle" type="text" class="form-control form-control-lg" placeholder="제목을 입력해주세요 " autofocus/>
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
	                <input id="lecturePrice" name="lecturePrice" type="number" min="0" step="1000" class="form-control form-control-lg" placeholder="10000" />
	              </div>
	            </div>
	
	            <hr class="mx-n3">
				
				<!-- Lecture Thumbnail -->
	            <div class="row align-items-center py-3">
	              <div class="col-md-3 ps-5">
	                <h6 class="mb-0">미리보기 이미지</h6>
	              </div>
	              <div class="col-md-9 pe-5">
	              	<img id="lectureThumbnailImg"src="https://placehold.co/600x200" class="img-fluid file-drop" alt="Responsive image">
	              	<input id="lectureThumbnailUrl" name="lectureThumbnailUrl" type="hidden"></div>
	              	<div class="small text-muted mt-2">파일을 끌어다가 올려주세요</div>
	              </div>
	            </div>
	
	            <hr class="mx-n3">
				
				<!-- Lessons -->	
	            <div class="row align-items-center py-3">
	              <div class="col-md-3 ps-5">
	                <h6 class="mb-0">강의 업로드</h6>
					
	              </div>
	              <div class="col-md-9 pe-5">
	                <input class="form-control form-control-lg" type="file" onchange="handleVideoUpload(event)" multiple/>
	                <div class = "uploaded-video"></div>
	                <div class="small text-muted mt-2">강의에 필요한 영상을 올려주세요</div>
	                <div class="small text-danger mt-2 ">동영상 이름이 소제목으로 설정되오니 주의해주세요</div>
	              </div>
	            </div>
	            <hr class="mx-n3">
	            <div class="px-5 py-4">
	              <button type="submit" class="btn btn-primary btn-lg">강좌 만들기</button>
	            </div>
	            
	          </div> <!-- end card -->
	        </div>
	        
	      </div>
	      </form>
	    </div>
	  </div> <!-- end container -->
	</section>
	<%@ include file="/WEB-INF/views/component/footer.jsp" %>
</body>
</html>