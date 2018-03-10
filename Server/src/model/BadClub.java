package model;

import java.io.Serializable;
import java.util.ArrayList;

public class BadClub implements Serializable{

	BadSession session; 
	String clubID;
	ArrayList<BadSession> pastSessions = new ArrayList<>();
	ArrayList<BadPlayer> players = new ArrayList<>();
		
	public ArrayList<BadPlayer> getPlayers() {
		return players;
	}

	public void addPlayer(BadPlayer player)
	{
		players.add(player);
	}
	
	public void addPlayers(ArrayList<BadPlayer> players) {
		this.players.addAll(players);
	}

	public BadClub(String id) {
		this.clubID = id;
	}

	public BadSession getSession() {
		return session;
	}

	public void setSession(BadSession session) {
		this.session = session;
	}

	public String getClubID() {
		return clubID;
	}

	public void setClubID(String clubID) {
		this.clubID = clubID;
	}
	
	public ArrayList<BadSession> getPastSessions(){
		return this.pastSessions;
	}
	
	public void newSession(BadSession sess) {
		pastSessions.add(this.session);
		this.session = sess;
	}
	
}
