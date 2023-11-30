<%@page import="com.edu.blooming.domain.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<style>
table, th, td {
	board-style : solid;
	board-width : 1px;
	text-align : center;
}

ul {
	list-style-type : none;
}

li {
	display : inline-block;
}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
     
	<h2>마이페이지</h2>
	<hr>
	<br>
	<br>
		
	<h4>내가 작성한 글</h4>
	<hr>
	<table>
		<thead>
			<tr>
				<th style="width : 60px">번호</th>
				<th style="width : 700px">제목</th>
				<th style="width : 120px">작성자</th>
				<th style="width : 60px">조회수</th>
				<th style="width : 60px">댓글수</th>
				<th style="width : 60px">좋아요</th>
				<th style="width : 300px">작성일</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach var="vo" items="${listByMemberId }">
				<tr>
					<td>${vo.boardId }</td>
					<td><a href="/blooming/board/detail?boardId=${vo.boardId }">${vo.boardTitle }</a></td>
					<td>${vo.authorNickname }</td>
					<td>${vo.boardViewCount }</td>
					<td>${vo.boardReplyCount }</td>
					<td>${vo.boardLikeCount }</td>
					<fmt:formatDate value="${vo.boardDateCreated }"
					pattern="yyyy-MM-dd HH:mm:ss" var="boardDateCreated"/>
					<td>${boardDateCreated }</td>
				</tr>			
			</c:forEach>
		</tbody> 
	</table>
	
	<h4>내가 좋아요 누른 글</h4>
	<hr>
	<table>
		<thead>
			<tr>
				<th style="width : 60px">번호</th>
				<th style="width : 700px">제목</th>
				<th style="width : 120px">작성자</th>
				<th style="width : 60px">조회수</th>
				<th style="width : 60px">댓글수</th>
				<th style="width : 60px">좋아요</th>
				<th style="width : 300px">작성일</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach var="vo" items="${listByLike }">
				<tr>
					<td>${vo.boardId }</td>
					<td><a href="/blooming/board/detail?boardId=${vo.boardId }">${vo.boardTitle }</a></td>
					<td>${vo.authorNickname }</td>
					<td>${vo.boardViewCount }</td>
					<td>${vo.boardReplyCount }</td>
					<td>${vo.boardLikeCount }</td>
					<fmt:formatDate value="${vo.boardDateCreated }"
					pattern="yyyy-MM-dd HH:mm:ss" var="boardDateCreated"/>
					<td>${boardDateCreated }</td>
				</tr>			
			</c:forEach>
		</tbody> 
	</table>	
	
	<h4>내가 작성한 댓글</h4>
	<hr>
	<table>
		<thead>
			<tr>
				<th style="width : 60px">번호</th>
				<th style="width : 700px">댓글내용</th>
				<th style="width : 300px">작성일</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach var="vo" items="${replyListByMemberId }">
				<tr>
					<td>${vo.boardReplyId }</td>
					<td><a href="/blooming/board/detail?boardId=${vo.boardId }">${vo.boardReplyContent }</a></td>
					<fmt:formatDate value="${vo.boardReplyDateCreated }"
					pattern="yyyy-MM-dd HH:mm:ss" var="boardReplyDateCreated"/>
					<td>${boardReplyDateCreated }</td>
				</tr>			
			</c:forEach>
		</tbody> 
	</table>
		
	<br>
	<hr>
	<h4>비밀번호 변경하기</h4>	
	<input type="hidden" id="memberId" name="memberId" value="${loginVo.memberId}" />
	
	<form id="passwordChangeForm">
	    <label for="newPassword">새 비밀번호:</label>
	    <input type="password" id="newPassword" name="newPassword" required><br>
	    <label for="confirmPassword">비밀번호 확인:</label>
	    <input type="password" id="confirmPassword" name="confirmPassword" required><br>
	    <button type="button" id="changePasswordButton">비밀번호 변경</button>
	    <p>영문자와 숫자 조합, 4~8자리로 입력해 주세요</p>	    
	</form>
	
	<br>
	<hr>
	
	<h4>닉네임 변경하기</h4>
	<form id="changeNicknameForm">	
		<label for="nickname">새 닉네임 :</label>
		<input type="text" id="nickname" name="nickname" required><br>
		<button type="button" id="changeNicknameBtn">닉네임 변경</button>
		<p>한글, 영문, 숫자 2~6자로 입력해 주세요.</p>	
	</form>
	
	<br>
	<hr>

	<h4>프로필사진 변경하기</h4>
	<!-- 기존 프로필 이미지 표시 -->
	<img src="/blooming/image/display?fileName=${loginVo.memberProfileUrl}" alt="프로필사진" id="profilePreview">
	<form id="changeProfileForm" enctype="multipart/form-data">
		<input type="hidden" id="profileUrl" name="memberProfileUrl">
	    <input type="file" id="fileItem" name='uploadFile' style="height: 30px;">
	    <button type="button" id="changeProfileBtn">프로필사진 변경</button>
	    <button type="button" id="deleteProfileBtn">프로필사진 삭제</button>
	</form>

	<br>
	<hr>

	<script>

		// 비밀번호 형식 유효성 검사 
	    function checkPasswordValid(password){
	    	var passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d).{4,8}$/;
	    	return passwordPattern.test(password);
	    }
		
		// 닉네임 형식 유효성 검사 
	    function checkNicknameValid(nickname){
	       var nicknamePattern = /^[가-힣a-zA-Z0-9]{2,6}$/;
	       return nicknamePattern.test(nickname);
	    }
		
	    $(document).ready(function() {
	        $('#changePasswordButton').click(function() {
	        	var memberId = $('#memberId').val();
	            var memberPassword = $('#newPassword').val();
	            var confirmPassword = $('#confirmPassword').val();
	            
	            // 비밀번호 형식 및 일치여부 확인
	            if(memberPassword === "") {
	            	alert("변경할 비밀번호를 입력해 주세요.");
	            	return;
	            }
	            
	            if (!checkPasswordValid(memberPassword)) {
	                alert("비밀번호 형식을 확인해 주세요.");
	                return;
	            }
	            
	            if (memberPassword !== confirmPassword) {
	                alert("변경할 비밀번호가 일치하지 않습니다.");
	                return;
	            }
	
	            // AJAX 요청을 통해 서버로 비밀번호 변경 요청 전송
	            $.ajax({
	                type: 'PUT',
	                url: '/blooming/member/password', 
	                headers : {
	    				'Content-Type' : 'application/json'
	    			},
	                data: memberPassword,
	                
	                success: function(data) {
	                    // 변경이 성공적으로 완료된 경우
	                    alert("비밀번호가 성공적으로 변경되었습니다. 재로그인해 주세요.");
	                	window.location.href = "/blooming/member/logout";                
	                },
	                error: function(xhr, status, error) {
	                    // 변경이 실패한 경우
	                    alert("비밀번호 변경에 실패했습니다. 다시 시도해 주세요.");
	                }
	            }); // end ajax()
	        }); // end changePasswordButton()	        
	        
	        
	        $('#changeNicknameBtn').click(function() {
	            var memberNickname = $('#nickname').val();
	            
	            // 닉네임 형식 확인
	            if(memberNickname === "") {
	            	alert("변경할 닉네임을 입력해 주세요.");
	            	return;
	            }
	            
	            if (!checkNicknameValid(memberNickname)) {
	                alert("닉네임 형식을 확인해 주세요.");
	                return;
	            }
	        	
	            if (!checkNicknameValid(memberNickname)) {
	                alert("닉네임 형식을 확인해 주세요.");
	                return;
	            }
	            
	            // AJAX 요청을 통해 서버로 닉네임 변경 요청 전송
	            $.ajax({
	                type: 'PUT',
	                url: '/blooming/member/nickname', 
	                headers : {
	    				'Content-Type' : 'application/json'
	    			},
	                data: memberNickname,
	                
	                success: function(data) {
	                    // 변경이 성공적으로 완료된 경우
	                    alert("닉네임이 성공적으로 변경되었습니다.");	                	                
	                },
	                error: function(xhr, status, error) {
	                    // 변경이 실패한 경우
	                    alert("닉네임 변경에 실패했습니다. 다시 시도해 주세요.");
	                }
	            }); // end ajax()	        	
	        }); // end changeNicknameBtn()		        
	        
	    	// 업로드 파일 양식 및 크기 체크
			var regex = new RegExp("(.*?)\.(jpg|png)$");
			var maxSize = 1048576; //1MB
			function fileCheck(fileName, fileSize) {
				if (fileSize >= maxSize) {
					alert("파일 사이즈 초과");
					return false;
				}

				if (!regex.test(fileName)) {
					alert("해당 종류의 파일은 업로드할 수 없습니다.");
					return false;
				}
				return true;
			} // end fileCheck()

			// 이미지 업로드		    
			$("input[type='file']").on("change", function(e) {

				// FormData 객체를 인스턴스화 하여 변수에 저장 후 전송 (객체의 주소를 변수에 저장)
				var formData = new FormData();

				// fileList, file 객체에 접근
				var fileInput = $('input[name="uploadFile"]');
				var fileList = fileInput[0].files;
				var fileObj = fileList[0];
				
				console.log("fileName : " + fileObj.name);
				console.log("fileSize : " + fileObj.size);
				console.log("fileType(MimeType) : " + fileObj.type);

				if (!fileCheck(fileObj.name, fileObj.size)) {
					return false;
				}

				// key: uploadFile, value: fileObj
				formData.append("file", fileObj);

				// ajax로 전송
				$.ajax({
					url : '/blooming/image',
					processData : false,
					contentType : false,
					data : formData,
					type : 'POST',
					dataType : 'text',

					success : function(result) {
						// 파일 업로드 성공 시 실행되는 코드
						console.log("파일 업로드 성공, 파일 이름: " + result);
						// 파일 이름을 memberProfileUrl 입력 상자에 설정
						$('input[name="memberProfileUrl"]').val(result);
					}
				});
			}); // end on()        
	        
			$('#changeProfileBtn').click(function() {
	            var memberProfileUrl = $('#profileUrl').val();	          
	            console.log(memberProfileUrl);
	            // AJAX 요청을 통해 서버로 프로필사진 변경 요청 전송
	            $.ajax({
	                type: 'PUT',
	                url: '/blooming/member/profile', 
	                headers : {
	    				'Content-Type' : 'application/json'
	    			},
	                data: memberProfileUrl,
	                
	                success: function(data) {
	                    // 변경이 성공적으로 완료된 경우
	                    alert("프로필사진이 성공적으로 변경되었습니다.");	                	                
	                },
	                error: function(xhr, status, error) {
	                    // 변경이 실패한 경우
	                    alert("프로필사진 변경에 실패했습니다. 다시 시도해 주세요.");
	                }
	            }); // end ajax()	        	
	        }); // end changeNicknameBtn()
	        
	    }); // end document()
	    
	    
	 	
	    
	    
	    
	    
	    
	</script>

</body>
</html>