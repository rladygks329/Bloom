<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<title>${vo.boardTitle }</title>
</head>
<body>
	<h2>게시글 수정</h2>
	<form action="update" method="POST">
		<input type="hidden" name="page" value="${page }">
		<div>
			<p>글 번호 : ${vo.boardId }</p>
			<input type="hidden" name="boardId" value="${vo.boardId }">
		</div>
		<div>
			<p>제목 : </p>
			<input type="text" name="boardTitle" value="${vo.boardTitle }">
		</div>
		<div>
			<p>작성자 : ${vo.authorNickname }</p>
			<p>작성일 : ${vo.boardDateCreated }</p>
		</div>
		<div>
			<textarea rows="20" cols="120" class="boardContent" name="boardContent">${vo.boardContent }</textarea>
		</div>
		<div>
			<input type="submit" value="등록">
			<button type="button" onclick="goBack()">취소</button>
		</div>
	</form>	
	<script>
	    function goBack() {
	        window.history.back();
	    }	 
	    
	    $(function() {
			$('.boardContent').summernote({
			    height: 300,
			    lang: 'ko-KR',
			    callbacks: {
			    	onImageUpload: function(files, editor, welEditable) {
			        	for(var i = files.length -1; i>=0; i--) {
			        			sendFile(files[i], this);
			        	}
			        }
			    }
			});
		});
	    
	    function sendFile(file, el) {
			var data = new FormData();
			data.append('file', file);
			$.ajax({
				data: data,
				type : "post",
				url: '/blooming/image',
				cache :false,
				contentType : false,
				enctype : 'multipart/form-data',
				processData : false,
				success : function(data) {	
					console.log(data);
					
					$(el).summernote('editor.insertImage', "/blooming/image/display?fileName=" + data);					
					
				}
			});
		} // end senFile()		

	</script>

</body>
</html>









