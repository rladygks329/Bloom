package com.edu.blooming.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.service.BoardService;
import com.edu.blooming.util.PageCriteria;
import com.edu.blooming.util.PageMaker;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
  private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

  @Autowired
  private BoardService boardService;

  @GetMapping("/list")
  public void list(Model model, Integer page, Integer numsPerPage) {
    logger.info("list() 호출");
    logger.info("page = " + page + ", numsPerPage = " + numsPerPage);

    // Paging 처리
    PageCriteria criteria = new PageCriteria();
    if (page != null) {
      criteria.setPage(page);
    }

    if (numsPerPage != null) {
      criteria.setNumsPerPage(numsPerPage);
    }

    List<BoardVO> list = boardService.read(criteria);
    model.addAttribute("list", list);

    PageMaker pageMaker = new PageMaker();
    pageMaker.setCriteria(criteria);
    pageMaker.setTotalCount(boardService.getTotalCounts());
    pageMaker.setPageData();
    model.addAttribute("pageMaker", pageMaker);

  } // end list()

  @GetMapping("/detail")
  public void detail(Model model, Integer boardId, Integer page) {
    logger.info("detail() 호출 : boardId = " + boardId);
    List<BoardVO> list = boardService.read(boardId);
    model.addAttribute("list", list);
    model.addAttribute("page", page);
  }

  @PostMapping("/deleteQuestion")
  public String delete(Integer boardId) {
    logger.info("delete() 호출 : boardId = " + boardId);
    boolean shouldDelete = boardService.checkParentId(boardId);
    if (shouldDelete) {
      boardService.deleteQuestion(boardId);
    } else {
      boardService.updateQuestion(boardId);
    }
    return "redirect:/board/list";
  }



}


