package spring.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.springboot.pojo.Comment;
import spring.springboot.service.ApiCommentService;

@RestController
@RequestMapping("api")
public class ApiCommentController {

//    @Autowired
    private ApiCommentService commentService;


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
