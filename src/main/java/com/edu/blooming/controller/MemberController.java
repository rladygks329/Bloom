package com.edu.blooming.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.service.MemberService;


@Controller
@RequestMapping(value = "/member")
public class MemberController {
  private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

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
  public String loginPOST(HttpServletRequest request, MemberVO vo, RedirectAttributes rttr)
      throws Exception {
    logger.info("loginPOST() 호출 memberEmail = " + vo.getMemberEmail());

    MemberVO loginVo = memberService.login(vo);

    if (loginVo == null) {
      int result = 0;
      rttr.addFlashAttribute("result", result);
      return "redirect:/member/login";
    } else {
      HttpSession session = request.getSession();
      session.setAttribute("loginVo", loginVo);
      logger.info("loginVo =  " + loginVo.toString());
      return "redirect:/main";
    }
  } // end loginPOST()

  @GetMapping("/logout")
  public String logoutGET(HttpServletRequest request) throws Exception {
    logger.info("logoutGET() 호출");
    HttpSession session = request.getSession();
    session.invalidate();
    return "redirect:/main";
  }

  @PostMapping("/logout")
  public String logoutPOST(HttpServletRequest request) throws Exception {
    logger.info("logoutPOST() 호출");
    HttpSession session = request.getSession();
    session.invalidate();
    return "redirect:/main";
  }

  @GetMapping("/detail")
  public void memberDetailGET() {
    logger.info("memberDetail 호출");
  }

  @PostMapping("/changePassword")
  @ResponseBody
  public ResponseEntity<String> changePasswordPOST(@RequestParam("memberId") Integer memberId,
      @RequestParam("memberPassword") String memberPassword, HttpSession session,
      RedirectAttributes redirectAttributes) {
    logger.info("changePassword 호출 memberId = " + memberId);
    int result = memberService.updatePassword(memberId, memberPassword);

    logger.info("결과값 : " + result);
    if (result == 1) {
      session.invalidate();
      return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    return new ResponseEntity<String>("fail", HttpStatus.OK);

  }


} // end LoginController()


