package com.ssafy.ws.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ssafy.ws.model.dto.Article;
import com.ssafy.ws.model.service.ArticleService;

public class ArticleTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ArticleService articleService = context.getBean("articleService", ArticleService.class);
		int idx = 0;
		for (int i = 0; i < 5; i++) {
			Article article = new Article();
			article.setArticleId(idx++);
			article.setContent("하이용" + i);
			article.setCreatedAt("1999");
			article.setModifiedAt("2000");
			article.setTitle("제목 " + (i * 2));
			article.setUserSeq(1);
			articleService.writeArticle(article);
		}

		for (Article article : articleService.readAll()) {
			System.out.println(article);
		}
	}

}
