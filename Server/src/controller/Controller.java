package controller;

import java.util.Date;
import java.util.HashMap;

import model.BadClub;
import model.BadSession;
import teamgarbo.github.com.badbotapp.message.Message;

public class Controller {

	HashMap<String, BadClub> clubs; //this is the current session
	Server server;
	
	private Controller() {
		init();
	}
	
	private static Controller instance;
	
	public static Controller getInstance() {
		if(instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
	//Only called when server is first created (maybe make a server)
	public void init() {
		
	}
	
	public void processMessage(Message message) {
		System.out.println("hello");
	}
	
	//only called when a new session is created
	public void  initSession(Date date, String clubID) {
		
		clubs.get(clubID).newSession(new BadSession(date));
		
		//add players
	}
	
	public BadClub getClub(String clubID) {
		return clubs.get(clubID);
	}
	
	public Server getServer() {
		return this.server;
	}
}
