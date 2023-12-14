<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<meta charset="UTF-8">
<title>회원가입</title>
	<style>
		.form_instructor {display: none;}
	</style>
	
	<script>
	
		var emailFinalCheck = false;
		var emailCodeFinalCheck = false;
		var pwFinalCheck = false;
		var pwckFinalCheck = false;
		var nameFinalCheck = false;
		var nicknameFinalCheck = false;
		var phoneFinalCheck = false;
		var addressFinalCheck = false;
		var imageFinalCheck = false;
				
		function validateInputs(event){
			if(!emailFinalCheck) {
				alert("회원가입 정보를 확인해 주세요 : 이메일");
				return false;	
			}
			
			if(!emailCodeFinalCheck) {
				alert("회원가입 정보를 확인해 주세요 : 이메일인증");
				return false;
			}
			
			if(!pwFinalCheck) {
				alert("회원가입 정보를 확인해 주세요 : 비밀번호");
				return false;	
			}
			
			if(!pwckFinalCheck) {
				alert("회원가입 정보를 확인해 주세요 : 비밀번호확인");
				return false;	
			}
			
			if(!nameFinalCheck) {
				alert("회원가입 정보를 확인해 주세요 : 이름");
				return false;
			}
			
			if(!nicknameFinalCheck) {
				alert("회원가입 정보를 확인해 주세요 : 닉네임");
				return false;
			}
			
			if(!phoneFinalCheck) {
				alert("회원가입 정보를 확인해 주세요 : 휴대폰번호");
				return false;
			}
			
			if(!addressFinalCheck) {
				alert("회원가입 정보를 확인해 주세요 : 주소");
				return false;
			}
			
			// diabled한 input은 넘어가지 않으므로 풀어준다.
			$("input[name='memberEmail']").each(function(i) {
		        $(this).removeAttr('disabled');
			});
			
			return true;
		} // end validateInputs()		
	</script>

