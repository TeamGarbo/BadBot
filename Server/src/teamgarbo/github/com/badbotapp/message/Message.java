package teamgarbo.github.com.badbotapp.message;

import java.io.Serializable;

public abstract class Message implements Serializable{

	String clubID;
	String playerID;

	public Message(	String clubID, String playerID){
		this.clubID = clubID;
		this.playerID = playerID;
	}
	
	public String getClubID() {
		return clubID;
	}
	public String getPlayerID() {
		return playerID;
	}
	
}
