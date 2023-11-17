package com.edu.blooming.controller;

import java.util.Map;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  
  @GetMapping("/member-detail")
  public void memberDetailGET() {
    logger.info("memberDetail 호출");
  }

  @GetMapping("/mypage")
  public String myPageGET(HttpServletRequest request, Model model) {
    logger.info("myPageGET() 호출");
    HttpSession session = request.getSession();
    MemberVO user = (MemberVO) session.getAttribute("loginVo");

    if (user.getMemberLevel().equals("instructor")) {
      Map<String, Object> result = memberService.getInstuctorStatus(user.getMemberId());
      model.addAllAttributes(result);
      return "/member/mypage-instructor";
    }
    return "/member/member-detail";
  }
  
  @PutMapping("/password")
  @ResponseBody
  public ResponseEntity<Void> changePasswordPOST(@RequestBody String memberPassword,
      HttpSession session) {
    int result = 0;
    if (session.getAttribute("loginVo") != null) {
      int memberId = ((MemberVO) session.getAttribute("loginVo")).getMemberId();
      result = memberService.updatePassword(memberId, memberPassword);
    }

    logger.info("결과값 : " + result);
    if (result == 1) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @PutMapping("/nickname")
  @ResponseBody
  public ResponseEntity<Void> changeNicknamePUT(@RequestBody String memberNickname,
      HttpSession session) {
    int result = 0;
    if (session.getAttribute("loginVo") != null) {
      int memberId = ((MemberVO) session.getAttribute("loginVo")).getMemberId();
      result = memberService.updatePassword(memberId, memberNickname);
    }

    logger.info("결과값 : " + result);
    if (result == 1) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @PutMapping("/introduce")
  @ResponseBody
  public ResponseEntity<Void> changeIntroducePUT(@RequestBody String memberIntroduce,
      HttpSession session) {
    int result = 0;
    if (session.getAttribute("loginVo") != null) {
      int memberId = ((MemberVO) session.getAttribute("loginVo")).getMemberId();
      result = memberService.updatePassword(memberId, memberIntroduce);
    }

    logger.info("결과값 : " + result);
    if (result == 1) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }


} // end LoginController()


