package com.edu.blooming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/video")
public class VideoController {
  private final static Logger logger = LoggerFactory.getLogger(VideoController.class);

  @javax.annotation.Resource(name = "uploadVideoPath")
  private String uploadPath;

}
