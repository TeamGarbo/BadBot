package controller;

import java.util.Date;
import java.util.HashMap;
import model.BadClub;
import model.BadPlayer;
import model.BadSession;
import teamgarbo.github.com.badbotapp.message.BadMessage;
import teamgarbo.github.com.badbotapp.message.Message;

public class Controller {

	HashMap<String, BadClub> clubs; //this is the current session
	Server server;
	HashMap<String, BadPlayer> allPlayers;
	
	private Controller() {
		clubs = new HashMap<>();
		allPlayers = new HashMap<>();
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


	///TODO: add player registration (name)
	public void processMessage(Message message) {
		if(message instanceof BadMessage) {
			BadClub club = clubs.get(message.getClubID());
			BadPlayer getPlayer = allPlayers.get(message.getPlayerID();
			if(getPlayer==null) {
				club.addPlayer(new BadPlayer(message.getPlayerID(), ));
			}else{
				club.addPlayer(getPlayer);
			}
		}
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
