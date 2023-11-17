package com.edu.blooming.domain;

public class MemberVO {
  private int memberId;
  private String memberEmail;
  private String memberPassword;
  private String memberName;
  private String memberNickname;
  private String memberPhone;
  private String memberAddress;
  private String memberLevel;
  private String memberIntroduce;
  private String memberProfileUrl;

  public MemberVO() {}

  public MemberVO(int memberId, String memberEmail, String memberPassword, String memberName,
      String memberNickname, String memberPhone, String memberAddress, String memberLevel,
      String memberIntroduce, String memberProfileUrl) {
    this.memberId = memberId;
    this.memberEmail = memberEmail;
    this.memberPassword = memberPassword;
    this.memberName = memberName;
    this.memberName = memberNickname;
    this.memberPhone = memberPhone;
    this.memberAddress = memberAddress;
    this.memberLevel = memberLevel;
    this.memberIntroduce = memberIntroduce;
    this.memberProfileUrl = memberProfileUrl;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public String getMemberEmail() {
    return memberEmail;
  }

  public void setMemberEmail(String memberEmail) {
    this.memberEmail = memberEmail;
  }

  public String getMemberPassword() {
    return memberPassword;
  }

  public void setMemberPassword(String memberPassword) {
    this.memberPassword = memberPassword;
  }

  public String getMemberName() {
    return memberName;
  }

  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }

  public String getMemberPhone() {
    return memberPhone;
  }

  public void setMemberPhone(String memberPhone) {
    this.memberPhone = memberPhone;
  }

  public String getMemberAddress() {
    return memberAddress;
  }

  public void setMemberAddress(String memberAddress) {
    this.memberAddress = memberAddress;
  }

  public String getMemberLevel() {
    return memberLevel;
  }

  public void setMemberLevel(String memberLevel) {
    this.memberLevel = memberLevel;
  }

  public String getMemberIntroduce() {
    return memberIntroduce;
  }

  public void setMemberIntroduce(String memberIntroduce) {
    this.memberIntroduce = memberIntroduce;
  }

  public String getMemberProfileUrl() {
    return memberProfileUrl;
  }

  public void setMemberProfileUrl(String memberProfileUrl) {
    this.memberProfileUrl = memberProfileUrl;
  }

  public String getMemberNickname() {
    return memberNickname;
  }

  public void setMemberNickname(String memberNickname) {
    this.memberNickname = memberNickname;
  }

  @Override
  public String toString() {
    return "MemberVO [memberId=" + memberId + ", memberEmail=" + memberEmail + ", memberPassword="
        + memberPassword + ", memberName=" + memberName + ", memberNickname=" + memberNickname
        + ", memberPhone=" + memberPhone + ", memberAddress=" + memberAddress + ", memberLevel="
        + memberLevel + ", memberIntroduce=" + memberIntroduce + ", memberProfileUrl="
        + memberProfileUrl + "]";
  }

}
