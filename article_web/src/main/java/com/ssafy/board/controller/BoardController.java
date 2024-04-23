package com.ssafy.board.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.util.PageNavigation;

import jakarta.servlet.ServletContext;

@Controller
@RequestMapping("/article")
public class BoardController {

	private final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private BoardService boardService;

	@GetMapping("/write")
	public String write(@RequestParam Map<String, String> map, Model model) {
		logger.debug("write call parameter {}", map);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "board/write";
	}
	
	@GetMapping("list")
	public ModelAndView list(@RequestParam Map<String, String> map) throws Exception{
		logger.debug("list parameter pgno : {}", map.get("pgno"));
		ModelAndView mav = new ModelAndView();
		List<BoardDto> list = boardService.listArticle(map);
		PageNavigation pageNavigation = boardService.makePageNavigation(map);
		mav.addObject("articles", list);
		mav.addObject("navigation", pageNavigation);
		mav.addObject("pgno", map.get("pgno"));
		mav.addObject("key", map.get("key"));
		mav.addObject("word", map.get("word"));
		mav.setViewName("board/list");
		return mav;
	}

}
