package com.revature.repos;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.Tag;


@Repository
public class TagRepo {
	
	private SessionFactory factory;
	//use this logger later for debugging if needed. REMOVE in production, or implement AOP logging.  
	Logger log = Logger.getLogger(TagRepo.class);

	@Autowired
	public TagRepo(SessionFactory factory) {
		this.factory = factory;
	}
	
	public List<Tag> getAll(){
		Session s = factory.getCurrentSession();
		return s.createQuery("from Tags", Tag.class).getResultList();
	}
	
	public Tag getById(int id) {
		Session s = factory.getCurrentSession();
		return s.get(Tag.class, id);
	}
	
	public Tag add(Tag newTag) {
		Session s = factory.getCurrentSession();
		s.save(newTag);
		return newTag;
	}
	
	public Tag update(Tag updatedTag) {
		Session s = factory.getCurrentSession();
		Tag tag = s.get(Tag.class, updatedTag.getTag_id());
		if(tag == null) return null;
		else tag = updatedTag;
		return tag; 
		
	}
	
	public boolean delete(int id) {
		Session s = factory.getCurrentSession();
		Tag tag = s.get(Tag.class, id);
		if(tag == null) return false;
		s.delete(tag);
		return true; 
	}
	
}
