package com.edu.blooming.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.BoardReplyVO;
import com.edu.blooming.persistence.BoardDAO;
import com.edu.blooming.persistence.BoardReplyDAO;

@Service
public class BoardReplyServiceImple implements BoardReplyService {
  private final static Logger logger = LoggerFactory.getLogger(BoardReplyServiceImple.class);

  @Autowired
  private BoardReplyDAO boardReplyDAO;

  @Autowired
  private BoardDAO boardDAO;

  @Transactional(value = "transactionManager")
  @Override
  public int create(int memberId, BoardReplyVO vo) {
    logger.info("create()호출 : memberId = " + memberId + "vo = " + vo.toString());

    boardReplyDAO.insert(vo);
    boardDAO.updateReplyCount(vo.getBoardId(), 1);
    return 1;
  }

  @Override
  public int update(int boardReplyId, String boardReplyContent) {
    logger.info("update()호출: boardReplyId = " + boardReplyId + ", boardReplyContent = "
        + boardReplyContent);
    return boardReplyDAO.update(boardReplyId, boardReplyContent);
  }

  @Transactional(value = "transactionManager")
  @Override
  public int delete(int boardReplyId, int boardId) {
    BoardReplyVO vo = boardReplyDAO.selectByReplyId(boardReplyId);
    int resultDelete = 0;
    if (vo.getBoardReplyCommentCount() == 0) {
      resultDelete = boardReplyDAO.delete(boardReplyId);
    } else {
      resultDelete = boardReplyDAO.updateForDelete(boardReplyId);
    }
    int result = boardDAO.updateReplyCount(boardId, -1);

    logger.info("delete() 호출 : boardReplyId = " + boardReplyId);
    logger.info("대댓글 수 = " + vo.getBoardReplyCommentCount());
    logger.info(resultDelete + "행 삭제 성공");
    logger.info(result + "행 수정 성공");
    return 1;
  }

  @Override
  public List<BoardReplyVO> getReplies(int boardId) {
    logger.info("getReplies() 호출 : boardId: " + boardId);
    return boardReplyDAO.selectByBoardId(boardId);
  }

}


