package Event.Repositories;

import Event.Models.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {

	List<Event> findByTag(String tag);
	Event findById(int id);
	List<Event> findByConfirm(boolean confirm);
}
