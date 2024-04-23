package com.ssafy.movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.movie.model.dto.MovieDto;
import com.ssafy.movie.model.service.MovieService;

@Controller
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	MovieService movieService;

	@GetMapping("/list")
	public ModelAndView movieList() throws Exception {
		ModelAndView mav = new ModelAndView();
		List<MovieDto> list = movieService.getMovieList();
		mav.addObject("movies", list);
		mav.setViewName("movie/list");
		return mav;
	}

	@GetMapping("/{id}")
	public ModelAndView getMovie(@PathVariable("id") int id) throws Exception {
		MovieDto movieDto = movieService.getMovie(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("movie", movieDto);
		mav.setViewName("movie/view");
		return mav;
	}

	@GetMapping("/write")
	public String writeForm() {
		return "movie/write";
	}

	@PostMapping("/write")
	public String writeMovie(@ModelAttribute MovieDto movieDto) throws Exception {
		movieService.writeMovie(movieDto);
		return "redirect:/movie/list";
	}

	@GetMapping("/{id}/modify")
	public ModelAndView modifyMovie(@PathVariable("id") int id) throws Exception {
		ModelAndView mav = new ModelAndView();
		MovieDto movieDto = movieService.getMovie(id);
		mav.addObject("movie", movieDto);
		mav.setViewName("movie/modify");
		return mav;
	}

	@PostMapping("/{id}/modify")
	public String modify(@ModelAttribute MovieDto movieDto) throws Exception {
		movieService.modifyMovie(movieDto);
		return "redirect:/movie/list";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable("id") int id) throws Exception {
		movieService.deleteMovie(id);
		return "redirect:/movie/list";
	}

}
