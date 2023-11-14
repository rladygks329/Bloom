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

  @Override
  public int delete(int boardReplyId, int boardId) {
    logger.info("delete() 호출 : boardReplyId = " + boardReplyId);

    // 댓글 정보 가져오기
    BoardReplyVO vo = boardReplyDAO.getBoardReply(boardReplyId);

    if (boardReply != null) {
      // 댓글의 boardReplyCommentCount 값이 0일 때
      if (boardReply.getBoardReplyCommentCount() == 0) {
        int resultDelete = boardReplyDAO.delete(boardReplyId);
        logger.info(resultDelete + "행 삭제 성공");
        int result = boardDAO.updateReplyCount(-1, boardId);
        logger.info(result + "행 수정 성공");
      } else {
        // 댓글의 boardReplyCommentCount 값이 0이 아닐 때
        int resultUpdate = boardReplyDAO.updateForDelete(boardReplyId);
        logger.info(resultUpdate + "행 업데이트 성공");
      }

      return 1; // 성공 여부에 따라 적절한 값을 반환
    } else {
      // 댓글이 존재하지 않는 경우 처리
      logger.error("해당 댓글이 존재하지 않습니다.");
      return 0; // 실패 여부에 따라 적절한 값을 반환
    }



    int resultDelete = boardReplyDAO.delete(boardReplyId);
    logger.info(resultDelete + "행 삭제 성공");
    int result = boardDAO.updateReplyCount(-1, boardId);
    logger.info(result + "행 수정 성공");
    return 1;
  }

  @Override
  public List<BoardReplyVO> getReplies(int boardId) {
    logger.info("getReplies() 호출 : boardId: " + boardId);
    return boardReplyDAO.selectByBoardId(boardId);
  }

}


