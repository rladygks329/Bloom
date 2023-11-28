package com.edu.blooming.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
    int memberId = (int) request.getAttribute("memberId");
    model.addAttribute("memberId", memberId);
    return "/cart/list";
  }

  @GetMapping(value = "/item")
  public ResponseEntity<List<LectureVO>> getCartItems(HttpServletRequest request) {
    int memberId = (int) request.getAttribute("memberId");
    List<LectureVO> list = cartService.getItems(memberId);
    return new ResponseEntity<List<LectureVO>>(list, HttpStatus.OK);
  }

  // @formatter:off
  @PostMapping("/item/{lectureId}")
  public ResponseEntity<Void> addCart(
      HttpServletRequest request,
      @PathVariable("lectureId") int lectureId
  ) {
    int memberId = (int) request.getAttribute("memberId");
    logger.info("addCart() 추가 memberId: "+ memberId + " lectureId " + lectureId);
    cartService.add(memberId, lectureId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @DeleteMapping("/item/{lectureId}")
  public ResponseEntity<Void> removeCart(
      HttpServletRequest request,
      @PathVariable("lectureId") int lectureId
  ) {
    int memberId = (int) request.getAttribute("memberId");
    logger.info("removeCart() 추가 memberId: "+ memberId + " lectureId " + lectureId);
    cartService.remove(memberId, lectureId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  
}
