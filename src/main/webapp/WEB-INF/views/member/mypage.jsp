<%@page import="com.edu.blooming.domain.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
     
    <%
    	MemberVO vo = (MemberVO) session.getAttribute("loginVo"); // 세션에서 로그인 정보를 가져옴
    %>
    
	<h2>마이페이지</h2>	
	<br>
	<hr>	
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
	
	<form id="changeNicknameForm">	
		<label for="nickname">새 닉네임 :</label>
		<input type="text" id="nickname" name="nickname" required><br>
		<button type="button" id="changeNicknameBtn">닉네임 변경</button>
		<p>한글, 영문, 숫자 2~6자로 입력해 주세요.</p>	
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
	        
	        
	        
	        
	    }); // end document()
	    
	    
	    
	    
	</script>



</body>
</html>