</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	
	<div class="container">
		<br>

		<div class="card bg-light">
			<article class="card-body mx-auto">
				<h4 class="card-title mt-3 text-center">계정 만들기</h4>
				<p class="text-center">무료로 시작하세요</p>
				<form action="register" method="post" onsubmit="return validateInputs(event)">
					<input type="hidden" name="memberLevel" value="${memberLevel}">
					<input type="hidden" name="memberProfileUrl">
					
					<!-- email -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-envelope"></i></span> 
						<input name="memberEmail" class="form-control" id="email_input" placeholder="Email address" type="email">
						<button class="btn btn-outline-secondary" type="button" id="button-send-emailCode" onclick="" disabled>인증번호 전송</button>
						<div class="valid-feedback">사용 가능한 이메일입니다.</div>
						<div class="invalid-feedback"></div>
					</div>
					
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-envelope"></i></span> 
						<input name="emailCode" class="form-control" id="emailCode_input" placeholder="인증번호 입력" type="text">
						<button class="btn btn-outline-secondary" type="button" id="button-check-emailCode" onclick="">인증번호 확인</button>
						<div class="valid-feedback">인증번호가 일치합니다</div>
						<div class="invalid-feedback"></div>
					</div>
					
					<!-- password -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-lock"></i></span> 
						<input name="memberPassword" class="form-control" id="pw_input" placeholder="비밀번호" type="password">
						<div class="valid-feedback">유효한 비밀번호입니다.</div>
						<div class="invalid-feedback"></div>
					</div>
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-lock"></i></span> 
						<input class="form-control" id="pwck_input" placeholder="비밀번호 확인" type="password">
						<div class="valid-feedback">비밀번호가 일치합니다.</div>
						<div class="invalid-feedback"></div>
					</div>
					
					<!-- name -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-user"></i></span> 
						<input name="memberName" class="form-control" id="name_input" placeholder="이름" type="text">
						<div class="valid-feedback">유효한 이름입니다</div>
						<div class="invalid-feedback"></div>
					</div>
					
					<!-- nickname -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-user"></i></span> 
						<input name="memberNickname" class="form-control" id="nickname_input" placeholder="닉네임" type="text">
						<div class="valid-feedback">사용 가능한 닉네임입니다</div>
						<div class="invalid-feedback"></div>
					</div>
					
					<!-- phone -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-phone"></i></span>
						<select class="form-select" id="phoneGroup01">
							<option selected value="010">010</option>
							<option value="011">011</option>
							<option value="016">016</option>
							<option value="017">017</option>
							<option value="018">018</option>
							<option value="019">019</option>
						</select>
						<input type="text" class="form-control" id="phoneGroup02" placeholder="xxxx" maxlength="4" aria-label="Username"> 
						<span class="input-group-text">-</span> 
						<input type="text" class="form-control" id="phoneGroup03" placeholder="xxxx" maxlength="4" aria-label="Server">
						<input type="hidden" id="phone_input" type="text" name="memberPhone" placeholder="번호 입력">
						<div class="valid-feedback">유효한 휴대폰번호입니다.</div>
						<div class="invalid-feedback"></div>
					</div>

					<!-- address -->
					<div class="input-group input-group-lg mb-3">
						<input type="text" id="sample6_postcode" class="form-control" placeholder="우편번호" aria-label="Recipient's username" aria-describedby="button-addon2" readonly>
						<button class="btn btn-outline-secondary" type="button" id="button-addon2" onclick="sample6_execDaumPostcode()">우편번호 찾기</button>
					</div>
					<div class="input-group input-group-lg mb-3">
						<input type="text" id="sample6_address" class="form-control" placeholder="주소" aria-label="Recipient's username" aria-describedby="button-addon2" readonly>
					</div>
					<div class="input-group input-group-lg mb-3">
						<input type="text" id="sample6_detailAddress" class="form-control" placeholder="상세 주소" aria-label="Recipient's username" aria-describedby="button-addon2"> <input type="text" id="sample6_extraAddress" class="form-control" placeholder="상세 주소 참고: 선택사항" aria-label="Recipient's username" aria-describedby="button-addon2">
					</div>
					<div>
						<input type="hidden" id="address_input" name="memberAddress">	
					</div>

					<div class="form_instructor">
						<!-- introduce -->
						<div class="input-group input-group-lg mb-3">
							<span class="input-group-text">자기소개</span>
							<textarea class="form-control" name="memberIntroduce" aria-label="With textarea" maxlength="300" placeholder="300자 이내로 작성해주세요"></textarea>
						</div>
	
						<!-- profile img -->
						<div class="mb-3">
							<label for="formFileLg" class="form-label">프로필 사진 이미지</label> <input class="form-control form-control-lg" id="fileItem" name='uploadFile' type="file">
						</div>
					</div>
					<!-- form-group end.// -->
					<p class="text-center">
						계정이 있으십니까? <a href="/blooming/member/login">로그인 하기</a>
					</p>
					<div class="d-flex justify-content-center">
						<input type="submit" class="btn btn-primary" value="회원가입">
					</div>
				</form>
			</article>
		</div>
		<!-- card.// -->
	</div>
	<!--container end.//-->
	
		<!-- 카카오톡 주소찾기 -->
		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		<script>
		
			// 이메일 유효성 및 중복검사
			function checkEmailDuplication(email) {
				var emailRegex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
				var input = $('#email_input');
				var invalidMsg = input.siblings(".invalid-feedback");
				
				if ($('#email_input').val() === "") {
					invalidMsg.text("이메일이 비어있습니다.")
					input.removeClass("is-valid").addClass("is-invalid");
					emailFinalCheck = false;
					$("#button-send-emailCode").prop("disabled", !emailFinalCheck);
					return;
				}
				
				// 유효성 검사를 통과하지 못한 경우
				if (!emailRegex.test(email)){					
					invalidMsg.text("이메일 형식을 확인해주세요")
					input.removeClass("is-valid").addClass("is-invalid");
					emailFinalCheck = false;
					$("#button-send-emailCode").prop("disabled", !emailFinalCheck);
					return;
				}
				
				var data = {
					memberEmail : email
				};
				$.ajax({
					type : 'POST',
					url : '/blooming/member/email',
					data : data,
					success: function (data) {				        
						input.removeClass("is-invalid").addClass("is-valid");
				    	emailFinalCheck = true;
				    	$("#button-send-emailCode").prop("disabled", !emailFinalCheck);
				    },
					error: function(xhr, status, error) {
						invalidMsg.text("중복된 이메일입니다")
						input.removeClass("is-valid").addClass("is-invalid");
						emailFinalCheck = false;
						$("#button-send-emailCode").prop("disabled", !emailFinalCheck);
	                }				
				});
			} // end checkEmailDuplication()

			// 이메일 입력 필드의 값이 변경될 때 검사 실행
			$('#email_input').on("blur", function() {
				checkEmailDuplication($('#email_input').val());
			});
			
			// 이메일 인증번호 전송
			$('#button-send-emailCode').on('click', function(){
				var email = $('#email_input').val()
				var data = {
					memberEmail : email
				};
				
				$.ajax({
					type : 'POST',
					url : '/blooming/member/sendemail',
					data : data,
					success: function(data) {
						
					},
					error: function(error) {
						console.error('이메일 전송 실패', error);
					}
				});
				alert('인증번호가 발송되었습니다. 확인해 주세요');
			}); 			
			
			// 이메일 인증번호 확인
			$('#button-check-emailCode').on('click', function () {
				var input = $('#emailCode_input');
				var invalidMsg = input.siblings(".invalid-feedback");
				var emailCode = $('#emailCode_input').val();
				var data = {
						emailCode : emailCode
				};
				
				$.ajax({
					type : 'POST',
					url : '/blooming/member/checkcode',
					data : data,
					success: function(data) {
						input.removeClass("is-invalid").addClass("is-valid");
						emailCodeFinalCheck = true;
						console.log(emailCodeFinalCheck);
						$('#email_input, #button-send-emailCode, #emailCode_input, #button-check-emailCode').prop('disabled', true);
					},
					error: function(error) {
						invalidMsg.text("인증번호가 일치하지 않습니다")
						input.removeClass("is-valid").addClass("is-invalid");
						emailCodeFinalCheck = false;
						console.log(emailCodeFinalCheck);
					}
				});			
			});					
			
			// 닉네임 유효성 및 중복검사
			function checkNicknameDuplication(nickname) {
				var nicknameRegex = /^[가-힣a-zA-Z0-9]{2,6}$/;
				var input = $('#nickname_input');
				var invalidMsg = input.siblings(".invalid-feedback");
				
				if ($('#nickname_input').val() === "") {	
					invalidMsg.text("닉네임이 비어있습니다.")
					input.removeClass("is-valid").addClass("is-invalid");
					nicknameFinalCheck = false;
					return;
				}
				
				// 유효성 검사를 통과하지 못한 경우
				if (!nicknameRegex.test(nickname)){
					console.log(nickname);
					invalidMsg.text("한글, 영문, 숫자 2~6자리로 입력해 주세요")
					input.removeClass("is-valid").addClass("is-invalid");	
					nicknameFinalCheck = false;
					return;
				}
				console.log(nickname);
				var data = {
					memberNickname : nickname
				};
				$.ajax({
					type : 'POST',
					url : '/blooming/member/checknickname',
					data : data,
					success: function (data) {				        
						input.removeClass("is-invalid").addClass("is-valid");
				    	nicknameFinalCheck = true; 
				    	console.log(nicknameFinalCheck);
				    },
					error: function(xhr, status, error) {
						invalidMsg.text("중복된 닉네임입니다")
						input.removeClass("is-valid").addClass("is-invalid");
						nicknameFinalCheck = false;
						console.log(nicknameFinalCheck);
	                }				
				});					
			} // end checkNicknameDuplication()

			// 닉네임 입력 필드의 값이 변경될 때 검사 실행
			$('#nickname_input').on("blur", function () {
			    checkNicknameDuplication($('#nickname_input').val());
			});
			
			// 비밀번호 및 비밀번호 확인 필드 값이 변경될 때 검사 실행			
			$('#pw_input').on("change", function() {
				checkPasswordValid();
				checkPasswordMatch();
			});

			$('#pwck_input').on("change", function() {
				checkPasswordMatch();
			});
			
			// 비밀번호 유효성검사
			function checkPasswordValid(password) {
				var pw = $('#pw_input').val();
				var passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d).{4,8}$/;
				var input = $('#pw_input');
				var invalidMsg = input.siblings(".invalid-feedback");
				
				if (pw === "") {
					invalidMsg.text("비밀번호를 입력해 주세요.")
					input.removeClass("is-valid").addClass("is-invalid");	
					pwFinalCheck = false;
				} else if (!passwordRegex.test(pw)) {
					invalidMsg.text("영문자와 숫자 조합, 4~8자리로 입력해 주세요.")
					input.removeClass("is-valid").addClass("is-invalid");	
					pwFinalCheck = false;
				} else {					
					input.removeClass("is-invalid").addClass("is-valid");
					pwFinalCheck = true;					
				}
			} // end checkPasswordValid()
			
			// 비밀번호확인 유효성검사
			function checkPasswordMatch() {
				var pw = $('#pw_input').val();
				var pwck = $('#pwck_input').val();
				var input = $('#pwck_input');
				var invalidMsg = input.siblings(".invalid-feedback");
				
				console.log(pw);
				console.log(pwck);
				
				if(pwck === '') {
					input.removeClass("is-valid is-invalid");
					pwckFinalCheck = false;
					return;
				}
				
				if (pw !== pwck) {
					invalidMsg.text("비밀번호가 일치하지 않습니다.")
					input.removeClass("is-valid").addClass("is-invalid");
					pwckFinalCheck = false;
				} else {
					input.removeClass("is-invalid").addClass("is-valid");
					pwckFinalCheck = true;
				}				
			}; // end checkPasswordMatch()

			// 이름 필드 값이 변경될 때 검사 실행
			$('#name_input').on("change", function() {
				checkNameValid();
			});

			// 이름 유효성검사
			function checkNameValid() {
				var name = $('#name_input').val();
				var nameRegex = /^[가-힣]{2,10}$/;
				var input = $('#name_input');
				var invalidMsg = input.siblings(".invalid-feedback");
				
				if (name === "") {
					invalidMsg.text("이름: 입력해 주세요.")
					input.removeClass("is-valid").addClass("is-invalid");
					nameFinalCheck = false;
					return;
				} 
				
				// 유효성 검사를 통과하지 못한경우
				if (!nameRegex.test(name)) {
					invalidMsg.text("이름 형식을 확인해 주세요(2~10자).")
					input.removeClass("is-valid").addClass("is-invalid");	
					console.log(name);
					nameFinalCheck = false;
					return;
				}
				input.removeClass("is-invalid").addClass("is-valid");
				nameFinalCheck = true;

			} // end checkNameValid()			

			// 휴대폰번호 필드값 변경 시 검사 실행	
			$('#phoneGroup01, #phoneGroup02, #phoneGroup03').on("change", function() {
				checkPhoneValid();
			});
			
			// 휴대폰번호 유효성검사
			function checkPhoneValid() {
				var firstNum = $('#phoneGroup01').val();
				var secondNum = $('#phoneGroup02').val();
				var thirdNum = $('#phoneGroup03').val();
				var phone = firstNum + '' + secondNum + '' + thirdNum;
				
				var phoneRegex = /^\d{10,11}$/;	
				var input = $('#phone_input');
				var invalidMsg = input.siblings(".invalid-feedback");
				
				console.log(thirdNum.length);
				if(thirdNum.length < 4){
					invalidMsg.text("세번째 칸은 4자리어야 합니다.")
					input.removeClass("is-valid").addClass("is-invalid");
					phoneFinalCheck = false;
					return;
				}
				
				if (!phoneRegex.test(phone)) {
					invalidMsg.text("휴대폰번호를 확인해 주세요.")
					input.removeClass("is-valid").addClass("is-invalid");
					phoneFinalCheck = false;
					return;
				} 
				
				if (phoneRegex.test(phone)) {
					input.removeClass("is-invalid").addClass("is-valid");
					$('#phone_input').val(phone);
					console.log($('#phone_input').val());
					phoneFinalCheck = true;
				}				
			} // end checkPhoneValid()
		
			
			// 카카오API			
			function sample6_execDaumPostcode() {
				new daum.Postcode({
					oncomplete : function(data) {
						// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
						// 각 주소의 노출 규칙에 따라 주소를 조합한다.
						// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
						var addr = ''; // 주소 변수
						var extraAddr = ''; // 참고항목 변수
	
						//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
						if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
							addr = data.roadAddress;
						} else { // 사용자가 지번 주소를 선택했을 경우(J)
							addr = data.jibunAddress;
						}
	
						// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
						if (data.userSelectedType === 'R') {
							// 법정동명이 있을 경우 추가한다. (법정리는 제외)
							// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
							if (data.bname !== ''
									&& /[동|로|가]$/g.test(data.bname)) {
								extraAddr += data.bname;
							}
							// 건물명이 있고, 공동주택일 경우 추가한다.
							if (data.buildingName !== ''
									&& data.apartment === 'Y') {
								extraAddr += (extraAddr !== '' ? ', '
										+ data.buildingName
										: data.buildingName);
							}
							// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
							if (extraAddr !== '') {
								extraAddr = ' (' + extraAddr + ')';
							}
							// 조합된 참고항목을 해당 필드에 넣는다.
							document.getElementById("sample6_extraAddress").value = extraAddr;
	
						} else {
							document.getElementById("sample6_extraAddress").value = '';
						}
	
						// 우편번호와 주소 정보를 해당 필드에 넣는다.
						document.getElementById('sample6_postcode').value = data.zonecode;
						document.getElementById("sample6_address").value = addr;
						// 커서를 상세주소 필드로 이동한다.
						document.getElementById("sample6_detailAddress").focus();
					}
				}).open();
			} // end 카카오API


			// 주소값 생성
			$('#sample6_address').on("change", function() {
				sumAddress();
			});

			$('#sample6_detailAddress').on("change", function() {
				sumAddress();
			});

			function sumAddress() {
				var postAddress = $('#sample6_postcode').val();
				var basicAddress = $('#sample6_address').val();
				var detailAddress = $('#sample6_detailAddress').val();
				var fullAddress = basicAddress + ' ' + detailAddress;
				console.log(fullAddress);
				$('#address_input').val(fullAddress);

				if (postAddress && basicAddress) {
					addressFinalCheck = true;
				}
			} // end sumAddress()
			
		</script>

		<script>
			var memberLevel = "${memberLevel}"; // memberLevel 값을 가져옴
			var formInstructor = document.querySelector(".form_instructor"); // CSS 클래스로 요소를 선택

			if (memberLevel === "instructor") {
				formInstructor.style.display = "block"; // 자기소개 및 프로필 사진을 보임
			}			
			
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
		</script>

	<br>
	<br>
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
</body>
</html>










