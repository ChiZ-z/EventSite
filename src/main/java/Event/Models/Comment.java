package Event.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "commentAll")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "comment_id")
	private int id;
	private String eventid;
	private String comment_value;
	private String userid;
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Comment() {

	}

	public Comment(String eventid, String comment_value, String userid) {
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}