package model;

public class BadPlayer {

	String name;
	int wins;
	int losses;
	int draws;
	BadMatch lastMatch;
	String ID;
	
	public String getName() {
		return name;
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
