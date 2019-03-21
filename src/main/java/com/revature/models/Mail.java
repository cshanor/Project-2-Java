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
@Table(name="MAIL")
@SequenceGenerator(name="mail_seq", sequenceName="MAIL_PK_SEQ", allocationSize=1)
public class Mail {
	
	@Id
	@Column(name="mail_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="mail_seq")
	private int mail_id;
	
	@Column(name="mail_description")
	private String mail_desc;
	
	@Column(name="mail_status")
	private String mail_status;
	

	@Column(name="mail_time")
	private String mail_time;
	
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
	private List<User> participants;
	
	public void addParticipant(User participant) {
		if(participants == null) participants = new ArrayList<>();
		participants.add(participant);
	}
	
	public Mail() {
		super();
	}

	public Mail(int mail_id, String mail_desc, List<User> participants) {
		super();
		this.mail_id = mail_id;
		this.mail_desc = mail_desc;
		this.participants = participants;
	}

	public Mail(String mail_desc, List<User> participants) {
		super();
		this.mail_desc = mail_desc;
		this.participants = participants;
	}

	public Mail(String mail_desc) {
		super();
		this.mail_desc = mail_desc;
	}

	public int getMail_id() {
		return mail_id;
	}

	public void setMail_id(int mail_id) {
		this.mail_id = mail_id;
	}

	public String getMail_desc() {
		return mail_desc;
	}

	public void setMail_desc(String mail_desc) {
		this.mail_desc = mail_desc;
	}

	public List<User> getParticipants() {
		return participants;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mail_desc == null) ? 0 : mail_desc.hashCode());
		result = prime * result + mail_id;
		result = prime * result + ((participants == null) ? 0 : participants.hashCode());
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
		Mail other = (Mail) obj;
		if (mail_desc == null) {
			if (other.mail_desc != null)
				return false;
		} else if (!mail_desc.equals(other.mail_desc))
			return false;
		if (mail_id != other.mail_id)
			return false;
		if (participants == null) {
			if (other.participants != null)
				return false;
		} else if (!participants.equals(other.participants))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mail [mail_id=" + mail_id + ", mail_desc=" + mail_desc + ", participants=" + participants + "]";
	}
	
	

}
