package teamgarbo.github.com.badbotapp.message;

public class ExistingPlayerMessage extends Message{

	String name;
	public ExistingPlayerMessage(String clubID, String playerID, String name) {
		super(clubID, playerID);
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

}
