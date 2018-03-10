package controller;

import java.util.Date;

import model.BadSession;

public class Controller {

	BadSession session; //this is the current session
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
	
	//only called when a new session is created
	public void  initSession(Date date) {
		session = new BadSession(date);
		
		//add players
	}
	
	public BadSession getSession() {
		return this.session;
	}
	
	public Server getServer() {
		return this.server;
	}
}
