package com.edu.blooming.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.persistence.MemberDAO;

@Service
public class MemberServiceImple implements MemberService {
	private static final Logger logger = 
			LoggerFactory.getLogger(MemberServiceImple.class);
	@Autowired
	private MemberDAO dao;
	

	
	@Override
	public int create(MemberVO vo) {
		logger.info("create()»£√‚: vo = " + vo.toString());
		return dao.insert(vo);
	} // end create()

	
	
} // end MemberService








