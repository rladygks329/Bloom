package com.edu.blooming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	
	
	@PostMapping("/register-type")
	public String registerTypePOST(@RequestParam("memberLevel") String memberLevel, Model model) {
		logger.info("registerTypePOST() 호출");
		model.addAttribute("memberLevel", memberLevel);
		logger.info(memberLevel);
		return "/member/register";
	}
	
	@PostMapping("/email")
	@ResponseBody
	public ResponseEntity<String> emailCheckPOST(@RequestParam("memberEmail") String memberEmail) throws Exception{
		logger.info("emailCheck() 호출");
		int result = memberService.emailCheck(memberEmail);

		logger.info("결과값 : " + result);
		if(result == 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("faile", HttpStatus.OK);
		
	} // end emailCheckPOST()
	
} // end RegisterController
























