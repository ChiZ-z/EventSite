package Event.Controllers;

import Event.Models.Comment;
import Event.Models.Event;
import Event.Models.Personal;
import Event.Models.User;
import Event.Repositories.CommentRepository;
import Event.Repositories.EventRepository;
import Event.Repositories.UserRepository;
import Event.Service.MailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EventController {

	public static final Logger log = Logger.getLogger(EventController.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private MailService mailService;

	@GetMapping("/")
	public String greeting(Map<String, Object> model) {
		log.fatal("Main page");
		return "greeting";
	}

	@GetMapping("/event/{event}/edit")
	public String edit(@PathVariable String event, Map<String, Object> model) {
		int eventid = Integer.parseInt(event);
		Event thisEvent = eventRepository.findById(eventid);
		model.put("artists", Personal.values());
		model.put("event", thisEvent);
		return "editEvent";
	}

	@GetMapping("/user/userProfile/{user}")
	public String userProfile(@PathVariable User user, Model model) {
		model.addAttribute("user", user);
		return "userProfile";
	}

	@GetMapping("/event")
	public String event(@AuthenticationPrincipal User user,@RequestParam(required = false, defaultValue = "") String filter, Model model, @RequestParam(required = false, defaultValue = "") String conf,
						@RequestParam(required = false, defaultValue = "") String all, @RequestParam(required = false, defaultValue = "") String I,
						@RequestParam(required = false) String date) throws ParseException {
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
		if (user!=null){
			if (I.equals("true")) {
				messages = eventRepository.findByEventGuists(user.getUsername());
			}
		}
		if (date != null && date != "") {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			messages = eventRepository.findByDate(date1);
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
		List<Comment> empList = new ArrayList<>();
		for (Comment s : thisEvent.getComments()) {

			empList.add(s);
		}
		Collections.sort(empList, new Comparator<Comment>() {
			@Override
			public int compare(Comment emp1, Comment emp2) {
				if (emp1.getDate() == emp2.getDate())
					return 0;
				else if (emp1.getDate().before(emp2.getDate()))
					return -1;
				else //if(emp1.getId() >emp2.getId())
					return 1;
			}
		});

		model.addAttribute("SetAuthor", empList);
		return "eventPage";
	}


	@PostMapping("/event/{event}")
	public String eventPage(@PathVariable String event, @AuthenticationPrincipal User user, @RequestParam(required = false) String nameGuist, @RequestParam(required = false) String tag, Map<String, Object> model) {
		int eventid = Integer.parseInt(event);
		Event thisEvent = eventRepository.findById(eventid);
		if (thisEvent.getEventGuists().contains(nameGuist) && nameGuist != null) {
			thisEvent.getEventGuists().remove(nameGuist);
		} else if (user != null) {
			if (thisEvent.getEventGuists().contains(user.getUsername())) {
				thisEvent.getEventGuists().remove(user.getUsername());
			} else if (!thisEvent.getEventGuists().contains(user.getUsername())) {
				thisEvent.getEventGuists().add(user.getUsername());
				if (!StringUtils.isEmpty(user.getEmail())) {
					String message = String.format(
							"Hello, %s! \n" +
									"You have registered for this event on the Date: %s",
							user.getUsername(),
							thisEvent.getDate()
					);
					mailService.send(user.getEmail(), "\n" +
							"Enjoy your time", message);
				}

			}
		} else if (thisEvent.getAmount() <= thisEvent.getAmountAll() && thisEvent.getAmount() != 0) {
			if (user != null) {
				if (!thisEvent.getEventGuists().contains(user.getUsername())) {
					thisEvent.getEventGuists().add(user.getUsername());
					if (!StringUtils.isEmpty(user.getEmail())) {
						String message = String.format(
								"Hello, %s! \n" +
										"You have registered for this event on the Date: %s",
								user.getUsername(),
								thisEvent.getDate()
						);
						mailService.send(user.getEmail(), "\n" +
								"Enjoy your time", message);
					}
				}
			} else if (nameGuist != null) {
				if (!thisEvent.getEventGuists().contains(nameGuist)) {
					thisEvent.getEventGuists().add(nameGuist);
				}
			}
		}
		thisEvent.setAmount(thisEvent.getAmountAll() - thisEvent.getEventGuists().size());
		if (thisEvent.getAmount() == 0 || thisEvent.getAmount() < 0) {
			thisEvent.setThereArePlaces(false);
		} else {
			thisEvent.setThereArePlaces(true);
		}
		eventRepository.save(thisEvent);
		model.put("event", thisEvent);
		return "redirect:/event/{event}";

	}

	@PostMapping("/event/{event}/comment")
	public String eventComment(@AuthenticationPrincipal User user, @PathVariable String event, Map<String, Object> model, @RequestParam String text) {
		if (user != null) {
			int eventid = Integer.parseInt(event);
			Event thisEvent = eventRepository.findById(eventid);
			Comment comment = new Comment(thisEvent.getId().toString(), text, user);
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

	@PostMapping("/event/{event}/edit")
	public String editEvent(@AuthenticationPrincipal User user, @PathVariable String event, @RequestParam String tag,
							@RequestParam(defaultValue = "0") int amount, @RequestParam String nameEvent, @RequestParam Map<String, String> form) {
		int eventid = Integer.parseInt(event);
		Event thisEvent = eventRepository.findById(eventid);
		if (form.containsKey("First")) {
			thisEvent.getArtists().add(Personal.First);
		} else {
			if (thisEvent.getArtists().contains(Personal.First)) {
				thisEvent.getArtists().remove(Personal.First);
			}
		}
		if (form.containsKey("Second")) {
			thisEvent.getArtists().add(Personal.Second);
		} else {
			if (thisEvent.getArtists().contains(Personal.Second)) {
				thisEvent.getArtists().remove(Personal.Second);
			}
		}
		if (form.containsKey("Third")) {
			thisEvent.getArtists().add(Personal.Third);
		} else {
			if (thisEvent.getArtists().contains(Personal.Third)) {
				thisEvent.getArtists().remove(Personal.Third);
			}
		}
		if (!form.get("text").equals(thisEvent.getText())) {
			thisEvent.setText(form.get("text"));
		}
		if (!form.get("tag").equals(thisEvent.getTag())) {
			thisEvent.setTag(form.get("tag"));
		}
		if (!form.get("nameEvent").equals(thisEvent.getName())) {
			thisEvent.setName(form.get("nameEvent"));
		}
		if (!form.get("amount").equals(Integer.toString(thisEvent.getAmountAll()))) {
			thisEvent.setAmountAll(Integer.parseInt(form.get("amount")));
		}
		eventRepository.save(thisEvent);
		return "redirect:/event/{event}";
	}
}

