package ImageHoster.service;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    /**
     * Service Method to add Comments to Images
     * @param comment
     */
    public void addComment(Comment comment) {
        comment.setCreatedDate(LocalDate.now());
        commentRepository.addComment(comment);
    }
}