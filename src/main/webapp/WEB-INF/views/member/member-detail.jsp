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
	
	<!-- 프로필 사진 표시 -->
	<img src="<%= vo.getMemberProfileUrl() %>" alt="프로필 사진">
	
	<br>
	<br>
	
	
	<form id="passwordChangeForm">
		<input type="hidden" id="memberId" name="memberId" value="${loginVo.memberId}" />
	    <label for="currentPassword">기존 비밀번호:</label>
	    <input type="password" id="currentPassword" name="currentPassword" required><br>
	    <label for="newPassword">새 비밀번호:</label>
	    <input type="password" id="newPassword" name="newPassword" required><br>
	    <label for="confirmPassword">비밀번호 확인:</label>
	    <input type="password" id="confirmPassword" name="confirmPassword" required><br>
	    <button type="button" id="changePasswordButton">비밀번호 변경</button>
	</form>
	
	<!-- 
	<p>프로필 이미지 출력 부분</p>
	<img src="<%= session.getAttribute("memberProfileUrl") %>" alt="프로필 이미지" />
	 -->
	

	<script>
	    $(document).ready(function() {
	        $('#changePasswordButton').click(function() {
	        	var memberId = '<%= vo.getMemberId() %>';
	        	var originPassword = '<%= vo.getMemberPassword() %>';
	            var currentPassword = $('#currentPassword').val();
	            var memberPassword = $('#newPassword').val();
	            var confirmPassword = $('#confirmPassword').val();
	
	            // 기존 비밀번호와 세션 비밀번호 비교
	            if (currentPassword !== originPassword) {
	                alert("기존 비밀번호가 일치하지 않습니다.");
	                return;
	            }
	            
	            // 비밀번호 일치 여부 확인
	            if (memberPassword !== confirmPassword) {
	                alert("변경할 비밀번호가 일치하지 않습니다.");
	                return;
	            }
	
	            // AJAX 요청을 통해 서버로 비밀번호 변경 요청 전송
	            $.ajax({
	                type: 'POST',
	                url: '/blooming/member/changePassword', 
	                data: {
	                    memberId: memberId,
	                    memberPassword: memberPassword,
	                },
	                success: function(data) {
	                    // 변경이 성공적으로 완료된 경우
	                    alert("비밀번호가 성공적으로 변경되었습니다.");
	                    
	                    $.ajax({
	                        type: 'POST',
	                        url: '/blooming/member/logout',  // 로그아웃 처리를 하는 컨트롤러 URL
	                        success: function() {
	                        	alert("로그아웃 성공! 재로그인해 주세요.");
	                        	window.location.href = '/blooming/main';
	                        },
	                        error: function(xhr, status, error) {
	                            // 로그아웃이 실패한 경우, 메인 페이지로 리디렉션
	                            window.location.href = '/blooming/main';
	                        }
	                    });	                    
	                },
	                error: function(xhr, status, error) {
	                    // 변경이 실패한 경우
	                    alert("비밀번호 변경에 실패했습니다. 다시 시도해 주세요.");
	                }
	            });
	        });
	    });
	</script>



</body>
</html>