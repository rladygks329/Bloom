package com.edu.blooming.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.edu.blooming.payment.KakaoPayment;
import com.edu.blooming.payment.PaymentMethod;
import com.edu.blooming.service.PurchaseService;
import com.edu.blooming.util.Utils;

@RequestMapping("/purchase")
@Controller
public class PurchaseController {
  private Logger logger = LoggerFactory.getLogger(PurchaseController.class);

  private Map<String, PaymentMethod> paymentMap;

  @Autowired
  private PurchaseService purchaseService;

  @Autowired
  public PurchaseController(KakaoPayment kakaoPayment) {
    this.paymentMap = new HashMap<String, PaymentMethod>();
    this.paymentMap.put("kakao", kakaoPayment);
  }

  @PostMapping("/{type}/ready")
  public ResponseEntity<Map<String, Object>> purchaseReady(Model model, HttpServletRequest request,
      @PathVariable("type") String type) {
    int memberId = (int) request.getAttribute("memberId");
    logger.info("purchaseReady 호출 : memberId : " + memberId + " type : " + type);
    PaymentMethod payment = paymentMap.get(type);
    String baseURL = Utils.getBaseUrl(request);
    logger.info("baseURL: " + baseURL);
    Map<String, Object> result = purchaseService.readyForPurchase(payment, baseURL, memberId);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("/{type}/success")
  public String purchaseProcess(Model model, HttpServletRequest request,
      @PathVariable("type") String type) {
    int memberId = (int) request.getAttribute("memberId");
    logger.info("Purchase 호출 : memberId : " + memberId);

    PaymentMethod payment = paymentMap.get(type);
    String token = getToken(request, type);

    Map<String, Object> result = purchaseService.approvePurchase(payment, memberId, token);
    purchaseService.success(memberId);
    logger.info("Purchase 호출 : memberId : " + memberId);
    logger.info("result : " + result.toString());
    return "redirect:/member/mypage";
  }

  private String getToken(HttpServletRequest request, String type) {
    if (type.equals("kakao")) {
      return (String) request.getParameter("pg_token");
    }
    return "";
  }

  @GetMapping("/fail")
  public String failPurchase() {
    return "redirect:/cart";
  }

  @GetMapping("/cancel")
  public String cancelPurchase() {
    return "redirect:/cart";
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<String> emptyCart(Exception e) {
    return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
