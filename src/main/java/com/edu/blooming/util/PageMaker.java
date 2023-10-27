package com.edu.blooming.util;

public class PageMaker {
  private PageCriteria criteria;
  private int totalCount;
  private int numsOfPageLinks;
  private int startPageNo;
  private int endPageNo;
  private boolean hasPrev;
  private boolean hasNext;

  public PageMaker() {
    this.numsOfPageLinks = 3;
  }

  public PageCriteria getCriteria() {
    return criteria;
  }

  public void setCriteria(PageCriteria criteria) {
    this.criteria = criteria;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getNumsOfPageLinks() {
    return numsOfPageLinks;
  }

  public int getStartPageNo() {
    return startPageNo;
  }

  public int getEndPageNo() {
    return endPageNo;
  }

  public boolean isHasPrev() {
    return hasPrev;
  }

  public boolean isHasNext() {
    return hasNext;
  }

  public void setPageData() {
    int totalLinkNo = (int) Math.ceil((double) totalCount / criteria.getNumsPerPage());
    int temp = (int) Math.ceil((double) criteria.getPage() / numsOfPageLinks) * numsOfPageLinks;

    if (temp > totalLinkNo) {
      endPageNo = totalLinkNo;
    } else {
      endPageNo = temp;
    }

    startPageNo = ((endPageNo - 1) / numsOfPageLinks) * numsOfPageLinks + 1;

    hasPrev = startPageNo != 1;
    hasNext = endPageNo * criteria.getNumsPerPage() < totalCount;
  }

} // end PageMaker
