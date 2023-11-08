package com.edu.blooming.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edu.blooming.domain.LectureReplyVO;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.service.LectureReplyService;
import com.edu.blooming.service.PurchaseService;


//@formatter:off
@RestController
@RequestMapping(value = "/lecture/{lectureId}/replies")
public class LectureReplyController {
  private final static Logger logger = LoggerFactory.getLogger(LectureReplyController.class);

  @Autowired
  private LectureReplyService lectureReplyService;
  
  @Autowired
  private PurchaseService purchaseService;
  
  @GetMapping
  public ResponseEntity<List<LectureReplyVO>> getReplies(@PathVariable("lectureId") int lectureId) {
    List<LectureReplyVO> list = lectureReplyService.getReplies(lectureId);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> createReply(
      HttpServletRequest request,
      @PathVariable("lectureId") int lectureId,
      @RequestBody LectureReplyVO vo) {
    logger.info("createReply() 호출 lectureId: " + lectureId + " vo :" + vo);
    HttpSession session = request.getSession();
    int memberId = ((MemberVO) session.getAttribute("loginVo")).getMemberId();
    
    if(!purchaseService.checkPurchase(memberId, lectureId)) {
      return new ResponseEntity<>("구매를 한 사람만 댓글을 달 수 있습니다.", HttpStatus.FORBIDDEN);
    }
    
    int result = lectureReplyService.create(lectureId, vo);
    HttpStatus status = (result == 1) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(1, status);
  }

  @PutMapping(value = "/{replyId}")
  public ResponseEntity<Integer> updateReply(
      @PathVariable("lectureId") int lectureId,
      @PathVariable("replyId") int replyId, 
      @RequestBody LectureReplyVO vo) {
    logger
        .info("updateReply() 호출 lectureId: " + lectureId + " replyId : " + replyId + " vo :" + vo);

    int result = lectureReplyService.update(vo);
    return new ResponseEntity<>(1, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{replyId}")
  public ResponseEntity<Integer> deleteReply(
      @PathVariable("lectureId") int lectureId,
      @PathVariable("replyId") int replyId) {
    logger.info("updateReply() 호출 lectureId: " + lectureId + "replyId : " + replyId);

    int result = lectureReplyService.delete(replyId);
    logger.info("result : " + result);
    HttpStatus status = (result == 1) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(1, status);
  }

}

