package teamgarbo.github.com.badbotapp.message;

import java.io.Serializable;

public abstract class Message implements Serializable{

	String clubID;
	String playerID;
	
	public String getClubID() {
		return clubID;
	}
	public String getPlayerID() {
		return playerID;
	}
	
}
