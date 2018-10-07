package GreetingController.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import GreetingController.domain.Message;

public interface MessageRepo extends CrudRepository<Message, Long> {
	List<Message> findByTag(String tag);
}