package Event.Controller;

import Event.Interfaces.UserRepo;
import Event.Users.Role;
import Event.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "userList";
    }

    //User = Long Id
    @GetMapping("{user}")
    public String userEdit(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "useredit";
    }

    @PostMapping
    public String userSave(@RequestParam("userId") User user, @RequestParam Map<String, String> role, @RequestParam String username, @RequestParam String password) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();
        String pass = user.getPassword();
        for (String key : role.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        if (!password.equals(user.getPassword())) {
            user.setPassword(password);
        }

        userRepo.save(user);
        if (user.getUsername().equals("")) {
            userRepo.delete(user);
        }
        return "redirect:/user";//возвращение
    }
}
