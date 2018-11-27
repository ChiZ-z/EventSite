package Event.Repositories;

import Event.Models.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
	List<Event> findByDate(Date date);
	List<Event> findByEventGuists(String GuistName);
	List<Event> findByTag(String tag);
	Event findById(int id);
	List<Event> findByConfirm(boolean confirm);
}
