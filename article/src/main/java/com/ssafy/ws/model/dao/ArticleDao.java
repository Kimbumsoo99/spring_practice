package com.ssafy.ws.model.dao;

import java.util.List;

import com.ssafy.ws.model.dto.Article;

public interface ArticleDao {
	public void insert(Article article);

	public Article select(int articleId);

	public List<Article> selectAll();

	public void update(Article article);

	public void delete(int articleId);
}
