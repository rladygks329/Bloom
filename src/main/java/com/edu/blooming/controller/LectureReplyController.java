package com.edu.blooming.controller;

import java.util.List;
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
import com.edu.blooming.service.LectureReplyService;

@RestController
@RequestMapping(value = "/lecture/{lectureId}/replies")
public class LectureReplyController {
  private final static Logger logger = LoggerFactory.getLogger(LectureReplyController.class);

  @Autowired
  private LectureReplyService lectureReplyService;

  @GetMapping
  public ResponseEntity<List<LectureReplyVO>> getReplies(@PathVariable("lectureId") int lectureId) {
    List<LectureReplyVO> list = lectureReplyService.getReplies(lectureId);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Integer> createReply(@PathVariable("lectureId") int lectureId,
      @RequestBody LectureReplyVO vo) {

    logger.info("createReply() 호출 lectureId: " + lectureId + " vo :" + vo);
    // TODO: 사용자가 강의을 구매한지 검사한 후, 아닐경우는 not auther 리턴하기
    // TODO: 사용자의 진도를 체크하여, 모든 영상을 다 들었으면 수강평을 작성할 수 있도록 변경하기
    int result = lectureReplyService.create(lectureId, vo);
    HttpStatus status = (result == 1) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(1, status);
  }

  @PutMapping(value = "/{replyId}")
  public ResponseEntity<Integer> updateReply(@PathVariable("lectureId") int lectureId,
      @PathVariable int replyId, @RequestBody LectureReplyVO vo) {
    logger
        .info("updateReply() 호출 lectureId: " + lectureId + " replyId : " + replyId + " vo :" + vo);

    int result = lectureReplyService.update(vo);
    // HttpStatus status = (result == 1) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(1, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{replyId}")
  public ResponseEntity<Integer> deleteReply(@PathVariable("lectureId") int lectureId,
      @PathVariable int replyId) {
    logger.info("updateReply() 호출 lectureId: " + lectureId + "replyId : " + replyId);

    int result = lectureReplyService.delete(replyId);
    logger.info("result : " + result);
    HttpStatus status = (result == 1) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(1, status);
  }

}
// @formatter:off

