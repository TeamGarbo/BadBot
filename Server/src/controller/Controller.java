package controller;

import java.util.Date;
import java.util.HashMap;
import model.BadClub;
import model.BadPlayer;
import model.BadSession;
import model.Properties;
import teamgarbo.github.com.badbotapp.message.*;

public class Controller {

	HashMap<String, BadClub> clubs; //this is the current session
	private Server server;
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
            	Controller.getInstance().setServer(new Server());
            }
        }.start();
	}
	
	private void createGameLoop(BadClub badClub) {
		new GameLoop(badClub);
		
	}

	public void setServer(Server server) {
		this.server = server;
	}

	///TODO: add player registration (name)
	public void processMessage(Message message) {
		if(message instanceof InitialMessage) {
			BadClub club = clubs.get(message.getClubID());
			if(club==null) {
				club = new BadClub(message.getClubID());
				clubs.put(club.getClubID(), club);
			}
			
			BadPlayer getPlayer = allPlayers.get(message.getPlayerID());
			if(getPlayer==null) {
				//TODO Create player properly
				BadPlayer myPlayer = new BadPlayer("Guest", message.getPlayerID());
				myPlayer.setElo(1000);
				allPlayers.put(myPlayer.getID(), myPlayer);
				club.addPlayer(myPlayer);
			}else{
				club.addPlayer(getPlayer);
			}
			System.out.println(message.getClubID() + " " + message.getPlayerID());
			
			this.server.sendMessage(message.getPlayerID(), message);
		}
		else if(message instanceof GameEndMessage) {
			BadClub club = clubs.get(message.getClubID());
			BadPlayer player = allPlayers.get(message.getPlayerID());
			
			switch(((GameEndMessage) message).result) {
			case Properties.WIN: player.incrementWins();
				break;
			case Properties.DRAW: player.incrementDraws();
				break;
			case Properties.LOSS: player.incrementLosses();
				break;
			default: player.incrementDraws(); //if its none then just increment the draws
				break;
			}
			club.addToQueue(player);
			
			BadPlayer[] nextPlayers = club.getTeam(4);
			for(BadPlayer dude: nextPlayers) {
				//TODO change court number 
				GameStartMessage newMessage = new GameStartMessage(club.getClubID(), dude.getID(), 4);
				this.server.sendMessage(newMessage.getPlayerID(), newMessage);
			}
		}
		else if(message instanceof LogoutMessage) {
			BadClub club = clubs.get(message.getClubID());
			BadPlayer player = allPlayers.get(message.getPlayerID());
			
			club.removeFromQueue(player);
			this.server.closeSocket(player.getID());
		}
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
