package com.edu.blooming.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.edu.blooming.util.FileUploadUtil;
import com.edu.blooming.util.MediaUtil;

@Controller
@RequestMapping(value = "/image")
public class ImageController {
  private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

  @Value("${uploadPath.img}")
  private String uploadPath;

  @PostMapping
  public ResponseEntity<String> uploadAjaxActionPost(@RequestParam("file") MultipartFile[] files) {
    logger.info("uploadAjaxActionPost 호출");

    // 파일 하나만 저장
    String result = null; // result : 파일 경로 및 썸네일 이미지 이름

    try {
      result = FileUploadUtil.saveUploadedImage(uploadPath, files[0].getOriginalFilename(),
          files[0].getBytes());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return new ResponseEntity<String>(result, HttpStatus.OK);
  } // end uploadAjaxActionPost()

  @GetMapping("/display")
  public ResponseEntity<byte[]> display(String fileName) {
    logger.info("display() 호출");

    ResponseEntity<byte[]> entity = null;
    InputStream in = null;

    String filePath = uploadPath + File.separator + fileName;

    try {
      in = new FileInputStream(filePath);

      // 파일 확장자
      String extension = filePath.substring(filePath.lastIndexOf(".") + 1);
      logger.info(extension);

      // 응답 헤더(response header)에 Content-Type 설정
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.setContentType(MediaUtil.getMediaType(extension));
      // 데이터 전송
      entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), // 파일에서 읽은 데이터
          httpHeaders, // 응답 헤더
          HttpStatus.OK);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return entity;
  }

} // end ImageController


