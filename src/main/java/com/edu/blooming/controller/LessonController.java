package com.edu.blooming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.edu.blooming.service.LessonService;

@Controller
@RequestMapping(value = "/lesson")
public class LessonController {

  private Logger logger = LoggerFactory.getLogger(LessonController.class);

  @Autowired
  private LessonService lessonService;

  @GetMapping(value = "/upload")
  public void lessonUploadGET() {
    logger.info("lessonUploadGET() 호출");
  }

  @PostMapping
  public String lessonUploadPOST(String lectureTitle, String lecturePrice,
      String lectureThumbnailUrl, String[] lectureVideos) {
    logger.info("lessonUploadPOST() 호출");
    logger.info("lectureTitle : " + lectureTitle + " lecturePrice : " + lecturePrice
        + " lectureThumbnailUrl : " + lectureThumbnailUrl);
    for (String s : lectureVideos) {
      logger.info("videos url : " + s);
    }
    return "lessons";
  }

}
