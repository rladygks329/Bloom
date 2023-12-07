<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<style>
	#register,
	a {
		display: inline-block;
		margin-right: 2px; /* 버튼 사이의 간격을 조절  */
	}
</style>
<title>새 글 작성 페이지</title>

</head>

<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	<br>
	<h3>질문글 작성</h3>
	<form action="register" method="POST">

		<div class="input-group mb-3">
	  		<input type="text" name="boardTitle" class="form-control" id="basic-url" aria-describedby="basic-addon3" placeholder="제목에 내용을 요약해 보세요" required>
		</div>
		<div id="test"></div>
		<div>
			<div id="boardContent">
				<textarea rows="20" class="boardContent" cols="120" name="boardContent" placeholder="내용 입력"></textarea>
			</div>
			<input type="hidden" id="memberId" name="memberId" value="${loginVo.memberId}" />
		</div>
		<div id="register">
			<input type="submit" class="btn btn-outline-secondary" value="등록">
			<a href="list"><button type="button" class="btn btn-outline-info" onclick="goBack()">취소</button></a>
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
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
</body>
</html>




