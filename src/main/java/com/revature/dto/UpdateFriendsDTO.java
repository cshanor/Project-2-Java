package com.revature.dto;

import com.revature.models.User;


/**
 * 
 * @author Brandon Morris
 * 
 * Data Transfer object to assist
 * in Request Handling. 
 *
 */
public class UpdateFriendsDTO {

	private User user;
	
	private String friend_username;
	
	
	
	
	public UpdateFriendsDTO() {
		super();
	
	}
	
	

	public UpdateFriendsDTO(User user) {
		super();
		this.user = user;
	}



	public UpdateFriendsDTO(User user, String friend_username) {
		this.user = user;
		this.friend_username = friend_username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFriend_username() {
		return friend_username;
	}

	public void setFriend_username(String friend_username) {
		this.friend_username = friend_username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((friend_username == null) ? 0 : friend_username.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		UpdateFriendsDTO other = (UpdateFriendsDTO) obj;
		if (friend_username == null) {
			if (other.friend_username != null)
				return false;
		} else if (!friend_username.equals(other.friend_username))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "UpdateFriendsDTO [user=" + user + ", friend_username=" + friend_username + "]";
	}


	
	
	
	
	
}
