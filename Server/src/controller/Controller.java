package controller;

import model.BadSession;

public class Controller {

	BadSession session; //this is the current session
	Server server;
	
	private Controller() {
		
	}
	
	private static Controller instance;
	
	public static Controller getInstance() {
		if(instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
}
