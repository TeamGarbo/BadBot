package message;

public class BadMessage extends Message{

	//BadAction action; //need some form of action on the message
	
	public BadMessage(String clubID, String playerID) {
		super();
		this.clubID = clubID;
		this.playerID = playerID;
	}
}
