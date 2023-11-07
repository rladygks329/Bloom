package com.edu.blooming.persistence;

import java.util.List;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.util.PageCriteria;

public interface BoardDAO {

  int insert(BoardVO vo);

  List<BoardVO> select(int boardId);

  List<BoardVO> select(PageCriteria criteria);

  int getTotalCounts();

  int updateReplyCount(int boardId, int amount);

  // boolean checkParentId(int boardId);
  //
  // int deleteQuestion(int boardId);

  int update(BoardVO vo);

  BoardVO selectForUpdate(int boardId);

  int updateViewCount(int boardId);

  int updateLikeCount(int boardId, int amount);

  int insertLike(int memberId, int boardId);

  int deleteLike(int memberId, int boardId);

  boolean selectIsMemberLikeBoard(int memberId, int boardId);
}
