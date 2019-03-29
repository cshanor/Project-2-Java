package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Tag;
import com.revature.repos.TagRepo;

@Service
public class TagService {
	
private TagRepo tagRepo;
	
	@Autowired
	public TagService(TagRepo TagRepo) {
		this.tagRepo = TagRepo;
	}
	
	@Transactional(readOnly=true, isolation=Isolation.SERIALIZABLE)
	public List<Tag> getAll() {
		return tagRepo.getAll();
	}
	
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	public Tag getById(int id) {
		return tagRepo.getById(id);
	}
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED, propagation=Propagation.REQUIRED)
	public Tag add(Tag newTag) {
		return tagRepo.add(newTag);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Tag update(Tag updatedTag) {
		return tagRepo.update(updatedTag);
	}
	
	@Transactional // default isolation = READ_UNCOMMITTED; default propagation = REQUIRED
	public boolean delete(int id) {
		return tagRepo.delete(id);
	}

}
