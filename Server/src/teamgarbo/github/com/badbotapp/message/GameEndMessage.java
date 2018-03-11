package teamgarbo.github.com.badbotapp.message;

import model.Properties;

public class GameEndMessage extends Message{	
	
	public int result; 
	
	public GameEndMessage(String clubID, String playerID, int result) {
		this.clubID = clubID;
		this.playerID = playerID;
		this.result = result;
	}
}
