package com.ssafy.movie.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.movie.model.dto.MovieDto;

@Mapper
public interface MovieMapper {
	void insert(MovieDto movieDto) throws SQLException;

	List<MovieDto> findAll() throws SQLException;

	MovieDto findById(int id) throws SQLException;

	void update(MovieDto movieDto) throws SQLException;

	void delete(int id) throws SQLException;
}
