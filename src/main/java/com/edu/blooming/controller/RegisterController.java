package com.edu.blooming.controller;

import javax.servlet.http.HttpServletRequest;
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
import com.edu.blooming.service.LectureService;
import com.edu.blooming.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class RegisterController {
  private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

  @Autowired
  private MemberService memberService;

  @Autowired
  LectureService lectureService;

  @GetMapping("/register")
  public String registerGET(@RequestParam("type") String type, Model model,
      HttpServletRequest request) {
    logger.info("registerGET() 호출");
    HttpSession session = request.getSession();
    if (session.getAttribute("loginVo") != null) {
      return "redirect:/main";
    }
    model.addAttribute("memberLevel", type);
    return "/member/register";
  } // end registerGET()
  /////

  @PostMapping("/register")
  public String registerPOST(MemberVO vo, Model model, HttpSession session) {
    logger.info("registerPOST() 호출");
    int result = memberService.register(vo);
    logger.info(vo.getMemberEmail());
    model.addAttribute("result", result);
    if (result == 1) {
      model.addAttribute("list_hot_like", lectureService.readHotLikeLectures(1, 5));
      model.addAttribute("list_hot_sale", lectureService.readHotSaleLectures(1, 5));
      return "/main";
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

  @PostMapping("/sendemail")
  @ResponseBody
  public String sendEmailPOST(HttpServletRequest request, String memberEmail, Model model) {
    HttpSession session = request.getSession();
    session.setMaxInactiveInterval(180);
    logger.info("세션 ID: " + session.getId() + ", 세션 유효 시간: " + session.getMaxInactiveInterval());

    String result = memberService.sendEmail(memberEmail, session);

    if (result != null && result.equals("success")) {
      logger.info("result = " + result);
      model.addAttribute("msg", "메일이 전송되었습니다. 인증번호를 확인해 주세요");
      return "alert";
    } else {
      model.addAttribute("msg", "이메일 전송에 실패했습니다");
      return "alert";
    }
  }

  @PostMapping("/checkcode")
  @ResponseBody
  public ResponseEntity<Void> checkCodePOST(@RequestParam String emailCode, HttpSession session) {
    String storedEmailCode = (String) session.getAttribute("emailCode");

    if (storedEmailCode == null || !storedEmailCode.equals(emailCode)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    session.invalidate();
    logger.info("세션 ID: " + session.getId() + ", 세션 유효 시간: " + session.getMaxInactiveInterval());
    return new ResponseEntity<>(HttpStatus.OK);
  }

} // end RegisterController

