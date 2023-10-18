-- 강의 목록
CREATE TABLE LECTURE
(
    LECTURE_ID NUMBER PRIMARY KEY,
    MEMBER_ID NUMBER NOT NULL, -- 강사 ID
    LECTURE_TITLE VARCHAR2(50 CHAR) NOT NULL,
    LECTURE_PRICE NUMBER NOT NULL,
    LECTURE_SALES_COUNT NUMBER DEFAULT(0) NOT NULL,
    LECTURE_TOTAL_SCORE NUMBER DEFAULT(0) NOT NULL,
    LECTURE_REPLY_COUNT NUMBER DEFAULT(0) NOT NULL,
    LECTURE_LIKE_COUNT NUMBER DEFAULT(0) NOT NULL,
    LECTURE_THUMBNAIL_URL VARCHAR2(200 CHAR) NOT NULL,
    LECTURE_DATE_CREATED TIMESTAMP DEFAULT(SYSDATE) NOT NULL
);

CREATE SEQUENCE LECTURE_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 100000
    NOCYCLE
    NOCACHE
    NOORDER;
    
-- 강의 동영상
CREATE TABLE LESSON
(
    LESSON_ID NUMBER PRIMARY KEY,
    LECTURE_ID NUMBER NOT NULL,
    LESSON_NAME VARCHAR2(30 CHAR),
    LESSON_URL VARCHAR2(100 CHAR)
);

CREATE SEQUENCE LESSON_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 100000
    NOCYCLE
    NOCACHE
    NOORDER;

CREATE TABLE LECTURE_LIKE
(
    LECTURE_ID NUMBER NOT NULL,
    MEMBER_ID NUMBER NOT NULL
);

CREATE TABLE CART
(
    MEMBER_ID NUMBER NOT NULL,
    LECTURE_ID NUMBER NOT NULL
);

 CREATE TABLE PURCHASE
 (
    LECTURE_ID NUMBER NOT NULL,
    MEMBER_ID NUMBER NOT NULL,
    PURCHASE_DATE_CREATED TIMESTAMP DEFAULT(SYSDATE) NOT NULL,
    PURCHASE_PRICE NUMBER NOT NULL
 );

 CREATE TABLE LECTURE_REPLY
 (
    LECTURE_REPLY_ID NUMBER PRIMARY KEY,
    MEMBER_ID NUMBER NOT NULL,
    LECTURE_ID NUMBER NOT NULL,
    LECTURE_REPLY_CONTENT VARCHAR2(200 CHAR) NOT NULL,
    LECTURE_REPLY_SCORE NUMBER NOT NULL
 );

 CREATE SEQUENCE LECTURE_REPLY_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 100000
    NOCYCLE
    NOCACHE
    NOORDER;