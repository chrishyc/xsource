package spring.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.springboot.pojo.Comment;
import spring.springboot.service.CommentService;

import java.util.Calendar;

/**
 * @author chris
 */
@RestController
public class DemoController {

//    @Autowired
    private CommentService commentService;
    
    @RequestMapping(value = "/springboot", produces = "application/json; charset=utf-8")
    public String sayHello() {
        return "hello spring Boot,---111222221--qqq==jjj-11111-";
    }
    
    @RequestMapping("/toLoginPage")
    public String toLoginPage(Model model) {
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        return "login";
    }
    @RequestMapping("/findCommentById")
    public Comment findCommentById(Integer id){
        Comment comment = commentService.findCommentById(id);
        return  comment;
        
    }
    
    @RequestMapping("/updateComment")
    public Comment updateComment(Comment comment){
        Comment commentById = commentService.findCommentById(comment.getId());
        commentById.setAuthor(comment.getAuthor());
        Comment comment1 = commentService.updateComment(commentById);
        return comment1;
    }
    
    @RequestMapping("/deleteComment")
    public void deleteComment(Integer id){
        commentService.deleteComment(id);
    }
}
