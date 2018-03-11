package teamgarbo.github.com.badbotapp.message;

public class GameEndMessage extends Message{

	public enum RESULT{
		WIN, DRAW, LOSE
	}
	
	public GameEndMessage(String clubID, String playerID, int result) {
		this.clubID = clubID;
		this.playerID = playerID;
		
		switch(result) {
		case RESULT.
		}
	}
}
