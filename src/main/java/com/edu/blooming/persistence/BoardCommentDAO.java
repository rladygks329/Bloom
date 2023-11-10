package com.edu.blooming.persistence;

import java.util.List;
import com.edu.blooming.domain.BoardCommentVO;

public interface BoardCommentDAO {
  int insert(BoardCommentVO vo);

  int update(int boardCommentId, String boardCommentContent);

  int delete(int boardCommentId);

  List<BoardCommentVO> selectByReplyId(int replyId);
}
