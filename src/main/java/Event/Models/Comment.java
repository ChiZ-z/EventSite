package Event.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "commentAll")
public class Comment implements Comparable<Comment>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "comment_id")
	private int id;
	private String eventid;
	private String comment_value;
	@OneToOne
	private User userid;
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Comment() {

	}

	public Comment(String eventid, String comment_value, User userid) {
		this.eventid = eventid;
		this.comment_value = comment_value;
		this.userid = userid;
	}

	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getComment_value() {
		return comment_value;
	}

	public void setComment_value(String comment_value) {
		this.comment_value = comment_value;
	}

	public User getUserid() {
		return userid;
	}

	public void setUserid(User userid) {
		this.userid = userid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(Comment com) {
		if(this.getDate()==com.getDate()) return 0;
		else if(this.getDate().before(com.getDate())) return 1;
		else return -1;
	}
}