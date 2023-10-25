package com.edu.blooming.persistence;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.blooming.domain.BoardVO;

@Repository
public class BoardDAOImple implements BoardDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int insert(BoardVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
