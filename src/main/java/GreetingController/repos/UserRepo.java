package GreetingController.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import GreetingController.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
