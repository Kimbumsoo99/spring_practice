package com.ssafy.ws.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ws.model.dao.ArticleDao;
import com.ssafy.ws.model.dto.Article;

@Service(value = "articleService")
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	ArticleDao articleDao;

	@Override
	public void writeArticle(Article article) {
		articleDao.insert(article);
	}

	@Override
	public Article readArticle(int articleSeq) {
		return articleDao.select(articleSeq);
	}

	@Override
	public void modifyArticle(Article article) {
		articleDao.update(article);
	}

	@Override
	public void deleteArticle(int articleSeq) {
		articleDao.delete(articleSeq);
	}

	@Override
	public List<Article> readAll() {
		return articleDao.selectAll();
	}
}
