package model;

import java.util.ArrayList;
import java.util.Date;

public class BadMatch {

	ArrayList<BadPlayer> players;
	ArrayList<BadPlayer> winners;
	ArrayList<BadPlayer> losers;
	Date timeStarted;
	int court;
	
	public BadMatch(ArrayList<BadPlayer> players, ArrayList<BadPlayer> winners, ArrayList<BadPlayer> losers, Date timeStarted, int court) {
		this.players = players;
		this.winners = winners;
		this.losers = losers;
		this.timeStarted = timeStarted;
		this.court = court;
	}

	public ArrayList<BadPlayer> getPlayers() {
		return players;
	}

	public ArrayList<BadPlayer> getWinners() {
		return winners;
	}

	public ArrayList<BadPlayer> getLosers() {
		return losers;
	}

	public Date getTimeStarted() {
		return timeStarted;
	}

	public int getCourt() {
		return court;
	}
	
	
}
