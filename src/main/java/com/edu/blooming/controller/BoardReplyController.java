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
import com.edu.blooming.domain.BoardReplyVO;
import com.edu.blooming.service.BoardReplyService;

@RestController
@RequestMapping(value = "/board/replies")
public class BoardReplyController {
  private final static Logger logger = LoggerFactory.getLogger(BoardReplyController.class);

  @Autowired
  private BoardReplyService boardReplyService;

  // 댓글 입력하기
  @PostMapping(value = "/{boardId}")
  public ResponseEntity<Integer> createReply(@PathVariable("boardId") int boardId,
      @RequestBody BoardReplyVO vo) {

    logger.info("createReply() 호출 : boardId = " + boardId + " vo = " + vo);

    int result = boardReplyService.create(boardId, vo);
    return new ResponseEntity<Integer>(result, HttpStatus.OK);
  }

  // 게시글의 댓글 가져오기
  @GetMapping(value = "/{boardId}")
  public ResponseEntity<List<BoardReplyVO>> getReplies(@PathVariable("boardId") int boardId) {
    logger.info("getReplies() 호출 : boardId = " + boardId);
    List<BoardReplyVO> list = boardReplyService.getReplies(boardId);
    return new ResponseEntity<List<BoardReplyVO>>(list, HttpStatus.OK);
  }

  // 댓글 수정하기
  @PutMapping("/{replyId}")
  public ResponseEntity<Integer> updateReply(@PathVariable("replyId") int replyId,
      @RequestBody String boardReplyContent) {
    int result = boardReplyService.update(replyId, boardReplyContent);
    return new ResponseEntity<Integer>(result, HttpStatus.OK);
  }

  // 댓글 삭제하기
  @DeleteMapping("/{replyId}")
  public ResponseEntity<Integer> deleteReply(@PathVariable("replyId") int replyId,
      @RequestBody int boardId) {
    logger.info("deleteReply() 호출 boardId: " + boardId + "boardReplyId : " + replyId);

    int result = boardReplyService.delete(replyId, boardId);
    logger.info("result : " + result);
    HttpStatus status = (result == 1) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(1, status);
  }


}


