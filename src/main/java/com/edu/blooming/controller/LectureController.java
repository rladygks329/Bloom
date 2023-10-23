package com.edu.blooming.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.service.LectureService;
import com.edu.blooming.util.PageCriteria;
import com.edu.blooming.util.PageMaker;

@Controller
@RequestMapping(value = "/lecture")
public class LectureController {
  private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

  /*
   * 2. search with pagenation 4. lecture/detail 페이지 만들기
   */
  @Autowired
  private LectureService lectureService;

  @GetMapping("/list")
  public void lectureGET(Model model, Integer page, Integer numsPerPage, String keyword) {
    logger.info("lectureGET() 호출");
    PageCriteria criteria = new PageCriteria();
    if (page != null) {
      criteria.setPage(page);
    }

    if (numsPerPage != null) {
      criteria.setNumsPerPage(numsPerPage);
    }

    
    
    List<LectureVO> list;
    PageMaker pageMaker = new PageMaker();
    pageMaker.setCriteria(criteria);
    
    if (keyword != null) {
      list = lectureService.read(criteria, keyword);
      pageMaker.setTotalCount(lectureService.get)
      
    } else {
      pageMaker.setTotalCount(lectureService.getTotalCounts());
      list = lectureService.read(criteria);
    }
    pageMaker.setPageData();

    model.addAttribute("lectureList", list);
    model.addAttribute("pageMaker", pageMaker);
    model.addAttribute("keyword", keyword);
  }

  @GetMapping("/detail")
  public void lectureDetailGET() {
    logger.info("lectureDetailGET() 호출");
  }

  @GetMapping("/upload")
  public void lectureUploadGET() {
    logger.info("lectureUploadGET() 호출");
  }

  @PostMapping("/upload")
  public String lectureUploadPOST() {
    logger.info("lectureUploadPOST() 호출");
    return "redirect:/blooming/lecture/list";
  }

  public void readByKeyword() {

  }

  public void read() {

  }
}
