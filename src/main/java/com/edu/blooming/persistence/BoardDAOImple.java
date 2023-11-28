package com.edu.blooming.persistence;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.util.PageCriteria;

@Repository
public class BoardDAOImple implements BoardDAO {
  private static final Logger logger = LoggerFactory.getLogger(BoardDAOImple.class);
  private static final String NAMESPACE = "com.edu.blooming.BoardMapper";

  @Autowired
  private SqlSession sqlSession;

  @Override
  public int insert(BoardVO vo) {
    logger.info("insert() 호출");
    return sqlSession.insert(NAMESPACE + ".insert", vo);
  }

  @Override
  public List<BoardVO> select(PageCriteria criteria) {
    logger.info("select() 호출: criteria = " + criteria.toString());
    logger.info("start = " + criteria.getStart());
    logger.info("end = " + criteria.getEnd());
    return sqlSession.selectList(NAMESPACE + ".paging", criteria);
  }

  @Override
  public int getTotalCounts() {
    logger.info("getTotalCounts()호출");
    return sqlSession.selectOne(NAMESPACE + ".total_count");
  }

  @Override
  public BoardVO select(int boardId) {
    logger.info("select() 호출: boardId = " + boardId);
    return sqlSession.selectOne(NAMESPACE + ".select_by_board_id", boardId);
  }

  @Override
  public int update(BoardVO vo) {
    logger.info("update() 호출: vo = " + vo.toString());
    return sqlSession.update(NAMESPACE + ".update", vo);
  }

  @Override
  public int updateReplyCount(int boardId, int amount) {
    logger.info("updateReplyCount()호출 : boardId = " + boardId + " amount = " + amount);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("boardId", boardId);
    args.put("amount", amount);

    return sqlSession.update(NAMESPACE + ".update_reply_count", args);
  }

  @Override
  public int updateViewCount(int boardId) {
    logger.info("updateViewCount()호출: boardId = " + boardId);
    return sqlSession.update(NAMESPACE + ".update_view_count", boardId);
  }

  @Override
  public int updateLikeCount(int boardId, int amount) {
    logger.info("updateLikeCount()호출: boardId = " + boardId);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("boardId", boardId);
    args.put("amount", amount);

    return sqlSession.update(NAMESPACE + ".update_like", args);
  }

  @Override
  public int insertLike(int memberId, int boardId) {
    logger.info("insertLike()호출: memberId = " + memberId + " boardId = " + boardId);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("boardId", boardId);

    return sqlSession.insert(NAMESPACE + ".insert_board_like", args);
  }

  @Override
  public int deleteLike(int memberId, int boardId) {
    logger.info("deleteLike()호출: memberId = " + memberId + " boardId = " + boardId);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("boardId", boardId);

    return sqlSession.delete(NAMESPACE + ".delete_board_like", args);
  }

  @Override
  public boolean selectIsMemberLikeBoard(int memberId, int boardId) {
    logger.info("selectIsMemberLikeBoard() 호출: memberId = " + memberId + " boardId = " + boardId);
    HashMap<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("boardId", boardId);

    int result = sqlSession.selectOne(NAMESPACE + ".select_is_member_like_board", args);
    return result == 1;
  }

  @Override
  public List<BoardVO> selectByTitleOrContent(PageCriteria criteria, String keyword) {
    logger.info("select() 호출 : criteria : " + criteria.toString() + " keyword : " + keyword);

    HashMap<String, Object> args = new HashMap<>();
    args.put("start", criteria.getStart());
    args.put("end", criteria.getEnd());
    args.put("keyword", "%" + keyword + "%");

    return sqlSession.selectList(NAMESPACE + ".paging_select_by_title_content", args);
  }

  @Override
  public List<BoardVO> selectByNickname(PageCriteria criteria, String keyword) {
    logger.info("select() 호출 : criteria : " + criteria + " keyword = " + keyword);

    HashMap<String, Object> args = new HashMap<>();
    args.put("start", criteria.getStart());
    args.put("end", criteria.getEnd());
    args.put("keyword", "%" + keyword + "%");

    return sqlSession.selectList(NAMESPACE + ".paging_select_by_nickname", args);
  }

  @Override
  public int getTotalCountsByTitleOrContent(String keyword) {
    logger.info("getTotalCountsByTitleOrContent()호출");
    return sqlSession.selectOne(NAMESPACE + ".total_count_by_title_content", "%" + keyword + "%");
  }

  @Override
  public int getTotalCountsByNickname(String keyword) {
    logger.info("getTotalCountsByMemberId()호출");
    return sqlSession.selectOne(NAMESPACE + ".total_count_by_nickname", "%" + keyword + "%");
  }

  @Override
  public int updateForDelete(BoardVO vo) {
    logger.info("updateForDelete 호출: vo = " + vo);
    return sqlSession.update(NAMESPACE + ".update_for_delete", vo);
  }

  @Override
  public int delete(int boardId) {
    logger.info("delete 호출 : boardId = " + boardId);
    return sqlSession.delete(NAMESPACE + ".delete", boardId);
  }

  @Override
  public List<BoardVO> selectByMemberId(int memberId) {
    logger.info("selectByMemberId 호출 : memberId = " + memberId);
    return sqlSession.selectList(NAMESPACE + ".select_by_member_id", memberId);
  }

  @Override
  public List<BoardVO> selectByMemberIdAndLike(int memberId) {
    logger.info("selectByMemberIdAndLike 호출 : memberId = " + memberId);
    return sqlSession.selectList(NAMESPACE + ".select_by_member_id_and_like", memberId);
  }

}


