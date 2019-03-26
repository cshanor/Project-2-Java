package com.revature;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.Tag;

@Repository
public class GameDriver {
	
	static SessionFactory factory;

	@Autowired
	public void setFactory(SessionFactory factor) {
		factory = factor;
	}
	
	public static void main(String[] args) {
		
		
		List<Tag> games = new ArrayList<>();
		
		Tag game1 = new Tag("Fortnite", "Fortnite Battle  a pickaxe.", 33214);
		Tag game2 = new Tag("League of Legends","Nothing but salt",21779);
		Tag game3 = new Tag("Grand Theft Auto V", "Lots of hackers and 12 year olds", 32982);
		Tag game4 = new Tag("Sekiro: Shadows Die Twice", "Your a cool samurai ninja guy", 506415);
		Tag game5 = new Tag("Apex Legends", "Shooty mc gun game again with robots this time", 511224);

		Session session = factory.getCurrentSession();
		session.save(games);
			
	}
	
}
