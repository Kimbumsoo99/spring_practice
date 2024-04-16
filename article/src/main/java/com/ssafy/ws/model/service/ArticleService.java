package com.ssafy.ws.model.service;

import java.util.List;

import com.ssafy.ws.model.dto.Article;

public interface ArticleService {
	public void writeArticle(Article article);

	public Article readArticle(int articleSeq);

	public void modifyArticle(Article article);

	public void deleteArticle(int articleSeq);

	public List<Article> readAll();
}
