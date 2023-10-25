package com.edu.blooming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.persistence.BoardDAO;

@Service
public class BoardServiceImple implements BoardService {

	@Autowired
	private BoardDAO boardDAO;
	
	@Override
	public int create(BoardVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
