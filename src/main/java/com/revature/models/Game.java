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
@Table(name="GAMES")
@SequenceGenerator(name="game_seq", sequenceName="GAME_PK_SEQ", allocationSize=1)
public class Game {
	
	@Id
	@Column(name="game_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="game_seq")
	private int game_id;
	
	@Column(name="game_title")
	private String game_title;
	
	@Column(name="game_description")
	private String game_desc;
	

	@Column(name="game_twitch_id")
	private int twitch_id;
	
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
	private List<User> subscribers;

	public Game(int game_id, String game_title, String game_desc, int twitch_id, List<User> subscribers) {
		super();
		this.game_id = game_id;
		this.game_title = game_title;
		this.game_desc = game_desc;
		this.twitch_id = twitch_id;
		this.subscribers = subscribers;
	}

	public Game(String game_title, String game_desc, int twitch_id) {
		super();
		this.game_title = game_title;
		this.game_desc = game_desc;
		this.twitch_id = twitch_id;
	}

	public Game() {
		super();
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public String getGame_title() {
		return game_title;
	}

	public void setGame_title(String game_title) {
		this.game_title = game_title;
	}

	public String getGame_desc() {
		return game_desc;
	}

	public void setGame_desc(String game_desc) {
		this.game_desc = game_desc;
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
		result = prime * result + ((game_desc == null) ? 0 : game_desc.hashCode());
		result = prime * result + game_id;
		result = prime * result + ((game_title == null) ? 0 : game_title.hashCode());
		result = prime * result + ((subscribers == null) ? 0 : subscribers.hashCode());
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
		Game other = (Game) obj;
		if (game_desc == null) {
			if (other.game_desc != null)
				return false;
		} else if (!game_desc.equals(other.game_desc))
			return false;
		if (game_id != other.game_id)
			return false;
		if (game_title == null) {
			if (other.game_title != null)
				return false;
		} else if (!game_title.equals(other.game_title))
			return false;
		if (subscribers == null) {
			if (other.subscribers != null)
				return false;
		} else if (!subscribers.equals(other.subscribers))
			return false;
		if (twitch_id != other.twitch_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [game_id=" + game_id + ", game_title=" + game_title + ", game_desc=" + game_desc + ", twitch_id="
				+ twitch_id + ", subscribers=" + subscribers + "]";
	}

}
