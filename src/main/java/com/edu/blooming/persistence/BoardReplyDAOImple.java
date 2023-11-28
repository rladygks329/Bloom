package com.edu.blooming.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.edu.blooming.domain.BoardReplyVO;

@Repository
public class BoardReplyDAOImple implements BoardReplyDAO {

  private static final Logger logger = LoggerFactory.getLogger(BoardReplyDAOImple.class);
  private static final String NAMESPACE = "com.edu.blooming.BoardReplyMapper";

  @Autowired
  private SqlSession sqlSession;

  @Override
  public int insert(BoardReplyVO vo) {
    logger.info("insert() 호출: vo = " + vo.toString());
    return sqlSession.insert(NAMESPACE + ".insert", vo);
  }

  @Override
  public int update(int boardReplyId, String boardReplyContent) {
    logger.info("update() 호출");
    logger.info("boardReplyId = " + boardReplyId + ", boardReplyContent = " + boardReplyContent);

    Map<String, Object> args = new HashMap<String, Object>();
    args.put("boardReplyId", boardReplyId);
    args.put("boardReplyContent", boardReplyContent);

    return sqlSession.update(NAMESPACE + ".update", args);
  }

  @Override
  public int delete(int boardReplyId) {
    logger.info("delete 호출 : boardReplyId = " + boardReplyId);
    return sqlSession.delete(NAMESPACE + ".delete", boardReplyId);
  }

  @Override
  public List<BoardReplyVO> selectByBoardId(int boardId) {
    logger.info("selectByBoardId() 호출 : boardId = " + boardId);
    return sqlSession.selectList(NAMESPACE + ".select_by_board_id", boardId);
  }

  @Override
  public int updateCommentCount(int boardReplyId, int amount) {
    logger.info("updateCommentCount() 호출 : boardReplyId = " + boardReplyId + " amount = " + amount);
    Map<String, Integer> args = new HashMap<>();
    args.put("boardReplyId", boardReplyId);
    args.put("amount", amount);
    return sqlSession.update(NAMESPACE + ".update_comment_count", args);
  }

  @Override
  public BoardReplyVO selectByReplyId(int boardReplyId) {
    logger.info("selectByReplyId() 호출 : boardReplyId = " + boardReplyId);
    return sqlSession.selectOne(NAMESPACE + ".select_by_reply_id", boardReplyId);
  }

  @Override
  public int updateForDelete(int boardReplyId) {
    logger.info("updateForDelete() 호출 : boardReplyId = " + boardReplyId);
    return sqlSession.update(NAMESPACE + ".update_for_delete", boardReplyId);
  }

  @Override
  public List<BoardReplyVO> selectByMemberId(int memberId) {
    logger.info("selectByMemberId 호출 : memberId = " + memberId);
    return sqlSession.selectList(NAMESPACE + ".select_by_member_id", memberId);
  }

}


