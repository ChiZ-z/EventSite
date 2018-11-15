package Event.Controllers;

import Event.Repositories.EventRepository;
import Event.Models.Event;
import Event.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AddEventConroller {
	@Autowired
	private EventRepository eventRepository;

	@Value("${upload.path}")
	private String uploadPath;//получаем string место файлов

	@GetMapping("/addEvent")
	public String addEvent(Map<String, Object> model) {
		return "addEvent";
	}

	@PostMapping("/addEvent")
	public String addEvent(@AuthenticationPrincipal User user, @RequestParam String text, @RequestParam String tag,
						   @RequestParam(defaultValue = "0") int amount,@RequestParam String nameEvent, Map<String, Object> model,
						   @RequestParam("file") MultipartFile file
	) throws IOException {
		boolean error=false;
		Pattern p = Pattern.compile("#\\w+");
		Matcher m = p.matcher(tag);
		if(user==null){
			return "login";
		}
		if (nameEvent.isEmpty()){
			model.put("event1", "1");
			error=true;
		}
		if (amount<20||amount>3000){
			model.put("event2", "2");
			error=true;
		}
		if (text.isEmpty()){
			model.put("event3", "3");
			error=true;
		}
		if (!m.matches()){
			model.put("event4", "4");
			error=true;
		}
		if (file.getOriginalFilename().isEmpty()){
			model.put("event5","5");
			error=true;
		}
		if (error==true){
			return "addEvent";
		}
		Event event = new Event(text, tag, user, amount,nameEvent,false,true);
		if(user.isAdmin()){
			event.setConfirm(true);
		}
		if (!file.getOriginalFilename().isEmpty()) {
			String sdafads= nameEvent;
			BufferedImage image = ImageIO.read(file.getInputStream());
			if (image.getWidth() < 340 || image.getHeight() < 450) {
				model.put("event", "incorrect pixel size of image");

				Iterable<Event> messages = eventRepository.findAll();
				model.put("events", messages);
				return "addEvent";
			}

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

		return "addEvent";
	}
}
