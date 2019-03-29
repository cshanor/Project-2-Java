package com.revature.repos;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.Profile;
/**
 * 
 * @author Brandon Morris
 *
 */
@Repository
public class ProfileRepo {

	private SessionFactory factory;

	//use this logger later for debugging if needed. REMOVE in production, or implement AOP logging.  
	Logger log = Logger.getLogger(ProfileRepo.class);
	
	@Autowired
	public ProfileRepo(SessionFactory factory) {
		this.factory = factory;
	}
	
	public List<Profile> getAll(){
		Session s = factory.getCurrentSession();
		return s.createQuery("from Profile", Profile.class).getResultList();
	}
	
	public Profile getById(int id) {
		Session s = factory.getCurrentSession();
		return s.get(Profile.class, id);
	}
	
	public Profile add(Profile newProfile) {
		Session s = factory.getCurrentSession();
		s.save(newProfile);
		return newProfile;
	}
	
	public Profile update(Profile updatedProfile) {
		Session s = factory.getCurrentSession();
		Profile profile = s.get(Profile.class, updatedProfile.getProfile_id());
		if(profile == null) return null;
		else profile = updatedProfile;
		return profile;
	}
	
	public boolean delete(int id) {
		Session s = factory.getCurrentSession();
		Profile profile = s.get(Profile.class, id);
		if(profile == null) return false;
		s.delete(profile);
		return true;
	}
	
	
}
