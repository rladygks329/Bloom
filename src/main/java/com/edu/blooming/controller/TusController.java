package com.edu.blooming.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.edu.blooming.service.TusService;

@Controller
public class TusController {
  private Logger logger = LoggerFactory.getLogger(TusController.class);
  @Autowired
  private TusService tusService;

  // 업로드 페이지
  @GetMapping("/tus")
  public String tusUploadPage() {
    return "tus";
  }

  // 업로드 엔드포인트
  @ResponseBody
  @RequestMapping(value = {"/tus/upload", "/tus/upload/**"})
  public ResponseEntity<String> tusUpload(HttpServletRequest request,
      HttpServletResponse response) {
    logger.info("tus-upload 호출");
    return ResponseEntity.ok(tusService.tusUpload(request, response));
  }

}

