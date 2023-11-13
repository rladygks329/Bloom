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
import com.edu.blooming.domain.BoardCommentVO;
import com.edu.blooming.service.BoardCommentService;

@RestController
@RequestMapping(value = "/board/comments")
public class BoardCommentController {
  private final static Logger logger = LoggerFactory.getLogger(BoardCommentController.class);

  @Autowired
  private BoardCommentService boardCommentService;

  // 코멘트 입력
  @PostMapping(value = "/{replyId}")
  public ResponseEntity<Integer> createComment(@PathVariable("replyId") int replyId,
      @RequestBody BoardCommentVO vo) {
    logger.info("createComment() 호출: replyId = " + replyId + " vo = " + vo.toString());
    int result = boardCommentService.create(replyId, vo);
    return new ResponseEntity<Integer>(result, HttpStatus.OK);
  }

  // 댓글의 코멘트 가져오기
  @GetMapping(value = "/{replyId}")
  public ResponseEntity<List<BoardCommentVO>> getReplies(@PathVariable("replyId") int replyId) {
    logger.info("getComments() 호출 : replyId = " + replyId);
    List<BoardCommentVO> list = boardCommentService.getComments(replyId);
    return new ResponseEntity<List<BoardCommentVO>>(list, HttpStatus.OK);
  }

  // 코멘트 수정하기
  @PutMapping("/{commentId}")
  public ResponseEntity<Integer> updateComment(@PathVariable("commentId") int commentId,
      @RequestBody String boardCommentContent) {
    int result = boardCommentService.update(commentId, boardCommentContent);
    return new ResponseEntity<Integer>(result, HttpStatus.OK);
  }

  // 코멘트 삭제하기
  @DeleteMapping("/{commentId}")
  public ResponseEntity<Integer> deleteComment(@PathVariable("commentId") int commentId,
      @RequestBody int replyId) {
    logger.info("deleteReply() 호출 replyId: " + replyId + "commentId : " + commentId);

    int result = boardCommentService.delete(commentId, replyId);
    logger.info("result : " + result);
    HttpStatus status = (result == 1) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(1, status);
  }



}


