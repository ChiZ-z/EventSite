package Event.Repositories;

import Event.Models.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	List<Comment> findByEventid(int id);
}
