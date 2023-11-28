<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<meta charset="UTF-8">
<!-- 서머노트 -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<title>새 글 작성 페이지</title>

</head>

<body>
	<h2>글 작성 페이지</h2>
	<form action="register" method="POST">
		<div>
			<p>제목 : </p>
			<input type="text" name="boardTitle" placeholder="제목 입력" required>
		</div>
		<div id="test"></div>
		<div>
			<p>내용</p>
			<div id="boardContent">
				<textarea rows="20" class="boardContent" cols="120" name="boardContent" placeholder="내용 입력"></textarea>
			</div>
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
	    	    
	    $(function() {
			$('.boardContent').summernote({
				placeholder: '최대 500자 작성 가능합니다.',
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
		}	   
	    
	</script>

</body>
</html>




