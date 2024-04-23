package com.ssafy.movie.model.service;

import java.util.List;

import com.ssafy.movie.model.dto.MovieDto;

public interface MovieService {
	void writeMovie(MovieDto movieDto) throws Exception;

	List<MovieDto> getMovieList() throws Exception;

	MovieDto getMovie(int id) throws Exception;

	void modifyMovie(MovieDto movieDto) throws Exception;

	void deleteMovie(int id) throws Exception;
}
