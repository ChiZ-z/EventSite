package Event.Controllers;

import org.apache.log4j.Logger;
import Event.Models.Event;
import Event.Models.User;
import Event.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@Controller
public class EventController {

	public static final Logger log = Logger.getLogger(EventController.class);

	@Autowired
	private EventRepository eventRepository;

	@GetMapping("/")
	public String greeting(Map<String, Object> model) {
		log.fatal("Main page");
		return "greeting";
	}

	@GetMapping("/user/userProfile/{user}")
	public String userProfile(@PathVariable User user, Model model) {
		return "userProfile";
	}

	@GetMapping("/event")
	public String event(@RequestParam(required = false, defaultValue = "") String filter, Model model, @RequestParam(required = false, defaultValue = "") String conf, @RequestParam(required = false, defaultValue = "") String all) {
		Iterable<Event> messages;
		if (filter != null && !filter.isEmpty()) {
			messages = eventRepository.findByTag(filter);
		} else {
			messages = eventRepository.findAll();
		}
		if (conf.equals("true")) {
			messages = eventRepository.findByConfirm(false);
		}
		if (all.equals("false")) {
			messages = eventRepository.findAll();
		}
		model.addAttribute("events", messages);
		model.addAttribute("filter", filter);
		return "event";
	}


	@GetMapping("/event/{event}")
	public String eventPage(@PathVariable String event, Model model) {
		int eventid = Integer.parseInt(event);
		Event thisEvent = eventRepository.findById(eventid);
		model.addAttribute("event", thisEvent);
		return "eventPage";
	}

	@PostMapping("/event/{event}")
	public String eventPage(@PathVariable String event, @AuthenticationPrincipal User user, @RequestParam(required = false) String nameGuist, @RequestParam(required = false) String tag, Map<String, Object> model) {
		int eventid = Integer.parseInt(event);
		Event thisEvent = eventRepository.findById(eventid);
		if (thisEvent.getAmount() <= thisEvent.getAmountAll()&& thisEvent.getAmount()!=0) {
			if (user != null) {
				if (!thisEvent.getEventGuists().contains(user.getUsername())){
					thisEvent.getEventGuists().add(user.getUsername());
				}
			}else if (nameGuist!=null){
				if (!thisEvent.getEventGuists().contains(nameGuist)){
					thisEvent.getEventGuists().add(nameGuist);
				}
			}
		}
		thisEvent.setAmount(thisEvent.getAmountAll()-thisEvent.getEventGuists().size());
		if (thisEvent.getAmount()==0||thisEvent.getAmount()<0){
			thisEvent.setThereArePlaces(false);
		}
		eventRepository.save(thisEvent);
		model.put("event", thisEvent);
		return "eventPage";

	}

	@PostMapping("/events/{event}")
	public String eventPage2(@PathVariable String event, @RequestParam(required = false) String guistName, @RequestParam(required = false) String tag, Map<String, Object> model) {


		return "redirect:/event/{event}";

	}

	@PostMapping("/events/{event}/confirm")
	public String eventConfirm(@PathVariable String event, @AuthenticationPrincipal User user, @RequestParam(required = false) String guistName, @RequestParam(required = false) String tag, Map<String, Object> model) {
		if (user.isAdmin()) {
			int eventid = Integer.parseInt(event);
			Event eventConfirmTrue = eventRepository.findById(eventid);
			if (!eventConfirmTrue.isConfirm()) {
				eventConfirmTrue.setConfirm(true);
			}
			eventRepository.save(eventConfirmTrue);
		}
		return "redirect:/event/{event}";

	}

}

