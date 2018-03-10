package model;

public class BadPlayer {

	String name;
	int wins = 0;
	int losses = 0;
	int draws = 0;
	BadMatch lastMatch = null;
	String ID;
	int elo = -1; //this is the rank of the player
	
	
	
	public BadPlayer(String name, String iD) {
		this.name = name;
		ID = iD;
	}
	public BadPlayer(String name, String iD, int elo) {
		this.name = name;
		ID = iD;
		this.elo = elo;
	}
	
	public String getName() {
		return name;
	}
	
	public int getElo() {
		return this.elo;
	}
	
	public void setElo(int elo) {
		this.elo = elo;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getWins() {
		return wins;
	}
	
	public void incrementWins() {
		wins++;
	}
	
	public int getLosses() {
		return losses;
	}
	
	public void incrementLosses() {
		losses++;
	}
	
	public int getDraws() {
		return draws;
	}
	
	public void incrementDraws() {
		draws++;
	}
	
	public BadMatch getLastMatch() {
		return lastMatch;
	}
	
	public void setLastMatch(BadMatch lastMatch) {
		this.lastMatch = lastMatch;
	}
	
	public String getID() {
		return ID;
	}
	
}
