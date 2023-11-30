package com.edu.blooming.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
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
import com.edu.blooming.domain.BoardReplyVO;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.service.BoardReplyService;
import com.edu.blooming.service.BoardService;
import com.edu.blooming.service.MemberService;


@Controller
@RequestMapping(value = "/member")
public class MemberController {
  private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

  @Autowired
  private MemberService memberService;

  @Autowired
  private BoardService boardService;

  @Autowired
  private BoardReplyService boardReplyService;

  @GetMapping("/login")
  public void loginGET() {
    logger.info("loginGET() 호출");
  }

  @PostMapping("/login")
  public String loginPOST(HttpServletRequest request, MemberVO vo, String targetURL,
      RedirectAttributes rttr) throws Exception {
    MemberVO loginVo = memberService.login(vo);

    if (loginVo == null) {
      String queryString = getLoginPageQueryString(targetURL);
      logger.info("redirect:/member/login" + queryString);
      return "redirect:/member/login" + queryString;
    }

    HttpSession session = request.getSession();
    session.setAttribute("loginVo", loginVo);
    return "redirect:" + getRedirectURL(targetURL);
  } // end loginPOST()

  @GetMapping("/logout")
  public String logoutGET(HttpServletRequest request) throws Exception {
    logger.info("logoutGET() 호출");
    HttpSession session = request.getSession();
    session.invalidate();
    return "redirect:/main";
  }

  @GetMapping("/instructor-page")
  public String instructorPageGet(HttpServletRequest request, Model model) {
    int memberId = (int) request.getAttribute("memberId");
    Map<String, Object> result = memberService.getInstuctorStatus(memberId);
    model.addAllAttributes(result);
    return "/member/mypage-instructor";
  }

  @GetMapping("/mypage")
  public String myPageGET(HttpServletRequest request, Model model) {
    logger.info("myPageGET() 호출");
    int memberId = (int) request.getAttribute("memberId");

    List<BoardVO> listByMemberId = boardService.readByMemberId(memberId);
    List<BoardVO> listByLike = boardService.readByMemberIdAndLIke(memberId);
    List<BoardReplyVO> replyListByMemberId = boardReplyService.readByMemberId(memberId);

    model.addAttribute("listByMemberId", listByMemberId);
    model.addAttribute("listByLike", listByLike);
    model.addAttribute("replyListByMemberId", replyListByMemberId);

    return "/member/mypage";
  }

  @PutMapping("/password")
  @ResponseBody
  public ResponseEntity<Void> changePasswordPOST(HttpServletRequest request,
      @RequestBody String memberPassword) {
    int memberId = (int) request.getAttribute("memberId");
    int result = memberService.updatePassword(memberId, memberPassword);

    logger.info("결과값 : " + result);
    if (result != 1) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/nickname")
  @ResponseBody
  public ResponseEntity<Void> changeNicknamePUT(HttpSession session, HttpServletRequest request,
      @RequestBody String memberNickname) {
    int memberId = (int) request.getAttribute("memberId");
    int result = memberService.updateNickname(memberId, memberNickname);

    logger.info("결과값 : " + result);
    if (result != 1) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
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
    if (result != 1) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/profile")
  @ResponseBody
  public ResponseEntity<Void> changeProfilePUT(HttpServletRequest request,
      @RequestBody String memberProfileUrl) {
    int memberId = (int) request.getAttribute("memberId");
    int result = memberService.updateProfileUrl(memberId, memberProfileUrl);

    logger.info("결과값 : " + result);
    if (result != 1) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }


  private String getLoginPageQueryString(String url) {
    if (url.isBlank()) {
      return "";
    }
    return "?targetURL=" + url;
  }

  private String getRedirectURL(String url) {
    if (url.isBlank()) {
      return "/main";
    }
    try {
      url = URLDecoder.decode(url, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return url;
  }

} // end LoginController()


