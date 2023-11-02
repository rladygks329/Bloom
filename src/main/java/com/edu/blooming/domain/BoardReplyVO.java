package com.edu.blooming.domain;

import java.util.Date;

public class BoardReplyVO {
  private int boardReplyId;
  private int memberId;
  private int boardId;
  private String boardReplyContent;
  private Date boardReplyDateCreated;
  private String authorName;

  public BoardReplyVO() {
    super();
    // TODO Auto-generated constructor stub
  }

  public BoardReplyVO(int boardReplyId, int memberId, int boardId, String boardReplyContent,
      Date boardReplyDateCreated, String authorName) {
    super();
    this.boardReplyId = boardReplyId;
    this.memberId = memberId;
    this.boardId = boardId;
    this.boardReplyContent = boardReplyContent;
    this.boardReplyDateCreated = boardReplyDateCreated;
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

  public String getBoardReplyContent() {
    return boardReplyContent;
  }

  public void setBoardReplyContent(String boardReplyContent) {
    this.boardReplyContent = boardReplyContent;
  }

  public Date getBoardReplyDateCreated() {
    return boardReplyDateCreated;
  }

  public void setBoardReplyDateCreated(Date boardReplyDateCreated) {
    this.boardReplyDateCreated = boardReplyDateCreated;
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
        + boardId + ", boardReplyContent=" + boardReplyContent + ", boardReplyDateCreated="
        + boardReplyDateCreated + ", authorName=" + authorName + "]";
  }

}
