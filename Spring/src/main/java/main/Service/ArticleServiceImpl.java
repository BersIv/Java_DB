package main.Service;

import main.Entity.Article;
import main.Exceptions.Duplicate;
import main.Exceptions.NotFound;
import main.Exceptions.StillInUse;
import main.Repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article addArticle(Article article) {
        if (articleRepository.existsByName(article.getName())) {
            throw new Duplicate("Article already exist");
        } else {
           return articleRepository.save(new Article(article));
        }
    }

    @Override
    public Article addArticle(String name) {
        if (articleRepository.existsByName(name)) {
            throw new Duplicate("Article already exist");
        } else {
            return articleRepository.save(new Article(name));
        }
    }

    @Override
    public void deleteArticleById(Integer id) {
        if (articleRepository.existsById(id)) {
            if (articleRepository.numberOfOperations(id) != 0){
                throw new StillInUse("Article is still in use");
            }
            articleRepository.deleteById(id);
        } else {
            throw new NotFound("Article not found");
        }
    }

    @Override
    public List<Article> findAll() {
        return (List<Article>) articleRepository.findAll();
    }

    @Override
    public Article findById(Integer id) {
        if(!articleRepository.existsById(id)){
            throw new NotFound("No article found by id");
        }
        return articleRepository.findArticleById(id);
    }

    @Override
    public Article findByName(String name) {
        if(!articleRepository.existsByName(name)){
            throw new NotFound("No article found by name");
        }
        return articleRepository.findArticleByName(name);
    }

}
