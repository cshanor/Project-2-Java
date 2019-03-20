package com.revature.models;

import java.util.ArrayList;
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
@Table(name="TAGS")
@SequenceGenerator(name="tag_seq", sequenceName="TAG_PK_SEQ", allocationSize=1)
public class Tag {
	
	@Id
	@Column(name="tag_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tag_seq")
	private int tag_id;
	
	@Column(name="tag_name")
	private String tag_name;
	
	@ManyToMany(
			fetch=FetchType.LAZY,
			cascade={
					CascadeType.PERSIST, CascadeType.DETACH,
					CascadeType.MERGE, CascadeType.REFRESH
			})
	@JoinTable(
			name="user_tags",
			joinColumns=@JoinColumn(name="tag_id"),
			inverseJoinColumns=@JoinColumn(name="user_id")
			)
	private List<User> subscribers;
	
	public void addSub(User subscriber) {
		if(subscribers == null) subscribers = new ArrayList<>();
		subscribers.add(subscriber);
	}

	public Tag(int tag_id, String tag_name, List<User> subscribers) {
		super();
		this.tag_id = tag_id;
		this.tag_name = tag_name;
		this.subscribers = subscribers;
	}
	
	public int getTag_id() {
		return tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public List<User> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<User> subscribers) {
		this.subscribers = subscribers;
	}

	public Tag(String tag_name) {
		super();
		this.tag_name = tag_name;
	}

	public Tag() {
		super();
	}
	
	

}
