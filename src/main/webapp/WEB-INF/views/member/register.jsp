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
</head>
<body>
	<h2>회원 가입하기</h2>
  	<form action="register" method="post">
    	<p>이메일</p>
	    <input class="email_input" type="text" name="memberEmail" required="required"> <br>
	    <span class="email_input_re_1" style="display: none;">사용 가능한 이메일입니다.></span> <br>
	    <span class="email_input_re_2" style="display: none;">이메일이 이미 존재합니다.></span>
	    
	    
	    
	    <p>패스워드</p>
	    <input type="password" name="memberPassword" placeholder="비밀번호 입력" required="required">
	    
	    <p>핸드폰</p>
	    <input type="text" name="memberPhone" placeholder="번호 입력">
	    
	    <p>주소</p>
	    <!-- <input type="text" name="memberAddress" placeholder="주소 입력" required="required"> <br> -->
	    
	    
	    <input type="text" id="sample6_postcode" placeholder="우편번호">
		<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
		<input type="text" id="sample6_address" name="memberAddress" placeholder="주소"><br>
		<input type="text" id="sample6_detailAddress" name="detailAddress" placeholder="상세주소">
		<input type="text" id="sample6_extraAddress" placeholder="참고항목">
		<input type="hidden" id="samplememberAddress" name="samplememberAddress">
		
		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		<script>
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
	    
		    // 이메일 중복검사
		    $('.email_input').on("propertychange change keyup paste input", function() {
		    	// console.log("keyup 테스트");
		    	var memberEmail = $('.email_input').val();
		    	var data = {memberEmail : memberEmail}	// 컨트롤러에 넘길 데이터 이름
		    	
		    	$.ajax({
		    		type : 'POST',
		    		url : '/blooming/member/email',
		    		data : data,	
		    		success : function(result){
		    			if (result === 'success') {
		                    $('.email_input_re_1').show();
		                    $('.email_input_re_2').hide();
		                } else if (result === 'faile') {
		                    $('.email_input_re_1').hide();
		                    $('.email_input_re_2').show();
		                }
		    		} // end success		    				    		
		    	}) // end ajax		    	
		    }) // end on
		    
		    
		    
		    
		</script>
	        	 
	    <p>회원등급</p>
	    <input type="text" name="memberLevel" placeholder="회원등급" required="required">
	    	 
	    <p>자기소개</p>
	    <input type="text" name="memberIntroduce" placeholder="자기소개" required="required">	
	     
	     
	    <p>프로필 사진</p>
		<input type="file" id ="fileItem" name='uploadFile' style="height: 30px;">	
		
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
			    	type : 'POST',
					url: '/blooming/image',
			    	processData : false,
			    	contentType : false,
			    	data : formData,
			    	dataType : 'json'
				});
				
			}); // end input[file]
			
			
			
		</script>	    
	    
	    <br>
	    <input type="submit" value="전송" >
  	</form>
  	

  	
  	
  	<!-- 
  	기본, 상세주소 조합부분, 자꾸 오류나서 일단 패스 
  	<script>
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
	</script>		
	-->

  	
  	
</body>

</html>










