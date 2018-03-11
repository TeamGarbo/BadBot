package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class BadClub implements Serializable{

	BadSession session; 
	String clubID;
	ArrayList<BadSession> pastSessions = new ArrayList<>();
	ArrayList<BadPlayer> players = new ArrayList<>();
	LinkedList<BadPlayer> playerQueue = new LinkedList<>();
		
	public ArrayList<BadPlayer> getPlayers() {
		return players;
	}
	
	public int getLowestElo(){
		int lowest = Integer.MAX_VALUE;
		int highest = Integer.MIN_VALUE;
		for(BadPlayer player : players){
			if(player.getElo() < lowest){
				lowest = player.getElo();
			}
			if(player.getElo() > highest){
				highest = player.getElo();
			}
		}

		return lowest;
	}

	public int getHighestElo(){
		int highest = Integer.MIN_VALUE;
		for(BadPlayer player : players){
			if(player.getElo() > highest){
				highest = player.getElo();
			}
		}

		return highest;
	}

	public void addPlayer(BadPlayer player)
	{
		players.add(player);
		playerQueue.add(player);
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

	public BadPlayer[] getTeam(int size){
		BadPlayer[] players = new BadPlayer[size];
		if(playerQueue.size() >= size) {
			for(int i=0; i<players.length; i++) {
				players[i] = playerQueue.pop();
			}
		}
		else {
			return null;
		}
		return players;
	}
	
	public void addToQueue(BadPlayer player) {
		playerQueue.add(player);
	}
}
