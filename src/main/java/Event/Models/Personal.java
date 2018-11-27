package Event.Models;

import javax.persistence.Table;

@Table
public enum Personal {
	First, Second, Third;
	public String getArt() {
		return name();
	}
}
