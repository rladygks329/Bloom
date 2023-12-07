<%@page import="com.edu.blooming.domain.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
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

	.form_instructor {display: none;}

</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/component/navigation.jsp"%> 
	<ul class="nav justify-content-center underline">
  		<li class="nav-item">
   			<a class="nav-link" href="/blooming/member/mypage">내 활동</a>
 		</li>
		<li class="nav-item">
			<a class="nav-link" href="/blooming/member/mypage-identify">내 정보 수정</a>
		</li>
	</ul>
	<br>
	<hr>

	<input type="hidden" id="memberId" name="memberId" value="${loginVo.memberId}" />
	<input type="hidden" id="memberIntroduce" name="memberIntroduce" value="${loginVo.memberIntroduce}" />
	<input type="hidden" id="memberLevel" name="memberLevel" value="${loginVo.memberLevel}" />
	

	<div class="container">
		<br>
		<div class="card bg-light">
			<article class="card-body mx-auto">
				<h4 class="card-title mt-3 text-center">비밀번호 변경</h4>
				<form id="passwordChangeForm">
					
					<!-- password -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-lock"></i></span> 
						<input name="newPassword" class="form-control" id="newPassword" placeholder="변경할 비밀번호" type="password" required>
					</div>
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-lock"></i></span> 
						<input class="form-control" id="confirmPassword" name="confirmPassword" placeholder="비밀번호 확인" type="password" required>
					</div>
					<div>
						<p>영문자와 숫자 조합, 4~8자리로 입력해 주세요</p>	
					</div>
					<div class="d-flex justify-content-center">
						<button type="button" id="changePasswordButton" class="btn btn-primary">비밀번호 변경</button>
					</div>					
				</form>
			</article>
		</div>
		<!-- card.// -->
	</div>
	<!--container end.//-->

	<div class="container">
		<br>
		<div class="card bg-light">
			<article class="card-body mx-auto">
				<h4 class="card-title mt-3 text-center">닉네임 변경</h4>
				<form id="changeNicknameForm">
					<!-- nickname -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-user"></i></span> 
						<input name="nickname" class="form-control" id="nickname" placeholder="변경할 닉네임" type="text" required>
					</div>
					<div>
						<p>한글, 영문, 숫자 2~6자로 입력해 주세요.</p>	
					</div>						
					<div class="d-flex justify-content-center">
						<button type="button" id="changeNicknameBtn" class="btn btn-primary">닉네임 변경</button>
					</div>					
				</form>
			</article>
		</div>
		<!-- card.// -->
	</div>
	<!--container end.//-->

	<div class="form_instructor">
		<div class="container">
			<br>
			<div class="card bg-light">
				<article class="card-body mx-auto">
					<h4 class="card-title mt-3 text-center">프로필사진 변경</h4>
					<!-- 기존 프로필 이미지 표시 -->
					<div class="text-center">
						<img src="/blooming/image/display?fileName=${loginVo.memberProfileUrl}" alt="프로필사진" id="profilePreview" onerror="this.src='https://dummyimage.com/450x300/dee2e6/6c757d.jpg';" alt="...">
					</div>
					<form id="changeProfileForm" enctype="multipart/form-data">
						<input type="hidden" id="profileUrl" name="memberProfileUrl">
						<!-- profile img -->
						<div>
							<input class="form-control form-control-lg" id="fileItem" name='uploadFile' type="file">
						</div>
						<div class="d-flex justify-content-center">
							<p>1MB 이하의 jpg, png 파일 형식만 가능</p>	
						</div>
						<div class="d-flex justify-content-center" >
							<button type="button" id="changeProfileBtn" class="btn btn-primary" style="margin-right: 5px;">프로필사진 적용</button>
		    				<button type="button" id="deleteProfileBtn" class="btn btn-primary">프로필사진 삭제</button>
						</div>
					
					</form>
				</article>
			</div>
			<!-- card.// -->
		</div>
		<!--container end.//-->		

		<div class="container">
			<br>
			<div class="card bg-light">
				<article class="card-body mx-auto">
					<h4 class="card-title mt-3 text-center">소개글 변경</h4>
					<form id="changeIntroduceForm">
						<!-- introduce -->
						<div class="input-group input-group-lg mb-3">
							<span class="input-group-text">자기소개</span>
							<textarea class="form-control" name="memberIntroduce" class="memberIntroduce" aria-label="With textarea" required></textarea>
						</div>
						<div class="d-flex justify-content-center">
							<p>100자 이내로 작성해 주세요.</p>	
						</div>
						<div class="d-flex justify-content-center" >							
		    				<button type="button" id="changeIntroduceBtn" class="btn btn-primary">소개글 변경</button>
						</div>
					
					</form>
				</article>
			</div>
			<!-- card.// -->
		</div>
		<!--container end.//-->
		
	</div>
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
	
	<script>
		// 프로필사진, 소개글 출력조건
		var memberLevel = $('#memberLevel').val();
		var formInstructor = document.querySelector(".form_instructor"); 
	
		if (memberLevel === "instructor") {
			formInstructor.style.display = "block"; 
		}

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
	    	// 소개글 출력
	    	var memberIntroduce = $('#memberIntroduce').val();
	    	console.log(memberIntroduce);
	    	$(".memberIntroduce").val(memberIntroduce);
	    	
	    	// 소개글 변경버튼
	    	$('#changeIntroduceBtn').click(function() {
	    		var memberIntroduce = $(".memberIntroduce").val();
	    		console.log("memberIntroduce = " + memberIntroduce);
	    		$.ajax({
	                type: 'PUT',
	                url: '/blooming/member/introduce', 
	                headers : {
	    				'Content-Type' : 'application/json'
	    			},
	    			data: memberIntroduce,
	                
	                success: function(data) {
	                    // 변경이 성공적으로 완료된 경우
	                    alert("소개글이 성공적으로 변경되었습니다.");	                	
	                },
	                error: function(xhr, status, error) {
	                    // 변경이 실패한 경우
	                    alert("소개글 변경에 실패했습니다. 다시 시도해 주세요.");
	                }
	            }); // end ajax()	
	    	})
	    	
	    	
	    	// 비밀번호 변경버튼
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
	        
	     // 닉네임 입력 필드의 값이 변경될 때 검사 실행
			$('#nickname').on("blur", function () {
			    console.log('1')
			});
	        
	        // 닉네임 변경버튼
	        $('#changeNicknameBtn').click(function() {
	            var memberNickname = $('#nickname').val();
	            console.log(memberNickname);
	            
	            // 닉네임 형식 확인
	            if(memberNickname === "") {
	            	alert("변경할 닉네임을 입력해 주세요.");
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
	                    alert("중복된 닉네임입니다. 다시 시도해 주세요.");
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
						$('#profilePreview').attr('src', '/blooming/image/display?fileName=' + result);
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
	        
			$('#deleteProfileBtn').click(function() {
			    // AJAX 요청을 통해 서버로 프로필 사진 삭제 요청 전송
			    $.ajax({
			        type: 'PUT',
			        url: '/blooming/member/profile', 
			        headers: {
			            'Content-Type': 'application/json'
			        },
			        data: JSON.stringify({ memberProfileUrl: null }),

			        success: function(data) {
			            alert("프로필 사진이 성공적으로 삭제되었습니다.");
			            //$('#profilePreview').attr('src', '');
			        },
			        error: function(xhr, status, error) {
			            alert("프로필 사진 삭제에 실패했습니다. 다시 시도해 주세요.");
			        }
			    }); // end ajax()
			}); // end deleteProfileBn()	        
	    }); // end document()
	    
	</script>

</body>
</html>
