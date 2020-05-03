package ImageHoster.service;

import ImageHoster.model.Comment;
import ImageHoster.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    // Making use of Constructor based Dependency Injection here.
    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    // This method creates new comment for the current Image
    public void createNewComment(Comment comment) {
        commentRepository.createComment(comment);
    }

}
