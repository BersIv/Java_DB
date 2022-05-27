package main.Repositories;

import main.Entity.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
    boolean existsByName(String name);
    Article findArticleById(Integer id);
    Article findArticleByName(String name);

    @Query(value = "SELECT count(*) FROM Articles A JOIN Operations O on O.ARTICLE_ID = A.ID WHERE A.ID = :id", nativeQuery = true)
    Integer numberOfOperations(@Param("id") Integer id);

}
