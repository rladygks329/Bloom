<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>chunk file upload with tus upload</title>
</head>
<body>

<input id="video-file" type="file" name="file">
<button id="upload-btn">업로드</button>
<button id="pause-btn">일시중지</button>
<div id="result"></div>

</body>

<script src="https://cdn.jsdelivr.net/npm/tus-js-client@latest/dist/tus.min.js"></script>
<script type="text/javascript">
  const uploadBtn = document.querySelector('#upload-btn')

  uploadBtn.addEventListener('click', () => {
    const pauseBtn = document.querySelector("#pause-btn");
    const file = document.querySelector("#video-file").files[0];
    const result = document.querySelector("#result");
  
    const chunkSize = 1024 * 1024 * 5; // 5MB

    let response;

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
        const percentage = ((bytesUploaded / bytesTotal) * 100).toFixed(2)
        result.textContent = percentage + "%"
      },

      // 업로드 완료 후 콜백
      onSuccess: () => {
        result.innerHTML = `
          <div>업로드 완료!</div>
          <div>${response}</div>
        `;
      },

  	  // 업로드 중 응답 콜백
      onAfterResponse: (req, res) => {
        response = res.getBody();
      }
    });

    // 중단된 업로드 조회
    upload.findPreviousUploads().then( (previousUploads) => {
      // 중단 된 업로드가 있으면 재개
      if(previousUploads.length) {
        upload.resumeFromPreviousUpload(previousUploads[0]);
      }

      // 업로드 시작
      upload.start();
    });

    // 업로드 일시 중지
    pauseBtn.addEventListener("click", function() {
      upload.abort();
    });
  });

</script>

</html>