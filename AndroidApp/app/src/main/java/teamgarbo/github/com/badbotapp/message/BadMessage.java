package teamgarbo.github.com.badbotapp.message;

public class BadMessage extends Message{

	//BadAction action; //need some form of action on the message

	private static final long serialVersionUID = 1L;

	public BadMessage(String clubID, String playerID) {
		super();
		this.clubID = clubID;
		this.playerID = playerID;
	}
}
