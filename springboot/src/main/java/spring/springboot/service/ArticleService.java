package spring.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.springboot.pojo.Article;
import spring.springboot.repository.ArticleRepository;

import java.util.List;

/**
 * @author chris
 */
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository repository;
    
    public List<Article> queryArticles() {
        return repository.findAll();
    }
}
