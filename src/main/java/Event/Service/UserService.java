package Event.Service;

import Event.Models.Role;
import Event.Models.User;
import Event.Repositories.RoleRepository;
import Event.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private MailService mailService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserService(RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	public boolean registrationUser(User user) {
		User userFromDb = userRepository.findByUsername(user.getUsername());

		if (userFromDb != null) {
			return false;
		}

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		Role userRole = roleRepository.findByRole("USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		user.setActivationCode(UUID.randomUUID().toString());

		userRepository.save(user);

		if (!StringUtils.isEmpty(user.getEmail())) {
			String message = String.format(
					"Hello, %s! \n" +
							"Please, visit next link: http://localhost:8090/activate/%s",
					user.getUsername(),
					user.getActivationCode()
			);

			mailService.send(user.getEmail(), "Activation code", message);
		}

		return true;
	}

	public boolean activateUser(String code) {
		User user = userRepository.findByActivationCode(code);

		if (user == null) {
			return false;
		}

		user.setActivationCode(null);

		userRepository.save(user);

		return true;
	}
}

