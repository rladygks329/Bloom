<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>새 글 작성 페이지</title>

</head>

<body>
	<h2>글 작성 페이지</h2>
	<form action="register" method="POST">
		<div>
			<p>제목 : </p>
			<input type="text" name="boardTitle" placeholder="제목 입력" required>
		</div>
		<div>
			<p>내용</p>
			<div id="boardContent">
				<textarea rows="20" cols="120" name="boardContent" placeholder="내용 입력"></textarea>
			</div>
			<input type="hidden" name="boardParentId" value="0">
			<input type="hidden" id="memberId" name="memberId" value="${loginVo.memberId}" />
		</div>
		<div>
			<input type="submit" value="등록">
		</div>
		<div>
    		<button type="button" onclick="goBack()">취소</button>
		</div>
	
	</form>
	
	<script>
	    function goBack() {
	        window.history.back();
	    }	
	    
	    
	    $(document).ready(function () {
	        // 파일을 끌어다 놓을 때(drag&drop)
	        // 브라우저가 파일을 자동으로 열어주는 기능을 막음
	        $('#boardContent').on('dragenter dragover', function (event) {
	            event.preventDefault();
	            console.log('드래그 중 테스트 중');
	        });

	        $('#boardContent').on('drop', function (event) {
	            event.preventDefault();
	            console.log('드롭 테스트 중');

	            // 파일을 서버로 업로드하기 위한 Ajax 사용
	            // multipart/form-data 타입으로 파일을 업로드하는 FormData 객체
	            var formData = new FormData();

	            // 드래그한 파일 정보를 갖고 있는 객체
	            var files = event.originalEvent.dataTransfer.files;

	            var i = 0;
	            for (i = 0; i < files.length; i++) {
	                console.log(files[i]);
	                formData.append("files", files[i]);
	            }

	            $.ajax({
	                type: 'post',
	                url: '/blooming/board/upload-ajax',
	                data: formData,
	                processData: false,
	                contentType: false,
	                success: function (data) {
	                    console.log(data);

	                    // 현재 커서 위치에 이미지 삽입
	                    var cursorPos = document.getElementById('boardContent').selectionStart;
	                    var imgTag = '<img src="display?fileName=' + data + '" alt="업로드된 이미지">';
	                    var content = document.getElementById('boardContent');
	                    var contentValue = content.value;
	                    content.value = contentValue.slice(0, cursorPos) + imgTag + contentValue.slice(cursorPos);

	                    // 이미지의 URL을 글 내용에 추가
	                    var imageUrl = 'display?fileName=' + data;
	                    content.value += '\n' + imageUrl;
	                }
	            });

	        });

	    }); // 문서 로드 완료 이벤트 종료
	    
	    
	</script>

</body>
</html>




