package com.edu.blooming.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
  private static final Logger logger = LoggerFactory.getLogger(CartController.class);

  @Autowired
  private CartService cartService;

  @GetMapping
  public String getCartPage(Model model, HttpServletRequest request) {
    logger.info("getCartPage() 호출");

    HttpSession session = request.getSession();

    int memberId = ((MemberVO) session.getAttribute("loginVo")).getMemberId();
    model.addAttribute("memberId", memberId);
    logger.info("memberId : " + memberId);
    return "/cart/list";
  }

  @GetMapping(value = "/item/{memberId}")
  public ResponseEntity<List<LectureVO>> getCartItems(@PathVariable("memberId") int memberId) {
    List<LectureVO> list = cartService.getItems(memberId);
    return new ResponseEntity<List<LectureVO>>(list, HttpStatus.OK);
  }

  /// @formatter:off
  @PostMapping("/item/{memberId}/{lectureId}")
  public ResponseEntity<Void> addCart(
      @PathVariable("lectureId") int lectureId,
      @PathVariable("memberId") int memberId) {
    logger.info("addCart() 추가 memberId: "+ memberId + " lectureId " + lectureId);
    try {
      cartService.add(memberId, lectureId);
    } catch (Exception e) {
      logger.info("insert 중 문제 발생");
      throw new IllegalStateException("추가 할 수 없습니다.");
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @DeleteMapping("/item/{memberId}/{lectureId}")
  public ResponseEntity<Void> removeCart(  
      @PathVariable("memberId") int memberId,
      @PathVariable("lectureId") int lectureId) {
    logger.info("removeCart() 추가 memberId: "+ memberId + " lectureId " + lectureId);
    try {
      cartService.remove(memberId, lectureId);
    } catch (Exception e) {
      logger.info("delete 중 문제 발생");
      throw new IllegalStateException("삭제할수 없습니다.");
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
