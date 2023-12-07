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
import org.springframework.web.bind.annotation.ExceptionHandler;
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
  public String loginGET(HttpServletRequest request) {
    logger.info("loginGET() 호출");
    HttpSession session = request.getSession();
    if (session.getAttribute("loginVo") != null) {
      logger.info("로그인되지 않은 상태 - mypage-identify로 리다이렉트");
      return "redirect:/main";
    }
    return "/member/login";
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

  @GetMapping("/mypage-update")
  public String myPageUpdateGET(HttpServletRequest request) {
    logger.info("myPageUpdateGET() 호출");

    HttpSession session = request.getSession();
    if (session.getAttribute("loginVo") == null) {
      logger.info("로그인되지 않은 상태 - mypage-identify로 리다이렉트");
      return "redirect:/member/mypage-identify";
    }

    return "/member/mypage-update";
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
  public ResponseEntity<Void> changeNicknamePUT(HttpServletRequest request,
      @RequestBody String memberNickname) {
    int memberId = (int) request.getAttribute("memberId");
    int result = memberService.updateNickname(memberId, memberNickname);

    logger.info("결과값 : " + result);
    if (result != 1) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // 회원 정보 업데이트 후 세션 업데이트
    HttpSession session = request.getSession();
    MemberVO loginVo = (MemberVO) session.getAttribute("loginVo");

    if (loginVo != null) {
      // 기존 세션에서 가져온 loginVo가 null이 아닌 경우에만 업데이트 수행
      loginVo.setMemberNickname(memberNickname);
      session.setAttribute("loginVo", loginVo);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/introduce")
  @ResponseBody
  public ResponseEntity<Void> changeIntroducePUT(@RequestBody String memberIntroduce,
      HttpServletRequest request) {
    int memberId = (int) request.getAttribute("memberId");
    logger.info(memberIntroduce);
    int result = memberService.updateIntroduce(memberId, memberIntroduce);

    logger.info("결과값 : " + result);
    if (result != 1) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // 회원 정보 업데이트 후 세션 업데이트
    HttpSession session = request.getSession();
    MemberVO loginVo = (MemberVO) session.getAttribute("loginVo");

    if (loginVo != null) {
      // 기존 세션에서 가져온 loginVo가 null이 아닌 경우에만 업데이트 수행
      loginVo.setMemberIntroduce(memberIntroduce);
      session.setAttribute("loginVo", loginVo);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/profile")
  @ResponseBody
  public ResponseEntity<Void> changeProfilePUT(HttpServletRequest request,
      @RequestBody String memberProfileUrl) {
    logger.info(memberProfileUrl);
    int memberId = (int) request.getAttribute("memberId");

    int result;
    if (memberProfileUrl == null || memberProfileUrl.equals("null")) {
      // 프로필 사진을 삭제하는 경우
      logger.info("프로필 사진을 삭제하는 경우");
      result = memberService.deleteProfileUrl(memberId);
    } else {
      // 프로필 사진을 업데이트하는 경우
      logger.info("프로필 사진을 업데이트하는 경우");
      result = memberService.updateProfileUrl(memberId, memberProfileUrl);
    }

    logger.info("결과값 : " + result);
    if (result != 1) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // 회원 정보 업데이트 후 세션 업데이트
    HttpSession session = request.getSession();
    MemberVO loginVo = (MemberVO) session.getAttribute("loginVo");

    if (loginVo != null) {
      // 기존 세션에서 가져온 loginVo가 null이 아닌 경우에만 업데이트 수행
      loginVo.setMemberProfileUrl(memberProfileUrl);
      session.setAttribute("loginVo", loginVo);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/mypage-identify")
  public void mypageIdentifyGET() {
    logger.info("mypageIdentifyGET() 호출");
  }

  @PostMapping("/identify")
  public String mypageIdentifyPOST(HttpServletRequest request, MemberVO vo, String targetURL,
      RedirectAttributes rttr) {
    logger.info("mypageIdentifyPOST() 호출");
    MemberVO loginVo = memberService.login(vo);

    if (loginVo == null) {
      // Handle the case when login is not successful
      String queryString = getLoginPageQueryString(targetURL);
      logger.info("redirect:/member/mypage-identify" + queryString);
      rttr.addFlashAttribute("errorMessage", "비밀번호를 확인해 주세요.");
      return "redirect:/member/mypage-identify" + queryString;
    }
    return "redirect:/member/mypage-update";

  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<String> duplicatedNickname(Exception e) {
    return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
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


