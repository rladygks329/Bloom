package com.edu.blooming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.edu.blooming.service.LectureService;

@Controller
@RequestMapping(value = "/main")
public class MainController {
  private static final Logger logger = LoggerFactory.getLogger(MainController.class);

  @Autowired
  LectureService lectureService;

  @GetMapping
  public String main(Model model) {
    model.addAttribute("list_hot_like", lectureService.readHotLikeLectures(1, 5));
    model.addAttribute("list_hot_sale", lectureService.readHotSaleLectures(1, 5));
    return "main";
  }



}
