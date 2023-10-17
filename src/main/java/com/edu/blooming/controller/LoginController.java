package com.edu.blooming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.service.MemberService;

@Controller
@RequestMapping(value="/member")
public class LoginController {
	private static final Logger logger = 
			LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/register")
	public void registerGET() {
		logger.info("registerGET()");
	} // end registerGET()
	
	@PostMapping("/register")
	public String registerPOST(MemberVO vo, RedirectAttributes reAttr) {
		// RedirectAttributes : ���û�� �����͸� �����ϱ� ���� �������̽�
		logger.info("registerPOST() ȣ��");
		logger.info(vo.toString());
		int result = memberService.create(vo);
		logger.info(result + "�� ���� �Ϸ�");
		if(result == 1) {
			reAttr.addFlashAttribute("insert_result", "success");
			return "redirect:/main01";			
		} else {
			return "redirect:/main02";
		}				
	} // end PostMapping()
	
	
	
} // end LoginController












