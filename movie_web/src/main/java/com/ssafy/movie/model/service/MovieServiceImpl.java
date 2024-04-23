package com.ssafy.movie.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.movie.model.dto.MovieDto;
import com.ssafy.movie.model.mapper.MovieMapper;

@Service
public class MovieServiceImpl implements MovieService{
	@Autowired
	MovieMapper movieMapper;
	
	@Override
	public void writeMovie(MovieDto movieDto) throws Exception {
		movieMapper.insert(movieDto);
		
	}

	@Override
	public List<MovieDto> getMovieList() throws Exception {
		return movieMapper.findAll();
	}

	@Override
	public MovieDto getMovie(int id) throws Exception {
		// TODO Auto-generated method stub
		return movieMapper.findById(id);
	}

	@Override
	public void modifyMovie(MovieDto movieDto) throws Exception {
		// TODO Auto-generated method stub
		movieMapper.update(movieDto);
	}

	@Override
	public void deleteMovie(int id) throws Exception {
		// TODO Auto-generated method stub
		movieMapper.delete(id);
	}
	
}
