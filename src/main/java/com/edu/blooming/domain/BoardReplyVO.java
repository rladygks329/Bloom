package com.edu.blooming.domain;

public class BoardReplyVO {
  private int boardReplyId;
  private int memberId;
  private int boardId;
  private String boardeReplyContent;
  private String authorName;

  public BoardReplyVO() {
    super();
    // TODO Auto-generated constructor stub
  }

  public BoardReplyVO(int boardReplyId, int memberId, int boardId, String boardeReplyContent,
      String authorName) {
    super();
    this.boardReplyId = boardReplyId;
    this.memberId = memberId;
    this.boardId = boardId;
    this.boardeReplyContent = boardeReplyContent;
    this.authorName = authorName;
  }

  public int getBoardReplyId() {
    return boardReplyId;
  }

  public void setBoardReplyId(int boardReplyId) {
    this.boardReplyId = boardReplyId;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public int getBoardId() {
    return boardId;
  }

  public void setBoardId(int boardId) {
    this.boardId = boardId;
  }

  public String getBoardeReplyContent() {
    return boardeReplyContent;
  }

  public void setBoardeReplyContent(String boardeReplyContent) {
    this.boardeReplyContent = boardeReplyContent;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  @Override
  public String toString() {
    return "BoardReplyVO [boardReplyId=" + boardReplyId + ", memberId=" + memberId + ", boardId="
        + boardId + ", boardeReplyContent=" + boardeReplyContent + ", authorName=" + authorName
        + "]";
  }

}
