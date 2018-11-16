package pl.aw.iventz.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT c FROM Comment c WHERE c.event.id = ?1")
    List<Comment> findAllByEventId(Long id);
}
