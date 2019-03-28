package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TAGS")
@SequenceGenerator(name = "tag_seq", sequenceName = "TAG_PK_SEQ", allocationSize = 1)
public class Tag {

	@Id
	@Column(name = "tag_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
	private int tag_id;

	@Column(name = "tag_title")
	private String tag_title;

	@ManyToMany(
			fetch=FetchType.LAZY,
			cascade={
					CascadeType.PERSIST, CascadeType.DETACH,
					CascadeType.MERGE, CascadeType.REFRESH
			})
	@JoinTable(
			name="user_mail",
			joinColumns=@JoinColumn(name="mail_id"),
			inverseJoinColumns=@JoinColumn(name="user_id")
			)

	@Column(name = "tag_twitch_id")
	private int twitch_id;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH })
	@JoinTable(name = "user_mail", joinColumns = @JoinColumn(name = "mail_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> subscribers;

	public Tag(int tag_id, String tag_title, int twitch_id, int ig_id, List<User> subscribers) {
		super();
		this.tag_id = tag_id;
		this.tag_title = tag_title;
		this.twitch_id = twitch_id;
		this.subscribers = subscribers;
	}

	public Tag() {
		super();
	}

	public int getTag_id() {
		return tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}

	public String getTag_title() {
		return tag_title;
	}

	public void setTag_title(String tag_title) {
		this.tag_title = tag_title;
	}

	public int getTwitch_id() {
		return twitch_id;
	}

	public void setTwitch_id(int twitch_id) {
		this.twitch_id = twitch_id;
	}

	public List<User> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<User> subscribers) {
		this.subscribers = subscribers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ig_id;
		result = prime * result + ((subscribers == null) ? 0 : subscribers.hashCode());
		result = prime * result + tag_id;
		result = prime * result + ((tag_title == null) ? 0 : tag_title.hashCode());
		result = prime * result + twitch_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (ig_id != other.ig_id)
			return false;
		if (subscribers == null) {
			if (other.subscribers != null)
				return false;
		} else if (!subscribers.equals(other.subscribers))
			return false;
		if (tag_id != other.tag_id)
			return false;
		if (tag_title == null) {
			if (other.tag_title != null)
				return false;
		} else if (!tag_title.equals(other.tag_title))
			return false;
		if (twitch_id != other.twitch_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tag [tag_id=" + tag_id + ", tag_title=" + tag_title + ", twitch_id=" + twitch_id + ", subscribers="
				+ subscribers + "]";
	}
}
