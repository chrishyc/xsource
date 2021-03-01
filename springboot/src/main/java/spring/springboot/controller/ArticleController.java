package spring.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.springboot.pojo.Article;
import spring.springboot.service.ArticleService;

import java.util.List;

/**
 * @author chris
 */
@Controller
public class ArticleController {

//    @Autowired
    private ArticleService service;
    
    @RequestMapping("/articleList")
    public String queryArticleList(Model model) {
        List<Article> articles = service.queryArticles();
        model.addAttribute("articles", articles);
        return "client/index";
    }
}
