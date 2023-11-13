package com.edu.blooming.persistence;

import java.util.List;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.util.PageCriteria;

public interface BoardDAO {

  int insert(BoardVO vo);

  BoardVO select(int boardId);

  List<BoardVO> select(PageCriteria criteria);

  List<BoardVO> selectByNickname(PageCriteria criteria, String keyword);

  List<BoardVO> selectByTitleOrContent(PageCriteria criteria, String keyword);

  int getTotalCounts();

  int getTotalCountsByTitleOrContent(String keyword);

  int getTotalCountsByNickname(String keyword);

  int updateReplyCount(int boardId, int amount);

  int update(BoardVO vo);

  // BoardVO selectForUpdate(int boardId);

  int updateViewCount(int boardId);

  int updateLikeCount(int boardId, int amount);

  int insertLike(int memberId, int boardId);

  int deleteLike(int memberId, int boardId);

  boolean selectIsMemberLikeBoard(int memberId, int boardId);

  int updateForDelete(BoardVO vo);

  int delete(int boardId);

}
