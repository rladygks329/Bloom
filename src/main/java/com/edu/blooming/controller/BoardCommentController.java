package com.edu.blooming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  @PostMapping(value = "/{replyId}")
  public ResponseEntity<Integer> createComment(@PathVariable("replyId") int replyId,
      @RequestBody BoardCommentVO vo) {
    logger.info("createComment() 호출: replyId = " + replyId + " vo = " + vo.toString());
    return null;
  }


}


