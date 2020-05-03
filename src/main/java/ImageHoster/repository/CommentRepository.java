package ImageHoster.repository;

import ImageHoster.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class CommentRepository {
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    public Comment createComment(Comment newComment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(newComment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }

        return newComment;
    }

}
