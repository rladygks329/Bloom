package com.edu.blooming.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.BoardCommentVO;
import com.edu.blooming.persistence.BoardCommentDAO;
import com.edu.blooming.persistence.BoardReplyDAO;

@Service
public class BoardCommentServiceImple implements BoardCommentService {
  private final static Logger logger = LoggerFactory.getLogger(BoardCommentServiceImple.class);

  @Autowired
  private BoardCommentDAO boardCommentDAO;

  @Autowired
  private BoardReplyDAO boardReplyDAO;

  @Transactional(value = "transactionalManager")
  @Override
  public int create(int memberId, BoardCommentVO vo) {
    logger.info("create()호출 : memberId = " + memberId + " vo = " + vo.toString());

    boardCommentDAO.insert(vo);
    boardReplyDAO.updateCommentCount(vo.getBoardReplyId(), 1);
    logger.info("updateCommenCount() 호출 boardReplyId = " + vo.getBoardReplyId());
    return 1;
  }

  @Override
  public int update(int boardCommentId, String boardCommentContent) {
    logger.info("update()호출: boardCommentId = " + boardCommentId + ", boardCommentContent = "
        + boardCommentContent);
    return boardCommentDAO.update(boardCommentId, boardCommentContent);
  }

  @Transactional(value = "transactionalManager")
  @Override
  public int delete(int boardCommentId, int boardReplyId) {
    logger.info("delete()호출: boardCommentId = " + boardCommentId);
    boardCommentDAO.delete(boardCommentId);
    boardReplyDAO.updateCommentCount(boardReplyId, -1);
    return 1;
  }

  @Override
  public List<BoardCommentVO> getComments(int replyId) {
    logger.info("getComments() 호출 : replyId: " + replyId);
    return boardCommentDAO.selectByReplyId(replyId);
  }

}
