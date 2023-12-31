package com.edu.blooming.persistence;

import java.util.List;
import com.edu.blooming.domain.BoardReplyVO;

public interface BoardReplyDAO {
  int insert(BoardReplyVO vo);

  int update(int boardReplyId, String replyContent);

  int delete(int boardReplyId);

  int updateForDelete(int boardReplyId);

  BoardReplyVO selectByReplyId(int boardReplyId);

  List<BoardReplyVO> selectByBoardId(int boardId);

  List<BoardReplyVO> selectByMemberId(int memberId);

  int updateCommentCount(int boardReplyId, int amount);
}
