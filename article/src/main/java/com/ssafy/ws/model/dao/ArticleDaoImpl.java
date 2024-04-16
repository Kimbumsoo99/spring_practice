package com.ssafy.ws.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ssafy.ws.model.dto.Article;

@Repository(value = "articleDao")
public class ArticleDaoImpl implements ArticleDao {
	List<Article> list = new ArrayList<>();

	@Override
	public void insert(Article article) {
		list.add(article);
	}

	@Override
	public Article select(int articleId) {
		for (Article article : list) {
			if (article.getArticleId() == articleId)
				return article;
		}
		return null;
	}

	@Override
	public List<Article> selectAll() {
		return list;
	}

	@Override
	public void update(Article article) {
		for (Article art : list) {
			if (art.getArticleId() == article.getArticleId()) {
				art = article;
				break;
			}
		}
	}

	@Override
	public void delete(int articleId) {
		list = list.stream().filter(article -> article.getArticleId() != articleId).collect(Collectors.toList());
	}

	// 스트림보다 더 효율적이라 함.
	/*
	 * @Override public void delete(int articleId) { Iterator<Article> iterator =
	 * list.iterator(); while (iterator.hasNext()) { Article article =
	 * iterator.next(); if (article.getArticleId() == articleId) {
	 * iterator.remove(); break; // 원하는 articleId를 찾았으므로 반복문 종료 } } }
	 */

}
