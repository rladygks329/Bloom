package com.edu.blooming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.edu.blooming.service.PurchaseService;

@RequestMapping(value = "/purchase")
@Controller
public class PurchaseController {
  private Logger logger = LoggerFactory.getLogger(PurchaseController.class);

  @Autowired
  private PurchaseService purchaseService;

  @PostMapping(value = "/{memberId}")
  public String purchasePOST(Model model, @PathVariable("memberId") int memberId) {
    logger.info("Purchase 호출 : memberId : " + memberId);
    int result = purchaseService.purchase(memberId);
    if (result == 0) {
      model.addAttribute("msg", "장바구니가 비어 있어 결제가 이뤄지지 않았습니다.");
      model.addAttribute("url", "list");
      return "alert";
    }
    return "redirect:/lecture/list";
  }

}
