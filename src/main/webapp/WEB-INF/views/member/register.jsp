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
.form_instructor {
	display: none;
}

.divider-text {
	position: relative;
	text-align: center;
	margin-top: 15px;
	margin-bottom: 15px;
}

.divider-text span {
	padding: 7px;
	font-size: 12px;
	position: relative;
	z-index: 2;
}

.divider-text:after {
	content: "";
	position: absolute;
	width: 100%;
	border-bottom: 1px solid #ddd;
	top: 55%;
	left: 0;
	z-index: 1;
}

.btn-facebook {
	background-color: #405D9D;
	color: #fff;
}

.btn-twitter {
	background-color: #42AEEC;
	color: #fff;
}
</style>

<script>
	var emailFinalCheck = false;
	var pwFinalCheck = false;
	var pwckFinalCheck = false;
	var nameFinalCheck = false;
	var phoneFinalCheck = false;
	var addressFinalCheck = false;

	function validateInputs(event) {
		if (emailFinalCheck == false) {
			alert("회원가입 정보를 확인해 주세요 : 이메일");
			return false;
		}

		if (pwFinalCheck == false) {
			alert("회원가입 정보를 확인해 주세요 : 비밀번호");
			return false;
		}

		if (pwckFinalCheck == false) {
			alert("회원가입 정보를 확인해 주세요 : 비밀번호확인");
			return false;
		}

		if (nameFinalCheck == false) {
			alert("회원가입 정보를 확인해 주세요 : 이름");
			return false;
		}

		if (phoneFinalCheck == false) {
			alert("회원가입 정보를 확인해 주세요 : 휴대폰번호");
			return false;
		}

		if (addressFinalCheck == false) {
			alert("회원가입 정보를 확인해 주세요 : 주소");
			return false;
		}
		return true;
	} // end validateInputs()
