package Event.Controllers;

import Event.Models.Comment;
import Event.Repositories.CommentRepository;
import Event.Repositories.UserRepository;
import org.apache.log4j.Logger;
import Event.Models.Event;
import Event.Models.User;
import Event.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;

@Controller
public class EventController {

	public static final Logger log = Logger.getLogger(EventController.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private CommentRepository commentRepository;

	@GetMapping("/")
	public String greeting(Map<String, Object> model) {
		log.fatal("Main page");
		return "greeting";
	}

	@GetMapping("/user/userProfile/{user}")
	public String userProfile(@PathVariable User user, Model model) {
		model.addAttribute("user", user);
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
		Map<User, HashMap<String, Date>> users = new HashMap<>();
		Map<String, Date> ex = new HashMap<>();
		for (Comment s : thisEvent.getComments()) {
			if (!users.containsKey(userRepository.findById((long) Integer.parseInt(s.getUserid())))) {
				ex.clear();
			}
			ex.put(s.getComment_value(), s.getDate());
			users.put(userRepository.findById((long) Integer.parseInt(s.getUserid())), new HashMap<>(ex));

		}


		model.addAttribute("SetAuthor", users);
		return "eventPage";
	}

	@PostMapping("/event/{event}")
	public String eventPage(@PathVariable String event, @AuthenticationPrincipal User user, @RequestParam(required = false) String nameGuist, @RequestParam(required = false) String tag, Map<String, Object> model) {
		int eventid = Integer.parseInt(event);
		Event thisEvent = eventRepository.findById(eventid);
		if (thisEvent.getAmount() <= thisEvent.getAmountAll() && thisEvent.getAmount() != 0) {
			if (user != null) {
				if (!thisEvent.getEventGuists().contains(user.getUsername())) {
					thisEvent.getEventGuists().add(user.getUsername());
				} else if (thisEvent.getEventGuists().contains(user.getUsername())) {
					thisEvent.getEventGuists().remove(user.getUsername());
				}
			} else if (nameGuist != null) {
				if (!thisEvent.getEventGuists().contains(nameGuist)) {
					thisEvent.getEventGuists().add(nameGuist);
				} else if (thisEvent.getEventGuists().contains(nameGuist)) {
					thisEvent.getEventGuists().remove(nameGuist);
				}
			}
		}
		thisEvent.setAmount(thisEvent.getAmountAll() - thisEvent.getEventGuists().size());
		if (thisEvent.getAmount() == 0 || thisEvent.getAmount() < 0) {
			thisEvent.setThereArePlaces(false);
		}
		eventRepository.save(thisEvent);
		model.put("event", thisEvent);
		return "eventPage";

	}

	@PostMapping("/event/{event}/comment")
	public String eventComment(@AuthenticationPrincipal User user, @PathVariable String event, Map<String, Object> model, @RequestParam String text) {
		if (user != null) {
			int eventid = Integer.parseInt(event);
			Event thisEvent = eventRepository.findById(eventid);
			Comment comment = new Comment(thisEvent.getId().toString(), text, user.getId().toString());
			Date date = new Date();
			String dateCreateComment = new SimpleDateFormat("dd-mmm-yyyy hh:mm").format(date);
			comment.setDate(date);
			thisEvent.getComments().add(comment);
			eventRepository.save(thisEvent);
			return "redirect:/event/{event}";
		}
		return "redirect:/login";
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

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> unsortMap) {

		List<Map.Entry<K, V>> list =
				new LinkedList<Map.Entry<K, V>>(unsortMap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;

	}

}

