package Event.Controller;

import Event.Requests.Event;
import Event.Users.User;
import Event.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @Value("${upload.path}")
    private String uploadPath;//получаем string место файлов

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/event")
    public String event(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Event> messages = eventRepository.findAll();
        if (filter != null && !filter.isEmpty()) {
            messages = eventRepository.findByTag(filter);
        } else {
            messages = eventRepository.findAll();
        }
        model.addAttribute("events", messages);
        model.addAttribute("filter", filter);
        return "event";
    }

    @PostMapping("/event")
    public String add(@AuthenticationPrincipal User user, @RequestParam String text, @RequestParam String tag, Map<String, Object> model,
                      @RequestParam("file") MultipartFile file
    ) throws IOException {

        Event event = new Event(text, tag, user);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String uploadedFile = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + uploadedFile));
            event.setFilename(uploadedFile);
        }

        eventRepository.save(event);
        Iterable<Event> messages = eventRepository.findAll();
        model.put("events", messages);

        return "event";
    }


}

