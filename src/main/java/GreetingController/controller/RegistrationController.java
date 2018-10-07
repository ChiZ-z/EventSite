package GreetingController.controller;

import java.util.Collections;
import java.util.Map;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import GreetingController.domain.Role;
import GreetingController.domain.User;
import GreetingController.repos.UserRepo;

@Controller
public class RegistrationController {
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}
	
	@PostMapping("/registration")
	public String addUser(User user, Map<String,Object> model) {
		User userFromdb = userRepo.findByUsername(user.getUsername());
		
		if(userFromdb!=null) {
			model.put("message", "User exists!");
			return "registration";
		}
		
		user.setActive(true);
		user.setRoles(Collections.singleton(Role.USER));
		userRepo.save(user);
		return "redirect:/login";
	}

}
