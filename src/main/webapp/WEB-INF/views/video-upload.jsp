<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<input id="video-file" type="file" name="file">
<button onclick="sendVideoChunks()">업로드</button>
<div id="result"></div>

</body>
<script>
    const sendVideoChunks = () => {
        const chunkSize = 1024 * 1024; // 1MB
        const file = document.getElementById("video-file").files[0];
        const resultElement = document.getElementById("result");

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
						resultElement.textContent = Math.round(currentChunk / totalChunks * 100) + "%";
	                    currentChunk++;
	                    if (currentChunk < totalChunks) {
	                        sendNextChunk();
	                    }
					},
					200 : function(msg) {
						resultElement.textContent = "100%";
						console.log(msg);
						//alert(msg);
					}
				},
			});
        };
        sendNextChunk();
    }
</script>
</html>