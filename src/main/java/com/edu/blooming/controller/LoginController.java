package com.edu.blooming.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	@GetMapping("/main")
	public void mainGET() {
		logger.info("mainGET() 호출");
	}
	
	@GetMapping("/login")
	public void loginGET() {
		logger.info("loginGET() 호출");
	}
	
	@PostMapping("/login")
	public String loginPOST(HttpServletRequest request, MemberVO vo, RedirectAttributes rttr) throws Exception {
		logger.info("loginPOST() 호출");
		
		HttpSession session = request.getSession();
		MemberVO loginVo = memberService.memberLogin(vo);
		
		if(loginVo == null) {
			int result = 0;
			rttr.addFlashAttribute("result", result);
			return "redirect:/member/login";
		} else {
			session.setAttribute("vo", loginVo);
			return "redirect:/member/main";
		} 
		
	} // end loginPOST()
	
	@GetMapping("/logout")
	public String logoutGET(HttpServletRequest request) throws Exception {
		logger.info("logoutGET() 호출");
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/member/main";
	}
	
//	@GetMapping("/logout")
//	public String logout(HttpServletRequest request) {
//		logger.info("logout() 호출");
//		HttpSession session = request.getSession();
//		if(session.getAttribute("memberId") != null) {
//			session.removeAttribute("memberId");
//			return "redirect:/board/list";
//		} else {
//			return "redirect:/board/list";
//		}
//	}
	
	
} // end LoginController()





















