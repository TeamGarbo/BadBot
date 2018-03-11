package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BadSession implements Serializable{

	String sessID; 
	ArrayList<BadPlayer> players;
	ArrayList<BadMatch> pastMatches;
	HashMap<Integer, BadMatch> currentMatches;
	Date date;

	private Match currentMatch = null;
	
	
	 
	public BadSession(Date date) {
		this.date = date;
		players = new ArrayList<BadPlayer>();
		pastMatches = new ArrayList<BadMatch>();
		currentMatches = new HashMap<Integer, BadMatch>();
	}
	
	public void addPlayer(BadPlayer player) {
		players.add(player);
	}
	public void addPlayers(ArrayList<BadPlayer> players) {
		players.addAll(players);
	}
	
	
	public void removePlayer(BadPlayer player) {
		players.remove(player);
	}
	
	public void newMatch(Integer court, BadMatch match) {
		BadMatch oldMatch = currentMatches.get(court);
		pastMatches.add(oldMatch);
		currentMatches.put(court, match);
	}
	
	public ArrayList<BadMatch> getPastMatches(){
		return this.pastMatches;
	}
	
	public ArrayList<BadPlayer> getPlayers(){
		return this.players;
	}
	
	public void setPastMatches(ArrayList<BadMatch> pastMatches) {
		this.pastMatches = pastMatches;
	}
	
	public Date getDate() {
		return this.date;
	}
	
}
