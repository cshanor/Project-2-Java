package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PROFILE")
@SequenceGenerator(name="profile_seq", sequenceName="PROFILE_PK_SEQ", allocationSize=1)
public class Profile {
	
	@Id
	@Column(name="profile_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="profile_seq")
	private int profile_id;

	@Column(name="first_name")
	private String first_name;

	@Column(name="last_name")
	private String last_name;

	@Column(name="alias")
	private String alias;

	@Column(name="slogan")
	private String slogan;

	@Column(name="icon_id")
	private int icon_id;

	@Column(name="description")
	private String desc;

	public Profile() {
		super();
	}

	public Profile(String first_name, String last_name, String alias, String slogan, int icon_id, String desc) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.alias = alias;
		this.slogan = slogan;
		this.icon_id = icon_id;
		this.desc = desc;
	}

	public Profile(int profile_id, String first_name, String last_name, String alias, String slogan, int icon_id,
			String desc) {
		super();
		this.profile_id = profile_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.alias = alias;
		this.slogan = slogan;
		this.icon_id = icon_id;
		this.desc = desc;
	}

	public int getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(int profile_id) {
		this.profile_id = profile_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public int getIcon_id() {
		return icon_id;
	}

	public void setIcon_id(int icon_id) {
		this.icon_id = icon_id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((first_name == null) ? 0 : first_name.hashCode());
		result = prime * result + icon_id;
		result = prime * result + ((last_name == null) ? 0 : last_name.hashCode());
		result = prime * result + profile_id;
		result = prime * result + ((slogan == null) ? 0 : slogan.hashCode());
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
		Profile other = (Profile) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (first_name == null) {
			if (other.first_name != null)
				return false;
		} else if (!first_name.equals(other.first_name))
			return false;
		if (icon_id != other.icon_id)
			return false;
		if (last_name == null) {
			if (other.last_name != null)
				return false;
		} else if (!last_name.equals(other.last_name))
			return false;
		if (profile_id != other.profile_id)
			return false;
		if (slogan == null) {
			if (other.slogan != null)
				return false;
		} else if (!slogan.equals(other.slogan))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Profile [profile_id=" + profile_id + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", alias=" + alias + ", slogan=" + slogan + ", icon_id=" + icon_id + ", desc=" + desc + "]";
	}

}
