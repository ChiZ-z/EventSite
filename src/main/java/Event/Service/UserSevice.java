package Event.Service;

import Event.Repositories.RoleRepository;
import Event.Repositories.UserRepository;
import Event.Users.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSevice implements UserDetailsService {
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public UserSevice(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserRepository.findByUsername(username);
    }
    public Role loadByRole(String role) throws UsernameNotFoundException {
        return roleRepository.findByRole(role);
    }
}
