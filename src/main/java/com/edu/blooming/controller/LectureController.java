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
import com.edu.blooming.service.LectureService;
import com.edu.blooming.service.PurchaseService;
import com.edu.blooming.util.PageCriteria;
import com.edu.blooming.util.PageMaker;

@Controller
@RequestMapping(value = "/lecture")
public class LectureController {
  private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

  @Autowired
  private LectureService lectureService;

  @Autowired
  private PurchaseService purchaseService;

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
      pageMaker.setTotalCount(lectureService.getTotalCounts(keyword));
      model.addAttribute("keyword", keyword);
    } else {
      list = lectureService.read(criteria);
      pageMaker.setTotalCount(lectureService.getTotalCounts());
    }
    pageMaker.setPageData();

    model.addAttribute("lectureList", list);
    model.addAttribute("pageMaker", pageMaker);
  }

  @GetMapping("/detail")
  public String lectureDetailGET(HttpServletRequest request, Model model, int lectureId) {
    logger.info("lectureDetailGET() 호출 lectureId : lectureId");

    // TODO: session에서 memberId를 가져오도록 변경
    int memberId = 1;
    Boolean isLike = lectureService.checkIsLike(lectureId, lectureId);
    Boolean isPurchase = purchaseService.checkPurchase(memberId, lectureId);
    model.addAttribute("like", isLike);
    model.addAttribute("purchase", isPurchase);

    // HttpSession session = request.getSession();
    // if (session.getAttribute("") == null) {
    // model.addAttribute("like", false);
    // }else {
    // memberId = (int) session.getAttribute("");
    // Boolean isLike = lectureService.checkIsLike(lectureId, lectureId);
    // model.addAttribute("like", isLike);
    // }

    logger.info("memberId : " + memberId);
    LectureVO vo = lectureService.read(lectureId);

    // 찾는 강의가 없는 경우
    if (vo == null) {
      model.addAttribute("msg", "찾으시는 강의가 존재하지 않습니다.");
      model.addAttribute("url", "list");
      return "alert";
    }
    model.addAttribute("memberId", memberId);
    model.addAttribute("lectureId", lectureId);
    model.addAttribute("lecture", vo);
    return "/lecture/detail";
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

  /// @formatter:off
  // TODO: 모든 속성을 select 하지 않도록 dao 수정 하기
  @PostMapping("/like/{lectureId}/{memberId}")
  public ResponseEntity<Integer> likeLecture(
      @PathVariable("lectureId") int lectureId,
      @PathVariable("memberId") int memberId) {
    lectureService.likeLecture(lectureId, memberId);
    int result = lectureService.read(lectureId).getLectureLikeCount();
    return new ResponseEntity<Integer>(result, HttpStatus.OK);
  }

  @DeleteMapping("/like/{lectureId}/{memberId}")
  public ResponseEntity<Integer> dislikeLecture(
      @PathVariable("lectureId") int lectureId,
      @PathVariable("memberId") int memberId) {
    lectureService.dislikeLecture(lectureId, memberId);
    int result = lectureService.read(lectureId).getLectureLikeCount();
    return new ResponseEntity<Integer>(result, HttpStatus.OK);
  }

}
