package com.edu.blooming.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.blooming.domain.BoardCommentVO;
import com.edu.blooming.persistence.BoardCommentDAO;
import com.edu.blooming.persistence.BoardDAO;

@Service
public class BoardCommentServiceImple implements BoardCommentService {
  private final static Logger logger = LoggerFactory.getLogger(BoardCommentServiceImple.class);

  @Autowired
  private BoardCommentDAO boardCommentDAO;

  @Autowired
  private BoardDAO boardDAO;

  @Override
  public int create(int memberId, BoardCommentVO vo) {
    logger.info("create()호출 : memberId = " + memberId + " vo = " + vo.toString());
    boardCommentDAO.insert(vo);
    return 1;
  }

  @Override
  public int update(int boardCommentId, String boardCommentContent) {
    logger.info("update()호출: boardCommentId = " + boardCommentId + ", boardCommentContent = "
        + boardCommentContent);
    return boardCommentDAO.update(boardCommentId, boardCommentContent);
  }

  @Override
  public int delete(int boardCommentId, int replyId) {
    logger.info("update()호출: boardCommentId = " + boardCommentId);
    return boardCommentDAO.delete(boardCommentId);
  }

  @Override
  public List<BoardCommentVO> getComments(int replyId) {
    logger.info("getReplies() 호출 : boardId: " + replyId);
    return boardCommentDAO.selectByReplyId(replyId);
  }

}
