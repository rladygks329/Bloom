package com.edu.blooming.controller;

import java.util.Map;
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
  public ResponseEntity<Map<String, Object>> getReplies(
      HttpServletRequest request, 
      @PathVariable("lectureId") int lectureId, 
      int lastReplyId, 
      int pageSize
  ) {
    int memberId = -1;
    HttpSession session = request.getSession();
    if (session.getAttribute("loginVo") != null) {
      memberId = ((MemberVO) session.getAttribute("loginVo")).getMemberId();
    }
    Map<String, Object> result = lectureReplyService.getReplies(lectureId, memberId, pageSize, lastReplyId);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
  
  @PostMapping
  public ResponseEntity<Object> createReply(
      HttpSession session,
      @PathVariable("lectureId") int lectureId,
      @RequestBody LectureReplyVO vo
  ) {
    logger.info("createReply() 호출 lectureId: " + lectureId + " vo :" + vo);
    if(session.getAttribute("loginVo") == null) {
      return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED); 
    }
    
    int memberId = ((MemberVO) session.getAttribute("loginVo")).getMemberId();
    if(!purchaseService.checkPurchase(memberId, lectureId)) {
      return new ResponseEntity<>("구매를 한 사람만 댓글을 달 수 있습니다.", HttpStatus.FORBIDDEN);
    }
    
    int result = lectureReplyService.create(lectureId, vo);
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @PutMapping(value = "/{replyId}")
  public ResponseEntity<Void> updateReply(
      @PathVariable("lectureId") int lectureId,
      @PathVariable("replyId") int replyId, 
      @RequestBody LectureReplyVO vo
  ) {
    logger.info("updateReply() 호출 lectureId: " + lectureId + " replyId : " + replyId + " vo :" + vo);
    int result = lectureReplyService.update(vo);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(value = "/{replyId}")
  public ResponseEntity<Void> deleteReply(
      @PathVariable("lectureId") int lectureId,
      @PathVariable("replyId") int replyId
  ) {
    logger.info("updateReply() 호출 lectureId: " + lectureId + "replyId : " + replyId);
    int result = lectureReplyService.delete(replyId);
    logger.info("result : " + result);
    HttpStatus status = (result == 1) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(status);
  }

}

