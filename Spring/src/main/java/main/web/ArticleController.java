package main.web;

import main.Entity.Article;
import main.Exceptions.Duplicate;
import main.Exceptions.NotFound;
import main.Exceptions.StillInUse;
import main.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "/get")
    public ResponseEntity<List<Article>> getArticles() {
        return new ResponseEntity<>(articleService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/getById-{id}")
    public ResponseEntity<Article> getByName(@PathVariable("id") Integer id) {
        try {
            return new ResponseEntity<>(articleService.findById(id), HttpStatus.OK);
        } catch (NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/getByName-{name}")
    public ResponseEntity<Article> getByName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(articleService.findByName(name), HttpStatus.OK);
        } catch (NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        try {
            return new ResponseEntity<>(articleService.addArticle(article),HttpStatus.OK);
        } catch (Duplicate e) {
            throw new ResponseStatusException(HttpStatus.IM_USED, e.getMessage());
        }
    }

    @PostMapping(value = "/add-{name}")
    public ResponseEntity<Article> addArticle(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(articleService.addArticle(name), HttpStatus.OK);
        } catch (Duplicate e) {
            throw new ResponseStatusException(HttpStatus.IM_USED, e.getMessage());
        }
    }

    @DeleteMapping(value = "/deleteById-{id}")
    public String deleteById(@PathVariable("id") Integer id) {
        try {
            articleService.deleteArticleById(id);
        } catch (StillInUse e) {
            throw new ResponseStatusException(HttpStatus.IM_USED, e.getMessage());
        }
        catch (NotFound e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "Article deleted";
    }
}