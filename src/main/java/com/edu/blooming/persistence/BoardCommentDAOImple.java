package com.edu.blooming.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.edu.blooming.domain.BoardCommentVO;

@Repository
public class BoardCommentDAOImple implements BoardCommentDAO {

  private static final Logger logger = LoggerFactory.getLogger(BoardCommentDAO.class);
  private static final String NAMESPACE = "com.edu.blooming.BoardCommentMapper";

  @Autowired
  private SqlSession sqlSession;

  @Override
  public int insert(BoardCommentVO vo) {
    logger.info("insert() 호출: vo = " + vo.toString());
    return sqlSession.insert(NAMESPACE + ".insert", vo);
  }

  @Override
  public int update(int boardCommentId, String boardCommentContent) {
    logger.info(
        "boardCommentId = " + boardCommentId + ", boardReplyContent = " + boardCommentContent);
    Map<String, Object> args = new HashMap<String, Object>();
    args.put("boardCommentId", boardCommentId);
    args.put("boardCommentContent", boardCommentContent);
    return sqlSession.update(NAMESPACE + ".update", args);
  }

  @Override
  public int delete(int boardCommentId) {
    logger.info("delete() 호출: boardCommentId = " + boardCommentId);
    return sqlSession.delete(NAMESPACE + ".delete", boardCommentId);
  }

  @Override
  public List<BoardCommentVO> selectByReplyId(int replyId) {
    logger.info("selectByReplyId() 호출 : boardId = " + replyId);
    return sqlSession.selectList(NAMESPACE + ".select_by_reply_id", replyId);
  }

}