</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/component/navigation.jsp"%>
	<%
	String memberLevel = (String) request.getAttribute("memberLevel");
	out.println("Member Level: " + memberLevel);
	%>

	<h2>회원 가입하기</h2>
	<form action="register" method="post" onsubmit="return validateInputs(event)">
		<p>이메일</p>
		<input class="email_input" type="text" name="memberEmail"> <span class="email_input_warning" style="display: none;">유효한 이메일 형식이 아닙니다.</span><br> <span class="email_input_re_1" style="display: none;">사용 가능한 이메일입니다.</span> <span class="email_input_re_2" style="display: none;">이메일이 이미 존재합니다.</span>
		<p>비밀번호</p>
		<input class="pw_input" type="password" name="memberPassword" placeholder="비밀번호 입력"> <span class="pw_input_re" style="display: none;"></span>
		<p>비밀번호 확인</p>
		<input class="pwck_input" type="password" name="memberPasswordCheck" placeholder="비밀번호 확인"> <span class="pwck_input_re_1" style="display: none;">비밀번호가 일치합니다.</span> <span class="pwck_input_re_2" style="display: none;">비밀번호가 일치하지 않습니다.</span>
		<p>이름</p>
		<input class="name_input" type="text" name="memberName" placeholder="이름 입력"> <span class="name_input_re" style="display: none;"></span>
		<p>휴대폰번호</p>
		<input class="pn_input" type="text" name="memberPhone" placeholder="번호 입력"> <span class="pn_input_re" style="display: none;"></span>
		<p>주소</p>
		<input type="text" id="sample6_postcode" placeholder="우편번호" readonly> <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" readonly><br> <input type="text" id="sample6_address" name="address" placeholder="주소" readonly><br> <input type="text" id="sample6_detailAddress" name="detailAddress" placeholder="상세주소"> <input type="text" id="sample6_extraAddress" placeholder="참고항목: 선택사항"> <input type="hidden" id="address_input" name="memberAddress">
		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		<script>
			// 입력 이메일 형식 유효성 검사 
			function mailFormCheck(email) {
				var form = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
				return form.test(email);
			}

			// 이메일 중복검사 함수
			function checkEmailDuplication(email) {
				if ($('.email_input').val() === "") {
					$('.email_input_warning').hide();

					// 유효성 검사를 통과한 경우에만 중복 검사 실행
				} else if (mailFormCheck(email)) {
					$('.email_input_warning').hide();
					var data = {
						memberEmail : email
					};
					$.ajax({
						type : 'POST',
						url : '/blooming/member/email',
						data : data,
						success : function(result) {
							if (result === 'success') {
								$('.email_input_re_1').show();
								$('.email_input_re_2').hide();
								emailFinalCheck = true;

							} else if (result === 'faile') {
								$('.email_input_re_1').hide();
								$('.email_input_re_2').show();

							}
						}
					});
				} else {
					// 유효성 검사를 통과하지 못한 경우
					$('.email_input_warning').show();
					$('.email_input_re_1').hide();
					$('.email_input_re_2').hide();

				}
			} // end checkEmailDuplication()

			// 이메일 입력 필드의 값이 변경될 때 검사 실행
			$('.email_input').on("blur", function() {
				checkEmailDuplication($('.email_input').val());
			});

			// 비밀번호 형식 및 확인 일치 유효성 검사			
			$('.pw_input').on("change", function() {
				checkPasswordValid();
				checkPasswordMatch();
			});

			$('.pwck_input').on("change", function() {
				checkPasswordMatch();
			});

			function checkPasswordValid() {
				var pw = $('.pw_input').val();
				var passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d).{4,8}$/;
				if (pw === "") {
					$('.pw_input_re').css('display', 'none');
					pwFinalCheck = false;
				} else if (!passwordPattern.test(pw)) {
					$('.pw_input_re').css('display', 'block');
					$('.pw_input_re').text('영문자와 숫자 조합, 4~8자리로 입력해 주세요');
					$('.pw_input_re').css('color', 'red');
					pwFinalCheck = false;
				} else {
					$('.pw_input_re').css('display', 'block');
					$('.pw_input_re').text('유효한 비밀번호입니다');
					$('.pw_input_re').css('color', 'green');
					pwFinalCheck = true;
				}
			} // end checkPasswordValid()

			function checkPasswordMatch() {
				var pw = $('.pw_input').val();
				var pwck = $('.pwck_input').val();

				if (pw === "" && pwck === "") {
					$('.pwck_input_re_1').css('display', 'none');
					$('.pwck_input_re_2').css('display', 'none');
					pwckFinalCheck = false;
				} else if (pw == pwck) {
					$('.pwck_input_re_1').css('display', 'block');
					$('.pwck_input_re_2').css('display', 'none');
					pwckFinalCheck = true;
				} else {
					$('.pwck_input_re_1').css('display', 'none');
					$('.pwck_input_re_2').css('display', 'block');
					pwckFinalCheck = false;
				}
			};

			// 이름 유효성 검사	
			$('.name_input').on("change", function() {
				checkNameValid();
			});

			function checkNameValid() {
				var nm = $('.name_input').val();
				var namePattern = /^[가-힣]{2,10}$/;
				if (nm === "") {
					$('.name_input_re').css('display', 'none');
					nameFinalCheck = false;
				} else if (!namePattern.test(nm)) {
					$('.name_input_re').css('display', 'block');
					$('.name_input_re').text('이름을 확인해 주세요(2자~10자)');
					$('.name_input_re').css('color', 'red');
					nameFinalCheck = false;
				} else {
					$('.name_input_re').css('display', 'none');
					nameFinalCheck = true;
				}
			} // end checkNameValid()			

			// 휴대폰번호 유효성 검사	
			$('.pn_input').on("change", function() {
				checkPhoneValid();
			});
			// 전부숫자만 받고 첫번째는 0, 두번째는 1로 고정하고 3번째자리는 0, 1, 6, 7, 8, 9 만 가능하게 하고 총 자릿수는 10 혹은 11자리로 하고싶음
			function checkPhoneValid() {
				var pn = $('.pn_input').val();
				// ^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$
				// ^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$
				var phonePattern = /^(01[016789]\d{7,8})$/;
				// var phonePattern = new RegExp('^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$');
				if (pn === "") {
					$('.pn_input_re').css('display', 'none');
					phoneFinalCheck = false;
				} else if (!phonePattern.test(pn)) {
					$('.pn_input_re').css('display', 'block');
					$('.pn_input_re').text('휴대전화번호 형식을 확인해 주세요.');
					$('.pn_input_re').css('color', 'red');
					phoneFinalCheck = false;
				} else {
					$('.pn_input_re').css('display', 'none');
					phoneFinalCheck = true;
				}
			} // end checkPhoneValid()

			// 카카오API
			function sample6_execDaumPostcode() {
				new daum.Postcode(
						{
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
									document
											.getElementById("sample6_extraAddress").value = extraAddr;

								} else {
									document
											.getElementById("sample6_extraAddress").value = '';
								}

								// 우편번호와 주소 정보를 해당 필드에 넣는다.
								document.getElementById('sample6_postcode').value = data.zonecode;
								document.getElementById("sample6_address").value = addr;
								// 커서를 상세주소 필드로 이동한다.
								document
										.getElementById("sample6_detailAddress")
										.focus();
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

		<input type="hidden" name="memberLevel" value="${memberLevel}">

		<div class="form_instructor">
			<p>강사소개</p>
			<textarea name="memberIntroduce" rows="5" cols="25"></textarea>

			<p>프로필 사진</p>
			<input type="file" id="fileItem" name='uploadFile' style="height: 30px;">

		</div>

		<input type="hidden" name="memberProfileUrl">

		<script>
			var memberLevel = "${memberLevel}"; // memberLevel 값을 가져옴
			var formInstructor = document.querySelector(".form_instructor"); // CSS 클래스로 요소를 선택

			if (memberLevel === "instructor") {
				formInstructor.style.display = "block"; // 자기소개 및 프로필 사진을 보임
			}
		</script>

		<script>
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
				formData.append("uploadFile", fileObj);

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
		</script>

		<br> <input type="submit" value="회원가입">
	</form>

	<div class="container">
		<br>

		<div class="card bg-light">
			<article class="card-body mx-auto">
				<h4 class="card-title mt-3 text-center">계정 만들기</h4>
				<p class="text-center">무료로 시작하세요</p>
				<form>
					<!-- name -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-user"></i></span> 
						<input name="" class="form-control" placeholder="이름" type="text">
						<div class="valid-feedback">Looks good!</div>
						<div class="invalid-feedback">Looks bad!</div>
					</div>
					
					<!-- email -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-envelope"></i>
						</span> <input name="" class="form-control" placeholder="Email address" type="email">
					</div>

					<!-- phone -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-phone"></i>
						</span>
						<select class="form-select" id="inputGroupSelect01">
							<option selected value="010">010</option>
							<option value="011">011</option>
							<option value="016">016</option>
							<option value="017">017</option>
							<option value="018">018</option>
							<option value="019">019</option>
						</select>
						<input type="text" class="form-control" placeholder="xxxx" aria-label="Username"> <span class="input-group-text">-</span> <input type="text" class="form-control" placeholder="xxxx" aria-label="Server">
					</div>

					<!-- password -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-lock"></i>
						</span> <input class="form-control" placeholder="비밀번호" type="password">
					</div>
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text"> <i class="fa fa-lock"></i>
						</span> <input class="form-control" placeholder="비밀번호 확인" type="password">
					</div>

					<!-- address -->
					<div class="input-group input-group-lg mb-3">
						<input type="text" class="form-control" placeholder="우편번호" aria-label="Recipient's username" aria-describedby="button-addon2">
						<button class="btn btn-outline-secondary" type="button" id="button-addon2">우편번호 찾기</button>
					</div>
					<div class="input-group input-group-lg mb-3">
						<input type="text" class="form-control" placeholder="주소" aria-label="Recipient's username" aria-describedby="button-addon2">
					</div>
					<div class="input-group input-group-lg mb-3">
						<input type="text" class="form-control" placeholder="상세 주소" aria-label="Recipient's username" aria-describedby="button-addon2"> <input type="text" class="form-control" placeholder="상세 주소 참고: 선택사항" aria-label="Recipient's username" aria-describedby="button-addon2">
					</div>

					<!-- introduce -->
					<div class="input-group input-group-lg mb-3">
						<span class="input-group-text">자기소개</span>
						<textarea class="form-control" aria-label="With textarea"></textarea>
					</div>

					<!-- profile img -->
					<div>
						<label for="formFileLg" class="form-label">프로필 사진 이미지</label> <input class="form-control form-control-lg" id="formFileLg" type="file">
					</div>

					<!-- form-group end.// -->
					<p class="text-center">
						계정이 있으십니까? <a href="/blooming/member/login">로그인 하기</a>
					</p>
				</form>
			</article>
		</div>
		<!-- card.// -->
	</div>
	<!--container end.//-->

	<br>
	<br>
	<%@ include file="/WEB-INF/views/component/footer.jsp"%>
</body>
</html>










