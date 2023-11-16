package com.edu.blooming.controller;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class RegisterController {
  private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

  @Autowired
  private MemberService memberService;

  @GetMapping("/register")
  public void registerGET() {
    logger.info("registerGET() 호출");
  } // end registerGET()

  @PostMapping("/register")
  public String registerPOST(MemberVO vo, HttpSession session) {
    logger.info("registerPOST() 호출");
    int result = memberService.create(vo);
    if (result == 1) {
      session.setAttribute("loginVo", vo);
      // 메인 페이지로 리다이렉트
      return "main";
    }
    // 회원 가입 실패 시, 회원 가입 페이지로 리다이렉트
    return "member/register";

  } // end registerPOST()

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
  public ResponseEntity<String> emailCheckPOST(@RequestParam("memberEmail") String memberEmail)
      throws Exception {
    logger.info("emailCheck() 호출");
    int result = memberService.checkEmail(memberEmail);

    logger.info("결과값 : " + result);
    if (result == 0) {
      return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    return new ResponseEntity<String>("faile", HttpStatus.OK);
  } // end emailCheckPOST()

  @PostMapping("/nickname")
  @ResponseBody
  public ResponseEntity<String> checkNicknamePOST(
      @RequestParam("memberNickname") String memberNickname) throws Exception {
    logger.info("checkNickname() 호출");
    int result = memberService.checkNickname(memberNickname);

    logger.info("결과값 : " + result);
    if (result == 0) {
      return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    return new ResponseEntity<String>("faile", HttpStatus.OK);
  } // end checkNicknamePOST()


} // end RegisterController

