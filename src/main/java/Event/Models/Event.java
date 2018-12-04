package Event.Models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class Event {
	//ENTITY OF EVENT
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Size(max = 2000)
	private String text;
	private String tag;
	private int amountAll;
	private int amount;
	private boolean thereArePlaces;
	private String name;
	private boolean confirm;
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "event_guists", joinColumns = @JoinColumn(name = "event_id"))
	@Column(name = "guists")
	private Set<String> eventGuists = new HashSet<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User author;
	private String filename;

	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "comments", joinColumns = @JoinColumn(name = "event_id"))
	@OneToMany(cascade = {CascadeType.ALL})
	private Set<Comment> comments;

	@ElementCollection(targetClass = Personal.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "personal", joinColumns = @JoinColumn(name = "personal_id"))
	@Enumerated(EnumType.STRING)
	private Set<Personal> artists;
	private Date date;

	public Event() {
	}

	public Event(String text, String tag, User user, int amount, String name, boolean confirm, boolean thereArePlaces) {
		this.author = user;
		this.text = text;
		this.tag = tag;
		this.name = name;
		this.confirm = confirm;
		this.amountAll = amount;
		this.amount = amountAll - eventGuists.size();
		this.thereArePlaces = thereArePlaces;
		this.artists = null;
		this.comments = new HashSet<Comment>();
	}

	public boolean isThereArePlaces() {
		return thereArePlaces;
	}

	public void setThereArePlaces(boolean thereArePlaces) {
		this.thereArePlaces = thereArePlaces;
	}

	public int getAmountAll() {
		return amountAll;
	}

	public void setAmountAll(int amountAll) {
		this.amountAll = amountAll;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Set<String> getEventGuists() {
		return eventGuists;
	}

	public void setEventGuists(Set<String> eventGuists) {
		this.eventGuists = eventGuists;
	}

	public String getAuthorName() {
		return author != null ? author.getUsername() : "<none>";
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Set<Personal> getArtists() {
		return artists;
	}

	public void setArtists(Set<Personal> artists) {
		this.artists = artists;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
}
