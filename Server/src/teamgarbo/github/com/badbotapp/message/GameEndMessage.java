package teamgarbo.github.com.badbotapp.message;

import model.Properties;

public class GameEndMessage extends Message{	
	
	public int result; 
	
	public GameEndMessage(String clubID, String playerID, int result) {
		super(clubID, playerID);
		this.result = result;
	}
}
