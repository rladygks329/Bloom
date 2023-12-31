package com.edu.blooming.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.persistence.BoardDAO;
import com.edu.blooming.util.PageCriteria;

@Service
public class BoardServiceImple implements BoardService {
  private static final Logger logger = LoggerFactory.getLogger(BoardServiceImple.class);
  @Autowired
  private BoardDAO boardDAO;

  public static final String OPTION_NICKNAME = "searchNickname";

  @Override
  public int create(BoardVO vo) {
    logger.info("create() 호출 : vo = " + vo.toString());
    return boardDAO.insert(vo);
  }

  @Override
  public List<BoardVO> read(PageCriteria criteria, String option, String keyword) {
    logger.info("read() 호출 option : " + option + " keyword : " + keyword);
    logger.info("start = " + criteria.getStart());
    logger.info("end = " + criteria.getEnd());

    if (option == null) {
      return boardDAO.select(criteria);
    }

    if (option.equals(OPTION_NICKNAME)) {
      return boardDAO.selectByNickname(criteria, keyword);
    }

    return boardDAO.selectByTitleOrContent(criteria, keyword);
  }

  @Override
  public int getTotalCounts(String option, String keyword) {
    logger.info("getTotalCounts() 호출" + option + "keyword : " + keyword);
    if (option == null) {
      return boardDAO.getTotalCounts();
    }

    if (option.equals(OPTION_NICKNAME)) {
      return boardDAO.getTotalCountsByNickname(keyword);
    }

    return boardDAO.getTotalCountsByTitleOrContent(keyword);
  }

  @Override
  public BoardVO read(int boardId) {
    logger.info("read() 호출: boardId = " + boardId);
    return boardDAO.select(boardId);
  }

  @Override
  public int update(BoardVO vo) {
    logger.info("update() 호출: vo = " + vo.toString());
    return boardDAO.update(vo);
  }

  @Override
  public int updateViewCount(int boardId) {
    logger.info("updateViewCount()호출: boardId = " + boardId);
    return boardDAO.updateViewCount(boardId);
  }

  @Transactional(value = "transactionManager")
  @Override
  public int likeBoard(int boardId, int memberId) {
    logger.info("likeBoard() 호출, boardId : " + boardId + " memberId : " + memberId);
    boardDAO.updateLikeCount(boardId, 1);
    return boardDAO.insertLike(memberId, boardId);
  }

  @Transactional(value = "transactionManager")
  @Override
  public int dislikeBoard(int boardId, int memberId) {
    logger.info("dislikeBoard() 호출, boardId : " + boardId + " memberId : " + memberId);
    boardDAO.updateLikeCount(boardId, -1);
    return boardDAO.deleteLike(memberId, boardId);
  }

  @Override
  public boolean checkIsLike(int memberId, int boardId) {
    logger.info("checkIsLike() 호출");
    return boardDAO.selectIsMemberLikeBoard(memberId, boardId);
  }

  @Override
  public int delete(BoardVO vo) {
    logger.info("deleteOrUpdate() 호출 : boardId = " + vo.getBoardId() + " boardReplyCount = "
        + vo.getBoardReplyCount());
    if (vo.getBoardReplyCount() != 0) {
      logger.info("updateForDelete() 호출");
      return boardDAO.updateForDelete(vo);
    }
    logger.info("delete() 호출");
    return boardDAO.delete(vo.getBoardId());
  }

  @Override
  public List<BoardVO> readByMemberId(int memberId) {
    logger.info("readByMemberId() 호출 = " + memberId);
    return boardDAO.selectByMemberId(memberId);
  }

  @Override
  public List<BoardVO> readByMemberIdAndLIke(int memberId) {
    logger.info("readByMemberIdAndLike() 호출 = " + memberId);
    return boardDAO.selectByMemberIdAndLike(memberId);
  }

}


