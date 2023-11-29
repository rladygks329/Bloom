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
  public void registerGET(@RequestParam("type") String type, Model model) {
    logger.info("registerGET() 호출");
    model.addAttribute("memberLevel", type);
  } // end registerGET()

  @PostMapping("/register")
  public String registerPOST(MemberVO vo, Model model, HttpSession session) {
    logger.info("registerPOST() 호출");
    int result = memberService.register(vo);
    model.addAttribute("result", result);
    if (result == 1) {
      return "main";
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
    if (!isEmailAvailable) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  } // end emailCheckPOST()

  @PostMapping("/checknickname")
  @ResponseBody
  public ResponseEntity<String> checkNicknamePOST(
      @RequestParam("memberNickname") String memberNickname) throws Exception {
    logger.info("checkNickname() 호출");
    boolean isNicknameAvailable = memberService.checkNickname(memberNickname);

    logger.info("결과값 : " + isNicknameAvailable);
    if (!isNicknameAvailable) {
      return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    return new ResponseEntity<String>("faile", HttpStatus.OK);
  } // end checkNicknamePOST()

} // end RegisterController

