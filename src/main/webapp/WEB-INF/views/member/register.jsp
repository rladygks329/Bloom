<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js" 
integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous">
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
	<style>
		.form_instructor {display: none;}
	</style>

</head>
<body>
	<!--	
	(1)첫 시작 메인페이지 - 로그인, 회원가입을 클릭할 수 있음
	(2)회원가입 클릭시 선택가능(일반회원, 강사회원)
	(3)각 화면에서 회원가입 폼 
	 - 이메일, 비밀번호, 이름, 연락처, 주소, (회원등급), (자기소개), (프로필사진)
	 - // 이메일: 형식검사, 중복검사
	 - // 비밀번호: 형식검사, 비밀번호 확인란
	 - 이름: 한글로만 입력되어있는지
	 - // 연락처: 형식검사
	 - 주소: API, 합쳐서 DB로 넘기기
	 - 등급: 처음 선택하고 조건문으로 숨기거나 보여주기(값은 디폴트로 고정)
	 - 자기소개, 프로필사진: 강사만
	 - 최종유효성검사
	 	이메일, 비밀번호, 이름, 주소, 비밀번호가 일치할 때만,
	(4)회원가입하면 로그인되면서 메인페이지 - 이때 로그아웃 버튼만 생성(세션값이 있을 때로)
		(회원, 강사 메인페이지 나눠야하나 아니면 다 똑같이 하고 강사인 경우에만 마이페이지나 강의등록 활성화)
	회원정보 세션으로 넘기기
	로그인상태면 메인페이지에서 로그아웃버튼만 활성화
	(5)로그아웃시 세션 삭제, 메인페이지 이동 
	강사 정보 조회화면
	 - 멤버테이블에서는 이름, 자기소개, 프로필사진 가져오기
	회원 마이페이지
	 - 정보수정기능(비밀번호, 연락처, 주소)
	 - 강사의 경우 자기소개, 프로필사진까지
	(전체적으로 코드 정리 필요) 
	-->
	
	<%
    String memberLevel = (String) request.getAttribute("memberLevel");
    out.println("Member Level: " + memberLevel);
	%>

	<h2>회원 가입하기</h2>
  	<form action="register" method="post">
    	
    	<p>이메일</p>
	    <input class="email_input" type="text" name="memberEmail" required="required">
	    <span class="email_input_warning" style="display: none;">유효한 이메일 형식이 아닙니다.</span><br>
	    <span class="email_input_re_1" style="display: none;">사용 가능한 이메일입니다.</span> 
	    <span class="email_input_re_2" style="display: none;">이메일이 이미 존재합니다.</span>
	    	    
	    <p>비밀번호</p>
	    <input class="pw_input" type="password" name="memberPassword" placeholder="비밀번호 입력" required="required">
	    <span class="pw_input_re" style="display: none;"></span>
	    
	    <p>비밀번호 확인</p>
	    <input class="pwck_input" type="password" name="memberPasswordCheck" placeholder="비밀번호 확인" required="required">
	    <span class="pwck_input_re_1" style="display: none;">비밀번호가 일치합니다.</span>
        <span class="pwck_input_re_2" style="display: none;">비밀번호가 일치하지 않습니다.</span>
	    
	    <p>이름</p>
	    <input class="name_input" type="text" name="memberName" placeholder="이름 입력" required="required">
	    <span class="name_input_re" style="display: none;"></span>
	    
	    <p>휴대폰번호</p>
	    <input class="pn_input" type="text" name="memberPhone" placeholder="번호 입력" required="required">
	    <span class="pn_input_re" style="display: none;"></span>
	    
	    <p>주소</p>
	    <input type="text" id="sample6_postcode" placeholder="우편번호">
		<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
		<input type="text" id="sample6_address" name="address" placeholder="주소"><br>
		<input type="text" id="sample6_detailAddress" name="detailAddress" placeholder="상세주소">
		<input type="text" id="sample6_extraAddress" placeholder="참고항목">
		<input type="hidden" id="address_input" name="memberAddress">
		
		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		
		<script>
			
			// 입력 이메일 형식 유효성 검사 
		    function mailFormCheck(email){
		       var form = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		       return form.test(email);
		    }		    
	    	
			// 이메일 중복검사 함수
			function checkEmailDuplication(email) {
				if($('.email_input').val() === "") {
					$('.email_input_warning').hide();
				// 유효성 검사를 통과한 경우에만 중복 검사 실행
				} else if (mailFormCheck(email)) {
			    	$('.email_input_warning').hide();
			        var data = { memberEmail: email };
			        $.ajax({
			            type: 'POST',
			            url: '/blooming/member/email',
			            data: data,
			            success: function (result) {
			                if (result === 'success') {
			                    $('.email_input_re_1').show();
			                    $('.email_input_re_2').hide();
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
			$('.email_input').on("blur", function () {
			    checkEmailDuplication($('.email_input').val());
			});

			// 비밀번호 형식 및 확인 일치 유효성 검사			
			$('.pw_input').on("change", function(){
				checkPasswordValid();
				checkPasswordMatch();
			});
		    
		    $('.pwck_input').on("change", function(){
				checkPasswordMatch();
			});
			
		    function checkPasswordValid(){
		    	var pw = $('.pw_input').val();
		    	var passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d).{4,8}$/;
		    	if (pw === "") {
		    		$('.pw_input_re').css('display','none');
		    	} else if (!passwordPattern.test(pw)) {
		    		$('.pw_input_re').css('display','block');
		    		$('.pw_input_re').text('영문자와 숫자 조합, 4~8자리로 입력해 주세요');
		    		$('.pw_input_re').css('color', 'red');
		    	} else {
		    		$('.pw_input_re').css('display','block');
		    		$('.pw_input_re').text('유효한 비밀번호입니다');
		    		$('.pw_input_re').css('color', 'green');
		    	}
		    } // end checkPasswordValid()
		    
			function checkPasswordMatch(){			 
			    var pw = $('.pw_input').val();
			    var pwck = $('.pwck_input').val();
			 	
			    if (pw === "" && pwck === "") {			        
			        $('.pwck_input_re_1').css('display','none');
			        $('.pwck_input_re_2').css('display','none');
			    } else if(pw == pwck) {
			        $('.pwck_input_re_1').css('display','block');
			        $('.pwck_input_re_2').css('display','none');
			        pwckcorCheck = true;
			    } else {
			        $('.pwck_input_re_1').css('display','none');
			        $('.pwck_input_re_2').css('display','block');
			        pwckcorCheck = false;
			    } 			    
			};  

			// 이름 유효성 검사	
			$('.name_input').on("change", function(){
				checkNameValid();
			});
			
			function checkNameValid(){
				var nm = $('.name_input').val();
				var namePattern = /^[가-힣]{2,10}$/;
				if(nm === "") {
					$('.name_input_re').css('display','none');
				} else if(!namePattern.test(nm)) {
					$('.name_input_re').css('display','block');
					$('.name_input_re').text('이름을 확인해 주세요(2자~10자).');
					$('.name_input_re').css('color','red');
				} else {
					$('.name_input_re').css('display','none');					
				}		
			} // end checkNameValid()			
			
			
			// 휴대폰번호 유효성 검사	
			$('.pn_input').on("change", function(){
				checkPhoneValid();
			});
			// 전부숫자만 받고 첫번째는 0, 두번째는 1로 고정하고 3번째자리는 0, 1, 6, 7, 8, 9 만 가능하게 하고 총 자릿수는 10 혹은 11자리로 하고싶음
			function checkPhoneValid(){
				var pn = $('.pn_input').val();
				// ^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$
				// ^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$
				var phonePattern = /^(01[016789]\d{7,8})$/;
				// var phonePattern = new RegExp('^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$');
				if(pn === "") {
					$('.pn_input_re').css('display','none');
				} else if(!phonePattern.test(pn)) {
					$('.pn_input_re').css('display','block');
					$('.pn_input_re').text('휴대전화번호 형식을 확인해 주세요.');
					$('.pn_input_re').css('color','red');
				} else {
					$('.pn_input_re').css('display','none');					
				}		
			} // end checkPhoneValid()
					
			// 카카오API
		    function sample6_execDaumPostcode() {
		        new daum.Postcode({
		            oncomplete: function(data) {
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
		                if(data.userSelectedType === 'R'){
		                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
		                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
		                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
		                        extraAddr += data.bname;
		                    }
		                    // 건물명이 있고, 공동주택일 경우 추가한다.
		                    if(data.buildingName !== '' && data.apartment === 'Y'){
		                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
		                    }
		                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
		                    if(extraAddr !== ''){
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
		    $('#sample6_address').on("change", function () {
		    	sumAddress();
		    });
		    
		    $('#sample6_detailAddress').on("change", function(){
		    	sumAddress();
		    });
		    
		    function sumAddress() {
		    	var basicAddress = $('#sample6_address').val();
		    	var detailAddress = $('#sample6_detailAddress').val();
		    	var fullAddress = basicAddress + ' ' + detailAddress;
		    	console.log(fullAddress);
		    	$('#address_input').val(fullAddress);
		    	
		    } // end sumAddress()
		    
		    /*
			$(document).ready(function() {
			    // 주소 입력란 값이 변경될 때마다 실행
			    $('#sample6_address, #sample6_detailAddress').on('input', function() {
			        // 두 개의 입력란에서 값을 가져옴
			        var basicAddress = $('#sample6_address').val();
			        var detailAddress = $('#sample6_detailAddress').val();
			        
			        // 주소 조합 (두 개의 주소를 합침)
			        var fullAddress = basicAddress + ' ' + detailAddress;
			        console.log(fullAddress);
			        // 숨겨진 입력란에 조합된 주소 정보를 설정
			        $('#memberAddress').val(fullAddress);
			    });
			});
		    */		    
		</script>
	
	    <input type="hidden" name="memberLevel" value="${memberLevel}" required="required">
	    
	    <div class="form_instructor">	 
		    <p>자기소개</p>
		    <input type="text" name="memberIntroduce" placeholder="자기소개">	
		     	     
		    <p>프로필 사진</p>
			<input type="file" id ="fileItem" name='uploadFile' style="height: 30px;">	
		    
		</div>
		
		<script>		
		    var memberLevel = "${memberLevel}"; // memberLevel 값을 가져옴
		    var formInstructor = document.querySelector(".form_instructor"); // CSS 클래스로 요소를 선택
	
		    if (memberLevel === "instructor") {
		        formInstructor.style.display = "block"; // 자기소개 및 프로필 사진을 보임
		    }
		</script>
				
		<!-- 파일 업로드 조건 체크 -->
		<script>
			var regex = new RegExp("(.*?)\.(jpg|png)$");
			var maxSize = 1048576; //1MB	
			
			function fileCheck(fileName, fileSize){

				if(fileSize >= maxSize){
					alert("파일 사이즈 초과");
					return false;
				}
					  
				if(!regex.test(fileName)){
					alert("해당 종류의 파일은 업로드할 수 없습니다.");
					return false;
				}				
				return true;				
			} // end fileCheck()
			
			// 이미지 업로드
			$("input[type='file']").on("change", function(e){
				
				// 첨부파일을 서버로 보내기 위한 가상의 form 태그
				// FormData 객체를 인스턴스화하여 변수에 저장(객체의 주소를 변수에 저장)
				var formData = new FormData();
				
				var fileInput = $('input[name="uploadFile"]');
				var fileList = fileInput[0].files;
				var fileObj = fileList[0];	
				
				//fileCheck가 false일 때 !연산자로 인해 true로 반환
				if(!fileCheck(fileObj.name, fileObj.size)){
					return false;
				}		
				
				// 사용자가 선택한 파일을 FormData에 uploadFile이란 key로 추가
				formData.append("uploadFile", fileObj);		
				
				// processData와 contentType은 false로 해야 한다
				$.ajax({
					url: '/blooming/image',
			    	processData : false,
			    	contentType : false,
			    	data : formData,
			    	type : 'POST',
			    	dataType : 'json'
			    	success : function(result){
			    		console.log(result);
			    	}
				});
				
			}); // end input[file]
			
			
			
		</script>	    
	    
	    <br>
	    <input type="submit" value="회원가입" >
  	</form>

  	
  	
</body>

</html>










