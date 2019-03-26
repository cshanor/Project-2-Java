package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Profile;
import com.revature.repos.ProfileRepo;

@Service
public class ProfileService {
	
	private ProfileRepo profileRepo;
	
	@Autowired
	public ProfileService(ProfileRepo ProfileRepo) {
		profileRepo = ProfileRepo;
	}
	
	@Transactional(readOnly=true, isolation=Isolation.SERIALIZABLE)
	public List<Profile> getAll() {
		return profileRepo.getAll();
	}
	
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	public Profile getById(int id) {
		return profileRepo.getById(id);
	}
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED, propagation=Propagation.REQUIRED)
	public Profile add(Profile newProfile) {
		return profileRepo.add(newProfile);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Profile update(Profile updatedProfile) {
		return profileRepo.update(updatedProfile);
	}
	
	@Transactional // default isolation = READ_UNCOMMITTED; default propagation = REQUIRED
	public boolean delete(int id) {
		return profileRepo.delete(id);
	}

}
