package model;

import java.util.ArrayList;

public class BadClub {

	BadSession session; 
	String clubID;
	ArrayList<BadSession> pastSessions;
	
	public BadClub() {
		
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
