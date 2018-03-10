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
		clubs = new HashMap<String, BadClub>();
		
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
		initServer();
		
		for (String key : clubs.keySet()) {
		    createGameLoop(clubs.get(key));
		}

	}
	
	public void initServer() {
		new Thread()
        {
            public void run() {
            	server = new Server();
            }
        }.start();
	}
	
	private void createGameLoop(BadClub badClub) {
		new GameLoop(badClub);
		
	}

	public void processMessage(Message message) {
		System.out.println("hello");
	}
	
	//only called when a new session is created
	public void  initSession(Date date, String clubID) {
		
		clubs.get(clubID).newSession(new BadSession(date));
		
		//add players
	}
	
	public void addClub(String id) {
		BadClub club = new BadClub(id);
		clubs.put(id, club);
		
	}
	
	public BadClub getClub(String clubID) {
		return clubs.get(clubID);
	}
	
	public Server getServer() {
		return this.server;
	}
}
