package com.edu.blooming.persistence;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.blooming.domain.MemberVO;

@Repository
public class MemberDAOImple implements MemberDAO {
	
	private static final Logger logger =
			LoggerFactory.getLogger(MemberDAOImple.class);
	
	private static final String NAMESPACE = 
			"com.edu.blooming.MemberMapper";

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int insert(MemberVO vo) {
		logger.info("insert() ȣ��");
		return sqlSession.insert(NAMESPACE + ".insert", vo);
	}

	@Override
	public MemberVO select(String memberEmail) {
		logger.info("select() ȣ�� : memberId = " + memberEmail);
		return null;
	}

	@Override
	public int update(String memberEmail, MemberVO vo) {
		logger.info("update() ȣ�� : memberId = " + memberEmail);
		return 0;
	}

	@Override
	public int delete(String memberEmail) {
		logger.info("delete() ȣ��  memberId = " + memberEmail);
		return 0;
	}

	@Override
	public String select(String memberEmail, String memberPassword) {
		logger.info("select() ȣ�� : memberEmail, memberPassword = " + memberEmail + ", " + memberPassword);
		return null;
	}
	


	
}




