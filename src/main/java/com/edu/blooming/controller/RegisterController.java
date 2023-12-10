package com.edu.blooming.controller;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
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
  public void registerGET(@RequestParam("type") String type, Model model) {
    logger.info("registerGET() 호출");
    model.addAttribute("memberLevel", type);
  } // end registerGET()

  @PostMapping("/register")
  public String registerPOST(MemberVO vo, Model model, HttpSession session) {
    logger.info("registerPOST() 호출");
    try {
      int result = memberService.register(vo);
      model.addAttribute("result", result);
      if (result == 1) {
        return "main";
      }
    } catch (MessagingException | UnsupportedEncodingException e) {
      // 예외 처리 로직 작성
      e.printStackTrace(); // 예외를 콘솔에 출력하거나 로깅할 수 있음
      model.addAttribute("result", -1); // 예외 발생 시 결과값을 -1로 설정 또는 다른 방법으로 처리
    }
    return "/register";
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
  public ResponseEntity<Void> emailCheckPOST(@RequestParam("memberEmail") String memberEmail)
      throws Exception {
    logger.info("emailCheck() 호출");
    boolean isEmailAvailable = memberService.checkEmail(memberEmail);
    logger.info("결과값 : " + isEmailAvailable);

    if (isEmailAvailable) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  } // end emailCheckPOST()

  @PostMapping("/checknickname")
  @ResponseBody
  public ResponseEntity<String> checkNicknamePOST(
      @RequestParam("memberNickname") String memberNickname) throws Exception {
    logger.info("checkNickname() 호출");
    boolean isNicknameAvailable = memberService.checkNickname(memberNickname);
    logger.info("결과값 : " + isNicknameAvailable);

    if (isNicknameAvailable) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  } // end checkNicknamePOST()

  @GetMapping("/confirm")
  public String emailConfirm(@RequestParam("email") String email,
      @RequestParam("emailKey") String emailKey, Model model) {
    logger.info("emailConfirm() 호출");
    logger.info("email = " + email + " emailKey = " + emailKey);
    memberService.updateEmailAuth(email, emailKey);
    model.addAttribute("confirmationMessage", "인증완료! 환영합니다 :) 로그인 후 이용해 주세요.");
    return "main";
  }

  @GetMapping("/emailAuthFail")
  public void emailAuthFailGET() {
    logger.info("emailAuthFailGET()");
  } // end emailAuthFailGET()

} // end RegisterController

