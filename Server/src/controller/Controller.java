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
	private int fixedCourtNumber = 1;
	
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
				RequestLogout msg = new RequestLogout(message.getClubID(), message.getPlayerID());
				this.server.sendMessage(msg.getPlayerID(), msg);
			}
			
			BadPlayer getPlayer = allPlayers.get(message.getPlayerID());
			System.out.println(getPlayer);
			if(getPlayer==null) {
				//TODO Create player properly
				RequestPlayerMessage msg = new RequestPlayerMessage(message.getClubID(), message.getPlayerID());
				this.server.sendMessage(msg.getPlayerID(), msg);
/*				
				BadPlayer myPlayer = new BadPlayer("Guest", message.getPlayerID());
				myPlayer.setElo(1000);
				allPlayers.put(myPlayer.getID(), myPlayer);
				club.addPlayer(myPlayer);*/
			}else{
				ExistingPlayerMessage msg = new ExistingPlayerMessage(message.getClubID(), message.getPlayerID(), getPlayer.getName());
				this.server.sendMessage(msg.getPlayerID(), msg);

				BadPlayer[] nextPlayers = club.addToQueue(getPlayer);
				if(nextPlayers!=null) {
					fixedCourtNumber++;
					for (BadPlayer dude : nextPlayers) {
						//TODO change court number
						GameStartMessage newMessage = new GameStartMessage(club.getClubID(), dude.getID(), fixedCourtNumber);
						this.server.sendMessage(newMessage.getPlayerID(), newMessage);
					}
				}
			}
			System.out.println("Player Created: " + message.getClubID() + " " + message.getPlayerID());
			
			StringMessage strMsg = new StringMessage(message.getClubID(), message.getPlayerID(), "Initial message received");
			this.server.sendMessage(strMsg.getPlayerID(), strMsg);
		}
		else if(message instanceof CreatePlayerMessage){
			BadClub club = clubs.get(message.getClubID());
			BadPlayer player = new BadPlayer(((CreatePlayerMessage) message).getName(), message.getPlayerID());
			player.setElo(((CreatePlayerMessage) message).getElo());
			allPlayers.put(player.getID(), player);

			BadPlayer[] nextPlayers = club.addToQueue(player);
			if(nextPlayers!=null) {
				fixedCourtNumber++;
				for (BadPlayer dude : nextPlayers) {
					//TODO change court number
					GameStartMessage newMessage = new GameStartMessage(club.getClubID(), dude.getID(), fixedCourtNumber);
					this.server.sendMessage(newMessage.getPlayerID(), newMessage);
				}
			}
			
			ExistingPlayerMessage msg = new ExistingPlayerMessage(message.getClubID(), message.getPlayerID(), ((CreatePlayerMessage) message).getName());
			this.server.sendMessage(msg.getPlayerID(), msg);
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
			BadPlayer[] nextPlayers = club.addToQueue(player);
			if(nextPlayers!=null) {
				fixedCourtNumber++;
				for (BadPlayer dude : nextPlayers) {
					//TODO change court number
					GameStartMessage newMessage = new GameStartMessage(club.getClubID(), dude.getID(), fixedCourtNumber);
					this.server.sendMessage(newMessage.getPlayerID(), newMessage);
				}
			}
			
			System.out.println("Player ended game: " + message.getClubID() + " " + message.getPlayerID());
			StringMessage strMsg = new StringMessage(club.getClubID(), player.getID(), "Game End received");
			this.server.sendMessage(strMsg.getPlayerID(), strMsg);
		}
		else if(message instanceof LogoutMessage) {
			BadClub club = clubs.get(message.getClubID());
			BadPlayer player = allPlayers.get(message.getPlayerID());
			
			RequestLogout msg = new RequestLogout(club.getClubID(), player.getID());
			this.server.sendMessage(msg.getPlayerID(), msg);
			
			System.out.println("Player logged out: " + message.getClubID() + " " + message.getPlayerID());
			club.removeFromQueue(player);
			//this.server.closeSocket(player.getID());
			
		}
	}
	
	//only called when a new session is created
	public void  initSession(Date date, String clubID) {
		
		clubs.get(clubID).newSession(new BadSession(date));
		
		//add players
	}
	
	public void addClub(String id, int noCourts, int size) {
		BadClub club = new BadClub(id, noCourts, size);
		clubs.put(id, club);
	}
	
	public BadClub getClub(String clubID) {
		return clubs.get(clubID);
	}
	
	public Server getServer() {
		return this.server;
	}
}
