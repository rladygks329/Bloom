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
		logger.info("insert() 호출");
		return sqlSession.insert(NAMESPACE + ".insert", vo);
	} // end insert()
	
	@Override
	public int emailCheck(String memberEmail) {
		logger.info("emailCheck() 호출");
		return sqlSession.selectOne(NAMESPACE + ".check_email_duplicated", memberEmail);
	} // end emailCheck()
	
	@Override
	public MemberVO memberLogin(MemberVO vo) {
		logger.info("memberLogin() 호출");
		logger.info(vo.toString());
		return sqlSession.selectOne(NAMESPACE + ".login", vo);
	}

	
	
	

	@Override
	public MemberVO select(String memberEmail) {
		logger.info("select() 호출 : memberId = " + memberEmail);
		return null;
	}

	@Override
	public int update(String memberEmail, MemberVO vo) {
		logger.info("update() 호출 : memberId = " + memberEmail);
		return 0;
	}

	@Override
	public int delete(String memberEmail) {
		logger.info("delete() 호출  memberId = " + memberEmail);
		return 0;
	}







	


	
}



