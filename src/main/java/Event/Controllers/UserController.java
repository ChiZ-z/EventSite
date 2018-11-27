package Event.Controllers;

import Event.Models.Role;
import Event.Repositories.RoleRepository;
import Event.Repositories.UserRepository;
import Event.Models.User;
import Event.Service.MailService;
import Event.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MailService mailService;

	@GetMapping
	public String userList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "userList";
	}

	//User = Long Id
	@GetMapping("{user}")
	public String userEdit(@PathVariable User user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("roles", roleRepository.findAll());
		return "useredit";
	}

	@PostMapping
	public String userSave(@RequestParam("userId") User user, @RequestParam Map<String, String> model, @RequestParam String username, @RequestParam String email) {
		user.setUsername(username);
		if (model.containsValue("USER")) {
			if (!user.getRoles().contains("USER")) {
				Role userRole = roleRepository.findByRole("USER");
				user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
			}
		}
		if (model.containsValue("ADMIN")) {
			if (!user.getRoles().contains("ADMIN")) {
				Role userRole = roleRepository.findByRole("ADMIN");
				user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
			}
		}
		if (email != null) {

			String message = String.format(
					"Hello, %s! \n" +
							"Please, visit next link: http://localhost:8090/activate/%s",
					user.getUsername(),
					user.getActivationCode()
			);
			user.setEmail(email);
			mailService.send(user.getEmail(), "Activation code", message);

		}
		userRepository.save(user);
		return "redirect:/user";//возвращение
	}
}
