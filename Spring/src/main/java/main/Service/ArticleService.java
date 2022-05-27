package main.Service;

import main.Entity.Article;

import java.util.List;


public interface ArticleService {
    Article findByName(String name);
    Article findById(Integer id);
    Article addArticle(Article article);
    Article addArticle(String name);
    void deleteArticleById(Integer id);
    List<Article> findAll();
}
