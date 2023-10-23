package com.edu.blooming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.service.MemberService;

@Controller
@RequestMapping(value="/member")
public class RegisterController {
	private static final Logger logger = 
			LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/register")
	public void registerGET() {
		logger.info("registerGET()");
	} // end registerGET()
	
	@GetMapping("/register-type")
	public void registerTypeGET() {
		logger.info("registerTypeGET()");
	} // end registerTypeGET()
	
	@GetMapping("/register-student")
	public void registerStudentGET() {
		logger.info("registerStudentGET()");
	} // end registerStudentGET()
	
	@GetMapping("/register-teacher")
	public void registerteacherGET() {
		logger.info("registerteacherGET()");
	} // end registerteacherGET()
	
	@PostMapping("/register")
	public String registerPOST(MemberVO vo, RedirectAttributes reAttr) {
		// RedirectAttributes : 재요청시 데이터를 전달하기 위한 인터페이스
		logger.info("registerPOST() 호출");
		logger.info(vo.toString());
		int result = memberService.create(vo);
		logger.info(result + "행 삽입 완료");
		if(result == 1) {
			reAttr.addFlashAttribute("insert_result", "success");
			return "redirect:/main01";	
		} else {
			return "redirect:/main02";
		}				
	} // end PostMapping()
	
	@PostMapping("/email")
	@ResponseBody
	public ResponseEntity<String> emailCheckPOST(@RequestParam("memberEmail") String memberEmail) throws Exception{
		logger.info("emailCheck() 호출");
		int result = memberService.emailCheck(memberEmail);

		logger.info("결과값 : " + result);
		if(result == 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("faile", HttpStatus.METHOD_FAILURE);
		}
	} // end emailCheckPOST()
	
	
} // end RegisterController
























